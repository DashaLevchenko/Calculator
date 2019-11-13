package Model;

import java.math.BigDecimal;
import java.math.MathContext;


public class Binary {
//    private final MathContext mathContext = new MathContext(20000);
    private final MathContext mathContext = MathContext.DECIMAL128;
//    private final MathContext mathContext = new MathContext(34, RoundingMode.DOWN);

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

    private BigDecimal numberFirst = null;
    private BigDecimal numberSecond = null;
    private BigDecimal result = null;

    public void setResult(BigDecimal result) {
        this.result = result;
    }

    public BigDecimal getResult() {
        return result;
    }

    public void add() {
        int scale = numberFirst.add(numberSecond).precision();
        result = numberFirst.add(numberSecond).round(new MathContext(scale));
    }

    public void subtract() {
        result = numberFirst.subtract(numberSecond).round(mathContext);
    }

    public void multiply() {
        result = numberFirst.multiply(numberSecond).round(mathContext);
    }

    public void divide() throws ArithmeticException {
        if (numberFirst.equals(BigDecimal.ZERO) && numberSecond.equals(BigDecimal.ZERO)) {
            throw new ArithmeticException("Result is undefined");
        } else if (numberSecond.equals(BigDecimal.ZERO)) {
            throw new ArithmeticException("Cannot divide by zero");
        } else {
            result = numberFirst.divide(numberSecond, mathContext);
        }
    }

    public void percent(BigDecimal number){
        result = number.divide(BigDecimal.valueOf(100), mathContext);
        numberSecond = result;
    }

    public void percent(BigDecimal number, BigDecimal percent){
        result = number.multiply(percent.divide(BigDecimal.valueOf(100), mathContext), mathContext);
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
            System.out.println(result);
            numberFirst = result;
        }
    }
}
