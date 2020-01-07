package Controller;

import Model.Calculator;
import Model.Exceptions.DivideZeroException;
import Model.Exceptions.InvalidInputException;
import Model.Exceptions.ResultUndefinedException;
import Model.MemoryEnum;

import java.math.BigDecimal;

public class e {

    public static void main (String[] args) throws DivideZeroException, ResultUndefinedException, InvalidInputException {
        BigDecimal u = Calculator.calculator(8, MemoryEnum.MEMORY_ADD,  MemoryEnum.MEMORY_ADD, MemoryEnum.MEMORY_RECALL );
        System.out.println(u);
    }
}
