package Demo;

import Model.Calculator;


/**
 * This class demonstrations calculator model.
 */
public class Demo {
    public static void main (String[] args) {
        try {
            System.out.println("Result: " + Calculator.calculator(0, 0, 0, 0));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
