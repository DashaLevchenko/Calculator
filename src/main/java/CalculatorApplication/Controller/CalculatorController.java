package CalculatorApplication.Controller;

import CalculatorApplication.Model.Calculator;
import CalculatorApplication.Model.Exceptions.DivideZeroException;
import CalculatorApplication.Model.Exceptions.InvalidInputException;
import CalculatorApplication.Model.Exceptions.ResultUndefinedException;
import CalculatorApplication.Model.History;
import CalculatorApplication.Model.Memory;
import CalculatorApplication.Model.OperationsEnum;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

import static javafx.scene.input.KeyCode.*;
import static javafx.scene.input.KeyCode.DIGIT2;

/**
 * This class realizes controller for calculator application
 */
public class CalculatorController {

    /**
     * Variable which keeps key and value of operation.
     * Key is id of button was pressed.
     * Value is enum variable which match some operation.
     */
    private static final HashMap<String, OperationsEnum> operation = new HashMap<>();

    /**
     * Variable which keeps key and value of exceptions.
     * Key is name of exception's class.
     * Value is message which refers to some exception.
     */
    private static final HashMap<String, String> exceptions = new HashMap<>();

    static {
        operation.put("add", OperationsEnum.ADD);
        operation.put("subtract", OperationsEnum.SUBTRACT);
        operation.put("divide", OperationsEnum.DIVIDE);
        operation.put("multiply", OperationsEnum.MULTIPLY);

        operation.put("sqrt", OperationsEnum.SQRT);
        operation.put("sqr", OperationsEnum.SQR);
        operation.put("oneDivideX", OperationsEnum.ONE_DIVIDE_X);
        operation.put("percent", OperationsEnum.PERCENT);
        operation.put("negate", OperationsEnum.NEGATE);
        operation.put("equal", OperationsEnum.EQUAL);

        exceptions.put("InvalidInputException", "Invalid input");
        exceptions.put("ResultUndefinedException", "Result is undefined");
        exceptions.put("DivideZeroException", "Cannot divide by zero");
        exceptions.put("OverflowException", "Overflow");
    }

    /** Maximal number of symbols which can be input in calculator */
    private final int DEFAULT_MAX_CHARS_INPUT = 16;

    /** Maximal invalid number which throws exception */
    private final BigDecimal MAX_INVALID_NUMBER = new BigDecimal("1E10000");

    /** Minimal invalid number which throws exception */
    private final BigDecimal MIN_INVALID_NUMBER = new BigDecimal("1E-10000");

    /** Default text for {@code generalDisplay} */
    private final String DEFAULT_TEXT_TO_GENERAL_DISPLAY = "0";

    /** Decimal separator which is being used for number in application after format */
    private final String DECIMAL_SEPARATOR_AFTER_FORMATTER = CalculatorNumberFormatter.DECIMAL_SEPARATOR_AFTER_FORMATTER;

    /** Variable keeps empty string value */
    private final String EMPTY_STRING = "";


    //region FXML elements
    //region Number buttons
    @FXML
    private GridPane calculatorButtons;
    @FXML
    private Button zero, one, two, three,
            four, five, six, seven, eight, nine;
    //endregion

    //region Memory buttons
    @FXML
    private GridPane memoryPanel;
    @FXML
    private Button memoryClear, memoryRecall, memoryStore,
            memoryAdd, memorySubtract;

    //endregion

    //region Operation buttons
    //Unary operation buttons
    @FXML
    private Button sqrt, sqr, oneDivideX;
    //Binary operation buttons
    @FXML
    private Button divide, multiply, subtract, add;
    @FXML
    private Button equal;
    @FXML
    private Button percent;
    //Clear buttons
    @FXML
    private Button CE, C;
    //endregion

    //region Displays
    @FXML
    private Label generalDisplay;
    @FXML
    private Label outOperationMemory;
    //endregion
    @FXML
    private ScrollPane scrollPaneOperation;
    @FXML
    private Button scrollButtonLeft, scrollButtonRight;

