package exceptions.diet;


import exceptions.SchwarzeneggerException;

/**
 * Represents exception when calories is negative.
 */
public class NegativeCaloriesException extends SchwarzeneggerException {

    /**
     * Constructs NegativeCaloriesException object inheriting abstract class SchwarzeneggerException.
     */
    public NegativeCaloriesException() {
        super("MESSAGE_NEGATIVE_CALORIES");
    }
}
