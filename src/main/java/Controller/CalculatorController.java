package Controller;

import Model.Calculator;
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
import javafx.stage.Stage;
import javafx.util.Duration;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.HashMap;

import static Controller.Text.*;

/**
 * This class realizes controller for calculator application
 */
public class CalculatorController {

    //region FXML elements
    //region Number buttons
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
    //endregion

    //region Memory buttons
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
    //endregion

    //region Binary operation buttons
    @FXML
    private Button divide;

    @FXML
    private Button multiply;

    @FXML
    private Button subtract;

    @FXML
    private Button add;
    //endregion

    @FXML
    private Button equal;

    @FXML
    private Button percent;

    //region Unary operation buttons
    @FXML
    private Button sqrt;

    @FXML
    private Button sqr;

    @FXML
    private Button oneDivideX;
    //endregion

    //region Displays
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
    //endregion

    //region Clear buttons
    @FXML
    private Button CE;

    @FXML
    private Button C;
    //endregion

    //region Change number buttons
    @FXML
    private Button backspace;

    @FXML
    private Button plusMinus;

    @FXML
    private Button point;
    //endregion

    //region Other Window elements
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
    //endregion
    /**
     * Variable which keeps key and value of operation.
     * Key is id of button was pressed.
     * Value is enum variable which match some operation.
     */
    static HashMap<String, OperationsEnum> operation = new HashMap<>();

    static {
        operation.put("add", OperationsEnum.ADD);
        operation.put("subtract", OperationsEnum.SUBTRACT);
        operation.put("divide", OperationsEnum.DIVIDE);
        operation.put("multiply", OperationsEnum.MULTIPLY);

        operation.put("sqrt", OperationsEnum.SQRT);
        operation.put("sqr", OperationsEnum.SQR);
        operation.put("oneDivideX", OperationsEnum.ONE_DIVIDE_X);
        operation.put("percent", OperationsEnum.PERCENT);
    }

    /**
     * Maximal number of symbols which can be input in calculator
     */
    public final int CHAR_MAX_INPUT = 16;

    /**
     * Minimal invalid number which throws exception
     */
    public final BigDecimal MAX_INVALID_NUMBER = new BigDecimal("1E10000");

    /**
     * Min invalid number which throws exception
     */
    public final BigDecimal MIN_INVALID_NUMBER = new BigDecimal("1E-10000");

    private OperationsEnum binaryOperation;
    private OperationsEnum unaryOperation;
    private OperationsEnum negateOperation = OperationsEnum.NEGATE;

    private int charValidInText = 16;
    private String firstStyleLabel;
    private String defaultText = "0";
    private String decimalSeparate = ",";
    private String separatorNumber = " ";
    private String minus = "-";
    private String emptyString = "";

    private boolean commaInText = false;
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
    private CalculatorHistory history;

    @FXML
    void initialize () {
        firstStyleLabel = generalDisplay.getStyle();
        generalDisplay.setText(defaultText);
        equal.setDefaultButton(true);
        history = new CalculatorHistory(calculator);
    }

    /**
     * This method show calculator's left menu, if button show menu was pressed
     */
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

    // Method does some button disable or not disable
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

    /**
     * Method adds comma to text from general display,
     * outs result on general display.
     * If comma was added to text, valid count char in text increases.
     * If text equals default text, valid count char in text increases.
     * <p>
     * If comma contains in text yet, method does nothing.
     */
    @FXML
    void commaPressed () {
        String out = generalDisplay.getText();

        if (!commaInText) {
            if (!canBackspace) {
                out = defaultText;
                canBackspace = true;
            }
            if (out.equals(defaultText)) {
                charValidInText++;
            }
            out = out.concat(decimalSeparate);
            commaInText = true;
            charValidInText++;
            canChangeOperator = false;
            printResult(out);
        }

        memoryPressed = false;
    }

    private int lengthTextWithoutSeparator (String out) {
        return deleteNumberSeparator(out, separatorNumber).length();
    }

