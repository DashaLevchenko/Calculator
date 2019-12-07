package Model;

import Model.Exceptions.DivideZeroException;
import Model.Exceptions.InvalidInputException;
import Model.Exceptions.OperationException;
import Model.Exceptions.ResultUndefinedException;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Model {
    private BigDecimal numberFirst;
    private BigDecimal numberSecond;
    private OperationsEnum operation;
    private OperationsEnum binaryOperation;
    private BigDecimal result;
    private Binary binary = new Binary();
    private Unary unary = new Unary();
    private History history = new History();

    public History getHistory () {
        return history;
    }
//(5, ADD, 6)

    public BigDecimal calculator (ArrayList formula) throws OperationException, DivideZeroException, ResultUndefinedException, InvalidInputException {
        history.clear();
        clearCalculator();
        for (int i = 0; i < formula.size(); i++) {
            Object object = formula.get(i);
            clearCalculator(i, object, formula);

            if (object instanceof OperationsEnum) {
                OperationsEnum operationsEnum = (OperationsEnum) object;
                if (isBinary(operationsEnum) && !isEqual(object)) {
                    calculateBinaryOperation();
                    numberSecond = null;
                }
                setOperation(operationsEnum);
            }

            if (object instanceof BigDecimal) {
                BigDecimal number = (BigDecimal) object;
                setNumber(number);
            }


            boolean canCalculate = canCalculate(i, formula);
            if (canCalculate) {
                calculate();
            }
        }
        return result;
    }

    private void clearCalculator (int index, Object objectPresent, ArrayList formula) {
        int nextIndex = index - 1;
        Object previousObject = null;
        if (nextIndex >= 0) {
            previousObject = formula.get(nextIndex);
        }

        if (previousObject != null) {
            if (isEqual(previousObject)) {
                if (objectPresent instanceof Number) {
                    numberFirst = null;
                }
                if (!isEqual(objectPresent)) {
                    numberSecond = null;
                    result = null;
                    if (!isPercent(objectPresent)) {
                        binaryOperation = null;
                        operation = null;
                    }
                }
            }
        }
    }

    public void clearCalculator () {
        numberFirst = null;
        numberSecond = null;
        result = null;
        binaryOperation = null;
        operation = null;
    }


    public void setNumber (BigDecimal number) {
        if (numberFirst == null) {
            numberFirst = number;
        } else {
            numberSecond = number;
        }
        history.addNumber(number);
    }

    public void setOperation (OperationsEnum operation) {
        if (isBinary(operation)) {
            binaryOperation = operation;
        }
        this.operation = operation;
        history.addOperation(operation);
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

    private void calculateUnaryOperation () throws OperationException, InvalidInputException, DivideZeroException {
        BigDecimal number;
        if (numberSecond == null) {
            number = numberFirst;
        } else {
            number = numberSecond;
        }
        calculateUnary(number);

        if (binaryOperation != null) {
            numberSecond = result;
        } else {
            numberFirst = result;
            numberSecond = null;

        }
    }

    private void calculateUnary (BigDecimal number) throws OperationException, InvalidInputException, DivideZeroException {
        unary.setNumber(number);
        unary.setOperation(operation);
        unary.calculateUnary();
        result = unary.getResult();
    }

    public boolean isBinary (OperationsEnum operation) {
        boolean isBinary = false;
        if (operation != null) {
            if (operation.equals(OperationsEnum.ADD) || operation.equals(OperationsEnum.SUBTRACT) ||
                    operation.equals(OperationsEnum.MULTIPLY) || operation.equals(OperationsEnum.DIVIDE)) {
                isBinary = true;
            }
        }

        return isBinary;
    }


    private boolean canCalculate (int index, ArrayList formula) {
        boolean canCalculate = false;

        int nextIndex = index + 1;
        Object nextObject = null;
        if (formula.size() - 1 >= nextIndex) {
            nextObject = formula.get(nextIndex);
        }

        if (!nextOperationUnary(nextObject) || !isBinary(operation)) {
            canCalculate = true;
        }

        return canCalculate;
    }

    private boolean nextOperationUnary (Object nextObject) {
        boolean isUnary = false;

        if (nextObject != null) {
            if (nextObject instanceof OperationsEnum) {
                OperationsEnum nextOperation = (OperationsEnum) nextObject;
                if (isUnary(nextOperation)) {
                    isUnary = true;
                }
            }
        }

        return isUnary;
    }

    private void calculate () throws OperationException, DivideZeroException, ResultUndefinedException, InvalidInputException {
        if (operation != null) {
            if (isUnary(operation)) {
                calculateUnaryOperation();
            }
            if (isEqual(operation)) {
                calculateEqual();
            }
            if (isPercent(operation)) {
                calculatePercent();
            }
        }

    }

    private void calculatePercent () throws OperationException, InvalidInputException, DivideZeroException, ResultUndefinedException {
        if (binaryOperation == null) {
            calculateUnaryOperation();
        } else {
            boolean binaryOperationDivide = binaryOperation.equals(OperationsEnum.DIVIDE);
            boolean binaryOperationMultiply = binaryOperation.equals(OperationsEnum.MULTIPLY);

            BigDecimal percent;
            if (binaryOperationDivide || binaryOperationMultiply) {
                binary.setNumberFirst(numberSecond);
                percent = BigDecimal.ONE;
                binary.setNumberSecond(percent);
            } else {
                binary.setNumberFirst(numberFirst);
                if (numberSecond != null) {
                    percent = numberSecond;
                } else {
                    percent = numberFirst;
                }
            }
            binary.setNumberSecond(percent);
            binary.setOperation(operation);
            binary.calculateBinary();
            result = binary.getResult();
            numberSecond = result;
        }


    }

    private boolean isPercent (Object operation) {
        return operation.equals(OperationsEnum.PERCENT);
    }

    private void calculateEqual () throws OperationException, DivideZeroException, ResultUndefinedException {
        if (binaryOperation != null) {
            if (numberSecond == null) {
                numberSecond = numberFirst;
            }
            operation = binaryOperation;
            calculateBinaryOperation();
        }
    }

    private boolean isEqual (Object operation) {
        boolean isEqual = false;

        if (operation instanceof OperationsEnum) {
            OperationsEnum operationsEnum = (OperationsEnum) operation;
            isEqual = operationsEnum.equals(OperationsEnum.EQUAL);
        }

        return isEqual;
    }

    public boolean isUnary (Object operation) {
        boolean isUnary = false;
        if (operation != null) {
            if (operation.equals(OperationsEnum.SQRT) || operation.equals(OperationsEnum.SQR) ||
                    operation.equals(OperationsEnum.ONE_DIVIDE_X) || operation.equals(OperationsEnum.NEGATE)) {
                isUnary = true;
            }
        }

        return isUnary;
    }


}
