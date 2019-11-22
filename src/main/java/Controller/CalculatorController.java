package Controller;

import Model.*;
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
import javafx.stage.Stage;
import javafx.util.Duration;

import java.math.BigDecimal;

public class CalculatorController {
    //region FXML object
    @FXML
    private GridPane calculatorButtons;

    @FXML
    private Button zero;

    @FXML
    private Button seven;

    @FXML
    private Button eight;

    @FXML
    private Button nine;

    @FXML
    private Button four;

    @FXML
    private Button five;

    @FXML
    private Button six;

    @FXML
    private Button one;

    @FXML
    private Button two;

    @FXML
    private Button three;

    @FXML
    private GridPane memoryPanel;

    @FXML
    private Button memoryClear;

    @FXML
    private Button memoryRecall;

    @FXML
    private Button memoryStore;

    @FXML
    private Button memoryAdd;

    @FXML
    private Button memorySubtract;

    @FXML
    private Button divide;

    @FXML
    private Button multiply;

    @FXML
    private Button subtract;

    @FXML
    private Button add;

    @FXML
    private Button equal;

    @FXML
    private Button percent;

    @FXML
    private Button sqrt;

    @FXML
    private Button sqr;

    @FXML
    private Button oneDivideX;

    @FXML
    private Label generalDisplay;

    @FXML
    private Label outOperationMemory;

    @FXML
    private ScrollPane scrollPaneOperation;

    @FXML
    private Button scrollButtonLeft;

    @FXML
    private Button scrollButtonRight;

    @FXML
    private Button CE;

    @FXML
    private Button C;

    @FXML
    private Button backspace;

    @FXML
    private Button plusMinus;

    @FXML
    private Button point;

    @FXML
    private Label title;

    @FXML
    private Label textStandard;

    @FXML
    private Button cancel;

    @FXML
    private Button maximizeButton;


    @FXML
    private Button hideButton;

    @FXML
    private AnchorPane leftMenu;

    @FXML
    private AnchorPane generalAnchorPane;
    //endregion

    private final int CHAR_MAX_INPUT = 16;
    private final BigDecimal MAX_NUMBER = new BigDecimal("1E10000");
    private final BigDecimal MIN_NUMBER = new BigDecimal("1E-10000");

    private OperationsEnum binaryOperation;
    private OperationsEnum unaryOperation;
    private OperationsEnum percentOperation;

    private int charValidInText = 16;
    private String firstStyleLabel;
    private String historyOperations = "";
    private String historyUnaryOperations = "";
    private String negateHistory = "";
    private String defaultText = "0";
    private String decimalSeparate = ",";
    private String separatorNumber = " ";
    private String minus = "-";
    private String emptyString = "";

    private boolean pointInText = false;
    private boolean canChangeOperator = false;
    private boolean equalWasPress;
    private boolean showLeftMenu = false;
    private boolean isError = false;
    private boolean negatePressed = false;
    private boolean memoryPressed = false;
    private boolean canBackspace = true;

    private double moveScroll;
    private double xOffset = 0;
    private double yOffset = 0;
    private double speedOfAnimation = 0.1;

    private Memory memory;
    private boolean percentPressed = false;
    private Calculator calculator = new Calculator();


    @FXML
    void initialize () {
        firstStyleLabel = generalDisplay.getStyle();
        generalDisplay.setText(defaultText);
        equal.setDefaultButton(true);
    }

    @FXML
    void showLeftMenu () {
        TranslateTransition transition = new TranslateTransition(Duration.seconds(speedOfAnimation), leftMenu);
        if (!showLeftMenu) {
            transition.setToX(leftMenu.getWidth());
            showLeftMenu = true;
            textStandard.setVisible(false);
        } else {
            transition.setToX(0);
            showLeftMenu = false;
            textStandard.setVisible(true);
        }
        transition.play();
    }

