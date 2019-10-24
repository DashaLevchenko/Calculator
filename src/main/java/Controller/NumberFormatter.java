package Controller;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

class NumberFormatter {
    private static DecimalFormatSymbols symbols = new DecimalFormatSymbols();
    private static DecimalFormat decimalFormat = new DecimalFormat();
    private static final BigDecimal MIN_DECIMAL_NUMBER_WITHOUT_E = BigDecimal.valueOf(0.0000000000000001);
    private static final BigDecimal MAX_NUMBER_INPUT = BigDecimal.valueOf(9999999999999999L);
    private static final int MAX_SCALE = 16;

    static {
        symbols.setExponentSeparator("e");
        symbols.setGroupingSeparator(' ');
        symbols.setDecimalSeparator(',');
        decimalFormat.setDecimalFormatSymbols(symbols);
    }

    static String formatterNumber(BigDecimal number) {
        StringBuilder pattern = new StringBuilder();
        if (number.abs().compareTo(MAX_NUMBER_INPUT) > 0) {
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
            pattern.append("0.");
            if (number.scale() > MAX_SCALE) {
                number = number.round(new MathContext(MAX_SCALE, RoundingMode.HALF_UP));
                number = number.stripTrailingZeros();
//                if ((number.scale() - number.precision() > 2 && number.scale() == MAX_SCALE) || number.abs().compareTo(MIN_DECIMAL_NUMBER_WITHOUT_E) < 0) {
                if ((number.scale() - number.precision() > 2 ) || number.abs().compareTo(MIN_DECIMAL_NUMBER_WITHOUT_E) < 0) {
                    if (number.precision() != 1 && number.precision() <= MAX_SCALE) {
                        pattern.append("#".repeat(number.precision()));
                    }
                    pattern.append("E0");
                } else {
//                    number = number.setScale(16, RoundingMode.HALF_UP);
                    number = number.setScale(16, RoundingMode.HALF_EVEN);
                    pattern.append("#".repeat(MAX_SCALE));
                }
            } else {
                pattern.append("#".repeat(number.scale()));
            }
        } else {
            pattern.append("#,##0");
            if (number.scale() > 0) {
                number = number.setScale(16, RoundingMode.HALF_UP);
                if (number.scale() < MAX_SCALE) {
                    pattern.append(".").append("#".repeat(number.scale()));
                } else {
                    number = number.round(new MathContext(MAX_SCALE, RoundingMode.HALF_EVEN));
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

    static BigDecimal parseNumber(String text) {
        BigDecimal number = null;
        text = text.replace("+", "").replace(" ", "");
        if (decimalFormat != null && !text.isEmpty()) {
            try {
                decimalFormat.setParseBigDecimal(true);
                number = (BigDecimal) decimalFormat.parse(text);

                number = number.stripTrailingZeros();
                if(number.scale() < 0){
                    number= number.setScale(0);
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return number;
    }

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
