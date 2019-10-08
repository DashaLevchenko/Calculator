package Model;


import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
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
//        System.out.println(new DecimalFormat("#.################").format(r));
//        System.out.println(new DecimalFormat("#.################").format(r2));
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
//        DecimalFormat decimalFormat = new DecimalFormat("#.E0");
//        DecimalFormat decimalFormat2 = new DecimalFormat("##.E0");
//

//        BigDecimal bigDecimal = BigDecimal.valueOf(0.000001234567891234567);
//        BigDecimal z = new BigDecimal("0.06178094427299493");
        BigDecimal x = BigDecimal.valueOf(9999999999999999L);
//        BigDecimal y = BigDecimal.valueOf(1);
//        BigDecimal y = BigDecimal.valueOf(9999999999999999L);
        BigDecimal y = BigDecimal.valueOf(6);
        BigDecimal z = x.add(y);
//        BigDecimal z = new BigDecimal("59999999999999990"); // 5,999999999999999e+16
//        BigDecimal z = new BigDecimal("19999999999999998"); // 1,1e+17
//        BigDecimal z = BigDecimal.valueOf(999999999.9999999);
        z = z.abs(new MathContext(16, RoundingMode.HALF_UP));


        String s = z.toPlainString();


//        System.out.println(separateNumber(z.toString()).replace(".", ",").replace("E", "e"));

        String t = "9999999999999999";
        String n = "9 999 999 999 999 999";
//        String n = "9 999 999 999 999 999";
        DecimalFormat decimalFormat = new DecimalFormat("###,###.#");
        t = decimalFormat.format(new BigDecimal(t));
        System.out.println(t);
        t = decimalFormat.parse(n).toString();
        System.out.println(t);



    }


    private static String separateNumber(String text) {
        String pattern;

        if (new BigDecimal(text).compareTo(BigDecimal.valueOf(9999999999999999L)) > 0) {
            String subString = text.substring(text.indexOf(".")).substring(1, text.indexOf("E")-1);
            if(subString.chars().filter(ch -> ch == '0').count() == subString.length()){
                text = text.replace("0", "");
            }

        } else {
            if (text.contains(".")) {
                int lengthAfterComma = text.substring(text.indexOf(".")).length();
                if (lengthAfterComma > 16){
                    lengthAfterComma = 16;
                }
                pattern = "###,###." + ("#").repeat(lengthAfterComma);
            } else {
                pattern = "###,###";
            }
            text = new DecimalFormat(pattern).format(new BigDecimal(text));
        }

        return text;
    }

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
