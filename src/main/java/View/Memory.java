package View;

import Model.Arithmetic;

import java.math.BigDecimal;

public class Memory {
    private BigDecimal number = null;

    Memory(BigDecimal number) {
        this.number = number;
    }

    void memoryPlus(BigDecimal numberSecond) {
        if (number != null) {
            number = Arithmetic.sum(number, numberSecond);
        } else{
            number = numberSecond;
        }
    }

    void memoryMinus(BigDecimal numberSecond) {
        if (number != null) {
            number = Arithmetic.minus(number, numberSecond);
        } else{
            number = numberSecond;
        }
    }

    BigDecimal memoryRecall() {

        return number;
    }


}
