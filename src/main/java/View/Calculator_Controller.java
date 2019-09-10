package View;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ZoomEvent;

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
    private TextField outText;

    @FXML
    void initialize() {

    }

    @FXML
    public void binaryOperation(ActionEvent actionEvent) {
        System.out.println("op");
    }

    private int count = 0;

    @FXML
    public void number(ActionEvent actionEvent) {
        if (start) {
            outText.setText("" + ((Button) actionEvent.getSource()).getText());
            start = false;
        } else {
            if (outText.getText().length() < 16) { // 1232456985

                outText.setText(outText.getText() + ((Button) actionEvent.getSource()).getText());

//                if (count == 11) {
//                    outText.setStyle("-fx-font-size: 32px;" +
//                            "-fx-background-color: e6e6e6;" +
//                            "-fx-font-family:\"Segoe UI Semibold\"");
//                } else if (count == 12) {
//                    outText.setStyle("-fx-font-size: 30px;" +
//                            "-fx-background-color: e6e6e6;" +
//                            "-fx-font-family:\"Segoe UI Semibold\"");
//                }else if (count == 13) {
//                    outText.setStyle("-fx-font-size: 24px;" +
//                            "-fx-background-color: e6e6e6;" +
//                            "-fx-font-family:\"Segoe UI Semibold\"");
//                }
//                if (outText.getText().length() > outText.getLength()) {
//                    outText.setStyle("-fx-font-size: 32px;" +
//                            "-fx-background-color: fff;" +
//                            "-fx-font-family:\"Segoe UI Semibold\"");
//                }

            }
        }
//        System.out.println(count+" "+outText.textProperty().is);


    }


}
