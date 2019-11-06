package Model;

import java.math.BigDecimal;
import java.math.MathContext;


public class Arithmetic {


    /**
     * Method calculates sum of numbers
     *
     * @param x First parameter
     * @param y Second parameter value to be added to {@code x}.
     * @return Sum of x and y
     */
    public static BigDecimal sum(BigDecimal x, BigDecimal y) {
        return x.add(y).round(MathContext.DECIMAL128);
    }

    /**
     * Method calculates difference of numbers
     *
     * @param x First parameter
     * @param y Second parameter value to be subtracted from {@code x}.
     * @return Difference of {@code x} and {@code y}
     */
    public static BigDecimal subtract(BigDecimal x, BigDecimal y) {
        return x.subtract(y).round(MathContext.DECIMAL128);
    }

    public static BigDecimal multiply(BigDecimal x, BigDecimal y) {
        return x.multiply(y).round(MathContext.DECIMAL128);
    }

    static BigDecimal divide(BigDecimal x, BigDecimal y) throws ArithmeticException {
        if (x.equals(BigDecimal.ZERO) && y.equals(BigDecimal.ZERO)) {
            throw new ArithmeticException("Result is undefined");
        } else if (y.equals(BigDecimal.ZERO)) {
            throw new ArithmeticException("Cannot divide by zero");
        } else {
            return x.divide(y, MathContext.DECIMAL128);
        }
    }

    static BigDecimal squareRoot(BigDecimal x) throws IllegalArgumentException {
        if (x.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Invalid input");
        } else {
            return x.sqrt(MathContext.DECIMAL128);
        }
    }

    static BigDecimal xSquare(BigDecimal x) {
        return x.pow(2).round(MathContext.DECIMAL128);
    }

    static BigDecimal oneDivideX(BigDecimal y) throws ArithmeticException {
        return Arithmetic.divide(BigDecimal.ONE, y);
    }

    static BigDecimal percent(BigDecimal x, BigDecimal percent) {
        if (percent.equals(BigDecimal.ZERO)) {
            return BigDecimal.ZERO;
        }
        return x.multiply(percent.divide(BigDecimal.valueOf(100)), MathContext.DECIMAL128);
    }

    public static BigDecimal calculate(BigDecimal number1, BigDecimal number2, OperationsEnum operation) {
        BigDecimal result;
        if (operation == null) {
            throw new NullPointerException("Enter operation: ADD, SUBTRACT, MULTIPLY, PERCENT, DIVIDE");
        }
        if (operation.equals(OperationsEnum.ADD)) {
            result = sum(number1, number2);
        } else if (operation.equals(OperationsEnum.SUBTRACT)) {
            result = subtract(number1, number2);
        } else if (operation.equals(OperationsEnum.MULTIPLY)) {
            result = multiply(number1, number2);
        } else if (operation.equals(OperationsEnum.PERCENT)) {
            result = percent(number1, number2);
        } else if (operation.equals(OperationsEnum.DIVIDE)) {
            result = divide(number1, number2);
        }else{
            throw new IllegalArgumentException("Enter binary operation");
        }

        result = result.round(MathContext.DECIMAL128);

        return result;
    }


    public static BigDecimal calculate(BigDecimal number, OperationsEnum operation) {
        BigDecimal result;
        if (operation == null) {
            throw new NullPointerException("Enter operation: SQRT, SQR, ONE_DIVIDE_X, PERCENT");
        }
            if (operation.equals(OperationsEnum.SQRT)) {
                result = squareRoot(number);
            } else if (operation.equals(OperationsEnum.SQR)) {
                result = xSquare(number);
            } else if (operation.equals(OperationsEnum.ONE_DIVIDE_X)) {
                result = oneDivideX(number);
            } else if (operation.equals(OperationsEnum.PERCENT)) {
                result = percent(number, BigDecimal.ZERO);
            } else {
                throw new IllegalArgumentException("Enter unary operation");
            }
            result = result.round(MathContext.DECIMAL128);

        return result;
    }


}
