package Model;

import java.math.BigDecimal;

/**
 * Class realizes calculator
 */
public class Calculator {
    private BigDecimal numberFirst = null;
    private BigDecimal numberSecond = null;
    private BigDecimal percent = null;
    private Binary binary = new Binary();
    private Unary unary = new Unary();

    public void setPercent (BigDecimal percent) {
        this.percent = percent;
    }

    public void setResult (BigDecimal result) {
        this.result = result;
    }
    public BigDecimal getNumberFirst () {
        return numberFirst;
    }

    public BigDecimal getNumberSecond () {
        return numberSecond;
    }

    private BigDecimal result;


    /**
     * Constructor sets first and second numbers for calculation binary operation
     * @param numberFirst First number
     * @param numberSecond Second number
     */
    Calculator (BigDecimal numberFirst, BigDecimal numberSecond) {
        setNumberFirst(numberFirst);
        setNumberSecond(numberSecond);
    }

    /**
     * Constructor sets first for calculation unary operation
     * @param numberFirst First number
     */
    public Calculator (BigDecimal numberFirst) {
        setNumberFirst(numberFirst);
    }

    public Calculator () {
    }

    /**
     * Method calculates math operation and return result of calculation
     * @param operation Operation need to calculate
     * @return Result of calculation
     */
    public BigDecimal calculator (OperationsEnum operation)throws NullPointerException, ArithmeticException, IllegalArgumentException {
        if(operation == null){
            throw  new NullPointerException("Enter operation");
        }
            if (isBinary(operation)) {
                if (numberFirst != null && numberSecond != null) {
                    binary.setNumberFirst(numberFirst);
                    binary.setNumberSecond(numberSecond);
                    binary.calculateBinary(operation);
                    result = binary.getResult();
                    numberFirst = result;
                }
            } else if (isUnary(operation)) {
                if (numberSecond == null) {
                    unary.setNumber(numberFirst);
                    unary.calculateUnary(operation);
                    numberFirst = unary.getResult();
                    numberSecond = null;
                } else {
                    unary.setNumber(numberSecond);
                    unary.calculateUnary(operation);
                    numberSecond = unary.getResult();
                }
                result = unary.getResult();
            } else if (isPercent(operation)) {
                if (numberFirst != null && numberSecond == null) {
                    unary.setNumber(numberFirst);
                    unary.calculateUnary(operation);
                    result = unary.getResult();
                    numberFirst = result;
                } else {
                    binary.percent(numberSecond, percent);
                    result = binary.getResult();
                    numberSecond = result;
                }
            }

        return result;
    }

    private boolean isPercent (OperationsEnum operation) {
        return operation.equals(OperationsEnum.PERCENT);

    }

    private boolean isBinary (OperationsEnum operation) {
        return operation.equals(OperationsEnum.ADD) || operation.equals(OperationsEnum.SUBTRACT) ||
                operation.equals(OperationsEnum.MULTIPLY) || operation.equals(OperationsEnum.DIVIDE);
    }

    private boolean isUnary (OperationsEnum operation) {
        return operation.equals(OperationsEnum.SQRT) || operation.equals(OperationsEnum.SQR) ||
                operation.equals(OperationsEnum.ONE_DIVIDE_X);
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
