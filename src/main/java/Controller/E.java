package Controller;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class E {

    private static final int MAX_SCALE = 16;
    /**
     * A {@code MathContext} object with a precision setting  16 digits, and a
     * rounding mode of {@link RoundingMode#UP UP}.
     */
    private static final MathContext MATH_CONTEXT_UP = new MathContext(MAX_SCALE, RoundingMode.UP);

    /**
     * A {@code MathContext} object with a precision setting  16 digits, and a
     * rounding mode of {@link RoundingMode#HALF_DOWN HALF_DOWN}.
     */
    private static final MathContext MATH_CONTEXT_HALF_DOWN = new MathContext(MAX_SCALE, RoundingMode.HALF_DOWN);

    /**
     * A {@code MathContext} object with a precision setting  16 digits, and a
     * rounding mode of {@link RoundingMode#HALF_UP HALF_UP}.
     */
    private static final MathContext MATH_CONTEXT_HALF_UP = new MathContext(MAX_SCALE, RoundingMode.HALF_UP);

    /**
     * A {@code MathContext} object with a precision setting  17 digits, and a
     * rounding mode of {@link RoundingMode#HALF_DOWN HALF_DOWN}.
     */
    private static final MathContext MATH_CONTEXT_HALF_DOWN_MAX_SCALE_PLUS_ONE = new MathContext(MAX_SCALE + 1, RoundingMode.HALF_DOWN);



    private static MathContext chooseMathContext (BigDecimal number) {
        MathContext mathContext;
        number = number.round(MATH_CONTEXT_HALF_DOWN_MAX_SCALE_PLUS_ONE);
        String numberStr = number.toPlainString();

        String point = ".";
        if (numberStr.contains(point)) {
            int indexAfterPoint = numberStr.indexOf(point) + 1;
            String decimalPart = numberStr.substring(indexAfterPoint);

            char nine = '9';
            long countNine = decimalPart.chars().filter(ch -> ch == nine).count();

            if (countNine == decimalPart.length()) {
                mathContext = MATH_CONTEXT_UP;
            } else {
                mathContext = MATH_CONTEXT_HALF_DOWN;
            }
        } else {
            mathContext = MATH_CONTEXT_HALF_UP;
        }

        return mathContext;
    }

    public static void main (String[] args) {
//        BigDecimal o = BigDecimal.valueOf(9.99999999999999999999999);
//        BigDecimal o = BigDecimal.valueOf(9.999999);
//        MathContext n = chooseMathContext(o);
//        System.out.println(o.round(n));
//        System.out.println(n);



    }
}
