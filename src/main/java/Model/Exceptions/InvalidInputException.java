package Model.Exceptions;

public class InvalidInputException extends Exception {

    /**
     * Constructs an {@code InvalidInputException} with the specified  detail message.
     * @param message Detail about exception
     */
    public InvalidInputException(String message){

        super(message);
    }

}
