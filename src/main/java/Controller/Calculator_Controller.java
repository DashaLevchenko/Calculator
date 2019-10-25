package Controller;

import Model.Arithmetic;
import Model.OperationsEnum;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class Calculator_Controller {
    //region FXML
    @FXML
    private Label outText;
    @FXML
    private Button memoryClear;

    @FXML
    private Button memoryRecall;

    @FXML
    private Button memoryStore;

    @FXML
    private MenuButton memoryHistory;

    @FXML
    private Button memoryAdd;

    @FXML
    private Button memorySubtract;

    @FXML
    private MenuButton memoryList;

    @FXML
    private Button percent;

    @FXML
    private Button sqrt;

    @FXML
    private Button sqrX;

    @FXML
    private Button oneDivideX;

    @FXML
    private Button CE;

    @FXML
    private Button C;

    @FXML
    private Button Backspace;

    @FXML
    private Button divide;

    @FXML
    private Button seven;

    @FXML
    private Button eight;

    @FXML
    private Button nine;

    @FXML
    private Button multiply;

    @FXML
    private Button four;

    @FXML
    private Button five;

    @FXML
    private Button six;

    @FXML
    private Button subtract;

    @FXML
    private Button one;

    @FXML
    private Button two;

    @FXML
    private Button three;

    @FXML
    private Button add;

    @FXML
    private Button plusMinus;

    @FXML
    private Button zero;

    @FXML
    private Button point;

    @FXML
    private Button equal;

    @FXML
    private Label title;

    @FXML
    private Button cancel;

    @FXML
    private Button maximizeButton;

    @FXML
    private Button hideButton;

    @FXML
    private Label outOperationMemory;

    @FXML
    private Button scrollButtonLeft;

    @FXML
    private Button scrollButtonRight;

    @FXML
    private ScrollPane scrollPaneOperation;

    @FXML
    private AnchorPane leftMenu;

    @FXML
    private AnchorPane generalAnchorPane;

    @FXML
    private Button buttonMenu;

    @FXML
    private Label textStandard;
    //endregion
    private static final double MAX_FONT_SIZE = 71;
    private final int CHAR_MAX = 16;

    private OperationsEnum newBinaryOperation;
    private OperationsEnum oldBinaryOperation;
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
    private BigDecimal numberFirstBinaryOperations;
    private BigDecimal numberSecondBinaryOperations;
    private BigDecimal result;
    private BigDecimal numberUnaryOperations;
    private double moveScroll;
    private double xOffset = 0;
    private double yOffset = 0;
    private Memory memory;

    @FXML
    void initialize() {
        firstStyleLabel = outText.getStyle();
        outText.setText("0");
        equal.setDefaultButton(true);
    }

    @FXML
    void resizeWindow(MouseEvent event) {
        Stage stage = (Stage) generalAnchorPane.getScene().getWindow();
        Resize resize = new Resize(stage);
        resize.resizeAllStage();
        scrollOutOperationMemory();

    }

    @FXML
    void showLeftMenu(ActionEvent actionEvent) {
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
        Character buttonText = ((Button) actionEvent.getSource()).getText().charAt(0);
        numberUnaryOperations = null;

        historyOperations += historyUnaryOperations;
        if (historyOperations.isEmpty()) {
            setNum1();
        }
        if (!canChangeOperator) {
            calculateBinaryOperation();
            numberSecondBinaryOperations = null;
        }

        if (buttonText.equals(OperationsEnum.MINUS.getOperations())) {
            newBinaryOperation = OperationsEnum.MINUS;
        } else if (buttonText.equals(OperationsEnum.PLUS.getOperations())) {
            newBinaryOperation = OperationsEnum.PLUS;
        } else if (buttonText.equals(OperationsEnum.DIVIDE.getOperations())) {
            newBinaryOperation = OperationsEnum.DIVIDE;
        } else if (buttonText.equals(OperationsEnum.MULTIPLY.getOperations())) {
            newBinaryOperation = OperationsEnum.MULTIPLY;
        }
        if (!canChangeOperator) {
            oldBinaryOperation = newBinaryOperation;
            canChangeOperator = true;
            historyOperations += oldBinaryOperation.getSymbol();
        } else {
            if (!negatePressed) {
                historyOperations = new StringBuilder(historyOperations).delete(historyOperations.length() - 3, historyOperations.length()).toString();
                historyOperations += newBinaryOperation.getSymbol();
            }
        }

        historyUnaryOperations = "";
        outOperationMemory.setText(historyOperations);
        scrollOutOperationMemory();
        oldBinaryOperation = newBinaryOperation;
        equalWasPress = false;
    }

    @FXML
    public void unaryOperations(ActionEvent actionEvent) {
        Character buttonText = ((Button) actionEvent.getSource()).getText().charAt(0);
        if (buttonText.equals(OperationsEnum.SQRT.getOperations())) {
            unaryOperation = OperationsEnum.SQRT;
        } else if (buttonText.equals(OperationsEnum.SQR.getOperations())) {
            unaryOperation = OperationsEnum.SQR;
        } else if (buttonText.equals(OperationsEnum.ONE_DIVIDE_X.getOperations())) {
            unaryOperation = OperationsEnum.ONE_DIVIDE_X;
        }

        if (numberUnaryOperations == null) {
            numberUnaryOperations = NumberFormatter.parseNumber(outText.getText().replace(" ", ""));
            if (!negatePressed) {
                historyUnaryOperations += NumberFormatter.formatterNumber(numberUnaryOperations).replace(" ", "");
            }
        }


        historyUnaryOperations = unaryOperation.getSymbol() + historyUnaryOperations + ")";
        outOperationMemory.setText(historyOperations + historyUnaryOperations);
        calculateUnaryOperations();

        scrollOutOperationMemory();
        resizeOutputText();
    }

    @FXML
    public void percentOperation(ActionEvent actionEvent) {
        Character buttonText = ((Button) actionEvent.getSource()).getText().charAt(0);
        percentOperation = OperationsEnum.PERCENT;

        if (equalWasPress) {
            numberSecondBinaryOperations = null;
        }

        calculatePerCent();

        outOperationMemory.setText(historyOperations);
        canChangeOperator = false;
        scrollOutOperationMemory();
    }

    @FXML
    void scrollButtonLeftPressed(ActionEvent event) {
        scrollPaneOperation.setHvalue(scrollPaneOperation.getHvalue() + moveScroll);
        scrollButtonRight.setVisible(true);
        if (scrollPaneOperation.getHvalue() == scrollPaneOperation.getHmax()) {
            scrollButtonLeft.setVisible(false);
            scrollButtonRight.setVisible(true);
        }
    }

    @FXML
    void scrollButtonRightPressed(ActionEvent event) {
        scrollPaneOperation.setHvalue(scrollPaneOperation.getHvalue() - moveScroll);
        scrollButtonLeft.setVisible(true);
        if (scrollPaneOperation.getHvalue() == 0) {
            scrollButtonRight.setVisible(false);
            scrollButtonLeft.setVisible(true);
        }
    }

    @FXML
    public void pressedEqual(ActionEvent actionEvent) {
        Character buttonText = ((Button) actionEvent.getSource()).getText().charAt(0);
        if (!isError) {
            if (buttonText.equals(OperationsEnum.EQUAL.getOperations())) {
                if (newBinaryOperation != null) {
                    if (numberSecondBinaryOperations == null) {
                        numberSecondBinaryOperations = numberFirstBinaryOperations;
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
                }
            }
        } else {
            C.fire();
        }
    }

    @FXML
    void backspace(ActionEvent actionEvent) {
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
                    printResult(NumberFormatter.formatterInputNumber(out.replace(" ", "")));
                    setNum2();
                }
            }
        }
    }

    @FXML
    void clearAllC(ActionEvent actionEvent) {
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
        isError = false;
        negatePressed = false;
        charValidInText = CHAR_MAX;
        resizeOutputText();
        percentOperation = null;
    }

    @FXML
    void clearNumberCE(ActionEvent actionEvent) {
        outText.setStyle(firstStyleLabel);
        outText.setText("0");
        pointInText = false;
        charValidInText = CHAR_MAX;
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
            Backspace.fire();
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
                sqrX.fire();
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
        if (canChangeOperator || equalWasPress || memoryPressed || !historyUnaryOperations.isEmpty()) {
            canChangeOperator = false;
            memoryPressed = false;
            equalWasPress = false;
            charValidInText = CHAR_MAX;
            out = "";
            pointInText = false;
            historyUnaryOperations = "";
            outOperationMemory.setText(historyOperations);

        }
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
    }

    @FXML
    public void cancelButton(ActionEvent actionEvent) {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void maximizeWindow(ActionEvent actionEvent) {
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
        Font newFontNumber = null;
        Font newFontOperations = null;
        if (stage.isMaximized()) {
            newFontNumber = Font.font("Segoe UI Semibold", 34);
            newFontOperations = Font.font("Segoe MDL2 Assets", 20);
            percent.setFont(Font.font("Calculator MDL2 Assets", 20));
            sqrX.setFont(Font.font("Calculator MDL2 Assets", 25));
            oneDivideX.setFont(Font.font("Calculator MDL2 Assets", 22));
            divide.setFont(Font.font("Calculator MDL2 Assets", 20));
            equal.setFont(Font.font("Calculator MDL2 Assets", 20));
        } else {
            newFontNumber = Font.font("Segoe UI Semibold", 24);
            newFontOperations = Font.font("Segoe MDL2 Assets", 15);
            percent.setFont(Font.font("Calculator MDL2 Assets", 15));
            sqrX.setFont(Font.font("Calculator MDL2 Assets", 19));
            oneDivideX.setFont(Font.font("Calculator MDL2 Assets", 17));
            divide.setFont(Font.font("Calculator MDL2 Assets", 15));
            equal.setFont(Font.font("Calculator MDL2 Assets", 15));
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
        add.setFont(newFontOperations);
        multiply.setFont(newFontOperations);
        subtract.setFont(newFontOperations);
        sqrt.setFont(newFontOperations);
        CE.setFont(newFontOperations);
        C.setFont(newFontOperations);
        Backspace.setFont(newFontOperations);
    }

    @FXML
    public void hideWindow(ActionEvent actionEvent) {
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
    public void negate(ActionEvent actionEvent) {
        if (numberSecondBinaryOperations == null  && !equalWasPress && result != null) {
            if (historyUnaryOperations.isEmpty()) {
                historyUnaryOperations += NumberFormatter.formatterNumber(NumberFormatter.parseNumber(outText.getText())).replace(" ", "");
            }
        }
        if (!historyUnaryOperations.isEmpty()) {
            historyUnaryOperations = "negate(" + historyUnaryOperations + ")";
            outOperationMemory.setText(historyOperations + historyUnaryOperations);
        }

        String out = outText.getText();
        if (!out.equals("0")) {
            if (!out.contains("-")) {
                outText.setText("-" + out);
                charValidInText++;
                negatePressed = true;
            } else {
                outText.setText(out.replace("-", ""));
                charValidInText = CHAR_MAX;
                negatePressed = false;
            }
        }
        setNum2();
        resizeOutputText();
    }

    private void memoryButtonDisable(boolean disable) {
        memoryClear.setDisable(disable);
        memoryRecall.setDisable(disable);
    }

    @FXML
    void memoryStore(ActionEvent event) {
        if (memory == null) {
            memory = new Memory();
        }
        memory.setNumber(NumberFormatter.parseNumber(outText.getText()));
        memoryButtonDisable(false);
        memoryPressed = true;
    }

    @FXML
    void memoryAdd(ActionEvent event) {
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
    void memoryClear(ActionEvent event) {
        memoryPressed = true;
        memory = null;
        memoryButtonDisable(true);
    }

    @FXML
    void memoryRecall(ActionEvent event) {
        if (memory != null) {
            try {
                printResult(NumberFormatter.formatterNumber(Arithmetic.isOverflow(memory.memoryRecall())));
            } catch (Exception e) {
                printError(e);
            }
            setNum2();
        }
        memoryPressed = true;
    }

    @FXML
    void memorySubtract(ActionEvent event) {
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
        if (canChangeOperator) {
            out = "0";
            canChangeOperator = false;
            pointInText = false;
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
        numberFirstBinaryOperations = NumberFormatter.parseNumber(outText.getText());
        numberSecondBinaryOperations = null;
        if (historyOperations.isEmpty()) {
            if (historyUnaryOperations.isEmpty()) {
                historyOperations += outText.getText().replace(" ", "");
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
                result = Arithmetic.calculateBinaryOperations(numberFirstBinaryOperations, numberSecondBinaryOperations, newBinaryOperation);
                numberFirstBinaryOperations = result;
                printResult(NumberFormatter.formatterNumber(result));
                negatePressed = false;
                if (percentOperation == null) {
                    historyOperations += NumberFormatter.formatterNumber(numberSecondBinaryOperations).replace(" ", "");
                } else {
                    percentOperation = null;
                }
                numberSecondBinaryOperations = null;
            } catch (Exception e) {
                printError(e);
            }
        }
    }

    private void printError(Exception e) {
        isError = true;
        operationsIsDisable(true);
        outText.setStyle(firstStyleLabel);

        printResult(e.getMessage());
    }

    private void operationsIsDisable(boolean disable) {
        percent.setDisable(disable);
        sqrt.setDisable(disable);
        sqrX.setDisable(disable);
        oneDivideX.setDisable(disable);
        divide.setDisable(disable);
        multiply.setDisable(disable);
        subtract.setDisable(disable);
        add.setDisable(disable);
        plusMinus.setDisable(disable);
        point.setDisable(disable);

    }

    private void calculateUnaryOperations() {
        if (numberUnaryOperations != null) {
            try {
                result = Arithmetic.calculateUnaryOperations(numberUnaryOperations, unaryOperation);

                outText.setText(NumberFormatter.formatterNumber(result));
                resizeOutputText();
                numberUnaryOperations = result;
                result = result.round(new MathContext(16, RoundingMode.HALF_UP));
                unaryOperation = null;
//                if (newBinaryOperation == null) {
                    numberFirstBinaryOperations = numberUnaryOperations;
                    numberSecondBinaryOperations = null;
//                }
//                else {
//                    numberSecondBinaryOperations = result;
//                }

            } catch (Exception e) {
                printError(e);
            }
        }
    }

    private void calculatePerCent() {
        if (newBinaryOperation != null) {
            if (numberSecondBinaryOperations == null) {
                numberSecondBinaryOperations = numberFirstBinaryOperations;
            }
            if (newBinaryOperation.equals(OperationsEnum.DIVIDE) || newBinaryOperation.equals(OperationsEnum.MULTIPLY)) {
                result = Arithmetic.calculateBinaryOperations(BigDecimal.ONE, numberSecondBinaryOperations, percentOperation);
            }else {
                result = Arithmetic.calculateBinaryOperations(numberFirstBinaryOperations, numberSecondBinaryOperations, percentOperation);
            }
            numberSecondBinaryOperations = result;
        } else {
            result = Arithmetic.calculateUnaryOperations(numberFirstBinaryOperations, percentOperation);
            numberFirstBinaryOperations = result;
        }
        historyOperations += NumberFormatter.formatterNumber(result).replace(" ", "");

        printResult(NumberFormatter.formatterNumber(result));
    }

    void printResult(String out) {
        outText.setText(out);
        resizeOutputText();
    }
}