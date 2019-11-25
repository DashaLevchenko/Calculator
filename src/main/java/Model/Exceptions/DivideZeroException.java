package Model.Exceptions;

public class DivideZeroException extends Exception {

    /**
     * Constructs an {@code DivideZeroException} with the specified  detail message.
     * @param message Detail about exception
     */
    public DivideZeroException(String message){
        super(message);
    }
}
