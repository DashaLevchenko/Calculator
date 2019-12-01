package Model;

import java.math.BigDecimal;
import java.util.HashMap;

public class E {
    private static Calculator calculator = new Calculator();
    static HashMap<OperationsEnum, String> operationSymbols = new HashMap<>();
//    static {
//        operationSymbols.put(OperationsEnum.ADD, "+");
//        operationSymbols.put(OperationsEnum.SUBTRACT, "-");
//        operationSymbols.put(OperationsEnum.DIVIDE, "÷");
//        operationSymbols.put(OperationsEnum.MULTIPLY, "x");
//
//        operationSymbols.put(OperationsEnum.SQRT, "√");
//        operationSymbols.put(OperationsEnum.SQR, "sqr");
//        operationSymbols.put(OperationsEnum.ONE_DIVIDE_X, "1/");
//        operationSymbols.put(OperationsEnum.PERCENT, "");
//
//        operationSymbols.put(OperationsEnum.NEGATE, "negate");
//    }
    public static void main (String[] args) {
        //"√((5+3)/2-1)+4"
        try {
            calculator.setNumberFirst(BigDecimal.valueOf(2));
            calculator.setOperation(OperationsEnum.ADD);
            calculator.setNumberSecond(BigDecimal.valueOf(3));
            calculator.setOperation(OperationsEnum.SQRT);
            calculator.calculate();
            calculator.setOperation(OperationsEnum.SQRT);

            calculator.calculate();




//            calculator.calculate(OperationsEnum.ADD);

//            calculator.calculate(OperationsEnum.DIVIDE);

//            calculator.calculate(OperationsEnum.SUBTRACT);

//            calculator.setNumberSecond(null);
//            calculator.calculate(OperationsEnum.SQRT);


//            calculator.calculate(OperationsEnum.ADD);
//            calculator.calculate();
            System.out.println(calculator.getHistory().getStringHistory());
            System.out.println("Ответ: " + calculator.getResult());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

//    public static String stringHistory (){
//        ArrayList historyArray = calculator.getHistory().getListHistory();
//       for(Object object : historyArray){
//           if(object instanceof Number){
//               int index = historyArray.indexOf(object);
//               historyArray.remove(index);
////               historyArray.add(index, );
//           }
//       }
////        String history = "";
////        for (Object o : historyArray) {
//////            if(o instanceof OperationsEnum){
//////                history = history.concat(operationSymbols.get(o)).concat(" ");
//////            }else{
////                history = history.concat(o.toString()).concat(" ");
//////            }
////        }
//
//    return history;
//    }
}
