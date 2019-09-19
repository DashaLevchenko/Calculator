package Model;

public enum OperationsEnum {
    MINUS ('\uE949', " - "),
    PLUS ('\uE948', " + "),
    MULTIPLY ('\uE947', " x "),
    DIVIDE ('\uE94A', " \u00f7 "),
    PERCENT ('\uE94C',  " % "),
    SQRT ('\uE94B', "\u221A( "),
    SQRX ('\uF7C8', "sqr( "),
    ONE_DIVIDE_X ('\uF7C9', " 1/( ");
;


    private Character operation;
    private String symbol;

    public String getSymbol() {
        return symbol;
    }

    OperationsEnum(Character operation, String symbol){
        this.operation = operation;
        this.symbol = symbol;
    }
    public Character getOperations() {
        return operation;
    }
}
