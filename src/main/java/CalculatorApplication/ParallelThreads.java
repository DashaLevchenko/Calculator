package CalculatorApplication;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ParallelThreads {
    private StringBuilder stringBuilder = new StringBuilder();
    private StringBuilder stringBuilderSecond = new StringBuilder();
//    public static void main (String[] args) {
//
//        CalculatorNumberFormatter calculatorNumberFormatter = new CalculatorNumberFormatter();
//        ExecutorService pool = Executors.newFixedThreadPool(2);
//        pool.submit(() -> {
//            try {
//                System.out.println(calculatorNumberFormatter.getParsedNumber("123456e9"));
//
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//        });
//        pool.submit(() -> {
//            try {
////            System.out.println(calculatorNumberFormatter.formatNumber(BigDecimal.valueOf(55555E7), "#,##0E0"));
//            System.out.println(calculatorNumberFormatter.getParsedNumber("789123"));
//
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//        });
//
//
//
//    }

    public static void main (String[] args) {
        ParallelThreads parallelThreads = new ParallelThreads();
        ExecutorService pool = Executors.newFixedThreadPool(2);
        pool.submit(() -> {
            String text = "Hello";
            for (char c : text.toCharArray()) {
//                parallelThreads.stringBuilder.append(c);
                parallelThreads.experiment(String.valueOf(c));
                parallelThreads.experimentSecond(String.valueOf(c));
//                System.out.println(parallelThreads.stringBuilder +" "+Thread.currentThread().getName());
            }
        });
        pool.submit(() -> {
            String text = "World";
            for (char c : text.toCharArray()) {
                parallelThreads.experimentSecond(String.valueOf(c));
                parallelThreads.experiment(String.valueOf(c));
            }
        });

//        System.out.println(parallelThreads.stringBuilder);
    }

    private  void experiment (String string) {
        synchronized (stringBuilderSecond){
            stringBuilder.append(string);
            stringBuilderSecond.append(string);
            System.out.println(stringBuilder +" "+Thread.currentThread().getName()+" First");
            System.out.println(stringBuilderSecond +" "+Thread.currentThread().getName()+" Second");
        }
    }

    private  void experimentSecond (String string) {
        synchronized (stringBuilder){
            stringBuilder.append(string);
            stringBuilderSecond.append(string);
            System.out.println(stringBuilder +" "+Thread.currentThread().getName()+" First");
            System.out.println(stringBuilderSecond +" "+Thread.currentThread().getName()+" Second");
        }
    }

}
