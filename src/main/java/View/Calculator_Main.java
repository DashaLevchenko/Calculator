package View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;



public class Calculator_Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/View/calculator_view.fxml"));
        stage.setTitle("Calculator");
        stage.setScene(new Scene(root));
//        stage.setScene(new Scene(root));
        stage.setMinHeight(510);
        stage.setMinWidth(336);
        stage.initStyle(StageStyle.UNDECORATED);

        Resize.addResizeListener(stage);

        stage.show();
    }
}