    private void operationsIsDisable (boolean disable) {
        percent.setDisable(disable);
        sqrt.setDisable(disable);
        sqr.setDisable(disable);
        oneDivideX.setDisable(disable);
        divide.setDisable(disable);
        multiply.setDisable(disable);
        subtract.setDisable(disable);
        add.setDisable(disable);
        plusMinus.setDisable(disable);
        point.setDisable(disable);
    }

    //region Text

    @FXML
    void inputComma (ActionEvent actionEvent) {
        String out = generalDisplay.getText();

        if (!pointInText) {
            if (!canBackspace) {
                out = defaultText;
                canBackspace = true;
            }
            if (out.equals(defaultText)) {
                charValidInText++;
            }
            out += decimalSeparate;
            pointInText = true;
            charValidInText++;
            canChangeOperator = false;
        }

        printResult(out);
        memoryPressed = false;
    }

    @FXML
    void inputBackspace () {
        clearError();
        String out = generalDisplay.getText();
        if (canBackspace) {
            out = Text.backspace(out);

            if (pointInText) {
                if (!out.contains(decimalSeparate)) {
                    pointInText = false;
                    charValidInText--;
                }
                printResult(out);
            } else {
                printResult(formatterNumber(new BigDecimal(Text.deleteNumberSeparator(out, separatorNumber))));
            }
        }
    }

    @FXML
    void inputNumber (ActionEvent actionEvent) {
        String buttonText = ((Button) actionEvent.getSource()).getText();

        clearError();

        String out = Text.getTextLabel(generalDisplay, separatorNumber);
        out = clearDisplay(out);

        if (out.equals(defaultText)) {
            printResult(buttonText);
        } else {
            if (out.length() < charValidInText) {
                out += buttonText;
                printResult(FormatterNumber.formatterInputNumber(out));
            }
        }

        scrollOutOperationMemory();

        if (historyOperations.isEmpty()) {
            setCalculatorFirstNumber(null);
            setCalculatorSecondNumber(null);
            setCalculatorResult(null);
        }
    }

    @FXML
    void negate () {
        negatePressed = true;
        addNegateHistory();

        setNumberNegate();
        String out = Text.getTextLabel(generalDisplay, emptyString);
        out = Text.addNegate(out);

        if (out.contains(minus)) {
            charValidInText++;
        } else {
            charValidInText--;
        }

        printResult(out);
        scrollOutOperationMemory();
        canChangeOperator = false;
    }

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
            plusMinus.fire();
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

    //region Set number

    /*
    This method sets negative number in calculator object after negative button was pressed
     */
    private void setNumberNegate () {
        if (equalWasPress) {
            if (getFirstNumber() != null) {
                setCalculatorFirstNumber(getFirstNumber().negate());
            }
        } else {
            if (binaryOperation == null) {
                setBinaryFirstNumber();
                setCalculatorFirstNumber(getFirstNumber().negate());
                if (getResult() != null) {
                    setCalculatorResult(getResult().negate());
                }
            } else {
                if (canBackspace) {
                    setCalculatorSecondNumber(getDisplayNumber().negate());
                } else {
                    if (getSecondNumber() == null) {
                        setCalculatorSecondNumber(getFirstNumber().negate());
                    } else {
                        setCalculatorSecondNumber(getSecondNumber().negate());
                    }
                }
            }
        }
    }

    /*
     * This method sets first number in calculator object and adds it to history
     * after binary or unary operation buttons was pressed
     */
    private void setBinaryFirstNumber () {
        if (calculator.getNumberFirst() == null) {
            setCalculatorFirstNumber(getDisplayNumber());
        }
        addBinaryFirstNumberHistory();
    }


