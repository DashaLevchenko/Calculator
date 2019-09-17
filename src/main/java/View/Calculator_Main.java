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
        Scene scene = new Scene(root);
        stage.setScene(scene);
//        stage.setScene(new Scene(root));
        stage.setMinHeight(500);
        stage.setMinWidth(320);
        stage.initStyle(StageStyle.UNDECORATED);

        Resize resize = new Resize(stage);
        resize.resizeAllStage();

                stage.show();
    }
}
