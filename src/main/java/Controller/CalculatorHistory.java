package Controller;

import Model.Calculator;
import Model.History;
import Model.OperationsEnum;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class change calculator history
 */

public class CalculatorHistory {
    Calculator calculator;
    History history;
    private int minObjectsAmount = 2;

    public void clearHistoryUnaryOperations () {
        historyUnaryOperations = emptyString;
    }

    public void clearNegateHistory () {
        negateHistory = emptyString;
    }

    private String historyUnaryOperations = "";
    private String negateHistory = "";
    private HashMap<OperationsEnum, String> operationSymbols = new HashMap<>();
    private String emptyString = "";
    private String separatorNumber = " ";
    private CalculatorController calculatorController = new CalculatorController();

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

    ArrayList historyArrayG = new ArrayList();

    CalculatorHistory (Calculator calculator) {
        this.calculator = calculator;
        this.history = calculator.getHistory();
    }

    public String printHistory () {
        historyArrayG = calculator.getHistory().getListHistory();

        if (!calculator.getHistory().getListHistory().isEmpty()) {
            Object o = calculator.getHistory().getLast();
            int index = calculator.getHistory().getListHistory().size() - 1;

            if (o instanceof OperationsEnum) {
                OperationsEnum operation = (OperationsEnum) o;
                if (isBinary(operation)) {
//                    try {
                    changeBinaryOperationHistory(operation, index);
//                    } catch (ParseException e) {
//                        calculatorController.printError(e);
//                    }
                }
                if (isUnary(operation)) {
                    changeUnaryOperationHistory(operation, index);

                }
                if (operation.equals(OperationsEnum.PERCENT)) {
                    changePercentOperationHistory();
                }

                if (operation.equals(OperationsEnum.NEGATE)) {
                    changeNegateOperationHistory(operation, index);
                }
            }
            if (isNumber(o.toString())) {
                if (historySize() <= 2) {
                    clearHistory();
                } else {
                    addLastHistory(formatterNumberHistory(o.toString()));
                    deletePreviousHistoryObjects();
                }
            }
        }
        return calculator.getHistory().getStringHistory();
    }

    void deletePreviousHistoryObjects () {
        if (historySize() >= minObjectsAmount) {
            for (int i = historySize() - minObjectsAmount; i >= 0; i--) {
                Object prePreviousObject = getHistory(i);
                if (!operationSymbols.containsValue(prePreviousObject.toString())) {
                    deleteHistory(i);
                } else {
                    break;
                }
            }
        }
    }

    void changeNegateOperationHistory (OperationsEnum operation, int index) {
        if (historySize() > 1) {
            int previousIndex = index - 1;
            Object previousObject = getHistory(previousIndex);
            deleteLastHistory();
            deleteLastHistory();

            if (negateHistory.isEmpty()) {
                if (historyUnaryOperations.isEmpty()) {
                    if (isNumber(previousObject.toString())) {
                        BigDecimal number = (BigDecimal) previousObject;
                        negateHistory = formatterNumberHistory(number.negate().toString());
                    } else {
                        negateHistory = previousObject.toString();
                    }
                } else {
                    negateHistory = historyUnaryOperations;
                }
            }else{
                deleteLastHistory();
            }

            negateHistory = wrapOperationInBrackets(operation, negateHistory);
            addLastHistory(negateHistory);
            deletePreviousHistoryObjects();
        }
    }

    private String wrapOperationInBrackets (OperationsEnum operation, String text) {
        return operationSymbols.get(operation) + "(" + text + ")";
    }

    void changePercentOperationHistory () {
        deleteLastHistory();
        deleteLastHistory();
        deletePreviousHistoryObjects();

        BigDecimal calculateResult = calculator.getResult();

        addLastHistory(formatterNumberHistory(calculateResult.toString()));
    }

    boolean isNumber (String text) {
        try {
            if (text.contains("e")) {
                FormatterNumber.parseNumber(text);
            } else {
                new BigDecimal(text);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    void changeUnaryOperationHistory (OperationsEnum operation, int index) {
        int previousIndex = index - 1;
        Object previousObject = getHistory(previousIndex);

        deleteLastHistory();
        deleteLastHistory();

        deletePreviousHistoryObjects();
        if (negateHistory.isEmpty()) {
            if (historyUnaryOperations.isEmpty()) {
                if (isNumber(previousObject.toString())) {
                    historyUnaryOperations = formatterNumberHistory(previousObject.toString());
                }
            }
        } else {
            historyUnaryOperations = negateHistory;
            negateHistory = emptyString;
        }

        historyUnaryOperations = wrapOperationInBrackets(operation, historyUnaryOperations);
        addLastHistory(historyUnaryOperations);
    }

    Object getHistory (int index) {
        return calculator.getHistory().getListHistory().get(index);
    }

    boolean isUnary (OperationsEnum operationsEnum) {
        return calculator.isUnary(operationsEnum);
    }

    void changeBinaryOperationHistory (OperationsEnum operationsEnum, int index) {
        deleteLastHistory();
        if (historySize() >= 1) {
            Object previousObject = getHistory(index - 1);
            for (int i = historySize() - 1; i >= 0; i--) {
                Object prePreviousObject = getHistory(i);
                if (operationSymbols.containsValue(prePreviousObject.toString())) {
                    deleteLastHistory();
                } else {
                    break;
                }
            }
            if (historyUnaryOperations.isEmpty()) {
                if (isNumber(previousObject.toString())) {
                    deleteLastHistory();
                    addLastHistory(formatterNumberHistory(previousObject.toString()));
                }
            } else {
                historyUnaryOperations = emptyString;
            }
        }

        addLastHistory(operationSymbols.get(operationsEnum));
//        deletePreviousHistoryObjects();
    }

    public int historySize () {
        return calculator.getHistory().getListHistory().size();
    }

    void addLastHistory (String s) {
        calculator.getHistory().addOperationString(s);
    }

    void deleteLastHistory () {
        calculator.getHistory().deleteLast();
    }

    void deleteHistory (int index) {
        calculator.getHistory().getListHistory().remove(index);
    }

    void addHistory (int index, Object object) {
        calculator.getHistory().getListHistory().add(index, object);
    }

    void addHistoryNumber (BigDecimal number) {
        calculator.getHistory().addNumber(number);
    }

    void changeNumberHistory (Object object, int index) {
        int previousIndex = index - 1;
        Object previousObject = calculator.getHistory().getListHistory().get(previousIndex);

        if (calculator.getHistory().getListHistory().size() > index) {
            if (previousObject.equals(OperationsEnum.PERCENT)) {
                changePercentOperationHistory();
            }
        } else {
            deleteHistory(index);
            addHistory(index, object.toString());
        }
    }


    boolean isBinary (OperationsEnum operationsEnum) {
        return calculator.isBinary(operationsEnum);
    }


    /*
     * Method formatters number for print to history operation
     */
    String formatterNumberHistory (String text) {
        BigDecimal number = null;

        if (text.contains("e")) {
            try {
                number = FormatterNumber.parseNumber(text);
            } catch (ParseException e) {
                calculatorController.printError(e);
            }
        } else {
            number = new BigDecimal(text.replace(",", "."));
        }

        return Text.deleteNumberSeparator(FormatterNumber.numberFormatter(number), separatorNumber);
    }

    public String clearHistory () {
        historyArrayG.clear();
        calculator.clearHistory();
        negateHistory = emptyString;
        historyUnaryOperations = emptyString;
        return emptyString;
    }
}



