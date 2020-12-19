package Medication.Medicationmanager;

import java.io.IOException;
import java.util.logging.Level;

import exceptions.ExceptionHandler;
import exceptions.InvalidCommandWordException;
import logic.commands.Command;
import logic.commands.CommandLib;
import logic.commands.CommandResult;
import logic.parser.CommonParser;
import storage.Storage;

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

    public Medication(String name, int quantity){
        this.name = name;
        this.quantity = quantity;
        this.cl = new CommandLib();
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
        CommandResult commandResult = command.execute(commParts[1].trim(), foodList, storage, index);
        dietSessionUi.showToUser(commandResult.getFeedbackMessage());
        saveToFile(PATH_TO_DIET_FOLDER, storage, this);
    }

    public void start(boolean isNew, int index) throws IOException {

        this.cl = new CommandLib();
        cl.initMedicationSessionCl();
        endsession = false;
        // save the file upon creation
        saveToFile(PATH_TO_DIET_FOLDER, storage, this);
        dietSessionInputLoop();
        setEndDietSession(true);
    }

    private void dietSessionInputLoop() {
        String input = "";

        if (isNew) {
            input = dietSessionUi.getCommand("Diet Menu > New Diet Session");
        } else {
            input = dietSessionUi.getCommand("Diet Menu > Diet Session " + index);
        }

        while (!input.equals("end")) {

            try {
                processCommand(input);
            } catch (NullPointerException e) {
                dietSessionUi.showToUser(ExceptionHandler.handleUncheckedExceptions(e));
                break;
            } catch (InvalidCommandWordException e) {
                dietSessionUi.showToUser(ExceptionHandler.handleCheckedExceptions(e));
            }
            if (isNew) {
                input = dietSessionUi.getCommand("Diet Menu > New Diet Session");
            } else {
                input = dietSessionUi.getCommand("Diet Menu > Diet Session " + index);
            }
        }
    }

    public void save(Medication med) {
        String filePath;
        try {
            filePath = System.getProperty("user.dir");
            storage.init(filePath, med.getName());
            storage.saveMedication(filePath, med.getName(), med);
        } catch (IOException e) {
            System.out.println("save failed...");
        }
    }
}

