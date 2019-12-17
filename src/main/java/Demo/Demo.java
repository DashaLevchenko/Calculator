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

        } catch (DivideZeroException | ResultUndefinedException | InvalidInputException e) {
            System.out.println(e.getMessage());
        } catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
