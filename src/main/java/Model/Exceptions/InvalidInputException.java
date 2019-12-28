package Model.Exceptions;

/**
 * This class {@code InvalidInputException} a from of {@code Exception}.
 * The class is checked exception. The object of this class is created and thrown,
 * when tried square root negative number.
 */
public class InvalidInputException extends Exception {

    /**
     * Constructs an {@code InvalidInputException}.
     * This exception throws when square root negative number.
     */
    public InvalidInputException () {
        super();
    }

}
