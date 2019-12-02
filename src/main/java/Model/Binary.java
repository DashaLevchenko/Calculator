package Model;

import Model.Exceptions.DivideZeroException;
import Model.Exceptions.OperationException;
import Model.Exceptions.ResultUndefinedException;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;


public class Binary {
    /**
     * Max default scale
     */
    private int defaultScale = 10000;

    /**
     * Max default rounding
     */
    private final RoundingMode defaultRounding = RoundingMode.UP;
    private BigDecimal numberFirst;
    private BigDecimal numberSecond;
    private BigDecimal result;

    public void setNumberFirst (BigDecimal numberFirst) {
        this.numberFirst = numberFirst;
    }

    public void setNumberSecond (BigDecimal numberSecond) {
        this.numberSecond = numberSecond;
    }

    public BigDecimal getResult () {
        return result;
    }

    private void add () {
        result = numberFirst.add(numberSecond);
    }

    private void subtract () {
        result = numberFirst.subtract(numberSecond);
    }

    private void multiply () {
        result = numberFirst.multiply(numberSecond);
    }

    /**
     * Method divides two numbers
     *
     * @throws ResultUndefinedException If two numbers equal zero
     * @throws DivideZeroException If divide by zero
     */
    private void divide () throws ResultUndefinedException, DivideZeroException {
        if (compareZero(numberSecond) == 0) {
            if (compareZero(numberFirst) == 0) {
                throw new ResultUndefinedException("Result is undefined");
            }
            throw new DivideZeroException("Cannot divide by zero");
        }

        result = numberFirst.divide(numberSecond, defaultScale, defaultRounding);
    }

    private int compareZero (BigDecimal number) {
        number = number.setScale(0, RoundingMode.UP);
        return number.compareTo(BigDecimal.ZERO);
    }


    public void percent (BigDecimal number, BigDecimal percent) {
        result = number.multiply(percent.divide(BigDecimal.valueOf(100), MathContext.DECIMAL128));
    }

    /**
     * Method calculates binary operations
     *
     * @param operation Operation need to calculate
     * @throws NullPointerException     If operation equals null
     * @throws OperationException       If operation not equals binary operation
     * @throws DivideZeroException      If divide by zero
     * @throws ResultUndefinedException If zero divide by zero
     */
    public void calculateBinary (OperationsEnum operation) throws ResultUndefinedException, DivideZeroException, OperationException {
        if (operation == null) {
            throw new NullPointerException("Enter operation");
        }
        if (numberFirst != null && numberSecond != null) {
            if (operation.equals(OperationsEnum.ADD)) {
                add();
            } else if (operation.equals(OperationsEnum.SUBTRACT)) {
                subtract();
            } else if (operation.equals(OperationsEnum.MULTIPLY)) {
                multiply();
            } else if (operation.equals(OperationsEnum.DIVIDE)) {
                divide();
            } else {
                throw new OperationException("Enter binary operation");
            }
            numberFirst = result;
        }
    }

}
