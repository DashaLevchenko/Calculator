package Controller;

import Model.Arithmetic;
import Model.OperationsEnum;

import java.math.BigDecimal;

public class Memory {
    private BigDecimal number = null;

    void memoryAdd(BigDecimal numberSecond) {
        if (number != null) {
            number = Arithmetic.sum(number, numberSecond);
        } else{
            number = numberSecond;
        }
    }

    void memorySubtract(BigDecimal numberSecond) {
        if (number != null) {
//            number = Arithmetic.minus(number, numberSecond);
            number = Arithmetic.calculateBinaryOperations(number, numberSecond, OperationsEnum.MINUS);
        } else{
            number = numberSecond;
        }
    }

    BigDecimal memoryRecall() {

        return number;
    }


    public void setNumber(BigDecimal number) {
        this.number = number;
    }
}