    //Change number buttons
    @FXML
    private Button backspace, negate, point;
    //region Other Window elements
    @FXML
    private Label title, textStandard;
    @FXML
    private Button cancel, maximizeButton, hideButton;
    @FXML
    private AnchorPane leftMenu;
    //endregion
    @FXML
    private AnchorPane generalAnchorPane;
    //endregion

    /** Variable is true when can change binary operation */
    private boolean canChangeOperator = false;

    /** Variable is true when left menu is visible */
    private boolean showLeftMenu = false;

    /** Variable is true when was printed exception */
    private boolean isError = false;

    /** Variable is true when memory buttons was pressed */
    private boolean memoryPressed = false;

    /** Variable is true when can change text from general display */
    private boolean canBackspace = true;

    /**
     * Variable keeps true if last symbol of text from general display is decimal separator,
     * else keeps false
     */
    private boolean isLastSymbolDecimalSeparator;

    /** Variable keeps result of calculation */
    private BigDecimal result;

    private double moveScroll;
    /** Variable keeps default x-position */
    private double xOffset = 0;

    /** Variable keeps default y-position */
    private double yOffset = 0;

//    private Memory memory;

    /** Formula which need to calculate */
    private ArrayList<Object> formula = new ArrayList<>();
    private Memory memory;

    public BigDecimal getResult () {
        return result;
    }

    @FXML
    void initialize () {
        generalDisplay.setText(DEFAULT_TEXT_TO_GENERAL_DISPLAY);
    }

    //region Input

    /**
     * Method adds comma to text from general display,
     * outs result on general display.
     * If comma was added to text, valid count char in text increases.
     * If text equals default text, valid count char in text increases.
     * <p>
     * If comma contains in text yet, method does nothing.
     *
     * @throws ParseException If can't parse text in number for check overflow
     */
    @FXML
    void commaButtonPressed () throws ParseException {
        BigDecimal number;

        if (canBackspace && !memoryPressed) {
            number = getDisplayNumber();
        } else {
            number = BigDecimal.ZERO;
            canBackspace = true;
        }

        if (number.scale() == 0) {
            isLastSymbolDecimalSeparator = true;
        }
        printToGeneralDisplay(number);

        canChangeOperator = false;
        memoryPressed = false;
    }


    /**
     * Method gets text from general display and parses text to number.
     *
     * @return Number was parsed from general display.
     * @throws ParseException If can't parse text to number.
     */
    private BigDecimal getDisplayNumber () throws ParseException {
        String textFromGeneralDisplay = generalDisplay.getText();
        isLastSymbolDecimalSeparator = textFromGeneralDisplay.endsWith(DECIMAL_SEPARATOR_AFTER_FORMATTER);
        return CalculatorNumberFormatter.getParsedNumber(textFromGeneralDisplay);
    }

    /**
     * Method delete last symbol in number.
     * Method parses number from general display,
     * delete last digit.
     * Example:
     * was:    1,9
     * became: 1,
     * <p>
     * was:    -8
     * became: 0
     * <p>
     * was:    98989897,123
     * became: 98989897,12
     * <p>
     * was:    98989897
     * became: 9898989
     *
     * @throws ParseException If can't parse text to number.
     */
    @FXML
    void backspacePressed () throws ParseException {
        clearError();
        if (canBackspace) {
            BigDecimal number = getDisplayNumber();

            if (!isLastSymbolDecimalSeparator) {
                int scale = number.scale();
                number = number.movePointRight(scale);
                number = number.divideToIntegralValue(BigDecimal.TEN);

                if (scale > 0) {
                    number = number.movePointLeft(scale - 1);
                    if (number.scale() == 0) {
                        isLastSymbolDecimalSeparator = true;
                    }
                }
            } else {
                isLastSymbolDecimalSeparator = false;
            }
            printToGeneralDisplay(number);
        }
    }

