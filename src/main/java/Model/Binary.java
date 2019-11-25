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
    private BigDecimal numberFirst = null;
    private BigDecimal numberSecond = null;
    private BigDecimal result = null;

    public BigDecimal getNumberFirst () {
        return numberFirst;
    }

    public void setNumberFirst (BigDecimal numberFirst) {
        this.numberFirst = numberFirst;
    }

    public BigDecimal getNumberSecond () {
        return numberSecond;
    }

    public void setNumberSecond (BigDecimal numberSecond) {
        this.numberSecond = numberSecond;
    }


    public void setResult (BigDecimal result) {
        this.result = result;
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
     * @throws ArithmeticException If divide by zero
     */
    private void divide () throws ResultUndefinedException, DivideZeroException {
        BigDecimal numberFirstWithoutScale = numberFirst.setScale(0, RoundingMode.UP);
        BigDecimal numberSecondWithoutScale = numberSecond.setScale(0, RoundingMode.UP);

        if (numberSecondWithoutScale.equals(BigDecimal.ZERO)) {
            if (numberFirstWithoutScale.equals(BigDecimal.ZERO)) {
                throw new ResultUndefinedException("Result is undefined");
            }
            throw new DivideZeroException("Cannot divide by zero");
        }
        result = numberFirst.divide(numberSecond, defaultScale, defaultRounding);
    }


    public void percent (BigDecimal number, BigDecimal percent) {
        result = number.multiply(percent.divide(BigDecimal.valueOf(100), MathContext.DECIMAL128));
    }

    /**
     * Method calculates binary operations
     *
     * @param operation Operation need to calculate
     * @throws NullPointerException     If operation equals null
     * @throws IllegalArgumentException If operation not equals binary operation
     * @throws ArithmeticException      If divide by zero
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
