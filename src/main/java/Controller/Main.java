package Controller;

import Model.Arithmetic;
import Model.OperationsEnum;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;

import static java.math.BigDecimal.valueOf;

public class Main {
    static DecimalFormat decimalFormat = new DecimalFormat();

    public static void main(String[] args) throws ParseException {
        BigDecimal z = Arithmetic.calculateBinaryOperations(valueOf(9999999999999999L), valueOf(9999999999999999L), OperationsEnum.PLUS);
        BigDecimal x = Arithmetic.calculateBinaryOperations(valueOf(9999999999999999L), valueOf(6), OperationsEnum.MULTIPLY);
        BigDecimal t = Arithmetic.calculateBinaryOperations(valueOf(1), valueOf(1000000000000000L), OperationsEnum.DIVIDE);
        BigDecimal m = Arithmetic.calculateBinaryOperations(t, valueOf(1000000000000000L), OperationsEnum.DIVIDE);
        BigDecimal b = Arithmetic.calculateBinaryOperations(valueOf(3483.3332), valueOf(56382), OperationsEnum.DIVIDE); //867,8333+3*4/56382=bbbbbbb
        BigDecimal c = Arithmetic.calculateBinaryOperations(valueOf(3483.3332), valueOf(56382), OperationsEnum.PLUS);
        BigDecimal r = Arithmetic.calculateBinaryOperations(valueOf(10), valueOf(3), OperationsEnum.DIVIDE);

//        System.out.println(formatterNumber(z));
//        System.out.println(formatterNumber(x));
//        System.out.println(formatterNumber(t));
//        System.out.println(formatterNumber(m));
//        System.out.println(formatterNumber(b));
//        System.out.println(formatterNumber(c));
//        System.out.println(formatterNumber(r));
//        System.out.println(formatterNumber(Arithmetic.calculateBinaryOperations(BigDecimal.valueOf(111), BigDecimal.valueOf(3.33333), OperationsEnum.MINUS)));
//        System.out.println((Double) decimalFormat.parse("9,88"));
//        System.out.println(("999,".charAt("999,".length()-1))==',');
    }

    private static String formatterNumber(BigDecimal z) {
        StringBuilder pattern = new StringBuilder();
        if (z.compareTo(BigDecimal.valueOf(9999999999999999L)) > 0) {
            pattern.append("0.");
            if (z.precision() > 16) {
                z = z.round(new MathContext(16, RoundingMode.HALF_UP));
                z = z.stripTrailingZeros();

                if (z.precision() != 1 && z.precision() > z.scale()) {
                    pattern.append("#".repeat(z.precision() - z.scale()));
                }
                pattern.append("E0");

            } else {
                pattern.append("#".repeat(z.scale()));
            }
        }else if (z.abs().compareTo(BigDecimal.ONE) < 0){
            if (z.abs().compareTo(BigDecimal.valueOf(0.0000000000000001)) < 0){
                z = z.round(new MathContext(16, RoundingMode.HALF_UP));
                pattern.append("#.E0");
            }else{
                pattern.append("#.").append("#".repeat(16));
            }

        } else {
            pattern.append("###,###");
            if (z.scale()>0){
                if (z.scale() < 16) {
                    pattern.append(".").append("#".repeat(z.scale()));
                }else{
                    pattern.append(".").append("#".repeat(16-(z.precision()-z.scale())));
                }
            }
        }
        decimalFormat.applyPattern(pattern.toString());
        return decimalFormat.format(z);
    }




}
