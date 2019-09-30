package View;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;


class Calculator_ControllerTest extends ApplicationTest {
Parent root;
    @Override
    public void start(Stage stage) throws Exception {
        root = FXMLLoader.load(Calculator_Main.class.getResource("calculator_view.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
        stage.toFront();

    }

    @Test
    void clickNumber(){
        Button button = from(root).lookup("#nine").query();
        clickOn(button);
        button = from(root).lookup("#two").query();
        clickOn(button);
        button = from(root).lookup("#two").query();
        clickOn(button);
        button = from(root).lookup("#two").query();
        clickOn(button);
        button = from(root).lookup("#two").query();
        clickOn(button);
        button = from(root).lookup("#two").query();
        clickOn(button);

        Label outLabel = from(root).lookup("#outText").query();
        assertEquals("922 222", outLabel.getText());
    }
}