    /*
     * This method sets second number in calculator object, adds it to history and adds first number to history
     * after binary or unary operation buttons was pressed
     */
    private void setBinarySecondNumber () {
        if (equalWasPress) {
            if (!canBackspace) {
                setCalculatorSecondNumber(getFirstNumber());
            } else {
                setCalculatorSecondNumber(getDisplayNumber());
            }
        } else {
            if (!memoryPressed && canBackspace) {
                setCalculatorSecondNumber(getDisplayNumber());
            }
            addBinarySecondNumberHistory();
        }
        addBinaryFirstNumberHistory();
    }

    /*
     * This method sets first and second numbers in calculator object and adds it to history
     * after binary or unary operation buttons was pressed
     */
    private void setNumbersBinary () {
        if (binaryOperation == null) {
            setBinaryFirstNumber();
            setCalculatorSecondNumber(null);
        } else {
            setBinarySecondNumber();
        }
        percentPressed = false;
    }

    /*
    This method sets first or second number in calculator object for calculate unary operation
     */
    private BigDecimal setNumberUnary () {
        BigDecimal unaryNumber;

        if (binaryOperation == null) {
            if (calculator.getNumberFirst() == null) {
                setCalculatorFirstNumber(getDisplayNumber());
            }
            setCalculatorSecondNumber(null);
            unaryNumber = calculator.getNumberFirst();
        } else {
            if (canBackspace) {
                setCalculatorSecondNumber(getDisplayNumber());
            } else {
                if (calculator.getResult() != null && !negatePressed) {
                    setCalculatorSecondNumber(calculator.getResult());
                }
            }
            unaryNumber = calculator.getNumberSecond();
        }
        return unaryNumber;
    }

    /*
     * This method sets result number in calculator object
     */
    private void setCalculatorResult (BigDecimal number) {
        calculator.setResult(number);
    }

    /*
     * This method sets first number in calculator object
     */
    private void setCalculatorFirstNumber (BigDecimal number) {
        calculator.setNumberFirst(number);
    }

    /*
     * This method sets second number in calculator object
     */
    private void setCalculatorSecondNumber (BigDecimal number) {
        calculator.setNumberSecond(number);
    }

    /*
     * Method sets number in calculator object for calculate percent,
     * and deletes last number if percent was pressed several time
     */
    private void setPercentNumber () {
        if (binaryOperation != null) {
            if (binaryOperation.equals(OperationsEnum.DIVIDE) || binaryOperation.equals(OperationsEnum.MULTIPLY)) {
                calculator.setPercent(BigDecimal.valueOf(1));
                setCalculatorSecondNumber(getDisplayNumber());
            } else {
                if (!equalWasPress) {
                    if (canBackspace) {
                        setCalculatorSecondNumber(getDisplayNumber());
                    } else {
                        if (getFirstNumber() != null) {
                            setCalculatorSecondNumber(getFirstNumber());
                        } else {
                            setCalculatorSecondNumber(getDisplayNumber());
                        }
                    }
                    calculator.setPercent(getFirstNumber());
                } else {
                    calculator.setPercent(getResult());
                    setCalculatorSecondNumber(getFirstNumber());
                }

                if (percentPressed) {
                    deleteLastHistory();
                }
            }
        } else {
            if (getFirstNumber() == null && getResult() == null) {
                setCalculatorFirstNumber(getDisplayNumber());
            }
        }
    }

    //endregion

    //region Get

    /*
     * This method gets result number from calculator object
     */
    private BigDecimal getResult () {
        return calculator.getResult();
    }

    /*
     * This method gets first number from calculator object
     */
    private BigDecimal getFirstNumber () {
        return calculator.getNumberFirst();
    }

    /*
     * This method gets second number from calculator object
     */
    private BigDecimal getSecondNumber () {
        return calculator.getNumberSecond();
    }

    /*
     * This method gets parse number from general display
     */
    private BigDecimal getDisplayNumber () {
        return FormatterNumber.parseNumber(generalDisplay.getText());
    }

