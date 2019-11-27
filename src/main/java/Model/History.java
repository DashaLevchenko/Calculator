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

    public void addOperationString (String historyString) {
            history.add(historyString);
    }

    private void needChangeLastOperation (OperationsEnum operation) {


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


    public String getStringHistory () {
        String stringHistory = "";
        for (Object o : history) {
            stringHistory = stringHistory.concat(o.toString()).concat(" ");
        }
        return stringHistory;
    }

    public ArrayList getListHistory (){
        return history;
    }



}
