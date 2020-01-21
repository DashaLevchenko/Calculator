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
public class CalculatorHistoryFormatter {
    /**
     * Variable keeps empty string value
     */
    private static final String EMPTY_STRING = "";
    /**
     * Variable which keeps key and value of operation.
     * Key is name of enum operation.
     * Value is string equivalent of enum operation.
     */
    private static HashMap<OperationsEnum, String> operationSymbols = new HashMap<>();

    static {
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

    /**
     * This variable keeps calculator number formatter for format number.
     */
    private CalculatorNumberFormatter calculatorNumberFormatter = new CalculatorNumberFormatter();

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
                    historyObjectFormatted = operationSymbols.get(operation);
                } else {
                    String previousFormattedHistoryRecord = getPreviousFormattedHistoryRecord(historyFormatted, unaryOperationsFormatted);
                    historyFormatted.remove(getLastIndexHistoryFormatted(historyFormatted));

                    if (isPreviousBinaryFormattedHistoryRecord(historyFormatted)) {
                        historyFormatted.remove(getLastIndexHistoryFormatted(historyFormatted));
                    }

                    unaryOperationsFormatted = formatUnaryOperation(previousFormattedHistoryRecord, operation);
                    historyObjectFormatted = unaryOperationsFormatted;
                }
            } else {
                BigDecimal number = (BigDecimal) object;
                historyObjectFormatted = calculatorNumberFormatter.formatNumberForCalculatorHistory(number);
            }

            historyFormatted.add(historyObjectFormatted);
        }

        return getStringHistory(historyFormatted);
    }



    private int getLastIndexHistoryFormatted (ArrayList<String> historyFormatted) {
        return historyFormatted.size() - 1;
    }

    /**
     * This method checks previous history record which was formatted.
     *
     * @param formattedHistory History was formatted
     * @return Returns true if previous history record was formatted is value of {@code OPERATION_SYMBOLS},
     *         and returns false if previous history record was formatted isn't value of {@code OPERATION_SYMBOLS}.
     */
    private boolean isPreviousBinaryFormattedHistoryRecord (ArrayList<String> formattedHistory) {
        boolean isPreviousBinaryFormattedHistoryObject;
        if (formattedHistory.size() > 0) {
            int indexPreviousFormattedHistory = formattedHistory.size() - 1;
            String previousFormattedHistoryObject = formattedHistory.get(indexPreviousFormattedHistory);
            isPreviousBinaryFormattedHistoryObject = !operationSymbols.containsValue(previousFormattedHistoryObject);
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
     *         previous formatted history record which was wrapped in brackets.
     */
    private String formatUnaryOperation (String previousFormattedHistoryRecord, OperationsEnum unaryOperation) {
        String operationSymbol = operationSymbols.get(unaryOperation);
        String previousHistoryObjectWrapped = wrapPreviousFormattedHistoryInBrackets(previousFormattedHistoryRecord);
        return operationSymbol.concat(previousHistoryObjectWrapped);
    }

    /**
     * Method gets previous formatted history record from {@code formattedHistory}.
     * * @return Previous formatted history record.
     */
    private String getPreviousFormattedHistoryRecord (ArrayList<String> historyFormatted, String unaryOperationsFormatted) {
        String previousFormattedHistoryRecord;
        if (historyFormatted.size() > 0) {
            if (!unaryOperationsFormatted.isEmpty()) {
                previousFormattedHistoryRecord = unaryOperationsFormatted;
            } else {
                previousFormattedHistoryRecord = historyFormatted.get(getLastIndexHistoryFormatted(historyFormatted));
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
    private String wrapPreviousFormattedHistoryInBrackets (String previousFormattedHistoryRecord) {
        String rightBracket = ")";
        String leftBracket = "(";
        return leftBracket.concat(previousFormattedHistoryRecord).concat(rightBracket);
    }


    /**
     * Method returns string of history with separator
     *
     * @param historyFormatted History need to write down with
     * @return History string with separator.
     */
    private String getStringHistory (ArrayList<String> historyFormatted) {
        String stringHistory = EMPTY_STRING;
        String separatorHistory = " ";

        for (String history : historyFormatted) {
            stringHistory = stringHistory.concat(history);
            stringHistory = stringHistory.concat(separatorHistory);
        }
        return stringHistory;
    }
}



