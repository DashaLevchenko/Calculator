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
    private BigDecimal numberFirst;
    private BigDecimal numberSecond;
    private BigDecimal percent;
    private OperationsEnum operation;

    private Binary binary = new Binary();
    private Unary unary = new Unary();


    private OperationsEnum percentOperation;
    private BigDecimal result;

    /**
     * Method calculates math operation
     * @throws DivideZeroException      If divide by zero
     * @throws ResultUndefinedException If zero divide by zero
     * @throws OperationException       If operation not equals calculator operation
     * @throws InvalidInputException    If square root negative number
     */
    public void calculate () throws DivideZeroException, ResultUndefinedException, OperationException, InvalidInputException {
        if (operation == null && percentOperation == null) {
            throw new OperationException("Enter operation");
        }
        if (percentOperation != null) {
            calculatePercent();
        } else if (isBinary(operation)) {
            calculateBinaryOperation();
        } else if (isUnary(operation)) {
            calculateUnaryOperation();
        }

    }

    //    private void calculatePercent (OperationsEnum operation) throws OperationException, InvalidInputException, DivideZeroException {
    private void calculatePercent () throws OperationException, InvalidInputException, DivideZeroException {
        if (numberFirst != null && numberSecond == null) {
            unary.setNumber(numberFirst);
            unary.setOperation(percentOperation);
            unary.calculateUnary();

            result = unary.getResult();
            numberFirst = result;
        } else {
            if (operation.equals(OperationsEnum.DIVIDE) || operation.equals(OperationsEnum.MULTIPLY)) {
                binary.percent(numberSecond, BigDecimal.valueOf(1));
            } else {
                if (numberFirst != null) {
                    binary.percent(numberFirst, percent);
                }
            }
            result = binary.getResult();
            numberSecond = result;
            percent = result;
        }
    }

    private void calculateUnaryOperation () throws OperationException, InvalidInputException, DivideZeroException {
        if (numberSecond == null) {
            calculateUnary(numberFirst);
            numberFirst = unary.getResult();
            numberSecond = null;
        } else {
            calculateUnary(numberSecond);
            numberSecond = unary.getResult();
        }
    }

    private void calculateUnary (BigDecimal number) throws OperationException, InvalidInputException, DivideZeroException {
        unary.setNumber(number);
        unary.setOperation(operation);
        unary.calculateUnary();
        result = unary.getResult();
    }

    private void calculateBinaryOperation () throws OperationException, DivideZeroException, ResultUndefinedException {
        if (numberFirst != null && numberSecond != null) {
            binary.setNumberFirst(numberFirst);
            binary.setNumberSecond(numberSecond);
            binary.setOperation(operation);
            binary.calculateBinary();

            result = binary.getResult();
            numberFirst = result;
        }
    }

    public boolean isBinary (OperationsEnum operation) {
        return operation.equals(OperationsEnum.ADD) || operation.equals(OperationsEnum.SUBTRACT) ||
                operation.equals(OperationsEnum.MULTIPLY) || operation.equals(OperationsEnum.DIVIDE);
    }

    public boolean isUnary (OperationsEnum operation) {
        return operation.equals(OperationsEnum.SQRT) || operation.equals(OperationsEnum.SQR) ||
                operation.equals(OperationsEnum.ONE_DIVIDE_X) || operation.equals(OperationsEnum.NEGATE);
    }

    private History history = new History();

    public void setNumberFirst (BigDecimal numberFirst) {
        this.numberFirst = numberFirst;
        if (numberFirst != null) {
            history.addNumber(numberFirst);
        }
    }

    public void setNumberSecond (BigDecimal numberSecond) {
        this.numberSecond = numberSecond;
        if (numberSecond != null) {
            history.addNumber(numberSecond);
        }
    }

    public BigDecimal getResult () {
        return result;
    }

    public History getHistory () {
        return history;
    }

    public void setOperation (OperationsEnum operation) {
        this.operation = operation;
        if (operation != null) {
            history.addOperation(operation);
        }
    }

    public void clearCalculator () {
        numberFirst = null;
        numberSecond = null;
        result = null;
        clearHistory();
        operation = null;
        percent = null;
    }

    public void clearHistory () {
        history.clear();
    }

    public OperationsEnum getOperation () {
        return operation;
    }


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

    public void setPercentOperation (OperationsEnum percentOperation) {
        this.percentOperation = percentOperation;
        if (percentOperation != null) {
            history.addOperation(percentOperation);
        }

    }
}
