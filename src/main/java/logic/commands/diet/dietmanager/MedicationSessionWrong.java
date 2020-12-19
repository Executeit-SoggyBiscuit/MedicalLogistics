package logic.commands.diet.dietmanager;

import exceptions.InvalidCommandWordException;
import logic.commands.Command;
import logic.commands.CommandResult;
import storage.Storage;

public class MedicationSessionWrong extends Command {

    @Override
    public CommandResult execute(String input, Storage storage) throws InvalidCommandWordException {
        throw new InvalidCommandWordException();
    }
}
