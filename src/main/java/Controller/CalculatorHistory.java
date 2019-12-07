package Controller;

import Model.Model;
import Model.OperationsEnum;

import java.math.BigDecimal;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class change calculator history for calculator application
 */

public class CalculatorHistory {
    private Model calculator;
    private OperationsEnum percentOperation = OperationsEnum.PERCENT;
    private OperationsEnum negateOperation = OperationsEnum.NEGATE;
    private CharSequence exponentSeparator = "e";

    public String getHistoryUnaryOperations () {
        return historyUnaryOperations;
    }

    private String historyUnaryOperations = "";
    private String negateHistory = "";

    public String getNegateHistory () {
        return negateHistory;
    }

    private String emptyString = "";
    private ArrayList<String> historyList = new ArrayList<>();
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
    CalculatorHistory (Model calculator) {
        this.calculator = calculator;
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
        if (calculator.getHistory().size() != 0) {
            Object lastObject = calculator.getHistory().getLast();
            int indexObject = calculator.getHistory().size() - 1;

            if (lastObject instanceof OperationsEnum) {
                OperationsEnum operation = (OperationsEnum) lastObject;
                changeOperation(operation, indexObject);
            }
            if (isNumber(lastObject)) {
                changeNumber(lastObject);
            }
        }
        return getStringHistory();
    }

    /**
     * Method deletes last history object from history
     */
    public void deleteLastObject () {
        historyList.remove(historyList.size() - 1);
    }

    /**
     * Method returns size of calculator history
     *
     * @return Size calculator history
     */
    public int historySize () {
        return historyList.size();
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
        if (!negateHistory.isEmpty()) {
            negateHistory = emptyString;
        }
    }

    /**
     * Method cleans calculator history
     *
     * @return Empty string for printing
     */
    public String clearHistory () {
        historyList.clear();
        negateHistory = emptyString;
        historyUnaryOperations = emptyString;
        return emptyString;
    }


    private boolean isBinary (OperationsEnum operationsEnum) {
        return calculator.isBinary(operationsEnum);
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
    private String formatterNumberHistory (BigDecimal number) {
        String formattedNumber = FormatterNumber.formatterNumber(number);
        return deleteGroupingSeparator(formattedNumber);
    }

    private void changeNumber (Object lastObject) {
        BigDecimal number = parseNumber(lastObject);
        String addNumber = formatterNumberHistory(number);
        historyList.add(addNumber);
    }

    private BigDecimal parseNumber (Object lastObject) {
        BigDecimal number = null;
        String exponent = "e";
        String lastHistory = lastObject.toString();
        if (lastHistory.contains(exponent)) {
            try {
                number = FormatterNumber.parseNumber(lastHistory);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            String numberWithoutSeparator = deleteGroupingSeparator(lastHistory);
            number = new BigDecimal(numberWithoutSeparator);
        }

        return number;
    }

    private String deleteGroupingSeparator (String text) {
        DecimalFormatSymbols formatterSymbols = FormatterNumber.getSymbols();
        String groupingSeparator = String.valueOf(formatterSymbols.getGroupingSeparator());
        return text.replace(groupingSeparator, "");
    }

    private void changeOperation (OperationsEnum operation, int indexObject) {
        if (isBinary(operation)) {
            changeBinaryOperationHistory(operation, indexObject);
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
     * <p>
     * if  {@code object} is not binary operation
     * and his previous history is not operation, method deletes previous history.
     * <p>
     * if  {@code object} is not operation
     * and his previous history is not operation, method deletes previous history.
     * <p>
     * //     * @param object Object which
     */
    private void deletePreviousHistory (Object historyObject) {
        int minSizeHistoryForDeletePrev = 2;

        if (historySize() >= minSizeHistoryForDeletePrev) {
            int indexStart = historySize() - minSizeHistoryForDeletePrev;

            for (int i = indexStart; i >= 0; i--) {
                String previousHistory = historyList.get(i);

                if (historyObject instanceof OperationsEnum) {
                    OperationsEnum operation = (OperationsEnum) historyObject;

                    if (isBinary(operation)) {
                        if (isOperationSymbol(previousHistory)) {
                            historyList.remove(i);
                        } else {
                            break;
                        }
                    } else {
                        if (!isOperationSymbol(previousHistory)) {
                            historyList.remove(i);
                        } else {
                            break;
                        }
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
    private void changeNegateOperationHistory (OperationsEnum operation, int index) {
        int minSizeHistory = 1;
        int sizeHistoryIn = calculator.getHistory().size();

        if (sizeHistoryIn > minSizeHistory) {
            if (negateHistory.isEmpty()) {
                Object previousObject = getPreviousObject(index);
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
            }

            negateHistory = wrapOperationInBrackets(operation, negateHistory);
            historyList.add(negateHistory);
            deletePreviousHistory(operation);
        }
    }


    private String wrapOperationInBrackets (OperationsEnum operation, String text) {
        String operationSymbol = operationSymbols.get(operation);
        String leftBracket = "(";
        String rightBracket = ")";
        return operationSymbol.concat(leftBracket).concat(text).concat(rightBracket);
    }

    /**
     * Method changes history of percent operation.
     * It deletes last number and adds result of percent calculate
     */
    private void changePercentOperationHistory () {
//        BigDecimal calculateResult = calculator.getResult();
//        String addHistory = formatterNumberHistory(calculateResult);
//        historyList.add(addHistory);
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
     * @param operation Unary operation
     * @param index     Index of unary operation
     */
    private void changeUnaryOperationHistory (OperationsEnum operation, int index) {
        Object previousObject = getPreviousObject(index);

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
        historyList.add(historyUnaryOperations);

        deletePreviousHistory(operation);
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
        return calculator.getHistory().get(index);
    }

    private boolean isUnary (OperationsEnum operationsEnum) {
        boolean isUnary = false;
        if (!operationsEnum.equals(negateOperation)) {
            isUnary = calculator.isUnary(operationsEnum);
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
     * @param operationsEnum Binary operation
     */
    private void changeBinaryOperationHistory (OperationsEnum operationsEnum, int indexObject) {
        if (!historyUnaryOperations.isEmpty()) {
            clearHistoryUnaryOperations();
        }

        Object previousObject = getPreviousObject(indexObject);
        if (previousObject instanceof BigDecimal) {
            String numberFormatted = formatterNumberHistory((BigDecimal) previousObject);
            historyList.add(numberFormatted);
        }

        String addHistory = operationSymbols.get(operationsEnum);
        historyList.add(addHistory);

        deletePreviousHistory(operationsEnum);
    }

    /**
     * Method returns string of history with separator.
     *
     * @return History was separated.
     */
    public String getStringHistory () {
        String stringHistory = "";
        for (String history : historyList) {
            stringHistory = stringHistory.concat(history);
            String separatorHistory = " ";
            stringHistory = stringHistory.concat(separatorHistory);
        }
        return stringHistory;
    }
}



