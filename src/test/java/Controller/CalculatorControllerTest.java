package Controller;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.loadui.testfx.utils.FXTestUtils;
import org.testfx.framework.junit5.ApplicationTest;

import java.awt.*;
import java.awt.event.InputEvent;

import static javafx.scene.input.KeyCode.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class CalculatorControllerTest extends ApplicationTest {
    private Parent root;
    private Label outLabel;
    private Label outOperationMemory;

    @Override
    public void start(Stage stage) throws Exception {
        root = FXMLLoader.load(CalculatorMain.class.getResource("/View/calculator_view.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
        stage.toFront();
    }

    private Robot robot;

    //Max positive number can input => 9999999999999999, min negative number can input => -9999999999999999
    private final String MAX_POSITIVE_NUMBER_INPUT = "9999999999999999";
    //Min positive number can input => 0,0000000000000001, Max negative number can input => -0,0000000000000001
    private final String MIN_POSITIVE_NUMBER_INPUT = "0,0000000000000001";

    // MAX_POSITIVE_NUMBER => 9,99999999999999949...99,9...9
    private final String MAX_POSITIVE_NUMBER = "1000000000000000x1000000000= x9999999999999999= /9999999999999999/10x5-1= += 0,0000000000000001x0,0000001=x0,000000000000001= +1 =+ =";
    // MIN_POSITIVE_NUMBER => 1,e-9999
    private final String MIN_POSITIVE_NUMBER = "0,0000000000000001  x 0,0000001 =  x 0,000000000000001=";

    private final String MAX_POSITIVE_NUMBER_VISIBLE = "1000000000000000  x 1000000000 =  x 9999999999999999 = ";

    {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    @BeforeEach
    void outMemory() {
        outOperationMemory = from(root).lookup("#outOperationMemory").query();
        outLabel = from(root).lookup("#generalDisplay").query();
    }

    /* Symbols by font: "/View/src/CalcMDL2.ttf
    Symbols for writing tests:
         - memory recall
         - memory clear
         - memory store
         - memory add
         - memory subtract
         - square x
         - one divide x
        √ - square root
         - negate
         - backspace
    */

    @Test
    void checkComma() {
        assertNumber(",", "0,", "");
        assertNumber(",3", "0,3", "");
        assertNumber(",3333333333333333", "0,3333333333333333", "");

        assertNumber(",,,", "0,", "");
        assertNumber(",,,3", "0,3", "");
        assertNumber(",,,3333333333333333", "0,3333333333333333", "");

        assertNumber("5,", "5,", "");
        assertNumber("5,3", "5,3", "");
        assertNumber("5,3333333333333333", "5,333333333333333", "");

        assertNumber("5,,,", "5,", "");
        assertNumber("5,,,3", "5,3", "");
        assertNumber("5,,,3333333333333333", "5,333333333333333", "");

        assertNumber("5+,", "0,", "5 + ");
        assertNumber("5+,3", "0,3", "5 + ");
        assertNumber("5+,3333333333333333", "0,3333333333333333", "5 + ");

        assertNumber("5+,,,", "0,", "5 + ");
        assertNumber("5+,,,3", "0,3", "5 + ");
        assertNumber("5+,,,3333333333333333", "0,3333333333333333", "5 + ");

    }

    @Test
    void checkInput() {
        //Text zero
        assertNumber("0000000", "0", "");
        assertNumber("001", "1", "");
        assertNumber("0000000000000012345", "12 345", "");

        //Number formatter
        assertNumber("222", "222", "");
        assertNumber("222222", "222 222", "");
        assertNumber("222222222", "222 222 222", "");
        assertNumber("2222222222222222222", "2 222 222 222 222 222", "");
        assertNumber("222222222222222222 ", "-2 222 222 222 222 222", "");
        assertNumber("222222222222222222  ", "2 222 222 222 222 222", "");
        assertNumber(MAX_POSITIVE_NUMBER_INPUT+" ", "-9 999 999 999 999 999", "");
        assertNumber(MAX_POSITIVE_NUMBER_INPUT+" ", "9 999 999 999 999 999", "");
        
        //Decimal number
        assertNumber("0,00000000", "0,00000000", "");
        assertNumber("0,00000000000000", "0,00000000000000", "");
        assertNumber("1,00000000000000", "1,00000000000000", "");
        assertNumber("0,000000000000001", "0,000000000000001", "");
        assertNumber("1,00000000000001", "1,00000000000001", "");
        assertNumber("1,000000000000001", "1,000000000000001", "");
        assertNumber("0,000000000000001234", "0,0000000000000012", "");
        assertNumber(MIN_POSITIVE_NUMBER_INPUT+"64553476", "0,0000000000000001", "");
        
        //Random
        assertNumber("1234,567891 ,234567", "1 234,567891234567", "");
        assertNumber("123 456,1234567891234", "123 456,1234567891", "");
        assertNumber("12456789,123456789", "12 456 789,12345678", "");
        assertNumber("1234567891234567,", "1 234 567 891 234 567,", "");
        assertNumber("1234567891234567,89", "1 234 567 891 234 567,", "");
        assertNumber("1234567891234567,89 ", "-1 234 567 891 234 567,", "");
        assertNumber("1234567891234567,89  ", "1 234 567 891 234 567,", "");
        assertNumber("1234567891234567,89  ", "1 234 567 891 234 567,", "");
        assertNumber("222,3333333333333  44", "-222,3333333333333", "");
        assertNumber("222,3333333333333   33,", "222,3333333333333", "");
    }

    @Test
    void checkBackspace() {
        assertNumber(" ", "0", "");
        assertNumber("5 ", "0", "");
        assertNumber("01 ", "0", "");
        assertNumber("8  ", "0", "");
        
        assertNumber("11111 ", "1 111", "");
        assertNumber("444  ", "-44", "");
        assertNumber("8888888888 ", "88 888 888", "");
        assertNumber("7838765,89  ", "0", "");
        
        //backspace point
        assertNumber("234567, ", "234 567", "");
        assertNumber("234567,8 ", "234 567", "");
        assertNumber("678395,  ", "-678 395", "");
        
        assertNumber("679 + 67 ", "0", "679 + ");
        assertNumber("3  + 987 ", "9", "1/(3) + ");
        assertNumber("3  - ,0000 ", "0,00", "sqr(3) - ");
        assertNumber("2456 x 2 - 789 ", "7", "2456 x 2 - ");
        assertNumber("86  ", "7 396", "sqr(86) ");
        
        //backspace result
        assertNumber("347 - 13 - ", "334", "347 - 13 - ");
        assertNumber("89654 √ = ", "299,422778024652", "");
        assertNumber("435576762 + 34325 = ", "435 611 087", "");
        assertNumber("9999999999999999    =   ", "9,999999999999996e+63", "");
        assertNumber("2,555555  1  x 0,0000000000000000  1", "0,0000000000000001", "sqr(2,555551) x ");
        assertNumber(",0000000000000000  1 x 2 / 0,0000000000000000  1", "0,0000000000000001", "0,0000000000000001 x 2 ÷ ");
    }

    @Test
    void checkAdd() {
        //both operands are integer
        assertNumber("0 + 0 +", "0", "0 + 0 + ");

        assertNumber("2 + 0 +", "2", "2 + 0 + ");
        assertNumber("0 + 2  +", "-2", "0 + -2 + ");
        assertNumber("2  + 0 +", "-2", "-2 + 0 + ");

        assertNumber("2 + 2 +", "4", "2 + 2 + ");
        assertNumber("2 + 2 +", "0", "2 + -2 + ");
        assertNumber("2 + 2 +", "0", "-2 + 2 + ");
        assertNumber("2 + 2 +", "-4", "-2 + -2 + ");

        assertNumber("2 + 3 +", "5", "2 + 3 + ");
        assertNumber("2  + 3 +", "1", "-2 + 3 + ");
        assertNumber("2 + 3  +", "-1", "2 + -3 + ");
        assertNumber("2  + 3  +", "-5", "-2 + -3 + ");
        //first operand is decimal
        assertNumber("0,2 + 0 +", "0,2", "0,2 + 0 + ");
        assertNumber("0,2  + 0 +", "-0,2", "-0,2 + 0 + ");

        assertNumber("0,2 + 3 +", "3,2", "0,2 + 3 + ");
        assertNumber("0,2 + 3  +", "-2,8", "0,2 + -3 + ");
        assertNumber("0,2  + 3 +", "2,8", "-0,2 + 3 + ");
        assertNumber("0,2  + 3  +", "-3,2", "-0,2 + -3 + ");
        //second operand is decimal
        assertNumber("0 + 0,2 +", "0,2", "0 + 0,2 + ");
        assertNumber("0 + 0,2  +", "-0,2", "0 + -0,2 + ");

        assertNumber("2 + 0,3 +", "2,3", "2 + 0,3 + ");
        assertNumber("2  + 0,3 +", "-1,7", "-2 + 0,3 + ");
        assertNumber("2 + 0,3  +", "1,7", "2 + -0,3 + ");
        assertNumber("2  + 0,3  +", "-2,3", "-2 + -0,3 + ");

        //both operands are decimal
        assertNumber("0,2 + 0,2 +", "0,4", "0,2 + 0,2 + ");
        assertNumber("0,2 + 0,2 +", "0", "0,2 + -0,2 + ");
        assertNumber("0,2 + 0,2 +", "0", "-0,2 + 0,2 + ");
        assertNumber("0,2 + 0,2 +", "-0,4", "-0,2 + -0,2 + ");

        assertNumber("0,2 + 0,3 +", "0,5", "0,2 + 0,3 + ");
        assertNumber("0,2  + 0,3 +", "0,1", "-0,2 + 0,3 + ");
        assertNumber("0,2 + 0,3  +", "-0,1", "0,2 + -0,3 + ");
        assertNumber("0,2  + 0,3  +", "-0,5", "-0,2 + -0,3 + ");


        /*
         * Max positive number which can input is saved in memory for accelerate tests,
         * and called by  symbol
         */
        assertNumber(MAX_POSITIVE_NUMBER_INPUT + " + 0 =", "9 999 999 999 999 999", "");
        assertNumber(" + 0 =", "-9 999 999 999 999 999", "");
        //  => 9999999999999999
        assertNumber(" + =", "2,e+16", "");
        assertNumber(" +  =", "0", "");
        assertNumber(" + =", "0", "");
        assertNumber(" +  =", "-2,e+16", "");
        //  => 9999999999999999
        assertNumber(" + 1 =", "1,e+16", "");
        assertNumber(" + 1  =", "9 999 999 999 999 998", "");
        assertNumber(" + 1 =", "-9 999 999 999 999 998", "");
        assertNumber(" + 1 =", "-1,e+16", "");
        //  => 9999999999999999
        assertNumber(" + 0,1 =", "9 999 999 999 999 999", "");
        assertNumber(" + 0,1  =", "9 999 999 999 999 999", "");
        assertNumber(" + 0,1 =", "-9 999 999 999 999 999", "");
        assertNumber(" + 0,1 = ", "-9 999 999 999 999 999", "");
        //  => 9999999999999999
        assertNumber(" + 0,0000000000000001 =", "9 999 999 999 999 999", "");
        assertNumber(" + 0,0000000000000001 =", "9 999 999 999 999 999", "");
        assertNumber(" + 0,0000000000000001 =", "-9 999 999 999 999 999", "");
        assertNumber(" + 0,0000000000000001 =", "-9 999 999 999 999 999", "");

        /*
         * Min positive number which can input is saved in memory for accelerate tests,
         * and called by  symbol
         */
        assertNumber(MIN_POSITIVE_NUMBER_INPUT + " + 0 =", "0,0000000000000001", "");
        assertNumber(" + 0 =", "-0,0000000000000001", "");
        //  => 0,0000000000000001
        assertNumber(" + =", "0,0000000000000002", "");
        assertNumber(" +  =", "0", "");
        assertNumber(" + =", "0", "");
        assertNumber(" +  =", "-0,0000000000000002", "");
        //  => 0,0000000000000001
        assertNumber(" + 1 =", "1", "");
        assertNumber(" + 1  =", "-0,9999999999999999", "");
        assertNumber(" + 1 =", "0,9999999999999999", "");
        assertNumber(" + 1 =", "-1", "");
        //  => 0,0000000000000001
        assertNumber(" + 0,1 =", "0,1000000000000001", "");
        assertNumber(" + 0,1  =", "-0,0999999999999999", "");
        assertNumber(" + 0,1 =", "0,0999999999999999", "");
        assertNumber(" + 0,1 =", "-0,1000000000000001", "");
        //  => 0,0000000000000001
        assertNumber(" + " + MAX_POSITIVE_NUMBER_INPUT + "=", "9 999 999 999 999 999", "");
        assertNumber(" + " + MAX_POSITIVE_NUMBER_INPUT + "  =", "-9 999 999 999 999 999", "");
        assertNumber(" + " + MAX_POSITIVE_NUMBER_INPUT + " =", "9 999 999 999 999 999", "");
        assertNumber(" + " + MAX_POSITIVE_NUMBER_INPUT + " =", "-9 999 999 999 999 999", "");

        /*
         * Max number visible => 9,999999999999999e+9999,
         * max number from model for controller => 9999999999999999 49...99,99...99
         * Max number is saved at memory for accelerate tests,
         * and called by  symbol
         */
        assertNumber(MAX_POSITIVE_NUMBER, "9,999999999999999e+9999", "");
        assertNumber(MAX_POSITIVE_NUMBER + "" + MAX_POSITIVE_NUMBER_VISIBLE + " +  =", "5,e+9983", "");
        //  => 9999999999999999 49...99,99...99
        assertNumber(" + 0 =", "9,999999999999999e+9999", "");
        assertNumber(" + 0 =", "-9,999999999999999e+9999", "");
        //  => 9999999999999999 49...99,99...99
        assertNumber(" + =", "Overflow", "9,999999999999999e+9999 + ");
        assertNumber(" +  =", "0", "");
        assertNumber(" + =", "0", "");
        assertNumber(" +  =", "Overflow", "negate(9,999999999999999e+9999) + negate(9,999999999999999e+9999) ");
        //  => 9999999999999999 49...99,99...99
        assertNumber(" + 1 =", "Overflow", "9,999999999999999e+9999 + ");
        assertNumber(" + 1  =", "9,999999999999999e+9999", "");
        assertNumber(" + 1 =", "-9,999999999999999e+9999", "");
        assertNumber(" + 1 =", "Overflow", "negate(9,999999999999999e+9999) + ");
        //  => 9999999999999999 49...99,99...99
        assertNumber(" + 0,1 =", "Overflow", "9,999999999999999e+9999 + ");
        assertNumber(" + 0,1  =", "9,999999999999999e+9999", "");
        assertNumber(" + 0,1 =", "-9,999999999999999e+9999", "");
        assertNumber(" + 0,1 =", "Overflow", "negate(9,999999999999999e+9999) + ");
        //  => 9999999999999999 49...99,99...99
        assertNumber(" + " + MIN_POSITIVE_NUMBER_INPUT + " =", "Overflow", "9,999999999999999e+9999 + ");
        assertNumber(" + " + MIN_POSITIVE_NUMBER_INPUT + "  =", "9,999999999999999e+9999", "");
        assertNumber(" + " + MIN_POSITIVE_NUMBER_INPUT + " =", "-9,999999999999999e+9999", "");
        assertNumber(" + " + MIN_POSITIVE_NUMBER_INPUT + "  = ", "Overflow", "negate(9,999999999999999e+9999) + ");
        //  => 9999999999999999 49...99,99...99
        assertNumber(" + " + MAX_POSITIVE_NUMBER_INPUT + " =", "Overflow", "9,999999999999999e+9999 + ");
        assertNumber(" + " + MAX_POSITIVE_NUMBER_INPUT + "  =", "9,999999999999999e+9999", "");
        assertNumber(" + " + MAX_POSITIVE_NUMBER_INPUT + " =", "-9,999999999999999e+9999", "");
        assertNumber(" + " + MAX_POSITIVE_NUMBER_INPUT + "  = ", "Overflow", "negate(9,999999999999999e+9999) + ");
        //  => 9999999999999999 49...99,99...99
        assertNumber(MIN_POSITIVE_NUMBER + "+  =", "Overflow", "1,e-9999 + ");
        assertNumber(MIN_POSITIVE_NUMBER + "+  =", "-9,999999999999999e+9999", "");
        assertNumber(MIN_POSITIVE_NUMBER + "+  =", "9,999999999999999e+9999", "");
        assertNumber(MIN_POSITIVE_NUMBER + "+  =", "Overflow", "negate(1,e-9999) + negate(9,999999999999999e+9999) ");

        /*
         * Min positive number is saved in memory for accelerate tests,
         * and called by  symbol
         */
        assertNumber(MIN_POSITIVE_NUMBER + "", "1,e-9999", "");
        //  => 0,0...01
        assertNumber(" + 0 =", "1,e-9999", "");
        assertNumber(" + 0 =", "-1,e-9999", "");
        //  => 0,0...01
        assertNumber(" + =", "2,e-9999", "");
        assertNumber(" +  =", "0", "");
        assertNumber(" + =", "0", "");
        assertNumber(" +  =", "-2,e-9999", "");
        //  => 0,0...01
        assertNumber(" + 1 =", "1", "");
        assertNumber(" + 1  =", "-1", "");
        assertNumber(" + 1 =", "1", "");
        assertNumber(" + 1 =", "-1", "");
        //  => 0,0...01
        assertNumber(" + 0,1 =", "0,1", "");
        assertNumber(" + 0,1  =", "-0,1", "");
        assertNumber(" + 0,1 =", "0,1", "");
        assertNumber(" + 0,1 =", "-0,1", "");
        //  => 0,0...01
        assertNumber(" + " + MIN_POSITIVE_NUMBER_INPUT + "=", "0,0000000000000001", "");
        assertNumber(" + " + MIN_POSITIVE_NUMBER_INPUT + " =", "-0,0000000000000001", "");
        assertNumber(" + " + MIN_POSITIVE_NUMBER_INPUT + "=", "0,0000000000000001", "");
        assertNumber(" + " + MIN_POSITIVE_NUMBER_INPUT + " = ", "-0,0000000000000001", "");
        //  => 0,0...01
        assertNumber(" + " + MAX_POSITIVE_NUMBER_INPUT + " =", "9 999 999 999 999 999", "");
        assertNumber(" + " + MAX_POSITIVE_NUMBER_INPUT + "  =", "-9 999 999 999 999 999", "");
        assertNumber(" + " + MAX_POSITIVE_NUMBER_INPUT + " =", "9 999 999 999 999 999", "");
        assertNumber(" + " + MAX_POSITIVE_NUMBER_INPUT + "  = ", "-9 999 999 999 999 999", "");

        //Random
        assertNumber("19 + 0,999 + =", "39,998", "");
        assertNumber(",734451643 + 999,99999 + 33,7478 =", "966,986641643", "");
        assertNumber("1 + 0,9999999999999999 -", "2", "1 + 0,9999999999999999 - ");
        assertNumber("645634 + 3241 + 8893  + ", "-642 305", "-645634 + 3241 + 88 + ");
        assertNumber("9999999999999999 + 25,7485 + ,63783 =", "9 999 999 999 999 974", "");
        assertNumber("45 + 975,43467 + 7465,134+", "8 485,56867", "45 + 975,43467 + 7465,134 + ");
        assertNumber("9999999999999999 + 999999999999999,9 + 99999999999999,99 +=", "2,22e+16", "");
        assertNumber("889499999,9999 + 7,9999999 + 755534,99 +", "890 255 526,989901", "889499999,9999 + -7,999999 + 755534,99 + ");
        assertNumber("0,8993654377 + 345278,996132 + 637784534,9999 +", "638 129 814,8953974", "0,8993654377 + 345278,996132 + 637784534,9999 + ");
        assertNumber("9999999999999999 + 99273555,53445 + 7687929,1213436 + 8888292995564725 + 3786661827488 + =", "2,001020348139744e+16", "");
    }


    @Test
    void checkSubtract() {
        //both operands are integer
        assertNumber("0 - 0 -", "0", "0 - 0 - ");

        assertNumber("2 - 0 -", "2", "2 - 0 - ");
        assertNumber("2  - 0 -", "-2", "-2 - 0 - ");
        assertNumber("0 - 2  -", "2", "0 - -2 - ");

        assertNumber("2 - 2 -", "0", "2 - 2 - ");
        assertNumber("2 - 2 -", "4", "2 - -2 - ");
        assertNumber("2 - 2 -", "-4", "-2 - 2 - ");
        assertNumber("2 - 2 -", "0", "-2 - -2 - ");

        assertNumber("2 - 3 -", "-1", "2 - 3 - ");
        assertNumber("2 - 3  -", "5", "2 - -3 - ");
        assertNumber("2  - 3 -", "-5", "-2 - 3 - ");
        assertNumber("2  - 3  -", "1", "-2 - -3 - ");
        //first operand is decimal
        assertNumber("0,2  - 0 -", "0,2", "0,2 - 0 - ");
        assertNumber("0,2  - 0 -", "-0,2", "-0,2 - 0 - ");

        assertNumber("0,2 - 3 -", "-2,8", "0,2 - 3 - ");
        assertNumber("0,2 - 3  -", "3,2", "0,2 - -3 - ");
        assertNumber("0,2  - 3 -", "-3,2", "-0,2 - 3 - ");
        assertNumber("0,2  - 3  -", "2,8", "-0,2 - -3 - ");
        //second operand is decimal
        assertNumber("0 - ,2 -", "-0,2", "0 - 0,2 - ");
        assertNumber("0 - ,2  -", "0,2", "0 - -0,2 - ");

        assertNumber("2 - 0,3 -", "1,7", "2 - 0,3 - ");
        assertNumber("2 - 0,3  -", "2,3", "2 - -0,3 - ");
        assertNumber("2  - 0,3 -", "-2,3", "-2 - 0,3 - ");
        assertNumber("2  - 0,3  -", "-1,7", "-2 - -0,3 - ");
        //both operands are decimal
        assertNumber("0,2 - 0,2 -", "0", "0,2 - 0,2 - ");
        assertNumber("0,2 - 0,2 -", "0,4", "0,2 - -0,2 - ");
        assertNumber("0,2 - 0,2 -", "-0,4", "-0,2 - 0,2 - ");
        assertNumber("0,2 - 0,2 -", "0", "-0,2 - -0,2 - ");

        assertNumber("0,2 - 0,3 -", "-0,1", "0,2 - 0,3 - ");
        assertNumber("0,2  - 0,3 -", "-0,5", "-0,2 - 0,3 - ");
        assertNumber("0,2 - 0,3  -", "0,5", "0,2 - -0,3 - ");
        assertNumber("0,2  - 0,3  -", "0,1", "-0,2 - -0,3 - ");

        /*
         * Max positive number which can input is saved in memory for accelerate tests,
         * and called by  symbol
         */
        assertNumber(MAX_POSITIVE_NUMBER_INPUT + " - 0 =", "9 999 999 999 999 999", "");
        assertNumber(" - 0 =", "-9 999 999 999 999 999", "");
        //  => 9999999999999999
        assertNumber(" - =", "0", "");
        assertNumber(" -  =", "2,e+16", "");
        assertNumber(" - =", "-2,e+16", "");
        assertNumber(" -  =", "0", "");
        //  => 9999999999999999
        assertNumber(" - 1 =", "9 999 999 999 999 998", "");
        assertNumber(" - 1  =", "1,e+16", "");
        assertNumber(" - 1 =", "-1,e+16", "");
        assertNumber(" - 1 =", "-9 999 999 999 999 998", "");
        //  => 9999999999999999
        assertNumber(" - 0,1 =", "9 999 999 999 999 999", "");
        assertNumber(" - 0,1  =", "9 999 999 999 999 999", "");
        assertNumber(" - 0,1 =", "-9 999 999 999 999 999", "");
        assertNumber(" - 0,1 = ", "-9 999 999 999 999 999", "");
        //  => 9999999999999999
        assertNumber(" - " + MIN_POSITIVE_NUMBER_INPUT + "=", "9 999 999 999 999 999", "");
        assertNumber(" - " + MIN_POSITIVE_NUMBER_INPUT + " =", "9 999 999 999 999 999", "");
        assertNumber(" - " + MIN_POSITIVE_NUMBER_INPUT + "=", "-9 999 999 999 999 999", "");
        assertNumber(" - " + MIN_POSITIVE_NUMBER_INPUT + " =", "-9 999 999 999 999 999", "");

        /*
         * Min positive number  which can input is saved in memory for accelerate tests,
         * and called by  symbol
         */
        assertNumber(MIN_POSITIVE_NUMBER_INPUT + " - 0 =", "0,0000000000000001", "");
        assertNumber(" - 0 =", "-0,0000000000000001", "");
        //  => 0,0000000000000001
        assertNumber(" - =", "0", "");
        assertNumber(" -  =", "0,0000000000000002", "");
        assertNumber(" - =", "-0,0000000000000002", "");
        assertNumber(" -  =", "0", "");
        //  => 0,0000000000000001
        assertNumber(" - 1 =", "-0,9999999999999999", "");
        assertNumber(" - 1  =", "1", "");
        assertNumber(" - 1 =", "-1", "");
        assertNumber(" - 1 =", "0,9999999999999999", "");
        //  => 0,0000000000000001
        assertNumber(" - 0,1 =", "-0,0999999999999999", "");
        assertNumber(" - 0,1  =", "0,1000000000000001", "");
        assertNumber(" - 0,1 =", "-0,1000000000000001", "");
        assertNumber(" - 0,1 =", "0,0999999999999999", "");
        //  => 0,0000000000000001
        assertNumber(" - " + MAX_POSITIVE_NUMBER_INPUT + " =", "-9 999 999 999 999 999", "");
        assertNumber(" - " + MAX_POSITIVE_NUMBER_INPUT + "  =", "9 999 999 999 999 999", "");
        assertNumber(" - " + MAX_POSITIVE_NUMBER_INPUT + " =", "-9 999 999 999 999 999", "");
        assertNumber(" - " + MAX_POSITIVE_NUMBER_INPUT + " =", "9 999 999 999 999 999", "");

        /*
         * Max number visible => 9,999999999999999e+9999,
         * max number from model for controller => 9999999999999999 49...99,99...99
         * Max number is saved at memory for accelerate tests,
         * and called by  symbol
         */
        assertNumber(MAX_POSITIVE_NUMBER + "" + MAX_POSITIVE_NUMBER_VISIBLE + " -  =", "-5,e+9983", "");
        //  => 9999999999999999 49...99,99...99
        assertNumber(" - 0 =", "9,999999999999999e+9999", "");
        assertNumber(" - 0 =", "-9,999999999999999e+9999", "");
        //  => 9999999999999999 49...99,99...99
        assertNumber(" - =", "0", "");
        assertNumber(" -  =", "Overflow", "9,999999999999999e+9999 - negate(9,999999999999999e+9999) ");
        assertNumber(" - =", "Overflow", "negate(9,999999999999999e+9999) - ");
        assertNumber(" -  =", "0", "");
        //  => 9999999999999999 49...99,99...99
        assertNumber(" - 1 =", "9,999999999999999e+9999", "");
        assertNumber(" - 1  =", "Overflow", "9,999999999999999e+9999 - ");
        assertNumber(" - 1 =", "Overflow", "negate(9,999999999999999e+9999) - ");
        assertNumber(" - 1 =", "-9,999999999999999e+9999", "");
        //  => 9999999999999999 49...99,99...99
        assertNumber(" - 0,1 =", "9,999999999999999e+9999", "");
        assertNumber(" - 0,1  =", "Overflow", "9,999999999999999e+9999 - ");
        assertNumber(" - 0,1 =", "Overflow", "negate(9,999999999999999e+9999) - ");
        assertNumber(" - 0,1 = ", "-9,999999999999999e+9999", "");
        //  => 9999999999999999 49...99,99...99
        assertNumber(" - " + MIN_POSITIVE_NUMBER_INPUT + " =", "9,999999999999999e+9999", "");
        assertNumber(" - " + MIN_POSITIVE_NUMBER_INPUT + "  =", "Overflow", "9,999999999999999e+9999 - ");
        assertNumber(" - " + MIN_POSITIVE_NUMBER_INPUT + " =", "Overflow", "negate(9,999999999999999e+9999) - ");
        assertNumber(" - " + MIN_POSITIVE_NUMBER_INPUT + "  = ", "-9,999999999999999e+9999", "");
        //  => 9999999999999999 49...99,99...99
        assertNumber(" - " + MAX_POSITIVE_NUMBER_INPUT + " =", "9,999999999999999e+9999", "");
        assertNumber(" - " + MAX_POSITIVE_NUMBER_INPUT + "  =", "Overflow", "9,999999999999999e+9999 - ");
        assertNumber(" - " + MAX_POSITIVE_NUMBER_INPUT + " =", "Overflow", "negate(9,999999999999999e+9999) - ");
        assertNumber(" - " + MAX_POSITIVE_NUMBER_INPUT + "  = ", "-9,999999999999999e+9999", "");
        //  => 9999999999999999 49...99,99...99
        assertNumber(MIN_POSITIVE_NUMBER + "-  =", "-9,999999999999999e+9999", "");
        assertNumber(MIN_POSITIVE_NUMBER + " -  =", "Overflow", "1,e-9999 - negate(9,999999999999999e+9999) ");
        assertNumber(MIN_POSITIVE_NUMBER + " -  =", "Overflow", "negate(1,e-9999) - ");
        assertNumber(MIN_POSITIVE_NUMBER + " -  =", "9,999999999999999e+9999", "");

        /*
         * Min positive number is saved in memory for accelerate tests,
         * and called by  symbol
         */
        assertNumber(MIN_POSITIVE_NUMBER + "", "1,e-9999", "");
        //  => 0,0...01
        assertNumber(" - 0 =", "1,e-9999", "");
        assertNumber(" - 0 =", "-1,e-9999", "");
        //  => 0,0...01
        assertNumber(" - =", "0", "");
        assertNumber(" -  =", "2,e-9999", "");
        assertNumber(" - =", "-2,e-9999", "");
        assertNumber(" -  =", "0", "");
        //  => 0,0...01
        assertNumber(" - 1 =", "-1", "");
        assertNumber(" - 1  =", "1", "");
        assertNumber(" - 1 =", "-1", "");
        assertNumber(" - 1 =", "1", "");
        //  => 0,0...01
        assertNumber(" - 0,1 =", "-0,1", "");
        assertNumber(" - 0,1  =", "0,1", "");
        assertNumber(" - 0,1 =", "-0,1", "");
        assertNumber(" - 0,1 =", "0,1", "");
        //  => 0,0...01
        assertNumber(" - " + MIN_POSITIVE_NUMBER_INPUT + " =", "-0,0000000000000001", "");
        assertNumber(" - " + MIN_POSITIVE_NUMBER_INPUT + "  =", "0,0000000000000001", "");
        assertNumber(" - " + MIN_POSITIVE_NUMBER_INPUT + " =", "-0,0000000000000001", "");
        assertNumber(" - " + MIN_POSITIVE_NUMBER_INPUT + "  = ", "0,0000000000000001", "");
        //  => 0,0...01
        assertNumber(" - " + MAX_POSITIVE_NUMBER_INPUT + " =", "-9 999 999 999 999 999", "");
        assertNumber(" - " + MAX_POSITIVE_NUMBER_INPUT + "  =", "9 999 999 999 999 999", "");
        assertNumber(" - " + MAX_POSITIVE_NUMBER_INPUT + " =", "-9 999 999 999 999 999", "");
        assertNumber(" - " + MAX_POSITIVE_NUMBER_INPUT + "  = ", "9 999 999 999 999 999", "");

        //Random
        assertNumber("687 - 456 - ,98524 -", "230,01476", "687 - 456 - 0,98524 - ");
        assertNumber("645634 - 3241 - 8893  - ", "-648 963", "-645634 - 3241 - 88 - ");
        assertNumber("45 - 975,43467 - 7465,134-", "-8 395,56867", "45 - 975,43467 - 7465,134 - ");
        assertNumber("24 - 0,9999999999999999 - 0,1 -", "22,9", "24 - 0,9999999999999999 - 0,1 - ");
        assertNumber("1 - 0,9999999999999999 -", "0,0000000000000001", "1 - 0,9999999999999999 - ");
        assertNumber("45623 - 67,23445 - 9,9756536  -", "45 565,7412036", "45623 - 67,23445 - -9,9756536 - ");
        assertNumber("6453,7546 - 998993,2133 - 09746 -", "-1 002 285,4587", "6453,7546 - 998993,2133 - 9746 - ");
        assertNumber("889499999,9999 - 7,9999999 - 755534,99 -", "888 744 473,009899", "889499999,9999 - -7,999999 - 755534,99 - ");
        assertNumber("0,8993654377 - 345278,996132 - 637784534,9999 -", "-638 129 813,0966666", "0,8993654377 - 345278,996132 - 637784534,9999 - ");

    }

    @Test
    void checkMultiply() {
        //both operands are integer
        assertNumber("0 x 0 x", "0", "0 x 0 x ");

        assertNumber("0 x 2 x", "0", "0 x 2 x ");
        assertNumber("2 x 0 x", "0", "2 x 0 x ");
        assertNumber("0 x 2  x", "0", "0 x -2 x ");
        assertNumber("2  x 0 x", "0", "-2 x 0 x ");

        assertNumber("2 x 2 x", "4", "2 x 2 x ");
        assertNumber("2 x 2  x", "-4", "2 x -2 x ");
        assertNumber("2  x 2 x", "-4", "-2 x 2 x ");
        assertNumber("2  x 2 x", "4", "-2 x -2 x ");

        assertNumber("2 x 3 x", "6", "2 x 3 x ");
        assertNumber("2 x 3  x", "-6", "2 x -3 x ");
        assertNumber("2  x 3 x", "-6", "-2 x 3 x ");
        assertNumber("2  x 3  x", "6", "-2 x -3 x ");
        //first operand is decimal
        assertNumber(",2 x 0 x", "0", "0,2 x 0 x ");
        assertNumber(",2  x 0 x", "0", "-0,2 x 0 x ");

        assertNumber("0,2 x 3 x", "0,6", "0,2 x 3 x ");
        assertNumber("0,2 x 3  x", "-0,6", "0,2 x -3 x ");
        assertNumber("0,2  x 3 x", "-0,6", "-0,2 x 3 x ");
        assertNumber("0,2  x 3  x", "0,6", "-0,2 x -3 x ");
        //second operand is decimal
        assertNumber("0 x ,2 x", "0", "0 x 0,2 x ");
        assertNumber("0 x ,2  x", "0", "0 x -0,2 x ");

        assertNumber("2 x 0,3 x", "0,6", "2 x 0,3 x ");
        assertNumber("2 x 0,3  x", "-0,6", "2 x -0,3 x ");
        assertNumber("2  x 0,3 x", "-0,6", "-2 x 0,3 x ");
        assertNumber("2  x 0,3  x", "0,6", "-2 x -0,3 x ");
        //both operands are decimal
        assertNumber("0,2 x 0,2 x", "0,04", "0,2 x 0,2 x ");
        assertNumber("0,2 x 0,2  x", "-0,04", "0,2 x -0,2 x ");
        assertNumber("0,2  x 0,2 x", "-0,04", "-0,2 x 0,2 x ");
        assertNumber("0,2  x 0,2  x", "0,04", "-0,2 x -0,2 x ");

        assertNumber("0,2 x 0,3 x", "0,06", "0,2 x 0,3 x ");
        assertNumber("0,2 x 0,3  x", "-0,06", "0,2 x -0,3 x ");
        assertNumber("0,2  x 0,3 x", "-0,06", "-0,2 x 0,3 x ");
        assertNumber("0,2  x 0,3  x", "0,06", "-0,2 x -0,3 x ");

        /*
         * Max positive number which can input is saved in memory for accelerate tests,
         * and called by  symbol
         */
        assertNumber(MAX_POSITIVE_NUMBER_INPUT + " x 0 =", "0", "");
        assertNumber(" x 0 =", "0", "");
        //  => 9999999999999999
        assertNumber(" x =", "9,999999999999998e+31", "");
        assertNumber(" x  =", "-9,999999999999998e+31", "");
        assertNumber(" x =", "-9,999999999999998e+31", "");
        assertNumber(" x  =", "9,999999999999998e+31", "");
        //  => 9999999999999999
        assertNumber(" x 1 =", "9 999 999 999 999 999", "");
        assertNumber(" x 1  =", "-9 999 999 999 999 999", "");
        assertNumber(" x 1 =", "-9 999 999 999 999 999", "");
        assertNumber(" x 1 =", "9 999 999 999 999 999", "");
        //  => 9999999999999999
        assertNumber(" x 0,1 =", "999 999 999 999 999,9", "");
        assertNumber(" x 0,1  =", "-999 999 999 999 999,9", "");
        assertNumber(" x 0,1 =", "-999 999 999 999 999,9", "");
        assertNumber(" x 0,1 = ", "999 999 999 999 999,9", "");
        //  => 9999999999999999
        assertNumber(" x " + MIN_POSITIVE_NUMBER_INPUT + " =", "0,9999999999999999", "");
        assertNumber(" x " + MIN_POSITIVE_NUMBER_INPUT + " =", "-0,9999999999999999", "");
        assertNumber(" x " + MIN_POSITIVE_NUMBER_INPUT + " =", "-0,9999999999999999", "");
        assertNumber(" x " + MIN_POSITIVE_NUMBER_INPUT + " =", "0,9999999999999999", "");


        /*
         * Min positive number  which can input is saved in memory for accelerate tests,
         * and called by  symbol
         */
        assertNumber(MIN_POSITIVE_NUMBER_INPUT + " x 0 =", "0", "");
        assertNumber(" x 0 =", "0", "");
        //  => 0,0000000000000001
        assertNumber(" x =", "1,e-32", "");
        assertNumber(" x  =", "-1,e-32", "");
        assertNumber(" x =", "-1,e-32", "");
        assertNumber(" x  =", "1,e-32", "");
        //  => 0,0000000000000001
        assertNumber(" x 1 =", "0,0000000000000001", "");
        assertNumber(" x 1  =", "-0,0000000000000001", "");
        assertNumber(" x 1 =", "-0,0000000000000001", "");
        assertNumber(" x 1 =", "0,0000000000000001", "");
        //  => 0,0000000000000001
        assertNumber(" x 0,1 =", "1,e-17", "");
        assertNumber(" x 0,1  =", "-1,e-17", "");
        assertNumber(" x 0,1 =", "-1,e-17", "");
        assertNumber(" x 0,1 =", "1,e-17", "");
        //  => 0,0000000000000001
        assertNumber(" x " + MAX_POSITIVE_NUMBER_INPUT + " =", "0,9999999999999999", "");
        assertNumber(" x " + MAX_POSITIVE_NUMBER_INPUT + "  =", "-0,9999999999999999", "");
        assertNumber(" x " + MAX_POSITIVE_NUMBER_INPUT + " =", "-0,9999999999999999", "");
        assertNumber(" x " + MAX_POSITIVE_NUMBER_INPUT + " =", "0,9999999999999999", "");

        /*
         * Max number visible => 9,999999999999999e+9999,
         * max number from model for controller => 9999999999999999 49...99,99...99
         * Max number is saved at memory for accelerate tests,
         * and called by  symbol
         */
        assertNumber(MAX_POSITIVE_NUMBER + "" + MAX_POSITIVE_NUMBER_VISIBLE + " x  =", "Overflow", "9,999999999999999e+9999 x ");
        //  => 9999999999999999 49...99,99...99
        assertNumber(" x 0 =", "0", "");
        assertNumber(" x 0 =", "0", "");
        //  => 9999999999999999 49...99,99...99
        assertNumber(" x =", "Overflow", "9,999999999999999e+9999 x ");
        assertNumber(" x  =", "Overflow", "9,999999999999999e+9999 x negate(9,999999999999999e+9999) ");
        assertNumber(" x =", "Overflow", "negate(9,999999999999999e+9999) x ");
        assertNumber(" x  =", "Overflow", "negate(9,999999999999999e+9999) x negate(9,999999999999999e+9999) ");
        //  => 9999999999999999 49...99,99...99
        assertNumber(" x 1 =", "9,999999999999999e+9999", "");
        assertNumber(" x 1  =", "-9,999999999999999e+9999", "");
        assertNumber(" x 1 =", "-9,999999999999999e+9999", "");
        assertNumber(" x 1 =", "9,999999999999999e+9999", "");
        //  => 9999999999999999 49...99,99...99
        assertNumber(" x 0,1 =", "9,999999999999999e+9998", "");
        assertNumber(" x 0,1  =", "-9,999999999999999e+9998", "");
        assertNumber(" x 0,1 =", "-9,999999999999999e+9998", "");
        assertNumber(" x 0,1 = ", "9,999999999999999e+9998", "");
        //  => 9999999999999999 49...99,99...99
        assertNumber(" x " + MIN_POSITIVE_NUMBER_INPUT + " =", "9,999999999999999e+9983", "");
        assertNumber(" x " + MIN_POSITIVE_NUMBER_INPUT + "  =", "-9,999999999999999e+9983", "");
        assertNumber(" x " + MIN_POSITIVE_NUMBER_INPUT + " =", "-9,999999999999999e+9983", "");
        assertNumber(" x " + MIN_POSITIVE_NUMBER_INPUT + "  = ", "9,999999999999999e+9983", "");
        //  => 9999999999999999 49...99,99...99
        assertNumber(" x " + MAX_POSITIVE_NUMBER_INPUT + " =", "Overflow", "9,999999999999999e+9999 x ");
        assertNumber(" x " + MAX_POSITIVE_NUMBER_INPUT + "  =", "Overflow", "9,999999999999999e+9999 x ");
        assertNumber(" x " + MAX_POSITIVE_NUMBER_INPUT + " =", "Overflow", "negate(9,999999999999999e+9999) x ");
        assertNumber(" x " + MAX_POSITIVE_NUMBER_INPUT + "  = ", "Overflow", "negate(9,999999999999999e+9999) x ");
        //  => 9999999999999999 49...99,99...99
        assertNumber(MIN_POSITIVE_NUMBER + "x  =", "9,999999999999999", "");
        assertNumber(MIN_POSITIVE_NUMBER + " x  =", "-9,999999999999999", "");
        assertNumber(MIN_POSITIVE_NUMBER + " x  =", "-9,999999999999999", "");
        assertNumber(MIN_POSITIVE_NUMBER + " x  =", "9,999999999999999", "");


        /*
         * Min positive number is saved in memory for accelerate tests,
         * and called by  symbol
         */
        assertNumber(MIN_POSITIVE_NUMBER + "", "1,e-9999", "");
        //  => 0,0...01
        assertNumber(" x 0 =", "0", "");
        assertNumber(" x 0 =", "0", "");
        //  => 0,0...01
        assertNumber(" x =", "Overflow", "1,e-9999 x ");
        assertNumber(" x  =", "Overflow", "1,e-9999 x negate(1,e-9999) ");
        assertNumber(" x =", "Overflow", "negate(1,e-9999) x ");
        assertNumber(" x  =", "Overflow", "negate(1,e-9999) x negate(1,e-9999) ");
        //  => 0,0...01
        assertNumber(" x 1 =", "1,e-9999", "");
        assertNumber(" x 1  =", "-1,e-9999", "");
        assertNumber(" x 1 =", "-1,e-9999", "");
        assertNumber(" x 1 =", "1,e-9999", "");
        //  => 0,0...01
        assertNumber(" x 0,1 =", "Overflow", "1,e-9999 x ");
        assertNumber(" x 0,1  =", "Overflow", "1,e-9999 x ");
        assertNumber(" x 0,1 =", "Overflow", "negate(1,e-9999) x ");
        assertNumber(" x 0,1 =", "Overflow", "negate(1,e-9999) x ");
        //  => 0,0...01
        assertNumber(" x " + MIN_POSITIVE_NUMBER_INPUT + " =", "Overflow", "1,e-9999 x ");
        assertNumber(" x " + MIN_POSITIVE_NUMBER_INPUT + "  =", "Overflow", "1,e-9999 x ");
        assertNumber(" x " + MIN_POSITIVE_NUMBER_INPUT + " =", "Overflow", "negate(1,e-9999) x ");
        assertNumber(" x " + MIN_POSITIVE_NUMBER_INPUT + "  = ", "Overflow", "negate(1,e-9999) x ");
        //  => 0,0...01
        assertNumber(" x " + MAX_POSITIVE_NUMBER_INPUT + " =", "9,999999999999999e-9984", "");
        assertNumber(" x " + MAX_POSITIVE_NUMBER_INPUT + "  =", "-9,999999999999999e-9984", "");
        assertNumber(" x " + MAX_POSITIVE_NUMBER_INPUT + " =", "-9,999999999999999e-9984", "");
        assertNumber(" x " + MAX_POSITIVE_NUMBER_INPUT + "  = ", "9,999999999999999e-9984", "");

        //Random
        assertNumber("687 x 456 x ,98524 x", "308 648,10528", "687 x 456 x 0,98524 x ");
        assertNumber("24 x 0,9999999999999999 x 0,1 x", "2,4", "24 x 0,9999999999999999 x 0,1 x ");
        assertNumber("1 x 0,9999999999999999 x", "0,9999999999999999", "1 x 0,9999999999999999 x ");
        assertNumber("645634 x 3241 x 8893  x ", "-184 139 981 872", "-645634 x 3241 x 88 x ");
        assertNumber("45 x 975,43467 x 7465,134x", "327 678 773,3908101", "45 x 975,43467 x 7465,134 x ");
        assertNumber("45623 x 67,23445 x 9,9756536  x", "-30 599 692,0677186", "45623 x 67,23445 x -9,9756536 x ");
        assertNumber("6453,7546 x 998993,2133 x 09746 x", "62 834 967 167 427,83", "6453,7546 x 998993,2133 x 9746 x ");
        assertNumber("889499999,9999 x 7,9999999 x 755534,99 x", "-5 376 386 316 791 022", "889499999,9999 x -7,999999 x 755534,99 x ");
        assertNumber("0,8993654377 x 345278,996132 x 637784534,9999 x", "198 052 504 342 910,6", "0,8993654377 x 345278,996132 x 637784534,9999 x ");
    }

    @Test
    void checkDivide() {
        //both operands are integer
        assertNumber("0 / 0 /", "Result is undefined", "0 ÷ 0 ÷ ");

        assertNumber("0 / 2 /", "0", "0 ÷ 2 ÷ ");
        assertNumber("2 / 0 /", "Cannot divide by zero", "2 ÷ 0 ÷ ");
        assertNumber("0 / 2  /", "0", "0 ÷ -2 ÷ ");
        assertNumber("2  / 0 /", "Cannot divide by zero", "-2 ÷ 0 ÷ ");

        assertNumber("2 / 3 /", "0,6666666666666667", "2 ÷ 3 ÷ ");
        assertNumber("2 / 3  /", "-0,6666666666666667", "2 ÷ -3 ÷ ");
        assertNumber("2  / 3 /", "-0,6666666666666667", "-2 ÷ 3 ÷ ");
        assertNumber("2  / 3  /", "0,6666666666666667", "-2 ÷ -3 ÷ ");

        //first operand is decimal
        assertNumber("0,2 / 0 /", "Cannot divide by zero", "0,2 ÷ 0 ÷ ");
        assertNumber("0,2 / 0  /", "Cannot divide by zero", "0,2 ÷ 0 ÷ ");
        assertNumber("0,2  / 0 /", "Cannot divide by zero", "-0,2 ÷ 0 ÷ ");
        assertNumber("0,2  / 0  /", "Cannot divide by zero", "-0,2 ÷ 0 ÷ ");

        assertNumber("0,2 / 3 /", "0,0666666666666667", "0,2 ÷ 3 ÷ ");
        assertNumber("0,2 / 3  /", "-0,0666666666666667", "0,2 ÷ -3 ÷ ");
        assertNumber("0,2  / 3 /", "-0,0666666666666667", "-0,2 ÷ 3 ÷ ");
        assertNumber("0,2  / 3  /", "0,0666666666666667", "-0,2 ÷ -3 ÷ ");

        //second operand is decimal
        assertNumber("0 / ,2 /", "0", "0 ÷ 0,2 ÷ ");
        assertNumber("0 / ,2  /", "0", "0 ÷ -0,2 ÷ ");

        assertNumber("2 / 0,3 /", "6,666666666666667", "2 ÷ 0,3 ÷ ");
        assertNumber("2 / 0,3  /", "-6,666666666666667", "2 ÷ -0,3 ÷ ");
        assertNumber("2  / 0,3 /", "-6,666666666666667", "-2 ÷ 0,3 ÷ ");
        assertNumber("2  / 0,3  /", "6,666666666666667", "-2 ÷ -0,3 ÷ ");

        //both operands are decimal
        assertNumber("0,2 / 0,2 /", "1", "0,2 ÷ 0,2 ÷ ");
        assertNumber("0,2 / 0,2  /", "-1", "0,2 ÷ -0,2 ÷ ");
        assertNumber("0,2  / 0,2 /", "-1", "-0,2 ÷ 0,2 ÷ ");
        assertNumber("0,2  / 0,2  /", "1", "-0,2 ÷ -0,2 ÷ ");

        assertNumber("0,2 / 0,3 /", "0,6666666666666667", "0,2 ÷ 0,3 ÷ ");
        assertNumber("0,2 / 0,3  /", "-0,6666666666666667", "0,2 ÷ -0,3 ÷ ");
        assertNumber("0,2  / 0,3 /", "-0,6666666666666667", "-0,2 ÷ 0,3 ÷ ");
        assertNumber("0,2  / 0,3  /", "0,6666666666666667", "-0,2 ÷ -0,3 ÷ ");

        /*
         * Max positive number which can input is saved in memory for accelerate tests,
         * and called by  symbol
         */
        assertNumber(MAX_POSITIVE_NUMBER_INPUT + " / 0 =", "Cannot divide by zero", "9999999999999999 ÷ ");
        assertNumber(" / 0 =", "Cannot divide by zero", "negate(9999999999999999) ÷ ");
        //  => 9999999999999999
        assertNumber(" / =", "1", "");
        assertNumber(" /  =", "-1", "");
        assertNumber(" / =", "-1", "");
        assertNumber(" /  =", "1", "");
        //  => 9999999999999999
        assertNumber(" / 1 =", "9 999 999 999 999 999", "");
        assertNumber(" / 1  =", "-9 999 999 999 999 999", "");
        assertNumber(" / 1 =", "-9 999 999 999 999 999", "");
        assertNumber(" / 1 =", "9 999 999 999 999 999", "");
        //  => 9999999999999999
        assertNumber(" / 0,1 =", "9,999999999999999e+16", "");
        assertNumber(" / 0,1  =", "-9,999999999999999e+16", "");
        assertNumber(" / 0,1 =", "-9,999999999999999e+16", "");
        assertNumber(" / 0,1 = ", "9,999999999999999e+16", "");
        //  => 9999999999999999
        assertNumber(" / " + MIN_POSITIVE_NUMBER_INPUT + " =", "9,999999999999999e+31", "");
        assertNumber(" / " + MIN_POSITIVE_NUMBER_INPUT + " =", "-9,999999999999999e+31", "");
        assertNumber(" / " + MIN_POSITIVE_NUMBER_INPUT + " =", "-9,999999999999999e+31", "");
        assertNumber(" / " + MIN_POSITIVE_NUMBER_INPUT + " =", "9,999999999999999e+31", "");


        /*
         * Min positive number  which can input is saved in memory for accelerate tests,
         * and called by  symbol
         */
        assertNumber(MIN_POSITIVE_NUMBER_INPUT + " / 0 =", "Cannot divide by zero", "0,0000000000000001 ÷ ");
        assertNumber(" / 0 =", "Cannot divide by zero", "negate(0,0000000000000001) ÷ ");
        //  => 0,0000000000000001
        assertNumber(" / =", "1", "");
        assertNumber(" /  =", "-1", "");
        assertNumber(" / =", "-1", "");
        assertNumber(" /  =", "1", "");
        //  => 0,0000000000000001
        assertNumber(" / 1 =", "0,0000000000000001", "");
        assertNumber(" / 1  =", "-0,0000000000000001", "");
        assertNumber(" / 1 =", "-0,0000000000000001", "");
        assertNumber(" / 1 =", "0,0000000000000001", "");
        //  => 0,0000000000000001
        assertNumber(" / 0,1 =", "0,000000000000001", "");
        assertNumber(" / 0,1  =", "-0,000000000000001", "");
        assertNumber(" / 0,1 =", "-0,000000000000001", "");
        assertNumber(" / 0,1 =", "0,000000000000001", "");
        //  => 0,0000000000000001
        assertNumber(" / 9999999999999999 =", "1,e-32", "");
        assertNumber(" / 9999999999999999  =", "-1,e-32", "");
        assertNumber(" / 9999999999999999 =", "-1,e-32", "");
        assertNumber(" / 9999999999999999 =", "1,e-32", "");

        /*
         * Max number visible => 9,999999999999999e+9999,
         * max number from model for controller => 9999999999999999 49...99,99...99
         * Max number is saved at memory for accelerate tests,
         * and called by  symbol
         */
        assertNumber(MAX_POSITIVE_NUMBER + "" + MAX_POSITIVE_NUMBER_VISIBLE + " /  =", "0,9999999999999999", "");
        //  => 9999999999999999 49...99,99...99
        assertNumber(" / 0 =", "Cannot divide by zero", "9,999999999999999e+9999 ÷ ");
        assertNumber(" / 0 =", "Cannot divide by zero", "negate(9,999999999999999e+9999) ÷ ");
        //  => 9999999999999999 49...99,99...99
        assertNumber(" / =", "1", "");
        assertNumber(" /  =", "-1", "");
        assertNumber(" / =", "-1", "");
        assertNumber(" /  =", "1", "");
        //  => 9999999999999999 49...99,99...99
        assertNumber(" / 1 =", "9,999999999999999e+9999", "");
        assertNumber(" / 1  =", "-9,999999999999999e+9999", "");
        assertNumber(" / 1 =", "-9,999999999999999e+9999", "");
        assertNumber(" / 1 =", "9,999999999999999e+9999", "");
        //  => 9999999999999999 49...99,99...99
        assertNumber(" / 0,1 =", "Overflow", "9,999999999999999e+9999 ÷ ");
        assertNumber(" / 0,1  =", "Overflow", "9,999999999999999e+9999 ÷ ");
        assertNumber(" / 0,1 =", "Overflow", "negate(9,999999999999999e+9999) ÷ ");
        assertNumber(" / 0,1 = ", "Overflow", "negate(9,999999999999999e+9999) ÷ ");
        //  => 9999999999999999 49...99,99...99
        assertNumber(" / " + MIN_POSITIVE_NUMBER_INPUT + " =", "Overflow", "9,999999999999999e+9999 ÷ ");
        assertNumber(" / " + MIN_POSITIVE_NUMBER_INPUT + "  =", "Overflow", "9,999999999999999e+9999 ÷ ");
        assertNumber(" / " + MIN_POSITIVE_NUMBER_INPUT + " =", "Overflow", "negate(9,999999999999999e+9999) ÷ ");
        assertNumber(" / " + MIN_POSITIVE_NUMBER_INPUT + " = ", "Overflow", "negate(9,999999999999999e+9999) ÷ ");
        //  => 9999999999999999 49...99,99...99
        assertNumber(" / " + MAX_POSITIVE_NUMBER_INPUT + " =", "1,e+9984", "");
        assertNumber(" / " + MAX_POSITIVE_NUMBER_INPUT + "  =", "-1,e+9984", "");
        assertNumber(" / " + MAX_POSITIVE_NUMBER_INPUT + " =", "-1,e+9984", "");
        assertNumber(" / " + MAX_POSITIVE_NUMBER_INPUT + "  = ", "1,e+9984", "");
        //  => 9999999999999999 49...99,99...99
        assertNumber(MIN_POSITIVE_NUMBER + "/  =", "Overflow", "1,e-9999 ÷ ");
        assertNumber(MIN_POSITIVE_NUMBER + " /  =", "Overflow", "1,e-9999 ÷ negate(9,999999999999999e+9999) ");
        assertNumber(MIN_POSITIVE_NUMBER + " /  =", "Overflow", "negate(1,e-9999) ÷ ");
        assertNumber(MIN_POSITIVE_NUMBER + " /  =", "Overflow", "negate(1,e-9999) ÷ negate(9,999999999999999e+9999) ");


        /*
         * Min positive number is saved in memory for accelerate tests,
         * and called by  symbol
         */
        assertNumber(MIN_POSITIVE_NUMBER + "", "1,e-9999", "");
        //  => 0,0...01
        assertNumber(" / 0 =", "Cannot divide by zero", "1,e-9999 ÷ ");
        assertNumber(" / 0 =", "Cannot divide by zero", "negate(1,e-9999) ÷ ");
        //  => 0,0...01
        assertNumber(" / =", "1", "");
        assertNumber(" /  =", "-1", "");
        assertNumber(" / =", "-1", "");
        assertNumber(" /  =", "1", "");
        //  => 0,0...01
        assertNumber(" / 1 =", "1,e-9999", "");
        assertNumber(" / 1  =", "-1,e-9999", "");
        assertNumber(" / 1 =", "-1,e-9999", "");
        assertNumber(" / 1 =", "1,e-9999", "");
        //  => 0,0...01
        assertNumber(" / 0,1 =", "1,e-9998", "");
        assertNumber(" / 0,1  =", "-1,e-9998", "");
        assertNumber(" / 0,1 =", "-1,e-9998", "");
        assertNumber(" / 0,1 =", "1,e-9998", "");
        //  => 0,0...01
        assertNumber(" / " + MIN_POSITIVE_NUMBER_INPUT + " =", "1,e-9983", "");
        assertNumber(" / " + MIN_POSITIVE_NUMBER_INPUT + "  =", "-1,e-9983", "");
        assertNumber(" / " + MIN_POSITIVE_NUMBER_INPUT + " =", "-1,e-9983", "");
        assertNumber(" / " + MIN_POSITIVE_NUMBER_INPUT + "  = ", "1,e-9983", "");
        //  => 0,0...01
        assertNumber(" / " + MAX_POSITIVE_NUMBER_INPUT + " =", "Overflow", "1,e-9999 ÷ ");
        assertNumber(" / " + MAX_POSITIVE_NUMBER_INPUT + "  =", "Overflow", "1,e-9999 ÷ ");
        assertNumber(" / " + MAX_POSITIVE_NUMBER_INPUT + " =", "Overflow", "negate(1,e-9999) ÷ ");
        assertNumber(" / " + MAX_POSITIVE_NUMBER_INPUT + "  = ", "Overflow", "negate(1,e-9999) ÷ ");

        //Random
        assertNumber("10 / 2,9999999 /", "3,333333444444448", "10 ÷ 2,9999999 ÷ ");
        assertNumber("1 / 0,9999999999999999 /", "1", "1 ÷ 0,9999999999999999 ÷ ");
        assertNumber("687 / 456 / ,98524 /", "1,529149189404024", "687 ÷ 456 ÷ 0,98524 ÷ ");
        assertNumber("24 / 0,9999999999999999 / 0,1 /", "240", "24 ÷ 0,9999999999999999 ÷ 0,1 ÷ ");
        assertNumber("645634 / 3241 / 8893  / ", "-2,263730330145017", "-645634 ÷ 3241 ÷ 88 ÷ ");
        assertNumber("0,8993654377 / 345278,996132 / 637784534,9999 /", "4,084059392295516e-15", "0,8993654377 ÷ 345278,996132 ÷ 637784534,9999 ÷ ");
        assertNumber("0,9999999999999999 / 0,77644 / 0,776767767666  /", "-1,658062522813398", "0,9999999999999999 ÷ 0,77644 ÷ -0,776767767666 ÷ ");

        //Exception
        assertNumber("67 -= / 0 =", "Result is undefined", "0 ÷ ");
        assertNumber("0 / 0 =", "Result is undefined", "sqr(0) ÷ ");
        assertNumber("0 √ / 0 =", "Result is undefined", "√(0) ÷ ");
        assertNumber("654 + 89 + 14 - = / 0 =", "Result is undefined", "0 ÷ ");

        assertNumber(MAX_POSITIVE_NUMBER_INPUT + "/ 0 =", "Cannot divide by zero", "9999999999999999 ÷ ");
        assertNumber(MAX_POSITIVE_NUMBER_INPUT + "/ 0 =", "Cannot divide by zero", "-9999999999999999 ÷ ");

        assertNumber(MIN_POSITIVE_NUMBER_INPUT + "/ 0 =", "Cannot divide by zero", "0,0000000000000001 ÷ ");
        assertNumber(MIN_POSITIVE_NUMBER_INPUT + "/ 0 =", "Cannot divide by zero", "-0,0000000000000001 ÷ ");

        assertNumber(MAX_POSITIVE_NUMBER_VISIBLE + "/ 0 =", "Cannot divide by zero", "9,999999999999999e+9999 ÷ ");
        assertNumber(MAX_POSITIVE_NUMBER_VISIBLE + "/ 0 =", "Cannot divide by zero", "negate(9,999999999999999e+9999) ÷ ");

        assertNumber(MAX_POSITIVE_NUMBER + "/ 0 =", "Cannot divide by zero", "9,999999999999999e+9999 ÷ ");
        assertNumber(MAX_POSITIVE_NUMBER + "/ 0 =", "Cannot divide by zero", "negate(9,999999999999999e+9999) ÷ ");

        assertNumber(MIN_POSITIVE_NUMBER + "/ 0 =", "Cannot divide by zero", "1,e-9999 ÷ ");
        assertNumber(MIN_POSITIVE_NUMBER + "/ 0 =", "Cannot divide by zero", "negate(1,e-9999) ÷ ");

        //Random exception
        assertNumber("1 / 0 =", "Cannot divide by zero", "1 ÷ ");
        assertNumber("1,3 / 0 =", "Cannot divide by zero", "1,3 ÷ ");
        assertNumber("98874 / 0 =", "Cannot divide by zero", "98874 ÷ ");
        assertNumber("988,74 / 0 =", "Cannot divide by zero", "988,74 ÷ ");
        assertNumber("1 / 5 / 78 / 0 =", "Cannot divide by zero", "1 ÷ 5 ÷ 78 ÷ ");
        assertNumber("2 + 4 - 1 + 3 / 0 =", "Cannot divide by zero", "2 + 4 - 1 + 3 ÷ ");
        assertNumber("0,0000000000000001 / 0 =", "Cannot divide by zero", "0,0000000000000001 ÷ ");
        assertNumber("87848  + 6356457 √ / 0 =", "Cannot divide by zero", "sqr(87848) + √(6356457) ÷ ");
        assertNumber("847 + 7865874,37 - 378 + 7687,1762 / 0 =", "Cannot divide by zero", "847 + 7865874,37 - 378 + 7687,1762 ÷ ");
    }

    @Test
    void checkEqual() {
        assertNumber("=", "0", "");

        //Operation with equal
        assertNumber("+ =", "0", "");
        assertNumber("- =", "0", "");
        assertNumber("x =", "0", "");
        assertNumber(" =", "0", "");
        assertNumber("√ =", "0", "");
        assertNumber("% =", "0", "");
        
        //Number with equal
        assertNumber("0 =", "0", "");
        assertNumber("0 =", "0", "");
        assertNumber("2 =", "2", "");
        assertNumber("2 =", "-2", "");
        assertNumber("0,2 =", "0,2", "");
        assertNumber("0,2 =", "-0,2", "");
        //First operand with operation and equal
        assertNumber("2 + =", "4", "");
        assertNumber("2 - =", "0", "");
        assertNumber("2 x =", "4", "");
        assertNumber("2 / =", "1", "");
        assertNumber("2  =", "4", "");
        assertNumber("2 √ =", "1,414213562373095", "");
        assertNumber("2  =", "0,5", "");

        //Two operands with operation and equal
        assertNumber("2 + 1 =", "3", "");
        assertNumber("2 - 1 =", "1", "");
        assertNumber("2 x 1 =", "2", "");
        assertNumber("2 / 1 =", "2", "");

        //One operands after equal
        assertNumber("2 + = +", "4", "4 + ");
        assertNumber("2 - = -", "0", "0 - ");
        assertNumber("2 x = x", "4", "4 x ");
        assertNumber("2 / = /", "1", "1 ÷ ");
        assertNumber("2  = ", "16", "sqr(4) ");
        assertNumber("2 √ = √", "1,189207115002721", "√(1,414213562373095) ");
        assertNumber("2  = ", "2", "1/(0,5) ");

        //Two operands after equal
        assertNumber("2 + = + +", "4", "4 + ");
        assertNumber("2 - = - -", "0", "0 - ");
        assertNumber("2 x = x x", "4", "4 x ");
        assertNumber("2 / = / /", "1", "1 ÷ ");
        assertNumber("2  =  ", "256", "sqr(sqr(4)) ");
        assertNumber("2 √ = √ √", "1,090507732665258", "√(√(1,414213562373095)) ");
        assertNumber("2  =  ", "0,5", "1/(1/(0,5)) ");

        ////One operands after equal and equal
        assertNumber("2 + = + =", "8", "");
        assertNumber("2 - = - =", "0", "");
        assertNumber("2 x = x =", "16", "");
        assertNumber("2 / = / =", "1", "");
        assertNumber("2  =  =", "16", "");
        assertNumber("2 √ = √ =", "1,189207115002721", "");
        assertNumber("2  =  =", "2", "");

        //One operand with operation and two equals
        assertNumber("2 + ==", "6", "");
        assertNumber("2 - ==", "-2", "");
        assertNumber("2 x ==", "8", "");
        assertNumber("2 / ==", "0,5", "");
        assertNumber("2  ==", "4", "");
        assertNumber("2 √ ==", "1,414213562373095", "");
        assertNumber("2  ==", "0,5", "");
        assertNumber("3 % ==", "0", "");

        //One operand with operation and several equals
        assertNumber("2 + ===", "8", "");
        assertNumber("2 - ===", "-4", "");
        assertNumber("2 x ===", "16", "");
        assertNumber("2 / ===", "0,25", "");
        assertNumber("2  ===", "4", "");
        assertNumber("2 √ ===", "1,414213562373095", "");
        assertNumber("2  ===", "0,5", "");
        assertNumber("3 % ===", "0", "");

        //Two operand with operation and several equals
        assertNumber("2 + 1 ===", "5", "");
        assertNumber("2 - 1 ===", "-1", "");
        assertNumber("2 x 1 ===", "2", "");
        assertNumber("2 / 1 ===", "2", "");
        assertNumber("2 + 3 % ===", "2,18", "");

        assertNumber("2 + ==== 6 - ===", "-12", "");
        assertNumber("2 - ==== 6 x ===", "1 296", "");
        assertNumber("2 x ==== 6 / ===", "0,0277777777777778", "");
        assertNumber("2 / ==== 6 + ===", "24", "");
    }

    @Test
    void checkNegate() {
        //Print "negate()" in history
        //after pressed equal
        assertNumber("0 = ", "0", "negate(0) ");
        assertNumber("1 = ", "-1", "negate(1) ");
        assertNumber("1 =  ", "1", "negate(negate(1)) ");
        //after operation and pressed equal
        assertNumber("1 += ", "-2", "negate(2) ");
        assertNumber("1 +=  ", "2", "negate(negate(2)) ");
        assertNumber("1 -= ", "0", "negate(0) ");
        assertNumber("1 -=  ", "0", "negate(negate(0)) ");
        assertNumber("1 x= ", "-1", "negate(1) ");
        assertNumber("1 x=  ", "1", "negate(negate(1)) ");
        assertNumber("1 /= ", "-1", "negate(1) ");
        assertNumber("1 /=  ", "1", "negate(negate(1)) ");
        assertNumber("1 = ", "-1", "negate(1) ");
        assertNumber("1 =  ", "1", "negate(negate(1)) ");
        assertNumber("1 = ", "-1", "negate(1) ");
        assertNumber("1 =  ", "1", "negate(negate(1)) ");
        assertNumber("1 √= ", "-1", "negate(1) ");
        assertNumber("1 √=  ", "1", "negate(negate(1)) ");
        //if result of previous operations was printed on general display
        assertNumber("2 + 3 - ", "-5", "2 + 3 - negate(5) ");
        assertNumber("2 + 3 -  ", "5", "2 + 3 - negate(negate(5)) ");
        assertNumber("2 + 3% - ", "-2,06", "2 + 0,06 - negate(2,06) ");
        assertNumber("2 + 3% -  ", "2,06", "2 + 0,06 - negate(negate(2,06)) ");
        assertNumber("9 √   ", "-3", "negate(√(9)) ");
        assertNumber("9 √   ", "3", "negate(negate(√(9))) ");

        //Don`t print "negate()" in history
        //If equal wasn't pressed
        assertNumber("0 ", "0", "");
        assertNumber("1 ", "-1", "");
        assertNumber("1  ", "1", "");
        //if result of previous operations wasn't print on general display and was inputted number
        assertNumber("2 + 3 - 5 +", "10", "2 + 3 - -5 + ");
        assertNumber("2 + 3 - 5  +", "0", "2 + 3 - 5 + ");
        assertNumber("2 + 3% - 5 +", "7,06", "2 + 0,06 - -5 + ");
        assertNumber("2 + 3% - 5  +", "-2,94", "2 + 0,06 - 5 + ");
        assertNumber("9 ", "81", "sqr(-9) ");
        assertNumber("9    ", "81", "sqr(9) ");
    }

    @Test
    void checkOperationHistory() {
        //One operation
        assertNumber("+", "0", "0 + ");
        assertNumber("-", "0", "0 - ");
        assertNumber("x", "0", "0 x ");
        assertNumber("/", "0", "0 ÷ ");
        assertNumber("", "0", "sqr(0) ");
        assertNumber("√", "0", "√(0) ");
        assertNumber("%", "0", "0 ");
//        //One operand and operation
        assertNumber("3 +", "3", "3 + ");
        assertNumber("3 -", "3", "3 - ");
        assertNumber("3 x", "3", "3 x ");
        assertNumber("3 /", "3", "3 ÷ ");
        assertNumber("3 ", "9", "sqr(3) ");
        assertNumber("3 ", "0,3333333333333333", "1/(3) ");
        assertNumber("3 √", "1,732050807568877", "√(3) ");
        assertNumber("3 %", "0", "0 ");
        //integer number with point
        assertNumber("2 + 3, +", "5", "2 + 3 + ");
        assertNumber("2 + 3,00000 +", "5", "2 + 3 + ");
        assertNumber("99999999999999999  = +", "9,999999999999998e+31", "9,999999999999998e+31 + ");
    }

    @Test
    void checkMIXBinaryOperations() {
        assertNumber("1 + 0,9999999999999999 - 0,9999999999999999 =", "1", "");
        assertNumber("34156,745   + 388656 x 45532  =", "1 925 066 427,985", "");
        assertNumber(",77765544 + 7785  / 4,999999 =", "-1 556,844780280956", "");
        assertNumber("9999999 + 9999999 - 9999999 x 0,9999999 =", "9 999 998,0000001", "");
        assertNumber("867,8333 + 3 x 4 / 56382 =       ", "0,0617809442729949", "");
        assertNumber("2332, + 3 / 0,5 + 455663325,6 =     ", "455 667 995,6", "negate(negate(negate(negate(455667995,6)))) ");
        assertNumber("19 - 0,9999999999999999 + 0,9999999999999999 =", "19", "");
        assertNumber("27786, , 999 - 768492,000000 x 65732   =", "48 688 021 125,732", "");
        assertNumber("887754 - 256544 / 0,99999999 =", "631 210,0063121001", "");
        assertNumber("43 - 87678   ,4 / 748 + 73 =", "84,77860962566845", "");
        assertNumber("2 - 3957 + 84 - 7  58 =", "-3 929", "");
        assertNumber("0,999999999 x 2 + 0,1 =", "2,099999998", "");
        assertNumber("0,9999999999999999 x 2 - 0,1 =", "1,9", "");
        assertNumber("0,9999999999999999 x 2 / 0,1 =", "20", "");
        assertNumber("233 x 38 + 4 - 44 =", "8 814", "");
        assertNumber("973 x 9 / 8664 + 87,533 =", "88,54373407202216", "");
        assertNumber("9 / 0,9999999999999999 - 0,9999999999999999 =", "8,000000000000001", "");
        assertNumber("1000 / 2,9999999999999999647 x 3 =", "1 000", "");
        assertNumber("972 / 865 + 85,32 - 876 =", "-789,5563005780347", "");
        assertNumber("7784 / 84 - ,49 x 44 =", "4 055,773333333333", "");
        assertNumber("297 / 36 x 409 / 94,0064 =", "35,8938327603227", "");
    }

    @Test
    void checkOneDivideX() {
        //Integer number
        assertNumber("1 ", "1", "1/(1) ");
        assertNumber("2 ", "2", "1/(1/(2)) ");
        assertNumber("2  ", "-2", "1/(1/(-2)) ");
        assertNumber("2   ", "-2", "1/(negate(1/(2))) ");
        assertNumber("2 ", "-2", "negate(1/(1/(2))) ");
        assertNumber("2     ", "-2", "negate(1/(negate(1/(-2)))) ");
        //Decimal number
        assertNumber("0,1 ", "10", "1/(0,1) ");
        assertNumber("0,2 ", "0,2", "1/(1/(0,2)) ");
        assertNumber("0,2  ", "-0,2", "1/(1/(-0,2)) ");
        assertNumber("0,2   ", "-0,2", "1/(negate(1/(0,2))) ");
        assertNumber("0,2  ", "-0,2", "negate(1/(1/(0,2))) ");
        assertNumber("0,2     ", "-0,2", "negate(1/(negate(1/(-0,2)))) ");

        assertNumber(MAX_POSITIVE_NUMBER_INPUT + " ", "0,0000000000000001", "1/(9999999999999999) ");
        assertNumber(MAX_POSITIVE_NUMBER_INPUT + "  ", "-0,0000000000000001", "1/(-9999999999999999) ");

        assertNumber(MIN_POSITIVE_NUMBER_INPUT + " ", "1,e+16", "1/(0,0000000000000001) ");
        assertNumber(MIN_POSITIVE_NUMBER_INPUT + "  ", "-1,e+16", "1/(-0,0000000000000001) ");

        assertNumber(MAX_POSITIVE_NUMBER + "", "Overflow", "1/(9,999999999999999e+9999) ");
        assertNumber(MAX_POSITIVE_NUMBER + "  ", "Overflow", "1/(negate(9,999999999999999e+9999)) ");

        assertNumber(MIN_POSITIVE_NUMBER + "", "1,e+9999", "1/(1,e-9999) ");
        assertNumber(MIN_POSITIVE_NUMBER + "  ", "-1,e+9999", "1/(negate(1,e-9999)) ");

        // Random
        assertNumber("8473  ", "-1,180219520830875e-4", "1/(-8473) ");
        assertNumber("0,9999999999999999 ", "1", "1/(0,9999999999999999) ");
        assertNumber("0,033734 ", "-29,64368293116737", "1/(-0,033734) ");
        assertNumber("0,0000000000000001 ", "1,e+16", "1/(0,0000000000000001) ");
        assertNumber("8354557,12320 ", "1,196951538248595e-7", "1/(8354557,1232) ");
        assertNumber("45,87342 ", "0,021799115915055", "1/(1/(1/(45,87342))) ");
        assertNumber("0,00056766546447 ", "1 761,600912138716", "1/(0,00056766546447) ");
        assertNumber("0,9999999999999999 ", "-1", "negate(1/(1/(1/(0,9999999999999999)))) ");
        assertNumber("425664738    ", "-2,349266713278937e-9", "1/(1/(negate(1/(425664738)))) ");
        assertNumber("9999999999999999 ", "0,0000000000000001", "1/(1/(1/(1/(1/(9999999999999999))))) ");

        //Exception
        assertNumber("0 ", "Cannot divide by zero", "1/(0) ");
        assertNumber("0 √ ", "Cannot divide by zero", "1/(√(0)) ");
        assertNumber("0  ", "Cannot divide by zero", "1/(sqr(0)) ");
        assertNumber("0 x 4656356 =  ", "Cannot divide by zero", "1/(0) ");
        assertNumber("1 - 1 - ", "Cannot divide by zero", "1 - 1 - 1/(0) ");
        assertNumber("4 + 5 - 9 +  ", "Cannot divide by zero", "4 + 5 - 9 + 1/(0) ");
        assertNumber("8789,844 + 88489,3 - =  ", "Cannot divide by zero", "1/(0) ");
        assertNumber("7637673 + 6564566 x 0 -  ", "Cannot divide by zero", "7637673 + 6564566 x 0 - 1/(0) ");
    }


    @Test
    void checkSquareRoot() {
        //Integer
        assertNumber("0 √", "0", "√(0) ");
        assertNumber("1 √", "1", "√(1) ");
        assertNumber("2 √√", "1,189207115002721", "√(√(2)) ");
        assertNumber("2 √√ ", "-1,189207115002721", "negate(√(√(2))) ");
        //Decimal
        assertNumber("0,1 √", "0,3162277660168379", "√(0,1) ");
        assertNumber("0,2 √√", "0,668740304976422", "√(√(0,2)) ");
        assertNumber("0,2 √√ ", "-0,668740304976422", "negate(√(√(0,2))) ");

        assertNumber(MAX_POSITIVE_NUMBER_INPUT + " √", "99 999 999,99999999", "√(9999999999999999) ");
        assertNumber(MAX_POSITIVE_NUMBER_INPUT + "  √", "Invalid input", "√(-9999999999999999) ");

        assertNumber(MIN_POSITIVE_NUMBER_INPUT + " √", "0,00000001", "√(0,0000000000000001) ");
        assertNumber(MIN_POSITIVE_NUMBER_INPUT + "  √", "Invalid input", "√(-0,0000000000000001) ");

        assertNumber(MAX_POSITIVE_NUMBER + "√", "1,e+5000", "√(9,999999999999999e+9999) ");
        assertNumber(MAX_POSITIVE_NUMBER + "  √", "Invalid input", "√(negate(9,999999999999999e+9999)) ");

        assertNumber(MIN_POSITIVE_NUMBER + "√", "3,162277660168379e-5000", "√(1,e-9999) ");
        assertNumber(MIN_POSITIVE_NUMBER + "  √", "Invalid input", "√(negate(1,e-9999)) ");

        //Random
        assertNumber("2 √", "1,414213562373095", "√(2) ");
        assertNumber("536 √", "23,15167380558045", "√(536) ");
        assertNumber("2 √√", "1,189207115002721", "√(√(2)) ");
        assertNumber("8473 √", "92,04890004774636", "√(8473) ");
        assertNumber("834468 √", "913,4922002951093", "√(834468) ");
        assertNumber("73542 √√", "16,4677345994808", "√(√(73542)) ");
        assertNumber("0,00132887878779 √ =", "0,0364537897589537", "");
        assertNumber("50 √ ", "-7,071067811865475", "negate(√(50)) ");
        assertNumber("0,033734 √", "0,1836681790621337", "√(0,033734) ");
        assertNumber("9895527748,9199 √√√ =", "17,75946465279331", "");
        assertNumber(",73543235 √", "0,8575735245446888", "√(0,73543235) ");
        assertNumber("837464,4893 √", "915,1308591125097", "√(837464,4893) ");
        assertNumber("45,87342 √√√", "1,613225729177976", "√(√(√(45,87342))) ");
        assertNumber("425664738 √√√", "11,98487363576367", "√(√(√(425664738))) ");
        assertNumber("0,0000000000000001 √", "0,00000001", "√(0,0000000000000001) ");
        assertNumber("999,9993945 √√√", "2,37137352617826", "√(√(√(999,9993945))) ");
        assertNumber("9836545,124 √√√", "7,483509659516713", "√(√(√(9836545,124))) ");
        assertNumber("778193,736 √ ", "-882,1528983118516", "negate(√(778193,736)) ");
        assertNumber("0,00056766546447 √", "0,0238257311424015", "√(0,00056766546447) ");
        assertNumber("98266647851 √ ", "-313 475,1152021481", "negate(√(98266647851)) ");
        assertNumber("0,124356233 √ ", "-0,3526417913407315", "negate(√(0,124356233)) ");
        assertNumber("0,000000000000002 √", "4,472135954999579e-8", "√(0,000000000000002) ");
        assertNumber("765,3 √   √ ", "5,259663116753397", "√(negate(negate(√(765,3)))) ");
        assertNumber("8354557,12320 √√√√", "2,707822636955336", "√(√(√(√(8354557,1232)))) ");
        assertNumber("227254673899,124 √√√", "26,27629342823825", "√(√(√(227254673899,124))) ");
        assertNumber("0,0045 √√√√√√√", "0,9586624632624689", "√(√(√(√(√(√(√(0,0045))))))) ");
        assertNumber("9999999999999999 √√√√√", "3,162277660168379", "√(√(√(√(√(9999999999999999))))) ");

        //Exception => square root negative number
        assertNumber("1  √", "Invalid input", "√(-1) ");
        assertNumber("2 - 3 = √", "Invalid input", "√(-1) ");
        assertNumber("2 + 3 =  √", "Invalid input", "√(negate(5)) ");
        assertNumber("2 + 6784585  = √", "Invalid input", "√(-6784583) ");
        assertNumber("74672 + 6784585 =  √", "Invalid input", "√(negate(6859257)) ");
        assertNumber("0,888885999 / 9599 x 1  =  √", "Invalid input", "√(-9,260193759766642e-5) ");

    }


    @Test
    void checkSquareX() {
        assertNumber("0 ", "0", "sqr(0) ");
        //Integer
        assertNumber("1 ", "1", "sqr(1) ");
        assertNumber("2 ", "4", "sqr(2) ");
        assertNumber("2  ", "16", "sqr(sqr(2)) ");
        assertNumber("2 ", "16", "sqr(sqr(-2)) ");
        assertNumber("2   ", "16", "sqr(negate(sqr(2))) ");
        assertNumber("2 ", "-16", "negate(sqr(sqr(2))) ");
        assertNumber("2     ", "-16", "negate(sqr(negate(sqr(-2)))) ");
        //Decimal
        assertNumber("0,1 ", "0,01", "sqr(0,1) ");
        assertNumber("0,2 ", "0,04", "sqr(0,2) ");
        assertNumber("0,2 ", "0,0016", "sqr(sqr(0,2)) ");
        assertNumber("0,2  ", "0,0016", "sqr(sqr(-0,2)) ");
        assertNumber("0,2   ", "0,0016", "sqr(negate(sqr(0,2))) ");
        assertNumber("0,2   ", "-0,0016", "negate(sqr(sqr(0,2))) ");
        assertNumber("0,2     ", "-0,0016", "negate(sqr(negate(sqr(-0,2)))) ");

        assertNumber(MAX_POSITIVE_NUMBER_INPUT + "", "9,999999999999998e+31", "sqr(9999999999999999) ");
        assertNumber(MAX_POSITIVE_NUMBER_INPUT + " ", "9,999999999999998e+31", "sqr(-9999999999999999) ");

        assertNumber(MIN_POSITIVE_NUMBER_INPUT + "", "1,e-32", "sqr(0,0000000000000001) ");
        assertNumber(MIN_POSITIVE_NUMBER_INPUT + " ", "1,e-32", "sqr(-0,0000000000000001) ");
        //max number visible - 9,999999999999999e+9999, max number from model for controller => 9,999999999999999499999999999999999e+9999
        assertNumber(MAX_POSITIVE_NUMBER + "", "Overflow", "sqr(9,999999999999999e+9999) ");
        assertNumber(MAX_POSITIVE_NUMBER + " ", "Overflow", "sqr(negate(9,999999999999999e+9999)) ");

        assertNumber(MIN_POSITIVE_NUMBER + "", "Overflow", "sqr(1,e-9999) ");
        assertNumber(MIN_POSITIVE_NUMBER + " ", "Overflow", "sqr(negate(1,e-9999)) ");
        //Random
        assertNumber("536 ", "287 296", "sqr(536) ");
        assertNumber("23,4 ", "547,56", "sqr(23,4) ");
        assertNumber("8473  ", "71 791 729", "sqr(-8473) ");
        assertNumber("0,0045 ", "0,00002025", "sqr(0,0045) ");
        assertNumber("834468 ", "696 336 843 024", "sqr(834468) ");
        assertNumber("0,00132887878779  =", "1,76591883263822e-6", "");
        assertNumber("0,033734 ", "0,001137982756", "sqr(-0,033734) ");
        assertNumber("5555 ", "952 217 706 900 625", "sqr(sqr(5555)) ");
        assertNumber(",73543235 ", "0,5408607414265225", "sqr(0,73543235) ");
        assertNumber("73542 ", "2,925106924469898e+19", "sqr(sqr(73542)) ");
        assertNumber("0,124356233 ", "0,0154644726859503", "sqr(0,124356233) ");
        assertNumber("999,9993945 ", "999 998,7890003666", "sqr(999,9993945) ");
        assertNumber("0,000000000000002 ", "4,e-30", "sqr(0,000000000000002) ");
        assertNumber("0,0000000000000001 ", "1,e-32", "sqr(0,0000000000000001) ");
        assertNumber("837464,4893 ", "701 346 770 838,5098", "sqr(-837464,4893) ");
        assertNumber("8354557,12320 ", "69 798 624 724 811,86", "sqr(8354557,1232) ");
        assertNumber("0,00056766546447 ", "3,222440795519408e-7", "sqr(0,00056766546447) ");
        assertNumber("778193,736  ", "-605 585 490 749,6377", "negate(sqr(778193,736)) ");
        assertNumber("45,87342 ", "19 610 512 980 404,22", "sqr(sqr(sqr(45,87342))) ");
        assertNumber("0,9999999999999999  ", "0,9999999999999998", "sqr(-0,9999999999999999) ");
        assertNumber("98266647851  ", "-9,656334079872443e+21", "negate(sqr(-98266647851)) ");
        assertNumber("9836545,124 ", "8,764773722965631e+55", "sqr(sqr(sqr(9836545,124))) ");
        assertNumber("425664738   ", "1,077807988668255e+69", "sqr(sqr(negate(sqr(425664738)))) ");
        assertNumber("227254673899,124 ", "7,113815415810459e+90", "sqr(sqr(sqr(-227254673899,124))) ");
        assertNumber("9999999999999999 ", "9,999999999999968e+511", "sqr(sqr(sqr(sqr(sqr(9999999999999999))))) ");
        assertNumber("0,9999999999999999  ", "-0,9999999999999992", "negate(sqr(sqr(sqr(0,9999999999999999)))) ");
    }

    @Test
    void checkOverflow() {
        /*
         * Max number from model for controller => 9999999999999999 9...99,99...99 because, number hasn`t got border scale
         * Max number for controller before printing on display => 9999999999999999 49...99,99...99
         * because number scales after 16-th symbol before print on general display, 4 scales down, 5 scales up
         */

        // checks number which invisible after printing on display 
        assertNumber(MAX_POSITIVE_NUMBER + "" + MAX_POSITIVE_NUMBER_VISIBLE + "+=", "5,e+9983", "");

        //  calls max positive number from memory => 9999999999999999 49...99,99...990
        assertNumber(MIN_POSITIVE_NUMBER + "+=", "Overflow", "1,e-9999 + ");


        // Min number from model for controller equal min number for controller before printing => 0,0...01 (1,e-9999)
        assertNumber(MIN_POSITIVE_NUMBER + "", "1,e-9999", "");

        //  calls min positive number from memory => 0,0...01 (1,e-9999)
        assertNumber("/10=", "Overflow", "1,e-9999 ÷ ");
        assertNumber("x0,1=", "Overflow", "1,e-9999 x ");
    }

    @Test
    void checkMIXUnaryOperations() {
        assertNumber("15678√", "15 678", "sqr(√(15678)) ");
        assertNumber("99999", "1,000020000300004e-10", "1/(sqr(99999)) ");
        assertNumber("854321√", "0,00108190582506", "√(1/(854321)) ");
        assertNumber("999999√", "-0,0010000005000004", "negate(1/(√(999999))) ");
        assertNumber("9874562√", "9 874 562", "√(sqr(9874562)) ");
        assertNumber("9999999", "1,00000020000003e-14", "sqr(1/(9999999)) ");
        assertNumber("96546784,32415 √ √ √ ", "9,956168238447662", "√(√(√(96546784,32415))) ");
        assertNumber("99999999,00001 √ √  ", "9 999,9999500005", "sqr(√(√(99999999,00001))) ");
        assertNumber("986472913,876543 √ √  ", "0,0056425927787405", "1/(√(√(986472913,876543))) ");
        assertNumber("999999999 √  √ ", "31 622,77658587241", "√(sqr(√(999999999))) ");
        assertNumber("8877878787,65 √  √ ", "0,0032577870844877", "√(1/(√(8877878787,65))) ");
        assertNumber("9999999999  √ √ ", "99 999,999995", "√(√(sqr(9999999999))) ");
        assertNumber("98788998999,874  √ √ ", "0,0017837042774312", "√(√(1/(98788998999,874))) ");
        assertNumber("34562212456    ", "2,036161793239094e+84", "sqr(sqr(sqr(34562212456))) ");
        assertNumber("99999999999   √  ", "9,9999999998e+21", "negate(negate(√(sqr(sqr(99999999999))))) ");
        assertNumber("787746478889,98787    ", "2,596893369539549e-48", "1/(sqr(sqr(787746478889,9878))) ");
        assertNumber("999999999999,768565  √  ", "9,99999999999537e+23", "sqr(√(sqr(999999999999,7685))) ");
        assertNumber("7876565765566    ", "2,598079199785716e-52", "sqr(1/(sqr(7876565765566))) ");
        assertNumber("9999999999999,999999 √   ", "9,999999999999998e+25", "sqr(sqr(√(9999999999999,999))) ");
        assertNumber("98545445454546    ", "1,060361063588689e-56", "sqr(sqr(1/(98545445454546))) ");
        assertNumber("99999999999999    ", "1,00000000000001e-14", "1/(1/(1/(99999999999999))) ");
        assertNumber("675543235774338   √ ", "25 991 214,58828613", "√(1/(1/(675543235774338))) ");
        assertNumber("999999999999999    ", "9,99999999999998e+29", "sqr(1/(1/(999999999999999))) ");
        assertNumber("1234567891234567  √  ", "8,100000065610006e-16", "1/(√(sqr(1234567891234567))) ");
        assertNumber("3425545656565653    ", "1,173436304521581e+31", "1/(sqr(1/(3425545656565653))) ");
        assertNumber("6654638824545454  = √", "6 654 638 824 545 454", "√(4,42842178851477e+31) ");
        assertNumber("999999,0000000001     ", "-999 998 000 001,0002", "negate(1/(1/(sqr(-999999,0000000001)))) ");


    }

    @Test
    void checkOperationsEnotationValid() {
        assertNumber("0,0011111111111111 / 10 +", "1,1111111111111e-4", "0,0011111111111111 ÷ 10 + ");
        assertNumber("0,0099999999999999 / 10 +", "9,9999999999999e-4", "0,0099999999999999 ÷ 10 + ");
        assertNumber("0,1111111111111111 / 1000 -", "1,111111111111111e-4", "0,1111111111111111 ÷ 1000 - ");
        assertNumber(MIN_POSITIVE_NUMBER_INPUT + " / 10 +", "1,e-17", "0,0000000000000001 ÷ 10 + ");
        assertNumber("0,0000000000000011 / 10 +", "1,1e-16", "0,0000000000000011 ÷ 10 + ");
        assertNumber("0,0000000000000009 / 10 +", "9,e-17", "0,0000000000000009 ÷ 10 + ");
        assertNumber("0,000000000000001 / 10000000000000000000 +", "1,e-30", "0,000000000000001 ÷ 1000000000000000 + ");
        assertNumber(MIN_POSITIVE_NUMBER_INPUT + " / 10000000000000000000 +", "1,e-31", "0,0000000000000001 ÷ 1000000000000000 + ");
        assertNumber("0,1111111111111111 / 1000000000000000 +", "1,111111111111111e-16", "0,1111111111111111 ÷ 1000000000000000 + ");

        assertNumber(MAX_POSITIVE_NUMBER_INPUT + " + 1 +", "1,e+16", "9999999999999999 + 1 + ");
        assertNumber(MAX_POSITIVE_NUMBER_INPUT + " + =", "2,e+16", "");
        assertNumber(MAX_POSITIVE_NUMBER_INPUT + " + 6 =", "1,000000000000001e+16", "");
        assertNumber(MAX_POSITIVE_NUMBER_INPUT + " + = = = =", "5,e+16", "");
        assertNumber(MAX_POSITIVE_NUMBER_INPUT + " + = = = = =", "5,999999999999999e+16", "");


        assertNumber(MIN_POSITIVE_NUMBER_INPUT + " / 9,999999999999996 +", "1,e-17", "0,0000000000000001 ÷ 9,999999999999996 + ");
        assertNumber(MIN_POSITIVE_NUMBER_INPUT + " / 99,99999999999996 +", "1,e-18", "0,0000000000000001 ÷ 99,99999999999996 + ");
        assertNumber("0,000000000000001 / 999999999999999,9 +", "1,e-30", "0,000000000000001 ÷ 999999999999999,9 + ");
        assertNumber(MIN_POSITIVE_NUMBER_INPUT + " / 999999999999999,6 +", "1,e-31", "0,0000000000000001 ÷ 999999999999999,6 + ");
        assertNumber("0,1111111111111111 / 999999999999999,6 +", "1,111111111111111e-16", "0,1111111111111111 ÷ 999999999999999,6 + ");
        assertNumber("0,0011111111111111 / 9,999999999999996 +", "1,1111111111111e-4", "0,0011111111111111 ÷ 9,999999999999996 + ");
        assertNumber("0,0011111111111111 / 99,99999999999996 +", "1,1111111111111e-5", "0,0011111111111111 ÷ 99,99999999999996 + ");
        assertNumber(MAX_POSITIVE_NUMBER_INPUT + " + 0,9999999999999996 +", "1,e+16", "9999999999999999 + 0,9999999999999996 + ");

    }


    @Test
    void checkPercent() {
        //One operand
        assertNumber("0%", "0", "0 ");
        assertNumber("2%", "0", "0 ");
        assertNumber("0,2%", "0", "0 ");

        assertNumber("0%", "0", "negate(0) ");
        assertNumber("2%", "0", "negate(0) ");
        assertNumber("0,2%", "0", "negate(0) ");

        //Two operands
        //first operand with percent
        //add
        assertNumber("0% + 2 +", "2", "0 + 2 + ");
        assertNumber("2% + 0 +", "0", "0 + 0 + ");
        assertNumber("0% + 2  +", "-2", "0 + -2 + ");
        assertNumber("2%  + 0 +", "0", "negate(0) + 0 + ");

        assertNumber("2% + 3 +", "3", "0 + 3 + ");
        assertNumber("2% + 3  +", "-3", "0 + -3 + ");
        assertNumber("2%  + 3 +", "3", "negate(0) + 3 + ");
        assertNumber("2%  + 3  +", "-3", "negate(0) + -3 + ");

        assertNumber("0,2% + 3 +", "3", "0 + 3 + ");
        assertNumber("0,2% + 3  +", "-3", "0 + -3 + ");
        assertNumber("0,2%  + 3 +", "3", "negate(0) + 3 + ");
        assertNumber("0,2%  + 3  +", "-3", "negate(0) + -3 + ");

        //subtract
        assertNumber("0% - 2 -", "-2", "0 - 2 - ");
        assertNumber("2% - 0 -", "0", "0 - 0 - ");
        assertNumber("0% - 2  -", "2", "0 - -2 - ");
        assertNumber("2%  - 0 -", "0", "negate(0) - 0 - ");

        assertNumber("2% - 3 -", "-3", "0 - 3 - ");
        assertNumber("2% - 3  -", "3", "0 - -3 - ");
        assertNumber("2%  - 3 -", "-3", "negate(0) - 3 - ");
        assertNumber("2%  - 3  -", "3", "negate(0) - -3 - ");

        assertNumber("2% - 0,3 -", "-0,3", "0 - 0,3 - ");
        assertNumber("2% - 0,3  -", "0,3", "0 - -0,3 - ");
        assertNumber("2%  - 0,3 -", "-0,3", "negate(0) - 0,3 - ");
        assertNumber("2%  - 0,3  -", "0,3", "negate(0) - -0,3 - ");

        //multiply
        assertNumber("0% x 2 x", "0", "0 x 2 x ");
        assertNumber("2% x 0 x", "0", "0 x 0 x ");
        assertNumber("0% x 2  x", "0", "0 x -2 x ");
        assertNumber("2%  x 0 x", "0", "negate(0) x 0 x ");

        assertNumber("2% x 3 x", "0", "0 x 3 x ");
        assertNumber("2% x 3  x", "0", "0 x -3 x ");
        assertNumber("2%  x 3 x", "0", "negate(0) x 3 x ");
        assertNumber("2%  x 3  x", "0", "negate(0) x -3 x ");

        assertNumber("2% x 0,3 x", "0", "0 x 0,3 x ");
        assertNumber("2% x 0,3  x", "0", "0 x -0,3 x ");
        assertNumber("2%  x 0,3 x", "0", "negate(0) x 0,3 x ");
        assertNumber("2%  x 0,3  x", "0", "negate(0) x -0,3 x ");

        //divide
        assertNumber("0% / 2 /", "0", "0 ÷ 2 ÷ ");
        assertNumber("0% / 2  /", "0", "0 ÷ -2 ÷ ");
        assertNumber("2% / 0 /", "Result is undefined", "0 ÷ 0 ÷ ");
        assertNumber("2%  / 0 /", "Result is undefined", "negate(0) ÷ 0 ÷ ");

        assertNumber("2% / 3 /", "0", "0 ÷ 3 ÷ ");
        assertNumber("2% / 3  /", "0", "0 ÷ -3 ÷ ");
        assertNumber("2%  / 3 /", "0", "negate(0) ÷ 3 ÷ ");
        assertNumber("2%  / 3  /", "0", "negate(0) ÷ -3 ÷ ");

        assertNumber("2% / 0,3 /", "0", "0 ÷ 0,3 ÷ ");
        assertNumber("2% / 0,3  /", "0", "0 ÷ -0,3 ÷ ");
        assertNumber("2%  / 0,3 /", "0", "negate(0) ÷ 0,3 ÷ ");
        assertNumber("2%  / 0,3  /", "0", "negate(0) ÷ -0,3 ÷ ");

        //Second operand with percent
        //add
        assertNumber("0 + 2% +", "0", "0 + 0 + ");
        assertNumber("2 + 0% +", "2", "2 + 0 + ");
        assertNumber("0 + 2%  +", "0", "0 + negate(0) + ");
        assertNumber("2  + 0% +", "-2", "-2 + 0 + ");

        assertNumber("2 + 3% +", "2,06", "2 + 0,06 + ");
        assertNumber("2 + 3%  +", "1,94", "2 + negate(0,06) + ");
        assertNumber("2  + 3% +", "-2,06", "-2 + -0,06 + ");
        assertNumber("2  + 3%  +", "-1,94", "-2 + negate(-0,06) + ");

        assertNumber("2 + 0,3% +", "2,006", "2 + 0,006 + ");
        assertNumber("2 + 0,3%  +", "1,994", "2 + negate(0,006) + ");
        assertNumber("2  + 0,3% +", "-2,006", "-2 + -0,006 + ");
        assertNumber("2  + 0,3%  +", "-1,994", "-2 + negate(-0,006) + ");

        //subtract
        assertNumber("0 - 2% -", "0", "0 - 0 - ");
        assertNumber("2 - 0% -", "2", "2 - 0 - ");
        assertNumber("0 - 2%  -", "0", "0 - negate(0) - ");
        assertNumber("2  - 0% -", "-2", "-2 - 0 - ");

        assertNumber("2 - 3% -", "1,94", "2 - 0,06 - ");
        assertNumber("2 - 3%  -", "2,06", "2 - negate(0,06) - ");
        assertNumber("2  - 3% -", "-1,94", "-2 - -0,06 - ");
        assertNumber("2  - 3%  -", "-2,06", "-2 - negate(-0,06) - ");

        assertNumber("2 - 0,3% -", "1,994", "2 - 0,006 - ");
        assertNumber("2 - 0,3%  -", "2,006", "2 - negate(0,006) - ");
        assertNumber("2  - 0,3% -", "-1,994", "-2 - -0,006 - ");
        assertNumber("2  - 0,3%  -", "-2,006", "-2 - negate(-0,006) - ");

        //multiply
        assertNumber("0 x 2% x", "0", "0 x 0,02 x ");
        assertNumber("2 x 0% x", "0", "2 x 0 x ");
        assertNumber("0 x 2%  x", "0", "0 x negate(0,02) x ");
        assertNumber("2  x 0% x", "0", "-2 x 0 x ");

        assertNumber("2 x 3% x", "0,06", "2 x 0,03 x ");
        assertNumber("2 x 3%  x", "-0,06", "2 x negate(0,03) x ");
        assertNumber("2  x 3% x", "-0,06", "-2 x 0,03 x ");
        assertNumber("2  x 3%  x", "0,06", "-2 x negate(0,03) x ");

        assertNumber("2 x 0,3% x", "0,006", "2 x 0,003 x ");
        assertNumber("2 x 0,3%  x", "-0,006", "2 x negate(0,003) x ");
        assertNumber("2  x 0,3% x", "-0,006", "-2 x 0,003 x ");
        assertNumber("2  x 0,3%  x", "0,006", "-2 x negate(0,003) x ");

        //divide
        assertNumber("0 / 2% /", "0", "0 ÷ 0,02 ÷ ");
        assertNumber("2 / 0% /", "Cannot divide by zero", "2 ÷ 0 ÷ ");
        assertNumber("0 / 2%  /", "0", "0 ÷ negate(0,02) ÷ ");
        assertNumber("2  / 0% /", "Cannot divide by zero", "-2 ÷ 0 ÷ ");

        assertNumber("2 / 3% /", "66,66666666666667", "2 ÷ 0,03 ÷ ");
        assertNumber("2 / 3%  /", "-66,66666666666667", "2 ÷ negate(0,03) ÷ ");
        assertNumber("2  / 3% /", "-66,66666666666667", "-2 ÷ 0,03 ÷ ");
        assertNumber("2  / 3%  /", "66,66666666666667", "-2 ÷ negate(0,03) ÷ ");

        assertNumber("2 / 0,3% /", "666,6666666666667", "2 ÷ 0,003 ÷ ");
        assertNumber("2 / 0,3%  /", "-666,6666666666667", "2 ÷ negate(0,003) ÷ ");
        assertNumber("2  / 0,3% /", "-666,6666666666667", "-2 ÷ 0,003 ÷ ");
        assertNumber("2  / 0,3%  /", "666,6666666666667", "-2 ÷ negate(0,003) ÷ ");

        //Both operands with percent
        //add
        assertNumber("0% + 2% +", "0", "0 + 0 + ");
        assertNumber("2% + 0% +", "0", "0 + 0 + ");
        assertNumber("0% + 2%  +", "0", "0 + negate(0) + ");
        assertNumber("2%  + 0% +", "0", "negate(0) + 0 + ");

        assertNumber("2% + 3% +", "0", "0 + 0 + ");
        assertNumber("2% + 3%  +", "0", "0 + negate(0) + ");
        assertNumber("2%  + 3% +", "0", "negate(0) + 0 + ");
        assertNumber("2%  + 3%  +", "0", "negate(0) + negate(0) + ");

        assertNumber("2% + 0,3% +", "0", "0 + 0 + ");
        assertNumber("2% + 0,3%  +", "0", "0 + negate(0) + ");
        assertNumber("2%  + 0,3% +", "0", "negate(0) + 0 + ");
        assertNumber("2%  + 0,3%  +", "0", "negate(0) + negate(0) + ");

        //subtract
        assertNumber("0% - 2% -", "0", "0 - 0 - ");
        assertNumber("2% - 0% -", "0", "0 - 0 - ");
        assertNumber("0% - 2%  -", "0", "0 - negate(0) - ");
        assertNumber("2%  - 0% -", "0", "negate(0) - 0 - ");

        assertNumber("2% - 3% -", "0", "0 - 0 - ");
        assertNumber("2% - 3%  -", "0", "0 - negate(0) - ");
        assertNumber("2%  - 3% -", "0", "negate(0) - 0 - ");
        assertNumber("2%  - 3%  -", "0", "negate(0) - negate(0) - ");

        assertNumber("2% - 0,3% -", "0", "0 - 0 - ");
        assertNumber("2% - 0,3%  -", "0", "0 - negate(0) - ");
        assertNumber("2%  - 0,3% -", "0", "negate(0) - 0 - ");
        assertNumber("2%  - 0,3%  -", "0", "negate(0) - negate(0) - ");

        //multiply
        assertNumber("0% x 2% x", "0", "0 x 0,02 x ");
        assertNumber("2% x 0% x", "0", "0 x 0 x ");
        assertNumber("0% x 2%  x", "0", "0 x negate(0,02) x ");
        assertNumber("2%  x 0% x", "0", "negate(0) x 0 x ");

        assertNumber("2% x 3% x", "0", "0 x 0,03 x ");
        assertNumber("2% x 3%  x", "0", "0 x negate(0,03) x ");
        assertNumber("2%  x 3% x", "0", "negate(0) x 0,03 x ");
        assertNumber("2%  x 3%  x", "0", "negate(0) x negate(0,03) x ");

        assertNumber("2% x 0,3% x", "0", "0 x 0,003 x ");
        assertNumber("2% x 0,3%  x", "0", "0 x negate(0,003) x ");
        assertNumber("2%  x 0,3% x", "0", "negate(0) x 0,003 x ");
        assertNumber("2%  x 0,3%  x", "0", "negate(0) x negate(0,003) x ");

        //divide
        assertNumber("0% / 2% /", "0", "0 ÷ 0,02 ÷ ");
        assertNumber("2% / 0% /", "Result is undefined", "0 ÷ 0 ÷ ");
        assertNumber("0% / 2%  /", "0", "0 ÷ negate(0,02) ÷ ");
        assertNumber("2%  / 0% /", "Result is undefined", "negate(0) ÷ 0 ÷ ");

        assertNumber("2% / 3% /", "0", "0 ÷ 0,03 ÷ ");
        assertNumber("2% / 3%  /", "0", "0 ÷ negate(0,03) ÷ ");
        assertNumber("2%  / 3% /", "0", "negate(0) ÷ 0,03 ÷ ");
        assertNumber("2%  / 3%  /", "0", "negate(0) ÷ negate(0,03) ÷ ");

        assertNumber("2% / 0,3% /", "0", "0 ÷ 0,003 ÷ ");
        assertNumber("2% / 0,3%  /", "0", "0 ÷ negate(0,003) ÷ ");
        assertNumber("2%  / 0,3% /", "0", "negate(0) ÷ 0,003 ÷ ");
        assertNumber("2%  / 0,3%  /", "0", "negate(0) ÷ negate(0,003) ÷ ");


        assertNumber(MAX_POSITIVE_NUMBER_INPUT + "%", "0", "0 ");
        assertNumber(MAX_POSITIVE_NUMBER_INPUT + "%", "0", "0 ");

        assertNumber(MIN_POSITIVE_NUMBER_INPUT + "%", "0", "0 ");
        assertNumber(MIN_POSITIVE_NUMBER_INPUT + "%", "0", "0 ");

        assertNumber(MAX_POSITIVE_NUMBER_VISIBLE + "%", "9,999999999999999e+9997", "9,999999999999999e+9997 ");
        assertNumber(MAX_POSITIVE_NUMBER_VISIBLE + "%", "-9,999999999999999e+9997", "-9,999999999999999e+9997 ");

        assertNumber(MAX_POSITIVE_NUMBER + "%", "Overflow", "9,999999999999999e+19997 ");
        assertNumber(MAX_POSITIVE_NUMBER + "%", "Overflow", "-9,999999999999999e+19997 ");

        assertNumber(MIN_POSITIVE_NUMBER + "%", "Overflow", "1,e-10001 ");
        assertNumber(MIN_POSITIVE_NUMBER + "%", "Overflow", "-1,e-10001 ");

        //Random
        assertNumber("8 sqr %", "0", "0 ");
        assertNumber("12  - % =", "-63,36", "");
        assertNumber("9 - % +", "8,19", "9 - 0,81 + ");
        assertNumber("99864 + 835 % =", "933 728,4", "");
        assertNumber("99836 / 83984 % =", "118,8750238140598", "");
        assertNumber("0,8798798798 x ,7467846746 %=", "0,0065708080972353", "");
        assertNumber("1224354,835764 + 0,999999 % = ", "1 236 598,371878092", "");
        assertNumber("927767657 - 7455,2342 + 73864 % =", "686 208 555 634,0563", "");
        assertNumber("100 + 88 - 11 + 34 - % +", "-234,21", "100 + 88 - 11 + 34 - 445,21 + ");
        assertNumber("0,0000000000001011 + 2 % - ", "1,03122e-13", "0,0000000000001011 + 2,022e-15 - ");
        assertNumber("984 - 356 % + 876 - % - ", "-28 638,844416", "984 - 3503,04 + 876 - 26995,804416 - ");
        assertNumber("5555555555555555 + 34566 - % +", "-3,08641975312477e+29", "5555555555555555 + 34566 - 3,086419753124826e+29 + ");
        assertNumber("9999999999999999 + 1000000000000000 %", "9,999999999999999e+28", "9999999999999999 + 9,999999999999999e+28 ");
    }

    @Test
    void checkMemorySubtract() {
        //  was pressed one time
        assertNumber("0  8  ", "0", "");

        assertNumber("5  8  ", "-5", "");
        assertNumber("5  8  ", "5", "");

        assertNumber("0,5  8  ", "-0,5", "");
        assertNumber("0,5  8  ", "0,5", "");

        assertNumber(MIN_POSITIVE_NUMBER_INPUT + " 9  ", "-0,0000000000000001", "");
        assertNumber(MIN_POSITIVE_NUMBER_INPUT + " 9  ", "0,0000000000000001", "");

        assertNumber(MAX_POSITIVE_NUMBER_INPUT + " 9  ", "-9 999 999 999 999 999", "");
        assertNumber(MAX_POSITIVE_NUMBER_INPUT + " 9  ", "9 999 999 999 999 999", "");

        assertNumber(MAX_POSITIVE_NUMBER_VISIBLE + " 9  ", "-9,999999999999999e+9999", "");
        assertNumber(MAX_POSITIVE_NUMBER_VISIBLE + " 9  ", "9,999999999999999e+9999", "");

        assertNumber(MAX_POSITIVE_NUMBER + " 9  ", "-9,999999999999999e+9999", "");
        assertNumber(MAX_POSITIVE_NUMBER + " 9  ", "9,999999999999999e+9999", "");

        assertNumber(MIN_POSITIVE_NUMBER + " 9  ", "-1,e-9999", "");
        assertNumber(MIN_POSITIVE_NUMBER + " 9  ", "1,e-9999", "");

        //  was pressed two time
        //both operands are integer
        assertNumber("0  0  ", "0", "");

        assertNumber("2  0  ", "-2", "");
        assertNumber("2   0  ", "2", "");
        assertNumber("0  2   ", "2", "");

        assertNumber("2  2  ", "-4", "");
        assertNumber("2  2  ", "0", "");
        assertNumber("2  2  ", "0", "");
        assertNumber("2  2  ", "4", "");

        assertNumber("2  3  ", "-5", "");
        assertNumber("2  3   ", "1", "");
        assertNumber("2   3  ", "-1", "");
        assertNumber("2   3   ", "5", "");
        //first operand is decimal
        assertNumber("0,2   0  ", "-0,2", "");
        assertNumber("0,2   0  ", "0,2", "");

        assertNumber("0,2  3  ", "-3,2", "");
        assertNumber("0,2  3   ", "2,8", "");
        assertNumber("0,2   3  ", "-2,8", "");
        assertNumber("0,2   3   ", "3,2", "");
        //second operand is decimal
        assertNumber("0  ,2  ", "-0,2", "");
        assertNumber("0  ,2   ", "0,2", "");

        assertNumber("2  0,3  ", "-2,3", "");
        assertNumber("2  0,3   ", "-1,7", "");
        assertNumber("2   0,3  ", "1,7", "");
        assertNumber("2   0,3   ", "2,3", "");
        //both operands are decimal
        assertNumber("0,2  0,2  ", "-0,4", "");
        assertNumber("0,2  0,2  ", "0", "");
        assertNumber("0,2  0,2  ", "0", "");
        assertNumber("0,2  0,2  ", "0,4", "");

        assertNumber("0,2  0,3  ", "-0,5", "");
        assertNumber("0,2   0,3  ", "-0,1", "");
        assertNumber("0,2  0,3   ", "0,1", "");
        assertNumber("0,2   0,3   ", "0,5", "");
//
//        //Min positive number which can input
        assertNumber(MIN_POSITIVE_NUMBER_INPUT+" "+MIN_POSITIVE_NUMBER_INPUT+"  ", "-0,0000000000000002", "");
        assertNumber(MIN_POSITIVE_NUMBER_INPUT+" "+MIN_POSITIVE_NUMBER_INPUT+"  ", "0", "");
        assertNumber(MIN_POSITIVE_NUMBER_INPUT+" "+MIN_POSITIVE_NUMBER_INPUT+"  ", "0", "");
        assertNumber(MIN_POSITIVE_NUMBER_INPUT+" "+MIN_POSITIVE_NUMBER_INPUT+"  ", "0,0000000000000002", "");

        assertNumber(MIN_POSITIVE_NUMBER_INPUT+" 1   ", "-1", "");
        assertNumber(MIN_POSITIVE_NUMBER_INPUT+" 1   ", "0,9999999999999999", "");
        assertNumber(MIN_POSITIVE_NUMBER_INPUT+" 1   ", "-0,9999999999999999", "");
        assertNumber(MIN_POSITIVE_NUMBER_INPUT+" 1   ", "1", "");

        assertNumber(MIN_POSITIVE_NUMBER_INPUT+" 0,1   ", "-0,1000000000000001", "");
        assertNumber(MIN_POSITIVE_NUMBER_INPUT+" 0,1   ", "0,0999999999999999", "");
        assertNumber(MIN_POSITIVE_NUMBER_INPUT+" 0,1   ", "-0,0999999999999999", "");
        assertNumber(MIN_POSITIVE_NUMBER_INPUT+" 0,1   ", "0,1000000000000001", "");

        assertNumber(MIN_POSITIVE_NUMBER_INPUT+" "+MAX_POSITIVE_NUMBER_INPUT+"  ", "-9 999 999 999 999 999", "");
        assertNumber(MIN_POSITIVE_NUMBER_INPUT+" "+MAX_POSITIVE_NUMBER_INPUT+"  ", "9 999 999 999 999 999", "");
        assertNumber(MIN_POSITIVE_NUMBER_INPUT+" "+MAX_POSITIVE_NUMBER_INPUT+"  ", "-9 999 999 999 999 999", "");
        assertNumber(MIN_POSITIVE_NUMBER_INPUT+" "+MAX_POSITIVE_NUMBER_INPUT+"  ", "9 999 999 999 999 999", "");

        // Max positive number which can input
        assertNumber(MAX_POSITIVE_NUMBER_INPUT+" "+MAX_POSITIVE_NUMBER_INPUT+"  ", "-2,e+16", "");
        assertNumber(MAX_POSITIVE_NUMBER_INPUT+" "+MAX_POSITIVE_NUMBER_INPUT+"  ", "0", "");
        assertNumber(MAX_POSITIVE_NUMBER_INPUT+" "+MAX_POSITIVE_NUMBER_INPUT+"  ", "0", "");
        assertNumber(MAX_POSITIVE_NUMBER_INPUT+" "+MAX_POSITIVE_NUMBER_INPUT+"  ", "2,e+16", "");

        assertNumber(MAX_POSITIVE_NUMBER_INPUT+" 1   ", "-1,e+16", "");
        assertNumber(MAX_POSITIVE_NUMBER_INPUT+" 1   ", "-9 999 999 999 999 998", "");
        assertNumber(MAX_POSITIVE_NUMBER_INPUT+" 1   ", "9 999 999 999 999 998", "");
        assertNumber(MAX_POSITIVE_NUMBER_INPUT+" 1   ", "1,e+16", "");

        assertNumber(MAX_POSITIVE_NUMBER_INPUT+" 0,1   ", "-9 999 999 999 999 999", "");
        assertNumber(MAX_POSITIVE_NUMBER_INPUT+" 0,1   ", "-9 999 999 999 999 999", "");
        assertNumber(MAX_POSITIVE_NUMBER_INPUT+" 0,1   ", "9 999 999 999 999 999", "");
        assertNumber(MAX_POSITIVE_NUMBER_INPUT+" 0,1   ", "9 999 999 999 999 999", "");
        // Min number
        assertNumber(MIN_POSITIVE_NUMBER + " " + MIN_POSITIVE_NUMBER + "  ", "-2,e-9999", "");
        assertNumber(MIN_POSITIVE_NUMBER + " " + MIN_POSITIVE_NUMBER + "  ", "0", "negate(1,e-9999) ");
        assertNumber(MIN_POSITIVE_NUMBER + " " + MIN_POSITIVE_NUMBER + "  ", "0", "");
        assertNumber(MIN_POSITIVE_NUMBER + " " + MIN_POSITIVE_NUMBER + "  ", "2,e-9999", "negate(1,e-9999) ");

        assertNumber(MIN_POSITIVE_NUMBER + " 1   ", "-1", "");
        assertNumber(MIN_POSITIVE_NUMBER + " 1   ", "1", "");
        assertNumber(MIN_POSITIVE_NUMBER + " 1   ", "-1", "");
        assertNumber(MIN_POSITIVE_NUMBER + " 1   ", "1", "");

        assertNumber(MIN_POSITIVE_NUMBER + " 0,1   ", "-0,1", "");
        assertNumber(MIN_POSITIVE_NUMBER + " 0,1   ", "0,1", "");
        assertNumber(MIN_POSITIVE_NUMBER + " 0,1   ", "-0,1", "");
        assertNumber(MIN_POSITIVE_NUMBER + " 0,1   ", "0,1", "");

        assertNumber(MIN_POSITIVE_NUMBER + " " + MIN_POSITIVE_NUMBER_INPUT + "  ", "-0,0000000000000001", "");
        assertNumber(MIN_POSITIVE_NUMBER + " " + MIN_POSITIVE_NUMBER_INPUT + "  ", "0,0000000000000001", "");
        assertNumber(MIN_POSITIVE_NUMBER + " " + MIN_POSITIVE_NUMBER_INPUT + "  ", "-0,0000000000000001", "");
        assertNumber(MIN_POSITIVE_NUMBER + " " + MIN_POSITIVE_NUMBER_INPUT + "  ", "0,0000000000000001", "");

        assertNumber(MIN_POSITIVE_NUMBER + " " + MAX_POSITIVE_NUMBER_INPUT + "  ", "-9 999 999 999 999 999", "");
        assertNumber(MIN_POSITIVE_NUMBER + " " + MAX_POSITIVE_NUMBER_INPUT + "  ", "9 999 999 999 999 999", "");
        assertNumber(MIN_POSITIVE_NUMBER + " " + MAX_POSITIVE_NUMBER_INPUT + "  ", "-9 999 999 999 999 999", "");
        assertNumber(MIN_POSITIVE_NUMBER + " " + MAX_POSITIVE_NUMBER_INPUT + "  ", "9 999 999 999 999 999", "");

        // Max number
        assertNumber(MAX_POSITIVE_NUMBER + " 1   ", "Overflow", "");
        assertNumber(MAX_POSITIVE_NUMBER + " 1   ", "-9,999999999999999e+9999", "");
        assertNumber(MAX_POSITIVE_NUMBER + " 1   ", "9,999999999999999e+9999", "");
        assertNumber(MAX_POSITIVE_NUMBER + " 1   ", "Overflow", "");

        assertNumber(MAX_POSITIVE_NUMBER + " 0,1   ", "Overflow", "");
        assertNumber(MAX_POSITIVE_NUMBER + " 0,1   ", "-9,999999999999999e+9999", "");
        assertNumber(MAX_POSITIVE_NUMBER + " 0,1   ", "9,999999999999999e+9999", "");
        assertNumber(MAX_POSITIVE_NUMBER + " 0,1   ", "Overflow", "");
        assertNumber(MAX_POSITIVE_NUMBER + " 0,1   ", "Overflow", "");

        assertNumber(MAX_POSITIVE_NUMBER + " " + MIN_POSITIVE_NUMBER_INPUT + "  ", "Overflow", "");
        assertNumber(MAX_POSITIVE_NUMBER + " " + MIN_POSITIVE_NUMBER_INPUT + "  ", "-9,999999999999999e+9999", "");
        assertNumber(MAX_POSITIVE_NUMBER + " " + MIN_POSITIVE_NUMBER_INPUT + "  ", "9,999999999999999e+9999", "");
        assertNumber(MAX_POSITIVE_NUMBER + " " + MIN_POSITIVE_NUMBER_INPUT + "  ", "Overflow", "");

        assertNumber(MAX_POSITIVE_NUMBER + " " + MAX_POSITIVE_NUMBER_INPUT + "  ", "Overflow", "");
        assertNumber(MAX_POSITIVE_NUMBER + " " + MAX_POSITIVE_NUMBER_INPUT + "  ", "-9,999999999999999e+9999", "");
        assertNumber(MAX_POSITIVE_NUMBER + " " + MAX_POSITIVE_NUMBER_INPUT + "  ", "9,999999999999999e+9999", "");
        assertNumber(MAX_POSITIVE_NUMBER + " " + MAX_POSITIVE_NUMBER_INPUT + "  ", "Overflow", "");
    }

    @Test
    void checkMemoryAdd() {
    //  pressed one time
        assertNumber("0  8  ", "0", "");

        assertNumber("5  8  ", "5", "");
        assertNumber("5  8  ", "-5", "");

        assertNumber("0,5  8  ", "0,5", "");
        assertNumber("0,5  8  ", "-0,5", "");

        assertNumber(MIN_POSITIVE_NUMBER_INPUT+" 9  ", "0,0000000000000001", "");
        assertNumber(MIN_POSITIVE_NUMBER_INPUT+" 9  ", "-0,0000000000000001", "");

        assertNumber(MAX_POSITIVE_NUMBER_INPUT+" 9  ", "9 999 999 999 999 999", "");
        assertNumber(MAX_POSITIVE_NUMBER_INPUT+" 9  ", "-9 999 999 999 999 999", "");

        assertNumber(MAX_POSITIVE_NUMBER_VISIBLE+" 9  ", "9,999999999999999e+9999", "");
        assertNumber(MAX_POSITIVE_NUMBER_VISIBLE+" 9  ", "-9,999999999999999e+9999", "");

        assertNumber(MAX_POSITIVE_NUMBER+" 9  ", "9,999999999999999e+9999", "");
        assertNumber(MAX_POSITIVE_NUMBER+" 9  ", "-9,999999999999999e+9999", "");

        assertNumber(MIN_POSITIVE_NUMBER+" 9  ", "1,e-9999", "");
        assertNumber(MIN_POSITIVE_NUMBER+" 9  ", "-1,e-9999", "");

    //  pressed two time
        //both operands are integer
        assertNumber("0  0   ", "0", "");

        assertNumber("2  0   ", "2", "");
        assertNumber("2   0   ", "-2", "");
        assertNumber("0  2    ", "-2", "");

        assertNumber("2  2   ", "4", "");
        assertNumber("2  2   ", "0", "");
        assertNumber("2  2   ", "0", "");
        assertNumber("2  2   ", "-4", "");

        assertNumber("2  3   ", "5", "");
        assertNumber("2   3   ", "1", "");
        assertNumber("2  3    ", "-1", "");
        assertNumber("2   3    ", "-5", "");
        //first operand is decimal
        assertNumber("0,2  0   ", "0,2", "");
        assertNumber("0,2   0   ", "-0,2", "");

        assertNumber("0,2  3   ", "3,2", "");
        assertNumber("0,2  3    ", "-2,8", "");
        assertNumber("0,2   3   ", "2,8", "");
        assertNumber("0,2   3    ", "-3,2", "");
        //second operand is decimal
        assertNumber("0  0,2   ", "0,2", "");
        assertNumber("0  0,2    ", "-0,2", "");

        assertNumber("2  0,3   ", "2,3", "");
        assertNumber("2   0,3   ", "-1,7", "");
        assertNumber("2  0,3    ", "1,7", "");
        assertNumber("2   0,3    ", "-2,3", "");

        //both operands are decimal
        assertNumber("0,2  0,2   ", "0,4", "");
        assertNumber("0,2  0,2   ", "0", "");
        assertNumber("0,2  0,2   ", "0", "");
        assertNumber("0,2  0,2   ", "-0,4", "");

        assertNumber("0,2  0,3   ", "0,5", "");
        assertNumber("0,2   0,3   ", "0,1", "");
        assertNumber("0,2  0,3    ", "-0,1", "");
        assertNumber("0,2   0,3    ", "-0,5", "");

        //Min positive number which can input
        assertNumber(MIN_POSITIVE_NUMBER_INPUT+" "+MIN_POSITIVE_NUMBER_INPUT+"  ", "0,0000000000000002", "");
        assertNumber(MIN_POSITIVE_NUMBER_INPUT+" "+MIN_POSITIVE_NUMBER_INPUT+"  ", "0", "");
        assertNumber(MIN_POSITIVE_NUMBER_INPUT+" "+MIN_POSITIVE_NUMBER_INPUT+"  ", "0", "");
        assertNumber(MIN_POSITIVE_NUMBER_INPUT+" "+MIN_POSITIVE_NUMBER_INPUT+"  ", "-0,0000000000000002", "");

        assertNumber(MIN_POSITIVE_NUMBER_INPUT+" 1   ", "1", "");
        assertNumber(MIN_POSITIVE_NUMBER_INPUT+" 1   ", "-0,9999999999999999", "");
        assertNumber(MIN_POSITIVE_NUMBER_INPUT+" 1   ", "0,9999999999999999", "");
        assertNumber(MIN_POSITIVE_NUMBER_INPUT+" 1   ", "-1", "");

        assertNumber(MIN_POSITIVE_NUMBER_INPUT+" 0,1   ", "0,1000000000000001", "");
        assertNumber(MIN_POSITIVE_NUMBER_INPUT+" 0,1   ", "-0,0999999999999999", "");
        assertNumber(MIN_POSITIVE_NUMBER_INPUT+" 0,1   ", "0,0999999999999999", "");
        assertNumber(MIN_POSITIVE_NUMBER_INPUT+" 0,1   ", "-0,1000000000000001", "");

        assertNumber(MIN_POSITIVE_NUMBER_INPUT+" "+MAX_POSITIVE_NUMBER_INPUT+"  ", "9 999 999 999 999 999", "");
        assertNumber(MIN_POSITIVE_NUMBER_INPUT+" "+MAX_POSITIVE_NUMBER_INPUT+"  ", "-9 999 999 999 999 999", "");
        assertNumber(MIN_POSITIVE_NUMBER_INPUT+" "+MAX_POSITIVE_NUMBER_INPUT+"  ", "9 999 999 999 999 999", "");
        assertNumber(MIN_POSITIVE_NUMBER_INPUT+" "+MAX_POSITIVE_NUMBER_INPUT+"  ", "-9 999 999 999 999 999", "");

        // Max positive number which can input
        assertNumber(MAX_POSITIVE_NUMBER_INPUT+" "+MAX_POSITIVE_NUMBER_INPUT+"  ", "2,e+16", "");
        assertNumber(MAX_POSITIVE_NUMBER_INPUT+" "+MAX_POSITIVE_NUMBER_INPUT+"  ", "0", "");
        assertNumber(MAX_POSITIVE_NUMBER_INPUT+" "+MAX_POSITIVE_NUMBER_INPUT+"  ", "0", "");
        assertNumber(MAX_POSITIVE_NUMBER_INPUT+" "+MAX_POSITIVE_NUMBER_INPUT+"  ", "-2,e+16", "");

        assertNumber(MAX_POSITIVE_NUMBER_INPUT+" 1   ", "1,e+16", "");
        assertNumber(MAX_POSITIVE_NUMBER_INPUT+" 1   ", "9 999 999 999 999 998", "");
        assertNumber(MAX_POSITIVE_NUMBER_INPUT+" 1   ", "-9 999 999 999 999 998", "");
        assertNumber(MAX_POSITIVE_NUMBER_INPUT+" 1   ", "-1,e+16", "");

        assertNumber(MAX_POSITIVE_NUMBER_INPUT+" 0,1   ", "9 999 999 999 999 999", "");
        assertNumber(MAX_POSITIVE_NUMBER_INPUT+" 0,1   ", "9 999 999 999 999 999", "");
        assertNumber(MAX_POSITIVE_NUMBER_INPUT+" 0,1   ", "-9 999 999 999 999 999", "");
        assertNumber(MAX_POSITIVE_NUMBER_INPUT+" 0,1   ", "-9 999 999 999 999 999", "");

        // Min number
        assertNumber(MIN_POSITIVE_NUMBER + " " + MIN_POSITIVE_NUMBER + "  ", "2,e-9999", "");
        assertNumber(MIN_POSITIVE_NUMBER + " " + MIN_POSITIVE_NUMBER + "  ", "0", "negate(1,e-9999) ");
        assertNumber(MIN_POSITIVE_NUMBER + " " + MIN_POSITIVE_NUMBER + "  ", "0", "");
        assertNumber(MIN_POSITIVE_NUMBER + " " + MIN_POSITIVE_NUMBER + "  ", "-2,e-9999", "negate(1,e-9999) ");

        assertNumber(MIN_POSITIVE_NUMBER + " 1   ", "1", "");
        assertNumber(MIN_POSITIVE_NUMBER + " 1   ", "-1", "");
        assertNumber(MIN_POSITIVE_NUMBER + " 1   ", "1", "");
        assertNumber(MIN_POSITIVE_NUMBER + " 1   ", "-1", "");

        assertNumber(MIN_POSITIVE_NUMBER + " 0,1   ", "0,1", "");
        assertNumber(MIN_POSITIVE_NUMBER + " 0,1   ", "-0,1", "");
        assertNumber(MIN_POSITIVE_NUMBER + " 0,1   ", "0,1", "");
        assertNumber(MIN_POSITIVE_NUMBER + " 0,1   ", "-0,1", "");

        assertNumber(MIN_POSITIVE_NUMBER + " " + MIN_POSITIVE_NUMBER_INPUT + "  ", "0,0000000000000001", "");
        assertNumber(MIN_POSITIVE_NUMBER + " " + MIN_POSITIVE_NUMBER_INPUT + "  ", "-0,0000000000000001", "");
        assertNumber(MIN_POSITIVE_NUMBER + " " + MIN_POSITIVE_NUMBER_INPUT + "  ", "0,0000000000000001", "");
        assertNumber(MIN_POSITIVE_NUMBER + " " + MIN_POSITIVE_NUMBER_INPUT + "  ", "-0,0000000000000001", "");

        assertNumber(MIN_POSITIVE_NUMBER + " " + MAX_POSITIVE_NUMBER_INPUT + "  ", "9 999 999 999 999 999", "");
        assertNumber(MIN_POSITIVE_NUMBER + " " + MAX_POSITIVE_NUMBER_INPUT + "  ", "-9 999 999 999 999 999", "");
        assertNumber(MIN_POSITIVE_NUMBER + " " + MAX_POSITIVE_NUMBER_INPUT + "  ", "9 999 999 999 999 999", "");
        assertNumber(MIN_POSITIVE_NUMBER + " " + MAX_POSITIVE_NUMBER_INPUT + "  ", "-9 999 999 999 999 999", "");

        // Max number
        assertNumber(MAX_POSITIVE_NUMBER + " 1   ", "Overflow", "");
        assertNumber(MAX_POSITIVE_NUMBER + " 1   ", "9,999999999999999e+9999", "");
        assertNumber(MAX_POSITIVE_NUMBER + " 1   ", "-9,999999999999999e+9999", "");
        assertNumber(MAX_POSITIVE_NUMBER + " 1   ", "Overflow", "");

        assertNumber(MAX_POSITIVE_NUMBER + " 0,1   ", "Overflow", "");
        assertNumber(MAX_POSITIVE_NUMBER + " 0,1   ", "9,999999999999999e+9999", "");
        assertNumber(MAX_POSITIVE_NUMBER + " 0,1   ", "-9,999999999999999e+9999", "");
        assertNumber(MAX_POSITIVE_NUMBER + " 0,1   ", "Overflow", "");

        assertNumber(MAX_POSITIVE_NUMBER + " " + MIN_POSITIVE_NUMBER_INPUT + "  ", "Overflow", "");
        assertNumber(MAX_POSITIVE_NUMBER + " " + MIN_POSITIVE_NUMBER_INPUT + "  ", "9,999999999999999e+9999", "");
        assertNumber(MAX_POSITIVE_NUMBER + " " + MIN_POSITIVE_NUMBER_INPUT + "  ", "-9,999999999999999e+9999", "");
        assertNumber(MAX_POSITIVE_NUMBER + " " + MIN_POSITIVE_NUMBER_INPUT + "  ", "Overflow", "");

        assertNumber(MAX_POSITIVE_NUMBER + " " + MAX_POSITIVE_NUMBER_INPUT + "  ", "Overflow", "");
        assertNumber(MAX_POSITIVE_NUMBER + " " + MAX_POSITIVE_NUMBER_INPUT + "  ", "9,999999999999999e+9999", "");
        assertNumber(MAX_POSITIVE_NUMBER + " " + MAX_POSITIVE_NUMBER_INPUT + "  ", "-9,999999999999999e+9999", "");
        assertNumber(MAX_POSITIVE_NUMBER + " " + MAX_POSITIVE_NUMBER_INPUT + "  ", "Overflow", "");
    }

    @Test
    void checkAllMemory() {
        assertNumber("7   ", "7", "");
        assertNumber("24   ", "0", "");
        assertNumber(" 776  ", "776", "");
        assertNumber("12  88   ", "100", "");
        assertNumber(" 256  876  ", "256", "");
        assertNumber("67  95    ", "95", "");
        assertNumber("12  88    ", "188", "");
        assertNumber("6231    56  ", "18 693", "");
        assertNumber("12  88      ", "376", "");
        assertNumber("98765      875   ", "99 640", "");
        assertNumber("0,88776  0,1364  =  = ", "99 640,75136", "");
        assertNumber("1  0,9999999999999999  0  ", "2", "");
        assertNumber("9999999    0,00018943  898  ", "29 999 996,99981057", "");
        assertNumber(" 9,9999999999999911   99999999  99999999  ", "99 999 999", "");
        assertNumber(MAX_POSITIVE_NUMBER_INPUT + "  9999999999999999   00000000000001  ", "9 999 999 999 999 999", "");
        assertNumber(MAX_POSITIVE_NUMBER_INPUT + "                    ", "1,8e+18", "");
    }

    @Test
    void checkAllOperations() {
        assertNumber("5  + 3 -", "28", "sqr(5) + 3 - ");
        assertNumber("5  - 3 x", "22", "sqr(5) - 3 x ");
        assertNumber("5  x 3 /", "75", "sqr(5) x 3 ÷ ");
        assertNumber("5  / 3 +", "8,333333333333333", "sqr(5) ÷ 3 + ");

        assertNumber("5 √ + 3 -", "5,23606797749979", "√(5) + 3 - ");
        assertNumber("5 √ - 3 x", "-0,7639320225002103", "√(5) - 3 x ");
        assertNumber("5 √ x 3 /", "6,708203932499369", "√(5) x 3 ÷ ");
        assertNumber("5 √ / 3 +", "0,7453559924999299", "√(5) ÷ 3 + ");

        assertNumber("5  + 3 -", "3,2", "1/(5) + 3 - ");
        assertNumber("5  - 3 x", "-2,8", "1/(5) - 3 x ");
        assertNumber("5  x 3 /", "0,6", "1/(5) x 3 ÷ ");
        assertNumber("5  / 3 +", "0,0666666666666667", "1/(5) ÷ 3 + ");

        assertNumber("5 + 3  -", "14", "5 + sqr(3) - ");
        assertNumber("5 + 3 √ -", "6,732050807568877", "5 + √(3) - ");
        assertNumber("5 + 3  -", "5,333333333333333", "5 + 1/(3) - ");

        assertNumber("5 - 3  -", "-4", "5 - sqr(3) - ");
        assertNumber("5 - 3 √ -", "3,267949192431123", "5 - √(3) - ");
        assertNumber("5 - 3  -", "4,666666666666667", "5 - 1/(3) - ");

        assertNumber("5 x 3  -", "45", "5 x sqr(3) - ");
        assertNumber("5 x 3 √ -", "8,660254037844386", "5 x √(3) - ");
        assertNumber("5 x 3  -", "1,666666666666667", "5 x 1/(3) - ");

        assertNumber("5 / 3  -", "0,5555555555555556", "5 ÷ sqr(3) - ");
        assertNumber("5 / 3 √ -", "2,886751345948129", "5 ÷ √(3) - ");
        assertNumber("5 / 3  -", "15", "5 ÷ 1/(3) - ");

//        Random
        assertNumber("2  3 +  - ", "5", "3 + 2 - ");
        assertNumber("5 √  -", "-2,23606797749979", "negate(√(5)) - ");
        assertNumber("878,756 + 45,63 %  x 78  =", "2 906 811,3127248", "");
        assertNumber(",883655 x 0,74553 % + 0,99999 + √ =", "2,009861478780531", "");


        assertNumber("3   + 8 -", "7,666666666666667", "negate(1/(3)) + 8 - ");
        assertNumber("985948894 - 7453628  x 6734 = ", "6 639 379 852 195,999", "");
        assertNumber("5 + 8 -", "-59", "5 + negate(negate(negate(sqr(8)))) - ");
        assertNumber("4376 + 7987 - % + 8,9 = ", "2 298 455 479 608,324", "sqr(-1516065,79) ");
        assertNumber("0,0000000000000001  x ,0000001 =x 0,000000000000001 =", "1,e-9999", "");
        assertNumber("2  + 6  - 8 % x 0,999999   /", "-36,799632", "sqr(2) + sqr(6) - 3,2 x -0,99999 ÷ ");
        assertNumber("675  + 784  + ,8354  - ", "1 070 281,69789316", "sqr(675) + sqr(784) + sqr(0,8354) - ");
        assertNumber("987987 + 7% - 3878 x 476746% + 99877% = ", "2,520299604187571e+25", "sqr(5020258563249,079) ");
        assertNumber("1000000000000000  x 1000000000 = 9999999999999999 x  = ", "9,999999999999999e+9999", "");
    }

    void assertNumber(String buttonsPressed, String result, String outOperationMemoryResult) {
        checkMouseInputNumber(buttonsPressed, result, outOperationMemoryResult);
    }

    void checkKeyInputNumber(String buttonsPressed, String result, String outOperationMemoryResult) {
        for (char idButton : buttonsPressed.toCharArray()) {
            keyboardInput(String.valueOf(idButton));
        }

        assertEquals(result, outLabel.getText());
        assertEquals(outOperationMemoryResult, outOperationMemory.getText());
        type(ESCAPE);
    }


    void checkNumpadInputNumber(String buttonsPressed, String result, String outOperationMemoryResult) {
        for (char idButton : buttonsPressed.toCharArray()) {
            keyboardNumpadInput(String.valueOf(idButton));
        }

        assertEquals(result, outLabel.getText());
        assertEquals(outOperationMemoryResult, outOperationMemory.getText());
        type(ESCAPE);
    }


    void checkMouseInputNumber(String buttonsPressed, String result, String outOperationMemoryResult) {
        for (char idButton : buttonsPressed.toCharArray()) {
            mouseInput(String.valueOf(idButton));
        }

        FXTestUtils.awaitEvents();
        assertEquals(result, outLabel.getText());
        assertEquals(outOperationMemoryResult, outOperationMemory.getText());
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
        } else if (idButton.equals("")) {
            type(F9);
        } else if (idButton.equals("")) {
            type(BACK_SPACE);
        } else if (idButton.equals("c")) {
            type(ESCAPE);
        } else if (idButton.equals("ce")) {
            type(DELETE);
        } else if (idButton.equals("+")) {
            type(ADD);
        } else if (idButton.equals("-")) {
            type(SUBTRACT);
        } else if (idButton.equals("x")) {
            type(MULTIPLY);
        } else if (idButton.equals("√")) {
            push(new KeyCodeCombination(DIGIT2, KeyCombination.SHIFT_DOWN));
        } else if (idButton.equals("%")) {
            push(new KeyCodeCombination(DIGIT5, KeyCombination.SHIFT_DOWN));
        } else if (idButton.equals("")) {
            type(R);
        } else if (idButton.equals("")) {
            type(Q);
        } else if (idButton.equals("/")) {
            type(DIVIDE);
        } else if (idButton.equals("=")) {
            type(ENTER);
        } else if (idButton.equals("")) {
            push(new KeyCodeCombination(M, KeyCombination.CONTROL_DOWN));
        } else if (idButton.equals("")) {
            push(new KeyCodeCombination(P, KeyCombination.CONTROL_DOWN));
        } else if (idButton.equals("")) {
            push(new KeyCodeCombination(Q, KeyCombination.CONTROL_DOWN));
        } else if (idButton.equals("")) {
            push(new KeyCodeCombination(L, KeyCombination.CONTROL_DOWN));
        } else if (idButton.equals("")) {
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
        } else if (idButton.equals("")) {
            type(F9);
        } else if (idButton.equals("")) {
            type(BACK_SPACE);
        } else if (idButton.equals("c")) {
            type(ESCAPE);
        } else if (idButton.equals("ce")) {
            type(DELETE);
        } else if (idButton.equals("+")) {
            push(new KeyCodeCombination(EQUALS, KeyCombination.SHIFT_DOWN));
        } else if (idButton.equals("-")) {
            type(MINUS);
        } else if (idButton.equals("x")) {
            push(new KeyCodeCombination(DIGIT8, KeyCombination.SHIFT_DOWN));
        } else if (idButton.equals("√")) {
            push(new KeyCodeCombination(DIGIT2, KeyCombination.SHIFT_DOWN));
        } else if (idButton.equals("%")) {
            push(new KeyCodeCombination(DIGIT5, KeyCombination.SHIFT_DOWN));
        } else if (idButton.equals("")) {
            type(R);
        } else if (idButton.equals("")) {
            type(Q);
        } else if (idButton.equals("/")) {
            type(SLASH);
        } else if (idButton.equals("=")) {
            type(ENTER);
        } else if (idButton.equals("")) {
            push(new KeyCodeCombination(M, KeyCombination.CONTROL_DOWN));
        } else if (idButton.equals("")) {
            push(new KeyCodeCombination(P, KeyCombination.CONTROL_DOWN));
        } else if (idButton.equals("")) {
            push(new KeyCodeCombination(Q, KeyCombination.CONTROL_DOWN));
        } else if (idButton.equals("")) {
            push(new KeyCodeCombination(L, KeyCombination.CONTROL_DOWN));
        } else if (idButton.equals("")) {
            push(new KeyCodeCombination(R, KeyCombination.CONTROL_DOWN));
        }
    }

    void mouseInput(String idButtonClickedMouse) {
        Button button = null;
        if (idButtonClickedMouse.equals("1")) {
            button = from(root).lookup("#one").query();
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
        } else if (idButtonClickedMouse.equals("")) {
            button = from(root).lookup("#plusMinus").query();
        } else if (idButtonClickedMouse.equals("")) {
            button = from(root).lookup("#backspace").query();
        } else if (idButtonClickedMouse.equals("c")) {
            button = from(root).lookup("#C").query();
        } else if (idButtonClickedMouse.equals("d")) {
            button = from(root).lookup("#CE").query();
        } else if (idButtonClickedMouse.equals("+")) {
            button = from(root).lookup("#add").query();
        } else if (idButtonClickedMouse.equals("-")) {
            button = from(root).lookup("#subtract").query();
        } else if (idButtonClickedMouse.equals("x")) {
            button = from(root).lookup("#multiply").query();
        } else if (idButtonClickedMouse.equals("√")) {
            button = from(root).lookup("#sqrt").query();
        } else if (idButtonClickedMouse.equals("%")) {
            button = from(root).lookup("#percent").query();
        } else if (idButtonClickedMouse.equals("")) {
            button = from(root).lookup("#oneDivideX").query();
        } else if (idButtonClickedMouse.equals("")) {
            button = from(root).lookup("#sqr").query();
        } else if (idButtonClickedMouse.equals("/")) {
            button = from(root).lookup("#divide").query();
        } else if (idButtonClickedMouse.equals("=")) {
            button = from(root).lookup("#equal").query();
        } else if (idButtonClickedMouse.equals("")) {
            button = from(root).lookup("#memoryStore").query();
        } else if (idButtonClickedMouse.equals("")) {
            button = from(root).lookup("#memoryAdd").query();
        } else if (idButtonClickedMouse.equals("")) {
            button = from(root).lookup("#memorySubtract").query();
        } else if (idButtonClickedMouse.equals("")) {
            button = from(root).lookup("#memoryClear").query();
        } else if (idButtonClickedMouse.equals("")) {
            button = from(root).lookup("#memoryRecall").query();
        }

        if (button != null) {
//            clickOn(button);
            Scene scene = button.getScene();
            double sceneX = scene.getWindow().getX();
            double sceneY = scene.getWindow().getY();

            int buttonX = (int) (sceneX + button.getBoundsInParent().getCenterX());
            int buttonY = (int) (sceneY + button.getBoundsInParent().getCenterY() + button.getParent().getBoundsInParent().getMinY() + button.getParent().getParent().getLayoutY());

            if (button.getId().equals("memoryStore") || button.getId().equals("memoryAdd") ||
                    button.getId().equals("memorySubtract") || button.getId().equals("memoryClear") || button.getId().equals("memoryRecall")) {
                buttonY += (int) button.getParent().getParent().getParent().getLayoutY();
            }
            FXTestUtils.awaitEvents();
            robot.mouseMove(buttonX, buttonY);
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        }
    }
}