package Model;

import Model.Exceptions.DivideZeroException;
import Model.Exceptions.InvalidInputException;
import Model.Exceptions.OperationException;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;


/**
 * This class calculates unary operations
 */
public class Unary {
    private BigDecimal number;

    public void setOperation (OperationsEnum operation) {
        this.operation = operation;
    }

    private OperationsEnum operation;
    private BigDecimal result;

    public void setNumber (BigDecimal number) {
        this.number = number;
    }

    public BigDecimal getResult () {
        return result;
    }

    public void setResult (BigDecimal result) {
        this.result = result;
    }

    /**
     * Method calculates square root
     *
     * @throws InvalidInputException If number need to calculate square root is negative
     */
    private void squareRoot () throws InvalidInputException {
        if (compareZero(number) < 0) {
            throw new InvalidInputException("Invalid input");
        }
        result = number.sqrt(MathContext.DECIMAL128);

    }

    private void xSquare () {
        result = number.pow(2).round(MathContext.DECIMAL128);
    }

    /**
     * Method divides one to x
     *
     * @throws DivideZeroException If divide by zero
     */
    private void oneDivideX () throws DivideZeroException {
        if (compareZero(number) == 0) {
            throw new DivideZeroException("Cannot divide by zero");
        }
        result = BigDecimal.ONE.divide(number, MathContext.DECIMAL128);
    }

    private int compareZero (BigDecimal number) {
        number = number.setScale(0, RoundingMode.UP);
        return number.compareTo(BigDecimal.ZERO);
    }

    private void percent () {
        result = BigDecimal.ZERO;
    }

    /**
     * Method calculates unary operations
     *
     * @throws NullPointerException  If operation equals null
     * @throws InvalidInputException If square root negative number
     * @throws DivideZeroException   If divide by zero
     * @throws OperationException    If operation not equals unary operation
     */
    public void calculateUnary () throws InvalidInputException, DivideZeroException, OperationException {
        if (operation == null) {
            throw new NullPointerException("Enter operation");
        }
        if (operation.equals(OperationsEnum.SQRT)) {
            squareRoot();
        } else if (operation.equals(OperationsEnum.SQR)) {
            xSquare();
        } else if (operation.equals(OperationsEnum.ONE_DIVIDE_X)) {
            oneDivideX();
        } else if (operation.equals(OperationsEnum.PERCENT)) {
            percent();
        } else if (operation.equals(OperationsEnum.NEGATE)) {
            negate();
        } else {
            throw new OperationException("Enter unary operation");
        }
        number = result;


    }

    private void negate () {
        result = number.negate();
    }

}
