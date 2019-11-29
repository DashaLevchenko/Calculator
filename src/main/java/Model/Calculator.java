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
    private History history = new History();

    private OperationsEnum percentOperation;

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
        if (percentOperation!=null) {
            history.addOperation(percentOperation);
        }

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
     */
    public void calculate () throws DivideZeroException, ResultUndefinedException, OperationException, InvalidInputException {
        if (operation == null && percentOperation == null) {
            throw new OperationException("Enter operation");
        }
        if (percentOperation != null) {
            calculatePercent();
        }else
        if (isBinary(operation)) {
            calculateBinaryOperation();
        } else if (isUnary(operation)) {
            calculateUnaryOperation();
        }

    }

    //    private void calculatePercent (OperationsEnum operation) throws OperationException, InvalidInputException, DivideZeroException {
    private void calculatePercent () throws OperationException, InvalidInputException, DivideZeroException {
        if (numberFirst != null && numberSecond == null) {
            unary.setNumber(numberFirst);
            unary.calculateUnary(percentOperation);

            result = unary.getResult();
            numberFirst = result;
        } else {
            if (operation.equals(OperationsEnum.DIVIDE) || operation.equals(OperationsEnum.MULTIPLY)) {
                binary.percent(numberSecond, BigDecimal.valueOf(1));
            }else{
                binary.percent(numberFirst, percent);
            }
            result = binary.getResult();
            numberSecond = result;
            percent = result;
        }
    }

    private void calculateUnaryOperation () throws OperationException, InvalidInputException, DivideZeroException {
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
    }

    private void calculateBinaryOperation () throws OperationException, DivideZeroException, ResultUndefinedException {
        if (numberFirst != null && numberSecond != null) {
            binary.setNumberFirst(numberFirst);

            binary.setNumberSecond(numberSecond);
            binary.calculateBinary(operation);

            result = binary.getResult();
            numberFirst = result;
        }
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
        if ( numberFirst != null) {
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
        history.getListHistory().clear();
    }
}
