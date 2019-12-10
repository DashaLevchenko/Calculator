package Demo;

import Model.Calculator;
import Model.Exceptions.DivideZeroException;
import Model.Exceptions.InvalidInputException;
import Model.Exceptions.OperationException;
import Model.Exceptions.ResultUndefinedException;
import Model.OperationsEnum;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

public class Demo {
    private static HashMap<String, OperationsEnum> operations = new HashMap<>();

    static {
        operations.put("+", OperationsEnum.ADD);
        operations.put("-", OperationsEnum.SUBTRACT);
        operations.put("/", OperationsEnum.DIVIDE);
        operations.put("x", OperationsEnum.MULTIPLY);

        operations.put("√", OperationsEnum.SQRT);
        operations.put("", OperationsEnum.SQR);
        operations.put("", OperationsEnum.ONE_DIVIDE_X);
        operations.put("%", OperationsEnum.PERCENT);

        operations.put("", OperationsEnum.NEGATE);
        operations.put("=", OperationsEnum.EQUAL);
    }

    public static void main (String[] args) {
        String input = "5+3/2-1=√+4=";

        try {
            BigDecimal result = Calculator.calculator(toArray(input));
            System.out.println("Result: " + result);
        } catch (OperationException | DivideZeroException | ResultUndefinedException | InvalidInputException e) {
            e.printStackTrace();
        }
    }


    private static ArrayList<Object> toArray (String args) {
        ArrayList<Object> array = new ArrayList<>();
        for (int i = 0; i < args.length(); i++) {
            String symbol = String.valueOf(args.toCharArray()[i]);

            if (isNumber(symbol)) {
                 addNumber(symbol, args, i, array);
            }

            if (isOperation(symbol)) {
               addOperation(symbol, array);
            }
        }
        return array;
    }

    private static String numberString = "";

    private static void addNumber (String symbol, String formulaInput, int i, ArrayList<Object> array) {
        numberString = numberString.concat(symbol);
        if (!isNumberNext(formulaInput, i)) {
            BigDecimal number = new BigDecimal(numberString);
            numberString = "";
            array.add(number);
        }
    }

    private static void addOperation (String symbol, ArrayList<Object> array) {
        OperationsEnum operation = operations.get(symbol);
        array.add(operation);
    }

    private static boolean isNumberNext (String args, int i) {
        boolean isNumberNext = false;
        int indexNext = i + 1;
        if (args.length() > indexNext) {
            String symbolNext = String.valueOf(args.toCharArray()[indexNext]);
            isNumberNext = isNumber(symbolNext);
        }

        return isNumberNext;
    }

    private static boolean isOperation (Object object) {
        return operations.containsKey(object.toString());
    }

    private static boolean isNumber (Object object) {
        boolean isNumber = false;
        if (object != null) {
            try {
                new BigDecimal(object.toString());
                isNumber = true;
            } catch (Exception e) {
                isNumber = false;
            }
        }

        return isNumber;
    }


}
