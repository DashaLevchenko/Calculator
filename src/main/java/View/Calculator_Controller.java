package View;

import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

import Model.Arithmetic;
import Model.OperationsEnum;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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
    private Button CE;

    @FXML
    private Button plusMinus;

    @FXML
    private Button C;

    @FXML
    private Button sum;

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
    private Button difference;

    @FXML
    private Button divide;

    @FXML
    private Button multiply;

    @FXML
    private Button cancel;

    @FXML
    private Label title;

    @FXML
    private Label outText;

    @FXML
    private Label outOperationMemory;
    //endregion


    private final int CHAR_MAX = 16;
    private int charactersNumber;
    private String textWithoutSeparateOld = "";
    private String textWithoutSeparateNew = "";
    private double widthMaxTextOutput;
    private Font firstStyleLabel;
    private boolean start = true;
    private boolean pointInText = false;

    private BigDecimal number1;
    private BigDecimal number2;
    private BigDecimal result;
    private OperationsEnum newOperation;
    private OperationsEnum oldOperation;
    private boolean operationEnd = false;
    private boolean canChangeOperator = false;
    private String historyOperations = "";

    @FXML
    void initialize() {
    }

    String oneOp;

    @FXML
    public void binaryOperation(ActionEvent actionEvent) {

        Character buttonText = ((Button) actionEvent.getSource()).getText().charAt(0);
        if (!canChangeOperator && newOperation != null) {
            setNum2();
            calculateBinaryOperation();
        }
        setNum1();

        if (number2 == null) {
            if (buttonText.equals(OperationsEnum.MINUS.getOperations())) {
                newOperation = OperationsEnum.MINUS;
            } else if (buttonText.equals(OperationsEnum.PLUS.getOperations())) {
                newOperation = OperationsEnum.PLUS;
            } else if (buttonText.equals(OperationsEnum.DIVIDE.getOperations())) {
                newOperation = OperationsEnum.DIVIDE;
            } else if (buttonText.equals(OperationsEnum.MULTIPLY.getOperations())) {
                newOperation = OperationsEnum.MULTIPLY;
            } else if (buttonText.equals(OperationsEnum.PERCENT.getOperations())) {
                newOperation = OperationsEnum.PERCENT;
            }

            if (!canChangeOperator) {
                oldOperation = newOperation;
                canChangeOperator = true;
                historyOperations += oldOperation.getSymbol();
            } else {
                if (!newOperation.equals(oldOperation)) {
                    historyOperations = new StringBuilder(historyOperations).delete(historyOperations.length() - 3, historyOperations.length()).toString();
                    historyOperations += newOperation.getSymbol();
                }
            }

        }


        outOperationMemory.setText(historyOperations);
        oldOperation = newOperation;
        CE.fire();

    }

    private void setNum1() {
        if (number1 == null) {
            number1 = new BigDecimal(textWithoutSeparateNew.replace(",", "."));
            start = true;
            historyOperations += number1;
            textWithoutSeparateNew = "";
        }
    }

    private void setNum2() {
        if (!canChangeOperator) {
            try {
                number2 = new BigDecimal(textWithoutSeparateNew.replace(",", "."));
                historyOperations += number2;
                operationEnd = true;
                textWithoutSeparateNew = "";
            } catch (Exception e) {
                number2 = BigDecimal.ZERO;
            }
            start = true;
            System.out.println(number1 + " " + number2);
        }

    }


    private void calculateBinaryOperation() {
        if (number1 != null && number2 != null) {
            textWithoutSeparateOld = textWithoutSeparateNew;
            try {
                result = Arithmetic.calculateBinaryOperations(number1, number2, newOperation);
                textWithoutSeparateNew = result.toString();
                resizeNumberFont();

                number1 = result;
                number2 = null;
                canChangeOperator = false;
                oldOperation = null;
                start = true;

                textWithoutSeparateNew = "";
            } catch (Exception e) {
                textWithoutSeparateNew = e.getMessage();
                resizeNumberFont();
            }
        }
    }

    @FXML
    public void calculate(ActionEvent actionEvent) {
        System.out.println("ok");
    }

    @FXML
    void backspace(ActionEvent actionEvent) {
        if (textWithoutSeparateOld != null && textWithoutSeparateOld.length() > 0) {
            if (textWithoutSeparateOld.length() == 1) {
                C.fire();
            }
            if (textWithoutSeparateOld.charAt(textWithoutSeparateOld.length() - 1) == ',') {
                pointInText = false;
                charactersNumber = CHAR_MAX;
            }
            textWithoutSeparateNew = new StringBuilder(textWithoutSeparateOld).deleteCharAt(textWithoutSeparateOld.length() - 1).toString();
            resizeNumberFont();
        }
    }

    @FXML
    void clearAllC(ActionEvent actionEvent) {
        textWithoutSeparateOld = "";
        textWithoutSeparateNew = "";
        number1 = null;
        number2 = null;
        historyOperations = "";
        if (!start) {
            outText.setStyle(firstStyleLabel.getStyle());
            outText.setText("0");
            outOperationMemory.setText("");
            start = true;
            pointInText = false;
        }
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
        String inputText;
        KeyCode keyCode = event.getCode();
        if (keyCode == KeyCode.DIGIT1 || keyCode == KeyCode.DIGIT2 || keyCode == KeyCode.DIGIT3 ||
                keyCode == KeyCode.DIGIT4 || keyCode == KeyCode.DIGIT5 || keyCode == KeyCode.DIGIT6 ||
                keyCode == KeyCode.DIGIT7 || keyCode == KeyCode.DIGIT8 || keyCode == KeyCode.DIGIT9 ||
                keyCode == KeyCode.DIGIT0 || keyCode == KeyCode.NUMPAD1 || keyCode == KeyCode.NUMPAD2 || keyCode == KeyCode.NUMPAD3 ||
                keyCode == KeyCode.NUMPAD4 || keyCode == KeyCode.NUMPAD5 || keyCode == KeyCode.NUMPAD6 ||
                keyCode == KeyCode.NUMPAD7 || keyCode == KeyCode.NUMPAD8 || keyCode == KeyCode.NUMPAD9 ||
                keyCode == KeyCode.NUMPAD0) {
            inputText = event.getText();
            textWithoutSeparateNew = textWithoutSeparateOld + inputText;
            resizeNumberFont();
        } else if (keyCode == KeyCode.ESCAPE) {
            CE.fire();
        } else if (keyCode == KeyCode.BACK_SPACE) {
            Backspace.fire();
        } else if (keyCode == KeyCode.PERIOD) {
            comma();
        }
    }

    @FXML
    public void number(ActionEvent actionEvent) {
        String buttonText = ((Button) actionEvent.getSource()).getText();
        textWithoutSeparateNew += buttonText;
        resizeNumberFont();
        if (number1 != null) {
            canChangeOperator = false;
        }
    }

    @FXML
    public void cancelButton(ActionEvent actionEvent) {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void negate(ActionEvent actionEvent) {
        BigDecimal numberNeedChange = Arithmetic.negate(new BigDecimal(textWithoutSeparateOld.replace(",", ".")));
        textWithoutSeparateNew = numberNeedChange.toString().replace(".", ",");
        if (textWithoutSeparateNew.contains("-")) {
            charactersNumber ++;
        } else {
            charactersNumber = CHAR_MAX;
        }
        resizeNumberFont();
    }

    @FXML
    public void commaMouseClick(ActionEvent actionEvent) {
        comma();
    }

    private void resizeNumberFont() {
        if (start) {
            textWithoutSeparateOld += textWithoutSeparateNew;
            firstStyleLabel = outText.getFont();
            outText.setText(textWithoutSeparateNew);
            charactersNumber = CHAR_MAX;
            start = false;
        } else {
            if (textWithoutSeparateNew.length() <= charactersNumber) {
                newSizeForText();
                textWithoutSeparateOld = textWithoutSeparateNew;
                outText.setText(separateNumber(textWithoutSeparateNew));
            }
        }
    }

    private void newSizeForText() {
        widthMaxTextOutput = outText.getWidth() - (outText.getPadding().getRight() + outText.getPadding().getLeft());
        Text textOld = new Text(separateNumber(textWithoutSeparateOld));
        Text textNew = new Text(separateNumber(textWithoutSeparateNew));
        textOld.setFont(outText.getFont());
        textNew.setFont(outText.getFont());
        double textWidthOld = textOld.getBoundsInLocal().getWidth();
        double textWidthNew = textNew.getBoundsInLocal().getWidth();
        double presentSize = outText.getFont().getSize();
        double percentOfChange;
        double newSize = presentSize;
        String alignment = outText.getAlignment().toString();

        if (textWidthNew > widthMaxTextOutput) {
            percentOfChange = (textWidthNew - textWidthOld) / textWidthOld;
            newSize = presentSize - (presentSize * percentOfChange);
            alignment = Pos.CENTER_RIGHT.toString();
        } else if (textWidthNew < textWidthOld) {
            percentOfChange = (textWidthOld - textWidthNew) / textWidthOld;
            newSize = presentSize + (presentSize * percentOfChange);
            alignment = Pos.CENTER_RIGHT.toString();
        }

        if (newSize > firstStyleLabel.getSize()) {
            newSize = firstStyleLabel.getSize();
            alignment = Pos.BOTTOM_RIGHT.toString();
        }

        outText.setStyle("-fx-font-size: " + newSize + "px;" +
                "-fx-background-color: e6e6e6;" +
                "-fx-font-family:\"Segoe UI Semibold\";" +
                "-fx-alignment:" + alignment + ";");
    }


    private String separateNumber(String text) {
        String textAfterComma = "";
        DecimalFormat decimalFormat = new DecimalFormat("###,###");

        if (text.contains(",")) {
            int commaIndex = text.indexOf(",");
            String textBeforeComma = text.substring(0, commaIndex);
            textAfterComma = text.substring(commaIndex);
            text = decimalFormat.format(new BigDecimal(textBeforeComma)) + textAfterComma;
        } else {
            text = decimalFormat.format(new BigDecimal(text)) + textAfterComma;
        }
        return text;
    }


    private void comma() {
        if (!pointInText) {
            if (start) {
                textWithoutSeparateNew += "0,";
                textWithoutSeparateOld += textWithoutSeparateNew;
                start = false;
            } else {
                textWithoutSeparateNew += ",";
            }
            charactersNumber += 1;
            pointInText = true;

        }
        resizeNumberFont();


    }

}

