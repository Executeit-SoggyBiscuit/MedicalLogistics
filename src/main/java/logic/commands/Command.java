package logic.commands;

import exceptions.InvalidCommandWordException;
import exceptions.InvalidDateFormatException;
import exceptions.SchwarzeneggerException;
import exceptions.diet.InvalidSearchDateException;
import exceptions.profile.InvalidCommandFormatException;
import logger.SchwarzeneggerLogger;
import models.Food;
import storage.Storage;
import ui.CommonUi;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A base class for command.
 */
public abstract class Command {
    protected static Logger logger = SchwarzeneggerLogger.getInstanceLogger();
    public CommonUi ui;

    public Command() {
        ui = new CommonUi();
    }
    public CommandResult execute(String args) throws SchwarzeneggerException {
        logger.log(Level.INFO, "Executing " + this);
        return new CommandResult();
    }
    public CommandResult execute(String input, Storage storage) throws InvalidCommandWordException,
            InvalidCommandFormatException, InvalidDateFormatException, InvalidSearchDateException {
        return new CommandResult();
    }
    public CommandResult execute(String input, ArrayList<Food> foodList,
                                 Storage storage, Integer index) throws InvalidCommandWordException {
        return new CommandResult();
    }
}
