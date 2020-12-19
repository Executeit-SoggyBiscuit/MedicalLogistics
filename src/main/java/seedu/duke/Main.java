package seedu.duke;

import Medication.Medicationmanager.MedicationManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import geocoder.GeocodeResult;
import geocoder.Geocoder;
import logger.SchwarzeneggerLogger;
import logic.commands.CommandLib;
import logic.parser.CommonParser;
import ui.CommonUi;

import java.io.IOException;
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
    private Main() throws IOException, InterruptedException {
        Geocoder geocoder = new Geocoder();

        GeocodeResult response = geocoder.geocodeSync("nus");
        for (int i = 0; i < response.getResults().size(); i++) {
            for (int j = 0; j < response.getResults().get(i).getAddressComponents().size(); j++) {
                System.out.println(response.getResults().get(i).getAddressComponents().get(j).getLongName());
            }
        }

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
