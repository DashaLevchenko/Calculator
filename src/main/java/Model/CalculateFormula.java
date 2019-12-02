package Model;

import Model.Exceptions.DivideZeroException;
import Model.Exceptions.InvalidInputException;
import Model.Exceptions.OperationException;
import Model.Exceptions.ResultUndefinedException;

import java.math.BigDecimal;
import java.util.HashMap;

/**
 * This class calculate formula and return result.
 */
public class CalculateFormula {
    private static Calculator calculator;
    private static HashMap<String, OperationsEnum> operationSymbols = new HashMap<>();
    private static OperationsEnum binaryOperation;
    private static OperationsEnum unaryOperation;
    private static String numberString = "";
    private static String decimalSeparator = ".";
    private static String equal = "=";

    static {
        operationSymbols.put("+", OperationsEnum.ADD);
        operationSymbols.put("-", OperationsEnum.SUBTRACT);
        operationSymbols.put("/", OperationsEnum.DIVIDE);
        operationSymbols.put("x", OperationsEnum.MULTIPLY);

        operationSymbols.put("√", OperationsEnum.SQRT);
        operationSymbols.put("", OperationsEnum.SQR);
        operationSymbols.put("", OperationsEnum.ONE_DIVIDE_X);
        operationSymbols.put("", OperationsEnum.PERCENT);

        operationSymbols.put("", OperationsEnum.NEGATE);
    }

    /**
     * This method calculate formula and return result.
     * Example:
     * formula: 5 + 3 =
     * result:  8
     * <p>
     * formula: 5 + 3 = 
     * result:  -8
     * <p>
     * formula: 5 + 3 x 8 = 
     * result:  -512
     *
     * @param formula Formula which need to calculate
     * @return Number was calculated by formula
     * @throws DivideZeroException      If divide by zero
     * @throws ResultUndefinedException If zero divide by zero
     * @throws OperationException       If operation not equals calculator operation
     * @throws InvalidInputException    If square root negative number
     */
    public static BigDecimal calculateFormula (String formula) throws DivideZeroException, InvalidInputException, ResultUndefinedException, OperationException {
        calculator = new Calculator();
        for (int i = 0; i < formula.length(); i++) {
            String symbol = String.valueOf(formula.toCharArray()[i]);
            parseNumber(symbol, i, formula);
            parseOperation(symbol);
            parseEqual(symbol);
        }

        return calculator.getResult();
    }

    private static void parseEqual (String symbol) throws ResultUndefinedException, DivideZeroException, InvalidInputException, OperationException {
        if (symbol.equals(equal)) {
            if (binaryOperation != null) {
                if (isUnary(getOperation())) {
                    setOperationSymbols(binaryOperation);
                } else {
                    if (getNumberSecond() == null) {
                        setNumber(getNumberFirst());
                    }
                    calculate();
                }
                setNumber(null);
                setOperationSymbols(null);
            }
        }
    }

    private static BigDecimal getNumberSecond () {
        return calculator.getNumberSecond();
    }

    private static void parseOperation (String symbol) throws ResultUndefinedException, DivideZeroException, InvalidInputException, OperationException {
        if (operationSymbols.containsKey(symbol)) {
            if (isBinary(operationSymbols.get(symbol))) {
                binaryOperation = operationSymbols.get(symbol);
            }
            if (isUnary(operationSymbols.get(symbol))) {
                unaryOperation = operationSymbols.get(symbol);
            }

            setOperationSymbols(operationSymbols.get(symbol));
        }
    }

    private static String nextSymbol (String formula, int index) {
        String symbolNext = "";
        if (formula.length() > index + 1) {
            symbolNext = String.valueOf(formula.toCharArray()[index + 1]);
        }
        return symbolNext;
    }

    private static void parseNumber (String symbol, int i, String formula) {
        String nextSymbol = nextSymbol(formula, i);
        if (isNumber(symbol) || symbol.equals(decimalSeparator)) {

            numberString = numberString.concat(symbol);

            if (!isNumber(nextSymbol)) {
                if (!nextSymbol.equals(decimalSeparator)) {
                    setNumber(new BigDecimal(numberString));
                    numberString = "";
                }
            }

        }
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


    private static void setNumber (BigDecimal number) {
        if (getNumberFirst() == null) {
            setNumberFirst(number);
        } else {
            setNumberSecond(number);
        }
    }

    private static void setNumberFirst (BigDecimal number) {
        calculator.setNumberFirst(number);
    }

    private static BigDecimal getNumberFirst () {
        return getNumberFirst();
    }

    private static void setOperationSymbols (OperationsEnum operation) throws DivideZeroException, InvalidInputException, ResultUndefinedException, OperationException {
        if (operation != null) {
            if (isUnary(operation)) {
                setCalculateUnaryOperation(operation);
            }
            if (isBinary(operation)) {
                setCalculateBinaryOperation(operation);
            }
        } else {
            binaryOperation = operation;
            unaryOperation = operation;
            setOperation(operation);
        }
    }

    private static void setCalculateUnaryOperation (OperationsEnum operation) throws ResultUndefinedException, DivideZeroException, InvalidInputException, OperationException {
        setOperation(operation);
        calculate();
        unaryOperation = operation;
    }

    private static void setCalculateBinaryOperation (OperationsEnum operation) throws ResultUndefinedException, DivideZeroException, InvalidInputException, OperationException {
        if (getOperation() != null) {
            if (isUnary(getOperation())) {
                setOperation(operation);
            }
            calculate();
            setNumberSecond(null);
        }
        binaryOperation = operation;
        setOperation(operation);
    }

    private static void setNumberSecond (BigDecimal number) {
        calculator.setNumberSecond(number);
    }

    private static OperationsEnum getOperation () {
        return calculator.getOperation();
    }

    private static boolean isBinary (OperationsEnum operation) {
        return calculator.isBinary(operation);
    }

    private static boolean isUnary (OperationsEnum operation) {
        return calculator.isUnary(operation);
    }

    private static void calculate () throws DivideZeroException, InvalidInputException, ResultUndefinedException, OperationException {
        calculator.calculate();
    }

    private static void setOperation (OperationsEnum operation) {
        calculator.setOperation(operation);
    }


}
