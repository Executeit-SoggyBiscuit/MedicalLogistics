package logic.commands.medication.medicationmanager;

import logic.commands.Command;
import logic.commands.CommandResult;
import storage.Storage;

import java.util.logging.Level;

import static ui.CommonUi.helpFormatter;

//@@author CFZeon
/**
 * A representation of the command for help commands in medication manager.
 */
public class MedicationSessionHelp extends Command {
    /**
     * Overrides execute for help command to display help information.
     *
     * @param input   user input for command
     * @param storage storage for medication manager
     * @return CommandResult with help message
     */
    @Override
    public CommandResult execute(String input, Storage storage) {
        StringBuilder message = new StringBuilder();
        message.append(helpFormatter("New", "new </d [DATE]> </t [TAG]>",
                "Create a new medication session"));
        message.append(helpFormatter("List", "list",
                "Show all past medication sessions"));
        message.append(helpFormatter("Delete", "delete [INDEX]",
                "Delete the medication session at the input index"));
        message.append(helpFormatter("Edit", "edit [INDEX]",
                "Edit the medication session at the input index"));
        message.append(helpFormatter("Search",
                "search </s [STARTING_DATE]> </e [END_DATE]> </t [TAG]>",
                "Search the medication session in between starting and end dates with a specific tag"));
        message.append(helpFormatter("Clear", "clear",
                "Clear all past medication sessions"));
        message.append(helpFormatter("End", "end",
                "Go back to Main Menu"));
        logger.log(Level.INFO, "Displayed help in medicationManager");
        return new CommandResult(message.toString().trim());
    }
}
