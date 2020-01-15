package CalculatorApplication.Controller;

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

    /**
     * CalculatorApplication.Controller of calculator
     */
    private CalculatorController generalController;

    /**
     * Method will close error window.
     */
    private void closeErrorWindow () {
        Stage stage = (Stage) reset.getScene().getWindow();
        stage.close();
    }


    void setGeneralController (CalculatorController generalController) {
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

    private void setErrorMessage (String message) {
        errorMessage.setText(message);
    }

    /**
     * If (@code ok} was pressed, calculator will close.
     */
    @FXML
    void okButtonPressed () {
        closeErrorWindow();
        generalController.closeWindow();
    }

    /**
     * This method sets error message.
     * If was thrown logger exception, {@code reset} will unvisible and {@code ok} will visible
     *
     * @param messageError      Message need to print
     * @param isLoggerException True, If logger exception was thrown
     */
    void changeErrorWindow (String messageError, boolean isLoggerException) {
        boolean isResetVisible;
        boolean isOkVisible;

        if (isLoggerException) {
            isResetVisible = false;
            isOkVisible = true;
        } else {
            isResetVisible = true;
            isOkVisible = false;
        }

        reset.setVisible(isResetVisible);
        ok.setVisible(isOkVisible);
        setErrorMessage(messageError);
    }

}
