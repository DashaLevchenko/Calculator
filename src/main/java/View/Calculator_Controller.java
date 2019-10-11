package View;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
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
    private int charactersNumber;
    private String textForOutput = "";
    private String firstStyleLabel;
    private boolean start = true;
    private boolean pointInText = false;
    private BigDecimal numberFirstBinaryOperations;
    private BigDecimal numberSecondBinaryOperations;
    private BigDecimal result;
    private BigDecimal numberUnaryOperations;
    private BigDecimal numberNegate;
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
        charactersNumber = CHAR_MAX;
        textForOutput = "0";
        outText.setText(textForOutput);
        equal.setDefaultButton(true);

    }

    @FXML
    void resizeWindow(MouseEvent event) {
        Stage stage = (Stage) generalAnchorPane.getScene().getWindow();
        Resize resize = new Resize(stage);
        resize.resizeAllStage();
//        resizeOutputText();
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
            if (numberSecondBinaryOperations != null && historyUnaryOperations.isEmpty()) {
                historyOperations += numberSecondBinaryOperations;
            }
            calculateBinaryOperation();
        }
        setNum1();
        if (historyOperations.isEmpty()) {
            historyOperations += numberFirstBinaryOperations;
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
            historyOperations = new StringBuilder(historyOperations).delete(historyOperations.length() - 3, historyOperations.length()).toString();
            historyOperations += newBinaryOperation.getSymbol();

        }

        historyUnaryOperations = "";
        outOperationMemory.setText(historyOperations);
        scrollOutOperationMemory();
        oldBinaryOperation = newBinaryOperation;
        textForOutput = "";
    }

    private void printResult() {
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
            pointInText = false;
            negatePressed = false;
            textForOutput = "";
        }
    }


    @FXML
    void backspace(ActionEvent actionEvent) {
        if (!equalWasPress) {
            if (textForOutput != null && textForOutput.length() > 0) {
                if ((textForOutput.length() == 2 && textForOutput.contains("-")) ||
                        textForOutput.length() == 1) {
                    CE.fire();
                }
                if (!textForOutput.isEmpty() && !textForOutput.equals("0")) {
                    if (textForOutput.charAt(textForOutput.length() - 1) == ',') {
                        pointInText = false;
                        charactersNumber = CHAR_MAX;
                    }
                    textForOutput = new StringBuilder(textForOutput).deleteCharAt(textForOutput.length() - 1).toString();
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
//        start = true;
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
        charactersNumber = CHAR_MAX;
        negatePressed = false;
    }

    @FXML
    void clearNumberCE(ActionEvent actionEvent) {
        outText.setStyle(firstStyleLabel);
        textForOutput = "0";
        outText.setText(textForOutput);
//        start = true;
        pointInText = false;
        if (isError) {
            C.fire();
        }
        resizeOutputText();
        charactersNumber = CHAR_MAX;
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
        if (textForOutput.replaceAll("[^0-9]", "").length() < charactersNumber) {
//            if (textForOutput.equals("0") && !buttonText.equals("0")){
//                textForOutput = "";
//            }
            if (outText.getText().equals("0")) {
                textForOutput = "";
            }

                textForOutput += buttonText;
            if(textForOutput.length() == 4){
                System.out.println("o");
            }
        }

        printResult();

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
//        outText.setText(textWithoutSeparateNew);
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
        if (textForOutput.isEmpty()) {
            textForOutput = outText.getText();
        }
        if (equalWasPress || newBinaryOperation != null) {
            if (!negatePressed) {
                historyUnaryOperations += textForOutput;
                negatePressed = true;
            }
            historyUnaryOperations = "negate(" + historyUnaryOperations + ")";
            outOperationMemory.setText(historyOperations + historyUnaryOperations);
        }

        if (!textForOutput.contains("-")) {
            textForOutput = "-" + textForOutput;
            charactersNumber++;
        } else {
            textForOutput = textForOutput.replace("-", "");
            charactersNumber = CHAR_MAX;
        }

        resizeOutputText();
        outText.setText(textForOutput);
        scrollOutOperationMemory();
    }

    @FXML
    public void commaMouseClick(ActionEvent actionEvent) {
        String buttonText = ((Button) actionEvent.getSource()).getText();
        if (!pointInText || !outText.getText().contains(",")) {
            if (outText.getText().equals("0")) {
                textForOutput = outText.getText();
//                start = false;
            } else if (textForOutput.isEmpty()) {
                textForOutput += "0";
            }
            textForOutput += buttonText;
            charactersNumber++;
            pointInText = true;
        }
        printResult();
    }


    private void resizeOutputText() {
        if (textForOutput.isEmpty()) {
            textForOutput = outText.getText();
        }
        if (!isError) {
            textForOutput = formatterNumber(parseNumber(textForOutput));
        }
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

    private DecimalFormat decimalFormat = new DecimalFormat();

    public String formatterNumber(BigDecimal number) {
        StringBuilder pattern = new StringBuilder();
        if (number.compareTo(BigDecimal.valueOf(9999999999999999L)) > 0) {
            pattern.append("0.");
            if (number.precision() > 16) {
                number = number.round(new MathContext(16, RoundingMode.HALF_UP));
                number = number.stripTrailingZeros();

                if (number.precision() != 1 && number.precision() > number.scale()) {
                    pattern.append("#".repeat(number.precision() - number.scale()));
                }
                pattern.append("E0");

            } else {
                pattern.append("#".repeat(number.scale()));
            }
        } else if (number.abs().compareTo(BigDecimal.ONE) < 0 && number.abs().compareTo(BigDecimal.ZERO) != 0) {
            if (number.abs().compareTo(BigDecimal.valueOf(0.0000000000000001)) < 0) {
                number = number.round(new MathContext(16, RoundingMode.HALF_UP));
                pattern.append("#.E0");
            } else {
                pattern.append("#.").append("#".repeat(16));
            }

        } else {
            pattern.append("#,###");
            if (number.scale() > 0) {
                if (number.scale() < 16) {
                    pattern.append(".").append("#".repeat(number.scale()));
                } else {
                    pattern.append(".").append("#".repeat(16 - (number.precision() - number.scale())));
                }
            }
        }
        decimalFormat.applyPattern(pattern.toString());
        return decimalFormat.format(number);
    }

    private BigDecimal parseNumber(String text) {
        BigDecimal number = null;
        if (decimalFormat != null && !text.isEmpty()) {
            try {
                System.out.println(decimalFormat.toPattern());
                if (text.contains(",")) {
                    decimalFormat.applyPattern(decimalFormat.toPattern());
                    number = BigDecimal.valueOf((Double) decimalFormat.parse(text));
                }else {
                    number = BigDecimal.valueOf((Long) decimalFormat.parse(text));
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return number;
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
//            if (textForOutput.isEmpty() && !outText.getText().isEmpty()) {
//                textForOutput = outText.getText().replace(" ", "").replace("e", "E").replace(",", ".");
//            }
//            if (!textForOutput.isEmpty()) {
//                if (textForOutput.contains(",") || textForOutput.contains("e")) {
//                    textForOutput = textForOutput.replace(",", ".").replace("e", "E");
//                }
//                numberFirstBinaryOperations = new BigDecimal(textForOutput);
//                start = true;
//            } else
//            if (result != null) {
//                numberFirstBinaryOperations = result;
//            } else {
//                numberFirstBinaryOperations = BigDecimal.ZERO;
//            }
            numberFirstBinaryOperations = parseNumber(textForOutput);
            numberSecondBinaryOperations = null;
            historyOperations += numberFirstBinaryOperations;
        }
    }

    private void setNum2() {
        if (!canChangeOperator) {
            if (!textForOutput.isEmpty()) {
                numberSecondBinaryOperations = parseNumber(textForOutput);
//                start = true;
            }
        } else {
            numberSecondBinaryOperations = null;
        }
    }

    private void calculateBinaryOperation() {
        if (numberFirstBinaryOperations != null && numberSecondBinaryOperations != null) {
            try {
                result = Arithmetic.calculateBinaryOperations(numberFirstBinaryOperations, numberSecondBinaryOperations, newBinaryOperation);
                textForOutput = result.toString();
//                start = true;
                printResult();
                numberFirstBinaryOperations = result;
                canChangeOperator = false;

                if (!equalWasPress) {
                    oldBinaryOperation = null;
                    numberSecondBinaryOperations = null;
                }


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

