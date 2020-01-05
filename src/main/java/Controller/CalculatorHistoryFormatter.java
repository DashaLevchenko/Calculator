package Controller;

import Model.Calculator;
import Model.History;
import Model.OperationsEnum;

import java.math.BigDecimal;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class change calculator history for calculator application.
 */
class CalculatorHistoryFormatter {
    /** Variable keeps empty string value */
    private final String EMPTY_STRING = "";

    /** Exponent separator which is being used for number in application */
    private final String EXPONENT_SEPARATOR = "e";

    /** Constant keeps value of percent operation */
    private final OperationsEnum PERCENT_OPERATION = OperationsEnum.PERCENT;

    /** Constant keeps value of negate operation */
    private final OperationsEnum NEGATE_OPERATION = OperationsEnum.NEGATE;

    /** This variable keeps unformatter calculator history */
    private History history;

    /** Variable keeps unary operation history was formatted */
    private String historyUnaryOperations;

    /** Variable keeps negate operation history was formatted */
    private String negateHistory;

    /** This list keeps history objects was formatted */
    private ArrayList<String> historyListOut = new ArrayList<>();

    /**
     * Variable which keeps key and value of operation.
     * Key is name of enum operation.
     * Value is symbol need to change.
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

    /**
     * This constructor initialize {@code this.history}.
     *
     * @param history History which need to change
     */
    CalculatorHistoryFormatter (History history) {
        this.history = history;
    }

    /**
     * This method changes standard history of calculator and returns history was changed .
     * Example:
     * history was: " 2 ADD 3 SUBTRACT"
     * history became: "2 + 3 - "
     * <p>
     * history was: " 2 ADD 3 SQRT"
     * history became: "2 + √(3) "
     * <p>
     * history was: " 2 ADD 3 SQRT SQR NEGATE SUBTRACT"
     * history became: "2 + negate(sqr(√(3))) - "
     *
     * @return History was changed
     */
    String formatHistory () throws ParseException {
        int historyInSize = history.size();
        negateHistory = "";
        historyUnaryOperations = "";

        if (historyInSize != 0) {
            for (int index = 0; index < historyInSize; index++) {
                Object object = history.get(index);

                if (object instanceof OperationsEnum) {
                    OperationsEnum operation = (OperationsEnum) object;
                    changeOperation(operation, index);
                }
                if (isNumber(object)) {
                    changeNumber(object);
                }
            }
        }

        return getStringHistory();
    }


    /** Method returns size of calculator history */
    private int historySize () {
        return historyListOut.size();
    }


    /**
     * Method cleans history which wrote down unary operation,
     * like "sqr(sqrt(8))"
     */
    private void clearHistoryUnaryOperations () {
        historyUnaryOperations = EMPTY_STRING;
    }

    /**
     * Method cleans history which wrote down negate operation,
     * like "negate(negate(8))"
     */
    private void clearNegateHistory () {
        negateHistory = EMPTY_STRING;
    }


    /** Method cleans calculator history */
    private void clearHistory () {
        historyListOut.clear();
        clearNegateHistory();
        historyUnaryOperations = EMPTY_STRING;
    }


    private boolean isBinary (OperationsEnum operationsEnum) {
        return Calculator.isBinary(operationsEnum);
    }

    /**
     * Method formats number for history.
     * It replaces "." to ",",
     * changes exponential separator "E" to "e",
     * deletes grouping separator
     * <p>
     * Example:
     * number: 999 999
     * return: 999999
     * <p>
     * number: 1E-9999
     * return: 1,e-9999
     * <p>
     * number: 1.98
     * return: 1,98
     *
     * @param number Number need format
     * @return String number was formatted
     */
    private String formatNumber (BigDecimal number) {
        String formattedNumber = CalculatorNumberFormatter.formatNumberForPrint(number);
        return deleteGroupingSeparator(formattedNumber);
    }

