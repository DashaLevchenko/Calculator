package Model;


import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;

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

    public static void main(String[] args) {
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

        //region for view
//        BigDecimal  numberZero = viewDecimalNumber(new BigDecimal("0.0665163472378805"));
//        BigDecimal  numberOne = viewDecimalNumber(new BigDecimal("1.0665163472378805"));
//        BigDecimal  numberTwo = viewDecimalNumber(new BigDecimal("13.8082191780821918"));
//        BigDecimal  numberThree = viewDecimalNumber(new BigDecimal("102.1538461538461538"));
//
//
//        System.out.println(numberZero);
//        System.out.println(numberOne);
//        System.out.println(numberTwo);
//        System.out.println(numberThree);
        //endregion
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
        String text = "-168546756876543";
        DecimalFormat decimalFormat = new DecimalFormat("###,###");


        System.out.println(decimalFormat.format(new BigDecimal(text)));





    }
    public static BigDecimal viewDecimalNumber(BigDecimal number){
        int scale;
        if (number.compareTo(BigDecimal.ONE) > 0 || number.compareTo(BigDecimal.valueOf(-1)) < 0) {
            int indexPoint = new StringBuilder(number.toPlainString()).indexOf(".");
            scale = 16 - indexPoint;
            number = number.setScale(scale, RoundingMode.HALF_UP);
        }
        return number;
    }

    public static BigDecimal scaleForBigdecimal(BigDecimal numberDouble){

        if (numberDouble.scale() < 0){
            numberDouble = numberDouble.setScale(0);
        }
        return numberDouble;
    }
//    public static String exponent(String i){
//        String result = i.replace("E", "e+" );
//
//        return result;
//    }
}
