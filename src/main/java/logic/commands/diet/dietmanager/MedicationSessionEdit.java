package logic.commands.diet.dietmanager;

import Medication.Medicationmanager.Medication;
import classes.LocationInfo;
import logic.commands.Command;
import logic.commands.CommandResult;
import storage.Storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;

import static seedu.duke.Constants.PATH_TO_DIET_FOLDER;
import static ui.diet.dietmanager.MedicationManagerUi.DIET_EDIT_WRONG_FORMAT;
import static ui.diet.dietmanager.MedicationManagerUi.DIET_FILE_ARRAY_OUT_OF_BOUND;
import static ui.diet.dietmanager.MedicationManagerUi.DIET_FILE_CORRUPTED_MSG;
import static ui.diet.dietmanager.MedicationManagerUi.DIET_NEW_SUCCESS;
import static ui.diet.dietmanager.MedicationManagerUi.EMPTY_STRING;

public class MedicationSessionEdit extends Command {

    @Override
    public CommandResult execute(String input, Storage storage) {
        String result = EMPTY_STRING;
        File folder = new File(PATH_TO_DIET_FOLDER);
        File[] listOfFiles = folder.listFiles();
        try {
            LocationInfo location = null;
            assert listOfFiles != null : "List of files should not be null";
            location = storage.readLocation(PATH_TO_DIET_FOLDER, listOfFiles[Integer.parseInt(input) - 1].getName());
            location.start(listOfFiles[Integer.parseInt(input) - 1].getName());
            logger.log(Level.INFO, "Diet session in edit mode");
            result = DIET_NEW_SUCCESS;
        } catch (FileNotFoundException
                | NumberFormatException e) {
            result = DIET_EDIT_WRONG_FORMAT;
            logger.log(Level.WARNING, "No file found at array index");
        } catch (IOException e) {
            logger.log(Level.WARNING, "failed to execute diet session");
        } catch (IndexOutOfBoundsException e) {
            result = DIET_FILE_ARRAY_OUT_OF_BOUND;
        } catch (NullPointerException e) {
            logger.log(Level.WARNING, "File might be corrupted");
            result = DIET_FILE_CORRUPTED_MSG;
        }
        return new CommandResult(result);
    }
}
