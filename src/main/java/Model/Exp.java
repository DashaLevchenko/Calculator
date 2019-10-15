package Model;


import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.Format;
import java.text.ParseException;

import static java.math.BigDecimal.valueOf;

public class Exp {

//    public static String plusL(long number) {
//        String temp = Long.valueOf(number).toString();
//        if (number > Integer.MAX_VALUE) {
//            temp = number + "L";
//
//        }
//        return temp;
//    }

    public static void main(String[] args) throws ParseException {
//        System.out.println(Arithmetic.sum(964197523864192L, 9641975238641969L));//10606172762506161//1,060617276250616e+16
//        long x = 964197523864192L; //1,060617276250616e+16
//        long y = 9641975238641969L;
//        long x = 7777777777777777L; //1,555555555555555e+16
//        long y = 7777777777777777L;
//        long x = 9999999999999999L; //2,e+16
//        long y = 9999999999999999L;
//
//        BigDecimal xy = BigDecimal.valueOf(x+y);//.movePointLeft(16);
//        String result = new DecimalFormat("0.E00").format(xy); //написать метод для стрингового паттерна, это всё в контроллере
//
//        System.out.println(exponent(result));
//
//
//       BigDecimal x = BigDecimal.valueOf(5);
//       System.out.println(x.abs());

//        long x = 593827124;//593827124, 5938271564L, 5344444440L,
//        long y = 5938271564L;//593827124, 5938271564L, 5344444440L,
//        System.out.println(plusL(x+y));
//        BigDecimal x = scaleForBigdecimal(BigDecimal.valueOf(2.0).stripTrailingZeros());
//        BigDecimal u = scaleForBigdecimal(BigDecimal.valueOf(2.8).stripTrailingZeros());
//        BigDecimal y = scaleForBigdecimal(BigDecimal.valueOf(20.880).stripTrailingZeros());
//        BigDecimal i = scaleForBigdecimal(BigDecimal.valueOf(20).stripTrailingZeros());
//
//        System.out.println(x);
//        System.out.println(u);
//        System.out.println(y);
//        System.out.println(i);

//        BigDecimal x = BigDecimal.valueOf(99999999999999999L);
//        BigDecimal y = BigDecimal.valueOf(99999999999999999L);
//        BigDecimal s = x.add(y);
//        System.out.println(s.stripTrailingZeros());
//        System.out.println(BigDecimal.valueOf(99999999999999999L));


//        combinationDivideValidNegative(valueOf(44.568), valueOf(879), "0.0507030716723549");
//        combinationDivideValidNegative(valueOf(191.64), valueOf(7071), "0.0271022486211286");
//        BigDecimal x = valueOf(44.568);
//        BigDecimal y = valueOf(879);
//        BigDecimal r = x.divide(y, MathContext.DECIMAL128);
//
//        BigDecimal x2 = valueOf(191.64);
//        BigDecimal y2 = valueOf(7071);
//        BigDecimal r2 = x2.divide(y2, MathContext.DECIMAL128);
//        BigDecimal x2 = valueOf(214);
//        BigDecimal y2 = valueOf(12);
//        BigDecimal r2 = x2.divide(y2, MathContext.DECIMAL128);

//
//        System.out.println(r);
//        System.out.println(r.abs(new MathContext(15))+"\n");
//        System.out.println(r2);
//        System.out.println(r2.abs(new MathContext(15))+"\n");
//
//        System.out.println(new DecimalFormat("0.0000000000000000").format(r));
//        System.out.println(new DecimalFormat("0.0000000000000000").format(r2));
//

//        int CHARACTERS_IN_LINE = 3;
//        StringBuilder fibonacci = new StringBuilder("1685467568765");
//        int insertSeparator = fibonacci.length()- CHARACTERS_IN_LINE;
//        for (int i = fibonacci.length(); i > 0; i--) {
//            if (i == insertSeparator) {
//                fibonacci.insert(i, " ");
//                insertSeparator -= (CHARACTERS_IN_LINE);
//            }
//        }
//        System.out.println(fibonacci.toString());
//
//        BigDecimal  numberZero = viewDecimalNumber(new BigDecimal("0.0665163472378805"));
//        BigDecimal  numberOne = viewDecimalNumber(new BigDecimal("1.0665163472378805"));
//        BigDecimal  numberTwo = viewDecimalNumber(new BigDecimal("13.8082191780821918"));
//        BigDecimal  numberThree = viewDecimalNumber(new BigDecimal("102.1538461538461538"));
////        BigDecimal sum = viewDecimalNumber(Arithmetic.sum(BigDecimal.valueOf(9999999999999999L), BigDecimal.valueOf(9999999999999999L)));
//        BigDecimal result = Arithmetic.sum(BigDecimal.valueOf(8.999999999999999), BigDecimal.valueOf(8.999999999999999));
//        BigDecimal result2 = Arithmetic.sum(BigDecimal.valueOf(9999999999999999L), BigDecimal.valueOf(9999999999999999L));
//        BigDecimal result4 = Arithmetic.sum(BigDecimal.valueOf(8888888888888888L), BigDecimal.valueOf(8888888888888888L));
//        BigDecimal result3 = Arithmetic.sum(BigDecimal.valueOf(7777777777777777L), BigDecimal.valueOf(7777777777777777L));
//        if (result.toString().length() > 16){

//        }
//        BigDecimal x = BigDecimal.valueOf(9999999999999999L); //2,e+16
//        BigDecimal y = BigDecimal.valueOf(9999999999999999L); //2,e+16  5,999999999999999e+16
//
//        DecimalFormat decimalFormat = new DecimalFormat("0.E0");
//        DecimalFormat decimalFormat2 = new DecimalFormat("00.E0");
//

//        BigDecimal bigDecimal = BigDecimal.valueOf(0.000001234567891234567);
        BigDecimal x = BigDecimal.valueOf(9999999999999999L);
//        BigDecimal y = BigDecimal.valueOf(1);
//        BigDecimal y = BigDecimal.valueOf(9999999999999999L);
        BigDecimal y = BigDecimal.valueOf(6); // 1,000000000000001e+16
//        BigDecimal z = new BigDecimal("0.06178094427299493");//!!
//        BigDecimal z = x.add(y);
//        BigDecimal z = new BigDecimal("111110000000000000000"); //1,1111e+20
//        BigDecimal z = new BigDecimal("59999999999999999"); // 5,999999999999999e+16
        BigDecimal z = new BigDecimal("19999999999999998");
//        BigDecimal z = new BigDecimal("0.0000000000000005062500000000001"); //5.062500000000001E-16
//        BigDecimal z = BigDecimal.valueOf(999999999.9999999);
//        BigDecimal z = new BigDecimal("0.000000000000000001");
//        BigDecimal z = new BigDecimal("0.000000000000000000");
//        BigDecimal z = new BigDecimal("0.000000000000001"); //0,000000000000001
//        BigDecimal z = BigDecimal.valueOf(9999.8888889);
//        System.out.println(z.scale());
//        z = new BigDecimal(z.toString(), new MathContext(16, RoundingMode.HALF_UP));

//        String s = z.toPlainString();
//0,000000000000001
//        System.out.println(separateNumber(z));
//        System.out.println(new DecimalFormat("#.E0").format(z));
//        System.out.println(z);

//        System.out.println(z.scale());
//        System.out.println(z.toString());


//        DecimalFormat decimalFormat = new DecimalFormat("000,000.000");
//        BigDecimal j = new BigDecimal(decimalFormat.format(BigDecimal.valueOf(8.454)).replace(",", "."));
//        String k = decimalFormat.parse(j.toString().replace(".", ",")).toString();
//
//        System.out.println(j);
//        System.out.println(k);

//        String s;
//        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
//        decimalFormatSymbols.setExponentSeparator("e");
//        decimalFormatSymbols.setGroupingSeparator(' ');
//        decimalFormatSymbols.setDecimalSeparator(',');
//
//        s = new DecimalFormat("0.E0", decimalFormatSymbols).format(BigDecimal.valueOf(199999999999998L));
//        System.out.println(new DecimalFormat("0.E0", decimalFormatSymbols).format(BigDecimal.valueOf(199999999999998L)));
//        System.out.println(new DecimalFormat("0.E0", decimalFormatSymbols).format(BigDecimal.valueOf(0.000000000000000001)));


        BigDecimal f = BigDecimal.valueOf(0.00000000);

        System.out.println(f.scale());
        System.out.println(f);

    }

