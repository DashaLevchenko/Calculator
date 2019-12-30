package Controller;

import CalculatorApp.CalculatorMain;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;


public class CalculatorUnknownError {
    private static Logger logger;

    public CalculatorUnknownError () {
        logger = Logger.getLogger(CalculatorUnknownError.class.getName());
        settingLogging();

    }

    public void catchUnknownError (Thread thread, Throwable throwable) {
        logger.log(Level.SEVERE, "Error: ", throwable);

        openErrorWindow("Something wrong.\n" +
                "Please, press reset for reset calculator.\n" +
                "\n" +
                "If error happens again, please, reinstall your calculator.", false);
    }

    private void openErrorWindow (String errorMessage, boolean isLoggerException) {
        FXMLLoader loaderError = new FXMLLoader(CalculatorUnknownError.class.getResource("/View/calculator_unknown_error_view.fxml"));

        Stage stageError = new Stage();
        stageError.initModality(Modality.APPLICATION_MODAL);
        try {
            Parent root = loaderError.load();
            stageError.setScene(new Scene(root));
            stageError.initStyle(StageStyle.UNDECORATED);
            stageError.show();

            CalculatorUnknownErrorController calculatorUnknownErrorController = loaderError.getController();
            calculatorUnknownErrorController.setGeneralController(CalculatorMain.getCalculatorController());
            calculatorUnknownErrorController.changeErrorWindow(errorMessage, isLoggerException);

        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error: ", e);
        }
    }

    private void settingLogging () {
        try {
            FileInputStream file = new FileInputStream("src/logging.config");
            LogManager.getLogManager().readConfiguration(file);
        } catch (IOException e) {
            e.printStackTrace();
            openErrorWindow("Cannot find logger configuration file.\n" +
                    "Please, write to support.", true);
        }
    }
}
