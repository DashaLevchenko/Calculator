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
    private boolean percentNegate;
    private boolean previousEqual;
    private Binary binary = new Binary();
    private Unary unary = new Unary();
    private History history = new History();

    public History getHistory () {
        return history;
    }

    public BigDecimal calculator (ArrayList formula) throws OperationException, DivideZeroException, ResultUndefinedException, InvalidInputException {
        for (int i = 0; i < formula.size(); i++) {
            Object object = formula.get(i);
            clearCalculator(i, object, formula);

            if (object instanceof OperationsEnum) {
                OperationsEnum operationsEnum = (OperationsEnum) object;

                try {
                    calculateLastBinaryOperation(operationsEnum);
                } catch (OperationException | DivideZeroException | ResultUndefinedException e) {
                    throw e;
                } finally {
                    addEqualHistory(i, formula);
                    addPercentResultHistory(operationsEnum);

                    setOperation(operationsEnum);
                }
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

    private void addPercentResultHistory (OperationsEnum operationsEnum) {
        if (isNegate(operationsEnum) && isPercent(operation)) {
            if (result != null) {
                history.addNumber(result);
            }
        }
    }

    private void addEqualHistory (int index, ArrayList formula) {
        Object previousObject = getPreviousFormulaObject(index, formula);

        if (previousObject != null) {
            if (previousObject instanceof OperationsEnum) {
                if (isEqual(previousObject)) {
                    if (numberFirst != null) {
                        history.addNumber(numberFirst);
                    }
                }
            }
        }
    }

    private void calculateLastBinaryOperation (OperationsEnum operationsEnum) throws OperationException, DivideZeroException, ResultUndefinedException {
        if (isBinary(operationsEnum) && !isEqual(operationsEnum)) {
            if (binaryOperation != null) {
                setOperation(binaryOperation);
                history.deleteLast();
                calculateBinaryOperation();
                numberSecond = null;
            }
            previousEqual = false;
        }
    }

    private void clearCalculator (int index, Object objectPresent, ArrayList formula) {
        Object previousObject = getPreviousFormulaObject(index, formula);

        if (previousObject != null) {
            if (previousEqual) {
                if (objectPresent instanceof Number) {
                    numberFirst = null;
                }
                if (!isEqual(objectPresent)) {
                    result = null;
                    if (!isNegate(objectPresent)) {
                        numberSecond = null;
                        previousEqual = false;
                        if (!isPercent(objectPresent)) {
                            binaryOperation = null;
                        }
                    }
                    operation = null;
                }
            }
        }
    }

    private Object getPreviousFormulaObject (int index, ArrayList formula) {
        int nextIndex = index - 1;
        Object previousObject = null;
        if (nextIndex >= 0) {
            previousObject = formula.get(nextIndex);
        }
        return previousObject;
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
        if (numberSecond == null || previousEqual) {
            number = numberFirst;
        } else {
            number = numberSecond;
        }

        calculateUnary(number);

        if (numberSecond == null && binaryOperation == null ||previousEqual) {
            numberFirst = result;
        } else {
            numberSecond = result;
        }
        if (operation.equals(OperationsEnum.NEGATE)) {
            percentNegate = true;
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
                addResultHistory();
                calculateUnaryOperation();
            }
            if (isPercent(operation)) {
                calculatePercent();
            }
            if (isEqual(operation)) {
                calculateEqual();
            }
        }
    }

    private void addResultHistory () {
        if (result != null && numberSecond == null) {
            history.deleteLast();
            history.addNumber(result);
            setOperation(operation);

        }
    }

    private boolean isNegate (Object operation) {
        return operation.equals(OperationsEnum.NEGATE);
    }

    private void calculatePercent () throws OperationException, InvalidInputException, DivideZeroException, ResultUndefinedException {
        if (binaryOperation == null) {
            calculateUnaryOperation();
            history.deleteLast();
        } else {
            boolean binaryOperationDivide = binaryOperation.equals(OperationsEnum.DIVIDE);
            boolean binaryOperationMultiply = binaryOperation.equals(OperationsEnum.MULTIPLY);

            BigDecimal percent;
            if (binaryOperationDivide || binaryOperationMultiply) {
                if (numberSecond != null) {
                    binary.setNumberFirst(numberSecond);
                } else {
                    binary.setNumberFirst(numberFirst);
                }
                percent = BigDecimal.ONE;
            } else {
                binary.setNumberFirst(numberFirst);
                if (numberSecond != null) {
                    percent = numberSecond;
                } else {
                    percent = numberFirst;
                }
                percent = percentNegate(percent);
            }

            binary.setNumberSecond(percent);
            binary.setOperation(operation);
            binary.calculateBinary();
            result = binary.getResult();
            numberSecond = result;


            history.deleteLast();
        }
        history.addNumber(result);
        history.addOperation(OperationsEnum.PERCENT);

    }

    private BigDecimal percentNegate (BigDecimal percent) {
        if (percentNegate) {
            percent = percent.negate();
            percentNegate = false;
        }
        return percent;
    }

    private boolean isPercent (Object operation) {
        boolean isPercent = false;
        if (operation != null) {
            isPercent = operation.equals(OperationsEnum.PERCENT);
        }
        return isPercent;
    }

    private void calculateEqual () throws OperationException, DivideZeroException, ResultUndefinedException {
        if (binaryOperation != null) {
            if (numberSecond == null) {
                if (result == null) {
                    numberSecond = numberFirst;
                } else {
                    numberSecond = result;
                }
            }
            operation = binaryOperation;
            try {
                calculateBinaryOperation();
                history.clear();
            } catch (OperationException | DivideZeroException | ResultUndefinedException e) {
                history.deleteLast();
                history.deleteLast();
                throw e;
            }

        }
        percentNegate = false;
        previousEqual = true;
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
