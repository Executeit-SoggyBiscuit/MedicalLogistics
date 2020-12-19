package logic.commands.diet.dietsession;

import Medication.Medicationmanager.Medication;
import logic.commands.Command;
import logic.parser.DietSessionParser;
import exceptions.diet.NegativeCaloriesException;
import exceptions.diet.NoNameException;
import logic.commands.CommandResult;
import storage.Storage;
import ui.diet.dietsession.MedicationSessionUi;

import java.util.ArrayList;
import java.util.logging.Level;


//@@author zsk612
/**
 * A representation of the command for add commands in diet session.
 */
public class MedicationAdd extends Command {

    /**
     * Overrides execute for add command to add food items.
     *
     * @param input user input for command
     * @param foodList arraylist that stored all the food items
     * @param storage storage for diet session
     * @param index Integer variable that shows the index of the session
     * @return An object CommandResult containing the executing status and feedback message to be displayed
     *         to user.
     */
    public CommandResult execute(String input, ArrayList<Medication> foodList, Storage storage, Integer index) {
        DietSessionParser parser = new DietSessionParser();
        String result = "";
        try {
            assert !input.isEmpty();
            StringBuilder userOutput = new StringBuilder();
            Double calories = parser.processFoodCalories(input);
            Medication temp = new Medication(parser.processFoodName(input), (int) parser.processFoodCalories(input));
            foodList.add(temp);
            userOutput.append("Yay! You have added " + temp.toString());
            result = userOutput.toString();
            logger.log(Level.INFO, "Added food to arraylist");
        } catch (IndexOutOfBoundsException e) {
            result = MedicationSessionUi.MESSAGE_ADD_WRONG_FORMAT;
            logger.log(Level.WARNING, "Wrong Add food item format");
        } catch (NumberFormatException e) {
            result = MedicationSessionUi.MESSAGE_WRONG_CALORIES;
            logger.log(Level.WARNING, "Put calories in a wrong format");
        } catch (NegativeCaloriesException e) {
            result = MedicationSessionUi.MESSAGE_NEGATIVE_CALORIES;
            logger.log(Level.WARNING, "Put negative calories");
        } catch (NoNameException e) {
            result = MedicationSessionUi.MESSAGE_NO_FOOD_NAME;
            logger.log(Level.WARNING, "no food name");
        }
        return new CommandResult(result);
    }
}
