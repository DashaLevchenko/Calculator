package Controller;

import Model.Memory;
import Model.Model;
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
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class realizes controller for calculator application
 */
public class Controller {

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

    //Binary operation buttons
    @FXML
    private Button divide, multiply, subtract, add;

    @FXML
    private Button equal;

    @FXML
    private Button percent;

    //Unary operation buttons
    @FXML
    private Button sqrt, sqr, oneDivideX;


    //region Displays
    @FXML
    private Label generalDisplay, outOperationMemory;

    @FXML
    private ScrollPane scrollPaneOperation;

    @FXML
    private Button scrollButtonLeft, scrollButtonRight;


    //endregion

    //Clear buttons
    @FXML
    private Button CE, C;


    //Change number buttons
    @FXML
    private Button backspace, plusMinus, point;


    //region Other Window elements
    @FXML
    private Label title, textStandard;

    @FXML
    private Button cancel, maximizeButton, hideButton;

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
    static final HashMap<String, OperationsEnum> operation = new HashMap<>();

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
     * Maximal invalid number which throws exception
     */
    public final BigDecimal MAX_INVALID_NUMBER = new BigDecimal("1E10000");

    /**
     * Minimal invalid number which throws exception
     */
    public final BigDecimal MIN_INVALID_NUMBER = new BigDecimal("1E-10000");

    /**
     * Default text for {@code generalDisplay}
     */
    private final String DEFAULT_TEXT = "0";


    private String firstStyleLabel;
    private String decimalSeparate = ",";
    private String emptyString = "";

    private boolean commaInText = false;
    private boolean canChangeOperator = false;

    private boolean showLeftMenu = false;
    private boolean isError = false;

    private boolean memoryPressed = false;
    private boolean canBackspace = true;


    private int charValidInText = 16;
    private double moveScroll;
    private double xOffset = 0;
    private double yOffset = 0;

    private Memory memory;

    private CalculatorHistory historyOut = new CalculatorHistory();
    private ArrayList<Object> formula = new ArrayList<>();
    BigDecimal result;


