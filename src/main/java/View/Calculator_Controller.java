package View;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import Model.Arithmetic;
import Model.OperationsEnum;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Calculator_Controller {
    @FXML
    private Label outText;

    @FXML
    private Button per_cent;

    @FXML
    private Button sqrt;

    @FXML
    private Button sqrX;

    @FXML
    private Button OneDivideX;

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
    private Button difference;

    @FXML
    private Button one;

    @FXML
    private Button two;

    @FXML
    private Button three;

    @FXML
    private Button sum;

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
    private String textWithoutSeparateOld = "";
    private String textWithoutSeparateNew = "";
    private Font firstStyleLabel;
    private boolean start = true;
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
    private double moveScroll;
    private boolean equalWasPress;
    private boolean showLeftMenu = false;
    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    void initialize() {
        firstStyleLabel = outText.getFont();

//        Stage stage = (Stage) generalAnchorPane.getScene().getWindow();


    }

    @FXML
    void resizeWindow(MouseEvent event) {
        Stage stage = (Stage) generalAnchorPane.getScene().getWindow();
        Resize resize = new Resize(stage);
        resize.resizeAllStage();
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
        if (newBinaryOperation != null && numberFirstBinaryOperations != null && !equalWasPress) {
            setNum2();
            historyOperations += numberSecondBinaryOperations;
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
//            if (numberFirstBinaryOperations != null) {
        if (!canChangeOperator) {
            oldBinaryOperation = newBinaryOperation;
            canChangeOperator = true;
            historyOperations += oldBinaryOperation.getSymbol();
        } else {
            if (!newBinaryOperation.equals(oldBinaryOperation)) {
                historyOperations = new StringBuilder(historyOperations).delete(historyOperations.length() - 3, historyOperations.length()).toString();
                historyOperations += newBinaryOperation.getSymbol();
            }
        }
//            }
//        }
        historyUnaryOperations = "";
        outOperationMemory.setText(historyOperations);
        scrollOutOperationMemory();
        oldBinaryOperation = newBinaryOperation;
        CE.fire();

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
            if (!textWithoutSeparateNew.isEmpty()) {
                if (textWithoutSeparateNew.contains(",")) {
                    textWithoutSeparateNew = textWithoutSeparateNew.replace(",", ".");
                }
                numberUnaryOperations = new BigDecimal(textWithoutSeparateNew);
                start = true;
            } else if (result != null) {
                numberUnaryOperations = result;
            } else {
                numberUnaryOperations = BigDecimal.ZERO;
            }
            historyUnaryOperations += numberUnaryOperations.toString();
        }
        historyUnaryOperations = unaryOperation.getSymbol() + historyUnaryOperations + " )";
        calculateUnaryOperations();

        outOperationMemory.setText(historyOperations + historyUnaryOperations);
        scrollOutOperationMemory();

        CE.fire();

    }

    @FXML
    public void per_centOperation(ActionEvent actionEvent) {
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
        if (buttonText.equals(OperationsEnum.EQUAL.getOperations())) {
            if (newBinaryOperation != null) {
                setNum1();
                setNum2();
                if (numberSecondBinaryOperations == null) {
                    numberSecondBinaryOperations = numberFirstBinaryOperations;
                }

                if (!equalWasPress) {
                    historyOperations += numberSecondBinaryOperations;
                    equalWasPress = true;
                } else {
                    historyOperations += newBinaryOperation.getSymbol() + numberSecondBinaryOperations;
                }
                calculateBinaryOperation();
                outOperationMemory.setText(historyOperations);
                scrollOutOperationMemory();
            }
        }
    }

    @FXML
    void backspace(ActionEvent actionEvent) {
        if (textWithoutSeparateOld != null && textWithoutSeparateOld.length() > 0) {
            if (textWithoutSeparateOld.length() == 1) {
                C.fire();
            }
            if (!textWithoutSeparateOld.isEmpty()) {
                if (textWithoutSeparateOld.charAt(textWithoutSeparateOld.length() - 1) == ',') {
                    pointInText = false;
                    charactersNumber = CHAR_MAX;
                }
                textWithoutSeparateNew = new StringBuilder(textWithoutSeparateOld).deleteCharAt(textWithoutSeparateOld.length() - 1).toString();
                printTextOutput();
            }

        }
    }

    @FXML
    void clearAllC(ActionEvent actionEvent) {
        textWithoutSeparateOld = "";
        textWithoutSeparateNew = "";
        outText.setStyle(firstStyleLabel.getStyle());
        outText.setText("0");
        outOperationMemory.setText("");
        start = true;
        pointInText = false;
        canChangeOperator = false;
        newBinaryOperation = null;
        numberFirstBinaryOperations = null;
        numberSecondBinaryOperations = null;
        historyOperations = "";
        scrollButtonRight.setVisible(false);
        scrollButtonLeft.setVisible(false);
        historyUnaryOperations = "";
        numberUnaryOperations = null;
        equalWasPress = false;
    }

    @FXML
    void clearNumberCE(ActionEvent actionEvent) {
        textWithoutSeparateOld = "";
        textWithoutSeparateNew = "";
        if (!start) {
            outText.setStyle(firstStyleLabel.getStyle());
            outText.setText("0");
            start = true;
            pointInText = false;
        }
    }

    @FXML
    void keyPressed(KeyEvent event) {
        KeyCode keyCode = event.getCode();
        if (keyCode == KeyCode.DIGIT1 || keyCode == KeyCode.NUMPAD1) {
            one.fire();
        } else if (keyCode == KeyCode.DIGIT2 || keyCode == KeyCode.NUMPAD2) {
            two.fire();
        } else if (keyCode == KeyCode.DIGIT3 || keyCode == KeyCode.NUMPAD3) {
            three.fire();
        } else if (keyCode == KeyCode.DIGIT4 || keyCode == KeyCode.NUMPAD4) {
            four.fire();
        } else if (keyCode == KeyCode.DIGIT5 || keyCode == KeyCode.NUMPAD5) {
            five.fire();
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
            CE.fire();
        } else if (keyCode == KeyCode.BACK_SPACE) {
            Backspace.fire();
        } else if (keyCode == KeyCode.PERIOD) {
            point.fire();
        } else if (keyCode == KeyCode.ADD || (keyCode == KeyCode.EQUALS && event.isShiftDown())) {
            sum.fire();
        } else if (keyCode == KeyCode.MINUS || keyCode == KeyCode.SUBTRACT) {
            difference.fire();
        } else if (keyCode == KeyCode.MULTIPLY) {
            multiply.fire();
        } else if (keyCode == KeyCode.SLASH || keyCode == KeyCode.DIVIDE) {
            divide.fire();
        }
    }

    @FXML
    public void number(ActionEvent actionEvent) {
        String buttonText = ((Button) actionEvent.getSource()).getText();
        textWithoutSeparateNew += buttonText;
        printTextOutput();
        if (numberFirstBinaryOperations != null) {
            canChangeOperator = false;
            equalWasPress = false;
        }
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
        BigDecimal numberNeedChange = Arithmetic.negate(new BigDecimal(textWithoutSeparateOld.replace(",", ".")));
        textWithoutSeparateNew = numberNeedChange.toString().replace(".", ",");
        if (textWithoutSeparateNew.contains("-")) {
            charactersNumber++;
        } else {
            charactersNumber = CHAR_MAX;
        }
        printTextOutput();
    }

    @FXML
    public void commaMouseClick(ActionEvent actionEvent) {
        String buttonText = ((Button) actionEvent.getSource()).getText();
        if (!pointInText) {
            if (start) {
                textWithoutSeparateNew += "0" + buttonText;
                textWithoutSeparateOld += textWithoutSeparateNew;
                start = false;
            } else {
                textWithoutSeparateNew += buttonText;
            }
            charactersNumber++;
            pointInText = true;
        }
        printTextOutput();
    }

    private void printTextOutput() {
        if (start) {
            textWithoutSeparateOld += textWithoutSeparateNew;
            outText.setText(textWithoutSeparateNew);
            charactersNumber = CHAR_MAX;
            start = false;
        } else {
            if (textWithoutSeparateNew.length() <= charactersNumber) {
                resizeOutputText();
                textWithoutSeparateOld = textWithoutSeparateNew;
                outText.setText(separateNumber(textWithoutSeparateNew));
            }
        }
    }

    private void resizeOutputText() {
        double widthMaxTextOutput = outText.getWidth() - outText.getPadding().getRight() - outText.getPadding().getLeft();
        Text textOld = new Text(separateNumber(textWithoutSeparateOld));
        Text textNew = new Text(separateNumber(textWithoutSeparateNew));
        textOld.setFont(outText.getFont());
        textNew.setFont(outText.getFont());
        double textWidthOld = textOld.getBoundsInLocal().getWidth();
        double textWidthNew = textNew.getBoundsInLocal().getWidth();
        double presentSize = outText.getFont().getSize();
        double percentOfChange;
        double newSize = presentSize;

        if (textWidthNew > widthMaxTextOutput) {
            percentOfChange = (textWidthNew - textWidthOld) / textWidthOld;
            newSize = presentSize - (presentSize * percentOfChange);
        } else if (textWidthNew < textWidthOld) {
            percentOfChange = (textWidthOld - textWidthNew) / textWidthOld;
            newSize = presentSize + (presentSize * percentOfChange);
        }

        if (newSize > firstStyleLabel.getSize()) {
            newSize = firstStyleLabel.getSize();
        }

        outText.setStyle("-fx-font-size: " + newSize + "px;" +
                "-fx-background-color: e6e6e6;" +
                "-fx-font-family:\"Segoe UI Semibold\";");
    }

    private String separateNumber(String text) {
//        String textAfterComma = "";
//        StringBuilder stringBuilder = new StringBuilder(text);
//
//
//        if (text.contains(",")) {
//            int commaIndex = text.indexOf(",");
//            String textBeforeComma = text.substring(0, commaIndex);
//            textAfterComma = text.substring(commaIndex);
//            text = decimalFormat.format(new BigDecimal(textBeforeComma)) + textAfterComma;
//        } else {
//            text = decimalFormat.format(new BigDecimal(text)) + textAfterComma;
//        }
        return text;
    }
//    private String separateNumber(String text) {
//        String textAfterComma = "";
//        DecimalFormat decimalFormat = new DecimalFormat("###,###");
//
//        if (text.contains(",")) {
//            int commaIndex = text.indexOf(",");
//            String textBeforeComma = text.substring(0, commaIndex);
//            textAfterComma = text.substring(commaIndex);
//            text = decimalFormat.format(new BigDecimal(textBeforeComma)) + textAfterComma;
//        } else {
//            text = decimalFormat.format(new BigDecimal(text)) + textAfterComma;
//        }
//        return text;
//    }

    private void scrollOutOperationMemory() {
        Text history = new Text(historyOperations);
        history.setFont(outOperationMemory.getFont());
        double maxWidthForLabelOperation = scrollPaneOperation.getWidth() - scrollPaneOperation.getPadding().getLeft() - scrollPaneOperation.getPadding().getRight();
        if (history.getBoundsInLocal().getWidth() > maxWidthForLabelOperation) {
            scrollButtonLeft.setVisible(true);
            double p = history.getBoundsInLocal().getWidth() / maxWidthForLabelOperation;
            int temp = (int) p;
            moveScroll = scrollPaneOperation.getHmax() / temp;
        }
    }

    private void setNum1() {
        if (numberFirstBinaryOperations == null && !textWithoutSeparateNew.isEmpty()) {
            if (!textWithoutSeparateNew.isEmpty()) {
                if (textWithoutSeparateNew.contains(",")) {
                    textWithoutSeparateNew = textWithoutSeparateNew.replace(",", ".");
                }
                numberFirstBinaryOperations = new BigDecimal(textWithoutSeparateNew);
                start = true;
                textWithoutSeparateNew = "";
            } else if (result != null) {
                numberFirstBinaryOperations = result;
            } else {
                numberFirstBinaryOperations = BigDecimal.ZERO;
            }
            numberSecondBinaryOperations = null;
            historyOperations += numberFirstBinaryOperations;


        }


    }

    private void setNum2() {
        if (!canChangeOperator) {
            if (!textWithoutSeparateNew.isEmpty()) {
                numberSecondBinaryOperations = new BigDecimal(textWithoutSeparateNew.replace(",", "."));
                textWithoutSeparateNew = "";
                start = true;
            }

//            historyOperations += numberSecondBinaryOperations;
        }
    }

    private void calculateBinaryOperation() {
        if (numberFirstBinaryOperations != null && numberSecondBinaryOperations != null) {
            textWithoutSeparateOld = textWithoutSeparateNew;
            result = Arithmetic.calculateBinaryOperations(numberFirstBinaryOperations, numberSecondBinaryOperations, newBinaryOperation);
            textWithoutSeparateNew = result.toString();
            printTextOutput();

            canChangeOperator = false;
            if (!equalWasPress) {
                oldBinaryOperation = null;
                numberSecondBinaryOperations = null;
                newBinaryOperation = null;
            }
            start = true;
            numberFirstBinaryOperations = result;
            textWithoutSeparateNew = "";
        }
    }

    private void calculateUnaryOperations() {
        if (numberUnaryOperations != null) {
            textWithoutSeparateOld = textWithoutSeparateNew;

            result = Arithmetic.calculateUnaryOperations(numberUnaryOperations, unaryOperation);

            textWithoutSeparateNew = result.toString();
            printTextOutput();

            if (newBinaryOperation == null) {
                numberFirstBinaryOperations = result;
            } else {
                numberSecondBinaryOperations = result;
                calculateBinaryOperation();
            }


            start = true;
            textWithoutSeparateNew = "";
            unaryOperation = null;
            numberUnaryOperations = result;

        }
    }

    private void calculatePerCent() {
        if (numberFirstBinaryOperations != null && numberSecondBinaryOperations != null) {
            textWithoutSeparateOld = textWithoutSeparateNew;
            result = Arithmetic.calculateBinaryOperations(numberFirstBinaryOperations, numberSecondBinaryOperations, percentOperation);
            textWithoutSeparateNew = result.toString();
            printTextOutput();

            if (newBinaryOperation != null) {
                numberSecondBinaryOperations = result;
                historyOperations += result;
                calculateBinaryOperation();
            } else {
                numberFirstBinaryOperations = result;
            }

            canChangeOperator = false;

        }

    }
}