    /**
     * Method gets digit from button which was pressed
     * and concat it with number from general display before printing.
     * <p>
     * If count symbols in text from general display more than valid count char,
     * method does not concat number from general display with digit from number button.
     * <p>
     * Also method sets default text and prints it on general display.
     *
     * @param actionEvent Button from number buttons group was pressed.
     * @throws ParseException If can't parse text to number.
     */
    @FXML
    void numberButtonPressed (ActionEvent actionEvent) throws ParseException {
        clearError();
        setDefaultTextAfterResult();

        BigDecimal digit = new BigDecimal(getButton(actionEvent).getText());
        BigDecimal number = getDisplayNumber();

        if (compareToZero(number) == 0 && number.scale() == 0 && !isLastSymbolDecimalSeparator) {
            number = digit;
        } else {
            int digitInNumber = countDigitInNumber(number);

            if (digitInNumber < DEFAULT_MAX_CHARS_INPUT) {
                int scale = number.scale();
                int digitScale;
                if (isLastSymbolDecimalSeparator) {
                    isLastSymbolDecimalSeparator = false;
                    scale++;
                    digitScale = scale;
                } else {
                    digitScale = scale + 1;
                }

                if (scale == 0) {
                    number = number.movePointRight(1);
                } else {
                    digit = digit.movePointLeft(digitScale);
                }

                if (number.signum() >= 0) {
                    number = number.add(digit);
                } else {
                    number = number.subtract(digit);
                }
            }
        }

        printToGeneralDisplay(number);
        canChangeOperator = false;
    }


    /**
     * Method calculate the number of digits in {@code number}
     *
     * @param number The number in which need to calculate the number of digits
     * @return Number of digit
     */
    private int countDigitInNumber (BigDecimal number) {
        int digitInNumber;
        number = number.abs();
        if (compareToZero(number) > 0 && compareToOne(number) < 0) {
            digitInNumber = number.scale();
        } else {
            digitInNumber = number.precision();
        }

        return digitInNumber;
    }

    private Button getButton (ActionEvent actionEvent) {
        return ((Button) actionEvent.getSource());
    }


    //endregion

    //region Operations

    /**
     * This method sets which key combination equals buttons pressed
     *
     * @param event Even was happened by keyboard
     */
    @FXML
    void keyPressed (KeyEvent event) {
        KeyCode keyCode = event.getCode();
        boolean isControlDownEvent = event.isControlDown();
        boolean isShiftDownEvent = event.isShiftDown();
        Button button;
        if (keyCode == DIGIT1 || keyCode == NUMPAD1) {
            button = one;
        } else if (keyCode == DIGIT2 || keyCode == NUMPAD2) {
            if (isShiftDownEvent) {
                button = sqrt;
            } else {
                button = two;
            }
        } else if (keyCode == DIGIT3 || keyCode == NUMPAD3) {
            button = three;
        } else if (keyCode == DIGIT4 || keyCode == NUMPAD4) {
            button = four;
        } else if (keyCode == DIGIT5 || keyCode == NUMPAD5) {
            if (isShiftDownEvent) {
                button = percent;
            } else {
                button = five;
            }
        } else if (keyCode == DIGIT6 || keyCode == NUMPAD6) {
            button = six;
        } else if (keyCode == DIGIT7 || keyCode == NUMPAD7) {
            button = seven;
        } else if (keyCode == DIGIT8 || keyCode == NUMPAD8) {
            if (isShiftDownEvent) {
                button = multiply;
            } else {
                button = eight;
            }
        } else if (keyCode == DIGIT9 || keyCode == NUMPAD9) {
            button = nine;
        } else if (keyCode == DIGIT0 || keyCode == NUMPAD0) {
            button = zero;
        } else if (keyCode == ESCAPE) {
            button = C;
        } else if (keyCode == BACK_SPACE) {
            button = backspace;
        } else if (keyCode == COMMA) {
            button = point;
        } else if (keyCode == ADD || (keyCode == EQUALS && isShiftDownEvent)) {
            button = add;
        } else if (keyCode == MINUS || keyCode == SUBTRACT) {
            button = subtract;
        } else if (keyCode == MULTIPLY) {
            button = multiply;
        } else if (keyCode == SLASH || keyCode == DIVIDE) {
            button = divide;
        } else if (keyCode == ENTER) {
            button = equal;
        } else if (keyCode == F9) {
            button = negate;
        } else if (keyCode == DELETE) {
            button = CE;
        } else if (keyCode == R) {
            if (isControlDownEvent) {
                button = memoryRecall;
            } else {
                button = oneDivideX;
            }
        } else if (keyCode == Q) {
            if (isControlDownEvent) {
                button = memorySubtract;
            } else {
                button = sqr;
            }
        } else if ((keyCode == M && isControlDownEvent)) {
            button = memoryStore;
        } else if ((keyCode == P && isControlDownEvent)) {
            button = memoryAdd;
        } else if ((keyCode == L && isControlDownEvent)) {
            button = memoryClear;
        } else {
            button = null;
        }

        if (button != null) {
            button.fire();
        }
    }

