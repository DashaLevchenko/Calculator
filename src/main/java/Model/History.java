package Model;

import java.math.BigDecimal;
import java.util.ArrayList;

public class History {

    private ArrayList<Object> history = new ArrayList<>();


    /**
     * This method adds number to {@code history}
     * @param number Number need to add to history
     */
    public void addNumber (BigDecimal number) {
        history.add(number);
    }

    /**
     * This method adds operation to {@code history}.
     * @param operation Operation need to add to history.
     */
    public void addOperation (OperationsEnum operation) {
        if (history.size() > 0) {
            deletePreviousOperation(operation);
        }
        history.add(operation);
    }

//    /**
//     * This method adds string to {@code history}.
//     * @param historyString String need to add to history.
//     */
//    public void addString (String historyString) {
//        history.add(historyString);
//    }

    /*
     * Method checks last history object.
     * If last history object is operation, methods deletes last history.
     */
    private void deletePreviousOperation (OperationsEnum operation) {
        if (history.size() > 0) {
            if (getLast() instanceof OperationsEnum) {
                OperationsEnum lastOperation = (OperationsEnum) getLast();

                boolean isBinaryLast = isBinary(lastOperation);
                boolean isBinaryNew = isBinary(operation);

                if (isBinaryLast && isBinaryNew) {
                    deleteLast();
                }
            }
        }

    }

    private boolean isBinary (OperationsEnum operation) {
        return operation.equals(OperationsEnum.ADD) || operation.equals(OperationsEnum.SUBTRACT) ||
                operation.equals(OperationsEnum.MULTIPLY) || operation.equals(OperationsEnum.DIVIDE);
    }

    /**
     * Method deletes last history object.
     */
    public void deleteLast () {
        if (history.size() > 0) {
            history.remove(history.size() - 1);
        }
    }

    /**
     * Method deletes history object by {@code index}.
     * @param index Index of history object need to delete.
     */
    public void delete (int index) {
        if (history.size() > index) {
            history.remove(index);
        }
    }

    /**
     * Method clears history.
     */
    public void clear () {
        history.clear();
    }

    /**
     * Method returns number history objects in history.
     * @return Number history objects in history.
     */
    public int size () {
        return history.size();
    }

    /**
     * Method returns last history objects.
     * @return Last history objects or null if history is empty.
     */
    public Object getLast () {
        Object lastObject = null;
        if (history.size() > 0) {
            lastObject = history.get(history.size() - 1);
        }
        return lastObject;
    }

    private String separatorHistory = " ";
    /**
     * Method returns string of history with separator.
     * @return History was separated.
     */
    public String getStringHistory () {
        String stringHistory = "";
        for (Object o : history) {
            stringHistory = stringHistory.concat(o.toString());
            stringHistory = stringHistory.concat(separatorHistory);
        }
        return stringHistory;
    }

    /**
     * Method returns history object by index.
     * @param index Index of history object need to return.
     * @return History object.
     */
    public Object get (int index) {
        return history.get(index);
    }
}
