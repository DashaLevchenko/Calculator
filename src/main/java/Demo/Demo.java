package Demo;

import Model.Calculator;

import java.math.BigDecimal;


/**
 * This class demonstrations calculator model.
 */
public class Demo {


    public static void main (String[] args) {
        try {
            BigDecimal result = Calculator.calculator(0,0,0,0,0,0,0);
            System.out.println("Result: " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
