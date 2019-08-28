package Model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.MathContext;

import static java.math.BigDecimal.valueOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class ArithmeticSquerRootTest {

    @Test
    void squareRootValid() {
//    assertionSQRTValid(valueOf(0), valueOf(0));
//    assertionSQRTValid(valueOf(1), valueOf(1));
//    assertionSQRTValid(valueOf(2), valueOf(1.414213562373095));
//    assertionSQRTValid(valueOf(3), valueOf(1.732050807568877));
//    assertionSQRTValid(valueOf(4), valueOf(2));
//    assertionSQRTValid(valueOf(5), valueOf(2.23606797749979));
//    assertionSQRTValid(valueOf(6), valueOf(2.449489742783178));



    assertionSQRTValid(valueOf(9999999999999999L), valueOf(99999999.99999999));



    }

    @Test
    void squareRootNOTValid() {



    }

    void assertionSQRTValid(BigDecimal x, BigDecimal resultSqrt) {
        resultSqrt = scaleForBigDecimal(resultSqrt.stripTrailingZeros());
        assertEquals(resultSqrt,  Arithmetic.squareRoot(x));
//        assertEquals(resultSqrt, scaleForBigDecimal(x.sqrt(MathContext.DECIMAL64).stripTrailingZeros()));
        assertEquals(resultSqrt, x.sqrt(MathContext.DECIMAL64));
    }

    void assertionSQRTNotValid(BigDecimal x) {
        try {
            Arithmetic.squareRoot(x);
            fail();
        } catch (ArithmeticException e) {
            assertEquals("Invalid input", e.getMessage());
        }

    }


    static BigDecimal scaleForBigDecimal(BigDecimal numberDouble) {
        if (numberDouble.scale() < 0) {
            numberDouble = numberDouble.setScale(0);
        }
        return numberDouble;
    }
}