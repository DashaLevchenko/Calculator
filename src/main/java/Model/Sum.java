package Model;

import java.math.BigDecimal;
import java.math.MathContext;


public class Sum {

    public static BigDecimal sum(BigDecimal x, BigDecimal y) {
        return scaleForBigdecimal(x.add(y).stripTrailingZeros());
    }

    public static BigDecimal scaleForBigdecimal(BigDecimal numberDouble){

        if (numberDouble.scale() < 0){
            numberDouble = numberDouble.setScale(0);
        }
        return numberDouble;
    }

}
