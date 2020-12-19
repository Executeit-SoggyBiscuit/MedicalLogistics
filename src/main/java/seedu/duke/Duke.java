package seedu.duke;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import exceptions.EndException;
import exceptions.ExceptionHandler;
import exceptions.SchwarzeneggerException;
import geocoder.GeocodeResult;
import geocoder.Geocoder;
import logger.SchwarzeneggerLogger;
import logic.commands.Command;
import logic.commands.CommandLib;
import logic.parser.CommonParser;
import ui.CommonUi;

import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Logger;

import static logic.parser.CommonParser.COMMAND_ARGS_INDEX;
import static logic.parser.CommonParser.COMMAND_TYPE_INDEX;
import static seedu.duke.Constants.PATH_TO_PROFILE_FILE;
import static seedu.duke.Constants.PATH_TO_PROFILE_FOLDER;
import static ui.CommonUi.LOGO;

/**
 * The Schwarzenegger program implements an application that keeps track of the user's gym and diet record.
 */
public class Duke {
    private static Logger logger;
    private CommandLib cl;
    private CommonUi ui;
    private CommonParser parser;

    /**
     * Constructs Duke object.
     */
    private Duke() {
        logger = SchwarzeneggerLogger.getInstanceLogger();
        cl = new CommandLib();
        cl.initMainMenuCl();
        ui = new CommonUi();
        parser = new CommonParser();
    }

    /**
     * Main entry-point for the java.seedu.duke.Duke application.
     *
     * @param args Unused in Schwarzenegger.
     */
    public static void main(String[] args) {
        new Duke().run();
    }

    /**
     * Runs Schwarzenegger.
     */
    private void run() {
        start();
    }

    /**
     * Starts up Duke with greeting message.
     */
    private void start() {

        try {
            ObjectMapper mapper = new ObjectMapper();
            Geocoder geocoder = new Geocoder();

            GeocodeResult response = geocoder.geocodeSync("nus");
            for (int i = 0; i < response.getResults().size(); i++) {
                for (int j = 0; j < response.getResults().get(i).getAddressComponents().size(); j++) {
                    System.out.println(response.getResults().get(i).getAddressComponents().get(j).getLongName());
                }
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        /**
     * Gets user's command and executes repeatedly until user requests to end Schwarzenegger.
     */
    private void runCommandLoopTillEnd() {
        logger.info("running main menu loop");

        while (true) {
            String userInput = ui.getCommand("Main Menu");
            String[] commParts = parser.parseCommand(userInput);
            Command cm = cl.getCommand(commParts[COMMAND_TYPE_INDEX]);

            try {
                cm.execute(commParts[COMMAND_ARGS_INDEX]);
            } catch (SchwarzeneggerException e) {
                if (e instanceof EndException) {
                    break;
                }

                ui.showToUser(ExceptionHandler.handleCheckedExceptions(e));
            } catch (Exception e) {
                ui.showToUser(ExceptionHandler.handleUncheckedExceptions(e));
            }
        }
    }

}
