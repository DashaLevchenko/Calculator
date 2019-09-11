package View;

import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ResourceBundle;

import javafx.beans.property.ObjectProperty;
import javafx.beans.value.WritableValue;
import javafx.collections.ObservableList;
import javafx.css.CssMetaData;
import javafx.css.Style;
import javafx.css.StyleableObjectProperty;
import javafx.css.StyleableProperty;
import javafx.css.converter.EnumConverter;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ZoomEvent;
import javafx.scene.layout.Region;
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

    @FXML
    void initialize() {

    }

    @FXML
    public void binaryOperation(ActionEvent actionEvent) {

    }
    private String textOnDisplay = "";

    @FXML
    public void number(ActionEvent actionEvent) {
        String buttonText = ((Button) actionEvent.getSource()).getText();
        textOnDisplay += buttonText;
        String textForPrint = separateNumber(textOnDisplay);
        Text text = new Text(textForPrint);
        text.setFont(outText.getFont());

        Double textWidth = text.getBoundsInLocal().getWidth();
        Double widthLabelForText = outText.getWidth() - (outText.getPadding().getRight() + outText.getPadding().getLeft());
        if (start) {
            outText.setText("" + buttonText);
            start = false;
        } else {
            if (textOnDisplay.length()-1 < charactersNumber) {
                    if (textWidth.compareTo(widthLabelForText) > 0 ) {
                        double presentSize = outText.getFont().getSize();
                        Text button = new Text(buttonText);
                        button.setFont(outText.getFont());

                        double percentOfChange = button.getBoundsInLocal().getWidth()/textWidth;
                        double newSize = presentSize - (presentSize*percentOfChange);
                        outText.setStyle("-fx-font-size: "+newSize+"px;"+
                                        "-fx-background-color: e6e6e6;" +
                                         "-fx-font-family:\"Segoe UI Semibold\";"+
                                         "-fx-alignment: center-right;");
                    }
                outText.setText(textForPrint);
            }
        }

    }

    private String separateNumber(String text){
        BigDecimal number = new BigDecimal(text);
        DecimalFormat decimalFormat = new DecimalFormat("###,###");

        return decimalFormat.format(number);
    }


}
