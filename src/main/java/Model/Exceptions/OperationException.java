package Model.Exceptions;

public class OperationException extends Exception {

    /**
     * Constructs an {@code OperationException} with the specified  detail message.
     * @param message Detail about exception
     */
    public OperationException(String message){
        super(message);
    }
}
