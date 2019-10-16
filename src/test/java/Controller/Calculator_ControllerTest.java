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
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;


import java.math.BigDecimal;

import static javafx.scene.input.KeyCode.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


class Calculator_ControllerTest extends ApplicationTest {
    private Parent root;
    private Label outLabel;

    @Override
    public void start(Stage stage) throws Exception {
        root = FXMLLoader.load(Calculator_Main.class.getResource("/View/calculator_view.fxml"));
        stage.setScene(new Scene(root));
        outLabel = from(root).lookup("#outText").query();
        stage.show();
        stage.toFront();
    }

    @BeforeAll
    static void config() throws Exception {
//        System.setProperty("testfx.robot", "awt");
        System.setProperty("testfx.robot", "glass");
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
        assertNumber("0", " negate", "c");
        assertNumber("0", " bs", "d");
        assertNumber("0", "1 bs", "c");
        assertNumber("0", "01 bs", "d");
        assertNumber("1", "001", "c");
        assertNumber("5", "000005", "c");
        assertNumber("1 111", "11111 bs", "d");
        assertNumber("-1 111", "11111 negate bs", "d");
        assertNumber("0", "11111 negate bs bs bs bs bs", "d");
        assertNumber("-6", "0000006 negate", "c");
        assertNumber("7", "00000007", "d");
        assertNumber("11 111", "111111 negate bs  negate", "d");
        assertNumber("11 111 111", "11111111, bs", "c");
        assertNumber("9", "0000000009", "d");
        assertNumber("0", "11111 negate bs  bs  bs  bs  bs", "c");
        assertNumber("11", "000000000011", "d");
        assertNumber("-111 111 111", "111111111, bs negate", "c");
        assertNumber("-1 111 111 111,", "1111111111, bs  negate ,", "c");
        assertNumber("123", "000000000000123", "d");
        assertNumber("12 345", "0000000000000012345", "d");
        assertNumber("1 111 111 111 111 111", "1111111111111111, bs", "c");
        assertNumber("12 345 678", "000000000000000012345678", "c");
        assertNumber("1 111 111 111 111 111", "1111111111111111,111111 bs", "d");
        assertNumber("123 456 789", "0000000000000000123456789", "c");
        assertNumber("0,9999999999999999", "0,9999999999999999", "d");
        assertNumber("0,9999999999999999", "0,9999999999999999999", "c");
        assertNumber("9,999999999999999", "9,999999999 , 999999 , 9", "d");
        assertNumber("-222,3333333333333", "222,3333333333333 negate", "c");
        assertNumber("222,3333333333333", "222,3333333333333 negate negate", "d");
        assertNumber("-222,3333333333333", "222,3333333333333 negate 33", "c");
        assertNumber("222,3333333333333", "222,3333333333333 negate negate 33,", "d");
        assertNumber("9 999 999 999 999 999", "99999999999999999", "c");
        assertNumber("999 999 999 999 999,9", "999999999999999,99999", "d");
        assertNumber("9 999 999 999 999 999,", "9999999999999999,9999", "c");
        assertNumber("-9 999 999 999 999 999,", "9999999999999999,9999 negate", "d");
        assertNumber("9 999 999 999 999 999,", "9999999999999999 , 9999 negate negate", "c");
        assertNumber("9 999 999 999,999999", "9999999999,999999 , 9999", "d");
        assertNumber("-9 999 999 999,999999", "9999999999,999999 , 9999 negate", "d");
        assertNumber("-9 999 999 999,999999", "9999999999,999999 , 9999 negate negate negate negate negate 99", "c");
        assertNumber("-1", "1111111111111111,111111 negate bs bs bs bs bs bs bs bs bs bs bs bs bs bs bs bs", "d");
        assertNumber("1,111111111111111", "1111111111111111,111111 negate bs bs bs bs bs bs bs bs bs bs bs bs bs bs bs bs  negate ,1111111111111111001", "c");
    }
//
//    @Test
//    void checkBinaryOperations() {
//        //One operand
//        //One operand with one operation
//        assertNumber("2", "2=", "c");
//        assertNumber("-2", "2=n", "c");
//        assertNumber("3", "3+", "c");
//        assertNumber("4", "4-", "c");
//        assertNumber("5", "5*", "c");
//        assertNumber("6", "6/", "c");
//        //One operand and change operation
//        assertNumber("13", "13+-*/", "c");
//        assertNumber("16", "16-*/+", "c");
//        assertNumber("18", "18*/+-", "c");
//        assertNumber("19", "19/+-*", "c");
//        //One operand and change operation with equal
//        assertNumber("1", "2+-*/=", "c");
//        assertNumber("4", "2-*/+=", "c");
//        assertNumber("0", "2*/+-=", "c");
//        assertNumber("4", "2/+-*=", "c");
//        //Two operands
//        //Two operands with one operation
//        assertNumber("33", "24+33", "c");
//        assertNumber("47", "37-47", "c");
//        assertNumber("55", "49*55", "c");
//        assertNumber("69", "52/69", "c");
//        //Two operands with one operation and equal
//        assertNumber("104", "71+33=", "c");
//        assertNumber("26", "43-17=", "c");
//        assertNumber("261", "87*3=", "c");
//        assertNumber("3,333333333333333", "10/3=", "c");
//        //Two operands and change operation
//        assertNumber("728", "54+674-", "c");
//        assertNumber("-128", "96n-32*", "c");
////        assertNumber("-3 234", "98*33n/", "c");
//        assertNumber("4,333333333333333", "13/3-", "c");
//        //region combinations two operands with equal
//        assertNumber("39,998", "19+0,999+=", "c");
//        assertNumber("0", "239+,9-=", "c");
//        assertNumber("904 401", "77+874*=", "c");
//        assertNumber("1", "122+95/=", "c");
//        assertNumber("0", "9-873-=", "c");
//        assertNumber("-222", "876-987+=", "c");
//        assertNumber("330 613,5001", "9,99n-565*=", "c");
//        assertNumber("1", "98,7-856/=", "c");
//        assertNumber("3 999 200,04", "2*999,9*=", "c");
//        assertNumber("-243,54", "123n*,99+=", "c");
//        assertNumber("0", "65*987-=", "c");
//        assertNumber("1", "998*6/=", "c");
//        assertNumber("1", "234,1/76n/=", "c");
//        assertNumber("199,8199819981998", "666/6,666+=", "c");
//        assertNumber("0", "0/786,87-=", "c");
//        assertNumber("416 611,5702479339", "213/,33*=", "c");
//        //endregion
//        //Two operands, one operation and change operation with equal
//        assertNumber("1", "241+998-*/=", "c");
//        assertNumber("-50", "98-73,*/+=n", "c");
//        assertNumber("0", "2*561/+-=", "c");
//        assertNumber("0,1111111111111111", "1/3+-*=", "c");
////Three operands
//        //Three operand without final operation
//        assertNumber("8 784", "5612+764-8784", "c");
//        assertNumber("-98,52", "2-3*98,52n", "c");
//        assertNumber("-0,345564", "6556n*842n/,3455n64", "c");
//        assertNumber("0,6", "7767/844-86n2bbb,6", "c");
//
//        //region combinations three operands with equal
//        assertNumber("9", "2+3+4=", "c");
//        assertNumber("1", "2+3-4=", "c");
//        assertNumber("20", "2+3*4=", "c");
//        assertNumber("1,25", "2+3/4=", "c");
//        assertNumber("-5", "2-3-4=", "c");
//        assertNumber("3", "2-3+4=", "c");
//        assertNumber("-4", "2-3*4=", "c");
//        assertNumber("-0,25", "2-3/4=", "c");
//        assertNumber("24", "2*3*4=", "c");
//        assertNumber("10", "2*3+4=", "c");
//        assertNumber("2", "2*3-4=", "c");
//        assertNumber("1,5", "2*3/4=", "c");
//        assertNumber("0,1666666666666667", "2/3/4=", "c");
//        assertNumber("4,666666666666667", "2/3+4=", "c");
//        assertNumber("-3,333333333333333", "2/3-4=", "c");
//        assertNumber("2,666666666666667", "2/3*4=", "c");
////Four operands
//        //Four operands without final operation
//        assertNumber("-777,0", "67543+98n-54321*777,nn0n", "c");
//        assertNumber("999,2", "99999-0,99999*98764,0/999,213466bbbbb", "c");
//        assertNumber("0,9826", "76165*87,198/98751+0,9826", "c");
//        assertNumber("9 834 216", "316789/31245+98712-9834216", "c");
//        //region combinations Four operands with equal
//        assertNumber("328,6566", ",4566+43,2+242+43=", "c");
//        assertNumber("9 999 998,0000001", "9999999+9999999-9999999*0,9999999=", "c");
//        assertNumber("0,0617809442729949", "867,8333+3*4/56382=bbbbbbb", "c");
//        assertNumber("455 667 995,6", "2332,+3/0,5+455663325,6=nnnbn", "c");
//        assertNumber("-690,57", "86-735-8,57-33=", "c");
//        assertNumber("-357 684,1186736475", "23n1-65b43*2345/5,73=", "c");
//        assertNumber("84,77860962566845", "43-87678nb,4/748+73=", "c");
//        assertNumber("-3 929", "2-3957+84-7b58=", "c");
//        assertNumber("0", "8635*88*65,6*0,1234bbbbn=", "c");
//        assertNumber("8 814", "233*38+4-44=", "c");
//        assertNumber("-50 623 218 535 195,59", "97548354766n,b452*3874-4/7465=", "c");
//        assertNumber("88,54373407202216", "973*9/8664+87,533=", "c");
//        assertNumber("1", "986/76/835/=", "c");
//        assertNumber("-789,5563005780347", "972/865+85,32-876=", "c");
//        assertNumber("4 055,773333333333", "7784/84-,49*44=", "c");
//        assertNumber("35,8938327603227", "297/36*409/94,0064=", "c");
//        //endregion
//    }

