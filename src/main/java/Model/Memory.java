package Model;

import java.math.BigDecimal;


/**
 * Class keeps number, add another number to this number,
 * subtract another number frm this number.
 */
public class Memory {
    private BigDecimal number;

//
//    private BigDecimal numberSecond;
//
//    public void setNumberSecond (BigDecimal numberSecond) {
//        this.numberSecond = numberSecond;
//    }

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

//    public void memory (MemoryEnum memoryOperation) {
//        if (memoryOperation == null) {
//            throw new NullPointerException("Enter memory operation");
//        }
//
//        if (memoryOperation.equals(MemoryEnum.MEMORY_ADD)) {
//            memoryAdd();
//        } else if (memoryOperation.equals(MemoryEnum.MEMORY_SUBTRACT)) {
//            memorySubtract();
//        } else if (memoryOperation.equals(MemoryEnum.MEMORY_RECALL)) {
//            memoryRecall();
////        } else if (memoryOperation.equals(MemoryEnum.MEMORY_STORE)) {
////            memorySrore();
//        } else if (memoryOperation.equals(MemoryEnum.MEMORY_CLEAR)) {
//            memoryClear();
//        }
//    }


    public void setNumber (BigDecimal number) {
        this.number = number;
    }
}
