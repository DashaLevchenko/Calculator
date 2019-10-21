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
    void checkInput() {
        assertNumber("0", " +/-", "");
        assertNumber("0", " bs", "");
        assertNumber("0", "1 bs", "");
        assertNumber("0", "01 bs", "");
        assertNumber("1", "001", "");
        assertNumber("5", "000005", "");
        assertNumber("1 111", "11111 bs", "");
        assertNumber("-1 111", "11111 +/- bs", "");
        assertNumber("0", "11111 +/- bs bs bs bs bs", "");
        assertNumber("-6", "0000006 +/-", "");
        assertNumber("7", "00000007", "");
        assertNumber("11 111", "111111 +/- bs  +/-", "");
        assertNumber("11 111 111", "11111111, bs", "");
        assertNumber("9", "0000000009", "");
        assertNumber("0", "11111 +/- bs  bs  bs  bs  bs", "");
        assertNumber("11", "000000000011", "");
        assertNumber("-111 111 111", "111111111, bs +/-", "");
        assertNumber("-1 111 111 111,", "1111111111, bs  +/- ,", "");
        assertNumber("123", "000000000000123", "");
        assertNumber("12 345", "0000000000000012345", "");
        assertNumber("1 111 111 111 111 111", "1111111111111111, bs", "");
        assertNumber("12 345 678", "000000000000000012345678", "");
        assertNumber("1 111 111 111 111 111", "1111111111111111,111111 bs", "");
        assertNumber("123 456 789", "0000000000000000123456789", "");
        assertNumber("0,9999999999999999", "0,9999999999999999", "");
        assertNumber("0,9999999999999999", "0,9999999999999999999", "");
        assertNumber("9,999999999999999", "9,999999999 , 999999 , 9", "");
        assertNumber("-222,3333333333333", "222,3333333333333 +/-", "");
        assertNumber("222,3333333333333", "222,3333333333333 +/- +/-", "");
        assertNumber("-222,3333333333333", "222,3333333333333 +/- 44", "");
        assertNumber("222,3333333333333", "222,3333333333333 +/- +/- 33,", "");
        assertNumber("9 999 999 999 999 999", "99999999999999999", "");
        assertNumber("999 999 999 999 999,9", "999999999999999,99999", "");
        assertNumber("9 999 999 999 999 999,", "9999999999999999,9999", "");
        assertNumber("-9 999 999 999 999 999,", "9999999999999999,9999 +/-", "");
        assertNumber("9 999 999 999 999 999,", "9999999999999999 , 9999 +/- +/-", "");
        assertNumber("9 999 999 999,999999", "9999999999,999999 , 9999", "");
        assertNumber("-9 999 999 999,999999", "9999999999,999999 , 9999 +/-", "");
        assertNumber("-9 999 999 999,999999", "9999999999,999999 , 9999 +/- +/- +/- +/- +/- 99", "");
        assertNumber("-1", "1111111111111111,111111 +/- bs bs bs bs bs bs bs bs bs bs bs bs bs bs bs bs", "");
        assertNumber("1,111111111111111", "1111111111111111,111111 +/- bs bs bs bs bs bs bs bs bs bs bs bs bs bs bs bs  +/- ,1111111111111111001", "");
    }

    @Test
    void checkBinaryOperations() {
        //O1/10
        // ne operand
        //One operand with one operation
        assertNumber("2", "2 =", "");
        //One operand and change operation with equal
        assertNumber("1", "2 + - * / =", "");
        assertNumber("4", "2 - * / + =", "");
        assertNumber("0", "2 * / + - =", "");
        assertNumber("4", "2 / + - * =", "");

        //One operand and change operation with several equal
        assertNumber("0,2", "5 + - * / = =", "");
        assertNumber("20", "5 - * / + = = =", "");
        assertNumber("-15", "5 * / + - = = = =", "");
        assertNumber("15 625", "5 / + - * = = = = =", "");
        //Two operands

        //Two operands with one operation and equal
        assertNumber("104", "71 + 33 =", "");
        assertNumber("20", "19 + 0,9999999999999999 =", "");
        assertNumber("26", "43 - 17 =", "");
        assertNumber("261", "87 * 3 =", "");
        assertNumber("3,333333333333333", "10 / 3 =", "");

        //region combinations two operands with equal
        assertNumber("39,998", "19 + 0,999 + =", "");
        assertNumber("0", "239 + ,9 - =", "");
        assertNumber("904 401", "77 + 874 * =", "");
        assertNumber("1", "122 + 95 / =", "");
        assertNumber("0", "9 - 873 - =", "");
        assertNumber("-222", "876 - 987 + =", "");
        assertNumber("330 613,5001", "9,99 +/- - 565 * =", "");
        assertNumber("1", "98,7 - 856 / =", "");
        assertNumber("3 999 200,04", "2 * 999,9 * =", "");
        assertNumber("-243,54", "123 +/- * ,99 + =", "");
        assertNumber("0", "65 * 987 - =", "");
        assertNumber("1", "998 * 6 / =", "");
        assertNumber("1", "234,1 / 76 +/- / =", "");
        assertNumber("199,8199819981998", "666 / 6,666 + =", "");
        assertNumber("0", "0 / 786,87 - =", "");
        assertNumber("416 611,5702479339", "213 / ,33 * =", "");
        //endregion
        //Two operands, one operation and change operation with equal
        assertNumber("1", "241 + 998 - * / =", "");
        assertNumber("-50", "98 - 73, * / + = +/-", "negate(50)");
        assertNumber("0", "2 * 561 / + - =", "");
        assertNumber("0,1111111111111111", "1 / 3 + - * =", "");
//Three operands
        //combinations three operands with equal
        assertNumber("8 862 769,64539", "8787876 + 74893 + ,64539 =", "");
        assertNumber("1", "1 + 0,9999999999999999 - 0,9999999999999999 =", "");
        assertNumber("1 925 066 427,985", "34156,745 +/- +/- + 388656 * 45532 bs =", "");
        assertNumber("-1 556,844780280956", ",77765544 + 7785 +/- / 4,999999 =", "");
        assertNumber("19", "19 - 0,9999999999999999 + 0,9999999999999999 =", "");
        assertNumber("1 046 461", "997655 - 38748, +  bs bs 87554 =", "");
        assertNumber("48 688 021 125,732", "27786, , 999 - 768492,000000 * 65732 +/-  =", "");
        assertNumber("631 210,0063121001", "887754 - 256544 / 0,99999999 =", "");
        assertNumber("1,899999998", "0,999999999 * 2 - 0,1 =", "");
        assertNumber("1,9", "0,9999999999999999 * 2 - 0,1 =", "");
        assertNumber("-1,658062522813398", "0,9999999999999999 / 0,77644 / 0,776767767666 +/- =", "");
        assertNumber("637 925,889675519", "288743 bs +/- , 00000001 / 88985455 + 637925,889999999 =", "");
        assertNumber("8,000000000000001", "9 / 0,9999999999999999 - 0,9999999999999999 =", "");
        assertNumber("1 000", "1000 / 2,9999999999999999647 * 3 =", "");
//Four operands
        //combinations Four operands with equal
        assertNumber("200 024,2", "199999 + 0,999999999999999999999 + 24,2 + 0,00000000000001 =", "");
        assertNumber("9 999 998,0000001", "9999999 + 9999999 - 9999999 * 0,9999999 =", "");
        assertNumber("0,0617809442729949", "867,8333 + 3 * 4 / 56382 = bs bs bs bs bs bs bs", "");
        assertNumber("455 667 995,6", "2332, + 3 / 0,5 + 455663325,6 = +/- +/- +/- bs +/-", "negate(negate(negate(negate(455667995,6))))");
        assertNumber("-690,57", "86 - 735 - 8,57 - 33 =", "");
        assertNumber("-357 684,1186736475", "23 +/- 1 - 65 bs 43 * 2345 / 5,73 =", "");
        assertNumber("84,77860962566845", "43 - 87678 +/- bs ,4 / 748 + 73 =", "");
        assertNumber("-3 929", "2 - 3957 + 84 - 7 bs 58 =", "");
        assertNumber("0", "8635 * 88 * 65,6 * 0,1234 bs bs bs bs +/- =", "");
        assertNumber("8 814", "233 * 38 + 4 - 44 =", "");
        assertNumber("-50 623 218 535 195,59", "97548354766 +/- , bs 452 * 3874 - 4 / 7465 =", "");
        assertNumber("88,54373407202216", "973 * 9 / 8664 + 87,533 =", "");
        assertNumber("1", "986 / 76 / 835 / =", "");
        assertNumber("-789,5563005780347", "972 / 865 + 85,32 - 876 =", "");
        assertNumber("4 055,773333333333", "7784 / 84 - ,49 * 44 =", "");
        assertNumber("35,8938327603227", "297 / 36 * 409 / 94,0064 =", "");


    }

    @Test
    void checkOperationMemory() {
        //One operation
        assertNumber("-2", "2 = +/-", "negate(2)");
        assertNumber("3", "3 +", "3 + ");
        assertNumber("4", "4 -", "4 - ");
        assertNumber("5", "5 *", "5 x ");
        assertNumber("6", "6 /", "6 ÷ ");
        //Two operations
        assertNumber("13", "13 + - * /", "13 ÷ ");
        assertNumber("16", "16 - * / +", "16 + ");
        assertNumber("18", "18 * / + -", "18 - ");
        assertNumber("19", "19 / + - *", "19 x ");
        assertNumber("33", "24 + 33", "24 + ");
        assertNumber("47", "37 - 47", "37 - ");
        assertNumber("55", "49 * 55", "49 x ");
        assertNumber("69", "52 / 69", "52 ÷ ");
        assertNumber("728", "54 + 674 -", "54 + 674 - ");
        assertNumber("-128", "96 +/- - 32 *", "-96 - 32 x ");
        assertNumber("-3 234", "98 * 33 +/- /", "98 x -33 ÷ ");
        assertNumber("4,333333333333333", "13 / 3 -", "13 ÷ 3 - ");
        assertNumber("8 784", "5612 + 764 - 8784", "5612 + 764 - ");
        assertNumber("3", "2 - 98,52 +/- * 3", "2 - -98,52 x ");
        assertNumber("-0,345564", "6556 +/- * 842 +/- / ,3455 +/- 64", "-6556 x -842 ÷ ");
        assertNumber("0,6", "7767 / 844 - +/-  +/- bs bs bs ,6", "7767 ÷ 844 - negate(negate(9,20260663507109))");
        //three operations
        assertNumber("-10 197 348", "67543 + 98 +/- - 54321 * 777, +/- +/- 0 +/- + ", "67543 + -98 - 54321 x -777 + ");
        assertNumber("999,2", "99999 - 0,99999 * 98764,0 / 999,213466 bs bs bs bs bs", "99999 - 0,99999 x 98764 ÷ ");
        assertNumber("0,9826", "76165 * 87,198 / 98751 + 0,9826", "76165 x 87,198 ÷ 98751 + ");
        assertNumber("9 834 216", "316789 / 31245 + 98712 - 9834216", "316789 ÷ 31245 + 98712 - ");
        assertNumber("5,292214940134438e+73", "555555555555555 * = = = = -", "5,292214940134438e+73 - ");


    }
        @Test
        void checkUnaryOperations () {
            //one operation
            assertNumber("0", "0 sqrt", "√(0)");
            assertNumber("0", "0 sqr", "sqr(0)");
            assertNumber("1", "1 sqrt", "√(1)");
            assertNumber("1", "1 sqr", "sqr(1)");
            assertNumber("1", "1 1/x", "1/(1)");
            assertNumber("547,56", "23,4 sqr", "sqr(23,4)");
            assertNumber("-7,071067811865475", "50 sqrt +/-", "negate(√(50))");
            assertNumber("0,0017789321070561", "562,135 1/x", "1/(562,135)");
            //two operation
            assertNumber("5,259663116753397", "765,3 sqrt +/- +/- sqrt ", "√(negate(negate(√(765,3))))");
            assertNumber("952 217 706 900 625", "5555 sqr sqr", "sqr(sqr(5555))");
            assertNumber("9 009", "9009 1/x 1/x", "1/(1/(9009))");
            assertNumber("15 678", "15678 sqrt sqr ", "sqr(√(15678))");
            assertNumber("1,000020000300004e-10", "99999 sqr 1/x", "1/(sqr(99999))");
            assertNumber("0,00108190582506", "854321 1/x sqrt", "√(1/(854321))");
            assertNumber("-0,0010000005000004", "999999 sqrt 1/x  +/-", "negate(1/(√(999999)))");
            assertNumber("9 874 562", "9874562 sqr sqrt", "√(sqr(9874562))");
            assertNumber("1,00000020000003e-14", "9999999 1/x sqr", "sqr(1/(9999999))");
            //three operation
            assertNumber("9,956168238447662", "96546784,32415 sqrt sqrt sqrt ", "√(√(√(96546784,32415)))");
            assertNumber("9 999,9999500005", "99999999,00001 sqrt sqrt sqr ", "sqr(√(√(99999999,00001)))");
            assertNumber("0,0056425927787405", "986472913,876543 sqrt sqrt 1/x ", "1/(√(√(986472913,876543)))");
            assertNumber("31 622,77658587241", "999999999 sqrt sqr sqrt ", "√(sqr(√(999999999)))");
            assertNumber("0,0032577870844877", "8877878787,65 sqrt 1/x sqrt ", "√(1/(√(8877878787,65)))");
            assertNumber("99 999,999995", "9999999999 sqr sqrt sqrt ", "√(√(sqr(9999999999)))");
            assertNumber("0,0017837042774312", "98788998999,874 1/x sqrt sqrt ", "√(√(1/(98788998999,874)))");
            assertNumber("2,036161793239094e+84", "34562212456 sqr sqr sqr ", "sqr(sqr(sqr(34562212456)))");
            assertNumber("9,9999999998e+21", "99999999999 sqr sqr sqrt +/- +/-", "negate(negate(√(sqr(sqr(99999999999)))))");
            assertNumber("2,596893369539549e-48", "787746478889,98787 sqr sqr 1/x ", "1/(sqr(sqr(787746478889,9878)))");
            assertNumber("9,99999999999537e+23", "999999999999,768565 sqr sqrt sqr ", "sqr(√(sqr(999999999999,7685)))");
            assertNumber("2,598079199785716e-52", "7876565765566 sqr 1/x sqr ", "sqr(1/(sqr(7876565765566)))");
            assertNumber("9,999999999999998e+25", "9999999999999,999999 sqrt sqr sqr ", "sqr(sqr(√(9999999999999,999)))");
            assertNumber("1,060361063588689e-56", "98545445454546 1/x sqr sqr ", "sqr(sqr(1/(98545445454546)))");
            assertNumber("1,00000000000001e-14", "99999999999999 1/x 1/x 1/x ", "1/(1/(1/(99999999999999)))");
            assertNumber("25 991 214,58828613", "675543235774338 1/x 1/x sqrt ", "√(1/(1/(675543235774338)))");
            assertNumber("9,99999999999998e+29", "999999999999999 1/x 1/x sqr ", "sqr(1/(1/(999999999999999)))");
            assertNumber("8,100000065610006e-16", "1234567891234567 sqr sqrt 1/x ", "1/(√(sqr(1234567891234567)))");
            assertNumber("1,173436304521581e+31", "3425545656565653 1/x sqr 1/x ", "1/(sqr(1/(3425545656565653)))");
            assertNumber("6 654 638 824 545 454", "6654638824545454 sqr = sqrt", "√(4,42842178851477e+31)");
            assertNumber("-999 998 000 001,0002", "999999,0000000001 +/- sqr 1/x 1/x +/-", "negate(1/(1/(sqr(-999999,0000000001))))");

        }

        @Test
        void checkOperationsEnotationValid () {
            //e-
            //if scale number more then 16 and count of zero more 2, ÷
            assertNumber("1,1111111111111e-4", "0,0011111111111111 / 10 +", "0,0011111111111111 ÷ 10 + ");
            assertNumber("-1,1111111111111e-4", "0,0011111111111111 +/- / 10 +", "-0,0011111111111111 ÷ 10 + ");
            assertNumber("-1,1111111111111e-4", "0,0011111111111111 / 10 +/- -", "0,0011111111111111 ÷ -10 - ");
            assertNumber("9,9999999999999e-4", "0,0099999999999999 / 10 +", "0,0099999999999999 ÷ 10 + ");
            assertNumber("1,111111111111111e-4", "0,1111111111111111 / 1000 -", "0,1111111111111111 ÷ 1000 - ");
            //number less 0,0000000000000001
            assertNumber("1,e-17", "0,0000000000000001 / 10 +", "0,0000000000000001 ÷ 10 + ");
            assertNumber("-1,e-17", "0,0000000000000001 / 10 +/- +", "0,0000000000000001 ÷ -10 + ");
            assertNumber("1,1e-16", "0,0000000000000011 / 10 +", "0,0000000000000011 ÷ 10 + ");
            assertNumber("9,e-17", "0,0000000000000009 / 10 +", "0,0000000000000009 ÷ 10 + ");
            assertNumber("1,e-18", "0,0000000000000001 / 100 +", "0,0000000000000001 ÷ 100 + ");
            assertNumber("-1,e-18", "0,0000000000000001 / 100 +/- +", "0,0000000000000001 ÷ -100 + ");
            assertNumber("1,e-30", "0,000000000000001 / 10000000000000000000 +", "0,000000000000001 ÷ 1000000000000000 + ");
            assertNumber("1,e-31", "0,0000000000000001 / 10000000000000000000 +", "0,0000000000000001 ÷ 1000000000000000 + ");
            assertNumber("1,111111111111111e-16", "0,1111111111111111 / 1000000000000000 +", "0,1111111111111111 ÷ 1000000000000000 + ");
            //e+
            assertNumber("1,e+16", "9999999999999999 + 1 +", "9999999999999999 + 1 + ");
            assertNumber("1,e+16", "9999999999999999 + 2 +", "9999999999999999 + 2 + ");
            assertNumber("-1,e+16", "9999999999999999 +/- - 2 =", "");
            assertNumber("2,e+16", "9999999999999999 + =", "");
            assertNumber("3,086419753086419e+31", "5555555555555555 * =", "");
            assertNumber("1,000000000000001e+16", "9999999999999999 + 6 =", "");
            assertNumber("5,e+16", "9999999999999999 + = = = =", "");
            assertNumber("5,999999999999999e+16", "9999999999999999 + = = = = =", "");

            assertNumber("1,e-17", "0,0000000000000001 / 9,999999999999996 +", "0,0000000000000001 ÷ 9,999999999999996 + ");
            assertNumber("1,1e-16", "0,0000000000000011 / 9,999999999999996 +", "0,0000000000000011 ÷ 9,999999999999996 + ");
            assertNumber("1,e-18", "0,0000000000000001 / 99,99999999999996 +", "0,0000000000000001 ÷ 99,99999999999996 + ");
            assertNumber("1,e-30", "0,000000000000001 / 999999999999999,9 +", "0,000000000000001 ÷ 999999999999999,9 + ");
            assertNumber("1,e-31", "0,0000000000000001 / 999999999999999,6 +", "0,0000000000000001 ÷ 999999999999999,6 + ");
            assertNumber("1,111111111111111e-16", "0,1111111111111111 / 999999999999999,6 +", "0,1111111111111111 ÷ 999999999999999,6 + ");
            assertNumber("1,1111111111111e-4", "0,0011111111111111 / 9,999999999999996 +", "0,0011111111111111 ÷ 9,999999999999996 + ");
            assertNumber("1,1111111111111e-5", "0,0011111111111111 / 99,99999999999996 +", "0,0011111111111111 ÷ 99,99999999999996 + ");
            assertNumber("1,e+16", "9999999999999999 + 0,9999999999999996 +", "9999999999999999 + 0,9999999999999996 + ");

        }

        @Test
        void checkOperationsEnotationInvalid () {
            //e-
            assertNumber("0,01", "0,1 / 10 =", "");
            assertNumber("0,001", "0,01 / 10 =", "");
            assertNumber("0,0001", "0,001 / 10 =", "");
            assertNumber("0,00001", "0,0001 / 10 =", "");
            assertNumber("0,000001", "0,00001 / 10 =", "");
            assertNumber("0,0000001", "0,000001 / 10 =", "");
            assertNumber("0,00000001", "0,0000001 / 10 =", "");
            assertNumber("0,000000001", "0,00000001 / 10 =", "");
            assertNumber("0,0000000001", "0,000000001 / 10 =", "");
            assertNumber("0,00000000001", "0,0000000001 / 10 =", "");
            assertNumber("0,000000000001", "0,00000000001 / 10 =", "");
            assertNumber("0,0000000000001", "0,000000000001 / 10 =", "");
            assertNumber("0,00000000000001", "0,0000000000001 / 10 =", "");
            assertNumber("0,000000000000001", "0,00000000000001 / 10 =", "");
            assertNumber("0,0000000000000001", "0,000000000000001 / 10 =", "");
            assertNumber("0,0111111111111111", "0,1111111111111111 / 10 =", "");
            assertNumber("0,1", "0,9999999999999999 / 10 =", "");
            assertNumber("0,0011111111111111", "0,1111111111111111 / 100 =", "");
            assertNumber("0,01", "0,9999999999999999 / 100 =", "");
            assertNumber("0,0000000000000001", "0,0000000000000001 / 1 =", "");
            assertNumber("0,000000000000001", "0,0000000000000001 * 10 =", "");
            assertNumber("-0,0000000000000001", "0,0000000000000001 +/- / 1 =", "");
            assertNumber("-0,000000000000001", "0,0000000000000001 * 10 +/- =", "");
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
        void checkExceptions(){
        //Divide zero
            assertNumber("Result is undefined", "0 / 0 =", "0 ÷ ");
            assertNumber("Cannot divide by zero", "1 / 0 =", "1 ÷ ");
            assertNumber("Cannot divide by zero", "0 1/x", "1/(0)");
            assertNumber("Cannot divide by zero", "0 sqrt 1/x", "1/(√(0))");
            assertNumber("Cannot divide by zero", "2 + 4 - 1 + 3 / 0 =", "2 + 4 - 1 + 3 ÷ ");
            assertNumber("Cannot divide by zero", "4 + 5 - 9 + 1/x ", "4 + 5 - 9 + 1/(0)");
        //Square root negative number
            assertNumber("Invalid input", "1 +/- sqrt", "√(-1)");
            assertNumber("Invalid input", "2 - 3 = sqrt", "√(-1)");
            assertNumber("Invalid input", "2 + 3 = +/- sqrt", "√(negate(5))");
        //Overflow
            assertNumber("Overflow", "9999999999999999 sqr  sqr  sqr  sqr  sqr  sqr  sqr  sqr  sqr  sqr ", "sqr(sqr(sqr(sqr(sqr(sqr(sqr(sqr(sqr(sqr(9999999999999999))))))))))");
            assertNumber("Overflow", "9999999999999999 / 1000000000000000 = = = sqr  sqr  sqr  sqr  sqr  sqr  sqr  sqr sqr", "sqr(sqr(sqr(sqr(sqr(sqr(sqr(sqr(sqr(9,999999999999999e-30)))))))))");
            assertNumber("Overflow", "9999999999999999 * = = = = = = = = = = = = = = = * = = = = = = * = = = = + = = = = = = = = = = + = + = * =", "4,399999999999754e+8961 x ");

    }

    @Test
    void checkPercent(){
        assertNumber("0", "8 %", "0");
        assertNumber("0", "0 + 1 %", "0 + 0");
        assertNumber("0", "0 * 1 %", "0 + 0,01");
        assertNumber("0", "2 + 7 = %", "0 + 0,01");
        assertNumber("0,81", "32 + 8 +/- % ", "0,81");
        assertNumber("0", "8 sqr %", "0");
        assertNumber("-63,36", "12 sqr - % =", "");
        assertNumber("8,19", "9 - % +", "9 - 0,81 + ");
        assertNumber("-4,75", "3 - 8 + % *", "3 - 8 + 0,25 x ");
        assertNumber("-28 638,844416", "984 - 356 % + 876 - % - ", "984 - 3503,04 + 876 - 26995,804416 - ");
        assertNumber("-17,3578685327433", "284 sqrt + 3 % = +/-", "negate(17,3578685327433)");
        assertNumber("-234,21", "100 + 88 - 11 + 34 - % +", "100 + 88 - 11 + 34 - 445,21 + ");
        assertNumber("1,03122e-13", "0,0000000000001011 + 2 % - ", "0,0000000000001011 + 2,022e-15 - ");
        assertNumber("9,999999999999999e+28", "9999999999999999 + 1000000000000000 %", "9999999999999999 + 9,999999999999999e+28");
        assertNumber("-3,08641975312477e+29", "5555555555555555 + 34566 - % +", "5555555555555555 + 34566 - 3,086419753124826e+29 + ");
        assertNumber("-9,776196932024121e+17", "987987 + 664 - 3,3335 + 999999,999999 % - % +", "987987 + 664 - 3,3335 + 9886476664,990114 - 9,776197030898774e+17 + ");
    }

    @Test
    void checkAllOperations(){
        assertNumber("0,0178571428571429", "87 + 324 - 443 / ,56 +/- sqr sqrt 1/x %", "87 + 324 - 443 ÷ 0,0178571428571429");
    //1,785714285714286 0,0178571428571429
    }

        void assertNumber (String result, String buttonsPressed, String outOperationMemoryResult){
            checkKeyInputNumber(result, buttonsPressed, outOperationMemoryResult);
        }

        void checkKeyInputNumber (String result, String buttonsPressed, String outOperationMemoryResult){
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

        private boolean isNumber (String buttonPressed){
            try {
                new BigDecimal(buttonPressed.replace(",", "."));
            } catch (Exception e) {
                return false;
            }
            return true;
        }

        void checkNumpadInputNumber (String result, String buttonsPressed, String clearButtonPressed){
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


        void checkMouseInputNumber (String result, String buttonsPressed, String clearButtonPressed){
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
//        keyboardInput(String.valueOf(clearButtonPressed));
            type(ESCAPE);
        }


        void keyboardNumpadInput (String idButton){
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
            } else if (idButton.equals("sqrt")) {
                push(new KeyCodeCombination(DIGIT2, KeyCombination.SHIFT_DOWN));
            } else if (idButton.equals("%")) {
                push(new KeyCodeCombination(DIGIT5, KeyCombination.SHIFT_DOWN));
            } else if (idButton.equals("1/x")) {
                type(R);
            } else if (idButton.equals("sqr")) {
                type(Q);
            } else if (idButton.equals("/")) {
                type(DIVIDE);
            } else if (idButton.equals("=")) {
                type(ENTER);
            }
        }

        void keyboardInput (String idButton){
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
            } else if (idButton.equals("sqrt")) {
                push(new KeyCodeCombination(DIGIT2, KeyCombination.SHIFT_DOWN));
            } else if (idButton.equals("%")) {
                push(new KeyCodeCombination(DIGIT5, KeyCombination.SHIFT_DOWN));
            } else if (idButton.equals("1/x")) {
                type(R);
            } else if (idButton.equals("sqr")) {
                type(Q);
            } else if (idButton.equals("/")) {
                type(SLASH);
            } else if (idButton.equals("=")) {
                type(ENTER);
            }

        }

        void mouseInput (String idButtonClickedMouse){
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
                button = from(root).lookup("#add").query();
            } else if (idButtonClickedMouse.equals("-")) {
                button = from(root).lookup("#subtract").query();
            } else if (idButtonClickedMouse.equals("*")) {
                button = from(root).lookup("#multiply").query();
            } else if (idButtonClickedMouse.equals("sqrt")) {
                button = from(root).lookup("#sqrt").query();
            } else if (idButtonClickedMouse.equals("%")) {
                button = from(root).lookup("#percent").query();
            } else if (idButtonClickedMouse.equals("1/x")) {
                button = from(root).lookup("#oneDivideX").query();
            } else if (idButtonClickedMouse.equals("sqr")) {
                button = from(root).lookup("#sqrX").query();
            } else if (idButtonClickedMouse.equals("/")) {
                button = from(root).lookup("#divide").query();
            } else if (idButtonClickedMouse.equals("=")) {
                button = from(root).lookup("#equal").query();
            }
            if (button != null) {
//            System.out.println(button.getBoundsInParent().getCenterX() + " " +button.getBoundsInParent().getCenterY());
                Scene scene = button.getScene();
                double sceneX = scene.getWindow().getX();
                double sceneY = scene.getWindow().getY();

                int buttonX = (int) (sceneX + button.getBoundsInParent().getCenterX());
                int buttonY = (int) (sceneY + button.getBoundsInParent().getCenterY() + button.getParent().getBoundsInParent().getMinY() + button.getParent().getParent().getLayoutY());

                robot.mouseMove(buttonX, buttonY);
//        FXTestUtils.
                try {
                    Thread.sleep(100);
                    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


//        robot.mousePress(1);

//        moveTo(buttonX, buttonY);
//            clickOn(buttonX, buttonY, MouseButton.PRIMARY);

            }
        }
    }