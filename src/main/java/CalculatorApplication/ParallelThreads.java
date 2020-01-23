package CalculatorApplication;

import CalculatorApplication.Controller.CalculatorHistoryFormatter;
import CalculatorApplication.Controller.CalculatorNumberFormatter;
import CalculatorApplication.Model.Calculator;
import CalculatorApplication.Model.Exceptions.DivideZeroException;
import CalculatorApplication.Model.Exceptions.InvalidInputException;
import CalculatorApplication.Model.Exceptions.ResultUndefinedException;

import java.math.BigDecimal;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static CalculatorApplication.Model.OperationsEnum.*;

public class ParallelThreads {
    public static void main (String[] args) {

        CalculatorNumberFormatter calculatorNumberFormatter = new CalculatorNumberFormatter();
        ExecutorService pool = Executors.newFixedThreadPool(3);
//        pool.submit(() -> calculate(8, ADD, 99, SQRT));
        pool.submit(() -> calculatorNumberFormatter.getParsedNumber("99989"));
        pool.submit(() -> calculatorNumberFormatter.getParsedNumber("7688"));
        pool.submit(() -> calculate(99, SQRT, SQR, SQR));
        pool.submit(() -> calculate(2, ADD, 3, EQUAL, SQR));
    }

    @SafeVarargs
    private static  <T> void calculate (T... object) {

        Calculator calculator = new Calculator();
        CalculatorHistoryFormatter calculatorHistoryFormatter = new CalculatorHistoryFormatter();
        try {
            BigDecimal result = calculator.calculator(object);
            System.out.println(result + " history: " + calculatorHistoryFormatter.formatCalculatorHistory(calculator.getHistory()));
        } catch (DivideZeroException | ResultUndefinedException | InvalidInputException e) {
            e.printStackTrace();
        }
    }


}
