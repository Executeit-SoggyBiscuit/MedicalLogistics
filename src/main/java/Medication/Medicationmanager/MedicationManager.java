package Medication.Medicationmanager;

import exceptions.ExceptionHandler;
import exceptions.InvalidCommandWordException;
import exceptions.InvalidDateFormatException;
import exceptions.SchwarzeneggerException;
import exceptions.diet.InvalidSearchDateException;
import logger.SchwarzeneggerLogger;
import logic.commands.Command;
import logic.commands.CommandLib;
import logic.commands.CommandResult;
import logic.parser.MedicationManagerParser;
import storage.Storage;
import ui.diet.dietmanager.MedicationManagerUi;

import java.util.logging.Level;
import java.util.logging.Logger;

import static seedu.duke.Constants.COMMAND_WORD_END;

public class MedicationManager {

    private CommandLib cl;
    private MedicationManagerParser parser;
    private MedicationManagerUi MedicationManagerUi;
    private Storage storage;
    private static Logger logger = SchwarzeneggerLogger.getInstanceLogger();

    /**
     * Constructs MedicationManager and initializes command library for MedicationManager.
     */
    public MedicationManager() {
        storage = new Storage();
        cl = new CommandLib();
        cl.initMedicationManagerCl();
        parser = new MedicationManagerParser();
        MedicationManagerUi = new MedicationManagerUi();
    }

    /**
     * Starts Medication manager to read user input.
     */
    public void start() {
        MedicationManagerUi.printOpening("Medication Menu");
        String input = MedicationManagerUi.getCommand("Medication Menu");
        assert input != null : "Null input before input loop";
        inputLoop(input);
        MedicationManagerUi.printReturning("Main Menu");
    }

    /**
     * Loops the command processing until user types "end".
     *
     * @param input user input String
     */
    private void inputLoop(String input) {
        while (!input.equals(COMMAND_WORD_END)) {
            try {
                processCommand(input);
            } catch (SchwarzeneggerException e) {
                MedicationManagerUi.showToUser(ExceptionHandler.handleCheckedExceptions(e));
            } catch (Exception e) {
                MedicationManagerUi.showToUser(ExceptionHandler.handleUncheckedExceptions(e));
            }
            input = MedicationManagerUi.getCommand("Medication Menu");
        }
    }

    /**
     * Processes the user input to interpret correct command words.
     *
     * @param input user input for command.
     * @throws InvalidCommandWordException handles InvalidCommandWordException.
     * @throws InvalidDateFormatException handles invalid date input
     */
    public void processCommand(String input) throws InvalidCommandWordException {
        String[] commParts = parser.parseCommand(input.trim());
        try {
            Command command = cl.getCommand(commParts[0]);
            CommandResult commandResult = command.execute(commParts[1].trim(), storage);
            MedicationManagerUi.showToUser(commandResult.getFeedbackMessage());
        } catch (ArrayIndexOutOfBoundsException e) {
            logger.log(Level.WARNING, "Invalid command in Medication session");
            throw new InvalidCommandWordException();
        } catch (InvalidDateFormatException e) {
            e.printStackTrace();
        } catch (InvalidSearchDateException e) {
            e.printStackTrace();
        }
    }

}
