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
 class Unary {
    private BigDecimal number;
    private MathContext mathContext = MathContext.DECIMAL128;
    private OperationsEnum operation;
    private BigDecimal result;

    protected void setOperation (OperationsEnum operation) {
        this.operation = operation;
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
        result = number.sqrt(mathContext);

    }

    private void xSquare () {
        int power = 2;
        result = number.pow(power).round(mathContext);
    }


    private void oneDivideX () throws DivideZeroException {
        if (compareZero(number) == 0) {
            throw new DivideZeroException("Cannot divide by zero");
        }

        BigDecimal one = BigDecimal.ONE;
        result = one.divide(number, mathContext);
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
    protected void calculateUnary () throws InvalidInputException, DivideZeroException, OperationException {
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
    protected void setNumber (BigDecimal number) {
        this.number = number;
    }

    protected BigDecimal getResult () {
        return result;
    }



}
