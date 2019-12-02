package Model;

import Model.Exceptions.DivideZeroException;
import Model.Exceptions.InvalidInputException;
import Model.Exceptions.OperationException;
import Model.Exceptions.ResultUndefinedException;

import java.math.BigDecimal;
import java.util.HashMap;

public class CalculateFormula {
    static Calculator calculator;
    static HashMap<String, OperationsEnum> operationSymbols = new HashMap<>();
    static OperationsEnum binaryOperation;
    static OperationsEnum unaryOperation;
    static String numberString = "";
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
                if (calculator.isUnary(calculator.getOperation())) {
                    setOperationSymbols(binaryOperation);
                } else {
                    if(calculator.getNumberSecond() == null){
                        setNumber(calculator.getNumberFirst());
                    }
                    calculator.calculate();
                }
                setNumber(null);
                setOperationSymbols(null);
            }
        }
    }

    private static void parseOperation (String symbol) throws ResultUndefinedException, DivideZeroException, InvalidInputException, OperationException {
        if (operationSymbols.containsKey(symbol)) {
            if (calculator.isBinary(operationSymbols.get(symbol))) {
                binaryOperation = operationSymbols.get(symbol);
            }
            if (calculator.isUnary(operationSymbols.get(symbol))) {
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

    private static boolean isNumber (String i) {
        boolean isNumber;
        try {
            new BigDecimal(i);
            isNumber = true;
        } catch (Exception e) {
            isNumber = false;
        }
        return isNumber;
    }


    private static void setNumber (BigDecimal number) {
        if (calculator.getNumberFirst() == null) {
            calculator.setNumberFirst(number);
        } else {
            calculator.setNumberSecond(number);
        }
    }

    private static void setOperationSymbols (OperationsEnum operation) throws DivideZeroException, InvalidInputException, ResultUndefinedException, OperationException {
        if (operation != null) {
            if (calculator.isUnary(operation)) {
                calculator.setOperation(operation);
                calculator.calculate();
                unaryOperation = operation;
            }
            if (calculator.isBinary(operation)) {
                if (calculator.getOperation() != null) {
                    if (calculator.isUnary(calculator.getOperation())) {
                        calculator.setOperation(operation);
                    }
                    calculator.calculate();
                    calculator.setNumberSecond(null);
                }
                binaryOperation = operation;
                calculator.setOperation(operation);
            }
        } else {
            binaryOperation = operation;
            unaryOperation = operation;
            calculator.setOperation(operation);
        }
    }


}
