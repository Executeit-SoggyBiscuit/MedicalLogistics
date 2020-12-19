package logic.commands.diet.dietsession;

import logic.commands.Command;
import models.Food;
import logic.commands.CommandResult;
import storage.Storage;
import ui.diet.dietsession.MedicationSessionUi;

import java.util.ArrayList;
import java.util.logging.Level;

public class MedicationHelp extends Command {

    @Override
    public CommandResult execute(String input, ArrayList<Food> foodList, Storage storage, Integer index) {
        StringBuilder helpMessage = new StringBuilder();
        MedicationSessionUi.printHelp(helpMessage);
        logger.log(Level.INFO, "Displayed help in dietSession");
        return new CommandResult(helpMessage.toString().trim());
    }
}
