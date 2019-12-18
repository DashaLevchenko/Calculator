package Controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Class launches calculator application
 */
public class CalculatorMain extends Application {

    private static FXMLLoader loader;

    public static void main (String[] args) {
        launch(args);
    }

    private static void catchError (Thread thread, Throwable throwable) {
        printError();
        throwable.printStackTrace();
    }


    private static void printError () {
        CalculatorController calculatorController = loader.getController();
        Label generalDisplay = calculatorController.getGeneralDisplay();
        String lastPrintedResult = generalDisplay.getText();

        calculatorController.setUnknownError(true);
        calculatorController.printError( "Something wrong\n" +
                "Please, press any available button\n" +
                "and try again.\n"+
                "Your last result: "+ lastPrintedResult);
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
        Thread.setDefaultUncaughtExceptionHandler(CalculatorMain::catchError);

        stage.show();
    }
}
