package exceptions.diet;

import exceptions.SchwarzeneggerException;


/**
 * Represents exception when no food name is entered.
 */
public class NoNameException extends SchwarzeneggerException {

    /**
     * Constructs SchwarzeneggerException object inheriting class Exception.
     */
    public NoNameException() {
        super("MESSAGE_NO_FOOD_NAME");
    }
}
