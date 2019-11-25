package Model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

public class E {
    private static Calculator calculator = new Calculator();
    static HashMap<OperationsEnum, String> operationSymbols = new HashMap<>();
    static {
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
    public static void main (String[] args) {
        //"√((5+3)/2-1)+4"
        try {
            calculator.setNumberFirst(BigDecimal.valueOf(5));
            calculator.setOperation(OperationsEnum.ADD);
            calculator.setNumberSecond(BigDecimal.valueOf(3));

            calculator.setOperation(OperationsEnum.DIVIDE);
            calculator.setNumberSecond(BigDecimal.valueOf(2));

            calculator.setOperation(OperationsEnum.SUBTRACT);
            calculator.setNumberSecond(BigDecimal.valueOf(1));

            calculator.setOperation(OperationsEnum.SQRT);
            calculator.setOperation(OperationsEnum.ADD);
            calculator.setNumberSecond(BigDecimal.valueOf(4));

//            calculator.calculate(OperationsEnum.ADD);

//            calculator.calculate(OperationsEnum.DIVIDE);

//            calculator.calculate(OperationsEnum.SUBTRACT);

//            calculator.setNumberSecond(null);
//            calculator.calculate(OperationsEnum.SQRT);


//            calculator.calculate(OperationsEnum.ADD);

//            System.out.println("Ответ: " + calculator.getResult());
            System.out.println(stringHistory());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static String stringHistory (){
        ArrayList historyArray = calculator.getHistory().getListHistory();
        String history = "";
        for (Object o : historyArray) {
            if(o instanceof OperationsEnum){
                history = history.concat(operationSymbols.get(o)).concat(" ");
            }else{
                history = history.concat(o.toString()).concat(" ");
            }
        }

    return history;
    }
}
