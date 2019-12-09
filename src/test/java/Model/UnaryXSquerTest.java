package Model;

import Model.Exceptions.DivideZeroException;
import Model.Exceptions.InvalidInputException;
import Model.Exceptions.OperationException;
import Model.Exceptions.ResultUndefinedException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class UnaryXSquerTest {
    private Unary unary = new Unary();
    private ArrayList<Object> formula;

    @Test
    void xSquare() {
        assertionXSqureValid("0.002", "0.000004");
        assertionXSqureValid("-0.002", "0.000004");

        assertionXSqureValid("0.003", "0.000009");
        assertionXSqureValid("-0.003", "0.000009");

        assertionXSqureValid("0.004", "0.000016");
        assertionXSqureValid("-0.004", "0.000016");

        assertionXSqureValid("0.004", "0.000016");
        assertionXSqureValid("-0.004", "0.000016");

        assertionXSqureValid("0.005", "0.000025");
        assertionXSqureValid("-0.005", "0.000025");

        assertionXSqureValid("0.006", "0.000036");
        assertionXSqureValid("-0.006", "0.000036");

        assertionXSqureValid("0.007", "0.000049");
        assertionXSqureValid("-0.007", "0.000049");

        assertionXSqureValid("0.008", "0.000064");
        assertionXSqureValid("-0.008", "0.000064");

        assertionXSqureValid("0.009", "0.000081");
        assertionXSqureValid("-0.009", "0.000081");

        assertionXSqureValid("0.010", "0.000100");
        assertionXSqureValid("-0.010", "0.000100");

        assertionXSqureValid("0.00011", "1.21E-8");
        assertionXSqureValid("-0.00011", "1.21E-8");

        assertionXSqureValid("0.00043", "1.849E-7");
        assertionXSqureValid("-0.00043", "1.849E-7");

        assertionXSqureValid("0.00098", "9.604E-7");
        assertionXSqureValid("-0.00098", "9.604E-7");

        assertionXSqureValid("0.00152", "0.0000023104");
        assertionXSqureValid("-0.00152", "0.0000023104");

        assertionXSqureValid("0.00278", "0.0000077284");
        assertionXSqureValid("-0.00278", "0.0000077284");

        assertionXSqureValid("0.00367", "0.0000134689");
        assertionXSqureValid("-0.00367", "0.0000134689");

        assertionXSqureValid("0.00547", "0.0000299209");
        assertionXSqureValid("-0.00547", "0.0000299209");

        assertionXSqureValid("0.00562", "0.0000315844");
        assertionXSqureValid("-0.00562", "0.0000315844");

        assertionXSqureValid("0.00679", "0.0000461041");
        assertionXSqureValid("-0.00679", "0.0000461041");

        assertionXSqureValid("0.00742", "0.0000550564");
        assertionXSqureValid("-0.00742", "0.0000550564");

        assertionXSqureValid("0.00893", "0.0000797449");
        assertionXSqureValid("-0.00893", "0.0000797449");

        assertionXSqureValid("0.00935", "0.0000874225");
        assertionXSqureValid("-0.00935", "0.0000874225");

        assertionXSqureValid("0.00952", "0.0000906304");
        assertionXSqureValid("-0.00952", "0.0000906304");

        assertionXSqureValid("0.00999", "0.0000998001");
        assertionXSqureValid("-0.00999", "0.0000998001");

        assertionXSqureValid("0.000001008", "1.016064E-12");
        assertionXSqureValid("-0.000001008", "1.016064E-12");

        assertionXSqureValid("0.000002256", "5.089536E-12");
        assertionXSqureValid("-0.000002256", "5.089536E-12");

        assertionXSqureValid("0.000003173", "1.0067929E-11");
        assertionXSqureValid("-0.000003173", "1.0067929E-11");

        assertionXSqureValid("0.000003477", "1.2089529E-11");
        assertionXSqureValid("-0.000003477", "1.2089529E-11");

        assertionXSqureValid("0.000003889", "1.5124321E-11");
        assertionXSqureValid("-0.000003889", "1.5124321E-11");

        assertionXSqureValid("0.000004378", "1.9166884E-11");
        assertionXSqureValid("-0.000004378", "1.9166884E-11");

        assertionXSqureValid("0.000004647", "2.1594609E-11");
        assertionXSqureValid("-0.000004647", "2.1594609E-11");

        assertionXSqureValid("0.000005448", "2.9680704E-11");
        assertionXSqureValid("-0.000005448", "2.9680704E-11");

        assertionXSqureValid("0.000006271", "3.9325441E-11");
        assertionXSqureValid("-0.000006271", "3.9325441E-11");

        assertionXSqureValid("0.000007118", "5.0665924E-11");
        assertionXSqureValid("-0.000007118", "5.0665924E-11");

        assertionXSqureValid("0.000008072", "6.5157184E-11");
        assertionXSqureValid("-0.000008072", "6.5157184E-11");

        assertionXSqureValid("0.000009112", "8.3028544E-11");
        assertionXSqureValid("-0.000009112", "8.3028544E-11");

        assertionXSqureValid("0.000009214", "8.4897796E-11");
        assertionXSqureValid("-0.000009214", "8.4897796E-11");

        assertionXSqureValid("0.000055555", "3.086358025E-9");
        assertionXSqureValid("-0.000055555", "3.086358025E-9");

        assertionXSqureValid("0.000082534", "6.811861156E-9");
        assertionXSqureValid("-0.000082534", "6.811861156E-9");

        assertionXSqureValid("0.072324911", "0.005230892751157921");
        assertionXSqureValid("-0.072324911", "0.005230892751157921");

        assertionXSqureValid("0.888888888", "0.790123455209876544");
        assertionXSqureValid("-0.888888888", "0.790123455209876544");

        assertionXSqureValid("1E-17", "1E-34");
        assertionXSqureValid("-1E-17", "1E-34");

        assertionXSqureValid("5.647753E-11", "3.1897113949009E-21");
        assertionXSqureValid("-5.647753E-11", "3.1897113949009E-21");

        assertionXSqureValid("8.8788779E-10", "7.883447276310841E-19");
        assertionXSqureValid("-8.8788779E-10", "7.883447276310841E-19");

        assertionXSqureValid("9.86859867454E-16", "9.73892397991326448442116E-31");
        assertionXSqureValid("-9.86859867454E-16", "9.73892397991326448442116E-31");

        assertionXSqureValid("1E-296", "1E-592");
        assertionXSqureValid("-1E-296", "1E-592");

        assertionXSqureValid("5.647753E-537", "3.1897113949009E-1073");
        assertionXSqureValid("-5.647753E-537", "3.1897113949009E-1073");

        assertionXSqureValid("8.8788779E-883", "7.883447276310841E-1765");
        assertionXSqureValid("-8.8788779E-883", "7.883447276310841E-1765");

        assertionXSqureValid("9.86859867454E-956", "9.73892397991326448442116E-1911");
        assertionXSqureValid("-9.86859867454E-956", "9.73892397991326448442116E-1911");

        assertionXSqureValid("1E-1296", "1E-2592");
        assertionXSqureValid("-1E-1296", "1E-2592");

        assertionXSqureValid("5.647753E-1537", "3.1897113949009E-3073");
        assertionXSqureValid("-5.647753E-1537", "3.1897113949009E-3073");

        assertionXSqureValid("8.8788779E-1883", "7.883447276310841E-3765");
        assertionXSqureValid("-8.8788779E-1883", "7.883447276310841E-3765");

        assertionXSqureValid("9.86859867454E-1956", "9.73892397991326448442116E-3911");
        assertionXSqureValid("-9.86859867454E-1956", "9.73892397991326448442116E-3911");

        assertionXSqureValid("1E-2296", "1E-4592");
        assertionXSqureValid("-1E-2296", "1E-4592");

        assertionXSqureValid("5.647753E-2537", "3.1897113949009E-5073");
        assertionXSqureValid("-5.647753E-2537", "3.1897113949009E-5073");

        assertionXSqureValid("8.8788779E-2883", "7.883447276310841E-5765");
        assertionXSqureValid("-8.8788779E-2883", "7.883447276310841E-5765");

        assertionXSqureValid("9.86859867454E-2956", "9.73892397991326448442116E-5911");
        assertionXSqureValid("-9.86859867454E-2956", "9.73892397991326448442116E-5911");

        assertionXSqureValid("1E-3296", "1E-6592");
        assertionXSqureValid("-1E-3296", "1E-6592");

        assertionXSqureValid("5.647753E-3537", "3.1897113949009E-7073");
        assertionXSqureValid("-5.647753E-3537", "3.1897113949009E-7073");

        assertionXSqureValid("8.8788779E-3883", "7.883447276310841E-7765");
        assertionXSqureValid("-8.8788779E-3883", "7.883447276310841E-7765");

        assertionXSqureValid("9.86859867454E-3956", "9.73892397991326448442116E-7911");
        assertionXSqureValid("-9.86859867454E-3956", "9.73892397991326448442116E-7911");

        assertionXSqureValid("1E-4296", "1E-8592");
        assertionXSqureValid("-1E-4296", "1E-8592");

        assertionXSqureValid("5.647753E-4537", "3.1897113949009E-9073");
        assertionXSqureValid("-5.647753E-4537", "3.1897113949009E-9073");

        assertionXSqureValid("8.8788779E-4883", "7.883447276310841E-9765");
        assertionXSqureValid("-8.8788779E-4883", "7.883447276310841E-9765");

        assertionXSqureValid("9.86859867454E-4956", "9.73892397991326448442116E-9911");
        assertionXSqureValid("-9.86859867454E-4956", "9.73892397991326448442116E-9911");

        assertionXSqureValid("1E-5296", "1E-10592");
        assertionXSqureValid("-1E-5296", "1E-10592");

        assertionXSqureValid("5.647753E-5537", "3.1897113949009E-11073");
        assertionXSqureValid("-5.647753E-5537", "3.1897113949009E-11073");

        assertionXSqureValid("8.8788779E-5883", "7.883447276310841E-11765");
        assertionXSqureValid("-8.8788779E-5883", "7.883447276310841E-11765");

        assertionXSqureValid("9.86859867454E-5956", "9.73892397991326448442116E-11911");
        assertionXSqureValid("-9.86859867454E-5956", "9.73892397991326448442116E-11911");

        assertionXSqureValid("1E-6296", "1E-12592");
        assertionXSqureValid("-1E-6296", "1E-12592");

        assertionXSqureValid("5.647753E-6537", "3.1897113949009E-13073");
        assertionXSqureValid("-5.647753E-6537", "3.1897113949009E-13073");

        assertionXSqureValid("8.8788779E-6883", "7.883447276310841E-13765");
        assertionXSqureValid("-8.8788779E-6883", "7.883447276310841E-13765");

        assertionXSqureValid("9.86859867454E-6956", "9.73892397991326448442116E-13911");
        assertionXSqureValid("-9.86859867454E-6956", "9.73892397991326448442116E-13911");

        assertionXSqureValid("1E-7296", "1E-14592");
        assertionXSqureValid("-1E-7296", "1E-14592");

        assertionXSqureValid("5.647753E-7537", "3.1897113949009E-15073");
        assertionXSqureValid("-5.647753E-7537", "3.1897113949009E-15073");

        assertionXSqureValid("8.8788779E-7883", "7.883447276310841E-15765");
        assertionXSqureValid("-8.8788779E-7883", "7.883447276310841E-15765");

        assertionXSqureValid("9.86859867454E-7956", "9.73892397991326448442116E-15911");
        assertionXSqureValid("-9.86859867454E-7956", "9.73892397991326448442116E-15911");

        assertionXSqureValid("1E-8296", "1E-16592");
        assertionXSqureValid("-1E-8296", "1E-16592");

        assertionXSqureValid("5.647753E-8537", "3.1897113949009E-17073");
        assertionXSqureValid("-5.647753E-8537", "3.1897113949009E-17073");

        assertionXSqureValid("8.8788779E-8883", "7.883447276310841E-17765");
        assertionXSqureValid("-8.8788779E-8883", "7.883447276310841E-17765");

        assertionXSqureValid("9.86859867454E-8956", "9.73892397991326448442116E-17911");
        assertionXSqureValid("-9.86859867454E-8956", "9.73892397991326448442116E-17911");

        assertionXSqureValid("1E-9296", "1E-18592");
        assertionXSqureValid("-1E-9296", "1E-18592");

        assertionXSqureValid("5.647753E-9537", "3.1897113949009E-19073");
        assertionXSqureValid("-5.647753E-9537", "3.1897113949009E-19073");

        assertionXSqureValid("8.8788779E-9883", "7.883447276310841E-19765");
        assertionXSqureValid("-8.8788779E-9883", "7.883447276310841E-19765");

        assertionXSqureValid("9.86859867454E-9956", "9.73892397991326448442116E-19911");
        assertionXSqureValid("-9.86859867454E-9956", "9.73892397991326448442116E-19911");

        assertionXSqureValid("1E-10296", "1E-20592");
        assertionXSqureValid("-1E-10296", "1E-20592");

        assertionXSqureValid("5.647753E-10537", "3.1897113949009E-21073");
        assertionXSqureValid("-5.647753E-10537", "3.1897113949009E-21073");

        assertionXSqureValid("8.8788779E-10883", "7.883447276310841E-21765");
        assertionXSqureValid("-8.8788779E-10883", "7.883447276310841E-21765");

        assertionXSqureValid("9.86859867454E-10956", "9.73892397991326448442116E-21911");
        assertionXSqureValid("-9.86859867454E-10956", "9.73892397991326448442116E-21911");

        assertionXSqureValid("-1E-9999", "1E-19998");
        assertionXSqureValid("1E-9999", "1E-19998");

        assertionXSqureValid("-0.0000000000000001", "1E-32");
        assertionXSqureValid("0.0000000000000001", "1E-32");

    }

    void assertionXSqureValid(String xString, String resultString) {
        BigDecimal x = new BigDecimal(xString);
        BigDecimal resultExpected = new BigDecimal(resultString);



        formula = new ArrayList<>(Arrays.asList(x, OperationsEnum.SQR));

        try {
            BigDecimal resultActual = Calculator.calculator(formula);
            assertEquals(resultExpected, resultActual);
        } catch (DivideZeroException | ResultUndefinedException | OperationException | InvalidInputException e) {
            e.printStackTrace();
        }
        checkUnary(x, resultExpected);

        assertXSquareInvalid(x);
    }

    private void checkUnary (BigDecimal x, BigDecimal resultExpected) {
        unary.setNumber(x);
        try {
            unary.setOperation(OperationsEnum.SQR);
            unary.calculateUnary();
        } catch (InvalidInputException | DivideZeroException | OperationException e) {
            e.printStackTrace();
        }
        BigDecimal resultActual = unary.getResult();

        assertEquals(resultExpected, resultActual);
        assertEquals(x.pow(2, MathContext.DECIMAL128), resultActual);
    }

    private void assertXSquareInvalid (BigDecimal x) {
        assertEnumNull(x);

        assertEnumInvalid(OperationsEnum.ADD);
        assertEnumInvalid(OperationsEnum.SUBTRACT);
        assertEnumInvalid(OperationsEnum.DIVIDE);
        assertEnumInvalid(OperationsEnum.MULTIPLY);
    }

    private void assertEnumInvalid(OperationsEnum operationsEnum) {
        try {
            unary.setOperation(operationsEnum);
            unary.calculateUnary();
            fail();
        } catch (Exception e) {
            assertEquals( "Enter unary operation", e.getMessage());
        }
    }

    private void assertEnumNull (BigDecimal x) {
        formula = new ArrayList<>(Arrays.asList(x, null));
        try {
            Calculator.calculator(formula);
            fail();
        } catch (Exception e) {
            assertEquals("Can not calculate null operation, enter operation or number.", e.getMessage());
        }
    }

}