    /**
     * Method calls methods which calculate binary and unary operations.
     *
     * @param actionEvent Operation button was pressed.
     * @throws ParseException If can't parse text to number.
     */
    //todo change else if
    @FXML
    public void operationPressed (ActionEvent actionEvent) throws ParseException {
        String buttonID = getButton(actionEvent).getId();

        OperationsEnum operationsEnum = operation.get(buttonID);

        if (operationsEnum != null) {
            if (Calculator.isBinary(operationsEnum)) {
                calculateBinaryOperation(operationsEnum);
            } else if (Calculator.isUnary(operationsEnum)) {
                calculateUnaryOperations(operationsEnum);
            } else if (Calculator.isPercent(operationsEnum)) {
                calculatePercentOperation();
            } else if (Calculator.isEqual(operationsEnum)) {
                calculateOperationsAfterEqual();
            }
        }
    }


    //endregion

    //region Calculate

    private void addOperationToFormula (OperationsEnum operationsEnum) {
        formula.add(operationsEnum);
    }

    /**
     * Method add number from general display to formula for calculate.
     *
     * @throws ParseException If can't parse text to number.
     */
    private void addNumberToFormula () throws ParseException {
        if (canBackspace) {
            formula.add(getDisplayNumber());
            canChangeOperator = false;
        }
    }

    /**
     * Method calculates operation,
     * calls print result method,
     * catches exception.
     */
    private void calculateFormula () {
        try {
            result = Calculator.calculator(formula);
            canBackspace = false;
            printCalculateResult();
        } catch (DivideZeroException | ResultUndefinedException | InvalidInputException e) {
            printError(getMassageException(e));
            printHistory();
        }
    }

    /**
     * Method calculate unary operations, if {@code sqr, sqrt, oneDivideX} was pressed.
     * Also method sets first or second number in calculator.
     * Prints result or exception.
     *
     * @param operationsEnum Operation
     */
    private void calculateUnaryOperations (OperationsEnum operationsEnum) throws ParseException {
        boolean isNegate = Calculator.isNegate(operationsEnum);

        if (isNegate) {
            calculateNegateOperation();
        } else {
            addNumberToFormula();
            addOperationToFormula(operationsEnum);
            calculateFormula();
            printHistory();
            memoryPressed = false;
        }

    }

    /**
     * This method calculate negate operation.
     * If can't backspace number because new number wasn't input
     * and was printed result of previous operation,
     * operation will add to formula and calculate negate operation by calculator.
     * <p>
     * If can backspace number because new number is entering now,
     *
     * @throws ParseException If can't parse text to number.
     */
    private void calculateNegateOperation () throws ParseException {
        if (!canBackspace || memoryPressed) {
            addOperationToFormula(OperationsEnum.NEGATE);
            calculateFormula();
        } else {
            BigDecimal number = getDisplayNumber().negate();
            printToGeneralDisplay(number);
        }
        memoryPressed = false;
    }

    private void calculateBinaryOperation (OperationsEnum operationsEnum) throws ParseException {
        if (!canChangeOperator) {
            addNumberToFormula();
        }

        addOperationToFormula(operationsEnum);
        canChangeOperator = true;
        calculateFormula();
        memoryPressed = false;
    }

    /**
     * Methods calculates operation, if {@code equal} was pressed.
     * Sets second number in calculator if {@code binaryOperation} not null.
     * Clears percent operation, sets default value {@code charValidInText}.
     */
    private void calculateOperationsAfterEqual () throws ParseException {
        clearError();
        addNumberToFormula();
        addOperationToFormula(OperationsEnum.EQUAL);
        calculateFormula();

        canBackspace = false;
        memoryPressed = false;

        scrollOutOperationMemory();
    }

