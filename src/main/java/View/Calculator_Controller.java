package View;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

import Model.Arithmetic;
import Model.OperationsEnum;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Calculator_Controller {
    private static final BigDecimal MAX_INPUT_NUMBER = BigDecimal.valueOf(9999999999999999L);
    private static final double MAX_FONT_SIZE = 71;
    @FXML
    private Label outText;

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

    private final int CHAR_MAX = 16;
    private String textForOutput = "";
    private String textWithoutSeparate = "";
    private String firstStyleLabel;
    private boolean pointInText = false;
    private BigDecimal numberFirstBinaryOperations;
    private BigDecimal numberSecondBinaryOperations;
    private BigDecimal result;
    private BigDecimal numberUnaryOperations;
    private OperationsEnum newBinaryOperation;
    private OperationsEnum oldBinaryOperation;
    private OperationsEnum unaryOperation;
    private OperationsEnum percentOperation;
    private boolean canChangeOperator = false;
    private String historyOperations = "";
    private String historyUnaryOperations = "";
    private String historyNegateNumber = "";
    private double moveScroll;
    private boolean equalWasPress;
    private boolean showLeftMenu = false;
    private boolean isError = false;
    private boolean negatePressed = false;
    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    void initialize() {
        firstStyleLabel = outText.getStyle();
        textForOutput = "0";
        outText.setText(textForOutput);
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
        numberUnaryOperations = null;
        historyOperations += historyUnaryOperations;
        Character buttonText = ((Button) actionEvent.getSource()).getText().charAt(0);
        if (newBinaryOperation != null && numberFirstBinaryOperations != null && !equalWasPress) {
            setNum2();
            if (!negatePressed) {
                if (numberSecondBinaryOperations != null) {
                    if (NumberFormatter.formatterNumber(numberSecondBinaryOperations).contains("E")) {
                        historyOperations += NumberFormatter.formatterNumber(numberSecondBinaryOperations);
                    } else {
                        historyOperations += numberSecondBinaryOperations;
                    }
                }
            }
            calculateBinaryOperation();
        }
        setNum1();

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
        textWithoutSeparate = "";
    }

    private void printResult() {
        if (!isError) {
            textForOutput = NumberFormatter.formatterNumber(new BigDecimal(textWithoutSeparate.replace(",", ".")));
            if (pointInText && textWithoutSeparate.charAt(textWithoutSeparate.length() - 1) == ',') {
                textForOutput += ",";
            }

        }
        resizeOutputText();
        outText.setText(textForOutput);
    }

    @FXML
    public void unaryOperations(ActionEvent actionEvent) {
        Character buttonText = ((Button) actionEvent.getSource()).getText().charAt(0);
        if (buttonText.equals(OperationsEnum.SQRT.getOperations())) {
            unaryOperation = OperationsEnum.SQRT;
        } else if (buttonText.equals(OperationsEnum.SQRX.getOperations())) {
            unaryOperation = OperationsEnum.SQRX;
        } else if (buttonText.equals(OperationsEnum.ONE_DIVIDE_X.getOperations())) {
            unaryOperation = OperationsEnum.ONE_DIVIDE_X;
        }
        if (numberUnaryOperations == null) {
            if (!textForOutput.isEmpty()) {
                numberUnaryOperations = new BigDecimal(textForOutput.replace(",", "."));
//                start = true;
            } else if (result != null) {
                numberUnaryOperations = result;
            } else {
                numberUnaryOperations = BigDecimal.ZERO;
            }
            historyUnaryOperations += numberUnaryOperations.toString();
        }


        historyUnaryOperations = unaryOperation.getSymbol() + historyUnaryOperations + " )";
        outOperationMemory.setText(historyOperations + historyUnaryOperations);
        calculateUnaryOperations();
        scrollOutOperationMemory();
        resizeOutputText();
//        textWithoutSeparateNew = "";
    }

    @FXML
    public void percentOperation(ActionEvent actionEvent) {
        Character buttonText = ((Button) actionEvent.getSource()).getText().charAt(0);
        if (buttonText.equals(OperationsEnum.PERCENT.getOperations())) {
            percentOperation = OperationsEnum.PERCENT;
        }
        setNum1();
        setNum2();
        calculatePerCent();

        outOperationMemory.setText(historyOperations);

        CE.fire();
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
        historyOperations = "";
        if (buttonText.equals(OperationsEnum.EQUAL.getOperations())) {
            if (newBinaryOperation != null) {
                setNum2();
                if (numberSecondBinaryOperations == null) {
                    numberSecondBinaryOperations = numberFirstBinaryOperations;
                }
                equalWasPress = true;
            }
            calculateBinaryOperation();
            outOperationMemory.setText(historyOperations);
            negatePressed = false;
            textForOutput = "";
            textWithoutSeparate = "";

        }
    }


    @FXML
    void backspace(ActionEvent actionEvent) {
        if (!equalWasPress) {
            if (textWithoutSeparate != null && textWithoutSeparate.length() > 0) {
                if ((textWithoutSeparate.length() == 2 && textWithoutSeparate.contains("-")) ||
                        textWithoutSeparate.length() == 1) {
                    CE.fire();
                }
                if (!textWithoutSeparate.isEmpty() && !textWithoutSeparate.equals("0")) {
                    if (textWithoutSeparate.charAt(textWithoutSeparate.length() - 1) == ',') {
                        pointInText = false;
                    }
                    textWithoutSeparate = new StringBuilder(textWithoutSeparate).deleteCharAt(textWithoutSeparate.length() - 1).toString();
                    printResult();
                }
            }
        }
    }

    @FXML
    void clearAllC(ActionEvent actionEvent) {
        outText.setStyle(firstStyleLabel);
        textForOutput = "0";
        outText.setText(textForOutput);
        outOperationMemory.setText("");
        textWithoutSeparate = "";
        pointInText = false;
        canChangeOperator = false;
        newBinaryOperation = null;
        numberFirstBinaryOperations = null;
        numberSecondBinaryOperations = null;
        result = null;
        historyOperations = "";
        scrollButtonRight.setVisible(false);
        scrollButtonLeft.setVisible(false);
        historyUnaryOperations = "";
        numberUnaryOperations = null;
        equalWasPress = false;
        operationsIsDisable(false);
        isError = false;
        negatePressed = false;
    }

    @FXML
    void clearNumberCE(ActionEvent actionEvent) {
        outText.setStyle(firstStyleLabel);
        textForOutput = "0";
        textWithoutSeparate = "";
        outText.setText(textForOutput);
        pointInText = false;
        if (isError) {
            C.fire();
        }
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
        } else if (keyCode == KeyCode.PERIOD) {
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
            oneDivideX.fire();
        } else if (keyCode == KeyCode.Q) {
            sqrX.fire();
        }
    }


    @FXML
    public void number(ActionEvent actionEvent) {
        String buttonText = ((Button) actionEvent.getSource()).getText();

        if (textWithoutSeparate.isEmpty()) {
            textWithoutSeparate += buttonText;
        } else {
            if (new BigDecimal(textWithoutSeparate.replace(",", ".")).precision() < CHAR_MAX) {
                if (outText.getText() != null) {
                    if (outText.getText().equals("0")) {
                        textWithoutSeparate = "";
                    }
                }
                textWithoutSeparate += buttonText;
            }
        }

        printInput();

        if (numberFirstBinaryOperations != null) {
            canChangeOperator = false;
            equalWasPress = false;
            if (!textForOutput.contains(",")) {
                pointInText = false;
            }
        }
        if (historyOperations.isEmpty()) {
            numberFirstBinaryOperations = null;
        }
        operationsIsDisable(false);
        isError = false;
    }

    private void printInput() {

        try {
            textForOutput = NumberFormatter.formatterInputNumber(new BigDecimal(textWithoutSeparate.replace(",", ".").replace("e", "E")));
        } catch (NumberFormatException e) {
            System.out.println(textWithoutSeparate);
        }

        result = NumberFormatter.parseNumber(textWithoutSeparate);
        if (pointInText && textWithoutSeparate.charAt(textWithoutSeparate.length() - 1) == ',') {
            textForOutput += ",";
        }

        resizeOutputText();
        outText.setText(textForOutput);
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
        if (textWithoutSeparate.isEmpty()) {
            textWithoutSeparate = NumberFormatter.parseNumber(outText.getText()).toString();
        }
        if (equalWasPress || newBinaryOperation != null) {
            if (!negatePressed) {
                historyUnaryOperations +=textWithoutSeparate;
                negatePressed = true;
            }
            historyUnaryOperations = "negate(" + historyUnaryOperations + ")";
            outOperationMemory.setText(historyOperations + historyUnaryOperations);
        }
        if (!textWithoutSeparate.contains("-")) {
            textWithoutSeparate = "-" + textWithoutSeparate;
        } else {
            textWithoutSeparate = textWithoutSeparate.replace("-", "");
        }


        printInput();
canChangeOperator = false;
        scrollOutOperationMemory();
    }

    @FXML
    public void commaMouseClick(ActionEvent actionEvent) {
        String buttonText = ((Button) actionEvent.getSource()).getText();
        if (!pointInText) {
            if (outText.getText().equals("0")) {
                textWithoutSeparate = outText.getText();
            } else if (textWithoutSeparate.isEmpty() || equalWasPress) {
                textWithoutSeparate = "0";
            }
            textWithoutSeparate += buttonText;

            pointInText = true;
        }

//        printResult();
        printInput();

    }


    private void resizeOutputText() {
        Text textNew = new Text(textForOutput);
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
        if (numberFirstBinaryOperations == null) {
            numberFirstBinaryOperations = result;
            numberSecondBinaryOperations = null;
            textWithoutSeparate = "";

            if (NumberFormatter.formatterNumber(numberFirstBinaryOperations).contains("E")) {
                historyOperations += NumberFormatter.formatterNumber(numberFirstBinaryOperations);
            } else {
                historyOperations += numberFirstBinaryOperations;
            }
        }

    }

    private void setNum2() {
        if (!canChangeOperator) {
            if (!textWithoutSeparate.isEmpty()) {
                numberSecondBinaryOperations = NumberFormatter.parseNumber(textWithoutSeparate);
            }
        } else {
            numberSecondBinaryOperations = null;
        }

    }

    private void calculateBinaryOperation() {
//        System.out.println(numberFirstBinaryOperations + " " + numberSecondBinaryOperations);
        if (numberFirstBinaryOperations != null && numberSecondBinaryOperations != null) {
            try {
                result = Arithmetic.calculateBinaryOperations(numberFirstBinaryOperations, numberSecondBinaryOperations, newBinaryOperation);
                numberFirstBinaryOperations = result;
                textWithoutSeparate = result.toString();
                printResult();

                if (!equalWasPress) {
                    oldBinaryOperation = null;
                    numberSecondBinaryOperations = null;
                }

                canChangeOperator = false;
                negatePressed = false;
            } catch (Exception e) {
                printError(e);
            }
        }
    }

    private void printError(Exception e) {
        isError = true;
        operationsIsDisable(true);
        outText.setStyle(firstStyleLabel);
        textForOutput = e.getMessage();
        resizeOutputText();
        outText.setText(textForOutput);
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
                textForOutput = result.toString();
                if (newBinaryOperation == null) {
                    numberFirstBinaryOperations = result;
                } else {
                    numberSecondBinaryOperations = result;
                }
//                start = true;
                unaryOperation = null;
                numberUnaryOperations = result;
                textForOutput = textForOutput.replace(".", ",");
                printResult();
            } catch (Exception e) {
                printError(e);
            }
        }
    }

    private void calculatePerCent() {
        if (numberFirstBinaryOperations != null && numberSecondBinaryOperations != null) {
            result = Arithmetic.calculateBinaryOperations(numberFirstBinaryOperations, numberSecondBinaryOperations, percentOperation);
            textForOutput = result.toString();
            printResult();

            if (newBinaryOperation != null) {
                numberSecondBinaryOperations = result;
                historyOperations += result;
            } else {
                numberFirstBinaryOperations = result;
            }

//            start = true;
            percentOperation = null;

        }
    }

}

