package logic.commands.diet.dietsession;

import exceptions.InvalidCommandWordException;
import logic.commands.Command;
import logic.commands.CommandResult;
import models.Food;
import storage.Storage;

import java.util.ArrayList;

public class MedicationWrong extends Command {
    @Override
    public CommandResult execute(String input, ArrayList<Food> foodList,
                                 Storage storage, Integer index) throws InvalidCommandWordException {
        throw new InvalidCommandWordException();
    }
}
