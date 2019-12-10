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


/**
 * This class demonstrations calculator model.
 */
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

        try {
            BigDecimal result = Calculator.calculator(0);
            System.out.println("Result: " + result);
        } catch (OperationException | DivideZeroException | ResultUndefinedException | InvalidInputException e) {
            e.printStackTrace();
        }
    }

    // This method checks each symbol from string, adds number or operation to list and returns this list.
    private static ArrayList<Object> toArray (String input) {
        ArrayList<Object> array = new ArrayList<>();
        for (int i = 0; i < input.length(); i++) {
            String symbol = String.valueOf(input.toCharArray()[i]);

            if (isNumber(symbol)) {
                addNumber(symbol, input, i, array);
            }

            if (isOperation(symbol)) {
                addOperation(symbol, array);
            }
        }
        return array;
    }

    private static String numberString = "";

    /*Method adds number to list.
     * if it is a number and next symbol is not number,
     * symbol is written down to variable "numberString" and  this variable casting to BigDecimal and is added to list.
     *If symbol is number and next symbol too, symbol is written down to variable "numberString".
     */
    private static void addNumber (String symbol, String formulaInput, int i, ArrayList<Object> array) {
        numberString = numberString.concat(symbol);
        boolean isNumberNext = isNumberNext(formulaInput, i);

        if (!isNumberNext) {
            BigDecimal number = new BigDecimal(numberString);
            array.add(number);
            numberString = "";
        }
    }

    //Method adds operation to list.
    private static void addOperation (String symbol, ArrayList<Object> array) {
        OperationsEnum operation = operations.get(symbol);
        array.add(operation);
    }

    // Method returns true if next symbol is number or false if isn't.
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
