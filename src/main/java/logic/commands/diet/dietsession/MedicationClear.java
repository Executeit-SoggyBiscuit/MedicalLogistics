package logic.commands.diet.dietsession;

import logic.commands.Command;
import models.Food;
import logic.commands.CommandResult;
import logic.commands.ExecutionResult;
import storage.Storage;
import ui.diet.dietsession.DietSessionUi;

import java.util.ArrayList;
import java.util.logging.Level;

public class MedicationClear extends Command {
    @Override
    public CommandResult execute(String input, ArrayList<Food> foodList, Storage storage, Integer index) {
        String result = "";
        String prompt;
        if (index <= 0) {
            prompt = DietSessionUi.DIET_INPUT_PROMPT_NEW;
        } else {
            prompt = DietSessionUi.DIET_INPUT_PROMPT_EDIT + index;
        }
        if (ui.checkConfirmation(prompt, "clear all records")) {
            foodList.clear();
            result = DietSessionUi.MESSAGE_CLEAR_SUCCESS;
            logger.log(Level.INFO, "Cleared all food in arraylist");
            return new CommandResult(result, ExecutionResult.OK);
        } else {
            result = DietSessionUi.MESSAGE_CLEAR_ABORTED;
            return new CommandResult(result, ExecutionResult.ABORTED);
        }
    }
}