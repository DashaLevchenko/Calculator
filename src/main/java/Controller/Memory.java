package Controller;

import java.math.BigDecimal;

class Memory {
    private BigDecimal number = null;

    void memoryAdd(BigDecimal numberSecond) throws ArithmeticException {
        if (number != null) {
            number = number.add(numberSecond);
        } else{
            number = numberSecond;
        }
    }

    void memorySubtract(BigDecimal numberSecond) {
        if (number != null) {
            number = number.subtract(numberSecond);
        } else{
            number = BigDecimal.ZERO.subtract(numberSecond);
        }
    }

    BigDecimal memoryRecall() {
        return number;
    }


    void setNumber(BigDecimal number) {
        this.number = number;
    }
}
