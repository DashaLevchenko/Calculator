package Model;

import java.math.BigDecimal;
import java.math.RoundingMode;


public class Arithmetic {

    public static BigDecimal sum(BigDecimal x, BigDecimal y) {
        return scaleForBigdecimal(x.add(y).stripTrailingZeros());
    }

    public static BigDecimal minus(BigDecimal x, BigDecimal y) {
        return scaleForBigdecimal(x.subtract(y).stripTrailingZeros());
    }

    public static BigDecimal multiply(BigDecimal x, BigDecimal y) {
        return scaleForBigdecimal(x.multiply(y).stripTrailingZeros());
    }

    public static BigDecimal divide(BigDecimal x, BigDecimal y) throws ArithmeticException {
        try {
            return scaleForBigdecimal(x.divide(y, 16, RoundingMode.HALF_UP).stripTrailingZeros());
        } catch (ArithmeticException e) {
            throw new ArithmeticException("Деление на ноль невозможно");
        }
    }

    public static BigDecimal scaleForBigdecimal(BigDecimal numberDouble){

        if (numberDouble.scale() < 0){
            numberDouble = numberDouble.setScale(0);
        }
        return numberDouble;
    }

}
