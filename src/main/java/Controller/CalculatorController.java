package Controller;

import Model.Calculator;
import Model.Exceptions.DivideZeroException;
import Model.Exceptions.InvalidInputException;
import Model.Exceptions.ResultUndefinedException;
import Model.History;
import Model.Memory;
import Model.OperationsEnum;
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
    private final String DEFAULT_TEXT = "0";
    /** Decimal separator which is being used for number in application */
    private final String DECIMAL_SEPARATOR = CalculatorNumberFormatter.DECIMAL_SEPARATOR_AFTER_FORMATTER;
    /** Grouping separator which is being used for number in application */
    private final String GROUPING_SEPARATOR = CalculatorNumberFormatter.GROUPING_SEPARATOR;

    //region FXML elements
    /** Variable keeps empty string value */
    private final String EMPTY_STRING = "";
    private final String MINUS = "-";
    //region Number buttons
    @FXML
    private GridPane calculatorButtons;
    //endregion
    @FXML
    private Button zero, one, two, three,
            four, five, six, seven, eight, nine;
    //region Memory buttons
    @FXML
    private GridPane memoryPanel;
    @FXML
    private Button memoryClear, memoryRecall, memoryStore,
            memoryAdd, memorySubtract;
    //Binary operation buttons
    @FXML
    private Button divide, multiply, subtract, add;
    @FXML
    private Button equal;
    @FXML
    private Button percent;
    //endregion
    //Unary operation buttons
    @FXML
    private Button sqrt, sqr, oneDivideX;
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
    //Clear buttons
    @FXML
    private Button CE, C;
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

    /** Variable keeps result of calculation */
    private BigDecimal result;

    private double moveScroll;
    /** Variable keeps default x-position */
    private double xOffset = 0;

    /** Variable keeps default y-position */
    private double yOffset = 0;

    private Memory memory;
    /** Variable keeps maximal length of symbol which can input */
    private int maxCharInText = 16;
    /** Formula which need to calculate */
    private ArrayList<Object> formula = new ArrayList<>();

    public BigDecimal getResult () {
        return result;
    }

    @FXML
    void initialize () {
        generalDisplay.setText(DEFAULT_TEXT);
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

    //region Text

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
    void addCommaToTextOutput () throws ParseException {
        String textFromDisplay = getTextDisplay();
        boolean isTextWithComma = textFromDisplay.contains(DECIMAL_SEPARATOR);

        if (!isTextWithComma || canChangeOperator) {
            if (!canBackspace) {
                textFromDisplay = DEFAULT_TEXT;
                canBackspace = true;
            }

            // Will increased min numbers symbol in text, if text from general display is "0".
            if (textFromDisplay.equals(DEFAULT_TEXT)) {
                maxCharInText++;
            }

            textFromDisplay = textFromDisplay.concat(DECIMAL_SEPARATOR);
            maxCharInText++;
            canChangeOperator = false;
            printResult(textFromDisplay);
        }

        memoryPressed = false;
    }

    private int lengthTextWithoutGroupingSeparator (String text) {
        String outWithoutSeparator = text.replace(GROUPING_SEPARATOR, EMPTY_STRING);
        return outWithoutSeparator.length();
    }

    private String formatTextInput (String text) throws ParseException {
        BigDecimal number = getParsedNumber(text);
        boolean isTextWithComma = text.contains(DECIMAL_SEPARATOR);

        if (!isTextWithComma) {
            text = formatTextForOutput(number);
        }

        return text;
    }

    /**
     * Method deletes last symbol in text from general display,
     * outs text which was changed, on general display,
     * If text was changed, valid count char in text decreases.
     * If last symbol is comma, valid count char in text decreases,
     * text casts to BigDecimal and it formats again before printing
     */
    @FXML
    void backspacePressed () throws ParseException {
        clearError();
        BigDecimal number = getParsedNumber(getTextDisplay());
        String print = CalculatorNumberFormatter.backspace(number);
        generalDisplay.setText(print);


//        String textFromDisplay = getTextDisplay();

//        if (canBackspace) {
//            int textLength = textFromDisplay.length();
//            int minLengthWithMinus = 2;
//            int minLengthText = 1;
//
//            boolean isTextContainsMinus = isTextContainsMinus(textFromDisplay);
//            boolean isMinLengthTextWithMinus = textLength == minLengthWithMinus && isTextContainsMinus;
//            boolean isMinLengthText = textLength == minLengthText;
//
//            if (isMinLengthTextWithMinus || isMinLengthText) {
//                textFromDisplay = setDefaultText();
//            } else {
//                textFromDisplay = textFromDisplay.substring(0, textLength - 1);
//
//                if (!textFromDisplay.contains(DECIMAL_SEPARATOR)) {
//                    maxCharInText--;
//                }
//            }
//            printResult(formatTextInput(textFromDisplay));
//        }
    }

    private String getTextDisplay () {
        return generalDisplay.getText();
    }

//    /**
//     * This method deletes last symbol in text
//     *
//     * @param text Text which need to backspace
//     * @return Text without last symbol
//     */
//    private String backspaceText (String text) {
//
//        int textLength = text.length();
//        int minLengthWithMinus = 2;
//        int minLengthText = 1;
//
//        boolean isTextContainsMinus = isTextContainsMinus(text);
//        boolean isMinLengthTextWithMinus = textLength == minLengthWithMinus && isTextContainsMinus;
//        boolean isMinLengthText = textLength == minLengthText;
//
//        if (isMinLengthTextWithMinus || isMinLengthText) {
//            text = setDefaultText();
//        } else {
//            text = text.substring(0, textLength - 1);
//
//            if (!text.contains(DECIMAL_SEPARATOR)) {
//                maxCharInText--;
//            }
//        }
//
//        return text;
//    }

    private boolean isTextContainsMinus (String text) {
        return text.contains(MINUS);
    }

    /**
     * Method gets text from button which was pressed
     * and concat it with text from general display before printing.
     * <p>
     * If count symbols in text from general display more than valid count char,
     * method does not concat text from general display with text from number button.
     * <p>
     * Also method sets default text and outs it on general display
     *
     * @param actionEvent Button from number buttons group was pressed
     */
    @FXML
    void numberButtonPressed (ActionEvent actionEvent) throws ParseException {
        clearError();
        String outText = getTextDisplay();
        outText = setDefaultTextAfterResult(outText);

        String buttonText = getButton(actionEvent).getText();
        boolean isTextDefault = outText.equals(DEFAULT_TEXT);

        if (isTextDefault) {
            outText = buttonText;
        } else {
            int lengthTextWithoutGroupingSeparator = lengthTextWithoutGroupingSeparator(outText);

            if (lengthTextWithoutGroupingSeparator < maxCharInText) {
                outText = outText.concat(buttonText);
            }
        }
        printResult(formatTextInput(outText));
        canChangeOperator = false;
    }

    private Button getButton (ActionEvent actionEvent) {
        return ((Button) actionEvent.getSource());
    }


    private BigDecimal getParsedNumber (String numberString) throws ParseException {
        return CalculatorNumberFormatter.getParsedNumber(numberString);
    }


    /**
     * Method inserts "-" before text.
     * Example: before: "9", after: "-9"
     *
     * @param text Text
     */
    private String addMinusToText (String text) {
        boolean isDefaultText = text.equals(DEFAULT_TEXT);

        if (!isDefaultText) {
            String firstChar = String.valueOf(text.charAt(0));
            if (!firstChar.equals(MINUS)) {
                text = MINUS.concat(text);
            } else {
                text = text.substring(1);
            }
        }
        return text;
    }


    /**
     * This method sets which key combination equals buttons pressed
     *
     * @param event Even was happened by keyboard
     */
    @FXML
    void keyPressed (KeyEvent event) {
        KeyCode keyCode = event.getCode();
        if (keyCode == KeyCode.DIGIT1 || keyCode == KeyCode.NUMPAD1) {
            one.fire();
        } else if (keyCode == KeyCode.DIGIT2 || keyCode == KeyCode.NUMPAD2) {
            if (event.isShiftDown()) {
                sqrt.fire();
            } else {
                two.fire();
            }
        } else if (keyCode == KeyCode.DIGIT3 || keyCode == KeyCode.NUMPAD3) {
            three.fire();
        } else if (keyCode == KeyCode.DIGIT4 || keyCode == KeyCode.NUMPAD4) {
            four.fire();
        } else if (keyCode == KeyCode.DIGIT5 || keyCode == KeyCode.NUMPAD5) {
            if (event.isShiftDown()) {
                percent.fire();
            } else {
                five.fire();
            }
        } else if (keyCode == KeyCode.DIGIT6 || keyCode == KeyCode.NUMPAD6) {
            six.fire();
        } else if (keyCode == KeyCode.DIGIT7 || keyCode == KeyCode.NUMPAD7) {
            seven.fire();
        } else if (keyCode == KeyCode.DIGIT8 || keyCode == KeyCode.NUMPAD8) {
            if (event.isShiftDown()) {
                multiply.fire();
            } else {
                eight.fire();
            }
        } else if (keyCode == KeyCode.DIGIT9 || keyCode == KeyCode.NUMPAD9) {
            nine.fire();
        } else if (keyCode == KeyCode.DIGIT0 || keyCode == KeyCode.NUMPAD0) {
            zero.fire();
        } else if (keyCode == KeyCode.ESCAPE) {
            C.fire();
        } else if (keyCode == KeyCode.BACK_SPACE) {
            backspace.fire();
        } else if (keyCode == KeyCode.COMMA) {
            point.fire();
        } else if (keyCode == KeyCode.ADD || (keyCode == KeyCode.EQUALS && event.isShiftDown())) {
            add.fire();
        } else if (keyCode == KeyCode.MINUS || keyCode == KeyCode.SUBTRACT) {
            subtract.fire();
        } else if (keyCode == KeyCode.MULTIPLY) {
            multiply.fire();
        } else if (keyCode == KeyCode.SLASH || keyCode == KeyCode.DIVIDE) {
            divide.fire();
        } else if (keyCode == KeyCode.ENTER) {
            equal.fire();
        } else if (keyCode == KeyCode.F9) {
            negate.fire();
        } else if (keyCode == KeyCode.DELETE) {
            CE.fire();
        } else if (keyCode == KeyCode.R) {
            if (event.isControlDown()) {
                memoryRecall.fire();
            } else {
                oneDivideX.fire();
            }
        } else if (keyCode == KeyCode.Q) {
            if (event.isControlDown()) {
                memorySubtract.fire();
            } else {
                sqr.fire();
            }
        } else if ((keyCode == KeyCode.M && event.isControlDown())) {
            memoryStore.fire();
        } else if ((keyCode == KeyCode.P && event.isControlDown())) {
            memoryAdd.fire();
        } else if ((keyCode == KeyCode.L && event.isControlDown())) {
            memoryClear.fire();
        }
    }

    //endregion

    //region Operations

    /**
     * Method calculate binary operations, if {@code divide, subtract, add, multiply} was pressed.
     * Also method sets first or second number in calculator.
     * Prints result or exception.
     *
     * @param actionEvent Binary operation buttons was pressed
     */
    @FXML
    public void operationPressed (ActionEvent actionEvent) throws ParseException {
        String buttonID = getButton(actionEvent).getId();

        OperationsEnum operationsEnum = operation.get(buttonID);

        if (operationsEnum != null) {
            boolean isBinary = Calculator.isBinary(operationsEnum);
            if (isBinary) {
                calculateBinaryOperation(operationsEnum);
            }

            boolean isUnary = Calculator.isUnary(operationsEnum);
            if (isUnary) {
                calculateUnaryOperations(operationsEnum);
            }

            boolean isPercent = Calculator.isPercent(operationsEnum);
            if (isPercent) {
                calculatePercentOperation();
            }

            boolean isEqual = Calculator.isEqual(operationsEnum);
            if (isEqual) {
                calculateOperationsAfterEqual();
            }
        }
    }


    private void addNumberToFormula () throws ParseException {
        if (canBackspace) {
            formula.add(getDisplayNumber());
            canChangeOperator = false;
        }
    }

    /**
     * Method returns calculator
     *
     * @return CalculatorApp.Calculator
     */

    private BigDecimal getDisplayNumber () throws ParseException {
        String textFromGeneralDisplay = getTextDisplay();
        return getParsedNumber(textFromGeneralDisplay);
    }

    private void addOperationToFormula (OperationsEnum operationsEnum) {
        formula.add(operationsEnum);
    }

    //endregion

    //region Calculate

    /**
     * Method calculates operation,
     * calls print result method,
     * catches exception.
     */
    private void calculateFormula () throws ParseException {
        try {
            result = Calculator.calculator(formula);
            String resultFormatted = formatTextForOutput(result);
            printResult(resultFormatted);
            canBackspace = false;

        } catch (DivideZeroException | ResultUndefinedException | InvalidInputException e) {
            printError(getMassageException(e));
            printHistory();
        }
    }

    /**
     * This method inserts {@code minus} to {@code out},
     * if {@code out} doesn't contains {@code minus}.
     */
    private void calculateNegateOperation () throws ParseException {
        String outText = getTextDisplay();
        int indexLastSymbol = outText.length() - 1;
        String lastSymbol = String.valueOf(outText.toCharArray()[indexLastSymbol]);

        boolean isCommaLastSymbol = lastSymbol.equals(DECIMAL_SEPARATOR);
        if (isCommaLastSymbol || canBackspace) {
            outText = addMinusToText(outText);
            printResult(outText);
        } else {
            addNumberToFormula();
            addOperationToFormula(OperationsEnum.NEGATE);
            calculateFormula();
        }

        boolean isTextContainsMinus = isTextContainsMinus(outText);
        if (isTextContainsMinus) {
            maxCharInText++;
        } else {
            maxCharInText--;
        }
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
        maxCharInText = DEFAULT_MAX_CHARS_INPUT;

        scrollOutOperationMemory();
//        throw new NullPointerException();
    }

    /** Method calculates percent operation, if {@code percent} was pressed */
    private void calculatePercentOperation () throws ParseException {
        addNumberToFormula();
        addOperationToFormula(OperationsEnum.PERCENT);
        calculateFormula();
        printHistory();

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
    //endregion

    //region Print

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

    /** Method outputs calculation's result on general display */
    private void printResult (String text) throws ParseException {
        BigDecimal numberCheckedOverflow = getParsedNumber(text);

        try {
            isOverflow(numberCheckedOverflow);
            generalDisplay.setText(text);
            resizeOutputText();
            printHistory();
        } catch (OverflowException e) {
            printError(getMassageException(e));
        }
    }

    private String getMassageException (Exception exception) {
        String exceptionType = exception.getClass().getSimpleName();
        return exceptions.get(exceptionType);
    }


    /** Method formatters number for print to general display */
    private String formatTextForOutput (BigDecimal number) {
        return CalculatorNumberFormatter.formatNumber(number);
    }

    /**
     * Method checked number before prints to general display.
     * If number is equal or more than max invalid number throws OverflowException.
     * Also if number is equal or less than min invalid number throws OverflowException.
     *
     * @param result Number which need to check.
     */
    private void isOverflow (BigDecimal result) throws OverflowException, ParseException {
        boolean overflow = false;
        BigDecimal number = getParsedNumber(formatTextForOutput(result));

        if (number != null) {
            BigDecimal absoluteNumber = number.abs();
            int compareOne = compareToOne(absoluteNumber);

            if (compareOne > 0) {
                overflow = absoluteNumber.compareTo(MAX_INVALID_NUMBER) >= 0;
            }

            if (compareOne < 0 && compareToZero(absoluteNumber) != 0) {
                overflow = absoluteNumber.compareTo(MIN_INVALID_NUMBER) <= 0;
            }
        }

        if (overflow) {
            throw new OverflowException();
        }
    }

    private int compareToZero (BigDecimal absoluteNumber) {
        return absoluteNumber.compareTo(BigDecimal.ZERO);
    }

    private int compareToOne (BigDecimal absoluteNumber) {
        return absoluteNumber.compareTo(BigDecimal.ONE);
    }
    //endregion

    //region History
    private void printHistory () {
        History history = Calculator.getHistory();
        if (history != null) {
            CalculatorHistoryFormatter calculatorHistoryFormatter = new CalculatorHistoryFormatter(history);
            String historyChanged = calculatorHistoryFormatter.formatHistory();
            outOperationMemory.setText(historyChanged);
        }

        scrollOutOperationMemory();
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
            scrollButtonLeft.setVisible(true);
        }
    }

    /* Method calculates number flipping,
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
        generalDisplay.setText(DEFAULT_TEXT);
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

        maxCharInText = DEFAULT_MAX_CHARS_INPUT;
        resizeOutputText();
        isError = false;
    }


    /**
     * Method sets default value to general display
     */
    @FXML
    void clearNumberCE () {
        generalDisplay.setText(DEFAULT_TEXT);
        maxCharInText = DEFAULT_MAX_CHARS_INPUT;
        clearError();
        resizeOutputText();
    }

    /**
     * Methods sets default value for some variable for cleans general display
     * This method calls if calculator's number button was pressed,
     * result of calculation was printed.
     *
     * @param textFromGeneralDisplay Text from general display
     */
    private String setDefaultTextAfterResult (String textFromGeneralDisplay) {
        if (!canBackspace || memoryPressed || canChangeOperator) {
            canBackspace = true;
            maxCharInText = DEFAULT_MAX_CHARS_INPUT;
            textFromGeneralDisplay = setDefaultText();
        }

        if (memoryPressed) {
            outOperationMemory.setText(EMPTY_STRING);
            formula.clear();
            Calculator.clearAllCalculator();
            memoryPressed = false;
        }
        return textFromGeneralDisplay;
    }

    private String setDefaultText () {
        maxCharInText = DEFAULT_MAX_CHARS_INPUT;
        return DEFAULT_TEXT;
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
    void memoryRecallPressed () throws ParseException {
        if (memory != null) {
            result = memory.memoryRecall();
            String out = formatTextForOutput(result);
            formula.add(result);

            printResult(out);
        }
        canBackspace = false;
        memoryPressed = true;
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
        BigDecimal number = null;

        if (canBackspace) {
            number = getDisplayNumber();
        } else {
            if (result != null) {
                number = result;
            }
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