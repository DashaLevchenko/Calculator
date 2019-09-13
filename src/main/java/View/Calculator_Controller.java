package View;

import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

import Model.Arithmetic;
import javafx.css.Style;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Calculator_Controller {



    //region Description
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button per_cent;

    @FXML
    private Button Backspace;

    @FXML
    private Button nine;

    @FXML
    private Button CE;

    @FXML
    private Button plusMinus;

    @FXML
    private Button C;

    @FXML
    private Button six;

    @FXML
    private Button one;

    @FXML
    private Button seven;

    @FXML
    private Button sum;

    @FXML
    private Button two;

    @FXML
    private Button three;

    @FXML
    private Button eight;

    @FXML
    private Button zero;

    @FXML
    private Button point;

    @FXML
    private Button equal;

    @FXML
    private Button sqrX;

    @FXML
    private Button sqrt;

    @FXML
    private Button OneDivideX;

    @FXML
    private Button four;

    @FXML
    private Button difference;

    @FXML
    private Button divide;

    @FXML
    private Button multiply;

    @FXML
    private Button five;

    @FXML
    private Label title;

    @FXML
    private Button cancel;

    @FXML
    private Label outText;
    //endregion

    private int charactersNumber = 16;
    private String textForDisplayWithoutSeparate = "";
    private double widthMaxTextOutput;
    private Font firstStyleLabel;
    private boolean start = true;

    @FXML
    void initialize() {
    }

    @FXML
    public void binaryOperation(ActionEvent actionEvent) {

    }

    @FXML
    void backspace(ActionEvent actionEvent) {
        backspaceText();
    }

    @FXML
    void clearAllC(ActionEvent actionEvent) {
        outTextClear();
    }

    @FXML
    void keyPressed(KeyEvent event) {
        String inputText = "";
        KeyCode keyCode = event.getCode();
        if (keyCode == KeyCode.DIGIT1 || keyCode == KeyCode.DIGIT2 || keyCode == KeyCode.DIGIT3 ||
                keyCode == KeyCode.DIGIT4 || keyCode == KeyCode.DIGIT5 || keyCode == KeyCode.DIGIT6 ||
                keyCode == KeyCode.DIGIT7 || keyCode == KeyCode.DIGIT8 || keyCode == KeyCode.DIGIT9 ||
                keyCode == KeyCode.DIGIT0 || keyCode == KeyCode.NUMPAD1 || keyCode == KeyCode.NUMPAD2 || keyCode == KeyCode.NUMPAD3 ||
                keyCode == KeyCode.NUMPAD4 || keyCode == KeyCode.NUMPAD5 || keyCode == KeyCode.NUMPAD6 ||
                keyCode == KeyCode.NUMPAD7 || keyCode == KeyCode.NUMPAD8 || keyCode == KeyCode.NUMPAD9 ||
                keyCode == KeyCode.NUMPAD0) {
            inputText = event.getText();
            resizeNumberFont(inputText, true);
        } else if (keyCode == KeyCode.ESCAPE) {
//            textForDisplayWithoutSeparate = "";
//            outText.setFont(firstStyleLabel);
//            outText.setText("0");
//            start = true;
            outTextClear();
        } else if (keyCode == KeyCode.BACK_SPACE) {
            backspaceText();
        }
    }

    @FXML
    public void number(ActionEvent actionEvent) {
        String buttonText = ((Button) actionEvent.getSource()).getText();
        resizeNumberFont(buttonText, true);
    }

    @FXML
    public void negate(ActionEvent actionEvent) {
        textForDisplayWithoutSeparate = Arithmetic.negate(new BigDecimal(textForDisplayWithoutSeparate)).toString();
        resizeNumberFont("", true);
    }

    private void resizeNumberFont(String numberAdd, boolean lessNumber) {
        if (start) {
            textForDisplayWithoutSeparate += numberAdd;
            firstStyleLabel = outText.getFont();
            start = false;
        } else {
            Text textOld = new Text(separateNumber(textForDisplayWithoutSeparate));
            Text textNew;
            textOld.setFont(outText.getFont());
            Double textWidthOld = textOld.getBoundsInLocal().getWidth();
            widthMaxTextOutput = outText.getWidth() - (outText.getPadding().getRight() + outText.getPadding().getLeft());
            double presentSize = outText.getFont().getSize();
            double percentOfChange;
            double newSize = presentSize;
            double textWidthNew;
            if (lessNumber) {
                if (textForDisplayWithoutSeparate.length() <= charactersNumber - 1) {
                    textNew = new Text(separateNumber(textForDisplayWithoutSeparate + numberAdd));
                    textNew.setFont(outText.getFont());
                    textWidthNew = textNew.getBoundsInLocal().getWidth();
                    if (textWidthNew > widthMaxTextOutput) {
                        percentOfChange = (textWidthNew - textWidthOld) / textWidthOld;
                        newSize = presentSize - (presentSize * percentOfChange);
                    }
                    textForDisplayWithoutSeparate += numberAdd;
                }
            } else {
                String textWithoutLastSymbol = new StringBuilder(textForDisplayWithoutSeparate).deleteCharAt(textForDisplayWithoutSeparate.length() - 1).toString();
                textNew = new Text(separateNumber(textWithoutLastSymbol));
                textNew.setFont(outText.getFont());
                textWidthNew = textNew.getBoundsInLocal().getWidth();
                percentOfChange = (textWidthOld - textWidthNew) / textWidthOld;
                newSize = presentSize + (presentSize * percentOfChange);
                textForDisplayWithoutSeparate = textWithoutLastSymbol;
            }
            if (newSize < firstStyleLabel.getSize()) {
                outText.setStyle("-fx-font-size: " + newSize + "px;" +
                        "-fx-background-color: e6e6e6;" +
                        "-fx-font-family:\"Segoe UI Semibold\";" +
                        "-fx-alignment: center-right;");
            }
        }
        outText.setText(separateNumber(textForDisplayWithoutSeparate));
    }

    private String separateNumber(String text) {
        BigDecimal number = new BigDecimal(text);
        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        return decimalFormat.format(number);
    }

    private void outTextClear() {
        textForDisplayWithoutSeparate = "";
        outText.setStyle(firstStyleLabel.getStyle());
        outText.setText("0");
        start = true;
    }

    private void backspaceText() {
        if (textForDisplayWithoutSeparate != null) {
            if (textForDisplayWithoutSeparate.length() == 1) {
                outTextClear();
            }
            StringBuilder stringBuilder = new StringBuilder(textForDisplayWithoutSeparate);
            String deleteNumber = stringBuilder.substring(stringBuilder.length() - 1);
            resizeNumberFont(deleteNumber, false);
        }
    }
}

