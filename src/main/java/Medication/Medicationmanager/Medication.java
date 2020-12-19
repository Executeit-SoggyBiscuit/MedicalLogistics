package medication.medicationsession;

import exceptions.ExceptionHandler;
import exceptions.InvalidCommandWordException;
import exceptions.InvalidDateFormatException;
import logger.SchwarzeneggerLogger;
import logic.commands.Command;
import logic.commands.CommandLib;
import logic.commands.CommandResult;
import logic.parser.medicationSessionParser;
import models.Food;
import storage.Storage;
import storage.medication.medicationStorage;
import ui.medication.medicationsession.medicationSessionUi;
import logic.parser.DateParser;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import static seedu.duke.Constants.PATH_TO_medication_FOLDER;

/**
 * A class that is responsible for interacting with user in medication Session.
 */
public class medicationSession {
    private static Logger logger = SchwarzeneggerLogger.getInstanceLogger();
    private final ArrayList<Food> foodList;

    private final String dateInput;
    private final String typeInput;
    private final LocalDate date;
    private boolean isNew;
    private int index;

    private final medicationSessionUi medicationSessionUi;
    private transient CommandLib cl;
    private final Storage storage;
    private final medicationSessionParser parser = new medicationSessionParser();
    public boolean endmedicationSession = false;

    /**
     * Constructs medicationSession and initialize command library for medicationSession.
     *
     * @param typeInput User input for meal type
     * @param dateInput User input for meal date
     * @param isNew Boolean that indicates whether the medication Session is new or not
     * @param index Integer for the index of the medication Session
     * @throws InvalidDateFormatException handles invalid date input
     */
    public medicationSession(String typeInput, String dateInput, boolean isNew, int index) throws InvalidDateFormatException {
        this.cl = new CommandLib();
        cl.initmedicationSessionCl();
        this.dateInput = dateInput;
        this.date = DateParser.parseDate(dateInput).toLocalDate();
        this.typeInput = typeInput;
        this.foodList = new ArrayList<>();
        storage = new medicationStorage();
        medicationSessionUi = new medicationSessionUi();
        this.isNew = isNew;
        this.index = index;
    }

    public String getDateInput() {
        return dateInput;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getTypeInput() {
        return typeInput;
    }

    public void setEndmedicationSession(Boolean hasEnded) {
        this.endmedicationSession = hasEnded;
    }

    /**
     * Starts medicationSession and initializes command library for medicationSession.
     * @param isNew Boolean that indicates whether the medication Session is new or not
     * @param index Integer for the index of the medication Session
     * @throws IOException handles input/output exception
     */
    public void start(boolean isNew, int index) throws IOException {

        logger.log(Level.INFO, "starting medication session");
        this.cl = new CommandLib();
        cl.initmedicationSessionCl();
        medicationSessionUi.printOpening();
        setEndmedicationSession(false);
        this.isNew = isNew;
        this.index = index;
        // save the file upon creation
        saveToFile(PATH_TO_medication_FOLDER, storage, this);
        medicationSessionInputLoop();
        setEndmedicationSession(true);
    }

    /**
     * Starts reading user input for medicationSession commands.
     */
    private void medicationSessionInputLoop() {
        String input = "";

        if (isNew) {
            input = medicationSessionUi.getCommand("medication Menu > New medication Session");
        } else {
            input = medicationSessionUi.getCommand("medication Menu > medication Session " + index);
        }

        while (!input.equals("end")) {

            try {
                processCommand(input);
            } catch (NullPointerException e) {
                medicationSessionUi.showToUser(ExceptionHandler.handleUncheckedExceptions(e));
                break;
            } catch (InvalidCommandWordException e) {
                medicationSessionUi.showToUser(ExceptionHandler.handleCheckedExceptions(e));
            }
            if (isNew) {
                input = medicationSessionUi.getCommand("medication Menu > New medication Session");
            } else {
                input = medicationSessionUi.getCommand("medication Menu > medication Session " + index);
            }
        }
    }

    /**
     * Processes user input for medicationSession commands.
     *
     * @param input user input for command
     * @throws NullPointerException handles null pointer exception
     * @throws InvalidCommandWordException handles invalid commands
     */
    private void processCommand(String input) throws NullPointerException, InvalidCommandWordException {
        String[] commParts = parser.parseCommand(input);
        Command command = cl.getCommand(commParts[0]);
        CommandResult commandResult = command.execute(commParts[1].trim(), foodList, storage, index);
        medicationSessionUi.showToUser(commandResult.getFeedbackMessage());
        saveToFile(PATH_TO_medication_FOLDER, storage, this);
    }

    /**
     * Calculates the sum of all food calories in medication session.
     *
     * @return sum of calories of food
     */
    public double getTotalCalories() {
        double totalCalories = 0;
        for (int i = 0; i < foodList.size(); i++) {
            totalCalories += foodList.get(i).getCalories();
        }
        return totalCalories;
    }


    /**
     * Constructs method to save changes to storage file.
     *
     * @param filePath string for file path
     * @param storage storage for medication manager
     * @param ds      medicationSession that is being changed
     */
    public void saveToFile(String filePath, medicationStorage storage, medicationSession ds) {
        try {
            storage.init(filePath, ds.getDate().toString() + " " + ds.getTypeInput());
            storage.writeToStoragemedicationSession(filePath, ds.getDate().toString() + " " + ds.getTypeInput(), ds);
            logger.log(Level.INFO, "medication session successfully saved");
        } catch (IOException e) {
            logger.log(Level.WARNING, "save profile session failed");
            medicationSessionUi.showToUser("Failed to save your medication session!");
        }
    }
}
