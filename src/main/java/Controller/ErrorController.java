package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ErrorController {

    @FXML
    private Button okButton;
    @FXML
    private TextArea errorText;
    @FXML
    private VBox errorDetails;

    @FXML
    void okButtonPressed () {
       Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void showMoreDetailsPressed () {
        boolean isVisible = errorDetails.isVisible();
        double height;
        Stage stage = (Stage) errorDetails.getScene().getWindow();

        if(isVisible){
            isVisible = false;
            height = 243;
            stage.setWidth(470);
        }else {
            isVisible = true;
            height = 570;
            stage.setWidth(830);
        }
        errorDetails.setVisible(isVisible);
        stage.setHeight(height);

    }


    void setErrorText (Throwable e) {
        StringWriter errorMsg = new StringWriter();
        e.printStackTrace(new PrintWriter(errorMsg));

        errorText.setText(errorMsg.toString());
    }
}
