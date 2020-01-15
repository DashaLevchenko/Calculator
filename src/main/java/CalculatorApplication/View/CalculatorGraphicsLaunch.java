package CalculatorApplication.View;

import CalculatorApplication.Controller.CalculatorUnknownError;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * Class launches graphics of calculator application.
 */
public class CalculatorGraphicsLaunch extends Application {
    /**
     * This variable keeps minimal height of application window
     */
    private static final int MIN_HEIGHT = 500;
    /**
     * This variable keeps minimal width of application window
     */
    private static final int MIN_WIDTH = 320;

    public static void launchCalculator (String[] args) {
        Application.launch(args);
    }



    @Override
    public void start (Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(CalculatorGraphicsLaunch.class.getResource("/CalculatorApplication/View/calculator_view.fxml"));

        Parent root = loader.load();

        stage.setScene(new Scene(root));
        stage.setMinHeight(MIN_HEIGHT);
        stage.setMinWidth(MIN_WIDTH);
        stage.initStyle(StageStyle.UNDECORATED);

        stage.show();

        CalculatorUnknownError calculatorUnknownError = new CalculatorUnknownError(loader.getController());
        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> calculatorUnknownError.catchUnknownError(throwable));

    }
}
