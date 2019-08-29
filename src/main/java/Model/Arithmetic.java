package Model;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;


public class Arithmetic {
   private static MathContext mathContext = new MathContext(16);


    public static BigDecimal sum(BigDecimal x, BigDecimal y) {
        return scaleForBigDecimal(x.add(y, mathContext));
    }

    public static BigDecimal minus(BigDecimal x, BigDecimal y) {
        return scaleForBigDecimal(x.subtract(y, mathContext));
    }

    public static BigDecimal multiply(BigDecimal x, BigDecimal y) {
        return scaleForBigDecimal(x.multiply(y, mathContext));
    }

    public static BigDecimal divide(BigDecimal x, BigDecimal y) throws ArithmeticException {
        try {
            return scaleForBigDecimal(x.divide(y, mathContext));
        } catch (ArithmeticException e) {
            throw new ArithmeticException("Cannot divide by zero");
        }
    }
    public static BigDecimal squareRoot(BigDecimal x) throws ArithmeticException {
        try {
            return x.sqrt(new MathContext(16, RoundingMode.HALF_DOWN));
        } catch (ArithmeticException e) {
            throw new ArithmeticException("Invalid input");
        }
    }
    public static BigDecimal xSquare(BigDecimal x) {
        return scaleForBigDecimal(multiply(x, x));
    }

    public static BigDecimal scaleForBigDecimal(BigDecimal numberDouble){
        numberDouble = numberDouble.stripTrailingZeros();
        if (numberDouble.scale() < 0){
            numberDouble = numberDouble.setScale(0);
        }
        return numberDouble;
    }

}
