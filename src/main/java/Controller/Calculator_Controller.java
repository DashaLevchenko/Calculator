package Controller;

import Model.Binary;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.math.BigDecimal;

public class Calculator_Controller {
    //region FXML object
    //region Number Button
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

    //region Memory
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

    //region Operations
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
    //endregion

    //region Change text
    @FXML
    private Label outText;

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
    //endregion

    //region Window resize button
    @FXML
    private Button cancel;

    @FXML
    private Button maximizeButton;

    //endregion

    @FXML
    private AnchorPane leftMenu;

    @FXML
    private AnchorPane generalAnchorPane;


    //endregion

    private static final double MAX_FONT_SIZE = 71;
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
    Binary binaryBinary = new Binary();
    Unary calculateUnary = new Unary();

    @FXML
    void initialize() {
        firstStyleLabel = outText.getStyle();
        outText.setText("0");
        EQUAL.setDefaultButton(true);
    }

    @FXML
    void resizeWindow() {
        Stage stage = (Stage) generalAnchorPane.getScene().getWindow();
        Resize resize = new Resize(stage);
        resize.resizeAllStage();
        scrollOutOperationMemory();

    }

    @FXML
    void showLeftMenu() {
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

    @FXML
    public void binaryOperation(ActionEvent actionEvent) {
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
        setNumbers();
        unaryOperation = null;

        if (binaryOperation == null) {
            binaryOperation = OperationsEnum.valueOf(((Button) actionEvent.getSource()).getId());
        }

        calculateBinaryOperation();
        if (!isError) {
            binaryOperation = OperationsEnum.valueOf(((Button) actionEvent.getSource()).getId());
            changeOperator();

            if (binaryBinary.getResult() != null) {
                printResult(binaryBinary.getResult());
                binaryBinary.setResult(null);
                binaryBinary.setNumberSecond(null);
                canBackspace = false;

            }
        }
        canBackspace = false;
        scrollOutOperationMemory();
    }


    private void setNumbers() {
        if (binaryBinary.getNumberFirst() == null) {
            if (binaryBinary.getResult() != null) {
                binaryBinary.setNumberFirst(binaryBinary.getResult());
            } else {
                binaryBinary.setNumberFirst(NumberFormatter.parseNumber(outText.getText()));
            }
            if (historyOperations.isEmpty() && !negatePressed) {
                historyOperations += NumberFormatter.formatterNumber(binaryBinary.getNumberFirst()).replace(" ", "");
                outOperationMemory.setText(historyOperations);
            }

        } else {
//            if (!equalWasPress) {
            if (canBackspace || memoryPressed) {
                if (!memoryPressed) {
                    binaryBinary.setNumberSecond(NumberFormatter.parseNumber(outText.getText()));
                }

                if (binaryBinary.getNumberSecond() != null && !percentPressed) {
                    historyOperations += NumberFormatter.formatterNumber(binaryBinary.getNumberSecond()).replace(" ", "");
                    outOperationMemory.setText(historyOperations);
                    memoryPressed = false;
                }
            }
//            } else {
//                binaryBinary.setNumberSecond(null);
//                equalWasPress = false;
//                percentPressed = false;
//            }
            if (historyOperations.isEmpty() && !negatePressed) {
                historyOperations += NumberFormatter.formatterNumber(binaryBinary.getNumberFirst()).replace(" ", "");
                outOperationMemory.setText(historyOperations);
            }

        }
        percentPressed = false;
        clearUnary();
    }


    private void clearUnary() {
        calculateUnary.setResult(null);
        calculateUnary.setNumber(null);
        historyUnaryOperations = "";
        unaryOperation = null;
    }

    private void clearBinary() {
        if (historyOperations.isEmpty()) {
//            if (!memoryPressed) {
//                binaryBinary.setNumberFirst(null);
//            }
            binaryBinary.setNumberSecond(null);
            binaryBinary.setResult(null);
        } else {
            binaryBinary.setNumberSecond(null);
        }
    }


    @FXML
    public void unaryOperations(ActionEvent actionEvent) {
        unaryOperation = OperationsEnum.valueOf(((Button) actionEvent.getSource()).getId());
        setNumberUnary();

        if (historyUnaryOperations.isEmpty()) {
            historyUnaryOperations = NumberFormatter.formatterNumber(calculateUnary.getNumber()).replace(" ", "");
        }
        if (negateHistory.isEmpty()) {
            historyUnaryOperations = getOperationUnary(unaryOperation) + historyUnaryOperations + ")";
        } else {
            historyUnaryOperations = getOperationUnary(unaryOperation) + negateHistory + ")";
            negateHistory = "";
        }

        outOperationMemory.setText(historyOperations + historyUnaryOperations);

        try {
            calculateUnary.calculateUnary(unaryOperation);
        } catch (Exception e) {
            printError(e);
        }

        if (!isError) {
            if (calculateUnary.getResult() != null) {
                if (historyOperations.isEmpty()) {
                    binaryBinary.setNumberFirst(calculateUnary.getResult());
                    binaryOperation = null;
                } else {
                    if (binaryOperation != null) {
                        binaryBinary.setNumberSecond(calculateUnary.getResult());
                    } else {
                        binaryBinary.setNumberFirst(calculateUnary.getResult());
                    }
                }
                printResult(calculateUnary.getResult());
                canBackspace = false;
            }

            scrollOutOperationMemory();
        }
    }

    private void setNumberUnary() {
        if (calculateUnary.getNumber() == null) {
            if (canBackspace) {
                calculateUnary.setNumber(NumberFormatter.parseNumber(outText.getText()));
            } else {
                if (binaryBinary.getResult() != null) {
                    calculateUnary.setNumber(binaryBinary.getResult());
                    binaryBinary.setResult(null);
                } else {
                    calculateUnary.setNumber(NumberFormatter.parseNumber(outText.getText()));
                }
            }
        }
    }

    @FXML
    public void percentOperation() {
        percentOperation = OperationsEnum.PERCENT;

        canChangeOperator = false;

        if (binaryOperation != null) {
            if (binaryOperation.equals(OperationsEnum.DIVIDE) || binaryOperation.equals(OperationsEnum.MULTIPLY)) {
                binaryBinary.percent(NumberFormatter.parseNumber(outText.getText()));
            } else {
                if (!equalWasPress) {
                    if (canBackspace) {
                        binaryBinary.setNumberSecond(NumberFormatter.parseNumber(outText.getText()));
                    } else {
                        if (binaryBinary.getNumberSecond() == null) {
                            binaryBinary.setNumberSecond(binaryBinary.getNumberFirst());
                        }
                    }
                    binaryBinary.percent(binaryBinary.getNumberFirst(), binaryBinary.getNumberSecond());
                    binaryBinary.setNumberSecond(binaryBinary.getResult());
                } else {
                    if (canBackspace) {
                        binaryBinary.setNumberSecond(binaryBinary.getNumberFirst());
                        binaryBinary.setNumberFirst(NumberFormatter.parseNumber(outText.getText()));
                    } else {
                        binaryBinary.setNumberFirst(binaryBinary.getResult());
                    }
                    binaryBinary.percent(binaryBinary.getNumberFirst(), binaryBinary.getNumberSecond());
                }
            }
            if (percentPressed) {
                deleteLastHistory();
            }

            historyOperations += NumberFormatter.formatterNumber(binaryBinary.getResult()).replace(" ", "");
            printResult(binaryBinary.getResult());
        } else {
            calculateUnary.setNumber(NumberFormatter.parseNumber(outText.getText()));
            calculateUnary.calculateUnary(percentOperation);
            printResult(calculateUnary.getResult());
            historyOperations = NumberFormatter.formatterNumber(calculateUnary.getResult()).replace(" ", "");
        }

        outOperationMemory.setText(historyOperations);
        percentPressed = true;
        canBackspace = false;

//        try {
//            if (binaryOperation != null) {
//                if (binaryOperation.equals(OperationsEnum.DIVIDE) || binaryOperation.equals(OperationsEnum.MULTIPLY)) {
//                    if (!equalWasPress) {
//                        if (numberSecondBinaryOperations == null) {
//                            numberSecondBinaryOperations = numberFirstBinaryOperations;
//                        }
//                        result = Binary.calculate(BigDecimal.ONE, numberSecondBinaryOperations, percentOperation);
//                        isOverflow(result);
//                        numberSecondBinaryOperations = result;
//                    } else {
//                        result = Binary.calculate(BigDecimal.ONE, numberFirstBinaryOperations, percentOperation);
//                        isOverflow(result);
//                        numberFirstBinaryOperations = result;
//                        historyOperations = "";
//                        outOperationMemory.setText(historyOperations);
//                    }
//                } else {
//                    if (!equalWasPress) {
//                        if (numberSecondBinaryOperations == null) {
//                            numberSecondBinaryOperations = numberFirstBinaryOperations;
//                        }
//                        result = Binary.calculate(numberFirstBinaryOperations, numberSecondBinaryOperations, percentOperation);
//                        isOverflow(result);
//                        numberSecondBinaryOperations = result;
//                        percentPressed = true;
//                    } else {
//                        if (!percentPressed) {
//                            numberSecondBinaryOperations = numberFirstBinaryOperations;
//                            percentPressed = true;
//                        }
//                        result = Binary.calculate(NumberFormatter.parseNumber(outText.getText()), numberFirstBinaryOperations, percentOperation);
//                        isOverflow(result);
//                        historyOperations = "";
//                        outOperationMemory.setText(historyOperations);
//                    }
//                }
//            } else {
//                result = Binary.calculate(numberFirstBinaryOperations, percentOperation);
//            }
//        } catch (ArithmeticException e) {
//            printError(e);
//        }
//        historyOperations += NumberFormatter.formatterNumber(result).replace(" ", "");
//        printResult(NumberFormatter.formatterNumber(result));
//
//        outOperationMemory.setText(historyOperations);
//        canChangeOperator = false;
//        scrollOutOperationMemory();
    }

    @FXML
    void scrollButtonLeftPressed() {
        scrollPaneOperation.setHvalue(scrollPaneOperation.getHvalue() + moveScroll);
        scrollButtonRight.setVisible(true);
        if (scrollPaneOperation.getHvalue() == scrollPaneOperation.getHmax()) {
            scrollButtonLeft.setVisible(false);
            scrollButtonRight.setVisible(true);
        }
    }

    @FXML
    void scrollButtonRightPressed() {
        scrollPaneOperation.setHvalue(scrollPaneOperation.getHvalue() - moveScroll);
        scrollButtonLeft.setVisible(true);
        if (scrollPaneOperation.getHvalue() == 0) {
            scrollButtonRight.setVisible(false);
            scrollButtonLeft.setVisible(true);
        }
    }

    @FXML
    public void pressedEqual() {
        if (isError) {
            C.fire();
        } else {
            if (binaryOperation != null) {
                canChangeOperator = false;
                if (binaryBinary.getNumberSecond() == null) {
                    if (!canBackspace) {
                        if (binaryBinary.getResult() != null) {
                            binaryBinary.setNumberSecond(binaryBinary.getNumberFirst());
                        } else {
                            if (binaryBinary.getNumberFirst() != null) {
                                binaryBinary.setNumberSecond(binaryBinary.getNumberFirst());
                            } else {
                                binaryBinary.setNumberSecond(NumberFormatter.parseNumber(outText.getText()));
                            }
                        }
                    } else {
                        binaryBinary.setNumberSecond(NumberFormatter.parseNumber(outText.getText()));
                    }
                }
                calculateBinaryOperation();
            }
            if (!isError) {
                if (binaryBinary.getResult() != null) {
                    printResult(binaryBinary.getResult());
                    canBackspace = false;
                    if (!isError) {
                        clearHistory();
                    }
                }
                if (unaryOperation != null) {
                    clearHistory();
                }
                if (negatePressed) {
                    negateHistory = "";
                    negatePressed = false;
                }

                if (historyOperations.equals("0")) {
                    historyOperations = "";
                    outOperationMemory.setText(historyOperations);
                }

                equalWasPress = true;
                canBackspace = false;
            } else {
                outOperationMemory.setText(historyOperations);
            }
        }
        scrollOutOperationMemory();


    }

    private void clearHistory() {
//        if (!isError) {
        historyOperations = "";
        historyUnaryOperations = "";
        outOperationMemory.setText(historyOperations);
//        }
    }

    @FXML
    void backspace() {
        if (isError) {
            C.fire();
        }
        String out = outText.getText();
        if (canBackspace) {
            if (out.replace(",", "").length() > 0) {
                if ((out.length() == 2 && out.contains("-")) ||
                        out.length() == 1) {
                    CE.fire();
                } else {
                    if (out.charAt(out.length() - 1) == ',') {
                        pointInText = false;
                        charValidInText--;
                    }

                    out = new StringBuilder(out).deleteCharAt(out.length() - 1).toString();
                    if (out.charAt(out.length() - 1) == ',') {
                        outText.setText(out);
                    } else {
                        outText.setText(NumberFormatter.formatterInputNumber(out.replace(" ", "")));
                    }
                    resizeOutputText();
                }
            }
        }
    }

    @FXML
    void clearAllC() {
        outText.setStyle(firstStyleLabel);
        outText.setText("0");
        clearHistory();
        pointInText = false;
        canChangeOperator = false;
        binaryOperation = null;
        oldBinaryOperation = null;
        binaryBinary.setNumberSecond(null);
        binaryBinary.setNumberFirst(null);
        binaryBinary.setResult(null);
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
    void clearNumberCE() {
        outText.setStyle(firstStyleLabel);
        outText.setText("0");
        pointInText = false;
        charValidInText = CHAR_MAX_INPUT;
        if (isError) {
            C.fire();
        }
        resizeOutputText();
    }

    @FXML
    void keyPressed(KeyEvent event) {
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

    @FXML
    public void number(ActionEvent actionEvent) {
        String buttonText = ((Button) actionEvent.getSource()).getText();


        if (isError) {
            C.fire();
        }

        String out = outText.getText().replace(" ", "");
        out = cleanDisplay(out);

        if (out.isEmpty() || out.equals("0")) {
            outText.setText(buttonText);
        } else {
            if (out.length() < charValidInText) {
                outText.setText(NumberFormatter.formatterInputNumber(out + buttonText));
            }
        }
        resizeOutputText();
        operationsIsDisable(false);
        scrollOutOperationMemory();
        if (equalWasPress && historyUnaryOperations.isEmpty()) {
            binaryBinary.setNumberFirst(null);
        }


    }

    private String cleanDisplay(String out) {
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

    @FXML
    public void cancelButton() {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void maximizeWindow() {
        Stage stage = (Stage) maximizeButton.getScene().getWindow();

        if (!stage.isMaximized()) {
            stage.setMaximized(true);
            maximizeButton.setText("\uE923");
        } else {
            stage.setMaximized(false);
            maximizeButton.setText("\uE922");
        }
        maximizeFontButton();
        resizeOutputText();
        scrollOutOperationMemory();
    }

    private void maximizeFontButton() {
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

    @FXML
    public void hideWindow() {
        Stage stage = (Stage) maximizeButton.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    void draggedWindow(MouseEvent event) {
        Stage stage = (Stage) title.getScene().getWindow();
        stage.setX(event.getScreenX() - xOffset);
        stage.setY(event.getScreenY() - yOffset);
    }

    @FXML
    void clickWindow(MouseEvent event) {
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }

    String negateHistory = "";

    @FXML
    public void negate() {
        negatePressed = true;
        addNegateHistory();

        StringBuilder out = new StringBuilder(outText.getText());
        if (binaryOperation != null) {
            if (!equalWasPress) {
                if (!canBackspace) {
                    if (binaryBinary.getNumberSecond() == null) {
                        binaryBinary.setNumberSecond(binaryBinary.getNumberFirst().negate());
                    } else {
                        binaryBinary.setNumberSecond(binaryBinary.getNumberSecond().negate());
                    }
                } else {
                    binaryBinary.setNumberSecond(NumberFormatter.parseNumber(outText.getText()).negate());
                }
            } else {
                if (binaryBinary.getNumberFirst() != null) {
                    binaryBinary.setNumberFirst(binaryBinary.getNumberFirst().negate());
                    binaryBinary.setResult(binaryBinary.getNumberFirst());
                }
            }
        } else {
            if (unaryOperation != null) {
                calculateUnary.setResult(calculateUnary.getResult().negate());
                calculateUnary.setNumber(calculateUnary.getNumber().negate());
            }
            if (historyOperations.isEmpty()) {
                if (binaryBinary.getNumberFirst() != null) {
                    binaryBinary.setNumberFirst(binaryBinary.getNumberFirst().negate());
                }
            }
        }

        if (!out.toString().equals("0")) {
            if (out.charAt(0) != '-') {
                out = out.insert(0, '-');
                charValidInText++;
            } else {
                out.deleteCharAt(0);
                charValidInText = CHAR_MAX_INPUT;
            }
        }


        outText.setText(out.toString());
        resizeOutputText();
        scrollOutOperationMemory();
        canChangeOperator = false;

    }

    private void addNegateHistory() {
        if (!canBackspace) {
            if (historyOperations.isEmpty()) {
                if (!historyUnaryOperations.isEmpty()) {
                    negateHistory = historyUnaryOperations;
                    historyUnaryOperations = "";
                }
            } else {
                if (historyUnaryOperations.isEmpty()) {
                    deleteLastHistory();
                } else {
                    if (negateHistory.isEmpty()) {
                        negateHistory = historyUnaryOperations;
                        historyUnaryOperations = "";
                    }
                }
            }
            if (negateHistory.isEmpty()) {
                negateHistory = outText.getText().replace(" ", "");
            }
            negateHistory = "negate(" + negateHistory + ")";

            outOperationMemory.setText(historyOperations + negateHistory);
        }
    }

    private void deleteLastHistory() {
        int partToDelete = historyOperations.split(" ").length;
        String numberDelete = historyOperations.split(" ")[partToDelete - 1];
        int charStart = historyOperations.length() - numberDelete.length();

        if (numberDelete.length() > 0) {
            if (!canChangeOperator) {
                int charEnd = historyOperations.length();
                if (numberDelete.length() == 1) {
                    if (negateHistory.isEmpty()) {
                        historyOperations = new StringBuilder(historyOperations).deleteCharAt(historyOperations.length() - 1).toString();
                    }
                } else {
                    if (historyOperations.substring(charStart, charEnd).equals(numberDelete)) {
                        historyOperations = new StringBuilder(historyOperations).delete(charStart, charEnd).toString();
                    }
                }
            } else {
                if (!negatePressed) {
                    historyOperations = new StringBuilder(historyOperations).delete(historyOperations.length() - getSymbol(binaryOperation).length(), historyOperations.length()).toString();
                }
            }
        }
    }

    private void memoryButtonDisable(boolean disable) {
        memoryClear.setDisable(disable);
        memoryRecall.setDisable(disable);
    }

    @FXML
    void memoryStore() {
        if (memory == null) {
            memory = new Memory();
        }
        memory.setNumber(setMemoryNumber());

        memoryButtonDisable(false);
        memoryPressed = true;
    }

    private BigDecimal setMemoryNumber() {
        BigDecimal number;
        if (binaryOperation != null) {
            if (canBackspace) {
                number = NumberFormatter.parseNumber(outText.getText());
            } else {
                if (binaryBinary.getResult() != null) {
                    number = binaryBinary.getResult();
                } else {
                    number = binaryBinary.getNumberFirst();
                }
            }
        } else if (unaryOperation != null && calculateUnary.getResult() != null) {
            number = calculateUnary.getResult();
        } else {
            number = NumberFormatter.parseNumber(outText.getText());
        }
        return number;
    }

    @FXML
    void memoryAdd() {
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
    void memoryClear() {
        memoryPressed = true;
        memory = null;
        memoryButtonDisable(true);
    }

    @FXML
    void memoryRecall() {
        if (memory != null) {
            getMemoryNumber(memory.memoryRecall());
            printResult(memory.memoryRecall());

        }
        memoryPressed = true;
    }

    private void getMemoryNumber(BigDecimal number) {
        if (historyOperations.isEmpty()) {
            clearUnary();
            binaryBinary.setNumberFirst(number);
            calculateUnary.setNumber(number);
            canBackspace = false;
        } else {
            if (binaryOperation != null) {
                binaryBinary.setNumberSecond(number);
            }
        }


    }

    @FXML
    void memorySubtract() {
        if (memory != null) {
            memory.memorySubtract(setMemoryNumber());
        } else {
            memory = new Memory();
            memory.memorySubtract(setMemoryNumber());
            memoryButtonDisable(false);
        }
        memoryPressed = true;
    }

    @FXML
    public void commaMouseClick(ActionEvent actionEvent) {
        String buttonText = ((Button) actionEvent.getSource()).getText();
        String out = outText.getText();

        if (canChangeOperator || !historyUnaryOperations.isEmpty()) {
            out = "0";
            canChangeOperator = false;
            canBackspace = true;
            pointInText = false;
            historyUnaryOperations = "";
            outOperationMemory.setText(historyOperations);
        }
        if (canBackspace) {
            if (!pointInText) {
                if (out.equals("0")) {
                    charValidInText++;
                }
                out += buttonText;
                pointInText = true;
                charValidInText++;
            }
        }
        outText.setText(out);
        resizeOutputText();
        memoryPressed = false;
    }

    private void resizeOutputText() {
        Text textNew = new Text(outText.getText());
        double widthMaxTextOutput = outText.getWidth() - outText.getPadding().getRight() - outText.getPadding().getLeft();
        textNew.setFont(outText.getFont());
        double textWidthNew = textNew.getBoundsInLocal().getWidth();
        double presentSize = outText.getFont().getSize();
        double percentOfChange;
        double newSize;


        percentOfChange = widthMaxTextOutput / textWidthNew;
        newSize = presentSize * percentOfChange;
        Stage stage = (Stage) maximizeButton.getScene().getWindow();


        if (newSize > 46 && !stage.isMaximized()) {
            newSize = 46;
        }
        if (stage.isMaximized()) {
            newSize = MAX_FONT_SIZE;
        }

        outText.setStyle("-fx-font-size: " + newSize + "px;" +
                "-fx-background-color: e6e6e6;" +
                "-fx-font-family:\"Segoe UI Semibold\";");

    }

    private void scrollOutOperationMemory() {
        String historyNegateNumber = "";
        Text history = new Text(historyOperations + historyUnaryOperations + historyNegateNumber);
        history.setFont(outOperationMemory.getFont());
        double maxWidthForLabelOperation = scrollPaneOperation.getWidth() - scrollPaneOperation.getPadding().getLeft() - scrollPaneOperation.getPadding().getRight();
        if (history.getBoundsInLocal().getWidth() > maxWidthForLabelOperation) {
            scrollButtonLeft.setVisible(true);
            double p = history.getBoundsInLocal().getWidth() / maxWidthForLabelOperation;
            int temp = (int) p;
            moveScroll = scrollPaneOperation.getHmax() / temp;
        } else {
            scrollButtonLeft.setVisible(false);
            scrollButtonRight.setVisible(false);
        }
    }


    private void calculateBinaryOperation() {
        try {
//            if (binaryBinary.getNumberSecond()!= null && binaryBinary.getNumberFirst()!=null){
//                System.out.println(binaryBinary.getNumberFirst().round(new MathContext(19, RoundingMode.HALF_DOWN))+" "+binaryBinary.getNumberSecond().round(new MathContext(19, RoundingMode.HALF_DOWN)));
//            }
            binaryBinary.calculateBinary(binaryOperation);
        } catch (Exception e) {
            outOperationMemory.setText(historyOperations + getSymbol(binaryOperation));
            printError(e);
        }
    }

    OperationsEnum oldBinaryOperation;

    private void changeOperator() {
        if (!canChangeOperator) {
            oldBinaryOperation = binaryOperation;
            canChangeOperator = true;
            historyOperations += getSymbol(oldBinaryOperation);
        } else {
            String lastSymbol = historyOperations.split(" ")[historyOperations.split(" ").length - 1];
            if (getSymbol(oldBinaryOperation).replace(" ", "").equals(lastSymbol)) {
                deleteLastHistory();
                oldBinaryOperation = binaryOperation;
            }
            historyOperations += getSymbol(binaryOperation);
        }
        historyUnaryOperations = "";
        outOperationMemory.setText(historyOperations);
        scrollOutOperationMemory();
    }


    private void printError(Exception e) {
        isError = true;
        operationsIsDisable(true);
        memoryPanel.setDisable(true);
        outText.setStyle(firstStyleLabel);

        outText.setText(e.getMessage());
        resizeOutputText();
        scrollOutOperationMemory();
    }

    private void operationsIsDisable(boolean disable) {
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

    private void printResult(BigDecimal result) {
        try {
            isOverflow(result);
            outText.setText(NumberFormatter.formatterNumber(result));
            resizeOutputText();
        } catch (ArithmeticException e) {
            printError(e);
        }
    }

    private void isOverflow(BigDecimal result) throws ArithmeticException {
        boolean overflow = false;
        BigDecimal number = NumberFormatter.parseNumber(NumberFormatter.formatterNumber(result));

        if (number.abs().compareTo(BigDecimal.ONE) > 0) {
            overflow = number.abs().compareTo(MAX_NUMBER_INTEGER) >= 0;
        } else if (number.abs().compareTo(BigDecimal.ONE) < 0 && number.compareTo(BigDecimal.ZERO) != 0) {
            overflow = number.abs().compareTo(MIN_NUMBER_DECIMAL) <= 0;
        }

        if (overflow) {
            throw new ArithmeticException("Overflow");
        }


    }

    private String getSymbol(OperationsEnum binaryOperation) {
        String symbol;
        if (binaryOperation.equals(OperationsEnum.SUBTRACT)) {
            symbol = " - ";
        } else if (binaryOperation.equals(OperationsEnum.ADD)) {
            symbol = " + ";
        } else if (binaryOperation.equals(OperationsEnum.MULTIPLY)) {
            symbol = " x ";
        } else if (binaryOperation.equals(OperationsEnum.DIVIDE)) {
            symbol = " ÷ ";
        } else if (binaryOperation.equals(OperationsEnum.PERCENT)) {
            symbol = " ";
        } else {
            symbol = "  ";
        }

        return symbol;
    }

    private String getOperationUnary(OperationsEnum unaryOperation) {
        String operation;
        if (unaryOperation.equals(OperationsEnum.SQRT)) {
            operation = "\u221A(";
        } else if (unaryOperation.equals(OperationsEnum.SQR)) {
            operation = "sqr(";
        } else if (unaryOperation.equals(OperationsEnum.ONE_DIVIDE_X)) {
            operation = "1/(";
        } else {
            operation = "";
        }
        return operation;
    }

}