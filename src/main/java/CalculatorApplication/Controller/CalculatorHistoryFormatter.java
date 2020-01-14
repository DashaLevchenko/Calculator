package CalculatorApplication.Controller;

import CalculatorApplication.Model.Calculator;
import CalculatorApplication.Model.History;
import CalculatorApplication.Model.OperationsEnum;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class change calculator history for calculator application.
 */
//todo delete static method
class CalculatorHistoryFormatter {
    /**
     * Variable which keeps key and value of operation.
     * Key is name of enum operation.
     * Value is symbol need to format.
     */
    private HashMap<OperationsEnum, String> operationSymbols = new HashMap<>();

     {
        operationSymbols.put(OperationsEnum.ADD, "+");
        operationSymbols.put(OperationsEnum.SUBTRACT, "-");
        operationSymbols.put(OperationsEnum.DIVIDE, "÷");
        operationSymbols.put(OperationsEnum.MULTIPLY, "x");

        operationSymbols.put(OperationsEnum.SQRT, "√");
        operationSymbols.put(OperationsEnum.SQR, "sqr");
        operationSymbols.put(OperationsEnum.ONE_DIVIDE_X, "1/");
        operationSymbols.put(OperationsEnum.PERCENT, "");

        operationSymbols.put(OperationsEnum.NEGATE, "negate");
    }

    /** Variable keeps empty string value */
    private final String EMPTY_STRING = "";
    /** Variable keeps unary operation history was formatted */
    private String unaryOperationsFormatted;
    /** This list keeps history objects was formatted */
    private ArrayList<String> formattedHistory = new ArrayList<>();

    /**
     * This method format {@code calculatorHistory}
     * and returns string {@code calculatorHistory} was formatted.
     * <p>
     * Example:
     * calculatorHistory: " 2 ADD 3 SUBTRACT"
     * return: "2 + 3 - "
     * <p>
     * calculatorHistory: " 2 ADD 3 SQRT"
     * return: "2 + √(3) "
     * <p>
     * calculatorHistory: " 2 ADD 3 SQRT SQR NEGATE SUBTRACT"
     * return: "2 + negate(sqr(√(3))) - "
     *
     * @param calculatorHistory Calculator history need to format
     * @return History was formatted
     */
    String formatCalculatorHistory (History calculatorHistory) {
        unaryOperationsFormatted = "";

        for (int index = 0; index < calculatorHistory.size(); index++) {
            Object object = calculatorHistory.get(index);

            if (object instanceof OperationsEnum) {
                OperationsEnum operation = (OperationsEnum) object;

                String previousFormattedHistoryObject;
                if (formattedHistory.size() > 0) {
                    previousFormattedHistoryObject = formattedHistory.get(formattedHistory.size() - 1);
                } else {
                    previousFormattedHistoryObject = EMPTY_STRING;
                }

                formatOperation(operation, previousFormattedHistoryObject);
            }
            if (object instanceof BigDecimal) {
                BigDecimal number = (BigDecimal) object;
                formatNumber(number);
            }
        }


        return getStringHistory();
    }


    /**
     * Method format {@code number} and add to {@code formattedHistory}
     *
     * @param number Number need to format.
     */
    private void formatNumber (BigDecimal number) {
        String addNumber = CalculatorNumberFormatter.formatNumberForHistory(number);
        formattedHistory.add(addNumber);
    }

    /**
     * Method calls method of format unary operation, if {@code operation} is unary operation,
     * and calls method of format binary operation, if {@code operation} is binary operation
     *
     * @param operation                      Operation which was in calculator history.
     * @param previousFormattedHistoryObject Previous object of formatted history.
     */
    private void formatOperation (OperationsEnum operation, String previousFormattedHistoryObject) {
        if (Calculator.isBinary(operation)) {
            formatBinaryOperation(operation);
        }
        if (Calculator.isUnary(operation)) {
            formatUnaryOperation(operation, previousFormattedHistoryObject);
        }
    }


    /**
     * Method wraps previous  history in brackets.
     * Example: (2),
     * (sqrt(2)),
     * (sqr(sqrt(2))),
     *
     * @param previousFormattedHistoryObject Previous object of formatted history need to wrap.
     * @return Previous object of formatted history was wrapped .
     */
    private String wrapPreviousFormattedHistoryInBrackets (String previousFormattedHistoryObject) {
        String rightBracket = ")";
        String leftBracket = "(";
        return leftBracket.concat(previousFormattedHistoryObject).concat(rightBracket);
    }


    /**
     * Method formats unary operation.
     * Method wraps previous object of formatted history in brackets,
     * remove it and check new previous object of formatted history,
     * if it isn't operation, new previous object will removed.
     * After, unary operation concatenates with wrapped previous history
     * and add to {@code formattedHistory}.
     * <p>
     * <p>
     * Example:
     * was: 4 SQR
     * became: sqr(4)
     * <p>
     * was: -4 SQR
     * became: sqr(-4)
     * <p>
     * was: 4 SQR ONE_DIVIDE_X
     * became: 1/(sqr(4))
     * <p>
     * was: 4 + 8 SQR
     * became: 4 + sqr(4)
     * <p>
     * was: 4 + negate(sqr(8)) -64 SQR
     * became: 4 + sqr(negate(sqr(8)))
     *
     * @param unaryOperation           Unary operation
     * @param previousFormattedHistory Previous object of formatted history need to wrap.
     */
    private void formatUnaryOperation (OperationsEnum unaryOperation, String previousFormattedHistory) {
        formattedHistory.remove(formattedHistory.size() - 1);

        int indexPreviousFormattedHistory = formattedHistory.size() - 1;
        if (!unaryOperationsFormatted.isEmpty()) {
            previousFormattedHistory = unaryOperationsFormatted;
        }
        unaryOperationsFormatted = previousFormattedHistory;

        String operationSymbol = operationSymbols.get(unaryOperation);
        String previousHistoryObjectWrapped = wrapPreviousFormattedHistoryInBrackets(previousFormattedHistory);
        unaryOperationsFormatted = operationSymbol.concat(previousHistoryObjectWrapped);

        if (formattedHistory.size() > 0) {
            String newPreviousFormattedHistoryObject = formattedHistory.get(indexPreviousFormattedHistory);
            if (!operationSymbols.containsValue(newPreviousFormattedHistoryObject)) {
                formattedHistory.remove(formattedHistory.size() - 1);
            }
        }

        formattedHistory.add(unaryOperationsFormatted);
    }


    /**
     * Method changes history of binary operation,
     * clears history of unary operation if is not empty
     * <p>
     * It replace operation to symbol.
     * Example:
     * was: 4 ADD
     * became: 4 +
     * <p>
     * was: 4 + 8 MULTIPLY
     * became: 4 + 8 x
     *
     * @param binaryOperation Binary operation
     */
    private void formatBinaryOperation (OperationsEnum binaryOperation) {
        if (!unaryOperationsFormatted.isEmpty()) {
            unaryOperationsFormatted = EMPTY_STRING;
        }

        String addHistory = operationSymbols.get(binaryOperation);
        formattedHistory.add(addHistory);
    }


    /** Method returns string of history with separator */
    private String getStringHistory () {
        String stringHistory = "";
        String separatorHistory = " ";

        for (String history : formattedHistory) {
            stringHistory = stringHistory.concat(history);
            stringHistory = stringHistory.concat(separatorHistory);
        }
        formattedHistory.clear();
        return stringHistory;
    }
}