    /*
     * This method gets number from memory after memory recall button was pressed
     */
    private void getMemoryNumber (BigDecimal numberFromMemory) {
        if (historyOperations.isEmpty()) {
            clearUnary();
            setCalculatorFirstNumber(numberFromMemory);
            canBackspace = false;
        } else {
            if (binaryOperation != null) {
                setCalculatorSecondNumber(numberFromMemory);
            }
        }
    }


    //endregion

    //region Operations pressed

    @FXML
    public void binaryOperation (ActionEvent actionEvent) {
        String buttonID = ((Button) actionEvent.getSource()).getId();

        addHistoryUnaryNegate();

        if (equalWasPress) {
            binaryOperation = null;
            equalWasPress = false;
        }

        setNumbersBinary();

        if (binaryOperation == null) {
            binaryOperation = OperationsEnum.valueOf(buttonID.toUpperCase());
        }

        calculateBinaryOperation();
        if (!isError) {
            binaryOperation = OperationsEnum.valueOf(buttonID.toUpperCase());
            changeOperator();
            pointInText = false;
        }
        canBackspace = false;
        scrollOutOperationMemory();
    }


    @FXML
    public void unaryOperations (ActionEvent actionEvent) {
        String buttonID = ((Button) actionEvent.getSource()).getId();

        if (equalWasPress) {
            binaryOperation = null;
            equalWasPress = false;
        }

        if (buttonID.equals(oneDivideX.getId())) {
            unaryOperation = OperationsEnum.ONE_DIVIDE_X;
        } else {
            unaryOperation = OperationsEnum.valueOf(buttonID.toUpperCase());
        }

        addUnaryNumberHistory(setNumberUnary());
        calculateUnaryOperation();
        setTextOperationHistory(historyOperations + historyUnaryOperations);

        scrollOutOperationMemory();
    }

    @FXML
    public void percentOperation () {
        percentOperation = OperationsEnum.PERCENT;

        canChangeOperator = false;

        calculatePercent();
        historyOperations += Text.deleteNumberSeparator(formatterNumber(calculator.getResult()), separatorNumber);
        printResult(formatterNumber(calculator.getResult()));
        setTextOperationHistory(historyOperations);

        percentPressed = true;
        canBackspace = false;
        calculator.setPercent(null);
    }


    @FXML
    void pressedEqual () {
        clearError();
        equalWasPress = true;

        if (binaryOperation != null) {
            canChangeOperator = false;
            if (getSecondNumber() == null) {
                setBinarySecondNumber();
            }

            calculateBinaryOperation();
        }

        if (!isError) {
            clearHistory();
        }
        negatePressed = false;
        canBackspace = false;

        scrollOutOperationMemory();
    }
    //endregion

    //region Calculate
    /*
     * Method calculates binary operation,
     * calls print result method,
     * catches exception and
     * prohibits to backspace text in general display
     */
    private void calculateBinaryOperation () {
        try {
            calculator.calculator(binaryOperation);

            if (calculator.getResult() != null) {
                printResult(FormatterNumber.numberFormatter(getResult()));
                canBackspace = false;
            }

            if (!equalWasPress) {
                setCalculatorSecondNumber(null);
            }
        } catch (Exception e) {
            if (equalWasPress) {
                historyOperations += negateHistory;
                setTextOperationHistory(historyOperations);
            } else {
                setTextOperationHistory(historyOperations + ChangeHistory.getSymbol(binaryOperation));
            }
            printError(e);
        }
    }



    /*
     * Method calculates unary operation, call print result method and catches exception
     * and prohibits to backspace text in general display
     */
    private void calculateUnaryOperation () {
        try {
            calculator.calculator(unaryOperation);
            printResult(FormatterNumber.numberFormatter(calculator.getResult()));
            canBackspace = false;
        } catch (Exception e) {
            printError(e);
        }
    }

    /*
     * Method sets number for calculate percent operation, calculate percent and catches exception
     */
    private void calculatePercent () {
        setPercentNumber();

        calculator.calculator(percentOperation);
    }


