package Demo;

import Model.CalculateFormula;

import java.math.BigDecimal;

public class Demo {

    public static void main (String[] args) {
        //"√((5+3)/2-1)+4"
        try {
            BigDecimal result = CalculateFormula.calculateFormula("5+3/2-1=√+4=");

            System.out.println("Ответ: " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
