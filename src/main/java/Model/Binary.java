package Model;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;


public class Binary {
    /**
     * Max default scale
     */
    private final int defaultScale = 10000;

    /**
     * Max default rounding
     */
    private final RoundingMode defaultRounding = RoundingMode.UP;
    private BigDecimal numberFirst = null;
    private BigDecimal numberSecond = null;
    private BigDecimal result = null;

    public BigDecimal getNumberFirst() {
        return numberFirst;
    }

    public void setNumberFirst(BigDecimal numberFirst) {
        this.numberFirst = numberFirst;
    }

    public BigDecimal getNumberSecond() {
        return numberSecond;
    }

    public void setNumberSecond(BigDecimal numberSecond) {
        this.numberSecond = numberSecond;
    }


    public void setResult(BigDecimal result) {
        this.result = result;
    }

    public BigDecimal getResult() {
        return result;
    }

    private void add() {
        result = numberFirst.add(numberSecond);
    }

    private void subtract() {
        result = numberFirst.subtract(numberSecond);
    }

    private void multiply() {
        result = numberFirst.multiply(numberSecond);
    }

    private void divide() throws ArithmeticException {
        if (numberFirst.equals(BigDecimal.ZERO) && numberSecond.equals(BigDecimal.ZERO)) {
            throw new ArithmeticException("Result is undefined");
        } else if (numberSecond.equals(BigDecimal.ZERO)) {
            throw new ArithmeticException("Cannot divide by zero");
        } else {
            result = numberFirst.divide(numberSecond, defaultScale, defaultRounding);
        }
    }

    public void percent(BigDecimal number){
        result = number.divide(BigDecimal.valueOf(100), MathContext.DECIMAL128);
        numberSecond = result;
    }

    public void percent(BigDecimal number, BigDecimal percent){
        result = number.multiply(percent.divide(BigDecimal.valueOf(100), MathContext.DECIMAL128));
    }

    public void calculateBinary(OperationsEnum operation) {
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
                throw new IllegalArgumentException("Enter binary operation");
            }
            numberFirst = result;
        }
    }
}