    //endregion

    //region Print

    /*
     * Method outputs exception's message on general display
     */
    private void printError (Exception e) {
        isError = true;
        operationsIsDisable(true);
        memoryPanel.setDisable(true);
        generalDisplay.setStyle(firstStyleLabel);

        generalDisplay.setText(e.getMessage());
        resizeOutputText();
        scrollOutOperationMemory();
    }

    /*
     * Method outputs calculation's result on general display
     */
    private void printResult (String text) {
        try {
            isOverflow(FormatterNumber.parseNumber(text));
            generalDisplay.setText(text);
            resizeOutputText();
        } catch (OverflowException e) {
            printError(e);
        }
    }

    /*
     * Method formatters number for print to general display
     */
    private String formatterNumber (BigDecimal number) {
        return FormatterNumber.numberFormatter(number);
    }

    /*
     * Method checked number before prints to general display.
     * If number is more than max number or less min number throws OverflowException
     */
    private void isOverflow (BigDecimal result) throws OverflowException {
        boolean overflow = false;
        BigDecimal number = FormatterNumber.parseNumber(FormatterNumber.numberFormatter(result));

        if (number.abs().compareTo(BigDecimal.ONE) > 0) {
            overflow = number.abs().compareTo(MAX_NUMBER) >= 0;
        } else if (number.abs().compareTo(BigDecimal.ONE) < 0 && number.compareTo(BigDecimal.ZERO) != 0) {
            overflow = number.abs().compareTo(MIN_NUMBER) <= 0;
        }

        if (overflow) {
            throw new OverflowException("Overflow");
        }
    }
    //endregion

    //region ChangeHistory

    /*
     * Method sets text in operation history display
     */
    private void setTextOperationHistory (String text) {
        outOperationMemory.setText(text);
    }

    /*
     * Methods adds to operation history negate history
     * if negate button was pressed and calculation's result was printed
     * or memory recall button was pressed
     */
    private void addNegateHistory () {
        if (!canBackspace) {
            if (historyOperations.isEmpty()) {
                if (!historyUnaryOperations.isEmpty()) {
                    negateHistory = historyUnaryOperations;
                    historyUnaryOperations = emptyString;
                }
            } else {
                if (historyUnaryOperations.isEmpty() || percentPressed) {
                    deleteLastHistory();
                } else {
                    if (negateHistory.isEmpty()) {
                        negateHistory = historyUnaryOperations;
                        historyUnaryOperations = emptyString;
                    }
                }
            }
            if (negateHistory.isEmpty()) {
                negateHistory = Text.getTextLabel(generalDisplay, separatorNumber);
            }
            negateHistory = "negate(" + negateHistory + ")";

            setTextOperationHistory(historyOperations + negateHistory);
        }
    }

    /*
     * Method add to history unary and negate history if binary operation buttons was pressed
     */
    private void addHistoryUnaryNegate () {
        if (!negatePressed) {
            historyOperations += historyUnaryOperations;
        } else {
            historyOperations += negateHistory;
            negatePressed = false;
        }
    }

    /*
     * Method formatters second number and adds to operation history after binary buttons was pressed
     */
    private void addBinarySecondNumberHistory () {
        if (getSecondNumber() != null) {
            if (!percentPressed && negateHistory.isEmpty() && historyUnaryOperations.isEmpty()) {
                historyOperations += formatterNumberHistory(getSecondNumber());
                setTextOperationHistory(historyOperations);
                memoryPressed = false;
            } else {
                negateHistory = emptyString;
                historyUnaryOperations = emptyString;
            }
        }
    }

    /*
     * Method formatters number for print to history operation
     */
    private String formatterNumberHistory (BigDecimal number) {
        return ChangeHistory.formatterNumberHistory(number, separatorNumber);
    }

