package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class CalculatorUnknownErrorController {

    @FXML
    private Button reset;

    private CalculatorController generalController;


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
    void resetButtonPressed () {
        generalController.clearAllC();
        generalController.memoryClear();
        closeErrorWindow();
    }
}
