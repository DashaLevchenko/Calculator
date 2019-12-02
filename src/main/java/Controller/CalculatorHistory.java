package Controller;

import Model.Calculator;
import Model.History;
import Model.OperationsEnum;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.HashMap;

/**
 * This class change calculator history for calculator application
 */

public class CalculatorHistory {
    private Calculator calculator;
    private History history;
    private int minObjectsAmount = 2;
    private OperationsEnum percentOperation = OperationsEnum.PERCENT;
    private OperationsEnum negateOperation = OperationsEnum.NEGATE;
    private CharSequence exponentSeparator = "e";
    private String groupingSeparator = " ";

    public String getHistoryUnaryOperations () {
        return historyUnaryOperations;
    }

    private String historyUnaryOperations = "";
    private String negateHistory = "";
    private String emptyString = "";
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
     * Constructor set calculator and history calculator which need to change
     *
     * @param calculator Calculator history you need to change
     */
    CalculatorHistory (Calculator calculator) {
        this.calculator = calculator;
        this.history = calculator.getHistory();
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
    public String getChangedHistory () {
        if (historySize() != 0) {
            Object lastObject = calculator.getHistory().getLast();
            int indexObject = historySize() - 1;

            if (lastObject instanceof OperationsEnum) {
                OperationsEnum operation = (OperationsEnum) lastObject;
                changeOperation(operation, indexObject);
            }
            if (isNumber(lastObject)) {
                changeNumber(lastObject);
            }
        }
        return calculator.getHistory().getStringHistory();
    }

    /**
     * Method deletes last history object from calculator history
     * Example:
     *
     */
    public void deleteLastHistory () {
        history.deleteLast();
    }

    /**
     * Method returns size of calculator history
     * @return Size calculator history
     */
    public int historySize () {
        return history.size();
    }

    /**
     * Method adds number to calculator history
     * @param number Number need to add to calculator history
     */
    public void addHistoryNumber (BigDecimal number) {
        history.addNumber(number);
    }

    /**
     * Method cleans history which wrote down unary operation,
     * like "sqr(sqrt(8))"
     */
    public void clearHistoryUnaryOperations () {
        historyUnaryOperations = emptyString;
    }

    /**
     * Method cleans history which wrote down negate operation,
     * like "negate(negate(8))"
     */
    public void clearNegateHistory () {
        negateHistory = emptyString;
    }

    /**
     * Method cleans calculator history
     * @return Empty string for printing
     */
    public String clearHistory () {
        calculator.clearHistory();
        negateHistory = emptyString;
        historyUnaryOperations = emptyString;
        return emptyString;
    }

    /**
     * Method adds text to calculator history
     * @param text Text need to add to calculator history
     */
    private void addLastHistory (String text) {
        history.addOperationString(text);
    }

    private boolean isBinary (OperationsEnum operationsEnum) {
        return calculator.isBinary(operationsEnum);
    }

    /**
     * Method formats number for history.
     * It replaces "." to ",",
     * changes exponential separator "E" to "e",
     * deletes grouping separator
     *
     * Example:
     *          number: 999 999
     *          return: 999999
     *
     *          number: 1E-9999
     *          return: 1,e-9999
     *
     *          number: 1.98
     *          return: 1,98
     * @param number Number need format
     * @return String number was formatted
     */
    private String formatterNumberHistory (BigDecimal number) {
        String formattedNumber = FormatterNumber.formatterNumber(number);
        return deleteGroupingSeparator(formattedNumber);
    }

    private void deleteHistory (int index) {
        history.delete(index);
    }

    private void changeNumber (Object lastObject) {
        if (historySize() == minObjectsAmount) {
            clearHistory();
        } else {
            BigDecimal number = parseNumber(lastObject);
            addLastHistory(formatterNumberHistory(number));
            deletePreviousHistory(lastObject);
        }
    }

    private BigDecimal parseNumber (Object lastObject) {
        BigDecimal number = null;
        if (lastObject.toString().contains("e")) {
            try {
                number = FormatterNumber.parseNumber(lastObject.toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            number = new BigDecimal(deleteGroupingSeparator(lastObject.toString()));
        }

        return number;
    }

    private String deleteGroupingSeparator (String text) {
        groupingSeparator = String.valueOf(FormatterNumber.symbols.getGroupingSeparator());
        return Text.deleteNumberSeparator(text, groupingSeparator);
    }

    private void changeOperation (OperationsEnum operation, int indexObject) {
        if (isBinary(operation)) {
            changeBinaryOperationHistory(operation);
        }
        if (isUnary(operation)) {
            changeUnaryOperationHistory(operation, indexObject);
        }
        if (operation.equals(percentOperation)) {
            changePercentOperationHistory();
        }

        if (operation.equals(negateOperation)) {
            changeNegateOperationHistory(operation, indexObject);
        }
    }

    /**
     * Method deletes previous history.
     * if  {@code object} is binary operation
     * and his previous history is binary operation too, method deletes previous history.
     *
     * if  {@code object} is not binary operation
     * and his previous history is not operation, method deletes previous history.
     *
     * if  {@code object} is not operation
     * and his previous history is not operation, method deletes previous history.
     *
     * @param object Object which
     */
    private void deletePreviousHistory (Object object) {
        if (historySize() >= minObjectsAmount) {
            int indexPrevious = historySize() - minObjectsAmount;
            String previousHistory = getHistoryObject(indexPrevious).toString();

            if (object instanceof OperationsEnum) {
                if (isBinary((OperationsEnum) object)) {
                    if (operationSymbols.containsValue(previousHistory)) {
                        deleteHistory(indexPrevious);
                    }
                } else {
                    if (!operationSymbols.containsValue(previousHistory)) {
                        deleteHistory(indexPrevious);
                    }
                }
            } else {
                if (!operationSymbols.containsValue(previousHistory)) {
                    deleteHistory(indexPrevious);
                }
            }
        }
    }

    /**
     * This method changes history of Negate operation.
     * It wraps number was negated again or unary history in brackets and adds to calculator history,
     * Example:
     *          was: 4 + 5 + -9 NEGATE
     *          became: 4 + 5 + negate(9)
     *
     *          was: sqr(9) -81 NEGATE
     *          became: negate(sqr(9))
     *
     * @param operation Negate operation
     * @param index Index of negate operation in calculator history
     */
    private void changeNegateOperationHistory (OperationsEnum operation, int index) {
        if (historySize() > 1) {
            Object previousObject = getPreviousObject(index);

            deleteLastHistory();

            if (negateHistory.isEmpty()) {
                if (historyUnaryOperations.isEmpty()) {
                    if (isNumber(previousObject)) {
                        BigDecimal number = parseNumber(previousObject);
                        negateHistory = formatterNumberHistory(number);
                    } else {
                        negateHistory = previousObject.toString();
                    }
                } else {
                    negateHistory = historyUnaryOperations;
                }
            } else {
                deleteLastHistory();
            }

            negateHistory = wrapOperationInBrackets(operation, negateHistory);
            addLastHistory(negateHistory);
            deletePreviousHistory(operation);
        }
    }

    /**
     * This method deletes two previous notes from history.
     * Example:
     *          was: 5 + 7 NEGATE
     *          became: 5 +
     */
    public void deleteOldHistory () {
        deleteLastHistory();
        deleteLastHistory();
    }

    private String wrapOperationInBrackets (OperationsEnum operation, String text) {
        return operationSymbols.get(operation) + "(" + text + ")";
    }

    /**
     * Method changes history of percent operation.
     * It deletes last number and adds result of percent calculate
     */
    private void changePercentOperationHistory () {
        deleteOldHistory();
        BigDecimal calculateResult = calculator.getResult();
        addLastHistory(formatterNumberHistory(calculateResult));
        deletePreviousHistory(percentOperation);
    }

    private boolean isNumber (Object object) {
        String text = object.toString();
        try {
            if (text.contains(exponentSeparator)) {
                FormatterNumber.parseNumber(text);
            } else {
                new BigDecimal(text);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Method changes history of unary operation.
     * It wraps number in brackets.
     * If negate history is not empty, method  wraps negate history in brackets too
     *
     *
     * Example:
     *          was: 4 SQR
     *          became: sqr(4)
     *
     *          was: 4 NEGATE SQR
     *          became: sqr(-4)
     *
     *          was: 4 SQR ONE_DIVIDE_X
     *          became: 1/(sqr(4))
     *
     *          was: 4 + 8 SQR
     *          became: 4 + sqr(4)
     *
     *          was: 4 + negate(sqr(8)) -64 SQR
     *          became: 4 + sqr(negate(sqr(8)))
     * @param operation Unary operation
     * @param index Index of unary operation
     */
    private void changeUnaryOperationHistory (OperationsEnum operation, int index) {
        Object previousObject = getPreviousObject(index);
        deleteOldHistory();

        if (negateHistory.isEmpty()) {
            if (historyUnaryOperations.isEmpty()) {
                if (isNumber(previousObject)) {
                    BigDecimal number = parseNumber(previousObject);
                    historyUnaryOperations = formatterNumberHistory(number);
                }
            }
        } else {
            historyUnaryOperations = negateHistory;
            clearNegateHistory();
        }

        historyUnaryOperations = wrapOperationInBrackets(operation, historyUnaryOperations);
        addLastHistory(historyUnaryOperations);
        deletePreviousHistory(operation);
    }

    private Object getPreviousObject (int index) {
        return getHistoryObject(index-1);
    }

    private Object getHistoryObject (int index) {
        return history.get(index);
    }

    private boolean isUnary (OperationsEnum operationsEnum) {
        boolean isUnary = false;
        if(!operationsEnum.equals(negateOperation)){
            isUnary = calculator.isUnary(operationsEnum);
        }
        return isUnary;
    }

    /**
     * Method changes history of binary operation,
     * clears history of unary operation if is not empty
     *
     * It replace operation to symbol.
     * Example:
     *          was: 4 ADD
     *          became: 4 +
     *
     *          was: 4 + 8 MULTIPLY
     *          became: 4 + 8 x
     * @param operationsEnum Binary operation
     */
    private void changeBinaryOperationHistory (OperationsEnum operationsEnum) {
        deleteLastHistory();
        if (historySize() >= 1) {
            if (!historyUnaryOperations.isEmpty()) {
                clearHistoryUnaryOperations();
            }
        }
        addLastHistory(operationSymbols.get(operationsEnum));
        if (historySize() >= 3) {
            deletePreviousHistory(operationsEnum);
        }
    }
}



