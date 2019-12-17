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
    private static final int MAX_SCALE_PRINT = 16;

    /**
     * Max scale for number
     */
    private static final int MAX_SCALE = 10000;

    /**
     * Max number before exponential formatting.
     */
    private static final BigDecimal MAX_NUMBER_INPUT = BigDecimal.valueOf(9999999999999999L);

    /**
     * A {@code MathContext} object with a precision setting  16 digits, and a
     * rounding mode of {@link RoundingMode#HALF_DOWN HALF_DOWN}.
     */
    private static final MathContext MATH_CONTEXT_HALF_DOWN = new MathContext(MAX_SCALE_PRINT, RoundingMode.HALF_DOWN);

    /**
     * A {@code MathContext} object with a precision setting  16 digits, and a
     * rounding mode of {@link RoundingMode#HALF_UP HALF_UP}.
     */
    private static final MathContext MATH_CONTEXT_HALF_UP = new MathContext(MAX_SCALE_PRINT, RoundingMode.HALF_UP);

    /**
     * Minimal precision for formatter decimal number
     */
    private static final int PRECISION_MIN = 1;

    /**
     * Minimal precision for formatter decimal number
     */
    private static final String DECIMAL_SEPARATOR = ".";

    /**
     * Exponent pattern
     */
    private static final String EXPONENT_PATTERN = "E0";

    /**
     * Number pattern
     */
    private static final String HASH_PATTERN = "#";

    /**
     * Simple decimal pattern
     */
    private static final String DECIMAL_PATTERN = "0.";

    /**
     * Simple integer pattern
     */
    private static final String INTEGER_PATTERN = "#,##0";

    /**
     * Exponent for print
     */
    private static final String EXPONENT = "e";

    /**
     * Empty string
     */
    private static final String EMPTY_STRING = "";


    /**
     * Constant keeps plus symbol
     */
    private static final String PLUS = "+";

    private static DecimalFormatSymbols symbols = new DecimalFormatSymbols();
    private static DecimalFormat decimalFormat = new DecimalFormat();

    //Set separator for formatter number
    static {
        symbols.setExponentSeparator(EXPONENT);
        symbols.setGroupingSeparator(' ');
        symbols.setDecimalSeparator(',');
        decimalFormat.setDecimalFormatSymbols(symbols);
    }

    static DecimalFormatSymbols getSymbols () {
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

            pattern.append(DECIMAL_PATTERN);
            int scale = number.scale();

            String appendToPattern = EMPTY_STRING;
            int hashRepeat = 0;
            String exponentPattern = EMPTY_STRING;

            if (scale > MAX_SCALE_PRINT) {
                int precision = number.precision();
                int numericInNumber = scale - precision;
                int minNumericInNumber = 2;

                if (numericInNumber > minNumericInNumber) {
                    if (precision != PRECISION_MIN && precision <= MAX_SCALE_PRINT) {
                        hashRepeat = precision;
                    }
                    exponentPattern = EXPONENT_PATTERN;
                } else {
                    number = number.setScale(MAX_SCALE_PRINT, RoundingMode.HALF_UP);
                    hashRepeat = MAX_SCALE_PRINT;
                }
            } else {
                hashRepeat = scale;
            }

            String hashPattern = HASH_PATTERN.repeat(hashRepeat);
            appendToPattern = appendToPattern.concat(hashPattern).concat(exponentPattern);
            pattern.append(appendToPattern);
        } else {
            pattern = patternNumber(number);
        }


        decimalFormat = new DecimalFormat(pattern.toString(), symbols);
        String outNumber = decimalFormat.format(number);
        outNumber = changeExponent(outNumber);

        return outNumber;
    }

    private static String changeExponent (String outNumber) {
        String negateExponent = EXPONENT.concat("-");
        String positiveExponent = EXPONENT.concat(PLUS);

        if (!outNumber.contains(negateExponent)) {
            outNumber = outNumber.replace(EXPONENT, positiveExponent);
        }
        return outNumber;
    }

    private static StringBuilder patternNumber (BigDecimal number) {
        StringBuilder pattern = new StringBuilder();

        pattern.append(INTEGER_PATTERN);

        int scale = number.scale();
        if (scale > 0) {
            pattern.append(DECIMAL_SEPARATOR);

            int repeatHash;
            if (isMoreMaxScale(scale)) {
                repeatHash = MAX_SCALE_PRINT;
            } else {
                repeatHash = scale;
            }
            pattern.append(HASH_PATTERN.repeat(repeatHash));
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


        String decimalPattern;
        String hashPattern;
        String exponentPattern = EMPTY_STRING;
        int hashRepeat = 0;
        if (isMoreMaxScale(numericInNumber)) {
            decimalPattern = DECIMAL_PATTERN;

            number = number.stripTrailingZeros();
            precision = number.precision();
            scale = number.scale();

            if (precision != PRECISION_MIN && precision > scale) {
                hashRepeat = numericInNumber;
            }
            exponentPattern = EXPONENT_PATTERN;

        } else {
            if (scale < 0) {
                decimalPattern = DECIMAL_PATTERN;
                exponentPattern = EXPONENT_PATTERN;
            } else {
                decimalPattern = INTEGER_PATTERN;
            }
        }

        hashPattern = HASH_PATTERN.repeat(hashRepeat);

        String addToPattern = decimalPattern.concat(hashPattern).concat(exponentPattern);
        return pattern.append(addToPattern);
    }

    private static boolean isMoreMaxScale (int number) {
        boolean isMore = false;
        if (number > MAX_SCALE_PRINT) {
            isMore = true;
        }
        return isMore;
    }

    private static int moreMaxNumber (BigDecimal number) {
        return number.abs().compareTo(MAX_NUMBER_INPUT);
    }

    private static BigDecimal roundNumber (BigDecimal number) {

        MathContext mathContext;
        if (number.scale() > 0 && number.scale() < MAX_SCALE) {
            mathContext = MATH_CONTEXT_HALF_DOWN;
        } else {
            mathContext = MATH_CONTEXT_HALF_UP;
        }

        return number.round(mathContext);
    }


    /**
     * Method parses number from text
     *
     * @param text Text need to parse
     * @return Number was parsed
     */
    static BigDecimal parseNumber (String text) {
        String groupingSeparator = " ";
        text = text.replace(PLUS, EMPTY_STRING).replace(groupingSeparator, EMPTY_STRING);
        decimalFormat.setParseBigDecimal(true);

        BigDecimal number = null;
        try {
            number = (BigDecimal) decimalFormat.parse(text);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (number != null) {
            number = number.stripTrailingZeros();
            if (number.scale() < 0) {
                number = number.setScale(0);
            }
        }


        return number;
    }

}
