package Controller;

import Model.Arithmetic;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

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
    private static final BigDecimal MAX_NUMBER_DECIMAL = new BigDecimal("9.999999999999999E-9999");
    private static final BigDecimal MIN_NUMBER_INTEGER = new BigDecimal("9.999999999999999E9999");
    private static final int MAX_SCALE_DECIMAL = MAX_NUMBER_DECIMAL.scale() - MAX_NUMBER_DECIMAL.precision();
    private static final int MAX_SCALE_INTEGER = (MIN_NUMBER_INTEGER.scale() - MIN_NUMBER_INTEGER.precision());

    private OperationsEnum newBinaryOperation;
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
    private BigDecimal numberFirstBinaryOperations;
    private BigDecimal numberSecondBinaryOperations;
    private BigDecimal result;
    private BigDecimal numberUnaryOperations;
    private double moveScroll;
    private double xOffset = 0;
    private double yOffset = 0;
    private Memory memory;
    private boolean percentPressed = false;

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
        numberUnaryOperations = null;
        historyOperations += historyUnaryOperations;

        String outLabelText = NumberFormatter.formatterNumber(NumberFormatter.parseNumber(outText.getText())).replace(" ", "");
        if (historyOperations.isEmpty() || numberFirstBinaryOperations == null || historyOperations.equals(outLabelText)) {
            setNum1();
            numberSecondBinaryOperations = null;
        }
        if (!canChangeOperator) {
            calculateBinaryOperation();
            numberSecondBinaryOperations = null;
        }

        newBinaryOperation = OperationsEnum.valueOf(((Button) actionEvent.getSource()).getId());


        changeOperator();

        equalWasPress = false;
    }


    @FXML
    public void unaryOperations(ActionEvent actionEvent) {
        if (historyOperations.isEmpty()) {
            newBinaryOperation = null;
        }

        OperationsEnum unaryOperation = OperationsEnum.valueOf(((Button) actionEvent.getSource()).getId());
        if (numberUnaryOperations == null) {
            numberUnaryOperations = NumberFormatter.parseNumber(outText.getText().replace(" ", ""));
            if (historyUnaryOperations.isEmpty()) {
                historyUnaryOperations += NumberFormatter.formatterNumber(numberUnaryOperations).replace(" ", "");
            }
        }
        if (outText.getText().contains("-") && !numberUnaryOperations.toString().contains("-") && negatePressed) {
            numberUnaryOperations = numberUnaryOperations.negate();
        }

        historyUnaryOperations = getOperationUnary(unaryOperation) + historyUnaryOperations + ")";
        outOperationMemory.setText(historyOperations + historyUnaryOperations);
        if (numberUnaryOperations != null) {
            try {
                result = Arithmetic.calculate(numberUnaryOperations, unaryOperation);
                isOverflow(result);
                numberUnaryOperations = result;
                printResult(NumberFormatter.formatterNumber(result));

                setNumberResultUnary();
            } catch (Exception e) {
                printError(e);
            }
        }
        canChangeOperator = false;
        scrollOutOperationMemory();
        resizeOutputText();
    }

    @FXML
    public void percentOperation() {
        percentOperation = OperationsEnum.PERCENT;
        try {
            if (newBinaryOperation != null) {
                if (newBinaryOperation.equals(OperationsEnum.DIVIDE) || newBinaryOperation.equals(OperationsEnum.MULTIPLY)) {
                    if (!equalWasPress) {
                        if (numberSecondBinaryOperations == null) {
                            numberSecondBinaryOperations = numberFirstBinaryOperations;
                        }
                        result = Arithmetic.calculate(BigDecimal.ONE, numberSecondBinaryOperations, percentOperation);
                        isOverflow(result);
                        numberSecondBinaryOperations = result;
                    } else {
                        result = Arithmetic.calculate(BigDecimal.ONE, numberFirstBinaryOperations, percentOperation);
                        isOverflow(result);
                        numberFirstBinaryOperations = result;
                        historyOperations = "";
                        outOperationMemory.setText(historyOperations);
                    }
                } else {
                    if (!equalWasPress) {
                        if (numberSecondBinaryOperations == null) {
                            numberSecondBinaryOperations = numberFirstBinaryOperations;
                        }
                        result = Arithmetic.calculate(numberFirstBinaryOperations, numberSecondBinaryOperations, percentOperation);
                        isOverflow(result);
                        numberSecondBinaryOperations = result;
                        percentPressed = true;
                    } else {
                        if (!percentPressed) {
                            numberSecondBinaryOperations = numberFirstBinaryOperations;
                            percentPressed = true;
                        }
                        result = Arithmetic.calculate(NumberFormatter.parseNumber(outText.getText()), numberFirstBinaryOperations, percentOperation);
                        isOverflow(result);
                        historyOperations = "";
                        outOperationMemory.setText(historyOperations);
                    }
                }
            } else {
                result = Arithmetic.calculate(numberFirstBinaryOperations, percentOperation);
            }
        } catch (ArithmeticException e) {
            printError(e);
        }
        historyOperations += NumberFormatter.formatterNumber(result).replace(" ", "");
        printResult(NumberFormatter.formatterNumber(result));

        outOperationMemory.setText(historyOperations);
        canChangeOperator = false;
        scrollOutOperationMemory();
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
        if (!isError) {
            if (newBinaryOperation != null) {
                if (numberSecondBinaryOperations == null) {
                    numberSecondBinaryOperations = numberFirstBinaryOperations;
                }
                if (percentPressed && equalWasPress) {
                    setNum1();
                }
                equalWasPress = true;
                calculateBinaryOperation();
            }

            if (!isError) {
                historyOperations = "";
                outOperationMemory.setText(historyOperations);
                negatePressed = false;
                canChangeOperator = false;
                historyUnaryOperations = "";
                numberUnaryOperations = null;
                equalWasPress = true;
                percentOperation = null;
            }

        } else {
            C.fire();
        }
        scrollOutOperationMemory();
    }

    @FXML
    void backspace() {
        if (isError) {
            C.fire();
        }
        String out = outText.getText();
        if (!equalWasPress && result == null) {
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
                        printResult(out);
                    } else {
                        printResult(NumberFormatter.formatterInputNumber(out.replace(" ", "")));
                    }
                    setNum2();
                }
            }
        }
    }

    @FXML
    void clearAllC() {
        outText.setStyle(firstStyleLabel);
        outText.setText("0");
        historyOperations = "";
        outOperationMemory.setText(historyOperations);
        pointInText = false;
        canChangeOperator = false;
        newBinaryOperation = null;
        numberFirstBinaryOperations = null;
        numberSecondBinaryOperations = null;
        result = null;
        scrollButtonRight.setVisible(false);
        scrollButtonLeft.setVisible(false);
        historyUnaryOperations = "";
        numberUnaryOperations = null;
        equalWasPress = false;
        operationsIsDisable(false);
        memoryPanel.setDisable(false);
        isError = false;
        negatePressed = false;
        percentPressed = false;
        charValidInText = CHAR_MAX_INPUT;
        resizeOutputText();
        percentOperation = null;
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
        setNum2();
        result = null;
        operationsIsDisable(false);
        scrollOutOperationMemory();
    }

    private String cleanDisplay(String out) {
        if (canChangeOperator || equalWasPress || memoryPressed || !historyUnaryOperations.isEmpty()) {
            canChangeOperator = false;
            memoryPressed = false;
            equalWasPress = false;
            charValidInText = CHAR_MAX_INPUT;
            out = "";
            percentOperation = null;
            pointInText = false;
            historyUnaryOperations = "";
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

    @FXML
    public void negate() {
        if (numberSecondBinaryOperations == null && equalWasPress || result != null) {
            if (historyUnaryOperations.isEmpty()) {
                historyUnaryOperations += NumberFormatter.formatterNumber(NumberFormatter.parseNumber(outText.getText())).replace(" ", "");
            }
        }


        StringBuilder out = new StringBuilder(outText.getText());
        if (!out.toString().equals("0")) {
            if (out.charAt(0) != '-') {
                out = out.insert(0, '-');
                charValidInText++;
            } else {
                out.deleteCharAt(0);
                charValidInText = CHAR_MAX_INPUT;
            }
            outText.setText(out.toString());
        }
        if (numberUnaryOperations == null) {
            if (!historyOperations.isEmpty()) {
                setNum2();
            }else{
                equalWasPress = false;
//                setNum1();
                numberFirstBinaryOperations = NumberFormatter.parseNumber(outText.getText());
            }
        } else {
            numberUnaryOperations = numberUnaryOperations.negate();
            setNumberResultUnary();
        }
        if (!historyUnaryOperations.isEmpty()) {
            if (historyOperations.equals("0")) {
                historyOperations = "";
            }
            if (!canChangeOperator && numberSecondBinaryOperations != null && !historyOperations.isEmpty()) {
                String numberDelete = NumberFormatter.formatterNumber(numberSecondBinaryOperations).replace(" ", "");
                int charStart = historyOperations.length() - numberDelete.length();
                if (charStart > 0) {
                    int charEnd = historyOperations.length();

                    if (historyOperations.substring(charStart, charEnd).equals(numberDelete)) {
                        historyOperations = new StringBuilder(historyOperations).delete(charStart, charEnd).toString();
                    }
                }
            }
            historyUnaryOperations = "negate(" + historyUnaryOperations + ")";
            outOperationMemory.setText(historyOperations + historyUnaryOperations);
        }

        resizeOutputText();
        negatePressed = true;
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
        memory.setNumber(NumberFormatter.parseNumber(outText.getText()));
        memoryButtonDisable(false);
        memoryPressed = true;
    }

    @FXML
    void memoryAdd() {
        if (memory != null) {
            memory.memoryAdd(NumberFormatter.parseNumber(outText.getText()));
        } else {
            memory = new Memory();
            memory.memoryAdd(NumberFormatter.parseNumber(outText.getText()));
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
            try {
                printResult(NumberFormatter.formatterNumber(isOverflow(memory.memoryRecall())));
            } catch (Exception e) {
                printError(e);
            }
            setNum2();
        }
        memoryPressed = true;
    }

    @FXML
    void memorySubtract() {
        if (memory != null) {
            memory.memorySubtract(NumberFormatter.parseNumber(outText.getText()));
        } else {
            memory = new Memory();
            memory.memorySubtract(NumberFormatter.parseNumber("-" + outText.getText()));
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
            pointInText = false;
            historyUnaryOperations = "";
            outOperationMemory.setText(historyOperations);
        }
        if (!pointInText) {
            if (out.equals("0")) {
                charValidInText++;
            }
            out += buttonText;
            pointInText = true;
            charValidInText++;
        }
        printResult(out);
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

    private void setNum1() {
        if (!equalWasPress || percentPressed) {
            numberFirstBinaryOperations = NumberFormatter.parseNumber(outText.getText());
        }
        if (!percentPressed) {
            numberSecondBinaryOperations = null;
        }
        if (historyOperations.isEmpty()) {
            if (historyUnaryOperations.isEmpty()) {
                historyOperations += NumberFormatter.formatterNumber(NumberFormatter.parseNumber(outText.getText())).replace(" ", "");
            }
        }
    }

    private void setNum2() {
        if (numberFirstBinaryOperations != null) {
            numberSecondBinaryOperations = NumberFormatter.parseNumber(outText.getText());
            canChangeOperator = false;
        }
    }

    private void calculateBinaryOperation() {
        if (numberFirstBinaryOperations != null && numberSecondBinaryOperations != null) {
            try {
                result = Arithmetic.calculate(numberFirstBinaryOperations, numberSecondBinaryOperations, newBinaryOperation);
                isOverflow(result);
                numberFirstBinaryOperations = result;

                printResult(NumberFormatter.formatterNumber(result));
                negatePressed = false;
            } catch (Exception e) {
                printError(e);
            }
            if (percentOperation == null && historyUnaryOperations.isEmpty()) {
                historyOperations += NumberFormatter.formatterNumber(numberSecondBinaryOperations).replace(" ", "");
            }
        }
    }

    private void changeOperator(){
        OperationsEnum oldBinaryOperation;
        if (!canChangeOperator) {
            oldBinaryOperation = newBinaryOperation;
            canChangeOperator = true;
            historyOperations += getSymbol(oldBinaryOperation);
        } else {
            if (!negatePressed) {
                historyOperations = new StringBuilder(historyOperations).delete(historyOperations.length() - getSymbol(newBinaryOperation).length(), historyOperations.length()).toString();
                historyOperations += getSymbol(newBinaryOperation);
            }
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

        printResult(e.getMessage());
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


    private void setNumberResultUnary() {
        if (numberFirstBinaryOperations != null && newBinaryOperation != null) {
            numberSecondBinaryOperations = numberUnaryOperations;
        } else {
            numberFirstBinaryOperations = numberUnaryOperations;
            numberSecondBinaryOperations = null;
        }
    }


    private void printResult(String out) {
        outText.setText(out);
        resizeOutputText();
    }

    private BigDecimal isOverflow(BigDecimal result) throws ArithmeticException {
        boolean needOverflow = false;
        BigDecimal number = result;

        if (number.scale() > 0) {
            needOverflow = number.scale() - number.precision() > MAX_SCALE_DECIMAL;
        } else if (number.scale() < 0) {
            number = number.round(new MathContext(CHAR_MAX_INPUT, RoundingMode.HALF_UP));
            needOverflow = number.scale() - number.precision() < MAX_SCALE_INTEGER;
        }

        if (needOverflow) {
            throw new ArithmeticException("Overflow");
        } else {
            return result;
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