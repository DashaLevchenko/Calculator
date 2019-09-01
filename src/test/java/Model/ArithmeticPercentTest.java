package Model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static java.math.BigDecimal.valueOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class ArithmeticPercentTest {

    @Test
    void percentTest() {
    assertionPercent(valueOf(555), valueOf(-66.5), "-369.075");
    assertionPercent(valueOf(555), valueOf(66.5), "369.075");
    assertionPercent(valueOf(-555), valueOf(66.5), "-369.075");
    assertionPercent(valueOf(-555), valueOf(-66.5), "369.075");
    assertionPercent(valueOf(100), valueOf(5), "5");

    }

    void assertionPercent(BigDecimal x, BigDecimal percent, String result) {
//        BigDecimal v = BigDecimal.ONE.divide(x, new MathContext(16));
//        if(v.scale() > 16){
//            v = v.setScale(16, RoundingMode.HALF_UP);
//        }
//        System.out.println("assertionPercent(valueOf("+x+"), \""+
//                scaleForBigDecimal(v).toPlainString()+"\");");
        assertEquals(new BigDecimal(result),  Arithmetic.percent(x, percent)); //0,0020876826722338
    }


    static BigDecimal scaleForBigDecimal(BigDecimal numberDouble) {
        numberDouble = numberDouble.stripTrailingZeros();
        if (numberDouble.scale() < 0) {
            numberDouble = numberDouble.setScale(0);
        }
        return numberDouble;
    }
}