    /** Method calculates percent operation, if {@code percent} was pressed */
    private void calculatePercentOperation () throws ParseException {
        addNumberToFormula();
        addOperationToFormula(OperationsEnum.PERCENT);
        calculateFormula();
        printHistory();

    }
    //endregion

    //region Print
    private void printHistory () {
        History calculatorHistory = Calculator.getHistory();
        CalculatorHistoryFormatter calculatorHistoryFormatter = new CalculatorHistoryFormatter();
        String historyChanged = calculatorHistoryFormatter.formatCalculatorHistory(calculatorHistory);
        outOperationMemory.setText(historyChanged);
        scrollOutOperationMemory();
    }

    /**
     * Method outputs exception's message on general display,
     * makes some buttons disable, resizes text on general display.
     *
     * @param messageError Message which need to print on {@code generalDisplay}
     */
    private void printError (String messageError) {
        isError = true;
        setOperationsDisable(true);
        memoryPanel.setDisable(true);

        generalDisplay.setText(messageError);

        resizeOutputText();
        scrollOutOperationMemory();
    }

    /** Method does some button disable or not disable */
    private void setOperationsDisable (boolean disable) {
        percent.setDisable(disable);
        sqrt.setDisable(disable);
        sqr.setDisable(disable);
        oneDivideX.setDisable(disable);
        divide.setDisable(disable);
        multiply.setDisable(disable);
        subtract.setDisable(disable);
        add.setDisable(disable);
        negate.setDisable(disable);
        point.setDisable(disable);
    }

    /**
     * Method checks overflow result number, if overflow true will print error massage,
     * else will print result was formatted.
     */
    private void printCalculateResult () {
        try {
            isOverflow(result);
            isLastSymbolDecimalSeparator = false;
            printToGeneralDisplay(result);
            printHistory();
        } catch (OverflowException e) {
            printError(getMassageException(e));
        }
    }


    private void printToGeneralDisplay (BigDecimal number) {
        number = roundNumber(number);
        if (!canBackspace || memoryPressed) {
            number = number.stripTrailingZeros();
        }
        String textForPrint = CalculatorNumberFormatter.formatNumberForPrint(number, isLastSymbolDecimalSeparator);
        generalDisplay.setText(textForPrint);
        resizeOutputText();
    }

    private String getMassageException (Exception exception) {
        String exceptionType = exception.getClass().getSimpleName();
        return exceptions.get(exceptionType);
    }

    /**
     * Method checks number before prints to general display.
     * It formats number to text and parse text to number for check overflow,
     * because when number formatting it rounding.
     *
     * @param result Number which need to check.
     * @throws OverflowException If number is equal or more than max invalid number throws OverflowException.
     *                           Also if number is equal or less than min invalid number throws OverflowException.
     */
    private void isOverflow (BigDecimal result) throws OverflowException {
        boolean overflow = false;
        BigDecimal number = roundNumber(result);

        if (number != null) {
            BigDecimal absoluteNumber = number.abs();
            int compareToOne = compareToOne(absoluteNumber);

            if (compareToOne > 0) {
                overflow = absoluteNumber.compareTo(MAX_INVALID_NUMBER) >= 0;
            }

            if (compareToOne < 0 && compareToZero(absoluteNumber) != 0) {
                overflow = absoluteNumber.compareTo(MIN_INVALID_NUMBER) <= 0;
            }
        }

        if (overflow) {
            throw new OverflowException();
        }
    }

    private BigDecimal roundNumber (BigDecimal number) {
        return CalculatorNumberFormatter.roundNumber(number);

    }

    private int compareToZero (BigDecimal absoluteNumber) {
        return absoluteNumber.compareTo(BigDecimal.ZERO);
    }

    private int compareToOne (BigDecimal absoluteNumber) {
        return absoluteNumber.compareTo(BigDecimal.ONE);
    }
    //endregion


    //region Scroll CalculatorHistory

