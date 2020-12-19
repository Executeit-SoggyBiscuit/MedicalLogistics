package seedu.duke;

import Medication.Medicationmanager.MedicationManager;
import classes.LocationInfo;
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
    private Main(){
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
    public static void main(String[] args) throws IOException, InterruptedException  {

        Geocoder geocoder = new Geocoder();

        GeocodeResult response = geocoder.geocodeSync("ntu","sg");
        String formattedAddress = "";
        if(userinput==allnumber) {

            for (int i = 0; i < response.getResults().size(); i++) {
                for (int j = 0; j < response.getResults().get(i).getAddressComponents().size(); j++) {
                    formattedAddress += response.getResults().get(i).getAddressComponents().get(j).getLongName() + " ";
                }
            }
            formattedAddress.trim();
        }else{
            formattedAddress = response.getResults().get(0).getFormattedAddress();
        }

        LocationInfo location = new LocationInfo();
        location.setAddress(formattedAddress);
        location.setName("ntu");
        location.setLatitude(response.getResults().get(0).getGeometry().getGeocodeLocation().getLatitude());
        location.setLongitude(response.getResults().get(0).getGeometry().getGeocodeLocation().getLongitude());

        new MedicationManager().start();
    }
}
