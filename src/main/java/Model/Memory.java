package Model;

import java.math.BigDecimal;


/**
 * Class calculates number without print
 */
public class Memory {
    private BigDecimal number;

    /**
     * Method adds numbers
     * @param numberSecond Number which added to {@code number}
     */
    public void memoryAdd(BigDecimal numberSecond) {
        if (number != null) {
            number = number.add(numberSecond);
        } else{
            number = numberSecond;
        }
    }
    /**
     * Method subtracts numbers
     * @param numberSecond Number which subtract to {@code number}
     */
    public void memorySubtract(BigDecimal numberSecond) {
        if(numberSecond.equals(BigDecimal.valueOf(-0.1))){
            System.out.println("l");
        }
        if (number != null) {
            number = number.subtract(numberSecond);
        } else{
            number = BigDecimal.ZERO.subtract(numberSecond);
        }
    }

    /**
     * Method returns {@code number}
     * @return Number
     */
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
