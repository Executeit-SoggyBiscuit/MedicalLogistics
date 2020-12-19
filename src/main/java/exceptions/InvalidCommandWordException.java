package exceptions;


//@@author tienkhoa16
/**
 * Represents exception when command word is invalid.
 */
public class InvalidCommandWordException extends SchwarzeneggerException {

    /**
     * Constructs InvalidCommandWordException object inheriting abstract class SchwarzeneggerException.
     */
    public InvalidCommandWordException() {
        super("MESSAGE_INVALID_COMMAND_WORD");
    }
}