    /*
     * Method formatters first number and adds to operation history after binary operations buttons was pressed
     */
    private void addBinaryFirstNumberHistory () {
        if (historyOperations.isEmpty() && !negatePressed) {
            historyOperations += formatterNumberHistory(getFirstNumber());
            setTextOperationHistory(historyOperations);
        }
    }

    /*
     * Method formatters number and adds to operation history after unary operations buttons was pressed
     */
    private void addUnaryNumberHistory (BigDecimal unaryNumber) {
        if (historyUnaryOperations.isEmpty()) {
            historyUnaryOperations = formatterNumberHistory(unaryNumber);
        }
        if (!negateHistory.isEmpty()) {
            historyUnaryOperations = negateHistory;
            negateHistory = emptyString;
        }
        historyUnaryOperations = ChangeHistory.getSymbol(unaryOperation) + historyUnaryOperations + ")";
    }

    /*
     * Method deletes last history was written down in history
     */
    private void deleteLastHistory () {
        if (!negatePressed || percentPressed) {
            historyOperations = ChangeHistory.deleteLastHistory(canChangeOperator, binaryOperation, historyOperations);
        }
    }

    /*
     * Method clears all history
     */
    private void clearHistory () {
        historyOperations = emptyString;
        negateHistory = emptyString;
        historyUnaryOperations = emptyString;
        setTextOperationHistory(historyOperations);
    }

    /*
     * Method changes binary operation symbol in history operation
     */
    private void changeOperator () {
        historyOperations = ChangeHistory.changeOperator(canChangeOperator, binaryOperation, historyOperations);
        canChangeOperator = true;
        historyUnaryOperations = emptyString;
        setTextOperationHistory(historyOperations);
        scrollOutOperationMemory();
    }
    //endregion

    //region Scroll ChangeHistory
    @FXML
    void scrollButtonLeftPressed () {
        scrollPaneOperation.setHvalue(scrollPaneOperation.getHvalue() + moveScroll);
        scrollButtonRight.setVisible(true);
        if (scrollPaneOperation.getHvalue() == scrollPaneOperation.getHmax()) {
            scrollButtonLeft.setVisible(false);
            scrollButtonRight.setVisible(true);
        }
    }

    @FXML
    void scrollButtonRightPressed () {
        scrollPaneOperation.setHvalue(scrollPaneOperation.getHvalue() - moveScroll);
        scrollButtonLeft.setVisible(true);
        if (scrollPaneOperation.getHvalue() == 0) {
            scrollButtonRight.setVisible(false);
            scrollButtonLeft.setVisible(true);
        }
    }

    /*
     * Method scrolls history, if text width more then width history area
     */
    private void scrollOutOperationMemory () {
        moveScroll = ResizeDisplay.scrollText(scrollPaneOperation, outOperationMemory.getText(), scrollButtonLeft, scrollButtonRight);
    }
    //endregion

    //region Clear
    @FXML
    void clearAllC () {
        generalDisplay.setStyle(firstStyleLabel);
        generalDisplay.setText(defaultText);
        clearHistory();
        pointInText = false;
        canChangeOperator = false;
        binaryOperation = null;
        setCalculatorSecondNumber(null);
        setCalculatorFirstNumber(null);
        setCalculatorResult(null);
        scrollButtonRight.setVisible(false);
        scrollButtonLeft.setVisible(false);
        clearUnary();
        equalWasPress = false;
        operationsIsDisable(false);
        memoryPanel.setDisable(false);
        isError = false;
        negatePressed = false;
        negateHistory = emptyString;
        percentPressed = false;
        charValidInText = CHAR_MAX_INPUT;
        resizeOutputText();
        percentOperation = null;
        canBackspace = true;
    }

    @FXML
    void clearNumberCE () {
        generalDisplay.setStyle(firstStyleLabel);
        generalDisplay.setText(defaultText);
        pointInText = false;
        charValidInText = CHAR_MAX_INPUT;
        clearError();
        resizeOutputText();
    }

