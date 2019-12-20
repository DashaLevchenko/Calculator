package Controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * Class launches calculator application
 */
public class CalculatorMain extends Application {

    private static FXMLLoader loader;

    public static void main (String[] args) {
        launch(args);
    }

    private static void catchUnknownError (Thread thread, Throwable throwable) {
        openErrorWindow(throwable);
    }

    private static void openErrorWindow (Throwable throwable) {
        Stage stageError = new Stage();
        stageError.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader loaderError = new FXMLLoader(CalculatorMain.class.getResource("/View/calculator_unknown_error_view.fxml"));
        try {
            Parent root = loaderError.load();
            stageError.setScene(new Scene(root));
            stageError.initStyle(StageStyle.UNDECORATED);
            stageError.show();

            CalculatorUnknownErrorController calculatorUnknownErrorController = loaderError.getController();
            calculatorUnknownErrorController.setErrorText(throwable);
            calculatorUnknownErrorController.setGeneralController(loader.getController());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void start (Stage stage) throws Exception {
        loader = new FXMLLoader(CalculatorMain.class.getResource("/View/calculator_view.fxml"));
        Parent root = loader.load();

        stage.setScene(new Scene(root));
        stage.setTitle("Calculator");
        stage.setMinHeight(500);
        stage.setMinWidth(320);
        stage.initStyle(StageStyle.UNDECORATED);
        Thread.setDefaultUncaughtExceptionHandler(CalculatorMain::catchUnknownError);

        stage.show();
    }
}
