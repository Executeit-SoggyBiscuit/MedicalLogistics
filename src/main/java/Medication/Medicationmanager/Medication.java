package Medication.Medicationmanager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import diet.dietsession.DietSession;
import exceptions.ExceptionHandler;
import exceptions.InvalidCommandWordException;
import logger.SchwarzeneggerLogger;
import logic.commands.Command;
import logic.commands.CommandLib;
import logic.commands.CommandResult;
import logic.parser.CommonParser;
import logic.parser.DietSessionParser;
import storage.Storage;
import ui.diet.dietsession.MedicationSessionUi;

import static seedu.duke.Constants.PATH_TO_DIET_FOLDER;

/**
 * A class that is responsible for interacting with user in Diet Session.
 */
public class Medication {
    private String name;
    private int quantity;

    private transient CommandLib cl;
    private final Storage storage = new Storage();
    public boolean endSession = false;
    //private ArrayList<Pair<Location, int>>

    private static Logger logger = SchwarzeneggerLogger.getInstanceLogger();

    private boolean isNew;
    private int index;

    private final MedicationSessionUi medicationSessionUi;
    private final DietSessionParser parser = new DietSessionParser();
    public boolean endDietSession = false;

    public Medication(String name, int quantity){
        this.name = name;
        this.quantity = quantity;
        this.cl = new CommandLib();
        medicationSessionUi = new MedicationSessionUi();
        cl.initMedicationSessionCl();
        //something for location
    }


    public void setQuantity(int newQuantity) {
        this.quantity = newQuantity;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public String getName() {
        return this.name;
    }

    private void processCommand(String input) throws NullPointerException, InvalidCommandWordException {
        CommonParser parser = new CommonParser();
        String[] commParts = parser.parseCommand(input);
        CommandLib cl = new CommandLib();
        Command command = cl.getCommand(commParts[0]);
        CommandResult commandResult = command.execute(commParts[1].trim(), storage, index);
        medicationSessionUi.showToUser(commandResult.getFeedbackMessage());
        saveToFile(PATH_TO_DIET_FOLDER, storage, this);
    }

    public void start(boolean isNew, int index) throws IOException {

        this.cl = new CommandLib();
        cl.initMedicationSessionCl();
        this.endSession = false;
        // save the file upon creation
        saveToFile(PATH_TO_DIET_FOLDER, storage, this);
        dietSessionInputLoop();
        this.endSession = true;
    }

    private void dietSessionInputLoop() {
        String input = "";
        input = medicationSessionUi.getCommand("Diet Menu > Diet Session " + index);

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
                input = medicationSessionUi.getCommand("Diet Menu > New Diet Session");
            } else {
                input = medicationSessionUi.getCommand("Diet Menu > Diet Session " + index);
            }
        }
    }

    public void saveToFile(String filePath, Storage storage, Medication ds) {
        try {
            storage.init(filePath, ds.getName());
            storage.saveMedication(filePath, ds.getName(), ds);
            logger.log(Level.INFO, "Diet session successfully saved");
        } catch (IOException e) {
            logger.log(Level.WARNING, "save profile session failed");
            MedicationSessionUi.showToUser("Failed to save your diet session!");
        }
    }
}

