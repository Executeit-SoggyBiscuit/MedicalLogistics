package logic.commands.diet.dietsession;

import Medication.Medicationmanager.Medication;
import logic.commands.Command;
import logic.commands.CommandResult;
import logic.commands.ExecutionResult;
import storage.Storage;
import ui.diet.dietsession.MedicationSessionUi;

import java.util.ArrayList;
import java.util.logging.Level;

public class MedicationClear extends Command {
    public CommandResult execute(String input, ArrayList<Medication> foodList, Storage storage, Integer index) {
        String result = "";
        String prompt;
        if (index <= 0) {
            prompt = MedicationSessionUi.DIET_INPUT_PROMPT_NEW;
        } else {
            prompt = MedicationSessionUi.DIET_INPUT_PROMPT_EDIT + index;
        }
        if (ui.checkConfirmation(prompt, "clear all records")) {
            foodList.clear();
            result = MedicationSessionUi.MESSAGE_CLEAR_SUCCESS;
            logger.log(Level.INFO, "Cleared all food in arraylist");
            return new CommandResult(result, ExecutionResult.OK);
        } else {
            result = MedicationSessionUi.MESSAGE_CLEAR_ABORTED;
            return new CommandResult(result, ExecutionResult.ABORTED);
        }
    }
}
