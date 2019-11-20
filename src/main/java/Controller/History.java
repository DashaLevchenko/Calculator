package Controller;

import Model.OperationsEnum;

import java.math.BigDecimal;

public class History {
private static OperationsEnum oldOperation;

    public static String changeOperator(boolean canChangeOperator, OperationsEnum operation, String history) {
        if (!canChangeOperator) {
            oldOperation = operation;
            history += getSymbol(oldOperation);
        } else {
            String lastSymbol = history.split(" ")[history.split(" ").length - 1];
            if (getSymbol(oldOperation).replace(" ", "").equals(lastSymbol)) {
                history = deleteLastHistory(canChangeOperator, operation, history);
                oldOperation = operation;
            }
            history += getSymbol(operation);
        }
        return history;
    }

    public static String deleteLastHistory (boolean canChangeOperator, OperationsEnum binaryOperation, String historyOperations) {
        int partToDelete = historyOperations.split(" ").length;
        String numberDelete = historyOperations.split(" ")[partToDelete - 1];
        int charStart = historyOperations.length() - numberDelete.length();

        if (numberDelete.length() > 0) {
            if (!canChangeOperator) {
                int charEnd = historyOperations.length();
                if (numberDelete.length() == 1) {
                        historyOperations = new StringBuilder(historyOperations).deleteCharAt(historyOperations.length() - 1).toString();
                } else {
                    if (historyOperations.substring(charStart, charEnd).equals(numberDelete)) {
                        historyOperations = new StringBuilder(historyOperations).delete(charStart, charEnd).toString();
                    }
                }
            } else {
                    historyOperations = new StringBuilder(historyOperations).delete(historyOperations.length() - getSymbol(binaryOperation).length(), historyOperations.length()).toString();
            }
        }
        return historyOperations;
    }

    public static String getSymbol (OperationsEnum operation) {
        String symbol;
        if (operation.equals(OperationsEnum.SUBTRACT)) {
            symbol = " - ";
        } else if (operation.equals(OperationsEnum.ADD)) {
            symbol = " + ";
        } else if (operation.equals(OperationsEnum.MULTIPLY)) {
            symbol = " x ";
        } else if (operation.equals(OperationsEnum.DIVIDE)) {
            symbol = " ÷ ";
        } else if (operation.equals(OperationsEnum.PERCENT)) {
            symbol = " ";
        } else if (operation.equals(OperationsEnum.SQRT)) {
            symbol = "√(";
        } else if (operation.equals(OperationsEnum.SQR)) {
            symbol = "sqr(";
        } else if (operation.equals(OperationsEnum.ONE_DIVIDE_X)) {
            symbol = "1/(";
        } else {
            symbol = "";
        }

        return symbol;
    }

    public static String formatterNumberHistory(BigDecimal number, String separatorNumber){
        return Input.deleteNumberSeparator(NumberFormatter.numberFormatter(number), separatorNumber);
    }

}



