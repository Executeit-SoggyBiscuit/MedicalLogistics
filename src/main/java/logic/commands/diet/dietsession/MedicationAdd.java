package logic.commands.diet.dietsession;

import Location.Medication;
import classes.ArrayListMedication;
import classes.LocationInfo;
import logic.commands.Command;
import logic.parser.DietSessionParser;
import logic.commands.CommandResult;
import storage.Storage;

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
    public CommandResult execute(String dummyInput, ArrayList<Medication> medicationList, Storage storage) {
        DietSessionParser parser = new DietSessionParser();
        System.out.println("Here is the list of medication at this location: ");
        showMedList(medicationList);
        String result = "";
        StringBuilder userOutput = new StringBuilder();
        Scanner userInput = new Scanner(System.in);

        System.out.println("Please indicate the index of medication: ");
        String indexInput  = userInput.nextLine();
        int medicationIndex = Integer.parseInt(indexInput) - 1;

        int quantity = 0;
        if (medicationIndex < 0) {
            quantity = addMedication(userInput, medicationList);
        } else {
            System.out.println("Please indicate whether you want to add or delete: ");
            String addOrDeleteInput = userInput.nextLine();
            int oldQuantity = medicationList.get(medicationIndex).getQuantity();
            if (addOrDeleteInput.equals("add")) {
                quantity = addQuantity(userInput);
                medicationList.get(medicationIndex).setQuantity(oldQuantity + quantity);
                System.out.println("\n" + "You have added " + quantity + " "
                        + medicationList.get(medicationIndex).getName() + "(s)");
            } else if (addOrDeleteInput.equals("delete")) {
                quantity = deleteQuantity(userInput);
                medicationList.get(medicationIndex).setQuantity(oldQuantity - quantity);
                System.out.println("\n" + "You have deleted " + (Math.min(oldQuantity, quantity)) + " " 
                        + medicationList.get(medicationIndex).getName() + "(s)");
                if (medicationList.get(medicationIndex).getQuantity() <= 0) {
                    System.out.println("There is no more " + medicationList.get(medicationIndex).getName() + " left.");
                    medicationList.remove(medicationIndex);
                }
            }
        }


        result = userOutput.toString();
        return new CommandResult(result);
    }

    private void showMedList(ArrayList<Medication> medicationList) {
        for (Medication med : medicationList) {
            System.out.println(med.getName() + " " + med.getQuantity());
        }
    }

    private int addMedication(Scanner in, ArrayList<Medication> medList) {
        int index = 0;
        String name;

        do {
            System.out.println("Please enter the name of the medication currently not in stock: ");
            name = in.nextLine();
        } while (checkExistence(name, ArrayListMedication.getInstance().getArray()));
        System.out.println("You have added " + name + " to the list");
        int quantity = addQuantity(in);
        Medication temp = new Medication(name, quantity);
        medList.add(temp);
        ArrayListMedication.getInstance().getArray().add(temp);
        logger.log(Level.INFO, "Added medication to arraylist");

        return quantity;
    }


    public boolean checkExistence(String inputName, ArrayList<Medication> medicationList) {
        boolean isExist = false;

        for (int i = 0; i < medicationList.size(); i++) {
            if (medicationList.get(i).getName().equals(inputName)) {
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
        while (number <= 0) {
            System.out.print("Quantity to add: ");
            input = in.nextLine();
            number = convertStringToNumber(input);
            System.lineSeparator();
        }
        return number;
    }

    private int deleteQuantity(Scanner in) {
        String input;
        int number = 0;
        while (number <= 0) {
            System.out.print("Quantity to delete: ");
            input = in.nextLine();
            number = convertStringToNumber(input);
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
