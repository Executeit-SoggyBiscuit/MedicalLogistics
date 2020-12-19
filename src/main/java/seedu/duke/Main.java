package seedu.Main;

import Medication.Medicationmanager.MedicationManager;
import logger.SchwarzeneggerLogger;
import logic.commands.CommandLib;
import logic.parser.CommonParser;
import ui.CommonUi;

import java.util.logging.Logger;


/**
 * The Schwarzenegger program implements an application that keeps track of the user's gym and diet record.
 */
public class Main {
    private static Logger logger;
    private CommandLib cl;
    private CommonUi ui;
    private CommonParser parser;

    /**
     * Constructs Main object.
     */
    private Main() {
        logger = SchwarzeneggerLogger.getInstanceLogger();
        cl = new CommandLib();
        cl.initMainMenuCl();
        ui = new CommonUi();
        parser = new CommonParser();
    }

    /**
     * Main entry-point for the java.seedu.Main.Main application.
     *
     * @param args Unused in Schwarzenegger.
     */
    public static void main(String[] args) {
        new MedicationManager().start();
    }
}
