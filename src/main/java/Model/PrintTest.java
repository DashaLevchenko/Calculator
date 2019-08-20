package Model;

public class PrintTest {

    public static void main(String[] args) {

        for (long i = 0, j = 9999999999999990L; i < 10; i++) {
            System.out.println("assertionSumValid(" + j +"L, " + j + "L, " + (j+j)+"L);");
            j++;
        }
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
//        }
    }
}
