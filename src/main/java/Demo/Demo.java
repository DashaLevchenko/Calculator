package Demo;

import Model.Exceptions.DivideZeroException;
import Model.Exceptions.InvalidInputException;
import Model.Exceptions.OperationException;
import Model.Exceptions.ResultUndefinedException;
import Model.Model;
import Model.OperationsEnum;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

public class Demo {
    //    private static Calculator calculator;
    private static Model calculator = new Model();
    private static HashMap<String, OperationsEnum> operations = new HashMap<>();

    static {
        operations.put("+", OperationsEnum.ADD);
        operations.put("-", OperationsEnum.SUBTRACT);
        operations.put("/", OperationsEnum.DIVIDE);
        operations.put("x", OperationsEnum.MULTIPLY);

        operations.put("√", OperationsEnum.SQRT);
        operations.put("", OperationsEnum.SQR);
        operations.put("", OperationsEnum.ONE_DIVIDE_X);
        operations.put("%", OperationsEnum.PERCENT);

        operations.put("", OperationsEnum.NEGATE);
    }

    public static void main (String[] args) {
//        List<Object> = List<OoperationsEnum, BigDecimal>


        ArrayList<Object> formula = new ArrayList<>();

        formula.add(BigDecimal.valueOf(5));
        formula.add(OperationsEnum.ADD);
        formula.add(OperationsEnum.SUBTRACT);
//        formula.add(OperationsEnum.EQUAL);

//        formula.add(BigDecimal.valueOf(3));
//        formula.add(OperationsEnum.DIVIDE);
//        formula.add(BigDecimal.valueOf(2));
//        formula.add(OperationsEnum.SUBTRACT);
//        formula.add(BigDecimal.valueOf(1));
//        formula.add(OperationsEnum.EQUAL);
//        formula.add(OperationsEnum.SQRT);
//        formula.add(OperationsEnum.ADD);
//        formula.add(BigDecimal.valueOf(4));
//        formula.add(OperationsEnum.EQUAL);

        try {
            BigDecimal result = calculator.calculator(formula);
            String p = calculator.getHistory().getHistory(" ");
            System.out.println(p);
            System.out.println("Result: " + result);
        } catch (OperationException e) {
            e.printStackTrace();
        } catch (DivideZeroException e) {
            e.printStackTrace();
        } catch (ResultUndefinedException e) {
            e.printStackTrace();
        } catch (InvalidInputException e) {
            e.printStackTrace();
        }


//        try {
////            System.out.println("Ответ: " + calculate(formula));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }


}
