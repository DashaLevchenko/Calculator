package Model;

import java.math.BigDecimal;


public class Arithmetic {

    public static BigDecimal sum(BigDecimal x, BigDecimal y) {
        return scaleForBigdecimal(x.add(y).stripTrailingZeros());
    }

    public static BigDecimal minus(BigDecimal x, BigDecimal y) {
        return scaleForBigdecimal(x.subtract(y).stripTrailingZeros());
    }

    public static BigDecimal scaleForBigdecimal(BigDecimal numberDouble){

        if (numberDouble.scale() < 0){
            numberDouble = numberDouble.setScale(0);
        }
        return numberDouble;
    }

}
