package Model;

import Model.Exceptions.DivideZeroException;
import Model.Exceptions.InvalidInputException;
import Model.Exceptions.OperationException;
import Model.Exceptions.ResultUndefinedException;

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
    private History history = new History();

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
     *
     * @param numberFirst  First number
     * @param numberSecond Second number
     */
    public Calculator (BigDecimal numberFirst, BigDecimal numberSecond) {
        setNumberFirst(numberFirst);
        setNumberSecond(numberSecond);
    }

    /**
     * Constructor sets first for calculation unary operation
     *
     * @param numberFirst First number
     */
    public Calculator (BigDecimal numberFirst) {
        setNumberFirst(numberFirst);
    }

    public Calculator () {
    }

    /**
     * Method calculates math operation and return result of calculation
     *
     * @param operation Operation need to calculate
     */
    public void calculate (OperationsEnum operation) throws DivideZeroException, ResultUndefinedException, OperationException, InvalidInputException {
        if (operation == null) {
            throw new OperationException("Enter operation");
        }

        if (isBinary(operation)) {
            calculateBinaryOperation(operation);
        } else if (isUnary(operation)) {
            calculateUnaryOperation(operation);
        } else if (isPercent(operation)) {
            calculatePercent(operation);
        }

    }

    private void calculatePercent (OperationsEnum operation) throws OperationException, InvalidInputException, DivideZeroException {
        if (numberFirst != null && numberSecond == null) {
            unary.setNumber(numberFirst);
            unary.calculateUnary(operation);
            history.addOperation(operation);
            result = unary.getResult();
            numberFirst = result;
        } else {
            history.addOperation(operation);
            binary.percent(numberSecond, percent);
            result = binary.getResult();
            numberSecond = result;
        }
    }

    private void calculateUnaryOperation (OperationsEnum operation) throws OperationException, InvalidInputException, DivideZeroException {
        if (numberSecond == null) {
            unary.setNumber(numberFirst);
            unary.calculateUnary(operation);
            history.addOperation(operation);
            numberFirst = unary.getResult();
            numberSecond = null;
        } else {
            unary.setNumber(numberSecond);
            unary.calculateUnary(operation);
            numberSecond = unary.getResult();
        }
        result = unary.getResult();
    }

    private void calculateBinaryOperation (OperationsEnum operation) throws OperationException, DivideZeroException, ResultUndefinedException {
        if (numberFirst != null && numberSecond != null) {
            binary.setNumberFirst(numberFirst);


            binary.setNumberSecond(numberSecond);
            binary.calculateBinary(operation);

            history.addOperation(operation);


            result = binary.getResult();
            numberFirst = result;
        }
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
        if (history.getHistory().isEmpty()) {
            history.addNumber(numberFirst);
        }
    }

    public void setNumberSecond (BigDecimal numberSecond) {
        this.numberSecond = numberSecond;
        history.addNumber(numberSecond);
    }

    public BigDecimal getResult () {
        return result;
    }

    public History getHistory(){
        return history;
    }
}
