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

    /**
     * Method returns history operation from calculator
     *
     * @return History operation
     */
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

            if (object == null) {
                throw new OperationException("Can not calculate null operation, enter operation or number.");
            }

            clearCalculator(i, object, formula);

            if (object instanceof OperationsEnum) {
                OperationsEnum operationsEnum = (OperationsEnum) object;
                calculateLastBinaryOperation(operationsEnum);
                addEqualHistory(i, formula);
                addPercentResultHistory(operationsEnum);
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

    //Method sets default value to all variables
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

    /* Method adds result of percent calculation to history.
     * If after percent was negate operation
     */
    private static void addPercentResultHistory (OperationsEnum operationsPresent) {
        OperationsEnum operationPrevious = operation;
        if (isNegate(operationsPresent) && isPercent(operationPrevious)) {
            if (result != null) {
                history.addNumber(result);
            }
        }
    }

    /* Method adds result of operations to history.
     * If previous operation was equal, result of calculation adds to history
     */
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

    /* Method calculate previous binary operation,
     * if present operation is binary too.
     */
    private static void calculateLastBinaryOperation (OperationsEnum operationPresent) throws OperationException, DivideZeroException, ResultUndefinedException {
        if (isBinary(operationPresent) && !isEqual(operationPresent)) {
            if (binaryOperation != null) {
                setOperation(binaryOperation);
                history.deleteLast();

                calculateBinaryOperation();

                numberSecond = null;
            }
            previousEqual = false;
        }
    }

    // Method sets default value to some variable, if previous operation was equal.
    private static void clearCalculator (int index, Object objectPresent, ArrayList formula) {
        Object previousObject = getPreviousFormulaObject(index, formula);

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

    // Method returns previous object from formula.
    private static Object getPreviousFormulaObject (int index, ArrayList formula) {
        int nextIndex = index - 1;
        Object previousObject = null;

        if (nextIndex >= 0) {
            previousObject = formula.get(nextIndex);
        }

        return previousObject;
    }

    // Method sets value for calculator number and adds it to history
    private static void setNumber (BigDecimal number) {
        if (numberFirst == null) {
            numberFirst = number;
        } else {
            numberSecond = number;
        }

        history.addNumber(number);
    }

    /* Method sets value for calculator operation,
     * sets value binary operation if operation is binary,
     * and adds it to history.
     */
    private static void setOperation (OperationsEnum operationsEnum) {
        if (isBinary(operationsEnum)) {
            binaryOperation = operationsEnum;
        }

        operation = operationsEnum;
        history.addOperation(operation);
    }

    // Method calculate binary operation, if first and second calculator numbers isn't null.
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

    // Method calculates unary operation,
    // sets true value to percent negate if unary operation is negate
    private static void calculateUnaryOperation () throws OperationException, InvalidInputException, DivideZeroException {
        setUnaryNumber();

        unary.setOperation(operation);
        unary.calculateUnary();
        result = unary.getResult();

        getUnaryResult();

        if (operation.equals(OperationsEnum.NEGATE)) {
            percentNegate = true;
        }
    }

    // Method chooses which number must be calculate with unary operation,
    // sets it to unary object.
    private static void setUnaryNumber () {
        BigDecimal number;
        if (numberSecond == null || previousEqual) {
            number = numberFirst;
        } else {
            number = numberSecond;
        }

        unary.setNumber(number);
    }

    /* Chooses which calculator variable must keep result of unary operation,
     * and sets result.
     */
    private static void getUnaryResult () {

        if (numberSecond == null && binaryOperation == null || previousEqual) {
            numberFirst = result;
        } else {
            numberSecond = result;
        }
    }


    /**
     * This method returns true if operation was binary,
     * and returns false if operation was not binary
     *
     * @param operation Operation which need to check
     * @return Returns true if operation was binary, and returns false if operation was not binary
     */
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

    /* Method decides can calculator calculate operation or not.
     * If present operation is binary operation or next operation not unary method returns true.
     */
    private static boolean canCalculate (int index, ArrayList formula) {
        boolean canCalculate = false;

        int nextIndex = index + 1;
        Object nextObject = null;

        int numberObjectsFormula = formula.size() - 1;
        if (numberObjectsFormula >= nextIndex) {
            nextObject = formula.get(nextIndex);
        }

        if (!nextOperationUnary(nextObject) || !isBinary(operation)) {
            canCalculate = true;
        }

        return canCalculate;
    }

    /* Method returns true if next object is unary operation,
     * and false if next object is null or isn't unary operation.
     */
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

    // Method chooses which operation need to calculate
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

    // Method adds result of calculation to history
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

    // Method calculates percent operation
    private static void calculatePercent () throws OperationException, InvalidInputException, DivideZeroException, ResultUndefinedException {
        if (binaryOperation == null) {
            calculateUnaryOperation();
            history.deleteLast();

        } else {
            BigDecimal percent = setPercentBinaryNumber();

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

    /* Method returns percent which need to calculate
    * and sets number in binary object for calculate percent.
    * If binary operation is divide or multiply, need to calculate one percent from number.
     */
    private static BigDecimal setPercentBinaryNumber () {
        BigDecimal percent;
        boolean binaryOperationDivide = binaryOperation.equals(OperationsEnum.DIVIDE);
        boolean binaryOperationMultiply = binaryOperation.equals(OperationsEnum.MULTIPLY);

        if (binaryOperationDivide || binaryOperationMultiply) {
            BigDecimal number;
            if (numberSecond != null) {
                number = numberSecond;
            } else {
                number = numberFirst;
            }

            binary.setNumberFirst(number);
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
        return percent;
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

    // Method calculates binary operation if present object from formula is equal
    // and binary operation was in formula. Also method cleans history after calculating binary operation.
    private static void calculateEqual () throws OperationException, DivideZeroException, ResultUndefinedException {
        if (binaryOperation != null) {
            setNumberEqual();
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

    // Sets second number if it is null
    private static void setNumberEqual () {
        if (numberSecond == null) {
            if (result == null) {
                numberSecond = numberFirst;
            } else {
                numberSecond = result;
            }
        }
    }

    private static boolean isEqual (Object operation) {
        boolean isEqual = false;

        if (operation instanceof OperationsEnum) {
            OperationsEnum operationsEnum = (OperationsEnum) operation;
            isEqual = operationsEnum.equals(OperationsEnum.EQUAL);
        }

        return isEqual;
    }

    /**
     * This method returns true if operation was unary,
     * and returns false if operation was not unary.
     *
     * @param operation Operation which need to check
     * @return Returns true if operation was unary, and returns false if operation was not unary.
     */
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
