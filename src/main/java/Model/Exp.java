package Model;


import java.math.BigDecimal;

public class Exp {
    public static String plusL(long number) {
        String temp = Long.valueOf(number).toString();
        if (number > Integer.MAX_VALUE) {
            temp = number + "L";

        }
        return temp;
    }

    public static void main(String[] args) {
//        System.out.println(Sum.sum(964197523864192L, 9641975238641969L));//10606172762506161//1,060617276250616e+16
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
        BigDecimal x = BigDecimal.valueOf(2.0);
        BigDecimal y = BigDecimal.valueOf(20.0);

        System.out.println(x.stripTrailingZeros());
        System.out.println(y.stripTrailingZeros());
//
//    }

//    public static String exponent(String i){
//        String result = i.replace("E", "e+" );
//
//        return result;
    }
}
