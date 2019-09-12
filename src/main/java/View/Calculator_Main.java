package View;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;



public class Calculator_Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

//    static Parent root;
    static String textInputted = "";

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

//        keyInput(root);

//        root.setOnKeyReleased(new EventHandler<KeyEvent>() {
//            public void handle(KeyEvent keyEvent) {
//                if (keyEvent.getCode() == KeyCode.DIGIT1) {
//                    new Calculator_Controller().getOutText().setText(keyEvent.getText());
//                }
//
//            }
//        });

                stage.show();
    }
//    public static void keyInput(Parent root){
//        root.setOnKeyPressed(new EventHandler<KeyEvent>() {
//            public void handle(KeyEvent keyEvent) {
//                if (keyEvent.getCode() == KeyCode.DIGIT1) {
//                    textInputted += keyEvent.getText();
//                }
//                new Calculator_Controller().getOutText().setText(textInputted);
//            }
//        });
//    }
}
