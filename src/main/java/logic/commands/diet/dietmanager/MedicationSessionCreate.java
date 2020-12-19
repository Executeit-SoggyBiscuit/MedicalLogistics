package logic.commands.diet.dietmanager;


import Medication.Medicationmanager.Medication;
import logic.parser.MedicationManagerParser;
import diet.dietsession.DietSession;
import exceptions.InvalidDateFormatException;
import exceptions.profile.InvalidCommandFormatException;
import logic.commands.Command;
import logic.commands.CommandResult;
import storage.Storage;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;

import static ui.diet.dietmanager.MedicationManagerUi.DIET_CREATE_WRONG_FORMAT;
import static ui.diet.dietmanager.MedicationManagerUi.DIET_DATE_WRONG_FORMAT;
import static ui.diet.dietmanager.MedicationManagerUi.DIET_IO_WRONG_FORMAT;
import static ui.diet.dietmanager.MedicationManagerUi.DIET_NEW_SUCCESS;
import static ui.diet.dietmanager.MedicationManagerUi.EMPTY_STRING;

public class MedicationSessionCreate extends Command {

    private final MedicationManagerParser parser = new MedicationManagerParser();

    /**
     * Overrides execute for create command to create new diet sessions.
     *
     * @param input   user input for command
     * @param storage storage for diet manager
     * @return CommandResult with ended diet session message
     */
    @Override
    public CommandResult execute(String input, Storage storage) {
        String result = EMPTY_STRING;
        try {
            StringBuilder message = new StringBuilder();
            HashMap<String, String> parsedParams = parser.extractDietManagerCommandTagAndInfo("new", input);
            // extract the date and tags and assigns it to the string
            String date = parser.extractNewDate(parsedParams, message);
            String tag = parser.extractNewTag(parsedParams, message);
            if (message.length() != 0) {
                ui.showToUser(message.toString().trim());
            }
            Medication med = new Medication(name, quantity);
            med.start(true, -1);
            result = DIET_NEW_SUCCESS;
        } catch (IOException e) {
            result = DIET_IO_WRONG_FORMAT;
        } catch (InvalidDateFormatException e) {
            logger.log(Level.WARNING, "Wrong date format");
            result = DIET_DATE_WRONG_FORMAT;
        } catch (InvalidCommandFormatException e) {
            logger.log(Level.WARNING, "Invalid command in dietSessionCreate");
            result = DIET_CREATE_WRONG_FORMAT;
        }
        return new CommandResult(result);
    }
}
