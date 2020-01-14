package CalculatorApplication.Controller;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

/**
 * Class formatters number for print
 */
class CalculatorNumberFormatter {
    /** Decimal separator before formatter */
    static final String DECIMAL_SEPARATOR_AFTER_FORMATTER = ",";
    /** This variable keeps grouping separator for number */
    private static final String GROUPING_SEPARATOR = " ";
    /** Max scale for number print */
    private static final int MAX_SCALE_PRINT = 16;

    /** Max scale for number */
    private static final int MAX_SCALE = 10000;

    /** Max number before exponential formatting. */
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

    /** Minimal precision for formatter decimal number */
    private static final int PRECISION_MIN = 1;

    /** Decimal separator before formatter */
    private static final String DECIMAL_SEPARATOR_BEFORE_FORMATTER = ".";

    /** Exponent pattern */
    private static final String EXPONENT_PATTERN = "E0";

    /** Number pattern */
    private static final String HASH_PATTERN = "#";

    /** Number pattern */
    private static final String ZERO_PATTERN = "0";

    /** Simple decimal pattern */
    private static final String DECIMAL_PATTERN = "0.";

    /** Simple integer pattern */
    private static final String INTEGER_PATTERN = "#,##0";

    /** Exponent for print */
    private static final String EXPONENT = "e";

    /** Empty string */
    private static final String EMPTY_STRING = "";

    /** Constant keeps plus symbol */
    private static final String PLUS = "+";
    /** Constant keeps plus symbol */
    private static final String MINUS = "-";
    private static DecimalFormatSymbols symbols = new DecimalFormatSymbols();
    private static DecimalFormat decimalFormat;


    /*Set separator for formatter number*/
    static {
        symbols.setExponentSeparator(EXPONENT);
        symbols.setGroupingSeparator(GROUPING_SEPARATOR.charAt(0));
        symbols.setDecimalSeparator(DECIMAL_SEPARATOR_AFTER_FORMATTER.charAt(0));

    }


    private static String formatNumber (BigDecimal number, String pattern) {
        decimalFormat = new DecimalFormat(pattern, symbols);
        decimalFormat.setRoundingMode(RoundingMode.DOWN);
        return decimalFormat.format(number);
    }

    /**
     * Method formatters number for print on display,
     * If last symbol for number was formatted must be decimal separator and number without decimal part,
     * pattern for format will concat decimal separator.
     *
     * @param number                       Number need to format
     * @param isLastSymbolDecimalSeparator Is true, If last symbol for number was formatted must be decimal separator
     * @return String with number was formatted
     */
    static String formatNumberForPrint (BigDecimal number, boolean isLastSymbolDecimalSeparator) {
        String pattern;
        number = roundNumber(number);
        if (isMoreMaxInputNumber(number) > 0) {
            pattern = definePatternNumberMoreMaxInput(number);
        } else if (compareOne(number) < 0 && compareZero(number) != 0) {
            int scale = number.scale();

            pattern = DECIMAL_PATTERN;

            if (scale > MAX_SCALE_PRINT) {
                int precision = number.precision();
                int numericInNumber = scale - precision;
                int minNumericInNumber = 2;

                if (numericInNumber > minNumericInNumber) {
                    if (precision != PRECISION_MIN && precision <= MAX_SCALE_PRINT) {
                        pattern = pattern.concat(HASH_PATTERN.repeat(precision));
                    }
                    pattern = pattern.concat(EXPONENT_PATTERN);
                } else {
                    number = number.setScale(MAX_SCALE_PRINT, RoundingMode.HALF_UP);
                    pattern = pattern.concat(HASH_PATTERN.repeat(MAX_SCALE_PRINT));
                }
            } else {
                pattern = pattern.concat(ZERO_PATTERN.repeat(scale));
            }
        } else {
            pattern = definePatternNumberMoreOneLessMaxInput(number);
            if (isLastSymbolDecimalSeparator) {
                pattern = pattern.concat(DECIMAL_SEPARATOR_BEFORE_FORMATTER);
            }
        }


        String formattedNumber = formatNumber(number, pattern);
        formattedNumber = changeExponent(formattedNumber);

        return formattedNumber;
    }


    private static String changeExponent (String outNumber) {
        String negateExponent = EXPONENT.concat(MINUS);
        String positiveExponent = EXPONENT.concat(PLUS);

        if (!outNumber.contains(negateExponent)) {
            outNumber = outNumber.replace(EXPONENT, positiveExponent);
        }
        return outNumber;
    }

    private static String definePatternNumberMoreOneLessMaxInput (BigDecimal number) {
        int scale = number.scale();
        String pattern;
        if (scale > 0) {
            int numberOfHash = Math.min(scale, MAX_SCALE_PRINT);
            pattern = INTEGER_PATTERN.concat(DECIMAL_SEPARATOR_BEFORE_FORMATTER);
            pattern = pattern.concat(ZERO_PATTERN.repeat(numberOfHash));
        } else {
            pattern = INTEGER_PATTERN;
        }

        return pattern;
    }

    private static int compareZero (BigDecimal number) {
        return number.abs().compareTo(BigDecimal.ZERO);
    }

    private static int compareOne (BigDecimal number) {
        return number.abs().compareTo(BigDecimal.ONE);
    }

    private static String definePatternNumberMoreMaxInput (BigDecimal number) {
        int scale = number.scale();
        int precision = number.precision();
        int numericInNumber = precision - scale;
        String pattern;
        if (numericInNumber > MAX_SCALE_PRINT) {
            pattern = DECIMAL_PATTERN;

            number = number.stripTrailingZeros();
            precision = number.precision();
            scale = number.scale();

            if (precision != PRECISION_MIN && precision > scale) {
                pattern = pattern.concat(HASH_PATTERN.repeat(precision - PRECISION_MIN));
            }

            pattern = pattern.concat(EXPONENT_PATTERN);
        } else {
            if (scale < 0) {
                pattern = DECIMAL_PATTERN.concat(EXPONENT_PATTERN);
            } else {
                pattern = INTEGER_PATTERN;
            }
        }
        return pattern;
    }

    private static int isMoreMaxInputNumber (BigDecimal number) {
        return number.abs().compareTo(MAX_NUMBER_INPUT);
    }

    static BigDecimal roundNumber (BigDecimal number) {
        MathContext mathContext;

        int scale = number.scale();
        if (scale > 0 && scale < MAX_SCALE) {
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
    static BigDecimal getParsedNumber (String text) throws ParseException {
        text = text.replace(PLUS, EMPTY_STRING);
        decimalFormat.setParseBigDecimal(true);
        return (BigDecimal) decimalFormat.parse(text);
    }

    static String formatNumberForHistory (BigDecimal number) {
        number = number.stripTrailingZeros();
        String formattedNumber = formatNumberForPrint(number, false);
        return formattedNumber.replace(GROUPING_SEPARATOR, EMPTY_STRING);
    }

}
