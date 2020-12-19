package logic.commands.diet.dietsession;

import Location.Medication;
import logic.commands.Command;
import logic.commands.CommandResult;
import logic.commands.ExecutionResult;
import storage.Storage;
import ui.diet.dietsession.MedicationSessionUi;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.stream.Collectors;

import static ui.CommonUi.LS;


public class MedicationList extends Command {

    public CommandResult execute(String input, ArrayList<Medication> foodList, Storage storage, Integer index) {
        String result = "";
        try {
            double totalCalories = 0;
            StringBuilder listResult = new StringBuilder();
            if (foodList.size() > 0) {
                String totalMealCalories = "\n" + LS + "Your total calories for this meal is " + totalCalories + ".";
                String formattedList = formatList(foodList);
                listResult.append(formattedList);
                listResult.append(totalMealCalories);
                result = listResult.toString().trim();
                logger.log(Level.INFO, "Listed all foods in Diet Session");
            } else {
                listResult.append(MedicationSessionUi.MESSAGE_NO_FOOD);
                result = listResult.toString().trim();
            }
        } catch (NullPointerException e) {
            result = MedicationSessionUi.MESSAGE_NO_FOOD;
            logger.log(Level.WARNING, "No item in food list");
        }
        return new CommandResult(result, ExecutionResult.OK);
    }

    private String formatList(ArrayList<Medication> foodList) {

        ArrayList<String> foodNames = (ArrayList<String>) foodList.stream()
                .map(Medication::getName).collect(Collectors.toList());
        int descriptionMaxLenInt = Math.max(10,
                foodNames.stream().max(Comparator.comparingInt(String::length)).get().length());

        String descriptionFormat = "%-" + String.format("%d", descriptionMaxLenInt + 1) + "s";

        String returnString = String.format("%-8s", "Index") + String.format(descriptionFormat, "Food")
                + String.format("%-9s", "Calories") + LS;

        StringBuilder infoBuilder = new StringBuilder(returnString);

        String listDescriptionFormat = "%-" + String.format("%d", descriptionMaxLenInt) + "s %-9s ";
        for (int i = 0; i < foodList.size(); i++) {
            String rowContent = String.format(listDescriptionFormat, foodList.get(i).getName());
            String row = String.format("%-8s", i + 1) + rowContent + LS;
            infoBuilder.append(row);
        }
        returnString = infoBuilder.toString().trim();
        return returnString;
    }
}
