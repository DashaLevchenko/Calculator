package Controller;

import Model.Binary;
import Model.Memory;
import Model.OperationsEnum;
import Model.Unary;
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

public class Calculator_Controller {
    //region FXML object
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
    private Button DIVIDE;

    @FXML
    private Button MULTIPLY;

    @FXML
    private Button SUBTRACT;

    @FXML
    private Button ADD;

    @FXML
    private Button EQUAL;

    @FXML
    private Button PERCENT;

    @FXML
    private Button SQRT;

    @FXML
    private Button SQR;

    @FXML
    private Button ONE_DIVIDE_X;

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
    private Button Backspace;

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
    private AnchorPane leftMenu;

    @FXML
    private AnchorPane generalAnchorPane;
    //endregion

    private final int CHAR_MAX_INPUT = 16;
    private final BigDecimal MAX_NUMBER_INTEGER = new BigDecimal("1E10000");
    private final BigDecimal MIN_NUMBER_DECIMAL = new BigDecimal("1E-10000");

    private OperationsEnum binaryOperation;
    private OperationsEnum unaryOperation;
    private OperationsEnum percentOperation;

    private int charValidInText = 16;
    private String firstStyleLabel;
    private String historyOperations = "";
    private String historyUnaryOperations = "";
    private String negateHistory = "";

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

    private Memory memory;
    private boolean percentPressed = false;
    private Binary calculateBinary = new Binary();
    private Unary calculateUnary = new Unary();
    private String defaultText = "0";
    private CharSequence decimalSeparate = ",";
    private String separatorNumber = " ";


    @FXML
    void initialize () {
        firstStyleLabel = generalDisplay.getStyle();
        generalDisplay.setText("0");
        EQUAL.setDefaultButton(true);
    }

    @FXML
    void showLeftMenu () {
        TranslateTransition transition = new TranslateTransition(Duration.seconds(0.1), leftMenu);
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
        PERCENT.setDisable(disable);
        SQRT.setDisable(disable);
        SQR.setDisable(disable);
        ONE_DIVIDE_X.setDisable(disable);
        DIVIDE.setDisable(disable);
        MULTIPLY.setDisable(disable);
        SUBTRACT.setDisable(disable);
        ADD.setDisable(disable);
        plusMinus.setDisable(disable);
        point.setDisable(disable);
    }

    //region Input

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
            out = Input.backspace(out);

