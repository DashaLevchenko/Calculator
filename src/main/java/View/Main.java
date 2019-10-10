package View;

import Model.Arithmetic;
import Model.OperationsEnum;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import static java.math.BigDecimal.valueOf;

public class Main {
    static DecimalFormat decimalFormat = new DecimalFormat();
    public static void main(String[] args) {
        BigDecimal z = Arithmetic.calculateBinaryOperations(valueOf(9999999999999999L), valueOf(9999999999999999L), OperationsEnum.PLUS);
        BigDecimal x = Arithmetic.calculateBinaryOperations(valueOf(9999999999999999L), valueOf(6), OperationsEnum.MULTIPLY);
        BigDecimal t = Arithmetic.calculateBinaryOperations(valueOf(1), valueOf(1000000000000000L), OperationsEnum.DIVIDE);

        System.out.println(formatterNumber(z));
        System.out.println(formatterNumber(x));
        System.out.println(formatterNumber(t));
    }

    private static String formatterNumber(BigDecimal z) {
        StringBuilder pattern = new StringBuilder("");
        if(z.precision() > 16){
            pattern.append("0.");
            z = z.round(new MathContext(16, RoundingMode.HALF_UP));
            z = z.stripTrailingZeros();

                if (z.precision() != 1) {
                    pattern.append("#".repeat(z.precision()-z.scale()));
                }

            pattern.append("E0");
        }
        decimalFormat.applyPattern(pattern.toString());
        return decimalFormat.format(z);
    }


}
