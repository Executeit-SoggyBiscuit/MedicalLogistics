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
import java.util.Scanner;
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
    /*
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

     */


    public CommandResult execute (String input, ArrayList<Medication> MedicationList, Storage storage, Integer index) {
        DietSessionParser parser = new DietSessionParser();
        String result = "";
            assert !input.isEmpty();
            StringBuilder userOutput = new StringBuilder();
            Scanner userInput = new Scanner(System.in);
            int MedicationIndex = Integer.parseInt(input);

            if( MedicationIndex == 0) {
                addMedication(userInput, MedicationList);
            }

            //ask quantity
            addQuantity(userInput);

            int quantity = addQuantity(userInput);
            System.out.println("\n" + "You have added " + Integer.toString(quantity));


            result = userOutput.toString();
        return new CommandResult(result);
    }

    private int addMedication(Scanner in, ArrayList<Medication> MedicationList) {
        int index = 0;
        String name;

        do {
            name = in.nextLine();
        } while(checkExistence(name, MedicationList));

        Medication temp = new Medication(name, 0);
        MedicationList.add(temp);
        System.out.println("You have added " + name + " to the list");
        logger.log(Level.INFO, "Added medication to arraylist");

        return index;
    }

    public boolean checkExistence(String inputName, ArrayList<Medication> MedicationList) {
        boolean isExist = false;

        for(int i = 0; i < MedicationList.size(); i++) {
            if(MedicationList.get(i).getName().equals(inputName)) {
                isExist = true;
                System.out.println("The medication is already in the list.");
                break;
            }
        }
        return isExist;
    }

    private int addQuantity(Scanner in) {
        String input;
        int number = 0;
        while(number <= 0) {
            System.out.print("Quantity to add: ");
            input = in.nextLine();
            number =  convertStringToNumber(input);
            System.lineSeparator();
        }

        return number;
    }

    private int convertStringToNumber(String input) {
        int index = 0;
        try {
            index = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("error: invalid number");
        }

        return index;
    }
}
