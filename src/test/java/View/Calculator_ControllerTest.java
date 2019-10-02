package View;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;


import static org.junit.jupiter.api.Assertions.assertEquals;


class Calculator_ControllerTest extends ApplicationTest {
    private Parent root;
    private Label outLabel;

    @Override
    public void start(Stage stage) throws Exception {
        root = FXMLLoader.load(Calculator_Main.class.getResource("calculator_view.fxml"));
        stage.setScene(new Scene(root));
        outLabel = from(root).lookup("#outText").query();
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
    void clickMouseNumberValid() {
//Integer number
        checkMouseInputNumber("1", "001");
        checkMouseInputNumber("99", "99");
        checkMouseInputNumber("1", "001");
        checkMouseInputNumber("999", "999");
        checkMouseInputNumber("1", "0001");
        checkMouseInputNumber("9 999", "9999");
        checkMouseInputNumber("1", "00001");
        checkMouseInputNumber("99 999", "99999");
        checkMouseInputNumber("1", "000001");
        checkMouseInputNumber("999 999", "999999");
        checkMouseInputNumber("1", "0000001");
        checkMouseInputNumber("9 999 999", "9999999");
        checkMouseInputNumber("1", "00000001");
        checkMouseInputNumber("99 999 999", "99999999");
        checkMouseInputNumber("1", "000000001");
        checkMouseInputNumber("999 999 999", "999999999");
        checkMouseInputNumber("1", "0000000001");
        checkMouseInputNumber("9 999 999 999", "9999999999");
        checkMouseInputNumber("1", "00000000001");
        checkMouseInputNumber("99 999 999 999", "99999999999");
        checkMouseInputNumber("1", "000000000001");
        checkMouseInputNumber("999 999 999 999", "999999999999");
        checkMouseInputNumber("1", "0000000000001");
        checkMouseInputNumber("9 999 999 999 999", "9999999999999");
        checkMouseInputNumber("1", "00000000000001");
        checkMouseInputNumber("99 999 999 999 999", "99999999999999");
        checkMouseInputNumber("1", "000000000000001");
        checkMouseInputNumber("999 999 999 999 999", "999999999999999");
        checkMouseInputNumber("1", "0000000000000001");
        checkMouseInputNumber("9 999 999 999 999 999", "9999999999999999");
        checkMouseInputNumber("1", "00000000000000001");
        checkMouseInputNumber("9 999 999 999 999 999", "99999999999999999");
        //Double
        checkMouseInputNumber("0,9999999999999999", "0,9999999999999999");
        checkMouseInputNumber("999 999 999 999 999,9", "999999999999999,9");
        checkMouseInputNumber("99 999 999 999 999,99", "99999999999999,99");
        checkMouseInputNumber("9 999 999 999 999,999", "9999999999999,999");
        checkMouseInputNumber("999 999 999 999,9999", "999999999999,9999");
        checkMouseInputNumber("99 999 999 999,99999", "99999999999,99999");
        checkMouseInputNumber("9 999 999 999,999999", "9999999999,999999");
        checkMouseInputNumber("999 999 999,9999999", "999999999,9999999");
        checkMouseInputNumber("99 999 999,99999999", "99999999,99999999");
        checkMouseInputNumber("9 999 999,999999999", "9999999,999999999");
        checkMouseInputNumber("999 999,9999999999", "999999,9999999999");
        checkMouseInputNumber("99 999,99999999999", "99999,99999999999");
        checkMouseInputNumber("9 999,999999999999", "9999,999999999999");
        checkMouseInputNumber("999,9999999999999", "999,9999999999999");
        checkMouseInputNumber("99,99999999999999", "99,99999999999999");
        checkMouseInputNumber("9,999999999999999", "9,999999999999999");
    }

    void checkMouseInputNumber(String result, String buttonsPressed) {
        for (char buttonPressed : buttonsPressed.toCharArray()) {
            clickOn((Button) from(root).lookup("#" + mouseInput(String.valueOf(buttonPressed))).query());
        }
        assertEquals(result, outLabel.getText());
        clickOn((Button) from(root).lookup("#C").query());
    }

    @Test
    void typeKeyNumberValid() {
//Integer number
        keyNumberInputInteger("1", "1", 1);
        keyNumberInputInteger("22", "2", 2);
        keyNumberInputInteger("333", "3", 3);
        keyNumberInputInteger("4 444", "4", 4);
        keyNumberInputInteger("55 555", "5", 5);
        keyNumberInputInteger("666 666", "6", 6);
        keyNumberInputInteger("7 777 777", "7", 7);
        keyNumberInputInteger("88 888 888", "8", 8);
        keyNumberInputInteger("999 999 999", "9", 9);
        keyNumberInputInteger("1 111 111 111", "1", 10);
        keyNumberInputInteger("22 222 222 222", "2", 11);
        keyNumberInputInteger("333 333 333 333", "3", 12);
        keyNumberInputInteger("4 444 444 444 444", "4", 13);
        keyNumberInputInteger("55 555 555 555 555", "5", 14);
        keyNumberInputInteger("666 666 666 666 666", "6", 15);
        keyNumberInputInteger("7 777 777 777 777 777", "7", 16);
        keyNumberInputInteger("8 888 888 888 888 888", "8", 17);

        keyNumberInputDouble("0,9999999999999999", "0", 1, "9", 16);
        keyNumberInputDouble("9 999 999 999 999 999,", "9", 16, "9", 1);
        keyNumberInputDouble("999 999 999 999 999,9", "9", 15, "9", 1);
        keyNumberInputDouble("99 999 999 999 999,99", "9", 14, "9", 2);
        keyNumberInputDouble("9 999 999 999 999,999", "9", 13, "9", 3);
        keyNumberInputDouble("999 999 999 999,9999", "9", 12, "9", 4);
        keyNumberInputDouble("99 999 999 999,99999", "9", 11, "9", 5);
        keyNumberInputDouble("9 999 999 999,999999", "9", 10, "9", 6);
        keyNumberInputDouble("999 999 999,9999999", "9", 9, "9", 7);
        keyNumberInputDouble("99 999 999,99999999", "9", 8, "9", 8);
        keyNumberInputDouble("9 999 999,999999999", "9", 7, "9", 9);
        keyNumberInputDouble("999 999,9999999999", "9", 6, "9", 10);
        keyNumberInputDouble("99 999,99999999999", "9", 5, "9", 11);
        keyNumberInputDouble("9 999,999999999999", "9", 4, "9", 12);
        keyNumberInputDouble("999,9999999999999", "9", 3, "9", 13);
        keyNumberInputDouble("99,99999999999999", "9", 2, "9", 14);
        keyNumberInputDouble("9,999999999999999", "9", 1, "9", 15);

    }

    private void keyNumberInputInteger(String result, String numberWillRepeat, int timesRepeatNumber) {
        type(keyboardNumpadInput(numberWillRepeat), timesRepeatNumber);
        assertEquals(result, outLabel.getText());
        clickOn((Button) from(root).lookup("#C").query());
        assertEquals("0", outLabel.getText());

        type(keyboardInput(numberWillRepeat), timesRepeatNumber);
        assertEquals(result, outLabel.getText());
        clickOn((Button) from(root).lookup("#C").query());
        assertEquals("0", outLabel.getText());
    }

    private void keyNumberInputDouble(String result, String numberWillRepeatBeforePoint, int timesRepeatNumberBeforePoint, String numberWillRepeatAfterPoint, int timesRepeatNumberAfterPoint) {
        type(keyboardNumpadInput(numberWillRepeatBeforePoint), timesRepeatNumberBeforePoint);
        type(keyboardNumpadInput(","));
        type(keyboardNumpadInput(numberWillRepeatAfterPoint), timesRepeatNumberAfterPoint);
        assertEquals(result, outLabel.getText());
        clickOn((Button) from(root).lookup("#C").query());
        assertEquals("0", outLabel.getText());

        type(keyboardInput(numberWillRepeatBeforePoint), timesRepeatNumberBeforePoint);
        type(keyboardInput(","));
        type(keyboardInput(numberWillRepeatAfterPoint), timesRepeatNumberAfterPoint);
        assertEquals(result, outLabel.getText());
        clickOn((Button) from(root).lookup("#C").query());
        assertEquals("0", outLabel.getText());
    }

    KeyCode keyboardNumpadInput(String idButtonClickedMouse) {
        KeyCode keyCode;
        if (idButtonClickedMouse.equals("1")) {
            keyCode = KeyCode.NUMPAD1;
        } else if (idButtonClickedMouse.equals("2")) {
            keyCode = KeyCode.NUMPAD2;
        } else if (idButtonClickedMouse.equals("3")) {
            keyCode = KeyCode.NUMPAD3;
        } else if (idButtonClickedMouse.equals("4")) {
            keyCode = KeyCode.NUMPAD4;
        } else if (idButtonClickedMouse.equals("5")) {
            keyCode = KeyCode.NUMPAD5;
        } else if (idButtonClickedMouse.equals("6")) {
            keyCode = KeyCode.NUMPAD6;
        } else if (idButtonClickedMouse.equals("7")) {
            keyCode = KeyCode.NUMPAD7;
        } else if (idButtonClickedMouse.equals("8")) {
            keyCode = KeyCode.NUMPAD8;
        } else if (idButtonClickedMouse.equals("9")) {
            keyCode = KeyCode.NUMPAD9;
        } else if (idButtonClickedMouse.equals("0")) {
            keyCode = KeyCode.NUMPAD0;
        } else if (idButtonClickedMouse.equals(",")) {
            keyCode = KeyCode.PERIOD;
        } else if (idButtonClickedMouse.equals("n")) {
            keyCode = KeyCode.F9;
        } else {
            keyCode = null;
        }

        return keyCode;
    }

    KeyCode keyboardInput(String idButtonClickedMouse) {
        KeyCode keyCode;
        if (idButtonClickedMouse.equals("1")) {
            keyCode = KeyCode.DIGIT1;
        } else if (idButtonClickedMouse.equals("2")) {
            keyCode = KeyCode.DIGIT2;
        } else if (idButtonClickedMouse.equals("3")) {
            keyCode = KeyCode.DIGIT3;
        } else if (idButtonClickedMouse.equals("4")) {
            keyCode = KeyCode.DIGIT4;
        } else if (idButtonClickedMouse.equals("5")) {
            keyCode = KeyCode.DIGIT5;
        } else if (idButtonClickedMouse.equals("6")) {
            keyCode = KeyCode.DIGIT6;
        } else if (idButtonClickedMouse.equals("7")) {
            keyCode = KeyCode.DIGIT7;
        } else if (idButtonClickedMouse.equals("8")) {
            keyCode = KeyCode.DIGIT8;
        } else if (idButtonClickedMouse.equals("9")) {
            keyCode = KeyCode.DIGIT9;
        } else if (idButtonClickedMouse.equals("0")) {
            keyCode = KeyCode.DIGIT0;
        } else if (idButtonClickedMouse.equals(",")) {
            keyCode = KeyCode.PERIOD;
        } else if (idButtonClickedMouse.equals("n")) {
            keyCode = KeyCode.F9;
        } else {
            keyCode = null;
        }

        return keyCode;
    }

    String mouseInput(String idButtonClickedMouse) {
        String idButton;
        if (idButtonClickedMouse.equals("1")) {
            idButton = "one";
        } else if (idButtonClickedMouse.equals("2")) {
            idButton = "two";
        } else if (idButtonClickedMouse.equals("3")) {
            idButton = "three";
        } else if (idButtonClickedMouse.equals("4")) {
            idButton = "four";
        } else if (idButtonClickedMouse.equals("5")) {
            idButton = "five";
        } else if (idButtonClickedMouse.equals("6")) {
            idButton = "six";
        } else if (idButtonClickedMouse.equals("7")) {
            idButton = "seven";
        } else if (idButtonClickedMouse.equals("8")) {
            idButton = "eight";
        } else if (idButtonClickedMouse.equals("9")) {
            idButton = "nine";
        } else if (idButtonClickedMouse.equals("0")) {
            idButton = "zero";
        } else if (idButtonClickedMouse.equals(",")) {
            idButton = "point";
        } else if (idButtonClickedMouse.equals("n")) {
            idButton = "plusMinus";
        } else {
            idButton = null;
        }
        return idButton;
    }
}