    /**
     * This method moves scroll of calculator history in left if scroll button was pressed.
     * Makes left button invisible, if can't scroll left
     */
    @FXML
    void scrollButtonLeftPressed () {
        double moveScrollLeft = scrollPaneOperation.getHvalue() + moveScroll;

        scrollPaneOperation.setHvalue(moveScrollLeft);
        scrollButtonRight.setVisible(true);

        double presentHvalue = scrollPaneOperation.getHvalue();
        double maxHvalue = scrollPaneOperation.getHmax();
        if (presentHvalue == maxHvalue) {
            scrollButtonLeft.setVisible(false);
        }
    }

    /**
     * This method moves scroll of calculator history in right if scroll button was pressed.
     * Makes right button invisible, if can't scroll right.
     * Makes left button visible, if can scroll left.
     */
    @FXML
    void scrollButtonRightPressed () {
        double moveScrollRight = scrollPaneOperation.getHvalue() - moveScroll;
        scrollPaneOperation.setHvalue(moveScrollRight);

        scrollButtonLeft.setVisible(true);
        double presentHvalue = scrollPaneOperation.getHvalue();
        if (presentHvalue == 0) {
            scrollButtonRight.setVisible(false);
        }
    }

    /**
     * Method calculates number flipping,
     * if text width more then width history area.
     */
    private void scrollOutOperationMemory () {
        moveScroll = ResizeDisplay.scrollText(scrollPaneOperation, outOperationMemory.getText(), scrollButtonLeft, scrollButtonRight);
    }
    //endregion

    //region Clear

    /**
     * This method sets default value all variable which used in calculator.
     */
    @FXML
    void clearAllC () {
        generalDisplay.setText(DEFAULT_TEXT_TO_GENERAL_DISPLAY);
        outOperationMemory.setText(EMPTY_STRING);

        formula.clear();
        Calculator.clearAllCalculator();

        canChangeOperator = false;
        canBackspace = true;
        memoryPressed = false;

        scrollButtonRight.setVisible(false);
        scrollButtonLeft.setVisible(false);

        setOperationsDisable(false);
        memoryPanel.setDisable(false);

        resizeOutputText();
        isError = false;
    }


    /**
     * Method sets default value to general display
     */
    @FXML
    void clearNumberCE () {
        generalDisplay.setText(DEFAULT_TEXT_TO_GENERAL_DISPLAY);
        clearError();
        resizeOutputText();
    }

    /**
     * Methods sets default value for some variable for cleans general display
     * This method calls if calculator's number button was pressed,
     * result of calculation was printed.
     */
    private void setDefaultTextAfterResult () {
        if (!canBackspace || memoryPressed || canChangeOperator) {
            canBackspace = true;
            generalDisplay.setText(DEFAULT_TEXT_TO_GENERAL_DISPLAY);
            if (memoryPressed) {
                outOperationMemory.setText(EMPTY_STRING);
                Calculator.clearAllCalculator();
                memoryPressed = false;
            }
        }

    }

    /**
     * Method imitates press C button,
     * if exception massage was printed and some button was pressed
     */
    private void clearError () {
        if (isError) {
            C.fire();
        }
    }
    //endregion

    //region Memory

    /**
     * Method saves number in memory, if {@code memoryStore} button was pressed
     */
    @FXML
    void memoryStorePressed () throws ParseException {
        if (memory == null) {
            memory = new Memory();
        }
        memory.setNumber(setMemoryNumber());

        setMemoryButtonsDisable(false);
        memoryPressed = true;
    }

    /**
     * Method adds number to number in memory, if {@code memoryAdd} button was pressed.
     * Sets variable {@code memoryPressed} value true.
     */
    @FXML
    void memoryAddPressed () throws ParseException {
        if (memory == null) {
            memory = new Memory();
            setMemoryButtonsDisable(false);
        }
        memory.memoryAdd(setMemoryNumber());
        memoryPressed = true;
    }

    /**
     * Method clears memory, if {@code memoryClear} button was pressed.
     * Makes some memory buttons disable.
     */
    @FXML
    void memoryClearPressed () {
        if (memory != null) {
            memory.memoryClear();
            memory = null;
            setMemoryButtonsDisable(true);
        }
    }

