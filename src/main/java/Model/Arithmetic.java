package Model;

import java.math.BigDecimal;
import java.math.MathContext;


public class Arithmetic {
    private static final BigDecimal MAX_NUMBER = new BigDecimal("9.999999999999995E9999");
    private static final BigDecimal MIN_NUMBER = new BigDecimal("-9.999999999999995E9999");
    private static final int MAX_SCALE = 10000;

    /**
     * 9
     * Method calculates sum of numbers
     *
     * @param x First parameter
     * @param y Second parameter value to be added to {@code x}.
     * @return Sum of x and y
     */
    public static BigDecimal sum(BigDecimal x, BigDecimal y) {
        return scaleForBigDecimal(x.add(y).round(MathContext.DECIMAL128));
    }

    /**
     * Method calculates difference of numbers
     *
     * @param x First parameter
     * @param y Second parameter value to be subtracted from {@code x}.
     * @return Difference of {@code x} and {@code y}
     */
    static BigDecimal minus(BigDecimal x, BigDecimal y) {
        return scaleForBigDecimal(x.subtract(y));
    }

    public static BigDecimal multiply(BigDecimal x, BigDecimal y) {
        return scaleForBigDecimal(x.multiply(y));
    }

    static BigDecimal divide(BigDecimal x, BigDecimal y) throws ArithmeticException {
        if (x.equals(BigDecimal.ZERO) && y.equals(BigDecimal.ZERO)) {
            throw new ArithmeticException("Result is undefined");
        } else if (y.equals(BigDecimal.ZERO)) {
            throw new ArithmeticException("Cannot divide by zero");
        } else {
            return scaleForBigDecimal(x.divide(y, MathContext.DECIMAL128));
        }
    }

    static BigDecimal squareRoot(BigDecimal x) throws ArithmeticException {
        if(x.compareTo(BigDecimal.ZERO) < 0){
            throw new ArithmeticException("Invalid input");
        }else{
            return x.sqrt(MathContext.DECIMAL128);
        }
    }

    static BigDecimal xSquare(BigDecimal x) {
        return scaleForBigDecimal(x.pow(2).round(MathContext.DECIMAL128));
    }

    static BigDecimal oneDivideX(BigDecimal y) throws ArithmeticException {
        return scaleForBigDecimal(Arithmetic.divide(BigDecimal.ONE, y));
    }

    static BigDecimal percent(BigDecimal x, BigDecimal percent) {
        if(percent.equals(BigDecimal.ZERO)){
            return BigDecimal.ZERO;
        }
        return scaleForBigDecimal(x.multiply(percent.movePointLeft(2), MathContext.DECIMAL128));
    }

    public static BigDecimal negate(BigDecimal x) {
        return scaleForBigDecimal(x.negate());
    }

    public static BigDecimal calculateBinaryOperations(BigDecimal number1, BigDecimal number2, OperationsEnum operation) {
        BigDecimal result = BigDecimal.ZERO;

        if (operation.equals(OperationsEnum.PLUS)) {
            result = sum(number1, number2);
        } else if (operation.equals(OperationsEnum.MINUS)) {
            result = minus(number1, number2);
        } else if (operation.equals(OperationsEnum.MULTIPLY)) {
            result = multiply(number1, number2);
        } else if (operation.equals(OperationsEnum.PERCENT)) {
            result = percent(number1, number2);
        } else if (operation.equals(OperationsEnum.DIVIDE)) {
            result = divide(number1, number2);
        }

        isOverflow(result);
        return result;
    }

    public static BigDecimal calculateUnaryOperations(BigDecimal number, OperationsEnum operation) {
        BigDecimal result = BigDecimal.ZERO;

        if (operation.equals(OperationsEnum.SQRT)) {
            result = squareRoot(number);
        } else if (operation.equals(OperationsEnum.SQR)) {
            result = xSquare(number);
        } else if (operation.equals(OperationsEnum.ONE_DIVIDE_X)) {
            result = oneDivideX(number);
        } else if (operation.equals(OperationsEnum.NEGATE)) {
            result = negate(number);
        }else if (operation.equals(OperationsEnum.PERCENT)) {
            result = percent(number, BigDecimal.ZERO);
        }
        isOverflow(result);
        return result;
    }

    public static BigDecimal scaleForBigDecimal(BigDecimal numberDouble) {
        numberDouble = numberDouble.stripTrailingZeros();
        if (numberDouble.scale() < 0) {
            numberDouble = numberDouble.setScale(0);
        }
        return numberDouble;
    }

    private static void isOverflow(BigDecimal result) {
        if (result.compareTo(MAX_NUMBER) > 0 || result.compareTo(MIN_NUMBER) < 0 || result.scale() > MAX_SCALE) {
            throw new ArithmeticException("Overflow");
        }

    }


}
