package Demo;

import Model.Calculator;
import Model.OperationsEnum;

import java.math.BigDecimal;

public class Demo {
    private static Calculator calculator = new Calculator();

    public static void main (String[] args) {
        //"√((5+3)/2-1)+4"
        try {
            calculator.setNumberFirst(BigDecimal.valueOf(5));
            calculator.setOperation(OperationsEnum.ADD);
            calculator.setNumberSecond(BigDecimal.valueOf(3));
            calculator.calculate();

            calculator.setOperation(OperationsEnum.DIVIDE);
            calculator.setNumberSecond(BigDecimal.valueOf(2));
            calculator.calculate();

            calculator.setOperation(OperationsEnum.SUBTRACT);
            calculator.setNumberSecond(BigDecimal.valueOf(1));
            calculator.calculate();

            calculator.setOperation(OperationsEnum.SQRT);
            calculator.setNumberSecond(null);
            calculator.calculate();

            calculator.setOperation(OperationsEnum.ADD);
            calculator.setNumberSecond(BigDecimal.valueOf(4));
            calculator.calculate();

            BigDecimal result = calculator.getResult();


            System.out.println("Ответ: " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
