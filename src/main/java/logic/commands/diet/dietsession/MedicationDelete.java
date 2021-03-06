package logic.commands.diet.dietsession;

import Location.Medication;
import logic.commands.Command;
import logic.commands.CommandResult;
import logic.commands.ExecutionResult;
import storage.Storage;
import ui.diet.dietsession.MedicationSessionUi;

import java.util.ArrayList;
import java.util.logging.Level;
public class MedicationDelete extends Command {

    public CommandResult execute(String input, ArrayList<Medication> foodList, Storage storage, Integer index) {
        String result = "";
        try {
            assert !input.isEmpty();
            int indexOfSession = Integer.parseInt(input);
            Medication temp = foodList.get(indexOfSession - 1);
            result = "You have deleted " + temp.toString() + " from your list!";
            foodList.remove(temp);
            logger.log(Level.INFO, "Removed food from arraylist");
        } catch (IndexOutOfBoundsException e) {
            result = MedicationSessionUi.MESSAGE_NO_SUCH_INDEX;
            logger.log(Level.WARNING, "Did not input index");
        } catch (NumberFormatException e) {
            result = MedicationSessionUi.MESSAGE_DELETE_WRONG_FORMAT;
            logger.log(Level.WARNING, "Did not input correct index");
        }
        return new CommandResult(result, ExecutionResult.OK);
    }
}
