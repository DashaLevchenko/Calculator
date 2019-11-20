package Model;

import java.math.BigDecimal;

public class Calculator {

    private BigDecimal numberFirst = null;
    private BigDecimal numberSecond = null;
    private BigDecimal result;


    private Binary binary = new Binary();
    private Unary unary = new Unary();

    Calculator (BigDecimal numberFirst, BigDecimal numberSecond) {
        setNumberFirst(numberFirst);
        setNumberSecond(numberSecond);
    }

    Calculator (BigDecimal numberFirst) {
        setNumberFirst(numberFirst);
    }


    public BigDecimal calculator (OperationsEnum operation) {
        if (numberFirst != null && numberSecond != null && isBinary(operation)) {
            binary.setNumberFirst(numberFirst);
            binary.setNumberSecond(numberSecond);
            binary.calculateBinary(operation);
            result = binary.getResult();
        } else if (numberFirst != null && isUnary(operation)) {
            unary.setNumber(numberFirst);
            unary.calculateUnary(operation);
            result = unary.getResult();
        }
        setNumberFirst(result);
        return result;
    }

    private boolean isBinary (OperationsEnum operation) {
        if (operation.equals(OperationsEnum.ADD) || operation.equals(OperationsEnum.SUBTRACT) ||
                operation.equals(OperationsEnum.MULTIPLY) || operation.equals(OperationsEnum.DIVIDE) || operation.equals(OperationsEnum.PERCENT)) {
           return true;
        } else {
            return false;
        }
    }

    private boolean isUnary (OperationsEnum operation) {
        if (operation.equals(OperationsEnum.SQRT) || operation.equals(OperationsEnum.SQR) ||
                operation.equals(OperationsEnum.ONE_DIVIDE_X) || operation.equals(OperationsEnum.PERCENT)) {
           return true;
        } else {
            return false;
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
