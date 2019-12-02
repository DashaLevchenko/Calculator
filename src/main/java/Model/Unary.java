package Model;

import Model.Exceptions.DivideZeroException;
import Model.Exceptions.InvalidInputException;
import Model.Exceptions.OperationException;

import java.math.BigDecimal;
import java.math.MathContext;

public class Unary {
    private BigDecimal number;
    private BigDecimal result;

    public void setNumber(BigDecimal number) {
        this.number = number;
    }

    public BigDecimal getResult() {
        return result;
    }

    public void setResult(BigDecimal result) {
        this.result = result;
    }

    /**
     * Method calculates square root
     * @throws IllegalArgumentException If number need to calculate square root is negative
     */
    private void squareRoot() throws InvalidInputException {
        if (number.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidInputException("Invalid input");
        } else {
            result = number.sqrt(MathContext.DECIMAL128);
        }
    }

    private void xSquare() {
        result = number.pow(2).round(MathContext.DECIMAL128);
    }

    /**
     * Method divides one to x
     * @throws ArithmeticException If divide by zero
     */
    private void oneDivideX() throws DivideZeroException {
        try {
            result = BigDecimal.ONE.divide(number, MathContext.DECIMAL128);
        } catch (ArithmeticException e) {
            throw new DivideZeroException("Cannot divide by zero");
        }
    }

    private void percent() {
        result = BigDecimal.ZERO;
    }

    /**
     * Method calculates unary operations
     * @param operation Operation need to calculate
     * @throws NullPointerException If operation equals null
     * @throws ArithmeticException If divide by zero
     * @throws IllegalArgumentException If operation not equals unary operation
     */
    public void calculateUnary(OperationsEnum operation) throws InvalidInputException, DivideZeroException, OperationException {
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
        }else if (operation.equals(OperationsEnum.NEGATE)) {
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