    private static String separateNumber(BigDecimal number) {
        String out = number.toString();
        DecimalFormat decimalFormat = null;
        if (number.compareTo(BigDecimal.valueOf(999999999999999L)) > 0) {
            number = new BigDecimal(number.toString(), new MathContext(16, RoundingMode.HALF_UP));

            decimalFormat = new DecimalFormat("#." + number.toPlainString().replaceAll("[1-9]", "0").replaceAll("0", "#") + "E0");//1,1111e+20

        } else if (number.compareTo(BigDecimal.ONE) < 0 && number.compareTo(BigDecimal.valueOf(-1)) > 0) {

            if (number.compareTo(BigDecimal.valueOf(0.000000000000001)) < 0) {
                decimalFormat = null;
            } else {
//                number = new BigDecimal(number.toString(), new MathContext(16, RoundingMode.HALF_UP));
                decimalFormat = new DecimalFormat("0.00000000000000##");
//                decimalFormat = new DecimalFormat(number.toPlainString().replaceAll("[1-9]", "0").replaceAll("0", "#"));

            }

        } else {
            decimalFormat = new DecimalFormat("###,###.#######");
        }
        if (decimalFormat != null) {
            out = decimalFormat.format(number);
        }
        if (out.contains("E") && !out.contains(",")) {
            out = new StringBuilder(out).insert(1, ",").toString();
        }

        return out;
    }


//    private static String separateNumber(String text) {
//        String pattern;
//
//        if (new BigDecimal(text).compareTo(BigDecimal.valueOf(9999999999999999L)) > 0) {
//            int scale1 = new BigDecimal(text).stripTrailingZeros().scale();
//            if (scale1 > 0) {
//                pattern = "0." + "#".repeat(scale1) + "E0";
//            } else {
//                pattern = "#.E0";
//            }
//                text = new DecimalFormat(pattern).format(new BigDecimal(text));
//
//        } else if (new BigDecimal(text).compareTo(BigDecimal.valueOf(-1)) > 0 && new BigDecimal(text).compareTo(BigDecimal.ONE) < 0)  {
//
//        } else {
//            if (text.contains(".")) {
//                int lengthAfterComma = text.substring(text.indexOf(".") + 1).length();
//                if (lengthAfterComma > 16) {
//                    lengthAfterComma = 16;
//                }
//                pattern = "###,##0." + ("0").repeat(lengthAfterComma);
//            } else {
//                pattern = "###,##0";
//            }
//            text = new DecimalFormat(pattern).format(new BigDecimal(text));
//        }
//
//
//
//        return text;
//    }

    private static void test(StringBuilder p, int perCent) {
        int temporary = p.length() - 1;
        for (int i = temporary; i > 0; i--) {
            if (temporary % 3 == perCent) {

                if (i % 3 == perCent && i != perCent) {
                    p.insert(i, " ");
                    temporary += 1;
                }

            }

        }
        System.out.println(p.toString());
    }

    public static BigDecimal viewDecimalNumber(BigDecimal number) {
        int scale;
        if (number.compareTo(BigDecimal.ONE) > 0 || number.compareTo(BigDecimal.valueOf(-1)) < 0) {
            int indexPoint = new StringBuilder(number.toPlainString()).indexOf(".");
            scale = 16 - indexPoint;
            number = number.setScale(scale, RoundingMode.HALF_UP);
        }
        return number;
    }

    public static BigDecimal scaleForBigdecimal(BigDecimal numberDouble) {

        if (numberDouble.scale() < 0) {
            numberDouble = numberDouble.setScale(0);
        }
        return numberDouble;
    }

    public static String exponent(String i) {
        String result = i.replace("E", "e+");

        return result;
    }
}
