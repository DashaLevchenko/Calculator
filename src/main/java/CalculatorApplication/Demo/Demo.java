package CalculatorApplication.Demo;

import CalculatorApplication.Model.Calculator;
import CalculatorApplication.Model.Exceptions.DivideZeroException;
import CalculatorApplication.Model.Exceptions.InvalidInputException;
import CalculatorApplication.Model.Exceptions.ResultUndefinedException;


/**
 * This class demonstrations calculator model.
 */
public class Demo {

    public static void main (String[] args) {
        System.out.print("Result: ");
        Calculator calculator = new Calculator();
        try {
            System.out.println(calculator.calculator(153));

        } catch (Exception e) {
            String messageError;
            if (e instanceof DivideZeroException) {
                messageError = "Cannot divide by zero";
            } else if (e instanceof ResultUndefinedException) {
                messageError = "Result is undefined";
            } else if (e instanceof InvalidInputException) {
                messageError = "Invalid input";
            } else {
                messageError = "Something wrong!";
                e.printStackTrace();
            }
            System.out.println(messageError);
        }
    }
}