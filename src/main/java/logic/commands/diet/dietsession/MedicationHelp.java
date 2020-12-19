package logic.commands.diet.dietsession;

import Location.Medication;
import logic.commands.Command;
import logic.commands.CommandResult;
import storage.Storage;
import ui.diet.dietsession.MedicationSessionUi;

import java.util.ArrayList;
import java.util.logging.Level;

public class MedicationHelp extends Command {

    public CommandResult execute(String input, ArrayList<Medication> foodList, Storage storage, Integer index) {
        StringBuilder helpMessage = new StringBuilder();
        MedicationSessionUi.printHelp(helpMessage);
        logger.log(Level.INFO, "Displayed help in dietSession");
        return new CommandResult(helpMessage.toString().trim());
    }
}
