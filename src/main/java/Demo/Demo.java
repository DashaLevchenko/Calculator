package Demo;

import Model.Calculator;
import Model.OperationsEnum;

import java.math.BigDecimal;
import java.util.HashMap;


/**
 * This class demonstrations calculator model.
 */
public class Demo {
    private static HashMap<String, OperationsEnum> operations = new HashMap<>();

    static {
        operations.put("+", OperationsEnum.ADD);
        operations.put("-", OperationsEnum.SUBTRACT);
        operations.put("/", OperationsEnum.DIVIDE);
        operations.put("x", OperationsEnum.MULTIPLY);

        operations.put("√", OperationsEnum.SQRT);
        operations.put("", OperationsEnum.SQR);
        operations.put("", OperationsEnum.ONE_DIVIDE_X);
        operations.put("%", OperationsEnum.PERCENT);

        operations.put("", OperationsEnum.NEGATE);
        operations.put("=", OperationsEnum.EQUAL);
    }

    public static void main (String[] args) {

        try {
            BigDecimal result = Calculator.calculator(5, OperationsEnum.ADD, 3, OperationsEnum.DIVIDE, 2, OperationsEnum.SUBTRACT,
                    1, OperationsEnum.EQUAL, OperationsEnum.SQRT, OperationsEnum.ADD, 4, OperationsEnum.EQUAL);


            System.out.println("Result: " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
