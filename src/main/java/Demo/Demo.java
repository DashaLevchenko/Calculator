package Demo;

import Model.Calculator;
import Model.Exceptions.DivideZeroException;
import Model.Exceptions.InvalidInputException;
import Model.Exceptions.ResultUndefinedException;
import Model.OperationsEnum;

/**
 * This class demonstrations calculator model.
 */
public class Demo {

    public static void main (String[] args) {
        System.out.print("Result: ");
        try {
            System.out.println(Calculator.calculator(5, OperationsEnum.DIVIDE, 0, OperationsEnum.DIVIDE, 2, OperationsEnum.SUBTRACT, 1,
                    OperationsEnum.EQUAL, OperationsEnum.SQRT, OperationsEnum.ADD, 4, OperationsEnum.EQUAL));

        } catch (Exception e) {
            String messageError;
            if (e instanceof DivideZeroException) {
                messageError = "Cannot divide by zero";
            } else if (e instanceof ResultUndefinedException) {
                messageError = "Result is undefined";
            } else if (e instanceof InvalidInputException) {
                messageError = "Invalid input";
            } else {
                messageError = "Something wrong!";
                e.printStackTrace();
            }
            System.out.println(messageError);
        }
    }
}