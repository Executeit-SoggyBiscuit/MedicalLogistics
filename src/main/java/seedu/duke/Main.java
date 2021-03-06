package seedu.duke;

import Location.LocationManager;
import classes.LocationInfo;

import distMatrix.DistMatrix;
import distMatrix.DistResults;
import geocoder.GeocodeResult;

import geocoder.Geocoder;
import logger.SchwarzeneggerLogger;
import logic.commands.CommandLib;
import logic.parser.CommonParser;
import ui.CommonUi;

import java.io.IOException;
import java.util.ArrayList;
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
        formattedAddress = response.getResults().get(0).getFormattedAddress();
        LocationInfo location = new LocationInfo(formattedAddress,"ntu", response.getResults().get(0).getGeometry().getGeocodeLocation().getLatitude(),response.getResults().get(0).getGeometry().getGeocodeLocation().getLongitude());

        response = geocoder.geocodeSync("nus","sg");
        formattedAddress = response.getResults().get(0).getFormattedAddress();
        LocationInfo location2 = new LocationInfo(formattedAddress,"nus", response.getResults().get(0).getGeometry().getGeocodeLocation().getLatitude(),response.getResults().get(0).getGeometry().getGeocodeLocation().getLongitude());
        response = geocoder.geocodeSync("nuh","sg");
        formattedAddress = response.getResults().get(0).getFormattedAddress();
        LocationInfo location3 = new LocationInfo(formattedAddress,"nus", response.getResults().get(0).getGeometry().getGeocodeLocation().getLatitude(),response.getResults().get(0).getGeometry().getGeocodeLocation().getLongitude());
        response = geocoder.geocodeSync("sim","sg");
        formattedAddress = response.getResults().get(0).getFormattedAddress();
        LocationInfo location4 = new LocationInfo(formattedAddress,"nus", response.getResults().get(0).getGeometry().getGeocodeLocation().getLatitude(),response.getResults().get(0).getGeometry().getGeocodeLocation().getLongitude());
        ArrayList<LocationInfo> locationInfos = new ArrayList<LocationInfo>();
        locationInfos.add(location2);
        locationInfos.add(location3);
        locationInfos.add(location4);


        DistMatrix distMatrix = new DistMatrix();

        DistResults distResults = distMatrix.getClient(location, locationInfos);


        new LocationManager().start();
    }
}
