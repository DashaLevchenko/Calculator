package View;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;


import static javafx.scene.input.KeyCode.*;
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


//n-negate;
// b-backspace;
// ,-",";
// d-clear number;
// c-clear all;
// r-square root;
// %-percent;
// x-one divide x;
// s-square;
    @Test
    void checkInput() {
        assertInputNumber("0", "n", "c");
        assertInputNumber("0", "b", "d");
        assertInputNumber("0", "1b", "c");
        assertInputNumber("0", "01b", "d");
        assertInputNumber("1", "001", "c");
        assertInputNumber("5", "000005", "c");
        assertInputNumber("1 111", "11111b", "d");
        assertInputNumber("-1 111", "11111nb", "d");
        assertInputNumber("0", "11111nbbbbb", "d");
        assertInputNumber("-6", "0000006n", "c");
        assertInputNumber("7", "00000007", "d");
        assertInputNumber("11 111", "111111nbn", "d");
        assertInputNumber("11 111 111", "11111111,b", "c");
        assertInputNumber("9", "0000000009", "d");
        assertInputNumber("0", "11111nbbbbb", "c");
        assertInputNumber("11", "000000000011", "d");
        assertInputNumber("-111 111 111", "111111111,bn", "c");
        assertInputNumber("-1 111 111 111,", "1111111111,bn,", "c");
        assertInputNumber("123", "000000000000123", "d");
        assertInputNumber("12 345", "0000000000000012345", "d");
        assertInputNumber("1 111 111 111 111 111", "1111111111111111,b", "c");
        assertInputNumber("12 345 678", "000000000000000012345678", "c");
        assertInputNumber("1 111 111 111 111 111", "1111111111111111,111111b", "d");
        assertInputNumber("123 456 789", "0000000000000000123456789", "c");
        assertInputNumber("0,9999999999999999", "0,9999999999999999", "d");
        assertInputNumber("0,9999999999999999", "0,9999999999999999999", "c");
        assertInputNumber("9,999999999999999", "9,999999999,999999,9", "d");
        assertInputNumber("-222,3333333333333", "222,3333333333333n", "c");
        assertInputNumber("222,3333333333333", "222,3333333333333nn", "d");
        assertInputNumber("-222,3333333333333", "222,3333333333333n33", "c");
        assertInputNumber("222,3333333333333", "222,3333333333333nn33,", "d");
        assertInputNumber("9 999 999 999 999 999", "99999999999999999", "c");
        assertInputNumber("999 999 999 999 999,9", "999999999999999,99999", "d");
        assertInputNumber("9 999 999 999 999 999,", "9999999999999999,9999", "c");
        assertInputNumber("-9 999 999 999 999 999,", "9999999999999999,9999n", "d");
        assertInputNumber("9 999 999 999 999 999,", "9999999999999999,9999nn", "c");
        assertInputNumber("9 999 999 999,999999", "9999999999,999999,9999", "d");
        assertInputNumber("-9 999 999 999,999999", "9999999999,999999,9999n", "d");
        assertInputNumber("-9 999 999 999,999999", "9999999999,999999,9999nnnnn99", "c");
        assertInputNumber("-1", "1111111111111111,111111nbbbbbbbbbbbbbbbb", "d");
        assertInputNumber("1,111111111111111", "1111111111111111,111111nbbbbbbbbbbbbbbbbn,1111111111111111", "c");
    }

    @Test
    void checkBinaryOperations() {
        assertInputNumber("2", "2=", "c");
        assertInputNumber("2", "2+", "c");
        assertInputNumber("2", "2-", "c");
        assertInputNumber("2", "2*", "c");
        assertInputNumber("2", "2/", "c");

        assertInputNumber("3", "2+3", "c");
        assertInputNumber("3", "2-3", "c");
        assertInputNumber("3", "2*3", "c");
        assertInputNumber("3", "2/3", "c");

        assertInputNumber("5", "2+3=", "c");
        assertInputNumber("-1", "2-3=", "c");
        assertInputNumber("6", "2*3=", "c");
        assertInputNumber("0,6666666666666667", "2/3=", "c");

        assertInputNumber("5", "2+3-", "c");
        assertInputNumber("-1", "2-3*", "c");
        assertInputNumber("6", "2*3/", "c");
        assertInputNumber("0,6666666666666667", "2/3-", "c");
//write combinations
//        assertInputNumber("5", "2+3-", "c");
//        assertInputNumber("-1", "2-3*", "c");
//        assertInputNumber("6", "2*3/", "c");
//        assertInputNumber("0,6666666666666667", "2/3-", "c");

        assertInputNumber("0", "2+3-=", "c");
        assertInputNumber("1", "2-3*=", "c");
        assertInputNumber("1", "2*3/=", "c");
        assertInputNumber("0", "2/3-=", "c");

        assertInputNumber("1", "2+-*/=", "c");
        assertInputNumber("4", "2-*/+=", "c");
        assertInputNumber("0", "2*/+-=", "c");
        assertInputNumber("4", "2/+-*=", "c");

        assertInputNumber("1", "2+3-*/=", "c");
        assertInputNumber("-2", "2-3*/+=", "c");
        assertInputNumber("0", "2*3/+-=", "c");
        assertInputNumber("0,4444444444444444", "2/3+-*=", "c");

    }

    void assertInputNumber(String result, String buttonsPressed, String clearButtonPressed) {
        checkMouseInputNumber(result, buttonsPressed, clearButtonPressed);
        checkKeyInputNumber(result, buttonsPressed, clearButtonPressed);
        checkNumpadInputNumber(result, buttonsPressed, clearButtonPressed);
    }

    void checkNumpadInputNumber(String result, String buttonsPressed, String clearButtonPressed) {
        for (char buttonPressed : buttonsPressed.toCharArray()) {
            keyboardNumpadInput(String.valueOf(buttonPressed));
        }

        assertEquals(result, outLabel.getText());
        keyboardNumpadInput(String.valueOf(clearButtonPressed));
    }

    void checkKeyInputNumber(String result, String buttonsPressed, String clearButtonPressed) {
        for (char buttonPressed : buttonsPressed.toCharArray()) {
            keyboardInput(String.valueOf(buttonPressed));
        }
        assertEquals(result, outLabel.getText());
        keyboardInput(String.valueOf(clearButtonPressed));
    }

    void checkMouseInputNumber(String result, String buttonsPressed, String clearButtonPressed) {
        for (char buttonPressed : buttonsPressed.toCharArray()) {
            mouseInput(String.valueOf(buttonPressed));
        }
        assertEquals(result, outLabel.getText());
        mouseInput(String.valueOf(clearButtonPressed));
    }


    void keyboardNumpadInput(String idButton) {
        if (idButton.equals("1")) {
            type(NUMPAD1);
        } else if (idButton.equals("2")) {
            type(NUMPAD2);
        } else if (idButton.equals("3")) {
            type(NUMPAD3);
        } else if (idButton.equals("4")) {
            type(NUMPAD4);
        } else if (idButton.equals("5")) {
            type(NUMPAD5);
        } else if (idButton.equals("6")) {
            type(NUMPAD6);
        } else if (idButton.equals("7")) {
            type(NUMPAD7);
        } else if (idButton.equals("8")) {
            type(NUMPAD8);
        } else if (idButton.equals("9")) {
            type(NUMPAD9);
        } else if (idButton.equals("0")) {
            type(NUMPAD0);
        } else if (idButton.equals(",")) {
            type(PERIOD);
        } else if (idButton.equals("n")) {
            type(F9);
        } else if (idButton.equals("b")) {
            type(BACK_SPACE);
        } else if (idButton.equals("c")) {
            type(ESCAPE);
        } else if (idButton.equals("d")) {
            type(DELETE);
        } else if (idButton.equals("+")) {
            type(ADD);
        } else if (idButton.equals("-")) {
            type(SUBTRACT);
        } else if (idButton.equals("*")) {
            type(MULTIPLY);
        } else if (idButton.equals("r")) {
            push(new KeyCodeCombination(DIGIT2, KeyCombination.SHIFT_DOWN));
        } else if (idButton.equals("%")) {
            push(new KeyCodeCombination(DIGIT5, KeyCombination.SHIFT_DOWN));
        } else if (idButton.equals("x")) {
            type(R);
        } else if (idButton.equals("s")) {
            type(Q);
        } else if (idButton.equals("/")) {
            type(DIVIDE);
        } else if (idButton.equals("=")) {
            type(ENTER);
        }
    }

    void keyboardInput(String idButton) {
        if (idButton.equals("1")) {
            type(DIGIT1);
        } else if (idButton.equals("2")) {
            type(DIGIT2);
        } else if (idButton.equals("3")) {
            type(DIGIT3);
        } else if (idButton.equals("4")) {
            type(DIGIT4);
        } else if (idButton.equals("5")) {
            type(DIGIT5);
        } else if (idButton.equals("6")) {
            type(DIGIT6);
        } else if (idButton.equals("7")) {
            type(DIGIT7);
        } else if (idButton.equals("8")) {
            type(DIGIT8);
        } else if (idButton.equals("9")) {
            type(DIGIT9);
        } else if (idButton.equals("0")) {
            type(DIGIT0);
        } else if (idButton.equals(",")) {
            type(PERIOD);
        } else if (idButton.equals("n")) {
            type(F9);
        } else if (idButton.equals("b")) {
            type(BACK_SPACE);
        } else if (idButton.equals("c")) {
            type(ESCAPE);
        } else if (idButton.equals("d")) {
            type(DELETE);
        } else if (idButton.equals("+")) {
            push(new KeyCodeCombination(EQUALS, KeyCombination.SHIFT_DOWN));
        } else if (idButton.equals("-")) {
            type(MINUS);
        } else if (idButton.equals("*")) {
            push(new KeyCodeCombination(DIGIT8, KeyCombination.SHIFT_DOWN));
        } else if (idButton.equals("r")) {
            push(new KeyCodeCombination(DIGIT2, KeyCombination.SHIFT_DOWN));
        } else if (idButton.equals("%")) {
            push(new KeyCodeCombination(DIGIT5, KeyCombination.SHIFT_DOWN));
        } else if (idButton.equals("x")) {
            type(R);
        } else if (idButton.equals("s")) {
            type(Q);
        } else if (idButton.equals("/")) {
            type(SLASH);
        } else if (idButton.equals("=")) {
            type(ENTER);
        }

    }

    void mouseInput(String idButtonClickedMouse) {
        if (idButtonClickedMouse.equals("1")) {
            clickOn((Button) from(root).lookup("#one").query());
        } else if (idButtonClickedMouse.equals("2")) {
            clickOn((Button) from(root).lookup("#two").query());
        } else if (idButtonClickedMouse.equals("3")) {
            clickOn((Button) from(root).lookup("#three").query());
        } else if (idButtonClickedMouse.equals("4")) {
            clickOn((Button) from(root).lookup("#four").query());
        } else if (idButtonClickedMouse.equals("5")) {
            clickOn((Button) from(root).lookup("#five").query());
        } else if (idButtonClickedMouse.equals("6")) {
            clickOn((Button) from(root).lookup("#six").query());
        } else if (idButtonClickedMouse.equals("7")) {
            clickOn((Button) from(root).lookup("#seven").query());
        } else if (idButtonClickedMouse.equals("8")) {
            clickOn((Button) from(root).lookup("#eight").query());
        } else if (idButtonClickedMouse.equals("9")) {
            clickOn((Button) from(root).lookup("#nine").query());
        } else if (idButtonClickedMouse.equals("0")) {
            clickOn((Button) from(root).lookup("#zero").query());
        } else if (idButtonClickedMouse.equals(",")) {
            clickOn((Button) from(root).lookup("#point").query());
        } else if (idButtonClickedMouse.equals("n")) {
            clickOn((Button) from(root).lookup("#plusMinus").query());
        } else if (idButtonClickedMouse.equals("b")) {
            clickOn((Button) from(root).lookup("#Backspace").query());
        } else if (idButtonClickedMouse.equals("c")) {
            clickOn((Button) from(root).lookup("#C").query());
        } else if (idButtonClickedMouse.equals("d")) {
            clickOn((Button) from(root).lookup("#CE").query());
        } else if (idButtonClickedMouse.equals("+")) {
            clickOn((Button) from(root).lookup("#add").query());
        } else if (idButtonClickedMouse.equals("-")) {
            clickOn((Button) from(root).lookup("#subtract").query());type();
        } else if (idButtonClickedMouse.equals("*")) {
            clickOn((Button) from(root).lookup("#multiply").query());
        } else if (idButtonClickedMouse.equals("r")) {
            clickOn((Button) from(root).lookup("#sqrt").query());
        } else if (idButtonClickedMouse.equals("%")) {
            clickOn((Button) from(root).lookup("#percent").query());
        } else if (idButtonClickedMouse.equals("x")) {
            clickOn((Button) from(root).lookup("#oneDivideX").query());
        } else if (idButtonClickedMouse.equals("s")) {
            clickOn((Button) from(root).lookup("#sqrX").query());
        } else if (idButtonClickedMouse.equals("/")) {
            clickOn((Button) from(root).lookup("#divide").query());
        } else if (idButtonClickedMouse.equals("=")) {
            clickOn((Button) from(root).lookup("#equal").query());
        }
    }
}