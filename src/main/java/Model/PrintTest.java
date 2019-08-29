package Model;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Random;

import static java.math.BigDecimal.valueOf;

public class PrintTest {

    public static void main(String[] args) {

//        for (long i = 0, j = 9999999999999990L; i < 10; i++) {
//            System.out.println("assertionSumValid(" + j +"L, " + j + "L, " + (j+j)+"L);");
//            j++;
//        }
//        for (long i = 2, j = 10; i < 100; i+=12, j += 123) {
//            System.out.println("assertionSumValid(" + i +", " + -j + ", " + (i+-j)+ ");");
//
//        }for (long i = 2, j = 10; i < 100; i+=12, j += 123) {
//            System.out.println("assertionSumValid(" + -i +", " + -j + ", " + (-i+-j)+ ");");
//
//        }
//        String number = "5";
//        for (long i = 0 ; i < 16; i++) {
//            System.out.println("combinationsSumValid(" + number +", " + number + ");");
//            number +=5;
//        }
//        number = "9";
//        for (long i = 0 ; i < 16; i++) {
//            System.out.println("combinationsSumValid(" + number +", " + number + ");");
//            number +=9;

//        for (long i = 1; i < 11; i++) {
//            for (int j = 1; j < 11; j++) {
//                System.out.println("combinationMultiplyValidNegative(valueOf("+i+"), valueOf("+j+"), \""+(i*j)+"\");");
//            }
//        }
        //16
//        Random randomFirst = new Random();
//        Random randomSecond = new Random();
//        for (int i = 1; i < 11; i++) {
//            String firstFirst = Integer.valueOf(randomFirst.nextInt((100000000 - 10000000) + 1) + 10000000).toString();
//            String first = Integer.valueOf(randomFirst.nextInt((10000000 - 1000000) + 1) + 1000000).toString();
//            String second = Integer.valueOf(randomSecond.nextInt(999999999)).toString();
//            System.out.println("combinationDivideValidNegative(valueOf(" + i +firstFirst+ first + "L), valueOf(" + second + "), \"\");");
//        }

        for (int i = 1; i < 122; i++) {
            System.out.println("assertionSQRTValid(valueOf(" + i+"), valueOf("+ BigDecimal.valueOf(i).sqrt(new MathContext(16, RoundingMode.HALF_UP))+"));");

        }
    }
}
//        for sum
//        BigDecimal minus = x.subtract(y, new MathContext(16, RoundingMode.HALF_DOWN));
//        if(minus.compareTo(ZERO) < 0){
//            minus = minus.negate();
//        }
//        System.out.println("combinationSumValidNegative(valueOf("+x+"), valueOf("+y+"), \""+x.add(y, new MathContext(16, RoundingMode.HALF_DOWN)).toPlainString()+"\", \""+minus.toPlainString()+"\");");

//          for multiply
//        System.out.println("combinationMultiplyValidNegative(valueOf("+ x +"), valueOf("+y+"), \""+x.multiply(y, new MathContext(16)).toPlainString()+"\");");

//        sor sqrt
//        System.out.println("assertionSQRTValid(valueOf("+x+"), \""+x.sqrt(new MathContext(16, RoundingMode.HALF_DOWN)).toPlainString()+"\");");