    // r-square root;
// %-percent;
// x-one divide x;
// s-square;
    @Test
    void checkUnaryOperations() {
//        assertNumber("99 999 999,99999999", "9999999999999999 sqrt", "c");
        //one operation
        assertNumber("0", "0 sqrt", "c");
        assertNumber("0", "0 sqrx", "c");
        assertNumber("1", "1 sqrt", "c");
        assertNumber("1", "1 sqrx", "c");
        assertNumber("1", "1 1/x", "c");
        assertNumber("3", "9 sqrt", "c");
        assertNumber("25", "5 sqrx", "c");
        assertNumber("0,25", "4 1/x", "c");
        //two operation
        assertNumber("3", "3 1/x 1/x", "c");
        assertNumber("50", "50 sqrx sqrt ", "c");
//three operation
        assertNumber("0,02", "50 sqrx sqrt 1/x", "c");
        assertNumber("9,999999999999998e+31", "9999999999999999 sqrx ", "c");
        assertNumber("99 999 999,99999999", "9999999999999999 sqrt ", "c");
    }
//
//    @Test
//    void checkOperationsEnotationValid() {
//        //e-
//        //if scale number more then 16 and count of zero more 2,
//        assertNumber("1,1111111111111e-4", "0,0011111111111111/10=", "c");
//        assertNumber("-1,1111111111111e-4", "0,0011111111111111n/10=", "c");
//        assertNumber("-1,1111111111111e-4", "0,0011111111111111/10n=", "c");
//        assertNumber("9,9999999999999e-4", "0,0099999999999999/10=", "c");
//        assertNumber("1,1111111111111e-5", "0,0011111111111111/100=", "c");
//        assertNumber("1,111111111111111e-4", "0,1111111111111111/1000=", "c");
//        //number less 0,0000000000000001
//        assertNumber("1,e-17", "0,0000000000000001/10=", "c");
//        assertNumber("-1,e-17", "0,0000000000000001/10n=", "c");
//        assertNumber("1,1e-16", "0,0000000000000011/10=", "c");
//        assertNumber("9,e-17", "0,0000000000000009/10=", "c");
//        assertNumber("1,e-18", "0,0000000000000001/100=", "c");
//        assertNumber("-1,e-18", "0,0000000000000001/100n=", "c");
//        assertNumber("1,e-30", "0,000000000000001/10000000000000000000=", "c");
//        assertNumber("1,e-31", "0,0000000000000001/10000000000000000000=", "c");
//        assertNumber("1,111111111111111e-16", "0,1111111111111111/1000000000000000=", "c");
//        //e+
//        assertNumber("1,e+16", "9999999999999999+1=", "c");
//        assertNumber("1,e+16", "9999999999999999+2=", "c");
//        assertNumber("-1,e+16", "9999999999999999n-2=", "c");
//        assertNumber("2,e+16", "9999999999999999+=", "c");
//        assertNumber("3,086419753086419e+31", "5555555555555555*=", "c");
//        assertNumber("1,000000000000001e+16", "9999999999999999+6=", "c");
//        assertNumber("5,e+16", "9999999999999999+====", "c");
//        assertNumber("5,999999999999999e+16", "9999999999999999+=====", "c");
//    }
//
//    @Test
//    void checkOperationsEnotationInvalid() {
//        //e-
//        assertNumber("0,01", "0,1/10=", "c");
//        assertNumber("0,001", "0,01/10=", "c");
//        assertNumber("0,0001", "0,001/10=", "c");
//        assertNumber("0,00001", "0,0001/10=", "c");
//        assertNumber("0,000001", "0,00001/10=", "c");
//        assertNumber("0,0000001", "0,000001/10=", "c");
//        assertNumber("0,00000001", "0,0000001/10=", "c");
//        assertNumber("0,000000001", "0,00000001/10=", "c");
//        assertNumber("0,0000000001", "0,000000001/10=", "c");
//        assertNumber("0,00000000001", "0,0000000001/10=", "c");
//        assertNumber("0,000000000001", "0,00000000001/10=", "c");
//        assertNumber("0,0000000000001", "0,000000000001/10=", "c");
//        assertNumber("0,00000000000001", "0,0000000000001/10=", "c");
//        assertNumber("0,000000000000001", "0,00000000000001/10=", "c");
//        assertNumber("0,0000000000000001", "0,000000000000001/10=", "c");
//        assertNumber("0,0111111111111111", "0,1111111111111111/10=", "c");
//        assertNumber("0,1", "0,9999999999999999/10=", "c");
//        assertNumber("0,0011111111111111", "0,1111111111111111/100=", "c");
//        assertNumber("0,01", "0,9999999999999999/100=", "c");
//        assertNumber("0,0000000000000001", "0,0000000000000001/1=", "c");
//        assertNumber("0,000000000000001", "0,0000000000000001*10=", "c");
//        assertNumber("-0,0000000000000001", "0,0000000000000001n/1=", "c");
//        assertNumber("-0,000000000000001", "0,0000000000000001*10n=", "c");
//        assertNumber("0,000000000000001", "1/1000000000000000=", "c");
//        //e+
//        assertNumber("9 999 999 999 999 998", "9999999999999999-1=", "c");
//        assertNumber("-9 999 999 999 999 998", "9999999999999999n+1=", "c");
//        assertNumber("9 999 999 999 999 998", "9999999999999999+1n=", "c");
//        assertNumber("9 999 999 999 999 999", "9999999999999998+1=", "c");
//        assertNumber("9 999 999 999 999 997", "9999999999999998-1=", "c");
//        assertNumber("-9 999 999 999 999 997", "9999999999999998n+1=", "c");
//        assertNumber("-9 999 999 999 999 999", "9999999999999998n-1=", "c");
//    }

