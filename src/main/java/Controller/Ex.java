package Controller;

import Model.Calculator;
import Model.History;

import java.math.BigDecimal;
import java.text.ParseException;

public class Ex {

    public static void main (String[] args) throws Exception {
//        Calculator.calculator(6, NEGATE, NEGATE, NEGATE);
//        prH();
//        prHF();
//        Calculator.calculator(6, ADD, 9, SUBTRACT, NEGATE, NEGATE, NEGATE,ADD);
//        prH();
//        prHF();
//        Calculator.calculator(6, ADD, 9, SUBTRACT, 8,NEGATE, NEGATE, NEGATE,ADD);
//        prH();
//        prHF();
//        Calculator.calculator(6, EQUAL, NEGATE, NEGATE, ADD);
//        prH();
//        Calculator.calculator(6, NEGATE, NEGATE, ADD);
//        prH();
//        prHF();
//        Calculator.calculator(6, ADD, 8, NEGATE, NEGATE, ADD);
//        prH();
//        prHF();

        String u = "-9";
        BigDecimal j = CalculatorNumberFormatter.getParsedNumber(u);

        System.out.println(CalculatorNumberFormatter.addNegatePrefix(j));




    }

    private static void prHF () {
        History h = Calculator.getHistory();

        for (int i = 0; i < h.size(); i++) {
            System.out.print(" "+h.get(i));
        }
        System.out.println();
        System.out.println();
    }

    private static void prH () throws ParseException {
        History h = Calculator.getHistory();
        CalculatorHistoryFormatter k = new CalculatorHistoryFormatter(h);
        System.out.println(k.formatHistory());
    }


}
