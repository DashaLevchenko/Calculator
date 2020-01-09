package CalculatorApplication.Model;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * This class keeps calculator's history.
 */
public class History{

    private ArrayList<Object> history = new ArrayList<>();

    /**
     * This method adds number to {@code history}
     * @param number Number need to add to history
     */
    void addNumber (BigDecimal number) {
        history.add(number);
    }

    /**
     * This method adds operation to {@code history}.
     * @param operation Operation need to add to history.
     */
    void addOperation (OperationsEnum operation) {
        if (history.size() > 0) {
            deletePreviousOperation(operation);
        }
        history.add(operation);
    }

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
            int indexLastObject = history.size() - 1;
            history.remove(indexLastObject);
        }
    }


    /**
     * Method clears history.
     */
    void clear () {
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
    private Object getLast () {
        Object lastObject = null;
        if (history.size() > 0) {
            int indexLastObject = history.size() - 1;
            lastObject = history.get(indexLastObject);
        }
        return lastObject;
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
