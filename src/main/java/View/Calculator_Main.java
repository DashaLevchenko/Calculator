package View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;


public class Calculator_Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/View/calculator_view.fxml"));
        stage.setTitle("Calculator");
//        stage.setScene(new Scene(root, 320, 470));
        stage.setScene(new Scene(root));
        stage.setMinHeight(510);
        stage.setMinWidth(336);
        stage.initStyle(StageStyle.UNDECORATED);

        stage.show();
    }
}
