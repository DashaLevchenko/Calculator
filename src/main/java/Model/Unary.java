package Model;

import java.math.BigDecimal;
import java.math.MathContext;

public class Unary {
    private BigDecimal number;
    private BigDecimal result;

    public BigDecimal getNumber() {
        return number;
    }

    public void setNumber(BigDecimal number) {
        this.number = number;
    }

    public BigDecimal getResult() {
        return result;
    }

    public void setResult(BigDecimal result) {
        this.result = result;
    }

    private void squareRoot() throws IllegalArgumentException {
        if (number.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Invalid input");
        } else {
            result = number.sqrt(MathContext.DECIMAL128);
        }
    }

    private void xSquare() {
        result = number.pow(2).round(MathContext.DECIMAL128);
    }

    private void oneDivideX() throws ArithmeticException {
        try {
            result = BigDecimal.ONE.divide(number, MathContext.DECIMAL128);
        } catch (ArithmeticException e) {
            throw new ArithmeticException("Cannot divide by zero");
        }
    }

    public void percent() {
        result = BigDecimal.ZERO;
    }

    public void calculateUnary(OperationsEnum operation) throws NullPointerException {
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
        } else {
            throw new IllegalArgumentException("Enter unary operation");
        }
        number = result;


    }

}
