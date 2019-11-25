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
            calculator.setNumberSecond(BigDecimal.valueOf(3));

            calculator.calculate(OperationsEnum.ADD);

            calculator.setNumberSecond(BigDecimal.valueOf(2));
            calculator.calculate(OperationsEnum.DIVIDE);

            calculator.setNumberSecond(BigDecimal.valueOf(1));
            calculator.calculate(OperationsEnum.SUBTRACT);

            calculator.setNumberSecond(null);
            calculator.calculate(OperationsEnum.SQRT);

            calculator.setNumberSecond(BigDecimal.valueOf(4));
            calculator.calculate(OperationsEnum.ADD);

            System.out.println("Ответ: " + calculator.getResult());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
