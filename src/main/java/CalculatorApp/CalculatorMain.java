package CalculatorApp;

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
public class CalculatorMain extends Application {
//    private static CalculatorController calculatorController;


    public static void main (String[] args) {
        launch(args);
    }


//    public static CalculatorController getCalculatorController () {
//        return calculatorController;
//    }


    @Override
    public void start (Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(CalculatorMain.class.getResource("/View/calculator_view.fxml"));

        Parent root = loader.load();

        stage.setScene(new Scene(root));
        stage.setTitle("Calculator");
        stage.setMinHeight(500);
        stage.setMinWidth(320);
        stage.initStyle(StageStyle.UNDECORATED);

        stage.show();
//        calculatorController = loader.getController();
//        CalculatorUnknownError calculatorUnknownError = new CalculatorUnknownError();
//        Thread.setDefaultUncaughtExceptionHandler(calculatorUnknownError::catchUnknownError);

    }
}
