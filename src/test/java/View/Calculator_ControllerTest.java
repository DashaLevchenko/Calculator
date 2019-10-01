package View;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.service.adapter.impl.AwtRobotAdapter;


import java.awt.*;

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

    @BeforeAll
    static void config() throws Exception {
        System.setProperty("testfx.robot", "awt");
//        System.setProperty("testfx.robot", "glass");
//        System.setProperty("testfx.headless", "true");
//        System.setProperty("prism.order", "d3d");
//        System.setProperty("prism.text", "t2k");
//        System.setProperty("java.awt.headless", "true");

    }

    @Test
    void clickNumber() {
        buttonNumberClicked("0", new String[]{"zero"});
        buttonNumberClicked("0", new String[]{"zero", "zero", "zero", "zero", "zero", "zero", "zero", "zero", "zero", "zero", "zero", "zero", "zero", "zero", "zero"});
        buttonNumberClicked("999 999", new String[]{"nine", "nine", "nine", "nine", "nine", "nine"});
        buttonNumberClicked("9 999 999 999 999 999", new String[]{"nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine"});
        buttonNumberClicked("9 999 999 999 999 999", new String[]{"nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine"});
        buttonNumberClicked("-9 999 999 999 999 999", new String[]{"nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "plusMinus"});
        buttonNumberClicked("-9 999 999 999 999 999", new String[]{"nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "plusMinus"});
        buttonNumberClicked("9 999 999 999 999 999", new String[]{"nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "plusMinus", "plusMinus"});
        buttonNumberClicked("9 999 999 999 999 999", new String[]{"nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "plusMinus", "plusMinus"});

        buttonNumberClicked("999 999 999 999 999,9", new String[]{"nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "point", "nine"});
        buttonNumberClicked("99 999 999 999 999,99", new String[]{"nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "point", "nine", "nine"});
        buttonNumberClicked("9 999 999 999 999,999", new String[]{"nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "point", "nine", "nine", "nine"});
        buttonNumberClicked("999 999 999 999,9999", new String[]{"nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "point", "nine", "nine", "nine", "nine"});
        buttonNumberClicked("99 999 999 999,99999", new String[]{"nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "point", "nine", "nine", "nine", "nine", "nine"});
        buttonNumberClicked("9 999 999 999,999999", new String[]{"nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "point", "nine", "nine", "nine", "nine", "nine", "nine"});
        buttonNumberClicked("999 999 999,9999999", new String[]{"nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "point", "nine", "nine", "nine", "nine", "nine", "nine", "nine"});
        buttonNumberClicked("99 999 999,99999999", new String[]{"nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "point", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine"});
        buttonNumberClicked("9 999 999,999999999", new String[]{"nine", "nine", "nine", "nine", "nine", "nine", "nine", "point", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine"});
        buttonNumberClicked("999 999,9999999999", new String[]{"nine", "nine", "nine", "nine", "nine", "nine", "point", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine"});
        buttonNumberClicked("99 999,99999999999", new String[]{"nine", "nine", "nine", "nine", "nine", "point", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine"});
        buttonNumberClicked("9 999,999999999999", new String[]{"nine", "nine", "nine", "nine", "point", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine"});
        buttonNumberClicked("999,9999999999999", new String[]{"nine", "nine", "nine", "point", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine"});
        buttonNumberClicked("99,99999999999999", new String[]{"nine", "nine", "point", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine"});
        buttonNumberClicked("9,999999999999999", new String[]{"nine", "point", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine"});
        buttonNumberClicked("0,9999999999999999", new String[]{"zero", "point", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine", "nine"});


//type(KeyCode.NUMPAD9, 9);

    }

    void buttonNumberClicked(String result, String idButton[]) {
        for (String s : idButton) {
            clickOn((Button) from(root).lookup("#" + s).query());
        }
        Label outLabel = from(root).lookup("#outText").query();
        assertEquals(result, outLabel.getText());

        clickOn((Button) from(root).lookup("#C").query());
        assertEquals("0", outLabel.getText());
    }
}