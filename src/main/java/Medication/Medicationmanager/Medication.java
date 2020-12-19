package Medication.Medicationmanager;

import java.io.IOException;

import java.util.logging.Level;
import java.util.logging.Logger;

import exceptions.InvalidCommandWordException;
import exceptions.InvalidDateFormatException;
import exceptions.diet.InvalidSearchDateException;
import logger.SchwarzeneggerLogger;
import logic.commands.Command;
import logic.commands.CommandLib;
import logic.commands.CommandResult;
import logic.parser.CommonParser;
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

    private static Logger logger = SchwarzeneggerLogger.getInstanceLogger();


    private final MedicationSessionUi medicationSessionUi;

    @SuppressWarnings({"checkstyle:MissingJavadocMethod", "checkstyle:WhitespaceAround"})
    public Medication(String name, int quantity){
        this.name = name;
        this.quantity = quantity;
        this.cl = new CommandLib();
        medicationSessionUi = new MedicationSessionUi();
        cl.initMedicationSessionCl();
    }


    @SuppressWarnings("checkstyle:MissingJavadocMethod")
    public void setQuantity(int newQuantity) {
        this.quantity = newQuantity;
    }

    @SuppressWarnings("checkstyle:MissingJavadocMethod")
    public int getQuantity() {
        return this.quantity;
    }

    @SuppressWarnings("checkstyle:MissingJavadocMethod")
    public String getName() {
        return this.name;
    }

    @SuppressWarnings("checkstyle:MissingJavadocMethod")
    private void processCommand(String input) throws NullPointerException, InvalidDateFormatException, InvalidCommandWordException, InvalidSearchDateException {
        CommonParser parser = new CommonParser();
        String[] commParts = parser.parseCommand(input);
        CommandLib cl = new CommandLib();
        Command command = cl.getCommand(commParts[0]);
        CommandResult commandResult = command.execute(commParts[1].trim(), storage);
        medicationSessionUi.showToUser(commandResult.getFeedbackMessage());
        saveToFile(PATH_TO_DIET_FOLDER, storage, this);
    }

    private void saveToFile(String filePath, Storage storage, Medication ds) {
        try {
            storage.init(filePath, ds.getName());
            storage.saveMedication(filePath, ds.getName(), ds);
            logger.log(Level.INFO, "Diet session successfully saved");
        } catch (IOException e) {
            logger.log(Level.WARNING, "save profile session failed");
            MedicationSessionUi.showToUser("Failed to save your diet session!");
        }
    }

    @SuppressWarnings("checkstyle:MissingJavadocMethod")
    public void start(boolean isNew, int index) throws IOException {

        this.cl = new CommandLib();
        cl.initMedicationSessionCl();
        this.endSession = false;
        // save the file upon creation
        saveToFile(PATH_TO_DIET_FOLDER, storage, this);
        dietSessionInputLoop();
        this.endSession = true;
    }

    @SuppressWarnings("checkstyle:MissingJavadocMethod")
    private void dietSessionInputLoop() {
        String input = "";
        input = medicationSessionUi.getCommand("Diet Menu > Diet Session ");

        while (!input.equals("end")) {

            try {
                processCommand(input);
            } catch (NullPointerException e) {
                break;
            } catch (InvalidSearchDateException e) {
                e.printStackTrace();
            } catch (InvalidCommandWordException e) {
                e.printStackTrace();
            } catch (InvalidDateFormatException e) {
                e.printStackTrace();
            }
        }
    }
}

