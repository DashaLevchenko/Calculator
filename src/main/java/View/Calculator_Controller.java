package View;

import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

import javafx.css.Style;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class Calculator_Controller {

    private boolean start = true;

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

    private int charactersNumber = 16;
    private String textForDisplayWithoutSeparate = "";
    private Double widthMaxTextOutput;
    private String firstStyleLabel;

    @FXML
    void initialize() {
    }

    @FXML
    public void binaryOperation(ActionEvent actionEvent) {

    }
    @FXML
    void backspace(KeyEvent event) {
        KeyCode keyCode = event.getCode();
        if(keyCode == KeyCode.BACK_SPACE){
            System.out.println("ok");
            if(textForDisplayWithoutSeparate != null){
//                resizeNumberFont("\b");
            }
        }
    }

    @FXML
    void keyPressed(KeyEvent event) {
        String inputText = "";
        KeyCode keyCode = event.getCode();
        if (keyCode == KeyCode.DIGIT1 || keyCode == KeyCode.DIGIT2 || keyCode == KeyCode.DIGIT3 ||
                keyCode == KeyCode.DIGIT4 || keyCode == KeyCode.DIGIT5 || keyCode == KeyCode.DIGIT6 ||
                keyCode == KeyCode.DIGIT7 || keyCode == KeyCode.DIGIT8 || keyCode == KeyCode.DIGIT9 ||
                keyCode == KeyCode.DIGIT0 ||keyCode == KeyCode.NUMPAD1 || keyCode == KeyCode.NUMPAD2 || keyCode == KeyCode.NUMPAD3 ||
                keyCode == KeyCode.NUMPAD4 || keyCode == KeyCode.NUMPAD5 || keyCode == KeyCode.NUMPAD6 ||
                keyCode == KeyCode.NUMPAD7 || keyCode == KeyCode.NUMPAD8 || keyCode == KeyCode.NUMPAD9 ||
                keyCode == KeyCode.NUMPAD0 ) {
            inputText = event.getText();
            resizeNumberFont(inputText, true);
        }else if (keyCode == KeyCode.ESCAPE){
            textForDisplayWithoutSeparate = "";
            outText.setStyle(firstStyleLabel);
            outText.setText("0");
            start = true;
        } else if(keyCode == KeyCode.BACK_SPACE){
            if(textForDisplayWithoutSeparate != null){
                StringBuilder stringBuilder = new StringBuilder(textForDisplayWithoutSeparate);
                String deleteNumber = stringBuilder.substring(stringBuilder.length()-1);
                resizeNumberFont(deleteNumber, false);
//                resizeNumberFont("");
                if (textForDisplayWithoutSeparate.length() == 1){
                    textForDisplayWithoutSeparate = "";
                    outText.setStyle(firstStyleLabel);
                    outText.setText("0");
                    start = true;
                }
            }
        }

    }

    @FXML
    public void number(ActionEvent actionEvent) {
        String buttonText = ((Button) actionEvent.getSource()).getText();
        resizeNumberFont(buttonText, true);
    }

    private void resizeNumberFont(String numberAdd, boolean lessNumber) {
        if (start) {
            textForDisplayWithoutSeparate += numberAdd;
            firstStyleLabel = outText.getStyle();
            start = false;
        } else {
                Text textOld = new Text(separateNumber(textForDisplayWithoutSeparate));
                textOld.setFont(outText.getFont());
                Double textWidthOld = textOld.getBoundsInLocal().getWidth();
                widthMaxTextOutput = outText.getWidth() - (outText.getPadding().getRight() + outText.getPadding().getLeft());
                double presentSize = outText.getFont().getSize();
                double percentOfChange;
                double newSize = presentSize;


                if(lessNumber){
                    if (textForDisplayWithoutSeparate.length() <= charactersNumber-1) {

                        Text textNew = new Text(separateNumber(textForDisplayWithoutSeparate + numberAdd));
                        textNew.setFont(outText.getFont());
                        Double textWidthNew = textNew.getBoundsInLocal().getWidth();
                        if (textWidthNew.compareTo(widthMaxTextOutput) > 0) {
                            percentOfChange = (textWidthNew - textWidthOld) / textWidthOld;
                            newSize = presentSize - (presentSize * percentOfChange);
                        }
                        textForDisplayWithoutSeparate += numberAdd;
                    }
                }
                else {
                    Text textNew = new Text(separateNumber(new StringBuilder(textForDisplayWithoutSeparate).deleteCharAt(textForDisplayWithoutSeparate.length()-1).toString()));
                    System.out.println(textNew.getText());
                    textNew.setFont(outText.getFont());
                    Double textWidthNew = textNew.getBoundsInLocal().getWidth();
                    percentOfChange = (textWidthOld - textWidthNew) / textWidthOld;
                    newSize = presentSize + (presentSize * percentOfChange);
                    textForDisplayWithoutSeparate = new StringBuilder(textForDisplayWithoutSeparate).deleteCharAt(textForDisplayWithoutSeparate.length()-1).toString();
                    System.out.println("ok");
                }

                if (newSize < 46) {
                    outText.setStyle("-fx-font-size: " + newSize + "px;" +
                            "-fx-background-color: e6e6e6;" +
                            "-fx-font-family:\"Segoe UI Semibold\";" +
                            "-fx-alignment: center-right;");
                }
            }

        outText.setText(separateNumber(textForDisplayWithoutSeparate));
        System.out.println(textForDisplayWithoutSeparate.length());
    }
    private void resizeNumberFont(String numberAdd) {
        if (start) {
            textForDisplayWithoutSeparate += numberAdd;
            firstStyleLabel = outText.getStyle();
            start = false;

        } else {
            if (textForDisplayWithoutSeparate.length() < charactersNumber) {
                Text textOld = new Text(separateNumber(textForDisplayWithoutSeparate));
                Text textNew = new Text(separateNumber(textForDisplayWithoutSeparate + numberAdd));
                textOld.setFont(outText.getFont());
                textNew.setFont(outText.getFont());

                Double textWidthOld = textOld.getBoundsInLocal().getWidth();
                Double textWidthNew = textNew.getBoundsInLocal().getWidth();
                widthMaxTextOutput = outText.getWidth() - (outText.getPadding().getRight() + outText.getPadding().getLeft());

                if (textWidthNew.compareTo(widthMaxTextOutput) > 0) {
                    double presentSize = outText.getFont().getSize();
                    double percentOfChange = (textWidthNew - textWidthOld) / textWidthOld;
                    double newSize;

                    newSize = presentSize - (presentSize * percentOfChange);
                    outText.setStyle("-fx-font-size: " + newSize + "px;" +
                            "-fx-background-color: e6e6e6;" +
                            "-fx-font-family:\"Segoe UI Semibold\";" +
                            "-fx-alignment: center-right;");
                }
                textForDisplayWithoutSeparate += numberAdd;

                outText.setText(separateNumber(textForDisplayWithoutSeparate));
            }
        }
    }

    private String separateNumber(String text) {
            BigDecimal number = new BigDecimal(text);
            DecimalFormat decimalFormat = new DecimalFormat("###,###");
            return decimalFormat.format(number);
    }
}

