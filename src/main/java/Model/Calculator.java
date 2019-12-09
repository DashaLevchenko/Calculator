package Model;

import Model.Exceptions.DivideZeroException;
import Model.Exceptions.InvalidInputException;
import Model.Exceptions.OperationException;
import Model.Exceptions.ResultUndefinedException;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Calculator {
    private static BigDecimal numberFirst;
    private static BigDecimal numberSecond;
    private static OperationsEnum operation;
    private static OperationsEnum binaryOperation;
    private static BigDecimal result;

    private static boolean percentNegate;
    private static boolean previousEqual;

    private static Binary binary = new Binary();
    private static Unary unary = new Unary();
    private static History history = new History();

    public static History getHistory () {
        return history;
    }

    /**
     * This method calculates math operations from formula
     *
     * @param formula Formula which need to calculate
     * @return Result of calculate formula
     * @throws DivideZeroException      If divide by zero
     * @throws ResultUndefinedException If zero divide by zero
     * @throws OperationException       If operation not equals calculator operation
     * @throws InvalidInputException    If square root negative number
     */
    public static BigDecimal calculator (ArrayList formula) throws OperationException, DivideZeroException, ResultUndefinedException, InvalidInputException {
        setDefaultValue();
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

    private static void setDefaultValue () {
        numberFirst = null;
        numberSecond = null;
        operation = null;
        binaryOperation = null;
        result = null;

        percentNegate = false;
        previousEqual = false;

        binary = new Binary();
        unary = new Unary();
        history = new History();
    }

    private static void addPercentResultHistory (OperationsEnum operationsEnum) {
        if (isNegate(operationsEnum) && isPercent(operation)) {
            if (result != null) {
                history.addNumber(result);
            }
        }
    }

    private static void addEqualHistory (int index, ArrayList formula) {
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

    private static void calculateLastBinaryOperation (OperationsEnum operationsEnum) throws OperationException, DivideZeroException, ResultUndefinedException {
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

    private static void clearCalculator (int index, Object objectPresent, ArrayList formula) throws OperationException {
        Object previousObject = getPreviousFormulaObject(index, formula);
        if (objectPresent == null) {
            throw new OperationException("Can not calculate null operation, enter operation or number.");
        }

        if (previousObject != null) {
            if (previousEqual) {
                if (objectPresent instanceof BigDecimal) {
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

    private static Object getPreviousFormulaObject (int index, ArrayList formula) {
        int nextIndex = index - 1;
        Object previousObject = null;
        if (nextIndex >= 0) {
            previousObject = formula.get(nextIndex);
        }
        return previousObject;
    }

    private static void setNumber (BigDecimal number) {
        if (numberFirst == null) {
            numberFirst = number;
        } else {
            numberSecond = number;
        }
        history.addNumber(number);
    }

    private static void setOperation (OperationsEnum operationsEnum) {
        if (isBinary(operationsEnum)) {
            binaryOperation = operationsEnum;
        }
        operation = operationsEnum;
        history.addOperation(operation);
    }


    private static void calculateBinaryOperation () throws OperationException, DivideZeroException, ResultUndefinedException {
        if (numberFirst != null && numberSecond != null) {
            binary.setNumberFirst(numberFirst);
            binary.setNumberSecond(numberSecond);
            binary.setOperation(operation);
            binary.calculateBinary();

            result = binary.getResult();
            numberFirst = result;
        }
    }

    private static void calculateUnaryOperation () throws OperationException, InvalidInputException, DivideZeroException {
        BigDecimal number;
        if (numberSecond == null || previousEqual) {
            number = numberFirst;
        } else {
            number = numberSecond;
        }

        calculateUnary(number);

        if (numberSecond == null && binaryOperation == null || previousEqual) {
            numberFirst = result;
        } else {
            numberSecond = result;
        }
        if (operation.equals(OperationsEnum.NEGATE)) {
            percentNegate = true;
        }
    }

    private static void calculateUnary (BigDecimal number) throws OperationException, InvalidInputException, DivideZeroException {
        unary.setNumber(number);
        unary.setOperation(operation);
        unary.calculateUnary();
        result = unary.getResult();
    }

    public static boolean isBinary (OperationsEnum operation) {
        boolean isBinary = false;
        if (operation != null) {
            if (operation.equals(OperationsEnum.ADD) || operation.equals(OperationsEnum.SUBTRACT) ||
                    operation.equals(OperationsEnum.MULTIPLY) || operation.equals(OperationsEnum.DIVIDE)) {
                isBinary = true;
            }
        }

        return isBinary;
    }


    private static boolean canCalculate (int index, ArrayList formula) {
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

    private static boolean nextOperationUnary (Object nextObject) {
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

    private static void calculate () throws OperationException, DivideZeroException, ResultUndefinedException, InvalidInputException {
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

    private static void addResultHistory () {
        if (result != null && numberSecond == null) {
            history.deleteLast();
            history.addNumber(result);
            setOperation(operation);

        }
    }

    private static boolean isNegate (Object operation) {
        return operation.equals(OperationsEnum.NEGATE);
    }

    private static void calculatePercent () throws OperationException, InvalidInputException, DivideZeroException, ResultUndefinedException {
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

    private static BigDecimal percentNegate (BigDecimal percent) {
        if (percentNegate) {
            percent = percent.negate();
            percentNegate = false;
        }
        return percent;
    }

    private static boolean isPercent (Object operation) {
        boolean isPercent = false;
        if (operation != null) {
            isPercent = operation.equals(OperationsEnum.PERCENT);
        }
        return isPercent;
    }

    private static void calculateEqual () throws OperationException, DivideZeroException, ResultUndefinedException {
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

    private static boolean isEqual (Object operation) {
        boolean isEqual = false;

        if (operation instanceof OperationsEnum) {
            OperationsEnum operationsEnum = (OperationsEnum) operation;
            isEqual = operationsEnum.equals(OperationsEnum.EQUAL);
        }

        return isEqual;
    }

    public static boolean isUnary (Object operation) {
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
