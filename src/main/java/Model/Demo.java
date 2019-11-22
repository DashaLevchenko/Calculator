package Model;

import java.math.BigDecimal;

public class Demo {

    public static void main (String[] args) {
        //"√((5+3)/2-1)+4"
        Calculator calculator = new Calculator(BigDecimal.valueOf(5), BigDecimal.valueOf(3));

        try {
            calculator.calculator(OperationsEnum.ADD);
            calculator.setNumberSecond(BigDecimal.valueOf(2));
            calculator.calculator(OperationsEnum.DIVIDE);
            calculator.setNumberSecond(BigDecimal.valueOf(1));
            calculator.calculator(OperationsEnum.SUBTRACT);
            calculator.setNumberSecond(null);
            calculator.calculator(OperationsEnum.SQRT);
            calculator.setNumberSecond(BigDecimal.valueOf(4));
            calculator.calculator(OperationsEnum.ADD);

            System.out.println("Ответ: " + calculator.getResult());
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

    }
}