    /*
     * Methods sets default value for some variable for cleans general display
     * This method calls if calculator's number button was pressed,
     * result of calculation was printed,
     */
    private String clearDisplay (String out) {
        if (canChangeOperator || !canBackspace || memoryPressed || !historyUnaryOperations.isEmpty() || !negateHistory.isEmpty()) {
            canChangeOperator = false;
            memoryPressed = false;
            canBackspace = true;
            charValidInText = CHAR_MAX_INPUT;
            out = emptyString;
            percentOperation = null;
            pointInText = false;
            historyUnaryOperations = emptyString;
            negateHistory = emptyString;
            setTextOperationHistory(historyOperations);
        }
        return out;
    }

    /*
     * Method imitates press C button,
     * if exception massage was printed and some button was pressed
     */
    private void clearError () {
        if (isError) {
            C.fire();
        }
    }

    /*
     * Method cleans history of unary operation and unary operation
     */
    private void clearUnary () {
        historyUnaryOperations = emptyString;
        unaryOperation = null;
    }
    //endregion

    //region Memory

    @FXML
    void memoryStore () {
        if (memory == null) {
            memory = new Memory();
        }
        memory.setNumber(setMemoryNumber());

        memoryButtonDisable(false);
        memoryPressed = true;
    }

    @FXML
    void memoryAdd () {
        if (memory == null) {
            memory = new Memory();
            memoryButtonDisable(false);
        }
        memory.memoryAdd(setMemoryNumber());

        memoryPressed = true;
    }

    @FXML
    void memoryClear () {
        memoryPressed = true;
        memory = null;
        memoryButtonDisable(true);
    }

    @FXML
    void memoryRecall () {
        if (memory != null) {
            getMemoryNumber(memory.memoryRecall());
            printResult(formatterNumber(memory.memoryRecall()));
        }
        negateHistory = emptyString;
        memoryPressed = true;
    }

    @FXML
    void memorySubtract () {
        if (memory == null) {
            memory = new Memory();
            memoryButtonDisable(false);
        }
        memory.memorySubtract(setMemoryNumber());

        memoryPressed = true;
    }

    /*
     * Method makes memory clear and memory recall buttons disable or not disable
     */
    private void memoryButtonDisable (boolean disable) {
        memoryClear.setDisable(disable);
        memoryRecall.setDisable(disable);
    }

    /*
     * Method sets number in memory object
     */
    private BigDecimal setMemoryNumber () {
        BigDecimal number;

        if (getFirstNumber() != null) {
            number = getFirstNumber();
        } else {
            number = getDisplayNumber();
        }

        return number;
    }

    //endregion

    //region Window
    @FXML
    public void closeWindow () {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void maximizeWindow () {
        Stage stage = (Stage) maximizeButton.getScene().getWindow();
        ResizeWindow resizeWindow = new ResizeWindow(stage);
        resizeWindow.maximizeWindow(maximizeButton);
        resizeWindow.resizeButton(calculatorButtons);

        resizeOutputText();
        scrollOutOperationMemory();
    }

    @FXML
    public void hideWindow () {
        Stage stage = (Stage) hideButton.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    void draggedWindow (MouseEvent event) {
        Stage stage = (Stage) title.getScene().getWindow();
        stage.setX(event.getScreenX() - xOffset);
        stage.setY(event.getScreenY() - yOffset);
    }

    @FXML
    void clickWindow (MouseEvent event) {
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }


    //endregion

    //region Resize

    @FXML
    void resizeWindow () {
        Stage stage = (Stage) generalAnchorPane.getScene().getWindow();
        ResizeWindow resize = new ResizeWindow(stage);
        resize.resizeAllStage();
        scrollOutOperationMemory();
    }

    /*
     * Method resize text for general display and sets one
     */
    private void resizeOutputText () {
        generalDisplay.setFont(ResizeDisplay.fontSize(generalDisplay));
    }

    //endregion

}