package Model;

import java.math.BigDecimal;

public class Memory {
    private BigDecimal number = null;

    public void memoryAdd(BigDecimal numberSecond) throws ArithmeticException {
        if (number != null) {
            number = number.add(numberSecond);
        } else{
            number = numberSecond;
        }
    }

    public void memorySubtract(BigDecimal numberSecond) {
        if (number != null) {
            number = number.subtract(numberSecond);
        } else{
            number = BigDecimal.ZERO.subtract(numberSecond);
        }
    }

    public BigDecimal memoryRecall() {
        return number;
    }

    public void memoryClear() {
        number = null;
    }


    public void setNumber(BigDecimal number) {
        this.number = number;
    }
}
