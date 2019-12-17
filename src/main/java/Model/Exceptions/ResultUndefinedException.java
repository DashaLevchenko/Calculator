package Model.Exceptions;


/**
 * This class {@code ResultUndefinedException} a from of {@code Exception}.
 * The class is checked exception. The object of this class is created and thrown,
 * when tried divide zero by zero.
 */
public class ResultUndefinedException extends Exception {
    /**
     * Constructs an {@code ResultUndefinedException} with the specified  detail message.
     * This exception throws when divide zero by zero.
     * @param message Detail about exception
     */
    public ResultUndefinedException(String message){
        super(message);
    }

    public ResultUndefinedException(){
        super();
    }

}
