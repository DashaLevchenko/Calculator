package Controller;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

/**
 * Class formatters number for print
 */
class FormatterNumber {
    /**
     * Max scale for number print
     */
    private static final int MAX_SCALE = 16;

    /**
     * Max number before exponential formatting.
     */
    private static final BigDecimal MAX_NUMBER_INPUT = BigDecimal.valueOf(9999999999999999L);

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
    private static final MathContext MATH_CONTEXT_UP_HALF_UP = new MathContext(MAX_SCALE, RoundingMode.HALF_UP);

    /**
     * A {@code MathContext} object with a precision setting  17 digits, and a
     * rounding mode of {@link RoundingMode#HALF_DOWN HALF_DOWN}.
     */
    private static final MathContext MATH_CONTEXT_HALF_DOWN_MAX_SCALE_PLUS_ONE = new MathContext(MAX_SCALE + 1, RoundingMode.HALF_DOWN);

    /**
     * Minimal precision for formatter decimal number
     */
    private static final int PRECISION_MIN = 1;

    private static DecimalFormatSymbols symbols = new DecimalFormatSymbols();
    private static DecimalFormat decimalFormat = new DecimalFormat();


    //Set separator for formatter number
    static {
        symbols.setExponentSeparator("e");
        symbols.setGroupingSeparator(' ');
        symbols.setDecimalSeparator(',');
        decimalFormat.setDecimalFormatSymbols(symbols);
    }

    public static DecimalFormatSymbols getSymbols () {
        return symbols;
    }

    /**
     * Method formatters number for print on display
     *
     * @param number Number need to format
     * @return String with number was formatted
     */
    static String formatterNumber (BigDecimal number) {
        StringBuilder pattern = new StringBuilder();

        number = roundNumber(number);

        if (moreMaxNumber(number) > 0) {
            pattern = patterMaxNumber(number);

        } else if (compareOne(number) < 0 && compareZero(number) != 0) {
            number = number.stripTrailingZeros();

            pattern.append("0.");
            int scale = number.scale();

            if (scale > MAX_SCALE) {
                int precision = number.precision();
                int numericInNumber = scale - precision;
                int minNumericInNumber = 2;

                if (numericInNumber > minNumericInNumber) {
                    if (precision != PRECISION_MIN && precision <= MAX_SCALE) {
                        pattern.append("#".repeat(precision));
                    }

                    pattern.append("E0");

                } else {
                    number = number.setScale(MAX_SCALE, RoundingMode.HALF_UP);
                    pattern.append("#".repeat(MAX_SCALE));
                }

            } else {
                pattern.append("#".repeat(scale));
            }
        } else {
            pattern = patternNumber(number);
        }

        decimalFormat = new DecimalFormat(pattern.toString(), symbols);
        String outNumber = decimalFormat.format(number);
        outNumber = changeExponent(outNumber);

        return outNumber;
    }

    private static String changeExponent (String outNumber) {
        String negateExponent = "e-";
        String positiveExponent = "e+";

        if (!outNumber.contains(negateExponent)) {
            String exponent = "e";
            outNumber = outNumber.replace(exponent, positiveExponent);
        }
        return outNumber;
    }

    private static StringBuilder patternNumber (BigDecimal number) {
        StringBuilder pattern = new StringBuilder();

        pattern.append("#,##0");

        int scale = number.scale();
        if (scale > 0) {
            pattern.append(".");

            if (isMoreMaxScale(scale)) {
                pattern.append("#".repeat(MAX_SCALE));
            } else {
                pattern.append("#".repeat(scale));
            }
        }
        return pattern;
    }

    private static int compareZero (BigDecimal number) {
        return number.abs().compareTo(BigDecimal.ZERO);
    }

    private static int compareOne (BigDecimal number) {
        return number.abs().compareTo(BigDecimal.ONE);
    }

    private static StringBuilder patterMaxNumber (BigDecimal number) {
        StringBuilder pattern = new StringBuilder();
        int scale = number.scale();
        int precision = number.precision();
        int numericInNumber = precision - scale;

        if (isMoreMaxScale(numericInNumber)) {
            pattern.append("0.");
            number = number.stripTrailingZeros();

            precision = number.precision();
            scale = number.scale();

            if (precision != PRECISION_MIN && precision > scale) {
                pattern.append("#".repeat(numericInNumber));
            }
            pattern.append("E0");

        } else {
            if (scale < 0) {
                pattern.append("0.E0");
            } else {
                pattern.append("#,##0");
            }
        }
        return pattern;
    }

    private static boolean isMoreMaxScale (int number) {
        boolean isMore = false;
        if (number > MAX_SCALE) {
            isMore = true;
        }
        return isMore;
    }

    private static int moreMaxNumber (BigDecimal number) {
        return number.abs().compareTo(MAX_NUMBER_INPUT);
    }

    private static BigDecimal roundNumber (BigDecimal number) {
        String numberStr = number.round(MATH_CONTEXT_HALF_DOWN_MAX_SCALE_PLUS_ONE).toPlainString();

        String point = ".";
        if (numberStr.contains(point)) {
            int indexAfterPoint = numberStr.indexOf(point) + 1;
            String decimalPart = numberStr.substring(indexAfterPoint);

            char nine = '9';
            long countNine = decimalPart.chars().filter(ch -> ch == nine).count();

            if (countNine == decimalPart.length()) {
                number = number.round(MATH_CONTEXT_UP);
            } else {
                number = number.round(MATH_CONTEXT_HALF_DOWN);
            }
        } else {
            number = number.round(MATH_CONTEXT_UP_HALF_UP);
        }
        return number;
    }

    /**
     * Method parses number from text
     *
     * @param text Text need to parse
     * @return Number was parsed
     */
    static BigDecimal parseNumber (String text) throws ParseException {
        String plus = "+";
        String emptyString = "";
        String groupingSeparator = " ";
        text = text.replace(plus, emptyString).replace(groupingSeparator, emptyString);
        decimalFormat.setParseBigDecimal(true);

        BigDecimal number = (BigDecimal) decimalFormat.parse(text);
        number = number.stripTrailingZeros();

        if (number.scale() < 0) {
            number = number.setScale(0);
        }

        return number;
    }

}
