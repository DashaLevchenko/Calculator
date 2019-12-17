package Model.Exceptions;

/**
 * This class {@code DivideZeroException} a from of {@code Exception}.
 * The class is checked exception. The object of this class is created and thrown,
 * when tried divide number by zero.
 */
public class DivideZeroException extends Exception {

    /**
     * Constructs an {@code DivideZeroException} with the specified detail message.
     * This exception throws when number is divided by zero.
     * @param message Detail about exception
     */
    public DivideZeroException(String message){
        super(message);
    }

    public DivideZeroException(){
        super();
    }
}
