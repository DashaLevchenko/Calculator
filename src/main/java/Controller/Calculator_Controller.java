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

public class Calculator_Controller {
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
                printResult(NumberFormatter.formatterInputNumber(out));
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

    private void setBinaryFirstNumber () {
        if (calculator.getNumberFirst() == null) {
            setCalculatorFirstNumber(getDisplayNumber());
        }
        addBinaryFirstNumberHistory();
    }


    private void setNumbersBinary () {
        if (binaryOperation == null) {
            setBinaryFirstNumber();
            setCalculatorSecondNumber(null);
        } else {
            setBinarySecondNumber();
        }
        percentPressed = false;
    }

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

    private void setCalculatorResult (BigDecimal number) {
        calculator.setResult(number);
    }

    private void setCalculatorFirstNumber (BigDecimal number) {
        calculator.setNumberFirst(number);
    }

    private void setCalculatorSecondNumber (BigDecimal number) {
        calculator.setNumberSecond(number);
    }

    //endregion

    //region Get Number from ...
    private BigDecimal getResult () {
        return calculator.getResult();
    }

    private BigDecimal getFirstNumber () {
        return calculator.getNumberFirst();
    }

    private BigDecimal getSecondNumber () {
        return calculator.getNumberSecond();
    }

    private BigDecimal getDisplayNumber () {
        return NumberFormatter.parseNumber(generalDisplay.getText());
    }

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

        addUnaryNegateHistory();

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
        outOperationMemory.setText(historyOperations + historyUnaryOperations);

        scrollOutOperationMemory();
    }

    @FXML
    public void percentOperation () {
        percentOperation = OperationsEnum.PERCENT;

        canChangeOperator = false;

        calculatePercent();
        historyOperations += Text.deleteNumberSeparator(formatterNumber(calculator.getResult()), separatorNumber);
        printResult(formatterNumber(calculator.getResult()));
        outOperationMemory.setText(historyOperations);

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
    private void calculateBinaryOperation () {
        try {
            calculator.calculator(binaryOperation);

            if (calculator.getResult() != null) {
                printResult(NumberFormatter.numberFormatter(getResult()));
                canBackspace = false;
            }

            if (!equalWasPress) {
                setCalculatorSecondNumber(null);
            }
        } catch (Exception e) {
            if (equalWasPress) {
                historyOperations += negateHistory;
                outOperationMemory.setText(historyOperations);
            } else {
                outOperationMemory.setText(historyOperations + History.getSymbol(binaryOperation));
            }
            printError(e);
        }
    }

    private void calculateUnaryOperation () {
        try {
            calculator.calculator(unaryOperation);
            printResult(NumberFormatter.numberFormatter(calculator.getResult()));
            canBackspace = false;
        } catch (Exception e) {
            printError(e);
        }
    }

    private void calculatePercent () {
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

        calculator.calculator(percentOperation);
    }
    //endregion

    //region Print
    private void printError (Exception e) {
        isError = true;
        operationsIsDisable(true);
        memoryPanel.setDisable(true);
        generalDisplay.setStyle(firstStyleLabel);

        generalDisplay.setText(e.getMessage());
        resizeOutputText();
        scrollOutOperationMemory();
    }

    private void printResult (String text) {
        try {
            isOverflow(NumberFormatter.parseNumber(text));
            generalDisplay.setText(text);
            resizeOutputText();
        } catch (OverflowException e) {
            printError(e);
        }
    }

    private String formatterNumber (BigDecimal number) {
        return NumberFormatter.numberFormatter(number);
    }

    private void isOverflow (BigDecimal result) throws OverflowException {
        boolean overflow = false;
        BigDecimal number = NumberFormatter.parseNumber(NumberFormatter.numberFormatter(result));

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

    //region History
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

            outOperationMemory.setText(historyOperations + negateHistory);
        }
    }

    private void addUnaryNegateHistory () {
        if (!negatePressed) {
            historyOperations += historyUnaryOperations;
        } else {
            historyOperations += negateHistory;
            negatePressed = false;
        }
    }

    private void addBinarySecondNumberHistory () {
        if (getSecondNumber() != null) {
            if (!percentPressed && negateHistory.isEmpty() && historyUnaryOperations.isEmpty()) {
                historyOperations += formatterNumberHistory(getSecondNumber());
                outOperationMemory.setText(historyOperations);
                memoryPressed = false;
            } else {
                negateHistory = emptyString;
                historyUnaryOperations = emptyString;
            }
        }
    }

    private String formatterNumberHistory (BigDecimal number) {
        return History.formatterNumberHistory(number, separatorNumber);
    }

    private void addBinaryFirstNumberHistory () {
        if (historyOperations.isEmpty() && !negatePressed) {
            historyOperations += formatterNumberHistory(getFirstNumber());
            outOperationMemory.setText(historyOperations);
        }
    }

    private void addUnaryNumberHistory (BigDecimal unaryNumber) {
        if (historyUnaryOperations.isEmpty()) {
            historyUnaryOperations = formatterNumberHistory(unaryNumber);
        }
        if (!negateHistory.isEmpty()) {
            historyUnaryOperations = negateHistory;
            negateHistory = emptyString;
        }
        historyUnaryOperations = History.getSymbol(unaryOperation) + historyUnaryOperations + ")";
    }

    private void deleteLastHistory () {
        if (!negatePressed || percentPressed) {
            historyOperations = History.deleteLastHistory(canChangeOperator, binaryOperation, historyOperations);
        }
    }

    private void clearHistory () {
        historyOperations = emptyString;
        negateHistory = emptyString;
        historyUnaryOperations = emptyString;
        outOperationMemory.setText(historyOperations);
    }

    private void changeOperator () {
        historyOperations = History.changeOperator(canChangeOperator, binaryOperation, historyOperations);
        canChangeOperator = true;
        historyUnaryOperations = emptyString;
        outOperationMemory.setText(historyOperations);
        scrollOutOperationMemory();
    }
    //endregion

    //region Scroll History
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
            outOperationMemory.setText(historyOperations);
        }
        return out;
    }

    private void clearError () {
        if (isError) {
            C.fire();
        }
    }

    private void clearUnary () {
        historyUnaryOperations = emptyString;
        unaryOperation = null;
    }

    private void clearBinary () {
        if (outOperationMemory.getText().isEmpty()) {
            binaryOperation = null;
        }
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

    private void memoryButtonDisable (boolean disable) {
        memoryClear.setDisable(disable);
        memoryRecall.setDisable(disable);
    }

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

    private void resizeOutputText () {
        generalDisplay.setFont(ResizeDisplay.fontSize(generalDisplay));
    }


    //endregion

}