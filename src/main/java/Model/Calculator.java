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


    public BigDecimal calculator (OperationsEnum operationsEnum) {
        if (numberFirst != null && numberSecond != null) {
            binary.setNumberFirst(numberFirst);
            binary.setNumberSecond(numberSecond);
            binary.calculateBinary(operationsEnum);
            result = binary.getResult();
        } else if (numberFirst != null) {
            unary.setNumber(numberFirst);
            unary.calculateUnary(operationsEnum);
            result = unary.getResult();
        }
        setNumberFirst(result);
        return result;
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