    @FXML
    void initialize () {
        firstStyleLabel = generalDisplay.getStyle();
        generalDisplay.setText(DEFAULT_TEXT);
        equal.setDefaultButton(true);
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
                out = DEFAULT_TEXT;
                canBackspace = true;
            }
            if (out.equals(DEFAULT_TEXT)) {
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
        String outWithoutSeparator = out.replace(" ", "");
        return outWithoutSeparator.length();
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
            } else {
                String outWithoutSeparator = out.replace(" ", "");
                BigDecimal number = new BigDecimal(outWithoutSeparator);
                out = formatterNumber(number);
            }
            printResult(out);
        }
    }

    /**
     * This method deletes last symbol in text
     *
     * @param text Text which need to change
     * @return Text was changed
     */
    public String backspace (String text) {
        int symbolsInTextWithoutComma = text.replace(decimalSeparate, "").length();

        if (symbolsInTextWithoutComma > 0) {
            int textLength = text.length();
            int minLengthWithMinus = 2;
            int minLength = 1;
            String minus = "-";

            if ((textLength == minLengthWithMinus && text.contains(minus)) ||
                    textLength == minLength) {
                text = DEFAULT_TEXT;
            } else {
                int lastSymbol = textLength - 1;
                text = new StringBuilder(text).deleteCharAt(lastSymbol).toString();
            }
        }

        return text;
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
        clearError();
        String out = generalDisplay.getText();
        out = setDefaultText(out);

        String buttonText = ((Button) actionEvent.getSource()).getText();

        if (out.equals(DEFAULT_TEXT)) {
            out = buttonText;
        } else {
            if (lengthTextWithoutSeparator(out) < charValidInText) {
                out = out.concat(buttonText);
            }
        }
        printResult(formatterInputNumber(out));
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

        String out = generalDisplay.getText();
        int indexLastSymbol = out.length() - 1;
        String lastSymbol = String.valueOf(out.toCharArray()[indexLastSymbol]);


        if (lastSymbol.equals(decimalSeparate) || canBackspace) {
            out = addNegate(out);
            printResult(out);
        } else {
            setNumber();
            setOperation(OperationsEnum.NEGATE);
            calculate();
        }

        String minus = "-";
        if (out.contains(minus)) {
            charValidInText++;
        } else {
            charValidInText--;
        }
        memoryPressed = false;
    }

    /**
     * Method inserts "-" before text.
     * Example: before: "9", after: "-9"
     *
     * @param text Text need to change
     * @return Text with "-"
     */
    public String addNegate (String text) {
        if (!text.equals(DEFAULT_TEXT)) {
            char minus = '-';
            char firstChar = text.charAt(0);
            if (firstChar != minus) {
                text = minus + text;
            } else {
                text = new StringBuilder(text).deleteCharAt(0).toString();
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


    /**
     * Method returns calculator
     *
     * @return Calculator
     */

    private BigDecimal getDisplayNumber () {
        return parseNumber(generalDisplay.getText());
    }


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
        if (!canChangeOperator) {
            setNumber();
        }
        String buttonID = ((Button) actionEvent.getSource()).getId();
        setOperation(operation.get(buttonID));
        canChangeOperator = true;
        calculate();
    }

    private void setNumber () {
        if (canBackspace) {
            formula.add(getDisplayNumber());
            canChangeOperator = false;
        }
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
if(buttonID.equals("sqrt")){
    System.out.println(";");
}
        setNumber();
        OperationsEnum unaryOperation = operation.get(buttonID);
        setOperation(unaryOperation);

        calculate();
    }


    /**
     * Method calculates percent operation, if {@code percent} was pressed
     */
    @FXML
    public void percentOperation () {
        setNumber();
        setOperation(OperationsEnum.PERCENT);
        calculate();

    }

    private void setOperation (OperationsEnum operationsEnum) {
        formula.add(operationsEnum);
    }

    /**
     * Methods calculates operation, if {@code equal} was pressed.
     * Sets second number in calculator if {@code binaryOperation} not null.
     * Clears percent operation, sets default value {@code charValidInText}.
     */
    @FXML
    void pressedEqual () {
        clearError();
        setNumber();
        setOperation(OperationsEnum.EQUAL);
        calculate();

        if (!isError) {
            clearHistory();
        }

        canBackspace = false;
        memoryPressed = false;
        charValidInText = CHAR_MAX_INPUT;

        scrollOutOperationMemory();
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
        Model calculator = new Model();
        try {
            result = calculator.calculator(formula);

            if (result != null) {
                String resultFormatted = FormatterNumber.formatterNumber(result);
                printResult(resultFormatted);
            }

            canBackspace = false;
        } catch (Exception e) {
            printError(e);
        } finally {
            printHistory(calculator);

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
        String errorMessage = e.getMessage();
        generalDisplay.setText(errorMessage);

        resizeOutputText();
        scrollOutOperationMemory();
    }

    /*
     * Method outputs calculation's result on general display
     */
    private void printResult (String text) {
        try {
            BigDecimal numberCheckedOverflow = parseNumber(text);
            isOverflow(numberCheckedOverflow);
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
    private void printHistory (Model calculator) {
        historyOut.clearHistory();
        String historyChanged = historyOut.getChangedHistory(calculator);
        outOperationMemory.setText(historyChanged);

        scrollOutOperationMemory();
    }

    private void clearHistory () {
        String historyClean = historyOut.clearHistory();
        outOperationMemory.setText(historyClean);
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
        generalDisplay.setText(DEFAULT_TEXT);
        outOperationMemory.setText(emptyString);

        clearHistory();


        formula.clear();

        commaInText = false;
        canChangeOperator = false;
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

    private void clearUnaryHistory () {
        historyOut.clearHistoryUnaryOperations();
    }

    /**
     * Method sets default value to general display
     */
    @FXML
    void clearNumberCE () {
        generalDisplay.setStyle(firstStyleLabel);
        generalDisplay.setText(DEFAULT_TEXT);
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
            canBackspace = true;
            charValidInText = CHAR_MAX_INPUT;
            commaInText = false;

            out = emptyString;
        }

        memoryPressed = false;
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
            BigDecimal numberMemory = memory.memoryRecall();
            String out = formatterNumber(numberMemory);
            formula.add(numberMemory);
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