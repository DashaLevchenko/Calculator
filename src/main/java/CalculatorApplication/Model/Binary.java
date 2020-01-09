package CalculatorApplication.Model;

import CalculatorApplication.Model.Exceptions.DivideZeroException;
import CalculatorApplication.Model.Exceptions.ResultUndefinedException;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;


/**
 * This class calculates binary operations,
 * like divide, add, subtract, multiply.
 */
class Binary {
    /** Max default rounding */
    private final RoundingMode DEFAULT_ROUNDING = RoundingMode.UP;

    /** Variable keeps value of first number */
    private BigDecimal numberFirst;

    /** Variable keeps value of second number */
    private BigDecimal numberSecond;

    /** Variable keeps value of operation */
    private OperationsEnum operation;

    /** Variable keeps value of result */
    private BigDecimal result;

    public void setOperation (OperationsEnum operation) {
        this.operation = operation;
    }

    /** This method sums two number */
    private void add () {
        result = numberFirst.add(numberSecond);
    }

    /** This method calculates the difference of two number */
    private void subtract () {
        result = numberFirst.subtract(numberSecond);
    }

    /** This method multiplies two number */
    private void multiply () {
        result = numberFirst.multiply(numberSecond);
    }

    /**
     * Method divides two numbers
     *
     * @throws ResultUndefinedException If two numbers equal zero
     * @throws DivideZeroException      If divide by zero
     */
    private void divide () throws ResultUndefinedException, DivideZeroException {
        if (compareZero(numberSecond) == 0) {
            if (compareZero(numberFirst) == 0) {
                throw new ResultUndefinedException();
            }
            throw new DivideZeroException();
        }

        int defaultScale = 10000;
        result = numberFirst.divide(numberSecond, defaultScale, DEFAULT_ROUNDING);
    }

    private int compareZero (BigDecimal number) {
        number = number.setScale(0, RoundingMode.UP);
        return number.compareTo(BigDecimal.ZERO);
    }

    /** This method calculate percent {@code numberSecond} of number {@code numberFirst}. */
    private void percent () {
        BigDecimal oneHundred = BigDecimal.valueOf(100);
        BigDecimal numberSecondDivideHundred = numberSecond.divide(oneHundred, MathContext.DECIMAL128);
        result = numberFirst.multiply(numberSecondDivideHundred);
    }

    /**
     * Method calculates binary operations
     *
     * @throws NullPointerException     If operation equals null
     * @throws DivideZeroException      If divide by zero
     * @throws ResultUndefinedException If zero divide by zero
     */
    public void calculateBinary () throws ResultUndefinedException, DivideZeroException {
        if (numberFirst != null && numberSecond != null) {
            if (operation == null) {
                throw new NullPointerException("Enter operation");
            }
            if (operation.equals(OperationsEnum.ADD)) {
                add();
            } else if (operation.equals(OperationsEnum.SUBTRACT)) {
                subtract();
            } else if (operation.equals(OperationsEnum.MULTIPLY)) {
                multiply();
            } else if (operation.equals(OperationsEnum.DIVIDE)) {
                divide();
            } else if (operation.equals(OperationsEnum.PERCENT)) {
                percent();
            } else {
                throw new IllegalArgumentException("Enter binary operation");
            }
            numberFirst = result;
        }
    }

    public void setNumberFirst (BigDecimal numberFirst) {
        this.numberFirst = numberFirst;
    }

    public void setNumberSecond (BigDecimal numberSecond) {
        this.numberSecond = numberSecond;
    }

    public BigDecimal getResult () {
        return result;
    }
}
