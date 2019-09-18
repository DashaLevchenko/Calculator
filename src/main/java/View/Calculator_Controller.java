package View;

import java.math.BigDecimal;
import java.net.URL;
import java.security.Key;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

import Model.Arithmetic;
import Model.OperationsEnum;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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
    private Label outOperationMemory;

    @FXML
    private Button scrollButtonLeft;

    @FXML
    private Button scrollButtonRight;

    @FXML
    private ScrollPane scrollPaneOperation;


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
        scrollOutOperationMemory();
        oldOperation = newOperation;
        CE.fire();

    }
    double plusScroll = 0;
    private void scrollOutOperationMemory(){

        Text history = new Text(historyOperations);
        if(history.getBoundsInLocal().getWidth() > outOperationMemory.getWidth())
        {
            scrollButtonLeft.setVisible(true);
            scrollPaneOperation.setHmax(1);
            scrollButtonLeft.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    System.out.println(scrollPaneOperation.hmaxProperty());

//                    if (plusScroll < 1) {

                        scrollPaneOperation.setHvalue(0.2); //8/40
//                    }
                }
            });
        }
    }

    private void setNum1() {
        if (number1 == null) {
            if (textWithoutSeparateNew.contains(",")) {
                textWithoutSeparateNew = textWithoutSeparateNew.replace(",", ".");
            }
            System.out.println(textWithoutSeparateNew);
            number1 = new BigDecimal(textWithoutSeparateNew);
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
        }

    }


    private void calculateBinaryOperation() {
        if (number1 != null && number2 != null) {
            textWithoutSeparateOld = textWithoutSeparateNew;
            result = Arithmetic.calculateBinaryOperations(number1, number2, newOperation);
            textWithoutSeparateNew = result.toString();
            resizeNumberFont();

            number1 = result;
            number2 = null;
            canChangeOperator = false;
            oldOperation = null;
            start = true;

            textWithoutSeparateNew = "";

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
        outText.setStyle(firstStyleLabel.getStyle());
        outText.setText("0");
        outOperationMemory.setText("");
        start = true;
        pointInText = false;
        canChangeOperator = false;
        newOperation = null;
        number1 = null;
        number2 = null;
        historyOperations = "";
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
            charactersNumber++;
        } else {
            charactersNumber = CHAR_MAX;
        }
        resizeNumberFont();
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
        resizeNumberFont();
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


}

