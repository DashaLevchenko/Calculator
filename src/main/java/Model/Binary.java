package Model;

import Model.Exceptions.DivideZeroException;
import Model.Exceptions.OperationException;
import Model.Exceptions.ResultUndefinedException;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

class Binary {

    private BigDecimal numberFirst;
    private BigDecimal numberSecond;

    protected void setOperation (OperationsEnum operation) {
        this.operation = operation;
    }

    private OperationsEnum operation;
    private BigDecimal result;

    protected void setNumberFirst (BigDecimal numberFirst) {
        this.numberFirst = numberFirst;
    }

    protected void setNumberSecond (BigDecimal numberSecond) {
        this.numberSecond = numberSecond;
    }

    protected BigDecimal getResult () {
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
     * Max default rounding
     */
    private RoundingMode defaultRounding = RoundingMode.UP;

    /**
     * Method divides two numbers
     *
     * @throws ResultUndefinedException If two numbers equal zero
     * @throws DivideZeroException      If divide by zero
     */
    private void divide () throws ResultUndefinedException, DivideZeroException {
        if (compareZero(numberSecond) == 0) {
            if (compareZero(numberFirst) == 0) {
                throw new ResultUndefinedException("Result is undefined");
            }
            throw new DivideZeroException("Cannot divide by zero");
        }

        int defaultScale = 10000;
        result = numberFirst.divide(numberSecond, defaultScale, defaultRounding);
    }

    private int compareZero (BigDecimal number) {
        number = number.setScale(0, RoundingMode.UP);
        return number.compareTo(BigDecimal.ZERO);
    }


    protected void percent () {
        result = numberFirst.multiply(numberSecond.divide(BigDecimal.valueOf(100), MathContext.DECIMAL128));
    }

    /**
     * Method calculates binary operations
     *
     * @throws NullPointerException     If operation equals null
     * @throws OperationException       If operation not equals binary operation
     * @throws DivideZeroException      If divide by zero
     * @throws ResultUndefinedException If zero divide by zero
     */
    protected void calculateBinary () throws ResultUndefinedException, DivideZeroException, OperationException {
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
                throw new OperationException("Enter binary operation");
            }
            numberFirst = result;
        }
    }

}
