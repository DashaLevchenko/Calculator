package Model.Exceptions;

public class ResultUndefinedException extends Exception {
    /**
     * Constructs an {@code ResultUndefinedException} with the specified  detail message.
     * @param message Detail about exception
     */
    public ResultUndefinedException(String message){
        super(message);
    }
    /**
     * Constructs an {@code ResultUndefinedException} without the specified  detail message.
     */
    public ResultUndefinedException(){
        super();
    }

}
