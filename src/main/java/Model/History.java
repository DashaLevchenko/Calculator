package Model;

import java.util.ArrayList;

public class History {

    private ArrayList<Object> history = new ArrayList<>();

    public <T extends Number> void addNumber (T number) {
        history.add(number);
    }

    public void addOperation (OperationsEnum operation) {
        if (history.size() > 0) {
            needChangeLastOperation(operation);
            history.add(operation);
        }
    }

    private void needChangeLastOperation (OperationsEnum operation) {
        Boolean isBinaryLast = false;
        boolean isBinaryNew = false;

        if (history.size() > 0) {
            if (history.get(history.size() - 1) instanceof OperationsEnum) {
                OperationsEnum lastOperation = (OperationsEnum) history.get(history.size() - 1);
                isBinaryLast = isBinary(lastOperation);
            }
            isBinaryNew = isBinary(operation);
        }

        if (isBinaryLast.equals(isBinaryNew)) {
            history.remove(history.size() - 1);
        }
    }

    private boolean isBinary (OperationsEnum operation) {
        return operation.equals(OperationsEnum.ADD) || operation.equals(OperationsEnum.SUBTRACT) ||
                operation.equals(OperationsEnum.MULTIPLY) || operation.equals(OperationsEnum.DIVIDE);
    }

    public void deleteLast () {
        if (history.size() > 0) {
            history.remove(history.size() - 1);
        }
    }

    public Object getLast () {
        if (history.size() > 0) {
            return history.get(history.size() - 1);
        } else {
            throw new IndexOutOfBoundsException();
        }
    }


    public String getHistory () {
        String stringHistory = "";
        for (Object o : history) {
            stringHistory = stringHistory.concat(o.toString()).concat(" ");
        }
        return stringHistory;
    }


}
