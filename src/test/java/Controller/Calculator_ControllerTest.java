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

    @BeforeEach
    void outMemory() {
        outOperationMemory = from(root).lookup("#outOperationMemory").query();
        outLabel = from(root).lookup("#outText").query();
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
        assertNumber("-2 222 222 222 222 222", "222222222222222222 ", "");
        assertNumber("2 222 222 222 222 222", "222222222222222222  ", "");
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
        assertNumber("-1 234 567 891 234 567,", "1234567891234567,89 ", "");
        assertNumber("1 234 567 891 234 567,", "1234567891234567,89  ", "");
        assertNumber("1 234 567 891 234 567,", "1234567891234567,89  ", "");
        assertNumber("-222,3333333333333", "222,3333333333333  44", "");
        assertNumber("222,3333333333333", "222,3333333333333   33,", "");
    }

    @Test
    void checkBackspace() {
        //zero
        assertNumber("0", " ", "");
        assertNumber("0", "5 ", "");
        assertNumber("0", "01 ", "");
        assertNumber("0", "8  ", "");
        assertNumber("1 111", "11111 ", "");
        assertNumber("-44", "444  ", "");
        assertNumber("88 888 888", "8888888888  ", "");
        assertNumber("0", "7838765,89           ", "");
        //comma 
        assertNumber("234 567", "234567, ", "");
        assertNumber("234 567", "234567,8  ", "");
        assertNumber("-678 395", "678395,  ", "");
        //in operation
        assertNumber("0", "679 + 67  ", "679 + ");
        assertNumber("9", "3  + 987  ", "1/(3) + ");
        assertNumber("0,00", "3  - ,0000  ", "sqr(3) - ");
        assertNumber("7", "2456 x 2 - 789  ", "2456 x 2 - ");
        //Don`t backspace result
        assertNumber("7 396", "86   ", "sqr(86)");
        assertNumber("334", "347 - 13 -  ", "347 - 13 - ");
        assertNumber("299,422778024652", "89654 √ =   ", "");
        assertNumber("435 611 087", "435576762 + 34325 =   ", "");
        assertNumber("9,999999999999996e+63", "9999999999999999       =   ", "");
        assertNumber("0,0000000000000001", "2,555555  1  x 0,0000000000000000  1", "sqr(2,555551) x ");
        assertNumber("0,0000000000000001", ",0000000000000000  1 x 2 / 0,0000000000000000  1", "0,0000000000000001 x 2 ÷ ");
    }

    @Test
    void checkAdd() {
        assertNumber("0 + 2 +", "2", "0 + 2 + ");
        assertNumber("2 + 0 +", "2", "2 + 0 + ");
        assertNumber("0 + 2  +", "-2", "0 + -2 + ");
        assertNumber("2  + 0 +", "-2", "-2 + 0 + ");
        assertNumber("2 + 3 +", "5", "2 + 3 + ");
        assertNumber("2 + 3  +", "-1", "2 + -3 + ");
        assertNumber("2  + 3 +", "1", "-2 + 3 + ");
        assertNumber("2  + 3  +", "-5", "-2 + -3 + ");
        assertNumber("0 + 2 =", "2", "");
        assertNumber("2 + 0 =", "2", "");
        assertNumber("0 + 2  =", "-2", "");
        assertNumber("2  + 0 =", "-2", "");
        assertNumber("2 + 3 =", "5", "");
        assertNumber("2 + 3  =", "-1", "");
        assertNumber("2  + 3 =", "1", "");
        assertNumber("2  + 3  =", "-5", "");
        assertNumber("0 + ,2 +", "0,2", "0 + 0,2 + ");
        assertNumber("0 + ,2  +", "-0,2", "0 + -0,2 + ");
        assertNumber(",2  + 0 +", "-0,2", "-0,2 + 0 + ");
        assertNumber("2 + 0,3 +", "2,3", "2 + 0,3 + ");
        assertNumber("0,2 + 3 +", "3,2", "0,2 + 3 + ");
        assertNumber("0,2 + 0,3 +", "0,5", "0,2 + 0,3 + ");
        assertNumber("2 + 0,3  +", "1,7", "2 + -0,3 + ");
        assertNumber("0,2 + 3  +", "-2,8", "0,2 + -3 + ");
        assertNumber("0,2 + 0,3  +", "-0,1", "0,2 + -0,3 + ");
        assertNumber("2  + 0,3 +", "-1,7", "-2 + 0,3 + ");
        assertNumber("0,2  + 3 +", "2,8", "-0,2 + 3 + ");
        assertNumber("0,2  + 0,3 +", "0,1", "-0,2 + 0,3 + ");
        assertNumber("2  + 0,3  +", "-2,3", "-2 + -0,3 + ");
        assertNumber("0,2  + 3  +", "-3,2", "-0,2 + -3 + ");
        assertNumber("0,2  + 0,3  +", "-0,5", "-0,2 + -0,3 + ");
        assertNumber("0 + ,2 =", "0,2", "");
        assertNumber("0 + ,2  =", "-0,2", "");
        assertNumber(",2  + 0 =", "-0,2", "");
        assertNumber("2 + 0,3 =", "2,3", "");
        assertNumber("0,2 + 3 =", "3,2", "");
        assertNumber("0,2 + 0,3 =", "0,5", "");
        assertNumber("2 + 0,3  =", "1,7", "");
        assertNumber("0,2 + 3  =", "-2,8", "");
        assertNumber("0,2 + 0,3  =", "-0,1", "");
        assertNumber("2  + 0,3 =", "-1,7", "");
        assertNumber("0,2  + 3 =", "2,8", "");
        assertNumber("0,2  + 0,3 =", "0,1", "");
        assertNumber("2  + 0,3  =", "-2,3", "");
        assertNumber("0,2  + 3  =", "-3,2", "");
        assertNumber("0,2  + 0,3  =", "-0,5", "");
        assertNumber("9999999999999999 + 0 =", "9 999 999 999 999 999", "");
        assertNumber("9999999999999999 + 0 =", "-9 999 999 999 999 999", "");
        assertNumber("9999999999999999 + 1 =", "1,e+16", "");
        assertNumber("9999999999999999 + 1  =", "9 999 999 999 999 998", "");
        assertNumber("9999999999999999 + 1 =", "-9 999 999 999 999 998", "");
        assertNumber("9999999999999999 + 1 =", "-1,e+16", "");
        assertNumber("9999999999999999 + 0,1 =", "9 999 999 999 999 999", "");
        assertNumber("9999999999999999 + 0,1  =", "9 999 999 999 999 999", "");
        assertNumber("9999999999999999 + 0,1 =", "-9 999 999 999 999 999", "");
        assertNumber("9999999999999999 + 0,1 =", "-9 999 999 999 999 999", "");
        assertNumber("0,0000000000000001 + 0 =", "0,0000000000000001", "");
        assertNumber("0,0000000000000001 + 0 =", "-0,0000000000000001", "");
        assertNumber("0,0000000000000001 + 1 =", "1", "");
        assertNumber("0,0000000000000001 + 1  =", "-0,9999999999999999", "");
        assertNumber("0,0000000000000001 + 1 =", "0,9999999999999999", "");
        assertNumber("0,0000000000000001 + 1 =", "-1", "");
        assertNumber("0,0000000000000001 + 0,1 =", "0,1000000000000001", "");
        assertNumber("0,0000000000000001 + 0,1  =", "-0,0999999999999999", "");
        assertNumber("0,0000000000000001 + 0,1 =", "0,0999999999999999", "");
        assertNumber("0,0000000000000001 + 0,1 =", "-0,1000000000000001", "");
        assertNumber("9999999999999999 + 0,0000000000000001 =", "9 999 999 999 999 999", "");
        assertNumber("9999999999999999 + 0,0000000000000001 =", "9 999 999 999 999 999", "");
        assertNumber("9999999999999999 + 0,0000000000000001 =", "-9 999 999 999 999 999", "");
        assertNumber("9999999999999999 + 0,0000000000000001 =", "-9 999 999 999 999 999", "");
        assertNumber("19 + 0,999 + =", "39,998", "");
        assertNumber("9999999999999998 + 1 =", "9 999 999 999 999 999", "");
        assertNumber("6557 + 66777 + 7575656565656 =", "7 575 656 638 990", "");
        assertNumber("9999999999999998  + 1 =", "-9 999 999 999 999 997", "");
        assertNumber("9999999999999999  + 1 =", "-9 999 999 999 999 998", "");
        assertNumber(",734451643 + 999,99999 + 33,7478 =", "966,986641643", "");
        assertNumber("1 + 0,9999999999999999 -", "2", "1 + 0,9999999999999999 - ");
        assertNumber("00032456 + 71223234 + 63543775,46344 =", "134 799 465,46344", "");
        assertNumber("645634 + 3241 + 8893  + ", "-642 305", "-645634 + 3241 + 88 + ");
        assertNumber("9999999999999999 + 25,7485 + ,63783 =", "9 999 999 999 999 974", "");
        assertNumber("45 + 975,43467 + 7465,134+", "8 485,56867", "45 + 975,43467 + 7465,134 + ");
        assertNumber("9999999999999999 + 999999999999999,9 + 99999999999999,99 =", "1,11e+16", "");
        assertNumber("9999999999999999 + 999999999999999,9 + 99999999999999,99 +=", "2,22e+16", "");
        assertNumber("199999 + 0,999999999999999999999 + 24,2 + 0,00000000000001 =", "200 024,2", "");
        assertNumber("2543648,9 + 71122444653354,43 + 999289985564,9966 =", "72 121 732 095 271,43", "");
        assertNumber("6453,7546 + 998993,2133 + 09746 +", "1 015 192,9679", "6453,7546 + 998993,2133 + 9746 + ");
        assertNumber(",283555656456454 + 41536,47786 + 536565243543452,9 + 254566541,13232 =", "536 565 498 151 530,8", "");
        assertNumber("91225536 + 77535 + 35555555366655 + 773888,2444456372 + 9341566,993676 +=+=", "142 222 627 140 725", "");
        assertNumber("123456789 + 42565476,25465465 + 32354556133,999 + 173734556 + 441224,355467 +=", "7 151 308 359,218243", "");
        assertNumber("889499999,9999 + 7,9999999 + 755534,99 +", "890 255 526,989901", "889499999,9999 + -7,999999 + 755534,99 + ");
        assertNumber("763645455243 + 87487868 + 878787878 + 231432 + 1332443 + 12334566 = + 0,555555555555555 =", "764 450 653 694,5556", "");
        assertNumber("0,8993654377 + 345278,996132 + 637784534,9999 +", "638 129 814,8953974", "0,8993654377 + 345278,996132 + 637784534,9999 + ");
        assertNumber("9999999999999999 + 99273555,53445 + 7687929,1213436 + 8888292995564725 + 3786661827488 + =", "2,001020348139744e+16", "");

    }

    @Test
    void checkSubtract() {
        //Integer
        assertNumber("-2", "0 - 2 -", "0 - 2 - ");
        assertNumber("2", "2 - 0 -", "2 - 0 - ");
        assertNumber("2", "0 - 2  -", "0 - -2 - ");
        assertNumber("-2", "2  - 0 -", "-2 - 0 - ");

        assertNumber("-1", "2 - 3 -", "2 - 3 - ");
        assertNumber("5", "2 - 3  -", "2 - -3 - ");
        assertNumber("-5", "2  - 3 -", "-2 - 3 - ");
        assertNumber("1", "2  - 3  -", "-2 - -3 - ");

        //with equal
        assertNumber("-2", "0 - 2 =", "");
        assertNumber("2", "2 - 0 =", "");
        assertNumber("2", "0 - 2  =", "");
        assertNumber("-2", "2  - 0 =", "");

        assertNumber("-1", "2 - 3 =", "");
        assertNumber("5", "2 - 3  =", "");
        assertNumber("-5", "2  - 3 =", "");
        assertNumber("1", "2  - 3  =", "");

        //Decimal
        assertNumber("-0,2", "0 - ,2 -", "0 - 0,2 - ");
        assertNumber("0,2", "0 - ,2  -", "0 - -0,2 - ");
        assertNumber("-0,2", ",2  - 0 -", "-0,2 - 0 - ");

        assertNumber("1,7", "2 - 0,3 -", "2 - 0,3 - ");
        assertNumber("-2,8", "0,2 - 3 -", "0,2 - 3 - ");
        assertNumber("-0,1", "0,2 - 0,3 -", "0,2 - 0,3 - ");

        assertNumber("2,3", "2 - 0,3  -", "2 - -0,3 - ");
        assertNumber("3,2", "0,2 - 3  -", "0,2 - -3 - ");
        assertNumber("0,5", "0,2 - 0,3  -", "0,2 - -0,3 - ");

        assertNumber("-2,3", "2  - 0,3 -", "-2 - 0,3 - ");
        assertNumber("-3,2", "0,2  - 3 -", "-0,2 - 3 - ");
        assertNumber("-0,5", "0,2  - 0,3 -", "-0,2 - 0,3 - ");

        assertNumber("-1,7", "2  - 0,3  -", "-2 - -0,3 - ");
        assertNumber("2,8", "0,2  - 3  -", "-0,2 - -3 - ");
        assertNumber("0,1", "0,2  - 0,3  -", "-0,2 - -0,3 - ");

        //with equal
        assertNumber("-0,2", "0 - ,2 =", "");
        assertNumber("0,2", "0 - ,2  =", "");
        assertNumber("-0,2", ",2  - 0 =", "");

        assertNumber("1,7", "2 - 0,3 =", "");
        assertNumber("-2,8", "0,2 - 3 =", "");
        assertNumber("-0,1", "0,2 - 0,3 =", "");

        assertNumber("2,3", "2 - 0,3  =", "");
        assertNumber("3,2", "0,2 - 3  =", "");
        assertNumber("0,5", "0,2 - 0,3  =", "");

        assertNumber("-2,3", "2  - 0,3 =", "");
        assertNumber("-3,2", "0,2  - 3 =", "");
        assertNumber("-0,5", "0,2  - 0,3 =", "");

        assertNumber("-1,7", "2  - 0,3  =", "");
        assertNumber("2,8", "0,2  - 3  =", "");
        assertNumber("0,1", "0,2  - 0,3  =", "");

        //Complicate tests
        assertNumber("18,001", "19 - 0,999 =", "");
        assertNumber("9 999 999 999 999 997", "9999999999999998 - 1 =", "");
        assertNumber("9 999 999 999 999 998", "9999999999999999 - 1 =", "");
        assertNumber("-9 999 999 999 999 999", "9999999999999998  - 1 =", "");
        assertNumber("-7 575 656 625 876", "6557 - 66777 - 7575656565656 =", "");
        assertNumber("-965,517738357", ",734451643 - 999,99999 - 33,7478 =", "");
        assertNumber("230,01476", "687 - 456 - ,98524 -", "687 - 456 - 0,98524 - ");
        assertNumber("-134 734 553,46344", "00032456 - 71223234 - 63543775,46344 =", "");
        assertNumber("-648 963", "645634 - 3241 - 8893  - ", "-645634 - 3241 - 88 - ");
        assertNumber("1,000000000000002e+16", "9999999999999999 - 25,7485 - ,63783 =", "");
        assertNumber("-8 395,56867", "45 - 975,43467 - 7465,134-", "45 - 975,43467 - 7465,134 - ");
        assertNumber("22,9", "24 - 0,9999999999999999 - 0,1 -", "24 - 0,9999999999999999 - 0,1 - ");
        assertNumber("0,0000000000000001", "1 - 0,9999999999999999 -", "1 - 0,9999999999999999 - ");
        assertNumber("199 973,8", "199999 - 0,999999999999999999999 - 24,2 - 0,00000000000001 =", "");
        assertNumber("-72 121 737 182 567,43", "2543648,9 - 71122444653354,43 - 999289985564,9966 =", "");
        assertNumber("45 565,7412036", "45623 - 67,23445 - 9,9756536  -", "45623 - 67,23445 - -9,9756536 - ");
        assertNumber("8 899 999 999 999 999", "9999999999999999 - 999999999999999,9 - 99999999999999,99 =", "");
        assertNumber("-1 002 285,4587", "6453,7546 - 998993,2133 - 09746 -", "6453,7546 - 998993,2133 - 9746 - ");
        assertNumber("-536 565 498 151 530,2", ",283555656456454 - 41536,47786 - 536565243543452,9 - 254566541,13232 =", "");
        assertNumber("35 555 474 334 109,24", "91225536 - 77535 - 35555555366655 - 773888,2444456372 - 9341566,993676 -==", "");
        assertNumber("6 657 481 203,218243", "123456789 - 42565476,25465465 - 32354556133,999 - 173734556 - 441224,355467 -===", "");
        assertNumber("888 744 473,009899", "889499999,9999 - 7,9999999 - 755534,99 -", "889499999,9999 - -7,999999 - 755534,99 - ");
        assertNumber("762 840 256 791,4444", "763645455243 - 87487868 - 878787878 - 231432 - 1332443 - 12334566 = - 0,555555555555555 =", "");
        assertNumber("-638 129 813,0966666", "0,8993654377 - 345278,996132 - 637784534,9999 -", "0,8993654377 - 345278,996132 - 637784534,9999 - ");
        assertNumber("9 991 111 595 267 635", "9999999999999999 - 99273555,53445 - 7687929,1213436 - 8888292995564725 - 378666 - 1827488 =", "");

    }

    @Test
    void checkMultiply() {
        //Integer
        assertNumber("0", "0 x 2 x", "0 x 2 x ");
        assertNumber("0", "2 x 0 x", "2 x 0 x ");
        assertNumber("0", "0 x 2  x", "0 x -2 x ");
        assertNumber("0", "2  x 0 x", "-2 x 0 x ");

        assertNumber("6", "2 x 3 x", "2 x 3 x ");
        assertNumber("-6", "2 x 3  x", "2 x -3 x ");
        assertNumber("-6", "2  x 3 x", "-2 x 3 x ");
        assertNumber("6", "2  x 3  x", "-2 x -3 x ");

        //with equal
        assertNumber("0", "0 x 2 =", "");
        assertNumber("0", "2 x 0 =", "");
        assertNumber("0", "0 x 2  =", "");
        assertNumber("0", "2  x 0 =", "");

        assertNumber("6", "2 x 3 =", "");
        assertNumber("-6", "2 x 3  =", "");
        assertNumber("-6", "2  x 3 =", "");
        assertNumber("6", "2  x 3  =", "");

        //Decimal
        assertNumber("0", "0 x ,2 x", "0 x 0,2 x ");
        assertNumber("0", "0 x ,2  x", "0 x -0,2 x ");
        assertNumber("0", ",2 x 0 x", "0,2 x 0 x ");
        assertNumber("0", ",2  x 0 x", "-0,2 x 0 x ");

        assertNumber("0,6", "2 x 0,3 x", "2 x 0,3 x ");
        assertNumber("0,6", "0,2 x 3 x", "0,2 x 3 x ");
        assertNumber("0,06", "0,2 x 0,3 x", "0,2 x 0,3 x ");

        assertNumber("-0,6", "2 x 0,3  x", "2 x -0,3 x ");
        assertNumber("-0,6", "0,2 x 3  x", "0,2 x -3 x ");
        assertNumber("-0,06", "0,2 x 0,3  x", "0,2 x -0,3 x ");

        assertNumber("-0,6", "2  x 0,3 x", "-2 x 0,3 x ");
        assertNumber("-0,6", "0,2  x 3 x", "-0,2 x 3 x ");
        assertNumber("-0,06", "0,2  x 0,3 x", "-0,2 x 0,3 x ");

        assertNumber("0,6", "2  x 0,3  x", "-2 x -0,3 x ");
        assertNumber("0,6", "0,2  x 3  x", "-0,2 x -3 x ");
        assertNumber("0,06", "0,2  x 0,3  x", "-0,2 x -0,3 x ");

        //with equal
        assertNumber("0", "0 x ,2 =", "");
        assertNumber("0", "0 x ,2  =", "");
        assertNumber("0", ",2 x 0 =", "");
        assertNumber("0", ",2  x 0 =", "");

        assertNumber("0,6", "2 x 0,3 =", "");
        assertNumber("0,6", "0,2 x 3 =", "");
        assertNumber("0,06", "0,2 x 0,3 =", "");

        assertNumber("-0,6", "2 x 0,3  =", "");
        assertNumber("-0,6", "0,2 x 3  =", "");
        assertNumber("-0,06", "0,2 x 0,3  =", "");

        assertNumber("-0,6", "2  x 0,3 =", "");
        assertNumber("-0,6", "0,2  x 3 =", "");
        assertNumber("-0,06", "0,2  x 0,3 =", "");

        assertNumber("0,6", "2  x 0,3  =", "");
        assertNumber("0,6", "0,2  x 3  =", "");
        assertNumber("0,06", "0,2  x 0,3  =", "");

        //Complicate tests
        assertNumber("18,981", "19 x 0,999 =", "");
        assertNumber("56", "7 - + / x 8 x", "7 x 8 x ");
        assertNumber("10", "5 x 1 x 2 x", "5 x 1 x 2 x ");
        assertNumber("80", "5 x 1 x 2 x 8 x", "5 x 1 x 2 x 8 x ");
        assertNumber("0,000000000000001", "0,0000000000000001 x 10 =", "");
        assertNumber("-0,000000000000001", "0,0000000000000001 x 10  =", "");
        assertNumber("3,317052658404904e+21", "6557 x 66777 x 7575656565656 =", "");
        assertNumber("308 648,10528", "687 x 456 x ,98524 x", "687 x 456 x 0,98524 x ");
        assertNumber("-24 786,12690977413", ",734451643 x 999,99999 x 33,7478 =", "");
        assertNumber("-1,6423165755e+17", "9999999999999999 x 25,7485 x ,63783 =", "");
        assertNumber("1,468891437446521e+20", "00032456 x 71223234 x 63543775,46344 =", "");
        assertNumber("2,4", "24 x 0,9999999999999999 x 0,1 x", "24 x 0,9999999999999999 x 0,1 x ");
        assertNumber("0,9999999999999999", "1 x 0,9999999999999999 x", "1 x 0,9999999999999999 x ");
        assertNumber("-184 139 981 872", "645634 x 3241 x 8893  x ", "-645634 x 3241 x 88 x ");
        assertNumber("327 678 773,3908101", "45 x 975,43467 x 7465,134x", "45 x 975,43467 x 7465,134 x ");
        assertNumber("0,000000048399758", "199999 x 0,999999999999999999999 x 24,2 x 0,00000000000001 =", "");
        assertNumber("-1,807820150566632e+32", "2543648,9 x 71122444653354,43 x 999289985564,9966 =", "");
        assertNumber("9,999999999999997e+44", "9999999999999999 x 999999999999999,9 x 99999999999999,99 =", "");
        assertNumber("-30 599 692,0677186", "45623 x 67,23445 x 9,9756536  x", "45623 x 67,23445 x -9,9756536 x ");
        assertNumber("62 834 967 167 427,83", "6453,7546 x 998993,2133 x 09746 x", "6453,7546 x 998993,2133 x 9746 x ");
        assertNumber("1,608762156067023e+27", ",283555656456454 x 41536,47786 x 536565243543452,9 x 254566541,13232 =", "");
        assertNumber("6,00978578110902e+117", "91225536 x 77535 x 35555555366655 x 773888,2444456372 x 9341566,993676 x==", "");
        assertNumber("2,885457654121366e+156", "123456789 x 42565476,25465465 x 32354556133,999 x 173734556 x 441224,355467 x===", "");
        assertNumber("-5 376 386 316 791 022", "889499999,9999 x 7,9999999 x 755534,99 x", "889499999,9999 x -7,999999 x 755534,99 x ");
        assertNumber("-1,240643711305041e+47", "763645455243 x 87487868 x 878787878 x 231432 x 1332443 x 12334566 = x 0,555555555555555 =", "");
        assertNumber("198 052 504 342 910,6", "0,8993654377 x 345278,996132 x 637784534,9999 x", "0,8993654377 x 345278,996132 x 637784534,9999 x ");
        assertNumber("4,694315569797804e+55", "9999999999999999 x 99273555,53445 x 7687929,1213436 x 8888292995564725 x 378666 x 1827488 =", "");
    }

    @Test
    void checkDivide() {
        //Integer
        assertNumber("Result is undefined", "0 / 0 /", "0 ÷ 0 ÷ ");

        assertNumber("0", "0 / 2 /", "0 ÷ 2 ÷ ");
        assertNumber("Cannot divide by zero", "2 / 0 /", "2 ÷ 0 ÷ ");
        assertNumber("0", "0 / 2  /", "0 ÷ -2 ÷ ");
        assertNumber("Cannot divide by zero", "2  / 0 /", "-2 ÷ 0 ÷ ");

        assertNumber("0,6666666666666667", "2 / 3 /", "2 ÷ 3 ÷ ");
        assertNumber("-0,6666666666666667", "2 / 3  /", "2 ÷ -3 ÷ ");
        assertNumber("-0,6666666666666667", "2  / 3 /", "-2 ÷ 3 ÷ ");
        assertNumber("0,6666666666666667", "2  / 3  /", "-2 ÷ -3 ÷ ");

        //with equal
        assertNumber("0", "0 / 2 =", "");
        assertNumber("Cannot divide by zero", "2 / 0 =", "2 ÷ ");
        assertNumber("0", "0 / 2  =", "");
        assertNumber("Cannot divide by zero", "2  / 0 =", "-2 ÷ ");

        assertNumber("0,6666666666666667", "2 / 3 =", "");
        assertNumber("-0,6666666666666667", "2 / 3  =", "");
        assertNumber("-0,6666666666666667", "2  / 3 =", "");
        assertNumber("0,6666666666666667", "2  / 3  =", "");

        //Decimal
        assertNumber("0", "0 / ,2 /", "0 ÷ 0,2 ÷ ");
        assertNumber("0", "0 / ,2  /", "0 ÷ -0,2 ÷ ");

        assertNumber("6,666666666666667", "2 / 0,3 /", "2 ÷ 0,3 ÷ ");
        assertNumber("0,0666666666666667", "0,2 / 3 /", "0,2 ÷ 3 ÷ ");
        assertNumber("0,6666666666666667", "0,2 / 0,3 /", "0,2 ÷ 0,3 ÷ ");

        assertNumber("-6,666666666666667", "2 / 0,3  /", "2 ÷ -0,3 ÷ ");
        assertNumber("-0,0666666666666667", "0,2 / 3  /", "0,2 ÷ -3 ÷ ");
        assertNumber("-0,6666666666666667", "0,2 / 0,3  /", "0,2 ÷ -0,3 ÷ ");

        assertNumber("-6,666666666666667", "2  / 0,3 /", "-2 ÷ 0,3 ÷ ");
        assertNumber("-0,0666666666666667", "0,2  / 3 /", "-0,2 ÷ 3 ÷ ");
        assertNumber("-0,6666666666666667", "0,2  / 0,3 /", "-0,2 ÷ 0,3 ÷ ");

        assertNumber("6,666666666666667", "2  / 0,3  /", "-2 ÷ -0,3 ÷ ");
        assertNumber("0,0666666666666667", "0,2  / 3  /", "-0,2 ÷ -3 ÷ ");
        assertNumber("0,6666666666666667", "0,2  / 0,3  /", "-0,2 ÷ -0,3 ÷ ");

        //with equal
        assertNumber("0", "0 / ,2 =", "");
        assertNumber("0", "0 / ,2  =", "");

        assertNumber("6,666666666666667", "2 / 0,3 =", "");
        assertNumber("0,0666666666666667", "0,2 / 3 =", "");
        assertNumber("0,6666666666666667", "0,2 / 0,3 =", "");

        assertNumber("-6,666666666666667", "2 / 0,3  =", "");
        assertNumber("-0,0666666666666667", "0,2 / 3  =", "");
        assertNumber("-0,6666666666666667", "0,2 / 0,3  =", "");

        assertNumber("-6,666666666666667", "2  / 0,3 =", "");
        assertNumber("-0,0666666666666667", "0,2  / 3 =", "");
        assertNumber("-0,6666666666666667", "0,2  / 0,3 =", "");

        assertNumber("6,666666666666667", "2  / 0,3  =", "");
        assertNumber("0,0666666666666667", "0,2  / 3  =", "");
        assertNumber("0,6666666666666667", "0,2  / 0,3  =", "");


        //Complicate tests
        assertNumber("0,01", "0,1 / 10 =", "");
        assertNumber("0,1", "0,9999999999999999 / 10 =", "");
        assertNumber("19,01901901901902", "19 / 0,999 =", "");
        assertNumber("0,01", "0,9999999999999999 / 100 =", "");
        assertNumber("3,333333333333333", "10 / 3 /", "10 ÷ 3 ÷ ");
        assertNumber("0,000000000000001", "1 / 1000000000000000 =", "");
        assertNumber("0,0000000000000001", "0,000000000000001 / 10 =", "");
        assertNumber("0,0000000000000001", "0,0000000000000001 / 1 =", "");
        assertNumber("0,0111111111111111", "0,1111111111111111 / 10 =", "");
        assertNumber("0,0011111111111111", "0,1111111111111111 / 100 =", "");
        assertNumber("0,3947368421052632", "6 / 1,9 / 8 /", "6 ÷ 1,9 ÷ 8 ÷ ");
        assertNumber("-0,0000000000000001", "0,0000000000000001  / 1 =", "");
        assertNumber("0,5833333333333333", "7 / 2 / 3 / 2 /", "7 ÷ 2 ÷ 3 ÷ 2 ÷ ");
        assertNumber("2,00000002", "2 + - x / 0,99999999 / ", "2 ÷ 0,99999999 ÷ ");
        assertNumber("3,333333444444448", "10 / 2,9999999 /", "10 ÷ 2,9999999 ÷ ");
        assertNumber("1", "1 / 0,9999999999999999 /", "1 ÷ 0,9999999999999999 ÷ ");
        assertNumber("1,296158168941309e-14", "6557 / 66777 / 7575656565656 =", "");
        assertNumber("-2,176294900243917e-5", ",734451643 / 999,99999 / 33,7478 =", "");
        assertNumber("1,529149189404024", "687 / 456 / ,98524 /", "687 ÷ 456 ÷ 0,98524 ÷ ");
        assertNumber("7,171339618067257e-12", "00032456 / 71223234 / 63543775,46344 =", "");
        assertNumber("-608 896 003 923 940,1", "9999999999999999 / 25,7485 / ,63783 =", "");
        assertNumber("240", "24 / 0,9999999999999999 / 0,1 /", "24 ÷ 0,9999999999999999 ÷ 0,1 ÷ ");
        assertNumber("-2,263730330145017", "645634 / 3241 / 8893  / ", "-645634 ÷ 3241 ÷ 88 ÷ ");
        assertNumber("0,0000000000001", "9999999999999999 / 999999999999999,9 / 99999999999999,99 =", "");
        assertNumber("6,179832703367877e-6", "45 / 975,43467 / 7465,134 /", "45 ÷ 975,43467 ÷ 7465,134 ÷ ");
        assertNumber("8,264421487603307e+17", "199999 / 0,9999999999999999 / 24,2 / 0,00000000000001 =", "");
        assertNumber("-3,578976119873449e-20", "2543648,9 / 71122444653354,43 / 999289985564,9966 =", "");
        assertNumber("-68,02219200094015", "45623 / 67,23445 / 9,9756536 /", "45623 ÷ 67,23445 ÷ -9,9756536 ÷ ");
        assertNumber("6,628625797804511e-7", "6453,7546 / 998993,2133 / 09746 /", "6453,7546 ÷ 998993,2133 ÷ 9746 ÷ ");
        assertNumber("4,997868081694287e-29", ",283555656456454 / 41536,47786 / 536565243543452,9 / 254566541,13232 =", "");
        assertNumber("2,184674886815982e+23", "91225536 / 77535 / 35555555366655 / 773888,2444456372 / 9341566,993676 /==", "");
        assertNumber("7,312189601215112e+45", "123456789 / 42565476,25465465 / 32354556133,999 / 173734556 / 441224,355467 /===", "");
        assertNumber("-147,1639505384479", "889499999,9999 / 7,9999999 / 755534,99 /", "889499999,9999 ÷ -7,999999 ÷ 755534,99 ÷ ");
        assertNumber("-4,700417823420592e-24", "763645455243 / 87487868 / 878787878 / 231432 / 1332443 / 12334566 = / 0,555555555555555 =", "");
        assertNumber("4,084059392295516e-15", "0,8993654377 / 345278,996132 / 637784534,9999 /", "0,8993654377 ÷ 345278,996132 ÷ 637784534,9999 ÷ ");
        assertNumber("2,130235995282849e-24", "9999999999999999 / 99273555,53445 / 7687929,1213436 / 8888292995564725 / 378666 / 1827488 =", "");
        assertNumber("-1,658062522813398", "0,9999999999999999 / 0,77644 / 0,776767767666  /", "0,9999999999999999 ÷ 0,77644 ÷ -0,776767767666 ÷ ");

        //Exception (Integer or decimal divide by zero, zero divide by zero
        assertNumber("Result is undefined", "67 -= / 0 =", "0 ÷ ");
        assertNumber("Result is undefined", "0 / 0 =", "sqr(0) ÷ ");
        assertNumber("Result is undefined", "0 √ / 0 =", "√(0) ÷ ");
        assertNumber("Result is undefined", "654 + 89 + 14 - = / 0 =", "0 ÷ ");

        assertNumber("Cannot divide by zero", "1 / 0 =", "1 ÷ ");
        assertNumber("Cannot divide by zero", "1,3 / 0 =", "1,3 ÷ ");
        assertNumber("Cannot divide by zero", "98874 / 0 =", "98874 ÷ ");
        assertNumber("Cannot divide by zero", "988,74 / 0 =", "988,74 ÷ ");
        assertNumber("Cannot divide by zero", "1 / 5 / 78 / 0 =", "1 ÷ 5 ÷ 78 ÷ ");
        assertNumber("Cannot divide by zero", "2 + 4 - 1 + 3 / 0 =", "2 + 4 - 1 + 3 ÷ ");
        assertNumber("Cannot divide by zero", "0,0000000000000001 / 0 =", "0,0000000000000001 ÷ ");
        assertNumber("Cannot divide by zero", "87848  + 6356457 √ / 0 =", "sqr(87848) + √(6356457) ÷ ");
        assertNumber("Cannot divide by zero", "847 + 7865874,37 - 378 + 7687,1762 / 0 =", "847 + 7865874,37 - 378 + 7687,1762 ÷ ");
        assertNumber("Cannot divide by zero", "87848  + 6356457 √ + 8873 % / 0 =", "sqr(87848) + √(6356457) + 684753688764,129 ÷ ");

    }

    @Test
    void checkEqual() {
        //One equal
        assertNumber("0", "=", "");
        assertNumber("0", "+ =", "");
        assertNumber("0", "- =", "");
        assertNumber("0", "x =", "");
        assertNumber("0", " =", "");
        assertNumber("0", "√ =", "");
        assertNumber("0", "% =", "");

        assertNumber("2", "2 =", "");
        assertNumber("4", "2 + =", "");
        assertNumber("0", "2 - =", "");
        assertNumber("4", "2 x =", "");
        assertNumber("1", "2 / =", "");
        assertNumber("4", "2  =", "");
        assertNumber("1,414213562373095", "2 √ =", "");
        assertNumber("0,5", "2  =", "");
        assertNumber("2,06", "2 + 3 % =", "");

        assertNumber("4", "2 + = + +", "4 + ");
        assertNumber("0", "2 - = - -", "0 - ");
        assertNumber("4", "2 x = x x", "4 x ");
        assertNumber("1", "2 / = / /", "1 ÷ ");
        assertNumber("256", "2  =  ", "sqr(sqr(4))");
        assertNumber("1,090507732665258", "2 √ = √ √", "√(√(1,414213562373095))");
        assertNumber("0,5", "2  =  ", "1/(1/(0,5))");
        assertNumber("0,0008741816", "2 + 3 % = % %", "0,0008741816");

        assertNumber("8", "2 + = + =", "");
        assertNumber("0", "2 - = - =", "");
        assertNumber("16", "2 x = x =", "");
        assertNumber("1", "2 / = / =", "");
        assertNumber("16", "2  =  =", "");
        assertNumber("1,189207115002721", "2 √ = √ =", "");
        assertNumber("2", "2  =  =", "");
        assertNumber("0,102436", "2 + 3 % = % =", "");
        //Several equals
        assertNumber("8", "2 + = = =", "");
        assertNumber("-4", "2 - = = =", "");
        assertNumber("16", "2 x = = =", "");
        assertNumber("0,25", "2 / = = =", "");
        assertNumber("4", "2  = = =", "");
        assertNumber("1,414213562373095", "2 √ = = =", "");
        assertNumber("0,5", "2  = = =", "");
        assertNumber("2,18", "2 + 3 % = = =", "");
        assertNumber("-12", "2 + = = = = 6 - = = =", "");

    }

    @Test
    void checkNegate() {
        assertNumber("0", "0 ", "");
        assertNumber("0", "0 = ", "negate(0)");
        assertNumber("-1", "1 ", "");
        assertNumber("1", "1  ", "");
        assertNumber("-1", "1 = ", "negate(1)");
        assertNumber("1", "1 =  ", "negate(negate(1))");
        assertNumber("9", "2 + 3 - 4  +", "2 + 3 - -4 + ");
        assertNumber("1", "2 + 3 - 4   +", "2 + 3 - 4 + ");
        assertNumber("-5", "2 + 3 - ", "2 + 3 - negate(5)");
        assertNumber("0", "2 + 3 -  5 +", "2 + 3 - 5 + ");
        assertNumber("5", "2 + 3 -  ", "2 + 3 - negate(negate(5))");
        assertNumber("-3", "9 √   ", "negate(negate(negate(√(9))))");
    }

    @Test
    void checkOperationHistory() {
        assertNumber("0", "+", "0 + ");
        assertNumber("0", "-", "0 - ");
        assertNumber("0", "x", "0 x ");
        assertNumber("0", "/", "0 ÷ ");
        assertNumber("0", "", "sqr(0)");
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
        assertNumber("9,999999999999998e+31", "99999999999999999  = +", "9,999999999999998e+31 + ");

    }

    @Test
    void checkMIXBinaryOperations() {
        //Add with
        assertNumber("1", "1 + 0,9999999999999999 - 0,9999999999999999 =", "");
        assertNumber("1 925 066 427,985", "34156,745   + 388656 x 45532  =", "");
        assertNumber("-1 556,844780280956", ",77765544 + 7785  / 4,999999 =", "");
        assertNumber("9 999 998,0000001", "9999999 + 9999999 - 9999999 x 0,9999999 =", "");
        assertNumber("0,0617809442729949", "867,8333 + 3 x 4 / 56382 =       ", "");
        assertNumber("455 667 995,6", "2332, + 3 / 0,5 + 455663325,6 =     ", "negate(negate(negate(negate(455667995,6))))");
        //Subtract with
        assertNumber("19", "19 - 0,9999999999999999 + 0,9999999999999999 =", "");
        assertNumber("48 688 021 125,732", "27786, , 999 - 768492,000000 x 65732   =", "");
        assertNumber("631 210,0063121001", "887754 - 256544 / 0,99999999 =", "");
        assertNumber("-357 684,1186736475", "23  1 - 65  43 x 2345 / 5,73 =", "");
        assertNumber("84,77860962566845", "43 - 87678   ,4 / 748 + 73 =", "");
        assertNumber("-3 929", "2 - 3957 + 84 - 7  58 =", "");
        //Multiply with
        assertNumber("2,099999998", "0,999999999 x 2 + 0,1 =", "");
        assertNumber("1,9", "0,9999999999999999 x 2 - 0,1 =", "");
        assertNumber("20", "0,9999999999999999 x 2 / 0,1 =", "");
        assertNumber("8 814", "233 x 38 + 4 - 44 =", "");
        assertNumber("-50 623 218 535 195,59", "97548354766  ,  452 x 3874 - 4 / 7465 =", "");
        assertNumber("88,54373407202216", "973 x 9 / 8664 + 87,533 =", "");
        //Divide with
        assertNumber("637 925,889675519", "288743   , 00000001 / 88985455 + 637925,889999999 =", "");
        assertNumber("8,000000000000001", "9 / 0,9999999999999999 - 0,9999999999999999 =", "");
        assertNumber("1 000", "1000 / 2,9999999999999999647 x 3 =", "");
        assertNumber("-789,5563005780347", "972 / 865 + 85,32 - 876 =", "");
        assertNumber("4 055,773333333333", "7784 / 84 - ,49 x 44 =", "");
        assertNumber("35,8938327603227", "297 / 36 x 409 / 94,0064 =", "");
    }

    @Test
    void checkOneDivideX() {
        assertNumber("1", "1 ", "1/(1)");
        assertNumber("2", "2  ", "1/(1/(2))");
        assertNumber("-2", "2   ", "1/(1/(-2))");
        assertNumber("-2", "2   ", "1/(negate(1/(2)))");
        assertNumber("-2", "2   ", "negate(1/(1/(2)))");
        assertNumber("-2", "2     ", "negate(1/(negate(1/(-2))))");

        assertNumber("10", "0,1 ", "1/(0,1)");
        assertNumber("0,2", "0,2  ", "1/(1/(0,2))");
        assertNumber("-0,2", "0,2   ", "1/(1/(-0,2))");
        assertNumber("-0,2", "0,2   ", "1/(negate(1/(0,2)))");
        assertNumber("-0,2", "0,2   ", "negate(1/(1/(0,2)))");
        assertNumber("-0,2", "0,2     ", "negate(1/(negate(1/(-0,2))))");
        //With equal
        assertNumber("1", "1  =", "");
        assertNumber("2", "2   =", "");
        assertNumber("-2", "2    =", "");
        assertNumber("-2", "2    =", "");
        assertNumber("-2", "2    =", "");
        assertNumber("-2", "2      =", "");

        assertNumber("10", "0,1  =", "");
        assertNumber("0,2", "0,2   =", "");
        assertNumber("-0,2", "0,2    =", "");
        assertNumber("-0,2", "0,2    =", "");
        assertNumber("-0,2", "0,2    =", "");
        assertNumber("-0,2", "0,2      =", "");

        //Complicate tests
        assertNumber("9 009", "9009  ", "1/(1/(9009))");
        assertNumber("0,001865671641791", "536 ", "1/(536)");
        assertNumber("73 542", "73542  ", "1/(1/(73542))");
        assertNumber("222,2222222222222", "0,0045 ", "1/(0,0045)");
        assertNumber("752,5140811849786", "0,00132887878779  =", "");
        assertNumber("0,0017789321070561", "562,135 ", "1/(562,135)");
        assertNumber("1,198368301720378e-6", "834468 ", "1/(834468)");
        assertNumber("-1,180219520830875e-4", "8473  ", "1/(-8473)");
        assertNumber("1,35974437349676", ",73543235 ", "1/(0,73543235)");
        assertNumber("1", "0,9999999999999999 ", "1/(0,9999999999999999)");
        assertNumber("-29,64368293116737", "0,033734 ", "1/(-0,033734)");
        assertNumber("1,01055752191605e-10", "9895527748,9199    =", "");
        assertNumber("8,041414377677394", "0,124356233 ", "1/(0,124356233)");
        assertNumber("0,0010000006055004", "999,9993945 ", "1/(999,9993945)");
        assertNumber("1,e+16", "0,0000000000000001 ", "1/(0,0000000000000001)");
        assertNumber("1,016617102238589e-7", "9836545,124 ", "1/(9836545,124)");
        assertNumber("1,e+16", "0,0000000000000001 ", "1/(0,0000000000000001)");
        assertNumber("1,196951538248595e-7", "8354557,12320 ", "1/(8354557,1232)");
        assertNumber("-1,19408048075669e-6", "837464,4893 ", "1/(-837464,4893)");
        assertNumber("0,021799115915055", "45,87342", "1/(1/(1/(45,87342)))");
        assertNumber("1 761,600912138716", "0,00056766546447 ", "1/(0,00056766546447)");
        assertNumber("500 000 000 000 000", "0,000000000000002 ", "1/(0,000000000000002)");
        assertNumber("-1,285027048842758e-6", "778193,736  ", "negate(1/(778193,736))");
        assertNumber("1,017639272193636e-11", "98266647851  ", "negate(1/(-98266647851))");
        assertNumber("-1", "0,9999999999999999", "negate(1/(1/(1/(0,9999999999999999))))");
        assertNumber("-2,349266713278937e-9", "425664738    ", "1/(1/(negate(1/(425664738))))");
        assertNumber("-4,4003495410787e-12", "227254673899,124   ", "1/(1/(1/(-227254673899,124)))");
        assertNumber("0,0000000000000001", "9999999999999999", "1/(1/(1/(1/(1/(9999999999999999)))))");

        //Exception (divide by zero)
        assertNumber("Cannot divide by zero", "0 ", "1/(0)");
        assertNumber("Cannot divide by zero", "0 √ ", "1/(√(0))");
        assertNumber("Cannot divide by zero", "0  ", "1/(sqr(0))");
        assertNumber("Cannot divide by zero", "0 x 4656356 =  ", "1/(0)");
        assertNumber("Cannot divide by zero", "1 - 1 - ", "1 - 1 - 1/(0)");
        assertNumber("Cannot divide by zero", "4 + 5 - 9 +  ", "4 + 5 - 9 + 1/(0)");
        assertNumber("Cannot divide by zero", "8789,844 + 88489,3 - =  ", "1/(0)");
        assertNumber("Cannot divide by zero", "7637673 + 6564566 x 0 -  ", "7637673 + 6564566 x 0 - 1/(0)");
    }

    @Test
    void checkSquareRoot() {
        assertNumber("0", "0 √", "√(0)");

        assertNumber("1", "1 √", "√(1)");
        assertNumber("1,189207115002721", "2 √ √", "√(√(2))");
        assertNumber("-1,189207115002721", "2 √ √ ", "negate(√(√(2)))");

        assertNumber("0,3162277660168379", "0,1 √", "√(0,1)");
        assertNumber("0,668740304976422", "0,2 √ √", "√(√(0,2))");
        assertNumber("-0,668740304976422", "0,2 √ √ ", "negate(√(√(0,2)))");
        //With equal
        assertNumber("0", "0 √ =", "");
        assertNumber("1", "1 √ =", "");
        assertNumber("1,189207115002721", "2 √ √ =", "");
        assertNumber("-1,189207115002721", "2 √ √  =", "");

        assertNumber("0,3162277660168379", "0,1 √ =", "");
        assertNumber("0,668740304976422", "0,2 √ √ =", "");
        assertNumber("-0,668740304976422", "0,2 √ √  =", "");

        //Complicate tests
        assertNumber("1,414213562373095", "2 √", "√(2)");
        assertNumber("23,15167380558045", "536 √", "√(536)");
        assertNumber("1,189207115002721", "2 √ √", "√(√(2))");
        assertNumber("92,04890004774636", "8473  √", "√(8473)");
        assertNumber("913,4922002951093", "834468 √", "√(834468)");
        assertNumber("16,4677345994808", "73542 √ √", "√(√(73542))");
        assertNumber("0,0364537897589537", "0,00132887878779 √ =", "");
        assertNumber("-7,071067811865475", "50 √ ", "negate(√(50))");
        assertNumber("0,1836681790621337", "0,033734 √", "√(0,033734)");
        assertNumber("17,75946465279331", "9895527748,9199 √ √ √ =", "");
        assertNumber("0,8575735245446888", ",73543235 √", "√(0,73543235)");
        assertNumber("915,1308591125097", "837464,4893 √", "√(837464,4893)");
        assertNumber("1,613225729177976", "45,87342 √ √ √", "√(√(√(45,87342)))");
        assertNumber("11,98487363576367", "425664738 √ √ √", "√(√(√(425664738)))");
        assertNumber("0,00000001", "0,0000000000000001 √", "√(0,0000000000000001)");
        assertNumber("2,37137352617826", "999,9993945 √ √ √", "√(√(√(999,9993945)))");
        assertNumber("7,483509659516713", "9836545,124 √ √ √", "√(√(√(9836545,124)))");
        assertNumber("-882,1528983118516", "778193,736 √ ", "negate(√(778193,736))");
        assertNumber("0,0238257311424015", "0,00056766546447 √", "√(0,00056766546447)");
        assertNumber("-313 475,1152021481", "98266647851 √ ", "negate(√(98266647851))");
        assertNumber("-0,3526417913407315", "0,124356233 √ ", "negate(√(0,124356233))");
        assertNumber("4,472135954999579e-8", "0,000000000000002 √", "√(0,000000000000002)");
        assertNumber("5,259663116753397", "765,3 √   √ ", "√(negate(negate(√(765,3))))");
        assertNumber("2,707822636955336", "8354557,12320 √ √ √ √", "√(√(√(√(8354557,1232))))");
        assertNumber("26,27629342823825", "227254673899,124 √ √ √", "√(√(√(227254673899,124)))");
        assertNumber("0,9586624632624689", "0,0045 √ √ √ √ √ √ √", "√(√(√(√(√(√(√(0,0045)))))))");
        assertNumber("3,162277660168379", "9999999999999999 √ √ √ √ √", "√(√(√(√(√(9999999999999999)))))");

        //Exception (Square root negative number)
        assertNumber("Invalid input", "1  √", "√(-1)");
        assertNumber("Invalid input", "2 - 3 = √", "√(-1)");
        assertNumber("Invalid input", "2 + 3 =  √", "√(negate(5))");
        assertNumber("Invalid input", "2 + 6784585  = √", "√(-6784583)");
        assertNumber("Invalid input", "74672 + 6784585 =  √", "√(negate(6859257))");
        assertNumber("Invalid input", "0,888885999 / 9599 x 1  =  √", "√(-9,260193759766642e-5)");
    }

    @Test
    void checkSquareX() {
        assertNumber("0", "0 ", "sqr(0)");
        assertNumber("1", "1 ", "sqr(1)");
        assertNumber("4", "2 ", "sqr(2)");
        assertNumber("16", "2  ", "sqr(sqr(2))");
        assertNumber("16", "2   ", "sqr(sqr(-2))");
        assertNumber("16", "2   ", "sqr(negate(sqr(2)))");
        assertNumber("-16", "2   ", "negate(sqr(sqr(2)))");
        assertNumber("-16", "2     ", "negate(sqr(negate(sqr(-2))))");

        assertNumber("0,01", "0,1 ", "sqr(0,1)");
        assertNumber("0,04", "0,2 ", "sqr(0,2)");
        assertNumber("0,0016", "0,2  ", "sqr(sqr(0,2))");
        assertNumber("0,0016", "0,2   ", "sqr(sqr(-0,2))");
        assertNumber("0,0016", "0,2   ", "sqr(negate(sqr(0,2)))");
        assertNumber("-0,0016", "0,2   ", "negate(sqr(sqr(0,2)))");
        assertNumber("-0,0016", "0,2     ", "negate(sqr(negate(sqr(-0,2))))");
        //With equal
        assertNumber("1", "1  =", "");
        assertNumber("4", "2  =", "");
        assertNumber("16", "2   =", "");
        assertNumber("16", "2    =", "");
        assertNumber("16", "2    =", "");
        assertNumber("-16", "2    =", "");
        assertNumber("-16", "2      =", "");

        assertNumber("0,01", "0,1  =", "");
        assertNumber("0,04", "0,2  =", "");
        assertNumber("0,0016", "0,2   =", "");
        assertNumber("0,0016", "0,2    =", "");
        assertNumber("0,0016", "0,2    =", "");
        assertNumber("-0,0016", "0,2    =", "");
        assertNumber("-0,0016", "0,2      =", "");

        //Complicate tests
        assertNumber("287 296", "536 ", "sqr(536)");
        assertNumber("547,56", "23,4 ", "sqr(23,4)");
        assertNumber("71 791 729", "8473  ", "sqr(-8473)");
        assertNumber("0,00002025", "0,0045 ", "sqr(0,0045)");
        assertNumber("696 336 843 024", "834468 ", "sqr(834468)");
        assertNumber("1,76591883263822e-6", "0,00132887878779  =", "");
        assertNumber("0,001137982756", "0,033734 ", "sqr(-0,033734)");
        assertNumber("952 217 706 900 625", "5555  ", "sqr(sqr(5555))");
        assertNumber("0,5408607414265225", ",73543235 ", "sqr(0,73543235)");
        assertNumber("9,194152180200956e+79", "9895527748,9199    =", "");
        assertNumber("2,925106924469898e+19", "73542  ", "sqr(sqr(73542))");
        assertNumber("0,0154644726859503", "0,124356233 ", "sqr(0,124356233)");
        assertNumber("999 998,7890003666", "999,9993945 ", "sqr(999,9993945)");
        assertNumber("4,e-30", "0,000000000000002 ", "sqr(0,000000000000002)");
        assertNumber("1,e-32", "0,0000000000000001 ", "sqr(0,0000000000000001)");
        assertNumber("701 346 770 838,5098", "837464,4893 ", "sqr(-837464,4893)");
        assertNumber("69 798 624 724 811,86", "8354557,12320 ", "sqr(8354557,1232)");
        assertNumber("3,222440795519408e-7", "0,00056766546447 ", "sqr(0,00056766546447)");
        assertNumber("-605 585 490 749,6377", "778193,736  ", "negate(sqr(778193,736))");
        assertNumber("19 610 512 980 404,22", "45,87342   ", "sqr(sqr(sqr(45,87342)))");
        assertNumber("0,9999999999999998", "0,9999999999999999  ", "sqr(-0,9999999999999999)");
        assertNumber("-9,656334079872443e+21", "98266647851  ", "negate(sqr(-98266647851))");
        assertNumber("8,764773722965631e+55", "9836545,124   ", "sqr(sqr(sqr(9836545,124)))");
        assertNumber("1,077807988668255e+69", "425664738    ", "sqr(sqr(negate(sqr(425664738))))");
        assertNumber("7,113815415810459e+90", "227254673899,124   ", "sqr(sqr(sqr(-227254673899,124)))");
        assertNumber("9,999999999999968e+511", "9999999999999999     ", "sqr(sqr(sqr(sqr(sqr(9999999999999999)))))");
        assertNumber("-0,9999999999999992", "0,9999999999999999    ", "negate(sqr(sqr(sqr(0,9999999999999999))))");
    }

    @Test
    void checkOverflow() {
        //Exception (Overflow, if number`s scale by result operation, more then legal scale),
        // max number => ‭9,999999999999999e+9999
        assertNumber("9,999999999999999e+9999", "1000000000000000  x 1000000000 = =x 9999999999999999 = ", "");
        assertNumber("9,999999999999999e+9999", " / 9999999999999999 / 10 = x 4 +  =", "");
        assertNumber("9,999999999999999e+9999", " / 9999999999999999 / 10 = x 4,999999999999999 +  =", "");
        //If max number add number more then 5,e+9983 will be exception
        assertNumber("Overflow", " / 9999999999999999 / 10 = x 5 = +  =", "5,e+9983 + ");
        assertNumber("Overflow", " / 9999999999999999 / 10 = x 5,0 = +  =", "5,e+9983 + ");
        assertNumber("Overflow", " / 9999999999999999 / 10 = x 5,1 = +  = ", "5,1e+9983 + ");

        assertNumber("Overflow", "1000000000000000x=x=x=x=x=x=x=x=x=x=", "1,e+7680 x ");
        assertNumber("Overflow", "1000000000000000x1000000000=9999999999999999 x  = x 10 = ", "9,999999999999999e+9999 x ");
        assertNumber("Overflow", "9999999999999999", "sqr(sqr(sqr(sqr(sqr(sqr(sqr(sqr(sqr(sqr(9999999999999999))))))))))");

        // number => 9,999999999999999e-9999
        assertNumber("9,999999999999999e-9999", "0,0000000000000001  x 0,0000001 = x 0,000000000000001= x 9,999999999999999 = ", "");
        assertNumber("Overflow", " /10 =", "9,999999999999999e-9999 ÷ ");
        assertNumber("Overflow", " x0,1 = ", "9,999999999999999e-9999 x ");

        assertNumber("1,e-9999", "0,0000000000000001  x 0,0000001 = x 0,000000000000001= ", "");
        assertNumber("Overflow", "/10=", "1,e-9999 ÷ ");
        assertNumber("Overflow", "x0,1=", "1,e-9999 x ");

        assertNumber("Overflow", "9999999999999999/1000000000000000===", "sqr(sqr(sqr(sqr(sqr(sqr(sqr(sqr(sqr(9,999999999999999e-30)))))))))");
        assertNumber("Overflow", "0,0000000000000001x,0000001=x0,0000000000000001 =", "sqr(sqr(sqr(sqr(sqr(sqr(sqr(sqr(1,e-39)))))))) x ");
        assertNumber("Overflow", "0,0000000000000001", "sqr(sqr(sqr(sqr(sqr(sqr(sqr(sqr(sqr(sqr(0,0000000000000001))))))))))");
    }

    @Test
    void checkMIXUnaryOperations() {
        assertNumber("15 678", "15678√", "sqr(√(15678))");
        assertNumber("1,000020000300004e-10", "99999", "1/(sqr(99999))");
        assertNumber("0,00108190582506", "854321√", "√(1/(854321))");
        assertNumber("-0,0010000005000004", "999999√", "negate(1/(√(999999)))");
        assertNumber("9 874 562", "9874562√", "√(sqr(9874562))");
        assertNumber("1,00000020000003e-14", "9999999", "sqr(1/(9999999))");
        //three operation
        assertNumber("9,956168238447662", "96546784,32415 √ √ √ ", "√(√(√(96546784,32415)))");
        assertNumber("9 999,9999500005", "99999999,00001 √ √  ", "sqr(√(√(99999999,00001)))");
        assertNumber("0,0056425927787405", "986472913,876543 √ √  ", "1/(√(√(986472913,876543)))");
        assertNumber("31 622,77658587241", "999999999 √  √ ", "√(sqr(√(999999999)))");
        assertNumber("0,0032577870844877", "8877878787,65 √  √ ", "√(1/(√(8877878787,65)))");
        assertNumber("99 999,999995", "9999999999  √ √ ", "√(√(sqr(9999999999)))");
        assertNumber("0,0017837042774312", "98788998999,874  √ √ ", "√(√(1/(98788998999,874)))");

        assertNumber("2,036161793239094e+84", "34562212456    ", "sqr(sqr(sqr(34562212456)))");
        assertNumber("9,9999999998e+21", "99999999999   √  ", "negate(negate(√(sqr(sqr(99999999999)))))");
        assertNumber("2,596893369539549e-48", "787746478889,98787    ", "1/(sqr(sqr(787746478889,9878)))");
        assertNumber("9,99999999999537e+23", "999999999999,768565  √  ", "sqr(√(sqr(999999999999,7685)))");
        assertNumber("2,598079199785716e-52", "7876565765566    ", "sqr(1/(sqr(7876565765566)))");
        assertNumber("9,999999999999998e+25", "9999999999999,999999 √   ", "sqr(sqr(√(9999999999999,999)))");
        assertNumber("1,060361063588689e-56", "98545445454546    ", "sqr(sqr(1/(98545445454546)))");

        assertNumber("1,00000000000001e-14", "99999999999999    ", "1/(1/(1/(99999999999999)))");
        assertNumber("25 991 214,58828613", "675543235774338   √ ", "√(1/(1/(675543235774338)))");
        assertNumber("9,99999999999998e+29", "999999999999999    ", "sqr(1/(1/(999999999999999)))");
        assertNumber("8,100000065610006e-16", "1234567891234567  √  ", "1/(√(sqr(1234567891234567)))");
        assertNumber("1,173436304521581e+31", "3425545656565653    ", "1/(sqr(1/(3425545656565653)))");
        assertNumber("6 654 638 824 545 454", "6654638824545454  = √", "√(4,42842178851477e+31)");
        assertNumber("-999 998 000 001,0002", "999999,0000000001     ", "negate(1/(1/(sqr(-999999,0000000001))))");

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
    void checkPercent() {
        //First operand with percent
        //Add
        assertNumber("2", "0% + 2 +", "0 + 2 + ");
        assertNumber("0", "2% + 0 +", "0 + 0 + ");
        assertNumber("-2", "0% + 2  +", "0 + -2 + ");
        assertNumber("0", "2%  + 0 +", "negate(0) + 0 + ");

        assertNumber("3", "2% + 3 +", "0 + 3 + ");
        assertNumber("-3", "2% + 3  +", "0 + -3 + ");
        assertNumber("3", "2%  + 3 +", "negate(0) + 3 + ");
        assertNumber("-3", "2%  + 3  +", "negate(0) + -3 + ");

        assertNumber("3", "0,2% + 3 +", "0 + 3 + ");
        assertNumber("-3", "0,2% + 3  +", "0 + -3 + ");
        assertNumber("3", "0,2%  + 3 +", "negate(0) + 3 + ");
        assertNumber("-3", "0,2%  + 3  +", "negate(0) + -3 + ");
        //Subtract
        assertNumber("-2", "0% - 2 -", "0 - 2 - ");
        assertNumber("0", "2% - 0 -", "0 - 0 - ");
        assertNumber("2", "0% - 2  -", "0 - -2 - ");
        assertNumber("0", "2%  - 0 -", "negate(0) - 0 - ");

        assertNumber("-3", "2% - 3 -", "0 - 3 - ");
        assertNumber("3", "2% - 3  -", "0 - -3 - ");
        assertNumber("-3", "2%  - 3 -", "negate(0) - 3 - ");
        assertNumber("3", "2%  - 3  -", "negate(0) - -3 - ");

        assertNumber("-0,3", "2% - 0,3 -", "0 - 0,3 - ");
        assertNumber("0,3", "2% - 0,3  -", "0 - -0,3 - ");
        assertNumber("-0,3", "2%  - 0,3 -", "negate(0) - 0,3 - ");
        assertNumber("0,3", "2%  - 0,3  -", "negate(0) - -0,3 - ");

        //Multiply
        assertNumber("0", "0% x 2 x", "0 x 2 x ");
        assertNumber("0", "2% x 0 x", "0 x 0 x ");
        assertNumber("0", "0% x 2  x", "0 x -2 x ");
        assertNumber("0", "2%  x 0 x", "negate(0) x 0 x ");

        assertNumber("0", "2% x 3 x", "0 x 3 x ");
        assertNumber("0", "2% x 3  x", "0 x -3 x ");
        assertNumber("0", "2%  x 3 x", "negate(0) x 3 x ");
        assertNumber("0", "2%  x 3  x", "negate(0) x -3 x ");

        assertNumber("0", "2% x 0,3 x", "0 x 0,3 x ");
        assertNumber("0", "2% x 0,3  x", "0 x -0,3 x ");
        assertNumber("0", "2%  x 0,3 x", "negate(0) x 0,3 x ");
        assertNumber("0", "2%  x 0,3  x", "negate(0) x -0,3 x ");

        //Divide
        assertNumber("0", "0% / 2 /", "0 ÷ 2 ÷ ");
        assertNumber("0", "0% / 2  /", "0 ÷ -2 ÷ ");
        assertNumber("Result is undefined", "2% / 0 /", "0 ÷ 0 ÷ ");
        assertNumber("Result is undefined", "2%  / 0 /", "negate(0) ÷ 0 ÷ ");

        assertNumber("0", "2% / 3 /", "0 ÷ 3 ÷ ");
        assertNumber("0", "2% / 3  /", "0 ÷ -3 ÷ ");
        assertNumber("0", "2%  / 3 /", "negate(0) ÷ 3 ÷ ");
        assertNumber("0", "2%  / 3  /", "negate(0) ÷ -3 ÷ ");

        assertNumber("0", "2% / 0,3 /", "0 ÷ 0,3 ÷ ");
        assertNumber("0", "2% / 0,3  /", "0 ÷ -0,3 ÷ ");
        assertNumber("0", "2%  / 0,3 /", "negate(0) ÷ 0,3 ÷ ");
        assertNumber("0", "2%  / 0,3  /", "negate(0) ÷ -0,3 ÷ ");
        //Second operand with percent
        //Add
        assertNumber("0", "0 + 2% +", "0 + 0 + ");
        assertNumber("2", "2 + 0% +", "2 + 0 + ");
        assertNumber("0", "0 + 2%  +", "0 + negate(0) + ");
        assertNumber("-2", "2  + 0% +", "-2 + 0 + ");

        assertNumber("2,06", "2 + 3% +", "2 + 0,06 + ");
        assertNumber("1,94", "2 + 3%  +", "2 + negate(0,06) + ");
        assertNumber("-2,06", "2  + 3% +", "-2 + -0,06 + ");
        assertNumber("-1,94", "2  + 3%  +", "-2 + negate(-0,06) + ");

        assertNumber("2,006", "2 + 0,3% +", "2 + 0,006 + ");
        assertNumber("1,994", "2 + 0,3%  +", "2 + negate(0,006) + ");
        assertNumber("-2,006", "2  + 0,3% +", "-2 + -0,006 + ");
        assertNumber("-1,994", "2  + 0,3%  +", "-2 + negate(-0,006) + ");
        //Subtract
        assertNumber("0", "0 - 2% -", "0 - 0 - ");
        assertNumber("2", "2 - 0% -", "2 - 0 - ");
        assertNumber("0", "0 - 2%  -", "0 - negate(0) - ");
        assertNumber("-2", "2  - 0% -", "-2 - 0 - ");

        assertNumber("1,94", "2 - 3% -", "2 - 0,06 - ");
        assertNumber("2,06", "2 - 3%  -", "2 - negate(0,06) - ");
        assertNumber("-1,94", "2  - 3% -", "-2 - -0,06 - ");
        assertNumber("-2,06", "2  - 3%  -", "-2 - negate(-0,06) - ");

        assertNumber("1,994", "2 - 0,3% -", "2 - 0,006 - ");
        assertNumber("2,006", "2 - 0,3%  -", "2 - negate(0,006) - ");
        assertNumber("-1,994", "2  - 0,3% -", "-2 - -0,006 - ");
        assertNumber("-2,006", "2  - 0,3%  -", "-2 - negate(-0,006) - ");
        //Multiply
        assertNumber("0", "0 x 2% x", "0 x 0,02 x ");
        assertNumber("0", "2 x 0% x", "2 x 0 x ");
        assertNumber("0", "0 x 2%  x", "0 x negate(0,02) x ");
        assertNumber("0", "2  x 0% x", "-2 x 0 x ");

        assertNumber("0,06", "2 x 3% x", "2 x 0,03 x ");
        assertNumber("-0,06", "2 x 3%  x", "2 x negate(0,03) x ");
        assertNumber("-0,06", "2  x 3% x", "-2 x 0,03 x ");
        assertNumber("0,06", "2  x 3%  x", "-2 x negate(0,03) x ");

        assertNumber("0,006", "2 x 0,3% x", "2 x 0,003 x ");
        assertNumber("-0,006", "2 x 0,3%  x", "2 x negate(0,003) x ");
        assertNumber("-0,006", "2  x 0,3% x", "-2 x 0,003 x ");
        assertNumber("0,006", "2  x 0,3%  x", "-2 x negate(0,003) x ");

        //Divide
        assertNumber("0", "0 / 2% /", "0 ÷ 0,02 ÷ ");
        assertNumber("Cannot divide by zero", "2 / 0% /", "2 ÷ 0 ÷ ");
        assertNumber("0", "0 / 2%  /", "0 ÷ negate(0,02) ÷ ");
        assertNumber("Cannot divide by zero", "2  / 0% /", "-2 ÷ 0 ÷ ");

        assertNumber("66,66666666666667", "2 / 3% /", "2 ÷ 0,03 ÷ ");
        assertNumber("-66,66666666666667", "2 / 3%  /", "2 ÷ negate(0,03) ÷ ");
        assertNumber("-66,66666666666667", "2  / 3% /", "-2 ÷ 0,03 ÷ ");
        assertNumber("66,66666666666667", "2  / 3%  /", "-2 ÷ negate(0,03) ÷ ");

        assertNumber("666,6666666666667", "2 / 0,3% /", "2 ÷ 0,003 ÷ ");
        assertNumber("-666,6666666666667", "2 / 0,3%  /", "2 ÷ negate(0,003) ÷ ");
        assertNumber("-666,6666666666667", "2  / 0,3% /", "-2 ÷ 0,003 ÷ ");
        assertNumber("666,6666666666667", "2  / 0,3%  /", "-2 ÷ negate(0,003) ÷ ");

        //Both of operands with percent
        //Add
        assertNumber("0", "0% + 2% +", "0 + 0 + ");
        assertNumber("0", "2% + 0% +", "0 + 0 + ");
        assertNumber("0", "0% + 2%  +", "0 + negate(0) + ");
        assertNumber("0", "2%  + 0% +", "negate(0) + 0 + ");

        assertNumber("0", "2% + 3% +", "0 + 0 + ");
        assertNumber("0", "2% + 3%  +", "0 + negate(0) + ");
        assertNumber("0", "2%  + 3% +", "negate(0) + 0 + ");
        assertNumber("0", "2%  + 3%  +", "negate(0) + negate(0) + ");

        assertNumber("0", "2% + 0,3% +", "0 + 0 + ");
        assertNumber("0", "2% + 0,3%  +", "0 + negate(0) + ");
        assertNumber("0", "2%  + 0,3% +", "negate(0) + 0 + ");
        assertNumber("0", "2%  + 0,3%  +", "negate(0) + negate(0) + ");
        //Subtract
        assertNumber("0", "0% - 2% -", "0 - 0 - ");
        assertNumber("0", "2% - 0% -", "0 - 0 - ");
        assertNumber("0", "0% - 2%  -", "0 - negate(0) - ");
        assertNumber("0", "2%  - 0% -", "negate(0) - 0 - ");

        assertNumber("0", "2% - 3% -", "0 - 0 - ");
        assertNumber("0", "2% - 3%  -", "0 - negate(0) - ");
        assertNumber("0", "2%  - 3% -", "negate(0) - 0 - ");
        assertNumber("0", "2%  - 3%  -", "negate(0) - negate(0) - ");

        assertNumber("0", "2% - 0,3% -", "0 - 0 - ");
        assertNumber("0", "2% - 0,3%  -", "0 - negate(0) - ");
        assertNumber("0", "2%  - 0,3% -", "negate(0) - 0 - ");
        assertNumber("0", "2%  - 0,3%  -", "negate(0) - negate(0) - ");
        //Multiply
        assertNumber("0", "0% x 2% x", "0 x 0,02 x ");
        assertNumber("0", "2% x 0% x", "0 x 0 x ");
        assertNumber("0", "0% x 2%  x", "0 x negate(0,02) x ");
        assertNumber("0", "2%  x 0% x", "negate(0) x 0 x ");

        assertNumber("0", "2% x 3% x", "0 x 0,03 x ");
        assertNumber("0", "2% x 3%  x", "0 x negate(0,03) x ");
        assertNumber("0", "2%  x 3% x", "negate(0) x 0,03 x ");
        assertNumber("0", "2%  x 3%  x", "negate(0) x negate(0,03) x ");

        assertNumber("0", "2% x 0,3% x", "0 x 0,003 x ");
        assertNumber("0", "2% x 0,3%  x", "0 x negate(0,003) x ");
        assertNumber("0", "2%  x 0,3% x", "negate(0) x 0,003 x ");
        assertNumber("0", "2%  x 0,3%  x", "negate(0) x negate(0,003) x ");
        //Divide
        assertNumber("0", "0% / 2% /", "0 ÷ 0,02 ÷ ");
        assertNumber("Result is undefined", "2% / 0% /", "0 ÷ 0 ÷ ");
        assertNumber("0", "0% / 2%  /", "0 ÷ negate(0,02) ÷ ");
        assertNumber("Result is undefined", "2%  / 0% /", "negate(0) ÷ 0 ÷ ");

        assertNumber("0", "2% / 3% /", "0 ÷ 0,03 ÷ ");
        assertNumber("0", "2% / 3%  /", "0 ÷ negate(0,03) ÷ ");
        assertNumber("0", "2%  / 3% /", "negate(0) ÷ 0,03 ÷ ");
        assertNumber("0", "2%  / 3%  /", "negate(0) ÷ negate(0,03) ÷ ");

        assertNumber("0", "2% / 0,3% /", "0 ÷ 0,003 ÷ ");
        assertNumber("0", "2% / 0,3%  /", "0 ÷ negate(0,003) ÷ ");
        assertNumber("0", "2%  / 0,3% /", "negate(0) ÷ 0,003 ÷ ");
        assertNumber("0", "2%  / 0,3%  /", "negate(0) ÷ negate(0,003) ÷ ");

        //Complicate tests
        assertNumber("0", "8 %", "0");
        assertNumber("0", "8 sqr %", "0");
        assertNumber("-63,36", "12  - % =", "");
        assertNumber("8,19", "9 - % +", "9 - 0,81 + ");
        assertNumber("933 728,4", "99864 + 835 % =", "");
        assertNumber("20 387 773,44", "3456 + 768 % =", "");
        assertNumber("-4,75", "3 - 8 + % x", "3 - 8 + 0,25 x ");
        assertNumber("118,8750238140598", "99836 / 83984 % =", "");
        assertNumber("8 067,78401026", "8769 + ,647554 % - 758 =", "");
        assertNumber("-302 362 537,16", "99735 - 34667 - 464787 % =", "");
        assertNumber("5,090573839195512e+20", "9089489 + 677873%%% =", "");
        assertNumber("7 637 115 857 243 435", "99989  + 76387863 %=", "");
        assertNumber("173,4717414393651", "0,99999999 + 8988 % = + % =", "");
        assertNumber("296 372 714 496 824,9", "7848556437 x 3776143% =", "");
        assertNumber("0,0065708080972353", "0,8798798798 x ,7467846746 %=", "");
        assertNumber("1 236 598,371878092", "1224354,835764 + 0,999999 % = ", "");
        assertNumber("2 279 616 337 722,047", "879879896 + 256576% + ,93763 % =", "");
        assertNumber("686 208 555 634,0563", "927767657 - 7455,2342 + 73864 % =", "");
        assertNumber("-17,3578685327433", "284 √ + 3 % = ", "negate(17,3578685327433)");
        assertNumber("-234,21", "100 + 88 - 11 + 34 - % +", "100 + 88 - 11 + 34 - 445,21 + ");
        assertNumber("13 967 298 779,78502", "8867675,78776 += 78754 %", "13967298779,78502");
        assertNumber("1,662072360057162e+35", "66847688683,9 x 76482546 % + 7874 % = % =", "");
        assertNumber("701 996 997 725,4007", "8378478,12333 + ,9829839898476 x % + 7876376 =", "");
        assertNumber("1,03122e-13", "0,0000000000001011 + 2 % - ", "0,0000000000001011 + 2,022e-15 - ");
        assertNumber("-28 638,844416", "984 - 356 % + 876 - % - ", "984 - 3503,04 + 876 - 26995,804416 - ");
        assertNumber("-3,08641975312477e+29", "5555555555555555 + 34566 - % +", "5555555555555555 + 34566 - 3,086419753124826e+29 + ");
        assertNumber("9,999999999999999e+28", "9999999999999999 + 1000000000000000 %", "9999999999999999 + 9,999999999999999e+28");
        assertNumber("-9,776196932024121e+17", "987987 + 664 - 3,3335 + 999999,999999 % - % +", "987987 + 664 - 3,3335 + 9886476664,990114 - 9,776197030898774e+17 + ");
    }

    @Test
    void checkMemory() {
        //
        assertNumber("5", "5  88  ", "");
        assertNumber("100", "12  88   ", "");
        assertNumber("188", "12  88    ", "");
        assertNumber("376", "12  88      ", "");
        //
        assertNumber("-5", "5  88  ", "");
        assertNumber("-100", "12  88   ", "");
        assertNumber("-188", "12  88    ", "");
        assertNumber("0", "12  88      ", "");
        //Mix
        assertNumber("7", "7   ", "");
        assertNumber("0", "24   ", "");
        assertNumber("256", " 256  876  ", "");
        assertNumber("776", " 776  ", "");
        assertNumber("95", "67  95    ", "");
        assertNumber("18 693", "6231    56  ", "");
        assertNumber("99 640", "98765      875   ", "");
        assertNumber("99 640,75136", "0,88776  0,1364  =  = ", "");
        assertNumber("2", "1  0,9999999999999999  0  ", "");
        assertNumber("29 999 996,99981057", "9999999    0,00018943  898  ", "");
        assertNumber("99 999 999", " 9,9999999999999911   99999999  99999999  ", "");
        assertNumber("9 999 999 999 999 999", "9999999999999999  9999999999999999   00000000000001  ", "");
        assertNumber("1,8e+18", "9999999999999999                    ", "");

    }

    @Test
    void checkAllOperations() {
        //Unary with binary
        //Square with binary
        assertNumber("28", "5  + 3 -", "sqr(5) + 3 - ");
        assertNumber("22", "5  - 3 x", "sqr(5) - 3 x ");
        assertNumber("75", "5  x 3 /", "sqr(5) x 3 ÷ ");
        assertNumber("8,333333333333333", "5  / 3 +", "sqr(5) ÷ 3 + ");
        //Square root with binary
        assertNumber("5,23606797749979", "5 √ + 3 -", "√(5) + 3 - ");
        assertNumber("-0,7639320225002103", "5 √ - 3 x", "√(5) - 3 x ");
        assertNumber("6,708203932499369", "5 √ x 3 /", "√(5) x 3 ÷ ");
        assertNumber("0,7453559924999299", "5 √ / 3 +", "√(5) ÷ 3 + ");
        //One divide x with binary
        assertNumber("3,2", "5  + 3 -", "1/(5) + 3 - ");
        assertNumber("-2,8", "5  - 3 x", "1/(5) - 3 x ");
        assertNumber("0,6", "5  x 3 /", "1/(5) x 3 ÷ ");
        assertNumber("0,0666666666666667", "5  / 3 +", "1/(5) ÷ 3 + ");
        //Binary with unary
        //Add with unary
        assertNumber("14", "5 + 3  -", "5 + sqr(3) - ");
        assertNumber("6,732050807568877", "5 + 3 √ -", "5 + √(3) - ");
        assertNumber("5,333333333333333", "5 + 3  -", "5 + 1/(3) - ");
        //Subtract with unary
        assertNumber("-4", "5 - 3  -", "5 - sqr(3) - ");
        assertNumber("3,267949192431123", "5 - 3 √ -", "5 - √(3) - ");
        assertNumber("4,666666666666667", "5 - 3  -", "5 - 1/(3) - ");
        //Multiply with unary
        assertNumber("45", "5 x 3  -", "5 x sqr(3) - ");
        assertNumber("8,660254037844386", "5 x 3 √ -", "5 x √(3) - ");
        assertNumber("1,666666666666667", "5 x 3  -", "5 x 1/(3) - ");
        //Divide with unary
        assertNumber("0,5555555555555556", "5 / 3  -", "5 ÷ sqr(3) - ");
        assertNumber("2,886751345948129", "5 / 3 √ -", "5 ÷ √(3) - ");
        assertNumber("15", "5 / 3  -", "5 ÷ 1/(3) - ");

        //Mix all operations
        assertNumber("5", "2  3 +  - ", "3 + 2 - ");
        assertNumber("-2,23606797749979", "5 √  -", "negate(√(5)) - ");
        assertNumber("2 906 811,3127248", "878,756 + 45,63 %  x 78  =", "");
        assertNumber("2,009861478780531", ",883655 x 0,74553 % + 0,99999 + √ =", "");
        assertNumber("7,666666666666667", "3   + 8 -", "negate(1/(3)) + 8 - ");
        assertNumber("6 639 379 852 195,999", "985948894 - 7453628  x 6734 = ", "");
        assertNumber("-59", "5 + 8 -", "5 + negate(negate(negate(sqr(8)))) - ");
        assertNumber("2 298 455 479 608,324", "4376 + 7987 - % + 8,9 = ", "sqr(-1516065,79)");
        assertNumber("59,02888304444444", "56 x %  789 %  / 0,55   89   + 0,99999 % = ", "");
        assertNumber("1,e-9999", "0,0000000000000001  x ,0000001 =x 0,000000000000001 =", "");
        assertNumber("-36,799632", "2  + 6  - 8 % x 0,999999   /", "sqr(2) + sqr(6) - 3,2 x -0,99999 ÷ ");
        assertNumber("1 070 281,69789316", "675  + 784  + ,8354  - ", "sqr(675) + sqr(784) + sqr(0,8354) - ");
        assertNumber("2,520299604187571e+25", "987987 + 7% - 3878 x 476746% + 99877% = ", "sqr(5020258563249,079)");
        assertNumber("-1,628217149182808e-8", "4567  + 78956,00009 x 64785 √  =   x 7536    √  =", "");
        assertNumber("0,0178571428571429", "87 + 324 - 443 / ,56   √  %", "87 + 324 - 443 ÷ 0,0178571428571429");
        assertNumber("9,999999999999999e+9999", "1000000000000000  x 1000000000 = 9999999999999999 x  = ", "");
        assertNumber("6 812 439,685034685", "+ 8787 % - 78476 x 676,1274 % = + 7467467,12 - 657637 + √ -", "-8,615722004179622e-5 + 7467467,12 - 657637 + √(6809830,119913843) - ");


    }

    void assertNumber(String result, String buttonsPressed, String outOperationMemoryResult) {
        System.out.println("assertNumber(\""+buttonsPressed+"\", \""+result+"\", \""+outOperationMemoryResult+"\");");
    }

//    void assertNumber(String buttonsPressed, String result,  String outOperationMemoryResult) {
////        System.out.println("assertNumber(\""+buttonsPressed+"\", \""+result+"\", \""+outOperationMemoryResult+"\");");
//
//        checkMouseInputNumber(result, buttonsPressed, outOperationMemoryResult);
//    }

    void checkKeyInputNumber(String result, String buttonsPressed, String outOperationMemoryResult) {
        for (char idButton : buttonsPressed.toCharArray()) {
            keyboardInput(String.valueOf(idButton));
        }

        assertEquals(result, outLabel.getText());
        assertEquals(outOperationMemoryResult, outOperationMemory.getText());
        type(ESCAPE);
    }


    void checkNumpadInputNumber(String result, String buttonsPressed, String outOperationMemoryResult) {
        for (char idButton : buttonsPressed.toCharArray()) {
            keyboardNumpadInput(String.valueOf(idButton));
        }

        assertEquals(result, outLabel.getText());
        assertEquals(outOperationMemoryResult, outOperationMemory.getText());
        type(ESCAPE);
    }


    void checkMouseInputNumber(String result, String buttonsPressed, String outOperationMemoryResult) {
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
        } else if (idButtonClickedMouse.equals("")) {
            button = from(root).lookup("#ONE_DIVIDE_X").query();
        } else if (idButtonClickedMouse.equals("")) {
            button = from(root).lookup("#SQR").query();
        } else if (idButtonClickedMouse.equals("/")) {
            button = from(root).lookup("#DIVIDE").query();
        } else if (idButtonClickedMouse.equals("=")) {
            button = from(root).lookup("#EQUAL").query();
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