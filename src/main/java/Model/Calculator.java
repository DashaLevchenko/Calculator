package Model;

import Model.Exceptions.DivideZeroException;
import Model.Exceptions.InvalidInputException;
import Model.Exceptions.ResultUndefinedException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * This class realizes algorithm of calculator Windows 10
 */
public class Calculator {
//    static boolean deleteNegateHistory;
    /** Variable keeps value of first number */
    private static BigDecimal numberFirst;
    /** Variable keeps value of second number */
    private static BigDecimal numberSecond;
    /** Variable keeps value of operation */
    private static OperationsEnum operation;
    /** Variable keeps value of binary operation */
    private static OperationsEnum binaryOperation;
    /** Variable keeps value of result */
    private static BigDecimal result;
    /** Variable true if result of percent operation must be negate */
    private static boolean percentNegate;
    /** Variable true if previous object was equal operation */
    private static boolean previousEqual;
    /** Variable keeps binary object */
    private static Binary binary = new Binary();
    /** Variable keeps unary object */
    private static Unary unary = new Unary();
    /** Variable keeps history object */
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
     * This method implements calculator.
     *
     * @param objects Object(s) for calculate, as like number, operation or list which keep numbers and operations.
     * @return Result of calculation.
     * @throws DivideZeroException      If divide by zero.
     * @throws ResultUndefinedException If zero divide by zero.
     * @throws NullPointerException     If {@code objects} is null or if {@code objects} isn't operation or number.
     * @throws InvalidInputException    If square root negative number.
     */
    @SafeVarargs
    public static <T> BigDecimal calculator (T... objects) throws DivideZeroException, ResultUndefinedException, InvalidInputException, NullPointerException {
        clearAllCalculator();
        ArrayList formula;

        if (objects == null) {
            throw new NullPointerException();
        }

        if (objects.length > 0) {
            if (objects[0] instanceof Collection) {
                formula = (ArrayList) objects[0];

            } else {
                formula = new ArrayList<>(Arrays.asList(objects));
            }

            calculateFormula(formula);
        }

        if (result == null) {
            if (numberSecond != null) {
                result = numberSecond;
            } else {
                if (numberFirst != null) {
                    result = numberFirst;
                }
            }
        }

        return result;
    }

    /**
     * This method calculates operations from formula.
     *
     * @param formula Formula which need to calculate.
     * @throws DivideZeroException      If divide by zero
     * @throws ResultUndefinedException If zero divide by zero
     * @throws InvalidInputException    If square root negate number.
     */
    private static void calculateFormula (ArrayList formula) throws DivideZeroException, ResultUndefinedException, InvalidInputException {
        for (int i = 0; i < formula.size(); i++) {
            Object object = formula.get(i);
            checkObject(object);

            clearCalculator(i, object, formula);
            parseNumber(object);
            parseOperation(object, i, formula);

            boolean canCalculate = canCalculate(i, formula);
            if (canCalculate) {
                calculate();
            }
        }
    }

    /**
     * Method checks object. If object is null, method will thrown NullPointerException.
     * If object isn't number or operation, method will thrown IllegalArgumentException.
     *
     * @param object Object which need to check.
     * @throws NullPointerException     If object is null.
     * @throws IllegalArgumentException If object isn't operation and isn't number.
     */
    private static void checkObject (Object object) throws NullPointerException, IllegalArgumentException {
        if (object == null) {
            throw new NullPointerException();
        }

        if (!(object instanceof OperationsEnum)) {
            if (!(object instanceof Number)) {
                throw new IllegalArgumentException();
            }
        }

    }

    /**
     * This method checks number. If object is number, it will set number.
     *
     * @param object Object which need to parse
     */
    private static void parseNumber (Object object) {
        if (object instanceof Number) {
            BigDecimal number = new BigDecimal(object.toString());
            setNumber(number);
        }
    }

    /**
     * This method checks object.
     * If object is operation, it will set operation or will calculated previous operation.
     *
     * @param objectPresent      Object which need to parse
     * @param indexPresentObject Index of present object in formula
     * @param formula            Full formula with object
     * @throws DivideZeroException      If divide by zero
     * @throws ResultUndefinedException If divide zero by zero
     */
    private static void parseOperation (Object objectPresent, int indexPresentObject, ArrayList formula) throws DivideZeroException, ResultUndefinedException {
        if (objectPresent instanceof OperationsEnum) {
            OperationsEnum operationsEnum = (OperationsEnum) objectPresent;
//            Object previousObject = getPreviousFormulaObject(indexPresentObject, formula);
//
//            if (isNegate(previousObject)) {
//                if (!isNegate(operationsEnum)) {
//                    if (numberSecond == null) {
//                        history.addNumber(result);
//                    }
//                }
//            }
//            if(isNegate(operationsEnum)){
//                if (numberSecond == null) {
//                    history.addNumber(result);
//                }
//            }
//            if (indexPresentObject - 1 > 0) {
//                Object previousObject = formula.get(indexPresentObject - 1);
//                if (!isNegate(objectPresent) && isNegate(previousObject)) {
//                    if (deleteNegateHistory) {
//                        history.deleteLast();
//                        history.addNumber(result);
//                        deleteNegateHistory = false;
//                    }
//                }
//            }
            try {
                calculateLastBinaryOperation(operationsEnum);
            } finally {
                addEqualHistory(indexPresentObject, formula);
                addPercentResultHistory(operationsEnum);
                setOperation(operationsEnum);
//                deleteNegateHistory(objectPresent, indexPresentObject, formula);
            }
        }

    }