    void assertNumber(String result, String buttonsPressed, String clearButtonPressed) {
        checkKeyInputNumber(result, buttonsPressed, clearButtonPressed);
    }

    void checkKeyInputNumber(String result, String buttonsPressed, String clearButtonPressed) {
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
//        keyboardInput(String.valueOf(clearButtonPressed));
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
        for (char buttonPressed : buttonsPressed.toCharArray()) {
            keyboardNumpadInput(String.valueOf(buttonPressed));
        }

        assertEquals(result, outLabel.getText());
        type(ESCAPE);
    }


    void checkMouseInputNumber(String result, String buttonsPressed, String clearButtonPressed) {
//        for (char buttonPressed : buttonsPressed.toCharArray()) {
//            mouseInput(String.valueOf(buttonPressed));
//        }
//        String out = outLabel.getText();
//        assertEquals(result, out);
//        mouseInput(String.valueOf(clearButtonPressed));

        for (String buttonPressed : buttonsPressed.split(" ")) {
            if (isNumber(buttonPressed)) {
                for (char idButton : buttonPressed.toCharArray()) {
                    mouseInput(String.valueOf(idButton));
                }
            } else {
                mouseInput(buttonPressed);
            }
        }
        String actual = outLabel.getText();

        assertEquals(result, actual);
//        keyboardInput(String.valueOf(clearButtonPressed));
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
            type(COMMA);
        } else if (idButton.equals("negate")) {
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
        } else if (idButton.equals("sqrt")) {
            push(new KeyCodeCombination(DIGIT2, KeyCombination.SHIFT_DOWN));
        } else if (idButton.equals("%")) {
            push(new KeyCodeCombination(DIGIT5, KeyCombination.SHIFT_DOWN));
        } else if (idButton.equals("1/x")) {
            type(R);
        } else if (idButton.equals("sqrx")) {
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
        } else if (idButtonClickedMouse.equals("negate")) {
            clickOn((Button) from(root).lookup("#plusMinus").query());
        } else if (idButtonClickedMouse.equals("bs")) {
            clickOn((Button) from(root).lookup("#Backspace").query());
        } else if (idButtonClickedMouse.equals("c")) {
            clickOn((Button) from(root).lookup("#C").query());
        } else if (idButtonClickedMouse.equals("d")) {
            clickOn((Button) from(root).lookup("#CE").query());
        } else if (idButtonClickedMouse.equals("+")) {
            clickOn((Button) from(root).lookup("#add").query());
        } else if (idButtonClickedMouse.equals("-")) {
            clickOn((Button) from(root).lookup("#subtract").query());
            type();
        } else if (idButtonClickedMouse.equals("*")) {
            clickOn((Button) from(root).lookup("#multiply").query());
        } else if (idButtonClickedMouse.equals("sqrt")) {
            clickOn((Button) from(root).lookup("#sqrt").query());
        } else if (idButtonClickedMouse.equals("%")) {
            clickOn((Button) from(root).lookup("#percent").query());
        } else if (idButtonClickedMouse.equals("1/x")) {
            clickOn((Button) from(root).lookup("#oneDivideX").query());
        } else if (idButtonClickedMouse.equals("sqrx")) {
            clickOn((Button) from(root).lookup("#sqrX").query());
        } else if (idButtonClickedMouse.equals("/")) {
            clickOn((Button) from(root).lookup("#divide").query());
        } else if (idButtonClickedMouse.equals("=")) {
            clickOn((Button) from(root).lookup("#equal").query());
        }
    }
}