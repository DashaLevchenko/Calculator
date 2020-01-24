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
public class CalculatorNumberFormatter {
    /**
     * Decimal separator before formatter
     */
    static final String DECIMAL_SEPARATOR_AFTER_FORMATTER = ",";
    /**
     * Max scale for number print
     */
    static final int MAX_SCALE_PRINT = 16;
    /**
     * Max scale for number
     */
    private static final int MAX_SCALE = 10000;
    /**
     * Exponent pattern
     */
    private static final String EXPONENT_PATTERN = "E0";
    /**
     * Number pattern
     */
    private static final String ZERO_PATTERN = "0";
    /**
     * This variable keeps grouping separator for number
     */
    private static final String GROUPING_SEPARATOR = " ";
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
     * Decimal separator before formatter
     */
    private static final String DECIMAL_SEPARATOR_BEFORE_FORMATTER = ".";
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

    /**
     * Create a DecimalFormatSymbols object for customize the behavior of the format.
     */
    private static final DecimalFormatSymbols SYMBOLS = new DecimalFormatSymbols();


    private static DecimalFormat DECIMAL_FORMAT = new DecimalFormat();

    /*
      Set separator for formatter number
     */ static {
        SYMBOLS.setExponentSeparator(EXPONENT);
        SYMBOLS.setGroupingSeparator(GROUPING_SEPARATOR.charAt(0));
        SYMBOLS.setDecimalSeparator(DECIMAL_SEPARATOR_AFTER_FORMATTER.charAt(0));
        DECIMAL_FORMAT.setDecimalFormatSymbols(SYMBOLS);
        DECIMAL_FORMAT.setParseBigDecimal(true);
    }

    /**
     * This method rounds number.
     *
     * @param number Number need to round
     * @return Number was rounded
     */
    BigDecimal roundNumber (BigDecimal number) {
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
     * This method formats a BigDecimal to produce a string.
     * Also method sets for {@code decimalFormat} customized symbols,
     * new pattern and new rounding mode.
     *
     * @param number  Number need to format
     * @param pattern Pattern for format number
     * @return String of formatted number.
     */
    public String formatNumber (BigDecimal number, String pattern) {
        synchronized (DECIMAL_FORMAT){
            DECIMAL_FORMAT.applyPattern(pattern);
            return DECIMAL_FORMAT.format(number);
        }
    }

//    private DecimalFormat getDecimalFormat () {
//        DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance();
//        decimalFormat.setDecimalFormatSymbols(SYMBOLS);
//        return decimalFormat;
//    }

    /**
     * Method formatters number for print on display,
     * If last symbol for number was formatted must be decimal separator and number without decimal part,
     * pattern for format will concat decimal separator.
     *
     * @param number                       Number need to format
     * @param isLastSymbolDecimalSeparator Is true, If last symbol for number was formatted must be decimal separator
     * @return String with number was formatted
     */
    String formatNumberForPrint (BigDecimal number, boolean isLastSymbolDecimalSeparator) {
        number = roundNumber(number);
        String pattern;

        boolean isMoreMaxNumberInput = number.abs().compareTo(MAX_NUMBER_INPUT) > 0;
        if (isMoreMaxNumberInput) {
            pattern = definePatternNumberMoreMaxInput(number);
        } else if (compareToOne(number) < 0 && compareToZero(number) != 0) {
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
        formattedNumber = changePositiveExponent(formattedNumber);

        return formattedNumber;
    }

    /**
     * Method changes positive exponent symbol.
     * Example:
     * numberFormatted: 1e17
     * return:          1e+17
     * <p>
     * numberFormatted: 1,67799e17
     * return:          1,67799e+17
     * <p>
     * numberFormatted: 1e-17
     * return:          1e-17
     *
     * @param numberFormatted Number which was formatted with exponent
     * @return Number which was formatted with exponent was changed
     */
    private String changePositiveExponent (String numberFormatted) {
        String MINUS = "-";
        String negateExponent = EXPONENT.concat(MINUS);
        String positiveExponent = EXPONENT.concat(PLUS);

        if (!numberFormatted.contains(negateExponent)) {
            numberFormatted = numberFormatted.replace(EXPONENT, positiveExponent);
        }
        return numberFormatted;
    }

    /**
     * This method define pattern for format {@code number}.
     * 1 < {@code number} < {@code MAX_NUMBER_INPUT}.
     *
     * @param number Number for find pattern.
     * @return Pattern for format {@code number}
     */
    private String definePatternNumberMoreOneLessMaxInput (BigDecimal number) {
        int scale = number.scale();
        String pattern;
        if (scale > 0) {
            int numberOfHash = Math.min(scale, MAX_SCALE_PRINT);
            pattern = INTEGER_PATTERN.concat(DECIMAL_SEPARATOR_BEFORE_FORMATTER).concat(ZERO_PATTERN.repeat(numberOfHash));
        } else {
            pattern = INTEGER_PATTERN;
        }
        return pattern;
    }

    private int compareToZero (BigDecimal number) {
        return number.abs().compareTo(BigDecimal.ZERO);
    }

    private int compareToOne (BigDecimal number) {
        return number.abs().compareTo(BigDecimal.ONE);
    }

    /**
     * This method define pattern for format {@code number}.
     * {@code number} > {@code MAX_NUMBER_INPUT}.
     *
     * @param number Number for find pattern.
     * @return Pattern for format {@code number}
     */
    private String definePatternNumberMoreMaxInput (BigDecimal number) {
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

    /**
     * Method parses number from text.
     *
     * @param text Text need to parse
     * @return Number was parsed
     * @throws ParseException If cannot parse text to number.
     */
    public  BigDecimal getParsedNumber (String text) throws ParseException {
        text = text.replace(PLUS, EMPTY_STRING);
        synchronized (PLUS) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return (BigDecimal) DECIMAL_FORMAT.parse(text);
        }
    }

    String formatNumberForCalculatorHistory (BigDecimal number) {
        number = number.stripTrailingZeros();
        String formattedNumber = formatNumberForPrint(number, false);
        return formattedNumber.replace(GROUPING_SEPARATOR, EMPTY_STRING);
    }

}
