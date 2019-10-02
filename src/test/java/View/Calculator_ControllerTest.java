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
    void clickNumberValid() {
//Integer number
//        inputNumber("0", "0", "");
        inputNumber("1", "001", "C");
        inputNumber("99", "99", "C");
        inputNumber("1", "001", "C");
        inputNumber("999", "999", "C");
        inputNumber("1", "0001", "C");
        inputNumber("9 999", "9999", "C");
        inputNumber("1", "00001", "C");
        inputNumber("99 999", "99999", "C");
        inputNumber("1", "000001", "C");
        inputNumber("999 999", "999999", "C");
        inputNumber("1", "0000001", "C");
        inputNumber("9 999 999", "9999999", "C");
        inputNumber("1", "00000001", "C");
        inputNumber("99 999 999", "99999999", "C");
        inputNumber("1", "000000001", "C");
        inputNumber("999 999 999", "999999999", "C");
        inputNumber("1", "0000000001", "C");
        inputNumber("9 999 999 999", "9999999999", "C");
        inputNumber("1", "00000000001", "C");
        inputNumber("99 999 999 999", "99999999999", "C");
        inputNumber("1", "000000000001", "C");
        inputNumber("999 999 999 999", "999999999999", "C");
        inputNumber("1", "0000000000001", "C");
        inputNumber("9 999 999 999 999", "9999999999999", "C");
        inputNumber("1", "00000000000001", "C");
        inputNumber("99 999 999 999 999", "99999999999999", "C");
        inputNumber("1", "000000000000001", "C");
        inputNumber("999 999 999 999 999", "999999999999999", "C");
        inputNumber("1", "0000000000000001", "C");
        inputNumber("9 999 999 999 999 999", "9999999999999999", "CE");
        inputNumber("1", "00000000000000001", "C");
        inputNumber("9 999 999 999 999 999", "99999999999999999", "CE");
        //Double
//        inputNumber("0,9999999999999999", "0,9999999999999999", "CE");
//        inputNumber("999 999 999 999 999,9", "999999999999999,9", "CE");
//        inputNumber("99 999 999 999 999,99", "99999999999999,99", "CE");
//        inputNumber("9 999 999 999 999,999", "9999999999999,999", "CE");
//        inputNumber("999 999 999 999,9999", "999999999999,9999", "CE");
//        inputNumber("99 999 999 999,99999", "99999999999,99999", "CE");
//        inputNumber("9 999 999 999,999999", "9999999999,999999", "CE");
//        inputNumber("999 999 999,9999999", "999999999,9999999", "CE");
//        inputNumber("99 999 999,99999999", "99999999,99999999", "CE");
//        inputNumber("9 999 999,999999999", "9999999,999999999", "CE");
//        inputNumber("999 999,9999999999", "999999,9999999999", "CE");
//        inputNumber("99 999,99999999999", "99999,99999999999", "CE");
//        inputNumber("9 999,999999999999", "9999,999999999999", "CE");
//        inputNumber("999,9999999999999", "999,9999999999999", "CE");
//        inputNumber("99,99999999999999", "99,99999999999999", "CE");
//        inputNumber("9,999999999999999", "9,999999999999999", "CE");

    }
    private void inputNumber(String result, String buttonsPressed, String afterInput) {
        checkInputNumber("-"+result, buttonsPressed+"n", afterInput);
        checkInputNumber(result, buttonsPressed+"nn", afterInput);
        checkInputNumber(result, buttonsPressed, afterInput);
    }

    void checkInputNumber(String result, String buttonsPressed, String afterInput) {
        mouseClicked(result, buttonsPressed, afterInput);
        assertEquals(result, outLabel.getText());
        clearDisplay(afterInput);

        keyNumpadTyped(result, buttonsPressed);
        assertEquals(result, outLabel.getText());
        clearDisplay(afterInput);

        keyTyped(result, buttonsPressed);
        assertEquals(result, outLabel.getText());
        clearDisplay(afterInput);
    }
    
    private void keyTyped(String result, String buttonsPressed) {
        for (char buttonPressed : buttonsPressed.toCharArray()) {
            type(keyboardInput(String.valueOf(buttonPressed)));
        }

    }

    private void keyNumpadTyped(String result, String buttonsPressed) {
        for (char buttonPressed : buttonsPressed.toCharArray()) {
            type(keyboardNumpadInput(String.valueOf(buttonPressed)));
        }
    }

    private void mouseClicked(String result, String buttonsPressed, String afterInput) {
        for (char buttonPressed : buttonsPressed.toCharArray()) {
            clickOn((Button) from(root).lookup("#" + mouseInput(String.valueOf(buttonPressed))).query());
        }
    }

    void clearDisplay(String clearButton) {
        if (clearButton.equals("C") || clearButton.equals("CE")) {
            clickOn((Button) from(root).lookup("#" + clearButton).query());
            assertEquals("0", outLabel.getText());
        }
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