package Controller;

import Model.Arithmetic;

import java.math.BigDecimal;

class Memory {
    private BigDecimal number = null;

    void memoryAdd(BigDecimal numberSecond) throws ArithmeticException {
        if (number != null) {
            number = Arithmetic.sum(number, numberSecond);
        } else{
            number = numberSecond;
        }
    }

    void memorySubtract(BigDecimal numberSecond) {
        if (number != null) {
            number = Arithmetic.minus(number, numberSecond);
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
