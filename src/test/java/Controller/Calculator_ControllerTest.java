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
import org.loadui.testfx.utils.FXTestUtils;
import org.testfx.framework.junit5.ApplicationTest;

import java.awt.*;
import java.awt.event.InputEvent;
import java.math.BigDecimal;

import static javafx.scene.input.KeyCode.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


class Calculator_ControllerTest extends ApplicationTest {
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
    }

    @BeforeEach
    void outMemory() {
        outOperationMemory = from(root).lookup("#outOperationMemory").query();
        outLabel = from(root).lookup("#outText").query();
    }

    @Test
    void checkInput() {
        //Input number
        //input zero
        assertNumber("0", "0000000", "");
        assertNumber("1", "001", "");
        assertNumber("12 345", "0000000000000012345", "");
        //Formatter number
        assertNumber("222", "222", "");
        assertNumber("222 222", "222222", "");
        assertNumber("222 222 222", "222222222", "");
        assertNumber("2 222 222 222 222 222", "2222222222222222222", "");
        assertNumber("-2 222 222 222 222 222", "222222222222222222 +/-", "");
        assertNumber("2 222 222 222 222 222", "222222222222222222 +/- +/-", "");
        //Input Decimal
        assertNumber("0,00000000", "0,00000000", "");
        assertNumber("0,00000000000000", "0,00000000000000", "");
        assertNumber("1,00000000000000", "1,00000000000000", "");
        assertNumber("0,000000000000001", "0,000000000000001", "");
        assertNumber("1,00000000000001", "1,00000000000001", "");
        assertNumber("1,000000000000001", "1,000000000000001", "");
        assertNumber("0,0000000000000012", "0,000000000000001234", "");
        assertNumber("1 234,567891234567", "1234,567891 ,234567", "");
        assertNumber("123 456,1234567891", "123 456,1234567891234", "");
        assertNumber("12 456 789,12345678", "12456789,123456789", "");
        assertNumber("1 234 567 891 234 567,", "1234567891234567,", "");
        assertNumber("1 234 567 891 234 567,", "1234567891234567,89", "");
        assertNumber("-1 234 567 891 234 567,", "1234567891234567,89 +/-", "");
        assertNumber("1 234 567 891 234 567,", "1234567891234567,89 +/- +/-", "");
        assertNumber("1 234 567 891 234 567,", "1234567891234567,89 +/- +/-", "");
        assertNumber("-222,3333333333333", "222,3333333333333 +/- 44", "");
        assertNumber("222,3333333333333", "222,3333333333333 +/- +/- 33,", "");
    }

    @Test
    void checkBackspace() {
        //zero
        assertNumber("0", " bs", "");
        assertNumber("0", "5 bs", "");
        assertNumber("0", "01 bs", "");
        assertNumber("0", "8 +/- bs", "");
        assertNumber("1 111", "11111 bs", "");
        assertNumber("-44", "444 +/- bs", "");
        assertNumber("88 888 888", "8888888888 bs bs", "");
        assertNumber("0", "7838765,89 +/- bs bs bs bs bs bs bs bs bs bs", "");
        //comma bs
        assertNumber("234 567", "234567, bs", "");
        assertNumber("234 567", "234567,8 bs bs", "");
        assertNumber("-678 395", "678395, +/- bs", "");
        //in operation
        assertNumber("0", "679 + 67 bs bs", "679 + ");
        assertNumber("9", "3 1/x + 987 bs bs", "1/(3) + ");
        assertNumber("0,00", "3 x² - ,0000 bs bs", "sqr(3) - ");
        assertNumber("7", "2456 x 2 - 789 bs bs", "2456 x 2 - ");
        //Don`t bs result
        assertNumber("7 396", "86 x² bs bs", "sqr(86)");
        assertNumber("334", "347 - 13 - bs bs", "347 - 13 - ");
        assertNumber("299,422778024652", "89654 √ = bs bs bs", "");
        assertNumber("435 611 087", "435576762 + 34325 = bs bs bs", "");
        assertNumber("9,999999999999996e+63", "9999999999999999 x² x² bs bs bs bs = bs bs bs", "");
        assertNumber("0,0000000000000001", "2,555555 bs 1 x² x 0,0000000000000000 bs 1", "sqr(2,555551) x ");
        assertNumber("0,0000000000000001", ",0000000000000000 bs 1 x 2 / 0,0000000000000000 bs 1", "0,0000000000000001 x 2 ÷ ");
    }

    @Test
    void checkAdd() {
        //Integer
        assertNumber("2", "0 + 2 +", "0 + 2 + ");
        assertNumber("2", "2 + 0 +", "2 + 0 + ");
        assertNumber("-2", "0 + 2 +/- +", "0 + -2 + ");
        assertNumber("-2", "2 +/- + 0 +", "-2 + 0 + ");

        assertNumber("5", "2 + 3 +", "2 + 3 + ");
        assertNumber("-1", "2 + 3 +/- +", "2 + -3 + ");
        assertNumber("1", "2 +/- + 3 +", "-2 + 3 + ");
        assertNumber("-5", "2 +/- + 3 +/- +", "-2 + -3 + ");

        //with equal
        assertNumber("2", "0 + 2 =", "");
        assertNumber("2", "2 + 0 =", "");
        assertNumber("-2", "0 + 2 +/- =", "");
        assertNumber("-2", "2 +/- + 0 =", "");

        assertNumber("5", "2 + 3 =", "");
        assertNumber("-1", "2 + 3 +/- =", "");
        assertNumber("1", "2 +/- + 3 =", "");
        assertNumber("-5", "2 +/- + 3 +/- =", "");

        //Decimal
        assertNumber("0,2", "0 + ,2 +", "0 + 0,2 + ");
        assertNumber("-0,2", "0 + ,2 +/- +", "0 + -0,2 + ");
        assertNumber("-0,2", ",2 +/- + 0 +", "-0,2 + 0 + ");

        assertNumber("2,3", "2 + 0,3 +", "2 + 0,3 + ");
        assertNumber("3,2", "0,2 + 3 +", "0,2 + 3 + ");
        assertNumber("0,5", "0,2 + 0,3 +", "0,2 + 0,3 + ");

        assertNumber("1,7", "2 + 0,3 +/- +", "2 + -0,3 + ");
        assertNumber("-2,8", "0,2 + 3 +/- +", "0,2 + -3 + ");
        assertNumber("-0,1", "0,2 + 0,3 +/- +", "0,2 + -0,3 + ");

        assertNumber("-1,7", "2 +/- + 0,3 +", "-2 + 0,3 + ");
        assertNumber("2,8", "0,2 +/- + 3 +", "-0,2 + 3 + ");
        assertNumber("0,1", "0,2 +/- + 0,3 +", "-0,2 + 0,3 + ");

        assertNumber("-2,3", "2 +/- + 0,3 +/- +", "-2 + -0,3 + ");
        assertNumber("-3,2", "0,2 +/- + 3 +/- +", "-0,2 + -3 + ");
        assertNumber("-0,5", "0,2 +/- + 0,3 +/- +", "-0,2 + -0,3 + ");

        //with equal
        assertNumber("0,2", "0 + ,2 =", "");
        assertNumber("-0,2", "0 + ,2 +/- =", "");
        assertNumber("-0,2", ",2 +/- + 0 =", "");

        assertNumber("2,3", "2 + 0,3 =", "");
        assertNumber("3,2", "0,2 + 3 =", "");
        assertNumber("0,5", "0,2 + 0,3 =", "");

        assertNumber("1,7", "2 + 0,3 +/- =", "");
        assertNumber("-2,8", "0,2 + 3 +/- =", "");
        assertNumber("-0,1", "0,2 + 0,3 +/- =", "");

        assertNumber("-1,7", "2 +/- + 0,3 =", "");
        assertNumber("2,8", "0,2 +/- + 3 =", "");
        assertNumber("0,1", "0,2 +/- + 0,3 =", "");

        assertNumber("-2,3", "2 +/- + 0,3 +/- =", "");
        assertNumber("-3,2", "0,2 +/- + 3 +/- =", "");
        assertNumber("-0,5", "0,2 +/- + 0,3 +/- =", "");

        assertNumber("9", "2 + 3 + 4 +", "2 + 3 + 4 + ");
        assertNumber("15", "2 + 3 + 4 + 6 +", "2 + 3 + 4 + 6 + ");
        assertNumber("5", "2 - x / + 3 -", "2 + 3 - ");
        assertNumber("39,998", "19 + 0,999 + =", "");
        assertNumber("2", "1 + 0,9999999999999999 -", "1 + 0,9999999999999999 - ");
        assertNumber("200 024,2", "199999 + 0,999999999999999999999 + 24,2 + 0,00000000000001 =", "");
    }

    @Test
    void checkSubtract() {
        //Integer
        assertNumber("-2", "0 - 2 -", "0 - 2 - ");
        assertNumber("2", "2 - 0 -", "2 - 0 - ");
        assertNumber("2", "0 - 2 +/- -", "0 - -2 - ");
        assertNumber("-2", "2 +/- - 0 -", "-2 - 0 - ");

        assertNumber("-1", "2 - 3 -", "2 - 3 - ");
        assertNumber("5", "2 - 3 +/- -", "2 - -3 - ");
        assertNumber("-5", "2 +/- - 3 -", "-2 - 3 - ");
        assertNumber("1", "2 +/- - 3 +/- -", "-2 - -3 - ");

        //with equal
        assertNumber("-2", "0 - 2 =", "");
        assertNumber("2", "2 - 0 =", "");
        assertNumber("2", "0 - 2 +/- =", "");
        assertNumber("-2", "2 +/- - 0 =", "");

        assertNumber("-1", "2 - 3 =", "");
        assertNumber("5", "2 - 3 +/- =", "");
        assertNumber("-5", "2 +/- - 3 =", "");
        assertNumber("1", "2 +/- - 3 +/- =", "");

        //Decimal
        assertNumber("-0,2", "0 - ,2 -", "0 - 0,2 - ");
        assertNumber("0,2", "0 - ,2 +/- -", "0 - -0,2 - ");
        assertNumber("-0,2", ",2 +/- - 0 -", "-0,2 - 0 - ");

        assertNumber("1,7", "2 - 0,3 -", "2 - 0,3 - ");
        assertNumber("-2,8", "0,2 - 3 -", "0,2 - 3 - ");
        assertNumber("-0,1", "0,2 - 0,3 -", "0,2 - 0,3 - ");

        assertNumber("2,3", "2 - 0,3 +/- -", "2 - -0,3 - ");
        assertNumber("3,2", "0,2 - 3 +/- -", "0,2 - -3 - ");
        assertNumber("0,5", "0,2 - 0,3 +/- -", "0,2 - -0,3 - ");

        assertNumber("-2,3", "2 +/- - 0,3 -", "-2 - 0,3 - ");
        assertNumber("-3,2", "0,2 +/- - 3 -", "-0,2 - 3 - ");
        assertNumber("-0,5", "0,2 +/- - 0,3 -", "-0,2 - 0,3 - ");

        assertNumber("-1,7", "2 +/- - 0,3 +/- -", "-2 - -0,3 - ");
        assertNumber("2,8", "0,2 +/- - 3 +/- -", "-0,2 - -3 - ");
        assertNumber("0,1", "0,2 +/- - 0,3 +/- -", "-0,2 - -0,3 - ");

        //with equal
        assertNumber("-0,2", "0 - ,2 =", "");
        assertNumber("0,2", "0 - ,2 +/- =", "");
        assertNumber("-0,2", ",2 +/- - 0 =", "");

        assertNumber("1,7", "2 - 0,3 =", "");
        assertNumber("-2,8", "0,2 - 3 =", "");
        assertNumber("-0,1", "0,2 - 0,3 =", "");

        assertNumber("2,3", "2 - 0,3 +/- =", "");
        assertNumber("3,2", "0,2 - 3 +/- =", "");
        assertNumber("0,5", "0,2 - 0,3 +/- =", "");

        assertNumber("-2,3", "2 +/- - 0,3 =", "");
        assertNumber("-3,2", "0,2 +/- - 3 =", "");
        assertNumber("-0,5", "0,2 +/- - 0,3 =", "");

        assertNumber("-1,7", "2 +/- - 0,3 +/- =", "");
        assertNumber("2,8", "0,2 +/- - 3 +/- =", "");
        assertNumber("0,1", "0,2 +/- - 0,3 +/- =", "");

        assertNumber("2", "5 - 1 - 2 -", "5 - 1 - 2 - ");
        assertNumber("-6", "5 - 1 - 2 - 8 -", "5 - 1 - 2 - 8 - ");
        assertNumber("-3", "5 / x + - 8 -", "5 - 8 - ");
        assertNumber("230,01476", "687 - 456 - ,98524 -", "687 - 456 - 0,98524 - ");
        assertNumber("22,9", "24 - 0,9999999999999999 - 0,1 -", "24 - 0,9999999999999999 - 0,1 - ");
        assertNumber("45 565,7412036", "45623 - 67,23445 - 9,9756536 +/- -", "45623 - 67,23445 - -9,9756536 - ");
    }

    @Test
    void checkMultiply() {
        //Integer
        assertNumber("0", "0 x 2 x", "0 x 2 x ");
        assertNumber("0", "2 x 0 x", "2 x 0 x ");
        assertNumber("0", "0 x 2 +/- x", "0 x -2 x ");
        assertNumber("0", "2 +/- x 0 x", "-2 x 0 x ");

        assertNumber("6", "2 x 3 x", "2 x 3 x ");
        assertNumber("-6", "2 x 3 +/- x", "2 x -3 x ");
        assertNumber("-6", "2 +/- x 3 x", "-2 x 3 x ");
        assertNumber("6", "2 +/- x 3 +/- x", "-2 x -3 x ");

        //with equal
        assertNumber("0", "0 x 2 =", "");
        assertNumber("0", "2 x 0 =", "");
        assertNumber("0", "0 x 2 +/- =", "");
        assertNumber("0", "2 +/- x 0 =", "");

        assertNumber("6", "2 x 3 =", "");
        assertNumber("-6", "2 x 3 +/- =", "");
        assertNumber("-6", "2 +/- x 3 =", "");
        assertNumber("6", "2 +/- x 3 +/- =", "");

        //Decimal
        assertNumber("0", "0 x ,2 x", "0 x 0,2 x ");
        assertNumber("0", "0 x ,2 +/- x", "0 x -0,2 x ");
        assertNumber("0", ",2 x 0 x", "0,2 x 0 x ");
        assertNumber("0", ",2 +/- x 0 x", "-0,2 x 0 x ");

        assertNumber("0,6", "2 x 0,3 x", "2 x 0,3 x ");
        assertNumber("0,6", "0,2 x 3 x", "0,2 x 3 x ");
        assertNumber("0,06", "0,2 x 0,3 x", "0,2 x 0,3 x ");

        assertNumber("-0,6", "2 x 0,3 +/- x", "2 x -0,3 x ");
        assertNumber("-0,6", "0,2 x 3 +/- x", "0,2 x -3 x ");
        assertNumber("-0,06", "0,2 x 0,3 +/- x", "0,2 x -0,3 x ");

        assertNumber("-0,6", "2 +/- x 0,3 x", "-2 x 0,3 x ");
        assertNumber("-0,6", "0,2 +/- x 3 x", "-0,2 x 3 x ");
        assertNumber("-0,06", "0,2 +/- x 0,3 x", "-0,2 x 0,3 x ");

        assertNumber("0,6", "2 +/- x 0,3 +/- x", "-2 x -0,3 x ");
        assertNumber("0,6", "0,2 +/- x 3 +/- x", "-0,2 x -3 x ");
        assertNumber("0,06", "0,2 +/- x 0,3 +/- x", "-0,2 x -0,3 x ");

        //with equal
        assertNumber("0", "0 x ,2 =", "");
        assertNumber("0", "0 x ,2 +/- =", "");
        assertNumber("0", ",2 x 0 =", "");
        assertNumber("0", ",2 +/- x 0 =", "");

        assertNumber("0,6", "2 x 0,3 =", "");
        assertNumber("0,6", "0,2 x 3 =", "");
        assertNumber("0,06", "0,2 x 0,3 =", "");

        assertNumber("-0,6", "2 x 0,3 +/- =", "");
        assertNumber("-0,6", "0,2 x 3 +/- =", "");
        assertNumber("-0,06", "0,2 x 0,3 +/- =", "");

        assertNumber("-0,6", "2 +/- x 0,3 =", "");
        assertNumber("-0,6", "0,2 +/- x 3 =", "");
        assertNumber("-0,06", "0,2 +/- x 0,3 =", "");

        assertNumber("0,6", "2 +/- x 0,3 +/- =", "");
        assertNumber("0,6", "0,2 +/- x 3 +/- =", "");
        assertNumber("0,06", "0,2 +/- x 0,3 +/- =", "");

        assertNumber("10", "5 x 1 x 2 x", "5 x 1 x 2 x ");
        assertNumber("80", "5 x 1 x 2 x 8 x", "5 x 1 x 2 x 8 x ");
        assertNumber("56", "7 - + / x 8 x", "7 x 8 x ");
        assertNumber("308 648,10528", "687 x 456 x ,98524 x", "687 x 456 x 0,98524 x ");
        assertNumber("2,4", "24 x 0,9999999999999999 x 0,1 x", "24 x 0,9999999999999999 x 0,1 x ");
        assertNumber("-30 599 692,0677186", "45623 x 67,23445 x 9,9756536 +/- x", "45623 x 67,23445 x -9,9756536 x ");
    }

    @Test
    void checkDivide() {
        //Integer
        assertNumber("0", "0 / 2 /", "0 ÷ 2 ÷ ");
        assertNumber("0", "0 / 2 +/- /", "0 ÷ -2 ÷ ");

        assertNumber("0,6666666666666667", "2 / 3 /", "2 ÷ 3 ÷ ");
        assertNumber("-0,6666666666666667", "2 / 3 +/- /", "2 ÷ -3 ÷ ");
        assertNumber("-0,6666666666666667", "2 +/- / 3 /", "-2 ÷ 3 ÷ ");
        assertNumber("0,6666666666666667", "2 +/- / 3 +/- /", "-2 ÷ -3 ÷ ");

        //with equal
        assertNumber("0", "0 / 2 =", "");
        assertNumber("0", "0 / 2 +/- =", "");

        assertNumber("0,6666666666666667", "2 / 3 =", "");
        assertNumber("-0,6666666666666667", "2 / 3 +/- =", "");
        assertNumber("-0,6666666666666667", "2 +/- / 3 =", "");
        assertNumber("0,6666666666666667", "2 +/- / 3 +/- =", "");

        //Decimal
        assertNumber("0", "0 / ,2 /", "0 ÷ 0,2 ÷ ");
        assertNumber("0", "0 / ,2 +/- /", "0 ÷ -0,2 ÷ ");

        assertNumber("6,666666666666667", "2 / 0,3 /", "2 ÷ 0,3 ÷ ");
        assertNumber("0,0666666666666667", "0,2 / 3 /", "0,2 ÷ 3 ÷ ");
        assertNumber("0,6666666666666667", "0,2 / 0,3 /", "0,2 ÷ 0,3 ÷ ");

        assertNumber("-6,666666666666667", "2 / 0,3 +/- /", "2 ÷ -0,3 ÷ ");
        assertNumber("-0,0666666666666667", "0,2 / 3 +/- /", "0,2 ÷ -3 ÷ ");
        assertNumber("-0,6666666666666667", "0,2 / 0,3 +/- /", "0,2 ÷ -0,3 ÷ ");

        assertNumber("-6,666666666666667", "2 +/- / 0,3 /", "-2 ÷ 0,3 ÷ ");
        assertNumber("-0,0666666666666667", "0,2 +/- / 3 /", "-0,2 ÷ 3 ÷ ");
        assertNumber("-0,6666666666666667", "0,2 +/- / 0,3 /", "-0,2 ÷ 0,3 ÷ ");

        assertNumber("6,666666666666667", "2 +/- / 0,3 +/- /", "-2 ÷ -0,3 ÷ ");
        assertNumber("0,0666666666666667", "0,2 +/- / 3 +/- /", "-0,2 ÷ -3 ÷ ");
        assertNumber("0,6666666666666667", "0,2 +/- / 0,3 +/- /", "-0,2 ÷ -0,3 ÷ ");

        //with equal
        assertNumber("0", "0 / ,2 =", "");
        assertNumber("0", "0 / ,2 +/- =", "");

        assertNumber("6,666666666666667", "2 / 0,3 =", "");
        assertNumber("0,0666666666666667", "0,2 / 3 =", "");
        assertNumber("0,6666666666666667", "0,2 / 0,3 =", "");

        assertNumber("-6,666666666666667", "2 / 0,3 +/- =", "");
        assertNumber("-0,0666666666666667", "0,2 / 3 +/- =", "");
        assertNumber("-0,6666666666666667", "0,2 / 0,3 +/- =", "");

        assertNumber("-6,666666666666667", "2 +/- / 0,3 =", "");
        assertNumber("-0,0666666666666667", "0,2 +/- / 3 =", "");
        assertNumber("-0,6666666666666667", "0,2 +/- / 0,3 =", "");

        assertNumber("6,666666666666667", "2 +/- / 0,3 +/- =", "");
        assertNumber("0,0666666666666667", "0,2 +/- / 3 +/- =", "");
        assertNumber("0,6666666666666667", "0,2 +/- / 0,3 +/- =", "");

        assertNumber("0,3947368421052632", "6 / 1,9 / 8 /", "6 ÷ 1,9 ÷ 8 ÷ ");
        assertNumber("0,5833333333333333", "7 / 2 / 3 / 2 /", "7 ÷ 2 ÷ 3 ÷ 2 ÷ ");
        assertNumber("2,00000002", "2 + - x / 0,99999999 / ", "2 ÷ 0,99999999 ÷ ");
        assertNumber("3,333333333333333", "10 / 3 /", "10 ÷ 3 ÷ ");
        assertNumber("3,333333444444448", "10 / 2,9999999 /", "10 ÷ 2,9999999 ÷ ");
        assertNumber("-1,658062522813398", "0,9999999999999999 / 0,77644 / 0,776767767666 +/- /", "0,9999999999999999 ÷ 0,77644 ÷ -0,776767767666 ÷ ");
    }

    @Test
    void checkEqual() {
        //One equal
        assertNumber("0", "=", "");
        assertNumber("0", "+ =", "");
        assertNumber("0", "- =", "");
        assertNumber("0", "x =", "");
        assertNumber("0", "x² =", "");
        assertNumber("0", "√ =", "");
        assertNumber("0", "% =", "");

        assertNumber("2", "2 =", "");
        assertNumber("4", "2 + =", "");
        assertNumber("0", "2 - =", "");
        assertNumber("4", "2 x =", "");
        assertNumber("1", "2 / =", "");
        assertNumber("4", "2 x² =", "");
        assertNumber("1,414213562373095", "2 √ =", "");
        assertNumber("0,5", "2 1/x =", "");
        assertNumber("2,06", "2 + 3 % =", "");

        assertNumber("4", "2 + = + +", "4 + ");
        assertNumber("0", "2 - = - -", "0 - ");
        assertNumber("4", "2 x = x x", "4 x ");
        assertNumber("1", "2 / = / /", "1 ÷ ");
        assertNumber("256", "2 x² = x² x²", "sqr(sqr(4))");
        assertNumber("1,090507732665258", "2 √ = √ √", "√(√(1,414213562373095))");
        assertNumber("0,5", "2 1/x = 1/x 1/x", "1/(1/(0,5))");
        assertNumber("0,0008741816", "2 + 3 % = % %", "0,0008741816");

        assertNumber("8", "2 + = + =", "");
        assertNumber("0", "2 - = - =", "");
        assertNumber("16", "2 x = x =", "");
        assertNumber("1", "2 / = / =", "");
        assertNumber("16", "2 x² = x² =", "");
        assertNumber("1,189207115002721", "2 √ = √ =", "");
        assertNumber("2", "2 1/x = 1/x =", "");
        assertNumber("0,102436", "2 + 3 % = % =", "");
        //Several equals
        assertNumber("8", "2 + = = =", "");
        assertNumber("-4", "2 - = = =", "");
        assertNumber("16", "2 x = = =", "");
        assertNumber("0,25", "2 / = = =", "");
        assertNumber("4", "2 x² = = =", "");
        assertNumber("1,414213562373095", "2 √ = = =", "");
        assertNumber("0,5", "2 1/x = = =", "");
        assertNumber("2,18", "2 + 3 % = = =", "");
        assertNumber("-12", "2 + = = = = 6 - = = =", "");

    }

    @Test
    void checkNegate() {
        assertNumber("0", "0 +/-", "");
        assertNumber("0", "0 = +/-", "negate(0)");
        assertNumber("-1", "1 +/-", "");
        assertNumber("1", "1 +/- +/-", "");
        assertNumber("-1", "1 = +/-", "negate(1)");
        assertNumber("1", "1 = +/- +/-", "negate(negate(1))");
        assertNumber("9", "2 + 3 - 4 +/- +", "2 + 3 - -4 + ");
        assertNumber("1", "2 + 3 - 4 +/- +/- +", "2 + 3 - 4 + ");
        assertNumber("-5", "2 + 3 - +/-", "2 + 3 - negate(5)");
        assertNumber("0", "2 + 3 - +/- 5 +", "2 + 3 - 5 + ");
        assertNumber("5", "2 + 3 - +/- +/-", "2 + 3 - negate(negate(5))");
        assertNumber("-3", "9 √ +/- +/- +/-", "negate(negate(negate(√(9))))");
    }

    @Test
    void checkOperationHistory() {
        assertNumber("0", "+", "0 + ");
        assertNumber("0", "-", "0 - ");
        assertNumber("0", "x", "0 x ");
        assertNumber("0", "/", "0 ÷ ");
        assertNumber("0", "x²", "sqr(0)");
        assertNumber("0", "√", "√(0)");
        assertNumber("0", "%", "0");

        assertNumber("3", "3 +", "3 + ");
        assertNumber("3", "3 -", "3 - ");
        assertNumber("3", "3 x", "3 x ");
        assertNumber("3", "3 /", "3 ÷ ");
        //Decimal number
        assertNumber("5", "2 + 3, +", "2 + 3 + ");
        assertNumber("5", "2 + 3,00000 +", "2 + 3 + ");
        assertNumber("5,000000001", "2,0000000 + 3,000000001 +", "2 + 3,000000001 + ");
        //Number with e-notation
        assertNumber("9,999999999999998e+31", "99999999999999999 x² = +", "9,999999999999998e+31 + ");

    }

    @Test
    void checkMIXBinaryOperations() {
        //Add with
        assertNumber("1", "1 + 0,9999999999999999 - 0,9999999999999999 =", "");
        assertNumber("1 925 066 427,985", "34156,745 +/- +/- + 388656 x 45532 bs =", "");
        assertNumber("-1 556,844780280956", ",77765544 + 7785 +/- / 4,999999 =", "");
        assertNumber("9 999 998,0000001", "9999999 + 9999999 - 9999999 x 0,9999999 =", "");
        assertNumber("0,0617809442729949", "867,8333 + 3 x 4 / 56382 = bs bs bs bs bs bs bs", "");
        assertNumber("455 667 995,6", "2332, + 3 / 0,5 + 455663325,6 = +/- +/- +/- bs +/-", "negate(negate(negate(negate(455667995,6))))");
        //Subtract with
        assertNumber("19", "19 - 0,9999999999999999 + 0,9999999999999999 =", "");
        assertNumber("48 688 021 125,732", "27786, , 999 - 768492,000000 x 65732 +/-  =", "");
        assertNumber("631 210,0063121001", "887754 - 256544 / 0,99999999 =", "");
        assertNumber("-357 684,1186736475", "23 +/- 1 - 65 bs 43 x 2345 / 5,73 =", "");
        assertNumber("84,77860962566845", "43 - 87678 +/- bs ,4 / 748 + 73 =", "");
        assertNumber("-3 929", "2 - 3957 + 84 - 7 bs 58 =", "");
        //Multiply with
        assertNumber("2,099999998", "0,999999999 x 2 + 0,1 =", "");
        assertNumber("1,9", "0,9999999999999999 x 2 - 0,1 =", "");
        assertNumber("20", "0,9999999999999999 x 2 / 0,1 =", "");
        assertNumber("8 814", "233 x 38 + 4 - 44 =", "");
        assertNumber("-50 623 218 535 195,59", "97548354766 +/- , bs 452 x 3874 - 4 / 7465 =", "");
        assertNumber("88,54373407202216", "973 x 9 / 8664 + 87,533 =", "");
        //Divide with
        assertNumber("637 925,889675519", "288743 bs +/- , 00000001 / 88985455 + 637925,889999999 =", "");
        assertNumber("8,000000000000001", "9 / 0,9999999999999999 - 0,9999999999999999 =", "");
        assertNumber("1 000", "1000 / 2,9999999999999999647 x 3 =", "");
        assertNumber("-789,5563005780347", "972 / 865 + 85,32 - 876 =", "");
        assertNumber("4 055,773333333333", "7784 / 84 - ,49 x 44 =", "");
        assertNumber("35,8938327603227", "297 / 36 x 409 / 94,0064 =", "");
    }

    @Test
    void checkOneDivideX() {
        assertNumber("1", "1 1/x", "1/(1)");
        assertNumber("2", "2 1/x 1/x", "1/(1/(2))");
        assertNumber("-2", "2 +/- 1/x 1/x", "1/(1/(-2))");
        assertNumber("-2", "2 1/x +/- 1/x", "1/(negate(1/(2)))");
        assertNumber("-2", "2 1/x 1/x +/-", "negate(1/(1/(2)))");
        assertNumber("-2", "2 +/- 1/x +/- 1/x +/-", "negate(1/(negate(1/(-2))))");

        assertNumber("10", "0,1 1/x", "1/(0,1)");
        assertNumber("0,2", "0,2 1/x 1/x", "1/(1/(0,2))");
        assertNumber("-0,2", "0,2 +/- 1/x 1/x", "1/(1/(-0,2))");
        assertNumber("-0,2", "0,2 1/x +/- 1/x", "1/(negate(1/(0,2)))");
        assertNumber("-0,2", "0,2 1/x 1/x +/-", "negate(1/(1/(0,2)))");
        assertNumber("-0,2", "0,2 +/- 1/x +/- 1/x +/-", "negate(1/(negate(1/(-0,2))))");

        assertNumber("1", "1 1/x =", "");
        assertNumber("2", "2 1/x 1/x =", "");
        assertNumber("-2", "2 +/- 1/x 1/x =", "");
        assertNumber("-2", "2 1/x +/- 1/x =", "");
        assertNumber("-2", "2 1/x 1/x +/- =", "");
        assertNumber("-2", "2 +/- 1/x +/- 1/x +/- =", "");

        assertNumber("10", "0,1 1/x =", "");
        assertNumber("0,2", "0,2 1/x 1/x =", "");
        assertNumber("-0,2", "0,2 +/- 1/x 1/x =", "");
        assertNumber("-0,2", "0,2 1/x +/- 1/x =", "");
        assertNumber("-0,2", "0,2 1/x 1/x +/- =", "");
        assertNumber("-0,2", "0,2 +/- 1/x +/- 1/x +/- =", "");

        assertNumber("0,0017789321070561", "562,135 1/x", "1/(562,135)");
        assertNumber("9 009", "9009 1/x 1/x", "1/(1/(9009))");
        assertNumber("0,021799115915055", "45,87342 1/x 1/x 1/x", "1/(1/(1/(45,87342)))");
        assertNumber("1", "0,9999999999999999 1/x", "1/(0,9999999999999999)");
        assertNumber("1,e+16", "0,0000000000000001 1/x", "1/(0,0000000000000001)");
        assertNumber("-1", "0,9999999999999999 1/x 1/x 1/x +/-", "negate(1/(1/(1/(0,9999999999999999))))");
    }

    @Test
    void checkSquareRoot() {
        assertNumber("0", "0 √", "√(0)");

        assertNumber("1", "1 √", "√(1)");
        assertNumber("1,189207115002721", "2 √ √", "√(√(2))");
        assertNumber("-1,189207115002721", "2 √ √ +/-", "negate(√(√(2)))");

        assertNumber("0,3162277660168379", "0,1 √", "√(0,1)");
        assertNumber("0,668740304976422", "0,2 √ √", "√(√(0,2))");
        assertNumber("-0,668740304976422", "0,2 √ √ +/-", "negate(√(√(0,2)))");

        assertNumber("0", "0 √ =", "");
        assertNumber("1", "1 √ =", "");
        assertNumber("1,189207115002721", "2 √ √ =", "");
        assertNumber("-1,189207115002721", "2 √ √ +/- =", "");

        assertNumber("0,3162277660168379", "0,1 √ =", "");
        assertNumber("0,668740304976422", "0,2 √ √ =", "");
        assertNumber("-0,668740304976422", "0,2 √ √ +/- =", "");

        assertNumber("1,414213562373095", "2 √", "√(2)");
        assertNumber("1,189207115002721", "2 √ √", "√(√(2))");
        assertNumber("-7,071067811865475", "50 √ +/-", "negate(√(50))");
        assertNumber("1,613225729177976", "45,87342 √ √ √", "√(√(√(45,87342)))");
        assertNumber("0,00000001", "0,0000000000000001 √", "√(0,0000000000000001)");
        assertNumber("5,259663116753397", "765,3 √ +/- +/- √ ", "√(negate(negate(√(765,3))))");
    }

    @Test
    void checkSquareX() {
        assertNumber("0", "0 x²", "sqr(0)");
        assertNumber("1", "1 x²", "sqr(1)");
        assertNumber("4", "2 x²", "sqr(2)");
        assertNumber("16", "2 x² x²", "sqr(sqr(2))");
        assertNumber("16", "2 +/- x² x²", "sqr(sqr(-2))");
        assertNumber("16", "2 x² +/- x²", "sqr(negate(sqr(2)))");
        assertNumber("-16", "2 x² x² +/-", "negate(sqr(sqr(2)))");
        assertNumber("-16", "2 +/- x² +/- x² +/-", "negate(sqr(negate(sqr(-2))))");

        assertNumber("0,01", "0,1 x²", "sqr(0,1)");
        assertNumber("0,04", "0,2 x²", "sqr(0,2)");
        assertNumber("0,0016", "0,2 x² x²", "sqr(sqr(0,2))");
        assertNumber("0,0016", "0,2 +/- x² x²", "sqr(sqr(-0,2))");
        assertNumber("0,0016", "0,2 x² +/- x²", "sqr(negate(sqr(0,2)))");
        assertNumber("-0,0016", "0,2 x² x² +/-", "negate(sqr(sqr(0,2)))");
        assertNumber("-0,0016", "0,2 +/- x² +/- x² +/-", "negate(sqr(negate(sqr(-0,2))))");

        assertNumber("1", "1 x² =", "");
        assertNumber("4", "2 x² =", "");
        assertNumber("16", "2 x² x² =", "");
        assertNumber("16", "2 +/- x² x² =", "");
        assertNumber("16", "2 x² +/- x² =", "");
        assertNumber("-16", "2 x² x² +/- =", "");
        assertNumber("-16", "2 +/- x² +/- x² +/- =", "");

        assertNumber("0,01", "0,1 x² =", "");
        assertNumber("0,04", "0,2 x² =", "");
        assertNumber("0,0016", "0,2 x² x² =", "");
        assertNumber("0,0016", "0,2 +/- x² x² =", "");
        assertNumber("0,0016", "0,2 x² +/- x² =", "");
        assertNumber("-0,0016", "0,2 x² x² +/- =", "");
        assertNumber("-0,0016", "0,2 +/- x² +/- x² +/- =", "");

        assertNumber("547,56", "23,4 x²", "sqr(23,4)");
        assertNumber("952 217 706 900 625", "5555 x² x²", "sqr(sqr(5555))");
        assertNumber("19 610 512 980 404,22", "45,87342 x² x² x²", "sqr(sqr(sqr(45,87342)))");
        assertNumber("0,9999999999999998", "0,9999999999999999 +/- x²", "sqr(-0,9999999999999999)");
        assertNumber("1,e-32", "0,0000000000000001 x²", "sqr(0,0000000000000001)");
        assertNumber("-0,9999999999999992", "0,9999999999999999 x² x² x² +/-", "negate(sqr(sqr(sqr(0,9999999999999999))))");

    }

    @Test
    void checkMIXUnaryOperations() {
        assertNumber("15 678", "15678 √ x² ", "sqr(√(15678))");
        assertNumber("1,000020000300004e-10", "99999 x² 1/x", "1/(sqr(99999))");
        assertNumber("0,00108190582506", "854321 1/x √", "√(1/(854321))");
        assertNumber("-0,0010000005000004", "999999 √ 1/x  +/-", "negate(1/(√(999999)))");
        assertNumber("9 874 562", "9874562 x² √", "√(sqr(9874562))");
        assertNumber("1,00000020000003e-14", "9999999 1/x x²", "sqr(1/(9999999))");
        //three operation
        assertNumber("9,956168238447662", "96546784,32415 √ √ √ ", "√(√(√(96546784,32415)))");
        assertNumber("9 999,9999500005", "99999999,00001 √ √ x² ", "sqr(√(√(99999999,00001)))");
        assertNumber("0,0056425927787405", "986472913,876543 √ √ 1/x ", "1/(√(√(986472913,876543)))");
        assertNumber("31 622,77658587241", "999999999 √ x² √ ", "√(sqr(√(999999999)))");
        assertNumber("0,0032577870844877", "8877878787,65 √ 1/x √ ", "√(1/(√(8877878787,65)))");
        assertNumber("99 999,999995", "9999999999 x² √ √ ", "√(√(sqr(9999999999)))");
        assertNumber("0,0017837042774312", "98788998999,874 1/x √ √ ", "√(√(1/(98788998999,874)))");
        assertNumber("2,036161793239094e+84", "34562212456 x² x² x² ", "sqr(sqr(sqr(34562212456)))");
        assertNumber("9,9999999998e+21", "99999999999 x² x² √ +/- +/-", "negate(negate(√(sqr(sqr(99999999999)))))");
        assertNumber("2,596893369539549e-48", "787746478889,98787 x² x² 1/x ", "1/(sqr(sqr(787746478889,9878)))");
        assertNumber("9,99999999999537e+23", "999999999999,768565 x² √ x² ", "sqr(√(sqr(999999999999,7685)))");
        assertNumber("2,598079199785716e-52", "7876565765566 x² 1/x x² ", "sqr(1/(sqr(7876565765566)))");
        assertNumber("9,999999999999998e+25", "9999999999999,999999 √ x² x² ", "sqr(sqr(√(9999999999999,999)))");
        assertNumber("1,060361063588689e-56", "98545445454546 1/x x² x² ", "sqr(sqr(1/(98545445454546)))");
        assertNumber("1,00000000000001e-14", "99999999999999 1/x 1/x 1/x ", "1/(1/(1/(99999999999999)))");
        assertNumber("25 991 214,58828613", "675543235774338 1/x 1/x √ ", "√(1/(1/(675543235774338)))");
        assertNumber("9,99999999999998e+29", "999999999999999 1/x 1/x x² ", "sqr(1/(1/(999999999999999)))");
        assertNumber("8,100000065610006e-16", "1234567891234567 x² √ 1/x ", "1/(√(sqr(1234567891234567)))");
        assertNumber("1,173436304521581e+31", "3425545656565653 1/x x² 1/x ", "1/(sqr(1/(3425545656565653)))");
        assertNumber("6 654 638 824 545 454", "6654638824545454 x² = √", "√(4,42842178851477e+31)");
        assertNumber("-999 998 000 001,0002", "999999,0000000001 +/- x² 1/x 1/x +/-", "negate(1/(1/(sqr(-999999,0000000001))))");

    }

    @Test
    void checkOperationsEnotationValid() {
        //e-
        //if scale number more then 16 and count of zero more 2,
        assertNumber("1,1111111111111e-4", "0,0011111111111111 / 10 +", "0,0011111111111111 ÷ 10 + ");
        assertNumber("9,9999999999999e-4", "0,0099999999999999 / 10 +", "0,0099999999999999 ÷ 10 + ");
        assertNumber("1,111111111111111e-4", "0,1111111111111111 / 1000 -", "0,1111111111111111 ÷ 1000 - ");
        //number less 0,0000000000000001
        assertNumber("1,e-17", "0,0000000000000001 / 10 +", "0,0000000000000001 ÷ 10 + ");
        assertNumber("1,1e-16", "0,0000000000000011 / 10 +", "0,0000000000000011 ÷ 10 + ");
        assertNumber("9,e-17", "0,0000000000000009 / 10 +", "0,0000000000000009 ÷ 10 + ");
        assertNumber("1,e-30", "0,000000000000001 / 10000000000000000000 +", "0,000000000000001 ÷ 1000000000000000 + ");
        assertNumber("1,e-31", "0,0000000000000001 / 10000000000000000000 +", "0,0000000000000001 ÷ 1000000000000000 + ");
        assertNumber("1,111111111111111e-16", "0,1111111111111111 / 1000000000000000 +", "0,1111111111111111 ÷ 1000000000000000 + ");
        //e+
        assertNumber("1,e+16", "9999999999999999 + 1 +", "9999999999999999 + 1 + ");
        assertNumber("2,e+16", "9999999999999999 + =", "");
        assertNumber("1,000000000000001e+16", "9999999999999999 + 6 =", "");
        assertNumber("5,e+16", "9999999999999999 + = = = =", "");
        assertNumber("5,999999999999999e+16", "9999999999999999 + = = = = =", "");

        assertNumber("1,e-17", "0,0000000000000001 / 9,999999999999996 +", "0,0000000000000001 ÷ 9,999999999999996 + ");
        assertNumber("1,e-18", "0,0000000000000001 / 99,99999999999996 +", "0,0000000000000001 ÷ 99,99999999999996 + ");
        assertNumber("1,e-30", "0,000000000000001 / 999999999999999,9 +", "0,000000000000001 ÷ 999999999999999,9 + ");
        assertNumber("1,e-31", "0,0000000000000001 / 999999999999999,6 +", "0,0000000000000001 ÷ 999999999999999,6 + ");
        assertNumber("1,111111111111111e-16", "0,1111111111111111 / 999999999999999,6 +", "0,1111111111111111 ÷ 999999999999999,6 + ");
        assertNumber("1,1111111111111e-4", "0,0011111111111111 / 9,999999999999996 +", "0,0011111111111111 ÷ 9,999999999999996 + ");
        assertNumber("1,1111111111111e-5", "0,0011111111111111 / 99,99999999999996 +", "0,0011111111111111 ÷ 99,99999999999996 + ");
        assertNumber("1,e+16", "9999999999999999 + 0,9999999999999996 +", "9999999999999999 + 0,9999999999999996 + ");

    }

    @Test
    void checkOperationsEnotationInvalid() {
        //e-
        assertNumber("0,01", "0,1 / 10 =", "");
        assertNumber("0,0000000000000001", "0,000000000000001 / 10 =", "");
        assertNumber("0,0111111111111111", "0,1111111111111111 / 10 =", "");
        assertNumber("0,1", "0,9999999999999999 / 10 =", "");
        assertNumber("0,0011111111111111", "0,1111111111111111 / 100 =", "");
        assertNumber("0,01", "0,9999999999999999 / 100 =", "");
        assertNumber("0,0000000000000001", "0,0000000000000001 / 1 =", "");
        assertNumber("0,000000000000001", "0,0000000000000001 x 10 =", "");
        assertNumber("-0,0000000000000001", "0,0000000000000001 +/- / 1 =", "");
        assertNumber("-0,000000000000001", "0,0000000000000001 x 10 +/- =", "");
        assertNumber("0,000000000000001", "1 / 1000000000000000 =", "");
        //e+
        assertNumber("9 999 999 999 999 998", "9999999999999999 - 1 =", "");
        assertNumber("-9 999 999 999 999 998", "9999999999999999 +/- + 1 =", "");
        assertNumber("9 999 999 999 999 998", "9999999999999999 + 1 +/- =", "");
        assertNumber("9 999 999 999 999 999", "9999999999999998 + 1 =", "");
        assertNumber("9 999 999 999 999 997", "9999999999999998 - 1 =", "");
        assertNumber("-9 999 999 999 999 997", "9999999999999998 +/- + 1 =", "");
        assertNumber("-9 999 999 999 999 999", "9999999999999998 +/- - 1 =", "");
    }

    @Test
    void checkExceptions() {
        //Divide zero
        assertNumber("Result is undefined", "0 / 0 =", "0 ÷ ");
        assertNumber("Cannot divide by zero", "1 / 0 =", "1 ÷ ");
        assertNumber("Cannot divide by zero", "0 1/x", "1/(0)");
        assertNumber("Cannot divide by zero", "0 √ 1/x", "1/(√(0))");
        assertNumber("Cannot divide by zero", "2 + 4 - 1 + 3 / 0 =", "2 + 4 - 1 + 3 ÷ ");
        assertNumber("Cannot divide by zero", "4 + 5 - 9 + 1/x ", "4 + 5 - 9 + 1/(0)");
        //Square root negative number
        assertNumber("Invalid input", "1 +/- √", "√(-1)");
        assertNumber("Invalid input", "2 - 3 = √", "√(-1)");
        assertNumber("Invalid input", "2 + 3 = +/- √", "√(negate(5))");
        //Overflow
        assertNumber("Overflow", "9999999999999999 x²  x²  x²  x²  x²  x²  x²  x²  x²  x²   x² ", "sqr(sqr(sqr(sqr(sqr(sqr(sqr(sqr(sqr(sqr(9999999999999999))))))))))");
        assertNumber("Overflow", "0,0000000000000001 x²  x² x²  x²  x²  x²  x²  x²  x² x²", "sqr(sqr(sqr(sqr(sqr(sqr(sqr(sqr(sqr(sqr(0,0000000000000001))))))))))");
        assertNumber("Overflow", "9999999999999999 / 1000000000000000 = = = x²  x²  x²  x²  x²  x²  x²  x² x²", "sqr(sqr(sqr(sqr(sqr(sqr(sqr(sqr(sqr(9,999999999999999e-30)))))))))");
        assertNumber("Overflow", "0,0000000000000001 x² x ,0000001 = x² x² x² x² x² x² x² x² x 0,0000000000000001 =", "sqr(sqr(sqr(sqr(sqr(sqr(sqr(sqr(1,e-39)))))))) x ");
        assertNumber("Overflow", "1000000000000000 x² x 1000000000 = x² x² x² x² x² x² x² x² MS 9999999999999999 x MR = x 10 = MC", "9,999999999999999e+9999 x ");
    }

    @Test
    void checkPercent() {
        assertNumber("0", "8 %", "0");
        assertNumber("0", "0 + 1 %", "0 + 0");
        assertNumber("0", "0 - 1 %", "0 - 0");
        assertNumber("0,01", "0 x 1 %", "0 x 0,01");
        assertNumber("0,01", "0 / 1 %", "0 ÷ 0,01");
        assertNumber("0,81", "2 + 7 = %", "0,81");
        assertNumber("0,01", "3 - 4 = %", "0,01");
        assertNumber("0,0071428571428571", "5 / 7 = %", "0,0071428571428571");
        assertNumber("0,54", "6 x 9 = %", "0,54");
        assertNumber("-14,68", "9 - 11 = 734 %", "-14,68");
        assertNumber("8 999,91", "2 + 7 = 99999 %", "8999,91");
        assertNumber("-2,56", "32 + 8 +/- % ", "32 + -2,56");
        assertNumber("0", "8 sqr %", "0");
        assertNumber("-63,36", "12 x² - % =", "");
        assertNumber("8,19", "9 - % +", "9 - 0,81 + ");
        assertNumber("-4,75", "3 - 8 + % x", "3 - 8 + 0,25 x ");
        assertNumber("-28 638,844416", "984 - 356 % + 876 - % - ", "984 - 3503,04 + 876 - 26995,804416 - ");
        assertNumber("-17,3578685327433", "284 √ + 3 % = +/-", "negate(17,3578685327433)");
        assertNumber("-234,21", "100 + 88 - 11 + 34 - % +", "100 + 88 - 11 + 34 - 445,21 + ");
        assertNumber("1,03122e-13", "0,0000000000001011 + 2 % - ", "0,0000000000001011 + 2,022e-15 - ");
        assertNumber("-3,08641975312477e+29", "5555555555555555 + 34566 - % +", "5555555555555555 + 34566 - 3,086419753124826e+29 + ");
        assertNumber("9,999999999999999e+28", "9999999999999999 + 1000000000000000 %", "9999999999999999 + 9,999999999999999e+28");
        assertNumber("-9,776196932024121e+17", "987987 + 664 - 3,3335 + 999999,999999 % - % +", "987987 + 664 - 3,3335 + 9886476664,990114 - 9,776197030898774e+17 + ");
    }

    @Test
    void checkMemory() {
        //M+
        assertNumber("5", "5 M+ 88 MR MC", "");
        assertNumber("100", "12 M+ 88 M+ MR MC", "");
        assertNumber("188", "12 M+ 88 M+ M+ MR MC", "");
        assertNumber("376", "12 M+ 88 M+ M+ MR M+ MR MC", "");
        //M-
        assertNumber("-5", "5 M- 88 MR MC", "");
        assertNumber("-100", "12 M- 88 M- MR MC", "");
        assertNumber("-188", "12 M- 88 M- M- MR MC", "");
        assertNumber("0", "12 M- 88 M- M- MR M- MR MC", "");
        //Mix
        assertNumber("7", "7 M+ M- MC", "");
        assertNumber("0", "24 M- M+ MR", "");
        assertNumber("256", "M- 256 M+ 876 MR MC", "");
        assertNumber("776", "M+ 776 M- MC", "");
        assertNumber("95", "67 M+ 95 M- MS MR MC", "");
        assertNumber("18 693", "6231 M+ M+ M+ 56 MR MC", "");
        assertNumber("99 640", "98765 M- M- M- MC MS 875 M+ MR ", "");
        assertNumber("99 640,75136", "0,88776 M+ 0,1364 M- = MR = MC", "");
        assertNumber("2", "1 M+ 0,9999999999999999 M+ 0 MR MC", "");
        assertNumber("29 999 996,99981057", "9999999 M+ M+ M+ 0,00018943 M- 898 MR MC", "");
        assertNumber("99 999 999", "MS 9,9999999999999911 M- M+ 99999999 M+ 99999999 MR MC", "");
        assertNumber("9 999 999 999 999 999", "9999999999999999 MS 9999999999999999 M- M+ 00000000000001 MR MC", "");
        assertNumber("1,8e+18", "9999999999999999 M+ M+ M+ M+ M+ MR M+ M+ M+ M+ M+  MR M+ M+ M+ M+ M+ MR MC", "");

    }

    @Test
    void checkAllOperations() {
        //Unary with binary
        //Square with binary
        assertNumber("28", "5 x² + 3 -", "sqr(5) + 3 - ");
        assertNumber("22", "5 x² - 3 x", "sqr(5) - 3 x ");
        assertNumber("75", "5 x² x 3 /", "sqr(5) x 3 ÷ ");
        assertNumber("8,333333333333333", "5 x² / 3 +", "sqr(5) ÷ 3 + ");
        //Square root with binary
        assertNumber("5,23606797749979", "5 √ + 3 -", "√(5) + 3 - ");
        assertNumber("-0,7639320225002103", "5 √ - 3 x", "√(5) - 3 x ");
        assertNumber("6,708203932499369", "5 √ x 3 /", "√(5) x 3 ÷ ");
        assertNumber("0,7453559924999299", "5 √ / 3 +", "√(5) ÷ 3 + ");
        //One divide x with binary
        assertNumber("3,2", "5 1/x + 3 -", "1/(5) + 3 - ");
        assertNumber("-2,8", "5 1/x - 3 x", "1/(5) - 3 x ");
        assertNumber("0,6", "5 1/x x 3 /", "1/(5) x 3 ÷ ");
        assertNumber("0,0666666666666667", "5 1/x / 3 +", "1/(5) ÷ 3 + ");
        //Binary with unary
        //Add with unary
        assertNumber("14", "5 + 3 x² -", "5 + sqr(3) - ");
        assertNumber("6,732050807568877", "5 + 3 √ -", "5 + √(3) - ");
        assertNumber("5,333333333333333", "5 + 3 1/x -", "5 + 1/(3) - ");
        //Subtract with unary
        assertNumber("-4", "5 - 3 x² -", "5 - sqr(3) - ");
        assertNumber("3,267949192431123", "5 - 3 √ -", "5 - √(3) - ");
        assertNumber("4,666666666666667", "5 - 3 1/x -", "5 - 1/(3) - ");
        //Multiply with unary
        assertNumber("45", "5 x 3 x² -", "5 x sqr(3) - ");
        assertNumber("8,660254037844386", "5 x 3 √ -", "5 x √(3) - ");
        assertNumber("1,666666666666667", "5 x 3 1/x -", "5 x 1/(3) - ");
        //Divide with unary
        assertNumber("0,5555555555555556", "5 / 3 x² -", "5 ÷ sqr(3) - ");
        assertNumber("2,886751345948129", "5 / 3 √ -", "5 ÷ √(3) - ");
        assertNumber("15", "5 / 3 1/x -", "5 ÷ 1/(3) - ");

        //Mix all operations
        assertNumber("5", "2 MS 3 + MR - MC", "3 + 2 - ");
        assertNumber("-2,23606797749979", "5 √ +/- -", "negate(√(5)) - ");
        assertNumber("2 906 811,3127248", "878,756 + 45,63 % +/- x 78 x² =", "");
        assertNumber("2,009861478780531", ",883655 x 0,74553 % + 0,99999 + √ =", "");
        assertNumber("7,666666666666667", "3 1/x +/- + 8 -", "negate(1/(3)) + 8 - ");
        assertNumber("6 639 379 852 195,999", "985948894 - 7453628 1/x x 6734 = ", "");
        assertNumber("-59", "5 + 8 x² +/- +/- +/- -", "5 + negate(negate(negate(sqr(8)))) - ");
        assertNumber("2 298 455 479 608,324", "4376 + 7987 - % + 8,9 = x²", "sqr(-1516065,79)");
        assertNumber("59,02888304444444", "56 x % MS 789 % M+ / 0,55 bs bs 89 M- MR + 0,99999 % = ", "");
        assertNumber("-36,799632", "2 x² + 6 x² - 8 % x 0,999999 bs +/- /", "sqr(2) + sqr(6) - 3,2 x -0,99999 ÷ ");
        assertNumber("1 070 281,69789316", "675 x² + 784 x² + ,8354 x² - ", "sqr(675) + sqr(784) + sqr(0,8354) - ");
        assertNumber("-1,628217149182808e-8", "4567 x² + 78956,00009 x 64785 √ +/- = 1/x M+ x 7536 M- MR +/- √ MC =", "");
        assertNumber("1,e-9999", "0,0000000000000001 x² x ,0000001 = x² x² x² x² x² x² x² x² x 0,000000000000001 =", "");
        assertNumber("0,0178571428571429", "87 + 324 - 443 / ,56 +/- x² √ 1/x %", "87 + 324 - 443 ÷ 0,0178571428571429");
        assertNumber("9,999999999999999e+9999", "1000000000000000 x² x 1000000000 = x² x² x² x² x² x² x² x² MS 9999999999999999 x MR = MC", "");


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
        FXTestUtils.awaitEvents();
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
        } else if (idButton.equals("x")) {
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
        } else if (idButton.equals("x")) {
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
        Button button = null;
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
            button = from(root).lookup("#ADD").query();
        } else if (idButtonClickedMouse.equals("-")) {
            button = from(root).lookup("#SUBTRACT").query();
        } else if (idButtonClickedMouse.equals("x")) {
            button = from(root).lookup("#MULTIPLY").query();
        } else if (idButtonClickedMouse.equals("√")) {
            button = from(root).lookup("#SQRT").query();
        } else if (idButtonClickedMouse.equals("%")) {
            button = from(root).lookup("#PERCENT").query();
        } else if (idButtonClickedMouse.equals("1/x")) {
            button = from(root).lookup("#ONE_DIVIDE_X").query();
        } else if (idButtonClickedMouse.equals("x²")) {
            button = from(root).lookup("#SQR").query();
        } else if (idButtonClickedMouse.equals("/")) {
            button = from(root).lookup("#DIVIDE").query();
        } else if (idButtonClickedMouse.equals("=")) {
            button = from(root).lookup("#EQUAL").query();
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