package Demo;

import Model.Calculator;
import Model.Exceptions.DivideZeroException;
import Model.Exceptions.InvalidInputException;
import Model.Exceptions.OperationException;
import Model.Exceptions.ResultUndefinedException;
import Model.OperationsEnum;

import java.math.BigDecimal;
import java.util.HashMap;

public class Demo {
    private static Calculator calculator = new Calculator();
    private static HashMap<String, OperationsEnum> operations = new HashMap<>();


    static {
        operations.put("+", OperationsEnum.ADD);
        operations.put("-", OperationsEnum.SUBTRACT);
        operations.put("/", OperationsEnum.DIVIDE);
        operations.put("x", OperationsEnum.MULTIPLY);

        operations.put("√", OperationsEnum.SQRT);
        operations.put("", OperationsEnum.SQR);
        operations.put("", OperationsEnum.ONE_DIVIDE_X);
        operations.put("", OperationsEnum.PERCENT);

        operations.put("", OperationsEnum.NEGATE);
    }

    public static void main (String[] args) {
        //"√((5+3)/2-1)+4"
        try {
            calculator.setNumberFirst(BigDecimal.valueOf(5));
            calculate("+", BigDecimal.valueOf(3));
            calculate("/", BigDecimal.valueOf(2));
            calculate("-", BigDecimal.valueOf(1));
            calculate("√", null);
            calculate("+", BigDecimal.valueOf(4));

            BigDecimal result = calculator.getResult();


            System.out.println("Ответ: " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void calculate (String operationSymbol, BigDecimal secondNumber) throws DivideZeroException, InvalidInputException, ResultUndefinedException, OperationException {
        if (operations.containsKey(operationSymbol)) {
            OperationsEnum operation = operations.get(operationSymbol);
            calculator.setOperation(operation);
            calculator.setNumberSecond(secondNumber);

            calculator.calculate();
        }

    }
}
