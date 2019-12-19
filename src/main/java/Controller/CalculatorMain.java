package Controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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


    private static void printError ( ) {
        CalculatorController calculatorController = loader.getController();

        calculatorController.setUnknownError(true);
        calculatorController.printError("Something wrong!\n"  +
                "Please, press any available button and try again\n" +
                "or restart calculator.\n" +
                "If error happens again, please, reinstall calculator.");
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
