package Controller;

import Model.OperationsEnum;

import java.math.BigDecimal;

/**
 * This class change calculator history
 */

public class History {
private static OperationsEnum oldOperation;

    /**
     * This method changes old operator if old and new operator are different
     *
     * @param canChangeOperator If parameter equal true old operation in history change,
     *                          else old operation adds to history
     * @param operation New operation
     * @param history String witch keeps history
     * @return String witch keeps history was changed
     */
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

    /**
     * This method deletes last text entry
     * @param canChangeOperator If tis operator is true deletes last operator was added to history
     * @param binaryOperation Symbol of this operation will be deletes
     * @param history String witch keeps history
     * @return String witch keeps history was changed
     */
    public static String deleteLastHistory (boolean canChangeOperator, OperationsEnum binaryOperation, String history) {
        int partToDelete = history.split(" ").length;
        String numberDelete = history.split(" ")[partToDelete - 1];
        int charStart = history.length() - numberDelete.length();

        if (numberDelete.length() > 0) {
            if (!canChangeOperator) {
                int charEnd = history.length();
                if (numberDelete.length() == 1) {
                        history = new StringBuilder(history).deleteCharAt(history.length() - 1).toString();
                } else {
                    if (history.substring(charStart, charEnd).equals(numberDelete)) {
                        history = new StringBuilder(history).delete(charStart, charEnd).toString();
                    }
                }
            } else {
                    history = new StringBuilder(history).delete(history.length() - getSymbol(binaryOperation).length(), history.length()).toString();
            }
        }
        return history;
    }

    /**
     * Method returns string of operation for writing down in history
     * @param operation Operation which need to find match
     * @return String of operation for writing out in history
     */
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

    /**
     * Method return string number was formatted for writing down in history
     * @param number Number witch need to format
     * @param separatorNumber Grouping separator is used for separate number
     * @return String number was formatted without grouping separator
     */
    public static String formatterNumberHistory(BigDecimal number, String separatorNumber){
        return Text.deleteNumberSeparator(NumberFormatter.numberFormatter(number), separatorNumber);
    }

}