    private static void deleteNegateHistory (Object objectPresent, int indexPresentObject, ArrayList formula) {
//        if (isNegate(objectPresent)) {
//            if (history.size() >= 2) {
//                Object previousObjectHistory = history.get(history.size() - 2);
//
//                if (binaryOperation == null && numberSecond == null) {
//                    deleteNegateHistory = previousObjectHistory instanceof Number;
//                }else {
//                    deleteNegateHistory = false;
//                }
//                if (deleteNegateHistory) {
//                    history.deleteLast();
//                    history.deleteLast();
//                } else {
//                    if (!isNegate(previousObjectHistory)) {
//                        history.deleteLast();
//                        history.addNumber(result);
//                        history.addOperation(operation);
//                    }
//                }
//
//            }
//        }
    }

    /**
     * Method sets default value to all variables
     */
    public static void clearAllCalculator () {
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
    private static void addPercentResultHistory (OperationsEnum operationPresent) {
        OperationsEnum operationPrevious = operation;
        if (isNegate(operationPresent) && isPercent(operationPrevious)) {
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
                if (isEqual((OperationsEnum) previousObject)) {
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
    private static void calculateLastBinaryOperation (OperationsEnum operationPresent) throws DivideZeroException, ResultUndefinedException {
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

    /**
     * Method sets default value to some variable, if previous operation was equal or unary operation
     *
     * @param index
     * @param objectPresent
     * @param formula
     */
    private static void clearCalculator (int index, Object objectPresent, ArrayList formula) {
        Object previousObject = getPreviousFormulaObject(index, formula);

        if (previousObject != null) {
            clearIfPreviousEqual(objectPresent);
            clearIfPresentNumber(objectPresent, previousObject);
        }
    }

    private static void clearIfPresentNumber (Object objectPresent, Object previousObject) {
        if (objectPresent instanceof BigDecimal) {
            if (isUnary((OperationsEnum) previousObject)) {
                numberFirst = null;
                operation = null;
                result = null;
            }
        }
    }

    private static void clearIfPreviousEqual (Object objectPresent) {
        if (previousEqual) {
            if (objectPresent instanceof BigDecimal) {
                numberFirst = null;
            }

            if (objectPresent instanceof OperationsEnum) {
                OperationsEnum operationsPresent = (OperationsEnum) objectPresent;
                if (!isEqual(operationsPresent)) {
                    result = null;
                    if (!isNegate(objectPresent)) {
                        numberSecond = null;
                        previousEqual = false;
                        if (!isPercent(operationsPresent)) {
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
    private static void calculateBinaryOperation () throws DivideZeroException, ResultUndefinedException {
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
    private static void calculateUnaryOperation () throws InvalidInputException, DivideZeroException {
        setUnaryNumber();

        unary.setOperation(operation);
        unary.calculateUnary();
        result = unary.getResult();

        getUnaryResult();

        if (isNegate(operation)) {
            percentNegate = true;
        }
    }

    /* Method chooses which number must be calculate with unary operation,
     *sets it to unary object.
     */
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
     * @param operation Operation which need to check.
     * @return Returns true if operation was binary, and returns false if operation was not binary
     */
    public static boolean isBinary (OperationsEnum operation) {
        return operation == OperationsEnum.ADD || operation == OperationsEnum.SUBTRACT ||
                operation == OperationsEnum.MULTIPLY || operation == OperationsEnum.DIVIDE;
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

        if (!isUnaryNextObject(nextObject) || !isBinary(operation)) {
            canCalculate = true;
        }

        return canCalculate;
    }

    /* Method returns true if next object is unary operation,
     * and false if next object is null or isn't unary operation.
     */
    private static boolean isUnaryNextObject (Object nextObject) {
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
    private static void calculate () throws DivideZeroException, ResultUndefinedException, InvalidInputException {
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

    /**
     * This method returns true if operation was negate,
     * and returns false if operation was not negate.
     *
     * @param operation Operation which need to check
     * @return Returns true if operation was negate, and returns false if operation was not negate.
     */
    public static boolean isNegate (Object operation) {
        return operation == OperationsEnum.NEGATE;
    }

    // Method calculates percent operation
    private static void calculatePercent () throws InvalidInputException, DivideZeroException, ResultUndefinedException {
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

    /**
     * Method returns percent which need to calculate
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

    /**
     * This method returns true if operation was percent,
     * and returns false if operation was not percent.
     *
     * @param operation Operation which need to check
     * @return Returns true if operation was percent, and returns false if operation was not percent.
     */
    public static boolean isPercent (OperationsEnum operation) {
        return operation == OperationsEnum.PERCENT;
    }

    // Method calculates binary operation if present object from formula is equal
    // and binary operation was in formula. Also method cleans history after calculating binary operation.
    private static void calculateEqual () throws DivideZeroException, ResultUndefinedException {
        if (binaryOperation != null) {
            setNumberEqual();
            operation = binaryOperation;

            try {
                calculateBinaryOperation();
                history.clear();
            } catch (DivideZeroException | ResultUndefinedException e) {
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

    /**
     * This method returns true if operation was equal,
     * and returns false if operation was not equal.
     *
     * @param operation Operation which need to check
     * @return Returns true if operation was equal, and returns false if operation was not equal.
     */
    public static boolean isEqual (OperationsEnum operation) {
        return operation == OperationsEnum.EQUAL;
    }

    /**
     * This method returns true if operation was unary,
     * and returns false if operation was not unary.
     *
     * @param operation Operation which need to check
     * @return Returns true if operation was unary, and returns false if operation was not unary.
     */
    public static boolean isUnary (OperationsEnum operation) {
        return operation == OperationsEnum.SQRT || operation == OperationsEnum.SQR ||
                operation == OperationsEnum.ONE_DIVIDE_X || operation == OperationsEnum.NEGATE;
    }

}
