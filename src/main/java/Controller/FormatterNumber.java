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

    public static DecimalFormatSymbols symbols = new DecimalFormatSymbols();
    private static DecimalFormat decimalFormat = new DecimalFormat();
    private static final BigDecimal MAX_NUMBER_INPUT = BigDecimal.valueOf(9999999999999999L);
    private static MathContext maxContext = new MathContext(MAX_SCALE + 1, RoundingMode.HALF_DOWN);
    private static int precisionMin = 1;


    //Set separator for formatter number
    static {
        symbols.setExponentSeparator("e");
        symbols.setGroupingSeparator(' ');
        symbols.setDecimalSeparator(',');
        decimalFormat.setDecimalFormatSymbols(symbols);
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

                    if (precision != precisionMin && precision <= MAX_SCALE) {
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
        if (!outNumber.contains("e-")) {
            outNumber = outNumber.replace("e", "e+");
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

            if (precision != precisionMin && precision > scale) {
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
        String numberStr = number.round(maxContext).toPlainString();

        String point = ".";
        if (numberStr.contains(point)) {
            int indexAfterPoint = numberStr.indexOf(point) + 1;
            String decimalPart = numberStr.substring(indexAfterPoint);

            long countNine = decimalPart.chars().filter(ch -> ch == '9').count();

            if (countNine == decimalPart.length()) {
                number = number.round(new MathContext(MAX_SCALE, RoundingMode.UP));
            } else {
                number = number.round(new MathContext(MAX_SCALE, RoundingMode.HALF_DOWN));
            }
        } else {
            number = number.round(new MathContext(MAX_SCALE, RoundingMode.HALF_UP));
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
        text = text.replace("+", "").replace(" ", "");

        decimalFormat.setParseBigDecimal(true);

        BigDecimal number = (BigDecimal) decimalFormat.parse(text);
        number = number.stripTrailingZeros();

        if (number.scale() < 0) {
            number = number.setScale(0);
        }

        return number;

    }

}
