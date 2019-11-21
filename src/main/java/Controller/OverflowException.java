package Controller;

public class OverflowException extends Exception {
    /**
     *  Constructs an {@code OverflowException} with the specified  detail message.
     * @param message Detail about exception
     */
    public OverflowException(String message){
        super(message);
    }
}
