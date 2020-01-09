package CalculatorApplication.Model;

import java.math.BigDecimal;


/**
 * Class keeps number, add another number to this number,
 * subtract another number frm this number.
 */
public class Memory {
    private BigDecimal number;

    /**
     * Method adds numbers
     */
    public void memoryAdd(BigDecimal numberSecond) {
        if (number != null) {
            number = number.add(numberSecond);
        } else {
            number = numberSecond;
        }
    }

    /**
     * Method subtracts numbers
     */
    public void memorySubtract(BigDecimal numberSecond) {
        if (number != null) {
            number = number.subtract(numberSecond);
        } else {
            number = BigDecimal.ZERO.subtract(numberSecond);
        }
    }

    /**
     * Method returns {@code number}
     *
     * @return Number
     */
    public BigDecimal memoryRecall () {
        return number;
    }

    /**
     * This method set null value to {@code number}
     */
    public void memoryClear () {
        number = null;
    }

    public void setNumber (BigDecimal number) {
        this.number = number;
    }
}
