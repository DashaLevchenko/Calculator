package Controller;

import java.math.BigDecimal;

public class e {

    public static void main (String[] args) {
        String k = "0.00000000000000123";
        BigDecimal l = new BigDecimal(k);
        System.out.println(l.precision());
        System.out.println(CalculatorNumberFormatter.formatNumberForPrint(l));
    }
}