    private String formatterInputNumber (String text) {
        BigDecimal number = parseNumber(text);

        if (!text.contains(decimalSeparate)) {
            text = formatterNumber(number);
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
    void backspacePressed () {
        clearError();
        String out = generalDisplay.getText();
        if (canBackspace) {
            out = backspace(out);

            if (commaInText) {
                if (!out.contains(decimalSeparate)) {
                    commaInText = false;
                    charValidInText--;
                }
                printResult(out);
            } else {
                String outWithoutSeparator = deleteNumberSeparator(out, separatorNumber);
                BigDecimal number = new BigDecimal(outWithoutSeparator);
                printResult(formatterNumber(number));
            }
        }
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
    void numberPressed (ActionEvent actionEvent) {
        String buttonText = ((Button) actionEvent.getSource()).getText();

        clearError();

        String out = generalDisplay.getText();
        out = setDefaultText(out);

        if (out.equals(defaultText)) {
            out = buttonText;
        } else {
            if (lengthTextWithoutSeparator(out) < charValidInText) {
                out = out.concat(buttonText);
            }
        }
        printResult(formatterInputNumber(out));

        scrollOutOperationMemory();
        canChangeOperator = false;
    }


    private BigDecimal parseNumber (String out) {
        BigDecimal number = null;
        try {
            number = FormatterNumber.parseNumber(out);
        } catch (ParseException e) {
            printError(e);
        }
        return number;
    }

    /**
     * This method inserts {@code minus} to {@code out},
     * if {@code out} doesn't contains {@code minus}.
     * Also method sets
     */
    @FXML
    void negatePressed () {
        negatePressed = true;

        String out = generalDisplay.getText();
        char lastSymbol = out.toCharArray()[out.length() - 1];
        if (lastSymbol == ',') {
            out = addNegate(out);
            printResult(out);
        } else {
            if (!equalWasPress) {
                setNumbersBinary();
            } else {
                setCalculatorSecondNumber(null);
            }
            setNegateHistory();
        }

        if (out.contains(minus)) {
            charValidInText++;
        } else {
            charValidInText--;
        }

        scrollOutOperationMemory();
        printHistory();

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
     * This method sets first number in calculator object and adds it to history
     * after binary or unary operation buttons was pressed
     */
    private void setFirstNumber () {
        if (getFirstNumber() == null) {
            setCalculatorFirstNumber(getDisplayNumber());
        } else {
            if (history.historySize() == 0) {
                addHistoryNumber(getFirstNumber());
            }
        }
    }


    /*
     * This method sets second number in calculator object, adds it to history and adds first number to history
     * after binary or unary operation buttons was pressed
     */
    private void setSecondNumber () {
        if (!canChangeOperator) {
            if (equalWasPress) {
                if (!canBackspace) {
                    setCalculatorSecondNumber(getFirstNumber());
                } else {
                    setCalculatorSecondNumber(getDisplayNumber());
                }
            } else {
                if (!memoryPressed && canBackspace) {
                    if (getSecondNumber() == null) {
                        setCalculatorSecondNumber(getDisplayNumber());
                    } else {
                        addHistoryNumber(getSecondNumber());
                        printHistory();
                    }
                }
            }
        }
    }

    /*
     * This method sets first and second numbers in calculator object and adds it to history
     * after binary or unary operation buttons was pressed
     */
    private void setNumbersBinary () {
        if (!percentPressed) {
            if (binaryOperation == null) {
                setFirstNumber();
                setCalculatorSecondNumber(null);
            } else {
                setSecondNumber();
            }
        }

        setPercentOperation(null);
        if (!negatePressed) {
            percentPressed = false;
            memoryPressed = false;
        }
        printHistory();
    }

    /*
      This method sets first or second number in calculator object for calculate unary operation
       */
    private void setNumberUnary () {
        if (binaryOperation == null) {
            if (getFirstNumber() == null) {
                setCalculatorFirstNumber(getDisplayNumber());
            }
            setCalculatorSecondNumber(null);
            if (negatePressed || equalWasPress) {
                equalWasPress = false;
                addHistoryNumber(getFirstNumber());
            }
        } else {
            if (canBackspace) {
                setCalculatorSecondNumber(getDisplayNumber());
            } else {
                if (calculator.getResult() != null && !negatePressed) {
                    setCalculatorSecondNumber(getResult());
                }
            }
        }
    }

    private void setPercentOperation (OperationsEnum operationsEnum) {
        calculator.setPercentOperation(operationsEnum);
    }


    /*
     * This method sets result number in calculator object
     */
    private void clearCalculatorResult () {
        calculator.setResult(null);
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
            if (!equalWasPress) {
                if (getSecondNumber() == null) {
                    if (canBackspace) {
                        setCalculatorSecondNumber(getDisplayNumber());
                    } else {
                        if (getFirstNumber() != null) {
                            setCalculatorSecondNumber(getFirstNumber());
                        } else {
                            setCalculatorSecondNumber(getDisplayNumber());
                        }
                    }
                }
                setPercent(getSecondNumber());
            } else {
                if (negatePressed) {
                    setPercent(getResult().negate());
                } else {
                    setPercent(getResult());
                }
                setCalculatorSecondNumber(getFirstNumber());
            }

        } else {
            if (getFirstNumber() == null && getResult() == null) {
                setCalculatorFirstNumber(getDisplayNumber());
            }
        }
        negatePressed = false;
    }

    private void setPercent (BigDecimal percent) {
        calculator.setPercent(percent);
    }

    //endregion

    //region Get

    /**
     * Method returns calculator
     *
     * @return Calculator
     */
    public Calculator getCalculator () {
        return calculator;
    }

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
        return parseNumber(generalDisplay.getText());
    }

    /*
     * This method gets number from memory after memory recall button was pressed
     */
    private void getMemoryNumber (BigDecimal numberFromMemory) {
        if (binaryOperation != null) {
            setCalculatorSecondNumber(numberFromMemory);
        } else {
            setCalculatorFirstNumber(numberFromMemory);
            canBackspace = false;
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
    public void binaryOperation (ActionEvent actionEvent) {
        String buttonID = ((Button) actionEvent.getSource()).getId();

        negatePressed = false;
        history.clearHistoryUnaryOperations();
        history.clearNegateHistory();
        if (canBackspace) {
            setCalculatorSecondNumber(null);
        }

        if (equalWasPress) {
            binaryOperation = null;
            equalWasPress = false;
        }

        setNumbersBinary();

        if (unaryOperation != null) {
            calculator.setOperation(binaryOperation);
            unaryOperation = null;
        }
        if (binaryOperation != null) {
            calculate();
            calculator.setOperation(binaryOperation);
        }
        binaryOperation = operation.get(buttonID);
        calculator.setOperation(binaryOperation);

        canChangeOperator = true;
        canBackspace = false;
        commaInText = false;

        scrollOutOperationMemory();
        printHistory();
    }

    /**
     * Method calculate unary operations, if {@code sqr, sqrt, oneDivideX} was pressed.
     * Also method sets first or second number in calculator.
     * Prints result or exception.
     *
     * @param actionEvent Unary operation was pressed
     */
    @FXML
    public void unaryOperations (ActionEvent actionEvent) {
        String buttonID = ((Button) actionEvent.getSource()).getId();

        if (equalWasPress) {
            binaryOperation = null;
            history.clearHistoryUnaryOperations();
        }

        unaryOperation = operation.get(buttonID);
        setNumberUnary();
        setOperation(unaryOperation);

        calculate();
        negatePressed = false;
        printHistory();
        scrollOutOperationMemory();
    }


    /**
     * Method calculates percent operation, if {@code percent} was pressed
     *
     * @param actionEvent Percent button was pressed
     */
    @FXML
    public void percentOperation (ActionEvent actionEvent) {
        String buttonID = ((Button) actionEvent.getSource()).getId();
        if (negatePressed) {
            if (binaryOperation != null) {
                setOperation(binaryOperation);
                deleteLastHistory();
            }
        }
        percentPressed = true;

        setPercentNumber();
        setPercentOperation(operation.get(buttonID));
        calculate();

        setPercent(null);
        printHistory();
    }

    private void setOperation (OperationsEnum operationsEnum) {
        calculator.setOperation(operationsEnum);
    }

    /**
     * Methods calculates operation, if {@code equal} was pressed.
     * Sets second number in calculator if {@code binaryOperation} not null.
     * Clears percent operation, sets default value {@code charValidInText}.
     */
    @FXML
    void pressedEqual () {
        clearError();
        clearPercent();
        equalWasPress = true;

        history.clearHistoryUnaryOperations();

        if (binaryOperation != null) {
            setOperation(binaryOperation);
            canChangeOperator = false;
            if (getSecondNumber() == null) {
                setSecondNumber();
            }
            calculate();
        } else {
            setNumbersBinary();
        }

        if (!isError) {
            clearHistory();
        }
        negatePressed = false;
        canBackspace = false;
        memoryPressed = false;
        charValidInText = CHAR_MAX_INPUT;

        scrollOutOperationMemory();
    }

    private void clearPercent () {
        if (percentPressed) {
            percentPressed = false;
            setPercentOperation(null);
            setPercent(null);
        }
    }


    //endregion

    //region Calculate
    /*
     * Method calculates operation,
     * calls print result method,
     * catches exception and
     * prohibits to backspace text in general display
     */
    private void calculate () {
        try {
            calculator.calculate();

            if (getResult() != null) {
                printResult(formatterNumber(getResult()));
                if (!negatePressed) {
                    canBackspace = false;
                }
            }
            if (!equalWasPress && !percentPressed && unaryOperation == null) {
                setCalculatorSecondNumber(null);
            }

        } catch (Exception e) {
            printError(e);
        }
    }

    //endregion

    //region Print

    /*
     * Method outputs exception's message on general display,
     * makes some buttons disable, resizes text on general display.
     */
    public void printError (Exception e) {
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
            isOverflow(parseNumber(text));
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
        return FormatterNumber.formatterNumber(number);
    }

    /*
     * Method checked number before prints to general display.
     * If number is equal or more than max invalid number throws OverflowException.
     * Also if number is equal or less than min invalid number throws OverflowException.
     */
    private void isOverflow (BigDecimal result) throws OverflowException {
        boolean overflow = false;

        BigDecimal number = parseNumber(formatterNumber(result));

        if (number != null) {
            if (compareOne(number) > 0) {
                overflow = number.abs().compareTo(MAX_INVALID_NUMBER) >= 0;

            } else if (compareOne(number) < 0 && compareZero(number) != 0) {
                overflow = number.abs().compareTo(MIN_INVALID_NUMBER) <= 0;
            }
        }

        if (overflow) {
            throw new OverflowException("Overflow");
        }
    }

    private int compareZero (BigDecimal number) {
        return number.abs().compareTo(BigDecimal.ZERO);
    }

    private int compareOne (BigDecimal number) {
        return number.abs().compareTo(BigDecimal.ONE);
    }
    //endregion

    //region History
    private void printHistory () {
        outOperationMemory.setText(history.getChangedHistory());
    }

    private void clearHistory () {
        outOperationMemory.setText(history.clearHistory());
    }

    /*
     * This method sets negative number in calculator object after negative button was pressed,
     * and calls method which adds operation in calculator history.
     */
    private void setNegateHistory () {
        setOperation(negateOperation);
        unaryOperation = negateOperation;
        calculate();
        if (binaryOperation == null) {
            clearCalculatorResult();
        }
        addNegateOperationHistory();
    }

    /*
     * Method adds negate operation in calculator history.
     */
    private void addNegateOperationHistory () {
        if (history.getHistoryUnaryOperations().isEmpty()) {
            if (canBackspace) {
                history.deleteOldHistory();
            } else {
                BigDecimal numberAddHistory;

                if (getResult() != null) {
                    deleteLastHistory();
                    numberAddHistory = getResult().negate();
                } else {
                    deleteLastHistory();
                    numberAddHistory = getFirstNumber().negate();
                }
                if (memoryPressed || percentPressed) {
                    deleteLastHistory();
                }
                addHistoryNumber(numberAddHistory);
                setOperation(negateOperation);
            }
        }
    }

    private void addHistoryNumber (BigDecimal number) {
        history.addHistoryNumber(number);
    }

    private void deleteLastHistory () {
        history.deleteLastHistory();
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
            scrollButtonRight.setVisible(true);
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

    /*
     * Method scrolls history, if text width more then width history area
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
        //Displays
        generalDisplay.setStyle(firstStyleLabel);
        generalDisplay.setText(defaultText);
        outOperationMemory.setText(emptyString);

        unaryOperation = null;
        binaryOperation = null;
        clearPercent();
        calculator.clearCalculator();
        clearHistory();
        clearBinary();
        equalWasPress = false;


        commaInText = false;
        canChangeOperator = false;
        negatePressed = false;
        canBackspace = true;
        memoryPressed = false;

        scrollButtonRight.setVisible(false);
        scrollButtonLeft.setVisible(false);

        operationsIsDisable(false);
        memoryPanel.setDisable(false);

        charValidInText = CHAR_MAX_INPUT;
        resizeOutputText();
        isError = false;
    }

    /**
     * Method sets default value to general display
     */
    @FXML
    void clearNumberCE () {
        generalDisplay.setStyle(firstStyleLabel);
        generalDisplay.setText(defaultText);
        commaInText = false;
        charValidInText = CHAR_MAX_INPUT;
        clearError();
        resizeOutputText();
    }

    /*
     * Methods sets default value for some variable for cleans general display
     * This method calls if calculator's number button was pressed,
     * result of calculation was printed,
     */
    private String setDefaultText (String out) {
        if (!canBackspace || memoryPressed || canChangeOperator) {
            memoryPressed = false;
            canBackspace = true;
            charValidInText = CHAR_MAX_INPUT;
            out = emptyString;
            commaInText = false;
        }
        if (binaryOperation == null || equalWasPress) {
            clearHistory();
            clearBinary();

            equalWasPress = false;
            memoryPressed = false;
        }
        return out;
    }

    private void clearBinary () {
        binaryOperation = null;
        calculator.setOperation(null);
        calculator.setNumberFirst(null);
        calculator.setResult(null);
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
    //endregion

    //region Memory

    /**
     * Method saves number in memory, if {@code memoryStore} button was pressed
     */
    @FXML
    void memoryStore () {
        if (memory == null) {
            memory = new Memory();
        }
        memory.setNumber(setMemoryNumber());

        memoryButtonDisable(false);
        memoryPressed = true;
    }

    /**
     * Method adds number to number in memory, if {@code memoryAdd} button was pressed.
     * Sets variable {@code memoryPressed} value true.
     */
    @FXML
    void memoryAdd () {
        if (memory == null) {
            memory = new Memory();
            memoryButtonDisable(false);
        }
        memory.memoryAdd(setMemoryNumber());

        memoryPressed = true;
    }

    /**
     * Method clears memory, if {@code memoryClear} button was pressed.
     * Makes some memory buttons disable.
     */
    @FXML
    void memoryClear () {
        memory.memoryClear();
        memory = null;
        memoryButtonDisable(true);
    }

    /**
     * Method recalls number from memory, if {@code memoryClear} button was pressed.
     * Also method sets value to calculator number and prints number from memory.
     * Sets variable {@code memoryPressed} value true.
     */
    @FXML
    void memoryRecall () {
        if (memory != null) {
            getMemoryNumber(memory.memoryRecall());
            BigDecimal numberMemory = memory.memoryRecall();
            printResult(formatterNumber(numberMemory));
        }
        memoryPressed = true;
    }

    /**
     * Method subtracts number to number in memory, if {@code memorySubtract} button was pressed.
     * If number in memory equal null, in memory sets negate number.
     * Sets variable {@code memoryPressed} value true.
     */
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

    /**
     * Close calculator, if {@code cancel} was pressed
     */
    @FXML
    public void closeWindow () {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    /**
     * Maximize window of calculator and resize all button, if {@code maximize} was pressed
     */
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
        Stage stage = (Stage) hideButton.getScene().getWindow();
        stage.setIconified(true);
    }

    /**
     * If mouse was dragged method changes location of calculator
     *
     * @param event Mouse event
     */
    @FXML
    void draggedWindow (MouseEvent event) {
        Stage stage = (Stage) title.getScene().getWindow();
        stage.setX(event.getScreenX() - xOffset);
        stage.setY(event.getScreenY() - yOffset);
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

    /*
     * Method resize text for general display and sets one
     */
    private void resizeOutputText () {
        generalDisplay.setFont(ResizeDisplay.fontSize(generalDisplay));
    }

    //endregion

}