    private void changeNumber (Object lastObject) {
        BigDecimal number = null;
        try {
            number = parseNumber(lastObject);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String addNumber = formatNumber(number);
        historyListOut.add(addNumber);
        deletePreviousHistory(number);
    }

    private BigDecimal parseNumber (Object lastObject) throws ParseException {
        BigDecimal number;
        String lastHistory = lastObject.toString();

        if (lastHistory.contains(EXPONENT_SEPARATOR)) {
            number = CalculatorNumberFormatter.getParsedNumber(lastHistory);
        } else {
            String numberWithoutSeparator = deleteGroupingSeparator(lastHistory);
            number = new BigDecimal(numberWithoutSeparator);
        }

        return number;
    }

    private String deleteGroupingSeparator (String text) {
        DecimalFormatSymbols formatterSymbols = CalculatorNumberFormatter.getSymbols();
        String groupingSeparator = String.valueOf(formatterSymbols.getGroupingSeparator());
        return text.replace(groupingSeparator, "");
    }

    private void changeOperation (OperationsEnum operation, int indexObject) throws ParseException {
        if (isBinary(operation)) {
            changeBinaryOperationHistory(operation);
        }
        if (isUnary(operation)) {
            changeUnaryOperationHistory(operation, indexObject);
        }
        if (operation.equals(PERCENT_OPERATION)) {
            changePercentOperationHistory();
        }
        if (operation.equals(NEGATE_OPERATION)) {
            changeNegateOperationHistory(operation, indexObject);
        }
        if (operation.equals(OperationsEnum.EQUAL)) {
            clearHistory();
        }


    }

    /**
     * Method deletes previous history.
     * if  {@code object} is binary operation
     * and his previous history is binary operation too, method deletes previous history.
     * <p>
     * if  {@code object} is not binary operation
     * and his previous history is not operation, method deletes previous history.
     * <p>
     * if  {@code object} is not operation
     * and his previous history is not operation, method deletes previous history.
     * <p>
     *
     * @param presentHistoryObject Present history object
     */
    private void deletePreviousHistory (Object presentHistoryObject) {
        int minSizeHistoryForDeletePrev = 2;

        if (historySize() >= minSizeHistoryForDeletePrev) {
            int indexStart = historySize() - minSizeHistoryForDeletePrev;

            for (int i = indexStart; i >= 0; i--) {
                String previousHistory = historyListOut.get(i);


                if (presentHistoryObject instanceof OperationsEnum || isNumber(presentHistoryObject)) {

                    boolean isBinaryPresent = false;
                    if (presentHistoryObject instanceof OperationsEnum) {
                        isBinaryPresent = isBinary((OperationsEnum) presentHistoryObject);
                    }

                    boolean isOperationPrevious = isOperationSymbol(previousHistory);

                    boolean isBinaryPresentIsPreviousOperation = isBinaryPresent && isOperationPrevious;
                    boolean isNotBinaryPresentIsNotPreviousOperation = !isBinaryPresent && !isOperationPrevious;

                    if (isBinaryPresentIsPreviousOperation || isNotBinaryPresentIsNotPreviousOperation) {
                        historyListOut.remove(i);
                    } else {
                        break;
                    }
                }

            }

        }
    }

    private boolean isOperationSymbol (String previousHistory) {
        return operationSymbols.containsValue(previousHistory);
    }

    /**
     * This method changes history of Negate operation.
     * It wraps number was negated again or unary history in brackets and adds to calculator history,
     * Example:
     * was: 4 + 5 + -9 NEGATE
     * became: 4 + 5 + negate(9)
     * <p>
     * was: sqr(9) -81 NEGATE
     * became: negate(sqr(9))
     *
     * @param operation Negate operation
     * @param index     Index of negate operation in calculator history
     */
    private void changeNegateOperationHistory (OperationsEnum operation, int index) throws ParseException {
        int minSizeHistory = 1;
        int sizeHistoryIn = history.size();

        if (sizeHistoryIn > minSizeHistory) {
            if (negateHistory.isEmpty()) {
                Object previousObject = getPreviousObject(index);

                if (historyUnaryOperations.isEmpty()) {
                    if (isNumber(previousObject)) {
                        BigDecimal number = parseNumber(previousObject);

                        negateHistory = formatNumber(number);

                    } else {
                        negateHistory = previousObject.toString();
                    }
                } else {
                    negateHistory = historyUnaryOperations;
                }
            }

            negateHistory = wrapOperationInBrackets(operation, negateHistory);
            historyListOut.add(negateHistory);
            deletePreviousHistory(operation);
        }
    }

    private String wrapOperationInBrackets (OperationsEnum operation, String text) {
        String operationSymbol = operationSymbols.get(operation);

        String rightBracket = ")";
        String leftBracket = "(";
        return operationSymbol.concat(leftBracket).concat(text).concat(rightBracket);
    }

    /*
     * Method changes history of percent operation.
     * It deletes last number and adds result of percent calculate
     */
    private void changePercentOperationHistory () {
        deletePreviousHistory(PERCENT_OPERATION);
    }

    private boolean isNumber (Object object) {
        String text = object.toString();
        boolean isNumber;

        if (text.contains(EXPONENT_SEPARATOR)) {
            try {
                parseNumber(text);
                isNumber = true;
            } catch (ParseException e) {
                isNumber = false;
            }

        } else {
            try {
                new BigDecimal(text);
                isNumber = true;
            } catch (NumberFormatException e) {
                isNumber = false;
            }

        }

        return isNumber;
    }

    /**
     * Method changes history of unary operation.
     * It wraps number in brackets.
     * If negate history is not empty, method  wraps negate history in brackets too
     * <p>
     * <p>
     * Example:
     * was: 4 SQR
     * became: sqr(4)
     * <p>
     * was: 4 NEGATE SQR
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
     * @param unaryOperation Unary operation
     * @param index          Index of negate operation in calculator history
     */
    private void changeUnaryOperationHistory (OperationsEnum unaryOperation, int index) {
        Object previousObject = getPreviousObject(index);

        if (negateHistory.isEmpty()) {
            if (historyUnaryOperations.isEmpty()) {
                if (isNumber(previousObject)) {
                    BigDecimal number = null;
                    try {
                        number = parseNumber(previousObject);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    historyUnaryOperations = formatNumber(number);
                }
            }
        } else {
            historyUnaryOperations = negateHistory;
            clearNegateHistory();
        }

        historyUnaryOperations = wrapOperationInBrackets(unaryOperation, historyUnaryOperations);
        historyListOut.add(historyUnaryOperations);

        deletePreviousHistory(unaryOperation);
    }

    private Object getPreviousObject (int index) {
        int previousIndex = index - 1;
        Object previousObject = null;
        if (previousIndex >= 0) {
            previousObject = getHistoryObject(index - 1);
        }
        return previousObject;
    }

    private Object getHistoryObject (int index) {
        return history.get(index);
    }

    private boolean isUnary (OperationsEnum operationsEnum) {
        boolean isUnary = false;
        if (operationsEnum != OperationsEnum.NEGATE) {
            isUnary = Calculator.isUnary(operationsEnum);
        }
        return isUnary;
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
    private void changeBinaryOperationHistory (OperationsEnum binaryOperation) {
        if (!historyUnaryOperations.isEmpty()) {
            clearHistoryUnaryOperations();
        }
        if (!negateHistory.isEmpty()) {
            clearNegateHistory();
        }

        String addHistory = operationSymbols.get(binaryOperation);
        historyListOut.add(addHistory);

        deletePreviousHistory(binaryOperation);
    }


    /** Method returns string of history with separator */
    private String getStringHistory () {
        String stringHistory = "";
        String separatorHistory = " ";

        for (String history : historyListOut) {
            stringHistory = stringHistory.concat(history);
            stringHistory = stringHistory.concat(separatorHistory);
        }

        return stringHistory;
    }
}



