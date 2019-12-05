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

            calculate("5");
            calculate("+");
            calculate("3");
            calculate("/");
            calculate("2");
            calculate("-");
            calculate("1");
            calculate("√");
            calculate("+");
            calculate("4");

            System.out.println("Ответ: " + result());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static BigDecimal result () {
        BigDecimal result = null;
        if (calculator.getResult() != null){
            result = calculator.getResult();
        }
        return result;
    }

    private static void calculate (String text) throws ResultUndefinedException, DivideZeroException, InvalidInputException, OperationException {
        for (int i = 0; i < text.length(); i++) {



        }

        boolean isNumber = isNumber(text);
        boolean isOperation = isOperation(text);

        if(isNumber){
            setNumber(text);
        }

        if (isOperation){
            setOperation(text);
        }

    }

    private static boolean isOperation (String text) {
        boolean isOperation = false;
        if (operations.containsKey(text)) {
            isOperation = true;
        }
        return isOperation;
    }

    private static boolean isNumber (String text) {
        boolean isNumber;
        try {
            new BigDecimal(text);
            isNumber = true;
        } catch (Exception e) {
            isNumber = false;
        }
        return isNumber;
    }

    private static void setOperation (String operationString) throws DivideZeroException, InvalidInputException, ResultUndefinedException, OperationException {
        OperationsEnum operation = operations.get(operationString);
        calculator.setOperation(operation);
        boolean isUnary = calculator.isUnary(operation);

        if (isUnary) {
            calculator.setNumberSecond(null);
            calculator.calculate();
        }
    }

    private static void setNumber (String numberString) throws DivideZeroException, InvalidInputException, ResultUndefinedException, OperationException {
        BigDecimal number = new BigDecimal(numberString);

        if (calculator.getNumberFirst() == null) {
            calculator.setNumberFirst(number);
        } else {
            calculator.setNumberSecond(number);

            if (calculator.getOperation() != null) {
                OperationsEnum operation = calculator.getOperation();
                boolean isBinary = calculator.isBinary(operation);
                if (isBinary) {
                    calculator.calculate();
                }
            }
        }
    }

}
