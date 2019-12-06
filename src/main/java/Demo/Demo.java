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
    private static Calculator calculator;
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
    }

    public static void main (String[] args) {
        //"√((5+3)/2-1)+4"
            String formula = "((5+3)/2-1)√+4";
        try {
            System.out.println("Ответ: " + calculate(formula));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static String numberString;

    /**
     * This method calculate formula and return result.
     *
     * @param formula Formula which need to calculate
     * @return Number was calculated by formula
     * @throws DivideZeroException      If divide by zero
     * @throws ResultUndefinedException If zero divide by zero
     * @throws OperationException       If operation not equals calculator operation
     * @throws InvalidInputException    If square root negative number
     */
    public static BigDecimal calculate (String formula) throws ResultUndefinedException, DivideZeroException, InvalidInputException, OperationException {
        calculator = new Calculator();
        numberString = "";

        for (int i = 0; i < formula.length(); i++) {
            char character = formula.toCharArray()[i];
            String symbol = String.valueOf(character);

            parseNumber(symbol, i, formula);
            parseOperation(symbol, i, formula);
        }

        return getResult();
    }

    private static BigDecimal getResult () throws ResultUndefinedException {
        BigDecimal result = calculator.getResult();
        if (result == null) {
            throw new ResultUndefinedException();
        }
        return result;
    }

    private static void parseOperation (String symbol, int index, String formula) throws ResultUndefinedException, DivideZeroException, InvalidInputException, OperationException {

        if (isOperation(symbol)) {
            setOperation(symbol, index, formula);
        }
    }

    private static boolean isOperation (String symbol) {
        return operations.containsKey(symbol);
    }


    private static void parseNumber (String symbol, int index, String formula) throws ResultUndefinedException, DivideZeroException, InvalidInputException, OperationException {
        if (isNumber(symbol)) {
            numberString = numberString.concat(symbol);
            boolean isNextSymbolPartNumber = isPartNumber(index, formula);


            if (!isNextSymbolPartNumber) {
                BigDecimal number = new BigDecimal(numberString);
                setNumber(number);
                numberString = "";
            }
        }
    }

    private static boolean isPartNumber (int index, String formula) {
        boolean isPartNumber = false;
        int nextIndex = index + 1;
        String decimalSeparator = ",";

        if (formula.length() > nextIndex) {
            String nextSymbol = String.valueOf(formula.charAt(nextIndex));
            if (isNumber(nextSymbol) || nextSymbol.equals(decimalSeparator)) {
                isPartNumber = true;
            }
        }

        return isPartNumber;
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

    private static void setOperation (String operationString, int index, String formula) throws DivideZeroException, InvalidInputException, ResultUndefinedException, OperationException {
        OperationsEnum operation = operations.get(operationString);
        calculator.setOperation(operation);
        boolean isUnary = calculator.isUnary(operation);

        if (isUnary) {
            boolean previousSymbolBracket = isBracket(index, formula);

            if (previousSymbolBracket) {
                calculator.setNumberSecond(null);
            }

            calculator.calculate();
        }
    }

    private static boolean isBracket (int index, String formula) {
        boolean isBracket = false;
        int previousIndex = index - 1;

        if (previousIndex > 0) {
            String previousSymbol = String.valueOf(formula.charAt(previousIndex));
            String rightBracket = ")";

            if (previousSymbol.equals(rightBracket)) {
                isBracket = true;
            }
        }
        return isBracket;
    }

    private static void setNumber (BigDecimal number) throws DivideZeroException, InvalidInputException, ResultUndefinedException, OperationException {
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
