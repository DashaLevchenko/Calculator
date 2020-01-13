package CalculatorApplication.View;

import CalculatorApplication.Controller.CalculatorController;
import CalculatorApplication.Controller.CalculatorUnknownError;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * Class launches calculator application.
 */
public class CalculatorGraphicsLaunch extends Application {
    private static CalculatorController calculatorController;

    public static void main (String[] args) {
        Application.launch(args);
    }


    public static CalculatorController getCalculatorController () {
        return calculatorController;
    }


    @Override
    public void start (Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(CalculatorGraphicsLaunch.class.getResource("/CalculatorApplication/View/calculator_view.fxml"));

        Parent root = loader.load();

        stage.setScene(new Scene(root));
        stage.setMinHeight(500);
        stage.setMinWidth(320);
        stage.initStyle(StageStyle.UNDECORATED);

        stage.show();

        calculatorController = loader.getController();
        CalculatorUnknownError calculatorUnknownError = new CalculatorUnknownError();
        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> calculatorUnknownError.catchUnknownError(throwable));

    }
}
