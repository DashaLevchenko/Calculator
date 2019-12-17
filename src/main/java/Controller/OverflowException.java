package Controller;

/**
 * This class {@code OverflowException} a from of {@code Exception}.
 * The class is checked exception. The object of this class is created and thrown,
 * when some number is bigger then maximum valid number.
 */
public class OverflowException extends Exception {
    /**
     *  Constructs an {@code OverflowException} with the specified  detail message.
     * @param message Detail about exception
     */
    public OverflowException(String message){
        super(message);
    }

    public OverflowException(){
        super();
    }
}
