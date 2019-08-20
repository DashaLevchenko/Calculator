package Model;

import java.math.BigDecimal;
import java.math.MathContext;


public class Sum {

    public static BigDecimal sum(BigDecimal x, BigDecimal y) {

        return x.add(y).stripTrailingZeros();
    }
}
