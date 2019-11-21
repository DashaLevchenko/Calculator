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
class NumberFormatter {
    private static DecimalFormatSymbols symbols = new DecimalFormatSymbols();
    private static DecimalFormat decimalFormat = new DecimalFormat();
    private static final BigDecimal MAX_NUMBER_INPUT = BigDecimal.valueOf(9999999999999999L);

    /**
     * Max scale for number print
     */
    private static final int MAX_SCALE = 16;

    //Set separator for formatter number
    static {
        symbols.setExponentSeparator("e");
        symbols.setGroupingSeparator(' ');
        symbols.setDecimalSeparator(',');
        decimalFormat.setDecimalFormatSymbols(symbols);
    }

    /**
     * Method formatters number for print on display
     * @param number Number need to format
     * @return String with number was formatted
     */
    static String numberFormatter(BigDecimal number) {
        StringBuilder pattern = new StringBuilder();

        number = roundNumber(number);
        if (number.abs().compareTo(MAX_NUMBER_INPUT) > 0) {
            if (number.precision() - number.scale() > MAX_SCALE) {
                pattern.append("0.");
                number = number.stripTrailingZeros();

                if (number.precision() != 1 && number.precision() > number.scale()) {
                    pattern.append("#".repeat(number.precision() - number.scale()));
                }
                pattern.append("E0");

            } else {
                if (number.scale() < 0) {
                    pattern.append("0.E0");
                } else {
                    pattern.append("#,##0");
                }
            }
        } else if (number.abs().compareTo(BigDecimal.ONE) < 0 && number.abs().compareTo(BigDecimal.ZERO) != 0) {
            number = number.stripTrailingZeros();

            if (number.scale() > MAX_SCALE) {
                pattern.append("0.");
                if ((number.scale() - number.precision() > 2)) {
                    if (number.precision() != 1 && number.precision() <= MAX_SCALE) {
                        pattern.append("#".repeat(number.precision()));
                    }
                    pattern.append("E0");
                } else {
                    number = number.setScale(MAX_SCALE, RoundingMode.HALF_UP);
                    pattern.append("#".repeat(MAX_SCALE));
                }
            } else {
                pattern.append("0.").append("#".repeat(number.scale()));
            }
        } else {
            pattern.append("#,##0");
            if (number.scale() > 0) {
                if (number.scale() < MAX_SCALE) {
                    pattern.append(".").append("#".repeat(number.scale()));
                } else {
                    pattern.append(".").append("#".repeat(MAX_SCALE));
                }
            }
        }
        decimalFormat = new DecimalFormat(pattern.toString(), symbols);
        String outNumber = decimalFormat.format(number);
        if (!outNumber.contains("e-")) {
            outNumber = outNumber.replace("e", "e+");
        }

        return outNumber;
    }

    private static BigDecimal roundNumber(BigDecimal number) {
        String numberStr = number.round(new MathContext(MAX_SCALE + 1, RoundingMode.HALF_DOWN)).toPlainString();

        if (numberStr.contains(".")) {
            String decimalPart = numberStr.substring(numberStr.indexOf(".") + 1);
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
     * @param text Text need to parse
     * @return Number was parsed
     */
    static BigDecimal parseNumber(String text) {
        BigDecimal number = null;
        text = text.replace("+", "").replace(" ", "");
        if (decimalFormat != null && !text.isEmpty()) {
            try {
                decimalFormat.setParseBigDecimal(true);
                number = (BigDecimal) decimalFormat.parse(text);

                number = number.stripTrailingZeros();
                if (number.scale() < 0) {
                    number = number.setScale(0);
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return number;
    }

    /**
     * Method returns text was formatted
     * @param text Text need to formatter
     * @return Text was formatted
     */
    static String formatterInputNumber(String text) {
        BigDecimal number = new BigDecimal(text.replace(",", "."));
        StringBuilder pattern = new StringBuilder();
        pattern.append("#,##0");
        if (number.scale() > 0) {
            if (number.scale() <= MAX_SCALE) {
                pattern.append(".").append("0".repeat(number.scale()));
            } else {
                if (number.precision() > number.scale()) {
                    pattern.append(".").append("0".repeat(MAX_SCALE - (number.precision() - number.scale())));
                } else {
                    pattern.append(".").append("0".repeat(MAX_SCALE));
                }
            }
        }

        return new DecimalFormat(pattern.toString(), symbols).format(number);
    }
}
