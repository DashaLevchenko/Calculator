
package Controller;


        import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
        import org.junit.jupiter.api.Test;
        import org.testfx.framework.junit5.ApplicationTest;

import java.awt.*;
import java.awt.event.InputEvent;
import java.math.BigDecimal;

import static javafx.scene.input.KeyCode.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

//import org.loadui.testfx.utils.*;


class TestContr extends ApplicationTest {
    private Parent root;
    private Label outLabel;
    private Label outOperationMemory;

    @Override
    public void start(Stage stage) throws Exception {
        root = FXMLLoader.load(Calculator_Main.class.getResource("/View/calculator_view.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
        stage.toFront();
    }

    private Robot robot;

    {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
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

    @BeforeEach
    void outMemory() {
        outOperationMemory = from(root).lookup("#outOperationMemory").query();
        outLabel = from(root).lookup("#outText").query();
    }


    @Test
    void checkNegate(){
        assertNumber("0", " +/-", "");//neg
        assertNumber("-6", "0000006 +/-", ""); //ne
        assertNumber("11 111", "111111 +/- bs  +/-", "");//ne
        assertNumber("-222,3333333333333", "222,3333333333333 +/-", "");//neg
        assertNumber("222,3333333333333", "222,3333333333333 +/- +/-", "");//neg
        assertNumber("-222,3333333333333", "222,3333333333333 +/- 44", "");
        //Input with backspace, comma, negate
        assertNumber("-1 111 111 111,", "1111111111, bs  +/- ,", "");//neg

    }




    void assertNumber(String result, String buttonsPressed, String outOperationMemoryResult) {
        checkKeyInputNumber(result, buttonsPressed, outOperationMemoryResult);
    }

    void checkKeyInputNumber(String result, String buttonsPressed, String outOperationMemoryResult) {
        for (String buttonPressed : buttonsPressed.split(" ")) {
            if (isNumber(buttonPressed)) {
                for (char idButton : buttonPressed.toCharArray()) {
                    keyboardInput(String.valueOf(idButton));
                }
            } else {
                keyboardInput(buttonPressed);
            }
        }
        assertEquals(result, outLabel.getText());
        assertEquals(outOperationMemoryResult, outOperationMemory.getText());
        type(ESCAPE);
    }

    private boolean isNumber(String buttonPressed) {
        try {
            new BigDecimal(buttonPressed.replace(",", "."));
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    void checkNumpadInputNumber(String result, String buttonsPressed, String clearButtonPressed) {
        for (String buttonPressed : buttonsPressed.split(" ")) {
            if (isNumber(buttonPressed)) {
                for (char idButton : buttonPressed.toCharArray()) {
                    keyboardNumpadInput(String.valueOf(idButton));
                }
            } else {
                keyboardNumpadInput(buttonPressed);
            }
        }
        assertEquals(result, outLabel.getText());
        type(ESCAPE);
    }


    void checkMouseInputNumber(String result, String buttonsPressed, String clearButtonPressed) {
        for (String buttonPressed : buttonsPressed.split(" ")) {
            if (isNumber(buttonPressed)) {
                for (char idButton : buttonPressed.toCharArray()) {
                    mouseInput(String.valueOf(idButton));
                }
            } else {
                mouseInput(buttonPressed);
            }
        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String actual = outLabel.getText();
        assertEquals(result, actual);
        type(ESCAPE);
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
            type(COMMA);
        } else if (idButton.equals("+/-")) {
            type(F9);
        } else if (idButton.equals("bs")) {
            type(BACK_SPACE);
        } else if (idButton.equals("c")) {
            type(ESCAPE);
        } else if (idButton.equals("ce")) {
            type(DELETE);
        } else if (idButton.equals("+")) {
            type(ADD);
        } else if (idButton.equals("-")) {
            type(SUBTRACT);
        } else if (idButton.equals("*")) {
            type(MULTIPLY);
        } else if (idButton.equals("√")) {
            push(new KeyCodeCombination(DIGIT2, KeyCombination.SHIFT_DOWN));
        } else if (idButton.equals("%")) {
            push(new KeyCodeCombination(DIGIT5, KeyCombination.SHIFT_DOWN));
        } else if (idButton.equals("1/x")) {
            type(R);
        } else if (idButton.equals("x²")) {
            type(Q);
        } else if (idButton.equals("/")) {
            type(DIVIDE);
        } else if (idButton.equals("=")) {
            type(ENTER);
        } else if (idButton.equals("MS")) {
            push(new KeyCodeCombination(M, KeyCombination.CONTROL_DOWN));
        } else if (idButton.equals("M+")) {
            push(new KeyCodeCombination(P, KeyCombination.CONTROL_DOWN));
        } else if (idButton.equals("M-")) {
            push(new KeyCodeCombination(Q, KeyCombination.CONTROL_DOWN));
        } else if (idButton.equals("MC")) {
            push(new KeyCodeCombination(L, KeyCombination.CONTROL_DOWN));
        } else if (idButton.equals("MR")) {
            push(new KeyCodeCombination(R, KeyCombination.CONTROL_DOWN));
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
            type(COMMA);
        } else if (idButton.equals("+/-")) {
            type(F9);
        } else if (idButton.equals("bs")) {
            type(BACK_SPACE);
        } else if (idButton.equals("c")) {
            type(ESCAPE);
        } else if (idButton.equals("ce")) {
            type(DELETE);
        } else if (idButton.equals("+")) {
            push(new KeyCodeCombination(EQUALS, KeyCombination.SHIFT_DOWN));
        } else if (idButton.equals("-")) {
            type(MINUS);
        } else if (idButton.equals("*")) {
            push(new KeyCodeCombination(DIGIT8, KeyCombination.SHIFT_DOWN));
        } else if (idButton.equals("√")) {
            push(new KeyCodeCombination(DIGIT2, KeyCombination.SHIFT_DOWN));
        } else if (idButton.equals("%")) {
            push(new KeyCodeCombination(DIGIT5, KeyCombination.SHIFT_DOWN));
        } else if (idButton.equals("1/x")) {
            type(R);
        } else if (idButton.equals("x²")) {
            type(Q);
        } else if (idButton.equals("/")) {
            type(SLASH);
        } else if (idButton.equals("=")) {
            type(ENTER);
        } else if (idButton.equals("MS")) {
            push(new KeyCodeCombination(M, KeyCombination.CONTROL_DOWN));
        } else if (idButton.equals("M+")) {
            push(new KeyCodeCombination(P, KeyCombination.CONTROL_DOWN));
        } else if (idButton.equals("M-")) {
            push(new KeyCodeCombination(Q, KeyCombination.CONTROL_DOWN));
        } else if (idButton.equals("MC")) {
            push(new KeyCodeCombination(L, KeyCombination.CONTROL_DOWN));
        } else if (idButton.equals("MR")) {
            push(new KeyCodeCombination(R, KeyCombination.CONTROL_DOWN));
        }
    }

    void mouseInput(String idButtonClickedMouse) {
        javafx.scene.control.Button button = null;
        if (idButtonClickedMouse.equals("1")) {
            button = (Button) from(root).lookup("#one").query();
        } else if (idButtonClickedMouse.equals("2")) {
            button = from(root).lookup("#two").query();
        } else if (idButtonClickedMouse.equals("3")) {
            button = from(root).lookup("#three").query();
        } else if (idButtonClickedMouse.equals("4")) {
            button = from(root).lookup("#four").query();
        } else if (idButtonClickedMouse.equals("5")) {
            button = from(root).lookup("#five").query();
        } else if (idButtonClickedMouse.equals("6")) {
            button = from(root).lookup("#six").query();
        } else if (idButtonClickedMouse.equals("7")) {
            button = from(root).lookup("#seven").query();
        } else if (idButtonClickedMouse.equals("8")) {
            button = from(root).lookup("#eight").query();
        } else if (idButtonClickedMouse.equals("9")) {
            button = from(root).lookup("#nine").query();
        } else if (idButtonClickedMouse.equals("0")) {
            button = from(root).lookup("#zero").query();
        } else if (idButtonClickedMouse.equals(",")) {
            button = from(root).lookup("#point").query();
        } else if (idButtonClickedMouse.equals("+/-")) {
            button = from(root).lookup("#plusMinus").query();
        } else if (idButtonClickedMouse.equals("bs")) {
            button = from(root).lookup("#Backspace").query();
        } else if (idButtonClickedMouse.equals("c")) {
            button = from(root).lookup("#C").query();
        } else if (idButtonClickedMouse.equals("d")) {
            button = from(root).lookup("#CE").query();
        } else if (idButtonClickedMouse.equals("+")) {
            button = from(root).lookup("#add").query();
        } else if (idButtonClickedMouse.equals("-")) {
            button = from(root).lookup("#subtract").query();
        } else if (idButtonClickedMouse.equals("*")) {
            button = from(root).lookup("#multiply").query();
        } else if (idButtonClickedMouse.equals("√")) {
            button = from(root).lookup("#√").query();
        } else if (idButtonClickedMouse.equals("%")) {
            button = from(root).lookup("#percent").query();
        } else if (idButtonClickedMouse.equals("1/x")) {
            button = from(root).lookup("#oneDivideX").query();
        } else if (idButtonClickedMouse.equals("x²")) {
            button = from(root).lookup("#sqrX").query();
        } else if (idButtonClickedMouse.equals("/")) {
            button = from(root).lookup("#divide").query();
        } else if (idButtonClickedMouse.equals("=")) {
            button = from(root).lookup("#equal").query();
        } else if (idButtonClickedMouse.equals("MS")) {
            button = from(root).lookup("#memoryStore").query();
        } else if (idButtonClickedMouse.equals("M+")) {
            button = from(root).lookup("#memoryAdd").query();
        } else if (idButtonClickedMouse.equals("M-")) {
            button = from(root).lookup("#memorySubtract").query();
        } else if (idButtonClickedMouse.equals("MC")) {
            button = from(root).lookup("#memoryClear").query();
        } else if (idButtonClickedMouse.equals("MR")) {
            button = from(root).lookup("#memoryRecall").query();
        }


        if (button != null) {
            Scene scene = button.getScene();
            double sceneX = scene.getWindow().getX();
            double sceneY = scene.getWindow().getY();

            int buttonX = (int) (sceneX + button.getBoundsInParent().getCenterX());
            int buttonY = (int) (sceneY + button.getBoundsInParent().getCenterY() + button.getParent().getBoundsInParent().getMinY() + button.getParent().getParent().getLayoutY());

            robot.mouseMove(buttonX, buttonY);
            try {
                Thread.sleep(100);
                robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}