            if (pointInText) {
                if (!out.contains(decimalSeparate)) {
                    pointInText = false;
                    charValidInText--;
                }
                printResult(out);
            } else {
                printResult(formatterNumber(new BigDecimal(Input.deleteNumberSeparator(out, separatorNumber))));
            }
        }
    }

    @FXML
    void inputNumber (ActionEvent actionEvent) {
        String buttonText = ((Button) actionEvent.getSource()).getText();

        clearError();

        String out = Input.getTextLabel(generalDisplay, separatorNumber);
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

        if (equalWasPress && historyUnaryOperations.isEmpty()) {
            calculateBinary.setNumberFirst(null);
        }
    }

    @FXML
    void negate () {
        negatePressed = true;

        addNegateHistory();

        setNumberNegate();
        String out = Input.getTextLabel(generalDisplay, "");
        out = Input.addNegate(out);

        if (out.contains("-")) {
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
                SQRT.fire();
            } else {
                two.fire();
            }
        } else if (keyCode == KeyCode.DIGIT3 || keyCode == KeyCode.NUMPAD3) {
            three.fire();
        } else if (keyCode == KeyCode.DIGIT4 || keyCode == KeyCode.NUMPAD4) {
            four.fire();
        } else if (keyCode == KeyCode.DIGIT5 || keyCode == KeyCode.NUMPAD5) {
            if (event.isShiftDown()) {
                PERCENT.fire();
            } else {
                five.fire();
            }
        } else if (keyCode == KeyCode.DIGIT6 || keyCode == KeyCode.NUMPAD6) {
            six.fire();
        } else if (keyCode == KeyCode.DIGIT7 || keyCode == KeyCode.NUMPAD7) {
            seven.fire();
        } else if (keyCode == KeyCode.DIGIT8 || keyCode == KeyCode.NUMPAD8) {
            if (event.isShiftDown()) {
                MULTIPLY.fire();
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
            Backspace.fire();
        } else if (keyCode == KeyCode.COMMA) {
            point.fire();
        } else if (keyCode == KeyCode.ADD || (keyCode == KeyCode.EQUALS && event.isShiftDown())) {
            ADD.fire();
        } else if (keyCode == KeyCode.MINUS || keyCode == KeyCode.SUBTRACT) {
            SUBTRACT.fire();
        } else if (keyCode == KeyCode.MULTIPLY) {
            MULTIPLY.fire();
        } else if (keyCode == KeyCode.SLASH || keyCode == KeyCode.DIVIDE) {
            DIVIDE.fire();
        } else if (keyCode == KeyCode.ENTER) {
            EQUAL.fire();
        } else if (keyCode == KeyCode.F9) {
            plusMinus.fire();
        } else if (keyCode == KeyCode.DELETE) {
            CE.fire();
        } else if (keyCode == KeyCode.R) {
            if (event.isControlDown()) {
                memoryRecall.fire();
            } else {
                ONE_DIVIDE_X.fire();
            }
        } else if (keyCode == KeyCode.Q) {
            if (event.isControlDown()) {
                memorySubtract.fire();
            } else {
                SQR.fire();
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
        if (binaryOperation != null) {
            if (!equalWasPress) {
                if (!canBackspace) {
                    if (numberFromBinarySecondNumber() == null) {
                        calculateBinary.setNumberSecond(numberFromBinaryFirstNumber().negate());
                    } else {
                        calculateBinary.setNumberSecond(numberFromBinarySecondNumber().negate());
                    }
                } else {
                    calculateBinary.setNumberSecond(numberFromDisplay().negate());
                }
            } else {
                if (numberFromBinaryFirstNumber() != null) {
                    calculateBinary.setNumberFirst(numberFromBinaryFirstNumber().negate());
                    calculateBinary.setResult(numberFromBinaryFirstNumber());
                }
            }
        } else {
            if (unaryOperation != null) {
                calculateUnary.setResult(calculateUnary.getResult().negate());
                calculateUnary.setNumber(calculateUnary.getNumber().negate());
            }
            if (historyOperations.isEmpty()) {
                if (numberFromBinaryFirstNumber() != null) {
                    calculateBinary.setNumberFirst(numberFromBinaryFirstNumber().negate());
                }
            }
        }
    }

    private void setNumbersBinary () {
        if (numberFromBinaryFirstNumber() == null) {
            if (calculateBinary.getResult() != null) {
                calculateBinary.setNumberFirst(numberFromBinaryResult());
            } else {
                calculateBinary.setNumberFirst(numberFromDisplay());
            }
            addBinaryFirstNumberHistory();
        } else {
            if (canBackspace || memoryPressed) {
                if (!memoryPressed) {
                    calculateBinary.setNumberSecond(numberFromDisplay());
                }

                addBinarySecondNumberHistory();
            } else if (equalWasPress) {
                if (calculateBinary.getResult() != null) {
                    calculateBinary.setNumberSecond(numberFromBinaryResult());
                } else {
                    if (numberFromBinaryFirstNumber() != null) {
                        calculateBinary.setNumberSecond(numberFromBinaryFirstNumber());
                    } else {
                        calculateBinary.setNumberSecond(numberFromDisplay());
                    }
                }
            }
            addBinaryFirstNumberHistory();
        }
        percentPressed = false;
        clearUnary();
    }

    private void setNumberUnary () {
        if (calculateUnary.getNumber() == null) {
            if (canBackspace) {
                calculateUnary.setNumber(numberFromDisplay());
            } else {
                if (calculateBinary.getResult() != null) {
                    calculateUnary.setNumber(calculateBinary.getResult());
                    calculateBinary.setResult(null);
                } else {
                    calculateUnary.setNumber(numberFromDisplay());
                }
            }
        }
    }
    //endregion

    //region Get Number from ...
    private BigDecimal numberFromBinaryResult () {
        return calculateBinary.getResult();
    }

    private BigDecimal numberFromBinaryFirstNumber () {
        return calculateBinary.getNumberFirst();
    }

    private BigDecimal numberFromBinarySecondNumber () {
        return calculateBinary.getNumberSecond();
    }

    private BigDecimal numberFromDisplay () {
        return NumberFormatter.parseNumber(generalDisplay.getText());
    }

    private void numberFromMemory (BigDecimal numberFromMemory) {
        if (historyOperations.isEmpty()) {
            clearUnary();
            calculateBinary.setNumberFirst(numberFromMemory);
            calculateUnary.setNumber(numberFromMemory);
            canBackspace = false;
        } else {
            if (binaryOperation != null) {
                calculateBinary.setNumberSecond(numberFromMemory);
            }
        }
    }


    //endregion

    //region Operations pressed
    @FXML
    public void binaryOperation (ActionEvent actionEvent) {
        if (!negatePressed) {
            historyOperations += historyUnaryOperations;
        } else {
            historyOperations += negateHistory;
            negateHistory = "";
            negatePressed = false;
        }

        if (equalWasPress) {
            clearBinary();
            equalWasPress = false;
        }
        setNumbersBinary();
        unaryOperation = null;

        if (binaryOperation == null) {
            binaryOperation = OperationsEnum.valueOf(((Button) actionEvent.getSource()).getId());
        }

        calculateBinaryOperation();
        if (!isError) {
            binaryOperation = OperationsEnum.valueOf(((Button) actionEvent.getSource()).getId());
            changeOperator();
            pointInText = false;
        }
        canBackspace = false;
        scrollOutOperationMemory();
    }

    @FXML
    public void unaryOperations (ActionEvent actionEvent) {
        unaryOperation = OperationsEnum.valueOf(((Button) actionEvent.getSource()).getId());
        setNumberUnary();
        addUnaryNumberHistory();
        calculateUnaryOperation();
        outOperationMemory.setText(historyOperations + historyUnaryOperations);

        scrollOutOperationMemory();
    }

    @FXML
    public void percentOperation () {
        percentOperation = OperationsEnum.PERCENT;

        canChangeOperator = false;

        if (binaryOperation != null) {
            if (binaryOperation.equals(OperationsEnum.DIVIDE) || binaryOperation.equals(OperationsEnum.MULTIPLY)) {
                calculateBinary.percent(numberFromDisplay());
            } else {
                if (!equalWasPress) {
                    if (canBackspace) {
                        calculateBinary.setNumberSecond(numberFromDisplay());
                    } else {
                        if (numberFromBinarySecondNumber() == null) {
                            calculateBinary.setNumberSecond(numberFromBinaryFirstNumber());
                        }
                    }
                    calculateBinary.percent(numberFromBinaryFirstNumber(), numberFromBinarySecondNumber());
                    calculateBinary.setNumberSecond(calculateBinary.getResult());
                } else {
                    if (canBackspace) {
                        calculateBinary.setNumberSecond(numberFromBinaryFirstNumber());
                        calculateBinary.setNumberFirst(numberFromDisplay());
                    } else {
                        calculateBinary.setNumberFirst(calculateBinary.getResult());
                    }
                    calculateBinary.percent(numberFromBinaryFirstNumber(), numberFromBinarySecondNumber());
                }
            }
            if (percentPressed) {
                deleteLastHistory();
            }

            historyOperations += Input.deleteNumberSeparator(formatterNumber(calculateBinary.getResult()), separatorNumber);
            printResult(formatterNumber(calculateBinary.getResult()));
        } else {
            calculateUnary.setNumber(numberFromDisplay());
            calculateUnary.calculateUnary(percentOperation);
            printResult(formatterNumber(calculateUnary.getResult()));
            historyOperations = Input.deleteNumberSeparator(formatterNumber(calculateUnary.getResult()), separatorNumber);
        }

        outOperationMemory.setText(historyOperations);
        percentPressed = true;
        canBackspace = false;
    }

    @FXML
    public void pressedEqual () {
        clearError();
        equalWasPress = true;

        if (binaryOperation != null) {
            canChangeOperator = false;
            if (numberFromBinarySecondNumber() == null) {
                if (!canBackspace) {
                    setNumbersBinary();
                } else {
                    calculateBinary.setNumberSecond(numberFromDisplay());
                }
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
            calculateBinary.calculateBinary(binaryOperation);
            if (calculateBinary.getResult() != null) {
                printResult(NumberFormatter.numberFormatter(calculateBinary.getResult()));
                canBackspace = false;
            }
            if (!equalWasPress) {
                calculateBinary.setResult(null);
                calculateBinary.setNumberSecond(null);
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
            calculateUnary.calculateUnary(unaryOperation);

            if (calculateUnary.getResult() != null) {
                if (historyOperations.isEmpty()) {
                    calculateBinary.setNumberFirst(calculateUnary.getResult());
                    binaryOperation = null;
                } else {
                    if (binaryOperation != null) {
                        calculateBinary.setNumberSecond(calculateUnary.getResult());
                    } else {
                        calculateBinary.setNumberFirst(calculateUnary.getResult());
                    }
                }
                printResult(NumberFormatter.numberFormatter(calculateUnary.getResult()));
                canBackspace = false;
            }
        } catch (Exception e) {
            printError(e);
        }
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
        } catch (ArithmeticException e) {
            printError(e);
        }
    }

    private String formatterNumber (BigDecimal number) {
        return NumberFormatter.numberFormatter(number);
    }

    private void isOverflow (BigDecimal result) throws ArithmeticException {
        boolean overflow = false;
        BigDecimal number = NumberFormatter.parseNumber(NumberFormatter.numberFormatter(result));

        if (number.abs().compareTo(BigDecimal.ONE) > 0) {
            overflow = number.abs().compareTo(MAX_NUMBER_INTEGER) >= 0;
        } else if (number.abs().compareTo(BigDecimal.ONE) < 0 && number.compareTo(BigDecimal.ZERO) != 0) {
            overflow = number.abs().compareTo(MIN_NUMBER_DECIMAL) <= 0;
        }

        if (overflow) {
            throw new ArithmeticException("Overflow");
        }

    }
    //endregion

    //region History
    private void addNegateHistory () {
        if (!canBackspace) {
            if (historyOperations.isEmpty()) {
                if (!historyUnaryOperations.isEmpty()) {
                    negateHistory = historyUnaryOperations;
                    historyUnaryOperations = "";
                }
            } else {
                if (historyUnaryOperations.isEmpty()||percentPressed) {
                    deleteLastHistory();
                } else {
                    if (negateHistory.isEmpty()) {
                        negateHistory = historyUnaryOperations;
                        historyUnaryOperations = "";
                    }
                }
            }
            if (negateHistory.isEmpty()) {
                negateHistory = Input.getTextLabel(generalDisplay, separatorNumber);
            }
            negateHistory = "negate(" + negateHistory + ")";

            outOperationMemory.setText(historyOperations + negateHistory);
        }
    }

    private void addBinarySecondNumberHistory () {
        if (numberFromBinarySecondNumber() != null && !percentPressed) {
            historyOperations += formatterNumberHistory(numberFromBinarySecondNumber());
            outOperationMemory.setText(historyOperations);
            memoryPressed = false;
        }
    }

    private String formatterNumberHistory (BigDecimal number) {
        return History.formatterNumberHistory(number, separatorNumber);
    }

    private void addBinaryFirstNumberHistory () {
        if (historyOperations.isEmpty() && !negatePressed) {
            historyOperations += formatterNumberHistory(numberFromBinaryFirstNumber());
            outOperationMemory.setText(historyOperations);
        }
    }

    private void addUnaryNumberHistory () {
        if (historyUnaryOperations.isEmpty()) {
            historyUnaryOperations = formatterNumberHistory(calculateUnary.getNumber());
        }
        if (!negateHistory.isEmpty()) {
            historyUnaryOperations = negateHistory;
            negateHistory = "";
        }
        historyUnaryOperations = History.getSymbol(unaryOperation) + historyUnaryOperations + ")";
    }

    private void deleteLastHistory () {
        if (!negatePressed||percentPressed) {
            historyOperations = History.deleteLastHistory(canChangeOperator, binaryOperation, historyOperations);
        }
    }

    private void clearHistory () {
        historyOperations = "";
        negateHistory = "";
        historyUnaryOperations = "";
        outOperationMemory.setText(historyOperations);
    }

    private void changeOperator () {
        historyOperations = History.changeOperator(canChangeOperator, binaryOperation, historyOperations);
        canChangeOperator = true;
        historyUnaryOperations = "";
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
        generalDisplay.setText("0");
        clearHistory();
        pointInText = false;
        canChangeOperator = false;
        binaryOperation = null;
        calculateBinary.setNumberSecond(null);
        calculateBinary.setNumberFirst(null);
        calculateBinary.setResult(null);
        scrollButtonRight.setVisible(false);
        scrollButtonLeft.setVisible(false);
        clearUnary();
        equalWasPress = false;
        operationsIsDisable(false);
        memoryPanel.setDisable(false);
        isError = false;
        negatePressed = false;
        negateHistory = "";
        percentPressed = false;
        charValidInText = CHAR_MAX_INPUT;
        resizeOutputText();
        percentOperation = null;
        canBackspace = true;
    }

    @FXML
    void clearNumberCE () {
        generalDisplay.setStyle(firstStyleLabel);
        generalDisplay.setText("0");
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
            out = "";
            percentOperation = null;
            pointInText = false;
            historyUnaryOperations = "";
            negateHistory = "";
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
        calculateUnary.setResult(null);
        calculateUnary.setNumber(null);
        historyUnaryOperations = "";
        unaryOperation = null;
    }

    private void clearBinary () {
        if (historyOperations.isEmpty()) {
            calculateBinary.setNumberSecond(null);
            calculateBinary.setResult(null);
        } else {
            calculateBinary.setNumberSecond(null);
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
        if (memory != null) {
            memory.memoryAdd(setMemoryNumber());
        } else {
            memory = new Memory();
            memory.memoryAdd(setMemoryNumber());
            memoryButtonDisable(false);
        }
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
            numberFromMemory(memory.memoryRecall());
            printResult(NumberFormatter.numberFormatter(memory.memoryRecall()));

        }
        memoryPressed = true;
    }

    @FXML
    void memorySubtract () {
        if (memory != null) {
            memory.memorySubtract(setMemoryNumber());
        } else {
            memory = new Memory();
            memory.memorySubtract(setMemoryNumber());
            memoryButtonDisable(false);
        }
        memoryPressed = true;
    }

    private void memoryButtonDisable (boolean disable) {
        memoryClear.setDisable(disable);
        memoryRecall.setDisable(disable);
    }

    private BigDecimal setMemoryNumber () {
        BigDecimal number;
        if (binaryOperation != null) {
            if (canBackspace) {
                number = numberFromDisplay();
            } else {
                if (calculateBinary.getResult() != null) {
                    number = calculateBinary.getResult();
                } else {
                    number = numberFromBinaryFirstNumber();
                }
            }
        } else if (unaryOperation != null && calculateUnary.getResult() != null) {
            number = calculateUnary.getResult();
        } else {
            number = numberFromDisplay();
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

        if (!stage.isMaximized()) {
            stage.setMaximized(true);
            maximizeButton.setText("");
        } else {
            stage.setMaximized(false);
            maximizeButton.setText("");
        }
        resizeFontButton();
        resizeOutputText();
        scrollOutOperationMemory();
    }

    @FXML
    public void hideWindow () {
        Stage stage = (Stage) maximizeButton.getScene().getWindow();
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

    private void resizeFontButton () {
        Stage stage = (Stage) maximizeButton.getScene().getWindow();
        Font newFontNumber;
        Font newFontOperations;
        if (stage.isMaximized()) {
            newFontNumber = Font.font("Segoe UI Semibold", 34);
            newFontOperations = Font.font("Segoe MDL2 Assets", 20);

            PERCENT.setFont(Font.font("Calculator MDL2 Assets", 20));
            SQR.setFont(Font.font("Calculator MDL2 Assets", 25));
            ONE_DIVIDE_X.setFont(Font.font("Calculator MDL2 Assets", 22));
            DIVIDE.setFont(Font.font("Calculator MDL2 Assets", 20));
            EQUAL.setFont(Font.font("Calculator MDL2 Assets", 20));
        } else {
            newFontNumber = Font.font("Segoe UI Semibold", 24);
            newFontOperations = Font.font("Segoe MDL2 Assets", 15);

            PERCENT.setFont(Font.font("Calculator MDL2 Assets", 15));
            SQR.setFont(Font.font("Calculator MDL2 Assets", 19));
            ONE_DIVIDE_X.setFont(Font.font("Calculator MDL2 Assets", 17));
            DIVIDE.setFont(Font.font("Calculator MDL2 Assets", 15));
            EQUAL.setFont(Font.font("Calculator MDL2 Assets", 15));
        }

        one.setFont(newFontNumber);
        two.setFont(newFontNumber);
        three.setFont(newFontNumber);
        four.setFont(newFontNumber);
        five.setFont(newFontNumber);
        six.setFont(newFontNumber);
        seven.setFont(newFontNumber);
        eight.setFont(newFontNumber);
        nine.setFont(newFontNumber);
        zero.setFont(newFontNumber);
        point.setFont(newFontNumber);

        plusMinus.setFont(newFontOperations);
        ADD.setFont(newFontOperations);
        MULTIPLY.setFont(newFontOperations);
        SUBTRACT.setFont(newFontOperations);
        SQRT.setFont(newFontOperations);
        CE.setFont(newFontOperations);
        C.setFont(newFontOperations);
        Backspace.setFont(newFontOperations);
    }

    //endregion

}