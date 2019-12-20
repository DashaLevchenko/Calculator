package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.PrintWriter;
import java.io.StringWriter;

public class CalculatorUnknownErrorController {
    @FXML
    private Button reset;

    @FXML
    private Button cancel;

    @FXML
    private TextArea errorText;

    @FXML
    private VBox errorDetails;

    private CalculatorController generalController;

    /**
     * Close calculator and error window, if {@code cancel} was pressed.
     */
    @FXML
    void cancelButtonPressed () {
        closeErrorWindow();
        generalController.closeWindow();
    }

    private void closeErrorWindow () {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    void setGeneralController (CalculatorController generalController) {
        this.generalController = generalController;
    }

    /**
     * Reset calculator and close error window, if {@code reset} was pressed.
     */
    @FXML
    void resetButtonPressed () {
        generalController.clearAllC();
        generalController.memoryClear();
        closeErrorWindow();
    }

    /**
     * Show more details about error, if {@code showMoreDetails} was pressed.
     */
    @FXML
    void showMoreDetailsPressed () {
        boolean isVisible = errorDetails.isVisible();
        double height;
        double width;

        if (isVisible) {
            isVisible = false;
            height = 280;
            width = 470;
        } else {
            isVisible = true;
            height = 570;
            width = 830;
        }

        Stage stage = (Stage) errorDetails.getScene().getWindow();
        stage.setWidth(width);
        errorDetails.setVisible(isVisible);
        stage.setHeight(height);

    }

    /**
     * This method sets stack trace to {@code errorText}.
     * @param e The exception will be sets.
     */
    void setErrorText (Throwable e) {
        StringWriter errorMsg = new StringWriter();
        e.printStackTrace(new PrintWriter(errorMsg));

        errorText.setText(errorMsg.toString());
    }
}
