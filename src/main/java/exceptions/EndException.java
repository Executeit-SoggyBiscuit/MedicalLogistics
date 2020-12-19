package exceptions;


/**
 * Represents exception when user wants to return to Main Menu.
 */
public class EndException extends Exception {

    private static final String MESSAGE_END = "End error";

    /**
     * Constructs EndException object inheriting abstract class SchwarzeneggerException.
     */
    public EndException() {
        super(MESSAGE_END);
    }
}
