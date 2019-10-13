package View;

import java.io.Writer;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

public class NumberFormatter {
    private static DecimalFormatSymbols symbols = new DecimalFormatSymbols();
    private static DecimalFormat decimalFormat = new DecimalFormat();
    private static final BigDecimal MIN_DECIMAL_NUMBER = BigDecimal.valueOf(0.0000000000000001);
    private static final BigDecimal MAX_NUMBER_INPUT = BigDecimal.valueOf(9999999999999999L);
    private static final int MAX_SCALE = 16;

    public static String formatterNumber(BigDecimal number) {
        symbols.setExponentSeparator("e");
        symbols.setGroupingSeparator(' ');

        StringBuilder pattern = new StringBuilder();
        if (number.compareTo(MAX_NUMBER_INPUT) > 0) {
            pattern.append("0.");
            if (number.precision() > MAX_SCALE) {
                number = number.round(new MathContext(MAX_SCALE, RoundingMode.HALF_UP));
                number = number.stripTrailingZeros();

                if (number.precision() != 1 && number.precision() > number.scale()) {
                    pattern.append("#".repeat(number.precision() - number.scale()));
                }
                pattern.append("E0");

            } else {
                pattern.append("0".repeat(number.scale()));
            }
        } else if (number.abs().compareTo(BigDecimal.ONE) < 0 && number.abs().compareTo(BigDecimal.ZERO) != 0) {
            if (number.abs().compareTo(MIN_DECIMAL_NUMBER) < 0) {
                number = number.round(new MathContext(MAX_SCALE, RoundingMode.HALF_UP));
                pattern.append("0.E0");
            } else {
                pattern.append("0.").append("#".repeat(MAX_SCALE));
            }

        } else {
            pattern.append("#,##0");
            if (number.scale() > 0) {
                if (number.scale() < MAX_SCALE) {
                    pattern.append(".").append("#".repeat(number.scale()));
                } else {
                    pattern.append(".").append("#".repeat(MAX_SCALE - (number.precision() - number.scale())));
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

    public static BigDecimal parseNumber(String text) {
        BigDecimal number = null;
        if (decimalFormat != null && !text.isEmpty()) {
            try {
                try {
                    number = BigDecimal.valueOf((Double) decimalFormat.parse(text));
                } catch (ClassCastException e) {
                    number = BigDecimal.valueOf((Long) decimalFormat.parse(text));
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return number;
    }

    public static String formatterInputNumber(BigDecimal number) {
        StringBuilder pattern = new StringBuilder();
        pattern.append("#,##0");
        if (number.scale() > 0) {
            if (number.scale() < MAX_SCALE) {
                pattern.append(".").append("0".repeat(number.scale()));
            } else {
                pattern.append(".").append("0".repeat(MAX_SCALE - (number.precision() - number.scale())));
            }
        }


        return new DecimalFormat(pattern.toString(), symbols).format(number);
    }
}
