package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;


/**
 * This class realize controller for unknown error of calculator application.
 */
public class CalculatorUnknownErrorController {

    @FXML
    private Button reset;
    @FXML
    private Button ok;

    @FXML
    private TextArea errorMessage;

    private CalculatorController generalController;


    private void closeErrorWindow () {
        Stage stage = (Stage) reset.getScene().getWindow();
        stage.close();
    }

    public void setGeneralController (CalculatorController generalController) {
        this.generalController = generalController;
    }

    /**
     * Reset calculator and close error window, if {@code reset} was pressed.
     */
    @FXML
    void resetCalculator () {
        generalController.clearAllC();
        generalController.memoryClearPressed();
        closeErrorWindow();
    }

    void setErrorMessage (String message) {
        errorMessage.setText(message);
    }

    @FXML
    void okButtonPressed () {
        closeErrorWindow();
        generalController.closeWindow();
    }

    public void changeErrorWindow (String messageError, boolean isLoggerException) {
        boolean isResetVisible;
        boolean isOkVisible;
        if (isLoggerException) {
             isResetVisible = false;
             isOkVisible = true;
            setErrorMessage(messageError);
        }else{
            isResetVisible = true;
            isOkVisible = false;
        }
            reset.setVisible(isResetVisible);
            ok.setVisible(isOkVisible);
            setErrorMessage(messageError);
    }

}
