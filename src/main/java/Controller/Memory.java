package Controller;

import Model.Arithmetic;
import Model.OperationsEnum;

import java.math.BigDecimal;

class Memory {
    private BigDecimal number = null;

    void memoryAdd(BigDecimal numberSecond) {
        if (number != null) {
            number = Arithmetic.calculateBinaryOperations(number, numberSecond, OperationsEnum.PLUS);
        } else{
            number = numberSecond;
        }
    }

    void memorySubtract(BigDecimal numberSecond) {
        if (number != null) {
            number = Arithmetic.calculateBinaryOperations(number, numberSecond, OperationsEnum.MINUS);
        } else{
            number = numberSecond;
        }
    }

    BigDecimal memoryRecall() {
        return number;
    }


    void setNumber(BigDecimal number) {
        this.number = number;
    }
}