    /**
     * Method recalls number from memory, if {@code memoryClear} button was pressed.
     * Also method sets value to calculator number and prints number from memory.
     * Sets variable {@code memoryPressed} value true.
     */
    @FXML
    void memoryRecallPressed () {
        memoryPressed = true;
        if (memory != null) {
            result = memory.memoryRecall();
            formula.add(result);

            printCalculateResult();
        }
        canBackspace = false;
    }

    /**
     * Method subtracts number to number in memory, if {@code memorySubtract} button was pressed.
     * If number in memory equal null, in memory sets negate number.
     * Sets variable {@code memoryPressed} value true.
     */
    @FXML
    void memorySubtractPressed () throws ParseException {
        if (memory == null) {
            memory = new Memory();
            setMemoryButtonsDisable(false);
        }
        memory.memorySubtract(setMemoryNumber());

        memoryPressed = true;
    }


    /** Method makes memory clear and memory recall buttons disable or not disable */
    private void setMemoryButtonsDisable (boolean disable) {
        memoryClear.setDisable(disable);
        memoryRecall.setDisable(disable);
    }


    /** Method sets number in memory object */
    private BigDecimal setMemoryNumber () throws ParseException {
        BigDecimal number;

        if (result != null && !canBackspace) {
            number = result;
        } else {
            number = getDisplayNumber();
        }

        return number;
    }

    //endregion

    //region Window

    /** Close calculator, if {@code cancel} was pressed */
    @FXML
    public void closeWindow () {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    /** Maximize window of calculator and resize all button, if {@code maximize} was pressed */
    @FXML
    public void maximizeWindow () {
        Stage stage = (Stage) maximizeButton.getScene().getWindow();
        ResizeWindow resizeWindow = new ResizeWindow(stage);
        resizeWindow.maximizeWindow(maximizeButton);
        resizeWindow.resizeButton(calculatorButtons);

        resizeOutputText();

        scrollOutOperationMemory();
    }

    /**
     * Hide window of calculator, doesn't close, if {@code hide} was pressed.
     */
    @FXML
    public void hideWindow () {
        hideWindowByButton(hideButton);
    }


    private void hideWindowByButton (Button button) {
        Stage stage = (Stage) button.getScene().getWindow();
        stage.setIconified(true);
    }


    /**
     * If mouse was dragged method changes location of calculator.
     *
     * @param event Mouse event
     */
    @FXML
    void draggedWindow (MouseEvent event) {
        Stage stage = (Stage) title.getScene().getWindow();

        double x = event.getScreenX() - xOffset;
        stage.setX(x);

        double y = event.getScreenY() - yOffset;
        stage.setY(y);
    }

    /**
     * Sets values {@code xOffset} and {@code yOffset}, if mouse event was click
     *
     * @param event Mouse event
     */
    @FXML
    void clickWindow (MouseEvent event) {
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }

    /**
     * This method show calculator's left menu, if button show menu was pressed
     */
    @FXML
    void showLeftMenu () {
        double transitionX;
        boolean visible;

        if (!showLeftMenu) {
            transitionX = leftMenu.getWidth();
            showLeftMenu = true;
            visible = false;
        } else {
            transitionX = 0;
            showLeftMenu = false;
            visible = true;
        }

        double speedOfAnimation = 0.1;
        Duration duration = Duration.seconds(speedOfAnimation);
        TranslateTransition transition = new TranslateTransition(duration, leftMenu);

        transition.setToX(transitionX);
        textStandard.setVisible(visible);
        transition.play();
    }

    //endregion

    //region Resize

    /**
     * This method resizes widow of calculator, if mouse was moved and dragged on border window.
     * Also method changes history display.
     */
    @FXML
    void resizeWindow () {
        Stage stage = (Stage) generalAnchorPane.getScene().getWindow();
        ResizeWindow resize = new ResizeWindow(stage);
        resize.resizeAllStage();
        scrollOutOperationMemory();
    }

    /** Method resize text for general display and sets one */
    private void resizeOutputText () {
        Font newFont = ResizeDisplay.fontSizeChangedWidth(generalDisplay);
        generalDisplay.setFont(newFont);
    }

    //endregion

}