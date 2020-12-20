package logic.commands.diet.dietsession;

import Location.Medication;
import logic.commands.Command;
import logic.commands.CommandResult;
import logic.commands.ExecutionResult;
import storage.Storage;
import ui.diet.dietsession.MedicationSessionUi;

import java.util.ArrayList;
import java.util.logging.Level;

public class MedicationClear extends Command {
    public CommandResult execute(String dummyInput, ArrayList<Medication> medicationList, Storage storage)  {

        String prompt = MedicationSessionUi.DIET_INPUT_PROMPT_EDIT;
        if (ui.checkConfirmation(prompt, "clear all records")) {
            medicationList.clear();
            String result = MedicationSessionUi.MESSAGE_CLEAR_SUCCESS;
            logger.log(Level.INFO, "Cleared all food in arraylist");
            return new CommandResult(result, ExecutionResult.OK);
        } else {
            String result = MedicationSessionUi.MESSAGE_CLEAR_ABORTED;
            return new CommandResult(result, ExecutionResult.ABORTED);
        }
    }
}
