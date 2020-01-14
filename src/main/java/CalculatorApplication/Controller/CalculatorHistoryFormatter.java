package CalculatorApplication.Controller;

import CalculatorApplication.Model.Calculator;
import CalculatorApplication.Model.History;
import CalculatorApplication.Model.OperationsEnum;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class format calculator history for calculator application.
 */
//todo delete static method
public class CalculatorHistoryFormatter {
    /** Variable keeps empty string value */
    private final String EMPTY_STRING = "";
    /**
     * Variable which keeps key and value of operation.
     * Key is name of enum operation.
     * Value is string equivalent of enum operation.
     */
    private final HashMap<OperationsEnum, String> OPERATION_SYMBOLS = new HashMap<>();

     {
        OPERATION_SYMBOLS.put(OperationsEnum.ADD, "+");
        OPERATION_SYMBOLS.put(OperationsEnum.SUBTRACT, "-");
        OPERATION_SYMBOLS.put(OperationsEnum.DIVIDE, "÷");
        OPERATION_SYMBOLS.put(OperationsEnum.MULTIPLY, "x");

        OPERATION_SYMBOLS.put(OperationsEnum.SQRT, "√");
        OPERATION_SYMBOLS.put(OperationsEnum.SQR, "sqr");
        OPERATION_SYMBOLS.put(OperationsEnum.ONE_DIVIDE_X, "1/");
        OPERATION_SYMBOLS.put(OperationsEnum.PERCENT, "");

        OPERATION_SYMBOLS.put(OperationsEnum.NEGATE, "negate");
    }

    /**
     * This method format {@code calculatorHistory}
     * and returns string equivalent of calculator history which  was formatted.
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
     * @return String of history was formatted.
     */
    public String formatCalculatorHistory (History calculatorHistory) {
        ArrayList<String> historyFormatted = new ArrayList<>();
        String unaryOperationsFormatted = EMPTY_STRING;

        for (int index = 0; index < calculatorHistory.size(); index++) {
            Object object = calculatorHistory.get(index);
            String historyObjectFormatted;

            if (object instanceof OperationsEnum) {
                OperationsEnum operation = (OperationsEnum) object;

                if (Calculator.isBinary(operation)) {
                    unaryOperationsFormatted = EMPTY_STRING;
                    historyObjectFormatted = OPERATION_SYMBOLS.get(operation);
                } else {
                    String previousFormattedHistoryRecord = getPreviousFormattedHistoryRecord(unaryOperationsFormatted, historyFormatted);
                    historyFormatted.remove(getLastIndex(historyFormatted));

                    if (isPreviousBinaryFormattedHistoryRecord(historyFormatted)) {
                        historyFormatted.remove(getLastIndex(historyFormatted));
                    }

                    unaryOperationsFormatted = formatUnaryOperation(previousFormattedHistoryRecord, operation);
                    historyObjectFormatted = unaryOperationsFormatted;
                }

            } else {
                BigDecimal number = (BigDecimal) object;
                historyObjectFormatted = CalculatorNumberFormatter.formatNumberForHistory(number);
            }

            historyFormatted.add(historyObjectFormatted);
        }

        return getStringHistory(historyFormatted);
    }


    private static int getLastIndex (ArrayList<String> formattedHistory) {
        return formattedHistory.size() - 1;
    }

    /**
     * This method checks previous history record which was formatted.
     *
     * @param formattedHistory History was formatted
     * @return Returns true if previous history record was formatted is value of {@code OPERATION_SYMBOLS},
     * and returns false if previous history record was formatted isn't value of {@code OPERATION_SYMBOLS}.
     */
    private boolean isPreviousBinaryFormattedHistoryRecord (ArrayList<String> formattedHistory) {
        boolean isPreviousBinaryFormattedHistoryObject;
        if (formattedHistory.size() > 0) {
            int indexPreviousFormattedHistory = formattedHistory.size() - 1;
            String previousFormattedHistoryObject = formattedHistory.get(indexPreviousFormattedHistory);
            isPreviousBinaryFormattedHistoryObject = !OPERATION_SYMBOLS.containsValue(previousFormattedHistoryObject);
        } else {
            isPreviousBinaryFormattedHistoryObject = false;
        }
        return isPreviousBinaryFormattedHistoryObject;
    }

    /**
     * This method defines string equivalent of unary operation,
     * wraps previous formatted history record in brackets and
     * concatenates equivalent of unary operation
     * with previous formatted history record was wrapped in brackets.
     * <p>
     * Example:
     * input: 4 SQR
     * return: sqr(4)
     * <p>
     * input: sqr(4) NEGATE
     * return: negate(sqr(4))
     * <p>
     *
     * @param unaryOperation                 Unary operation.
     * @param previousFormattedHistoryRecord Previous formatted history record.
     * @return String of formatted unary operation with
     * previous formatted history record which was wrapped in brackets.
     */
    private String formatUnaryOperation (String previousFormattedHistoryRecord, OperationsEnum unaryOperation) {
        String operationSymbol = OPERATION_SYMBOLS.get(unaryOperation);
        String previousHistoryObjectWrapped = wrapPreviousFormattedHistoryInBrackets(previousFormattedHistoryRecord);
        return operationSymbol.concat(previousHistoryObjectWrapped);
    }

    /**
     * Method gets previous formatted history record from {@code formattedHistory}.
     *
     * @param unaryOperationsFormatted String of unary operation which was formatted.
     * @param formattedHistory         History was formatted.
     * @return Previous formatted history record.
     */
    private String getPreviousFormattedHistoryRecord (String unaryOperationsFormatted, ArrayList<String> formattedHistory) {
        String previousFormattedHistoryRecord;
        if (formattedHistory.size() > 0) {
            if (!unaryOperationsFormatted.isEmpty()) {
                previousFormattedHistoryRecord = unaryOperationsFormatted;
            } else {
                previousFormattedHistoryRecord = formattedHistory.get(getLastIndex(formattedHistory));
            }
        } else {
            previousFormattedHistoryRecord = EMPTY_STRING;
        }

        return previousFormattedHistoryRecord;
    }


    /**
     * Method wraps previous history record in brackets.
     * Example: (2),
     * (sqrt(2)),
     * (sqr(sqrt(2))),
     *
     * @param previousFormattedHistoryRecord Previous record of formatted history need to wrap.
     * @return Previous record of formatted history was wrapped .
     */
    private static String wrapPreviousFormattedHistoryInBrackets (String previousFormattedHistoryRecord) {
        String rightBracket = ")";
        String leftBracket = "(";
        return leftBracket.concat(previousFormattedHistoryRecord).concat(rightBracket);
    }


    /** Method returns string of history with separator */
    private static String getStringHistory (ArrayList<String> formattedHistory) {
        String stringHistory = "";
        String separatorHistory = " ";

        for (String history : formattedHistory) {
            stringHistory = stringHistory.concat(history);
            stringHistory = stringHistory.concat(separatorHistory);
        }
        return stringHistory;
    }
}



