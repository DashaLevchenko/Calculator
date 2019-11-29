package Controller;

import Model.Calculator;
import Model.Memory;
import Model.OperationsEnum;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

public class CalculatorController {
    //region FXML object
    @FXML
    private GridPane calculatorButtons;

    @FXML
    private Button zero;

    @FXML
    private Button seven;

    @FXML
    private Button eight;

    @FXML
    private Button nine;

    @FXML
    private Button four;

    @FXML
    private Button five;

    @FXML
    private Button six;

    @FXML
    private Button one;

    @FXML
    private Button two;

    @FXML
    private Button three;

    @FXML
    private GridPane memoryPanel;

    @FXML
    private Button memoryClear;

    @FXML
    private Button memoryRecall;

    @FXML
    private Button memoryStore;

    @FXML
    private Button memoryAdd;

    @FXML
    private Button memorySubtract;

    @FXML
    private Button divide;

    @FXML
    private Button multiply;

    @FXML
    private Button subtract;

    @FXML
    private Button add;

    @FXML
    private Button equal;

    @FXML
    private Button percent;

    @FXML
    private Button sqrt;

    @FXML
    private Button sqr;

    @FXML
    private Button oneDivideX;

    @FXML
    private Label generalDisplay;

    @FXML
    private Label outOperationMemory;

    @FXML
    private ScrollPane scrollPaneOperation;

    @FXML
    private Button scrollButtonLeft;

    @FXML
    private Button scrollButtonRight;

    @FXML
    private Button CE;

    @FXML
    private Button C;

    @FXML
    private Button backspace;

    @FXML
    private Button plusMinus;

    @FXML
    private Button point;

    @FXML
    private Label title;

    @FXML
    private Label textStandard;

    @FXML
    private Button cancel;

    @FXML
    private Button maximizeButton;


    @FXML
    private Button hideButton;

    @FXML
    private AnchorPane leftMenu;

    @FXML
    private AnchorPane generalAnchorPane;
    //endregion

    static HashMap<OperationsEnum, String> operationSymbols = new HashMap<>();
    static HashMap<String, OperationsEnum> operation = new HashMap<>();

    static {
        operationSymbols.put(OperationsEnum.ADD, "+");
        operationSymbols.put(OperationsEnum.SUBTRACT, "-");
        operationSymbols.put(OperationsEnum.DIVIDE, "÷");
        operationSymbols.put(OperationsEnum.MULTIPLY, "x");

        operationSymbols.put(OperationsEnum.SQRT, "√");
        operationSymbols.put(OperationsEnum.SQR, "sqr");
        operationSymbols.put(OperationsEnum.ONE_DIVIDE_X, "1/");
        operationSymbols.put(OperationsEnum.PERCENT, "");

        operationSymbols.put(OperationsEnum.NEGATE, "negate");

        operation.put("add", OperationsEnum.ADD);
        operation.put("subtract", OperationsEnum.SUBTRACT);
        operation.put("divide", OperationsEnum.DIVIDE);
        operation.put("multiply", OperationsEnum.MULTIPLY);

        operation.put("sqrt", OperationsEnum.SQRT);
        operation.put("sqr", OperationsEnum.SQR);
        operation.put("oneDivideX", OperationsEnum.ONE_DIVIDE_X);
        operation.put("percent", OperationsEnum.PERCENT);


    }

    private final int CHAR_MAX_INPUT = 16;
    private final BigDecimal MAX_NUMBER = new BigDecimal("1E10000");
    private final BigDecimal MIN_NUMBER = new BigDecimal("1E-10000");

    private OperationsEnum binaryOperation;
    private OperationsEnum unaryOperation;

    private int charValidInText = 16;
    private String firstStyleLabel;
    //    private String historyOperations = "";
    private String historyUnaryOperations = "";
    private String negateHistory = "";
    private String defaultText = "0";
    private String decimalSeparate = ",";
    private String separatorNumber = " ";
    private String minus = "-";
    private String emptyString = "";

    private boolean pointInText = false;
    private boolean canChangeOperator = false;
    private boolean equalWasPress;
    private boolean showLeftMenu = false;
    private boolean isError = false;
    private boolean negatePressed = false;
    private boolean memoryPressed = false;
    private boolean canBackspace = true;

    private double moveScroll;
    private double xOffset = 0;
    private double yOffset = 0;
    private double speedOfAnimation = 0.1;

    private Memory memory;
    private boolean percentPressed = false;
    private Calculator calculator = new Calculator();


    @FXML
    void initialize () {
        firstStyleLabel = generalDisplay.getStyle();
        generalDisplay.setText(defaultText);
        equal.setDefaultButton(true);
    }

    @FXML
    void showLeftMenu () {
        TranslateTransition transition = new TranslateTransition(Duration.seconds(speedOfAnimation), leftMenu);
        if (!showLeftMenu) {
            transition.setToX(leftMenu.getWidth());
            showLeftMenu = true;
            textStandard.setVisible(false);
        } else {
            transition.setToX(0);
            showLeftMenu = false;
            textStandard.setVisible(true);
        }
        transition.play();
    }

    private void operationsIsDisable (boolean disable) {
        percent.setDisable(disable);
        sqrt.setDisable(disable);
        sqr.setDisable(disable);
        oneDivideX.setDisable(disable);
        divide.setDisable(disable);
        multiply.setDisable(disable);
        subtract.setDisable(disable);
        add.setDisable(disable);
        plusMinus.setDisable(disable);
        point.setDisable(disable);
    }

    //region Text

    @FXML
    void inputComma (ActionEvent actionEvent) {
        String out = generalDisplay.getText();

        if (!pointInText) {
            if (!canBackspace) {
                out = defaultText;
                canBackspace = true;
            }
            if (out.equals(defaultText)) {
                charValidInText++;
            }
            out += decimalSeparate;
            pointInText = true;
            charValidInText++;
            canChangeOperator = false;
        }

        printResult(out);
        memoryPressed = false;
    }

    @FXML
    void inputBackspace () {
        clearError();
        String out = generalDisplay.getText();
        if (canBackspace) {
            out = Text.backspace(out);

            if (pointInText) {
                if (!out.contains(decimalSeparate)) {
                    pointInText = false;
                    charValidInText--;
                }
                printResult(out);
            } else {
                printResult(formatterNumber(new BigDecimal(Text.deleteNumberSeparator(out, separatorNumber))));
            }
        }
    }

    @FXML
    void inputNumber (ActionEvent actionEvent) {
        String buttonText = ((Button) actionEvent.getSource()).getText();

        clearError();

        String out = Text.getTextLabel(generalDisplay, separatorNumber);
        out = clearDisplay(out);

        if (out.equals(defaultText)) {
            printResult(buttonText);
        } else {
            if (out.length() < charValidInText) {
                out += buttonText;
                printResult(FormatterNumber.formatterInputNumber(out));
            }
        }


        scrollOutOperationMemory();
        canChangeOperator = false;
    }

    @FXML
    void negate () {
        negatePressed = true;

        setNumberNegate();

        String out = Text.getTextLabel(generalDisplay, emptyString);
        out = Text.addNegate(out);

        if (out.contains(minus)) {
            charValidInText++;
        } else {
            charValidInText--;
        }

        printResult(out);
        scrollOutOperationMemory();
        printHistory();

    }

    @FXML
    void keyPressed (KeyEvent event) {
        KeyCode keyCode = event.getCode();
        if (keyCode == KeyCode.DIGIT1 || keyCode == KeyCode.NUMPAD1) {
            one.fire();
        } else if (keyCode == KeyCode.DIGIT2 || keyCode == KeyCode.NUMPAD2) {
            if (event.isShiftDown()) {
                sqrt.fire();
            } else {
                two.fire();
            }
        } else if (keyCode == KeyCode.DIGIT3 || keyCode == KeyCode.NUMPAD3) {
            three.fire();
        } else if (keyCode == KeyCode.DIGIT4 || keyCode == KeyCode.NUMPAD4) {
            four.fire();
        } else if (keyCode == KeyCode.DIGIT5 || keyCode == KeyCode.NUMPAD5) {
            if (event.isShiftDown()) {
                percent.fire();
            } else {
                five.fire();
            }
        } else if (keyCode == KeyCode.DIGIT6 || keyCode == KeyCode.NUMPAD6) {
            six.fire();
        } else if (keyCode == KeyCode.DIGIT7 || keyCode == KeyCode.NUMPAD7) {
            seven.fire();
        } else if (keyCode == KeyCode.DIGIT8 || keyCode == KeyCode.NUMPAD8) {
            if (event.isShiftDown()) {
                multiply.fire();
            } else {
                eight.fire();
            }
        } else if (keyCode == KeyCode.DIGIT9 || keyCode == KeyCode.NUMPAD9) {
            nine.fire();
        } else if (keyCode == KeyCode.DIGIT0 || keyCode == KeyCode.NUMPAD0) {
            zero.fire();
        } else if (keyCode == KeyCode.ESCAPE) {
            C.fire();
        } else if (keyCode == KeyCode.BACK_SPACE) {
            backspace.fire();
        } else if (keyCode == KeyCode.COMMA) {
            point.fire();
        } else if (keyCode == KeyCode.ADD || (keyCode == KeyCode.EQUALS && event.isShiftDown())) {
            add.fire();
        } else if (keyCode == KeyCode.MINUS || keyCode == KeyCode.SUBTRACT) {
            subtract.fire();
        } else if (keyCode == KeyCode.MULTIPLY) {
            multiply.fire();
        } else if (keyCode == KeyCode.SLASH || keyCode == KeyCode.DIVIDE) {
            divide.fire();
        } else if (keyCode == KeyCode.ENTER) {
            equal.fire();
        } else if (keyCode == KeyCode.F9) {
            plusMinus.fire();
        } else if (keyCode == KeyCode.DELETE) {
            CE.fire();
        } else if (keyCode == KeyCode.R) {
            if (event.isControlDown()) {
                memoryRecall.fire();
            } else {
                oneDivideX.fire();
            }
        } else if (keyCode == KeyCode.Q) {
            if (event.isControlDown()) {
                memorySubtract.fire();
            } else {
                sqr.fire();
            }
        } else if ((keyCode == KeyCode.M && event.isControlDown())) {
            memoryStore.fire();
        } else if ((keyCode == KeyCode.P && event.isControlDown())) {
            memoryAdd.fire();
        } else if ((keyCode == KeyCode.L && event.isControlDown())) {
            memoryClear.fire();
        }
    }

    //endregion

    //region Set number

    /*
    This method sets negative number in calculator object after negative button was pressed
     */
    private void setNumberNegate () {
        if (equalWasPress) {
            if (!memoryPressed) {
                if (getFirstNumber() != null) {
                    setCalculatorFirstNumber(getFirstNumber().negate());
                } else {
                    setCalculatorFirstNumber(getDisplayNumber().negate());
                }

            }else{
                setCalculatorFirstNumber(getSecondNumber().negate());
                setCalculatorSecondNumber(null);
                memoryPressed = false;
            }
            calculator.getHistory().deleteLast();
            calculator.getHistory().addNumber(getFirstNumber().negate());
        } else {
            if (binaryOperation == null) {
                setBinaryFirstNumber();
                setCalculatorFirstNumber(getFirstNumber().negate());
                if (getResult() != null) {
                    setCalculatorResult(getResult().negate());
                }
            } else {
                if (canBackspace) {
                    setCalculatorSecondNumber(getDisplayNumber().negate());
                } else {
                    if (getSecondNumber() == null) {
                        setCalculatorSecondNumber(getFirstNumber().negate());
                    } else {
                        setCalculatorSecondNumber(getSecondNumber().negate());
                    }
                    calculator.getHistory().deleteLast();
                    calculator.getHistory().addNumber(getSecondNumber().negate());
                }
            }
        }

        addNegateOperationHistory();
    }

    private void addNegateOperationHistory () {
        if (memoryPressed) {
            if (!equalWasPress) {
                calculator.getHistory().addNumber(memory.memoryRecall());
            }
                calculator.getHistory().addOperation(OperationsEnum.NEGATE);
        } else {
            if (equalWasPress) {
                calculator.getHistory().addOperation(OperationsEnum.NEGATE);
            } else {
                if (binaryOperation == null) {
                    if (getResult() != null) {
                        calculator.getHistory().addOperation(OperationsEnum.NEGATE);
                    }
                } else {
                    if (!canBackspace) {
                        calculator.getHistory().addOperation(OperationsEnum.NEGATE);
                    }
                }
            }
        }
    }

    /*
     * This method sets first number in calculator object and adds it to history
     * after binary or unary operation buttons was pressed
     */
    private void setBinaryFirstNumber () {
        if (calculator.getNumberFirst() == null) {
            setCalculatorFirstNumber(getDisplayNumber());
        } else {
            if (historySize() == 0) {
                calculator.getHistory().addNumber(calculator.getNumberFirst());
            }
        }
    }


    /*
     * This method sets second number in calculator object, adds it to history and adds first number to history
     * after binary or unary operation buttons was pressed
     */
    private void setBinarySecondNumber () {
        if (!canChangeOperator) {
            if (equalWasPress) {
                if (!canBackspace) {
                    setCalculatorSecondNumber(getFirstNumber());
                } else {
                    setCalculatorSecondNumber(getDisplayNumber());
                }
            } else {
                if (!memoryPressed && canBackspace) {
                    setCalculatorSecondNumber(getDisplayNumber());
                }
            }
        }
    }

    /*
     * This method sets first and second numbers in calculator object and adds it to history
     * after binary or unary operation buttons was pressed
     */
    private void setNumbersBinary () {
            if (!percentPressed) {
                if (binaryOperation == null) {
                    setBinaryFirstNumber();
                    setCalculatorSecondNumber(null);
                } else {
                    setBinarySecondNumber();
                }
            }

        percentPressed = false;
        calculator.setPercentOperation(null);
        memoryPressed = false;
    }

    /*
    This method sets first or second number in calculator object for calculate unary operation
     */
    private BigDecimal setNumberUnary () {
        BigDecimal unaryNumber;

        if (binaryOperation == null) {
            if (calculator.getNumberFirst() == null) {
                setCalculatorFirstNumber(getDisplayNumber());
            }
            setCalculatorSecondNumber(null);
            if (negatePressed || equalWasPress) {
                equalWasPress = false;
                calculator.getHistory().addNumber(getFirstNumber());
            }
            unaryNumber = getFirstNumber();
        } else {
            if (canBackspace) {
                setCalculatorSecondNumber(getDisplayNumber());
            } else {
                if (calculator.getResult() != null && !negatePressed) {
                    setCalculatorSecondNumber(calculator.getResult());
                }
            }
            unaryNumber = calculator.getNumberSecond();
        }
        return unaryNumber;
    }

    /*
     * This method sets result number in calculator object
     */
    private void setCalculatorResult (BigDecimal number) {
        calculator.setResult(number);
    }

    /*
     * This method sets first number in calculator object
     */
    private void setCalculatorFirstNumber (BigDecimal number) {
        calculator.setNumberFirst(number);
    }

    /*
     * This method sets second number in calculator object
     */
    private void setCalculatorSecondNumber (BigDecimal number) {
        calculator.setNumberSecond(number);
    }

    /*
     * Method sets number in calculator object for calculate percent,
     * and deletes last number if percent was pressed several time
     */
    private void setPercentNumber () {
        if (binaryOperation != null) {
            if (!equalWasPress) {
                if (getSecondNumber() == null) {
                    if (canBackspace) {
                        setCalculatorSecondNumber(getDisplayNumber());
                    } else {
                        if (getFirstNumber() != null) {
                            setCalculatorSecondNumber(getFirstNumber());
                        } else {
                            setCalculatorSecondNumber(getDisplayNumber());
                        }
                    }
                }
                calculator.setPercent(getSecondNumber());
            } else {
                calculator.setPercent(getResult());
                setCalculatorSecondNumber(getFirstNumber());
            }

        } else {
            if (getFirstNumber() == null && getResult() == null) {
                setCalculatorFirstNumber(getDisplayNumber());
            }
        }
    }

    //endregion

    //region Get

    /*
     * This method gets result number from calculator object
     */
    private BigDecimal getResult () {
        return calculator.getResult();
    }

    /*
     * This method gets first number from calculator object
     */
    private BigDecimal getFirstNumber () {
        return calculator.getNumberFirst();
    }

    /*
     * This method gets second number from calculator object
     */
    private BigDecimal getSecondNumber () {
        return calculator.getNumberSecond();
    }

    /*
     * This method gets parse number from general display
     */
    private BigDecimal getDisplayNumber () {
        try {
            return FormatterNumber.parseNumber(generalDisplay.getText());
        } catch (ParseException e) {
            printError(e);
        }
        return null;
    }

    /*
     * This method gets number from memory after memory recall button was pressed
     */
    private void getMemoryNumber (BigDecimal numberFromMemory) {
        if (binaryOperation != null) {
            setCalculatorSecondNumber(numberFromMemory);
        } else {
            setCalculatorFirstNumber(numberFromMemory);
            canBackspace = false;
        }
    }


    //endregion

    //region Operations pressed

    @FXML
    public void binaryOperation (ActionEvent actionEvent) {
        String buttonID = ((Button) actionEvent.getSource()).getId();
        historyUnaryOperations = emptyString;
        negateHistory = emptyString;

        if (equalWasPress) {
            binaryOperation = null;
            equalWasPress = false;
        }

        setNumbersBinary();
        if (unaryOperation != null) {
            calculator.setOperation(binaryOperation);
            unaryOperation = null;
        }

        if (binaryOperation != null) {
            calculate();
            calculator.setOperation(binaryOperation);
        }
        binaryOperation = operation.get(buttonID);
        calculator.setOperation(binaryOperation);

        canChangeOperator = true;
        canBackspace = false;
        pointInText = false;

        scrollOutOperationMemory();
        printHistory();
    }


    ArrayList historyArrayG = new ArrayList();

    private void printHistory () {
        historyArrayG = calculator.getHistory().getListHistory();

        if (!calculator.getHistory().getListHistory().isEmpty()) {
            Object o = calculator.getHistory().getLast();
            int index = calculator.getHistory().getListHistory().size() - 1;

            if (o instanceof OperationsEnum) {
                OperationsEnum operation = (OperationsEnum) o;
                if (isBinary(operation)) {
                    try {
                        changeBinaryOperationHistory(operation, index);
                    } catch (ParseException e) {
                        printError(e);
                    }
                }
                if (isUnary(operation)) {
                    changeUnaryOperationHistory(operation, index);

                }
                if (operation.equals(OperationsEnum.PERCENT)) {
                    changePercentOperationHistory();
                }

                if (operation.equals(OperationsEnum.NEGATE)) {
                    changeNegateOperationHistory(operation, index);
                    negatePressed = false;
                }
            }
            if (isNumber(o.toString())) {
                if (!negatePressed) {
                    try {
                        changeNumberHistory(o, index);
                    } catch (ParseException e) {
                        printError(e);
                    }
                } else {
                    changeNegateNumberHistory(index);

                }
            }

            outOperationMemory.setText(calculator.getHistory().getStringHistory());
        }
    }

    private void changeNegateNumberHistory (int index) {
        boolean nextNegate = false;
        if (historySize() >= 1) {
            for (int i = historySize() - 1; i >= 0; i--) {
                Object prePreviousObject = getHistory(i);
                if (!operationSymbols.containsValue(prePreviousObject.toString())) {
                    deleteLastHistory();
                } else {
                    break;
                }
            }
        }
    }

    private void changeNegateOperationHistory (OperationsEnum operation, int index) {
        if (historySize() > 1) {
            int previousIndex = index - 1;
            Object previousObject = getHistory(previousIndex);
            deleteLastHistory();
            deleteLastHistory();

            if (negateHistory.isEmpty()) {
                if (historyUnaryOperations.isEmpty()) {
                    if (isNumber(previousObject.toString())) {
                        negateHistory = formatterNumberHistory(previousObject.toString());
                    } else {
                        negateHistory = previousObject.toString();
                    }
                } else {
                    negateHistory = historyUnaryOperations;
                }
            }

            negateHistory = operationSymbols.get(operation) + "(" + negateHistory + ")";
            addLastHistory(negateHistory);
        }
        if (historySize() > 1) {
            for (int i = historySize() - 2; i >= 0; i--) {
                Object prePreviousObject = getHistory(i);
                if (!operationSymbols.containsValue(prePreviousObject.toString())) {
                    deleteHistory(i);
                } else {
                    break;
                }
            }
        }
    }

    private void changePercentOperationHistory () {
        deleteLastHistory();
        if (historySize() >= 1) {
            for (int i = historySize() - 1; i >= 0; i--) {
                Object prePreviousObject = getHistory(i);
                if (!operationSymbols.containsValue(prePreviousObject.toString())) {
                    deleteLastHistory();
                } else {
                    break;
                }
            }
        }

        addLastHistory(formatterNumberHistory(getResult().toString()));
    }

    private boolean isNumber (String text) {
        try {
            BigDecimal number;
            if (text.contains("e")) {
                number = FormatterNumber.parseNumber(text);
            } else {
                number = new BigDecimal(text);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void changeUnaryOperationHistory (OperationsEnum operation, int index) {
        int previousIndex = index - 1;
        Object previousObject = getHistory(previousIndex);

        deleteLastHistory();
        deleteLastHistory();

        if (historySize() >= 1) {
            for (int i = historySize() - 1; i >= 0; i--) {
                Object prePreviousObject = getHistory(i);
                if (!operationSymbols.containsValue(prePreviousObject.toString())) {
                    deleteLastHistory();
                } else {
                    break;
                }
            }
        }
        if (negateHistory.isEmpty()) {
            if (historyUnaryOperations.isEmpty()) {
                if (isNumber(previousObject.toString())) {
                    historyUnaryOperations = formatterNumberHistory(previousObject.toString());
                }
            }
        } else {
            historyUnaryOperations = negateHistory;
            negateHistory = emptyString;
        }


        historyUnaryOperations = operationSymbols.get(operation) + "(" + historyUnaryOperations + ")";
        addLastHistory(historyUnaryOperations);
    }

    private Object getHistory (int index) {
        return calculator.getHistory().getListHistory().get(index);
    }

    private boolean isUnary (OperationsEnum operationsEnum) {
        return operationsEnum.equals(OperationsEnum.SQRT) || operationsEnum.equals(OperationsEnum.SQR) ||
                operationsEnum.equals(OperationsEnum.ONE_DIVIDE_X);
    }

    private void changeBinaryOperationHistory (OperationsEnum operationsEnum, int index) throws ParseException {
        deleteLastHistory();
        if (historySize() >= 1) {
            int previousIndex = index - 1;
            Object previousObject = calculator.getHistory().getListHistory().get(previousIndex);
            for (int i = historySize() - 1; i >= 0; i--) {
                Object prePreviousObject = getHistory(i);
                if (operationSymbols.containsValue(prePreviousObject.toString())) {
                    deleteLastHistory();
                } else {
                    break;
                }
            }
            if (historyUnaryOperations.isEmpty()) {
                if (isNumber(previousObject.toString())) {
                    deleteLastHistory();
                    addLastHistory(formatterNumberHistory(previousObject.toString()));
                }
            } else {
                historyUnaryOperations = emptyString;
            }
        }

        addLastHistory(operationSymbols.get(operationsEnum));
    }

    private int historySize () {
        return calculator.getHistory().getListHistory().size();
    }

    private void addLastHistory (String s) {
        calculator.getHistory().addOperationString(s);
    }

    private void deleteLastHistory () {
        calculator.getHistory().deleteLast();
    }

    private void deleteHistory (int index) {
        calculator.getHistory().getListHistory().remove(index);
    }

    private void addHistory (int index, Object object) {
        calculator.getHistory().getListHistory().add(index, object);
    }

    private void changeNumberHistory (Object object, int index) throws ParseException {
        int previousIndex = index - 1;
        Object previousObject = calculator.getHistory().getListHistory().get(previousIndex);

        if (calculator.getHistory().getListHistory().size() > index) {
            if (previousObject.equals(OperationsEnum.PERCENT)) {
                changePercentOperationHistory();
            }
        } else {
            deleteHistory(index);
            addHistory(index, object.toString());
        }
    }


    private boolean isBinary (OperationsEnum operationsEnum) {
        return operationsEnum.equals(OperationsEnum.ADD) || operationsEnum.equals(OperationsEnum.SUBTRACT) ||
                operationsEnum.equals(OperationsEnum.MULTIPLY) || operationsEnum.equals(OperationsEnum.DIVIDE);
    }


    @FXML
    public void unaryOperations (ActionEvent actionEvent) {
        String buttonID = ((Button) actionEvent.getSource()).getId();

        if (equalWasPress) {
            binaryOperation = null;
            historyUnaryOperations = emptyString;
        }


        unaryOperation = operation.get(buttonID);
        setNumberUnary();
        calculator.setOperation(unaryOperation);

        calculate();
        printHistory();
        scrollOutOperationMemory();
    }

    @FXML
    public void percentOperation (ActionEvent actionEvent) {
        String buttonID = ((Button) actionEvent.getSource()).getId();
        negatePressed = false;
        percentPressed = true;


        setPercentNumber();
        calculator.setPercentOperation(operation.get(buttonID));
        calculate();
        printResult(formatterNumber(getResult()));

        calculator.setPercent(null);
        printHistory();
    }

    private void setOperation (OperationsEnum operationsEnum) {
        calculator.setOperation(operationsEnum);
    }


    @FXML
    void pressedEqual () {
        clearError();
        if (percentPressed) {
            percentPressed = false;
            calculator.setPercentOperation(null);
            calculator.setPercent(null);
        }
        equalWasPress = true;
        historyUnaryOperations = emptyString;

        if (binaryOperation != null) {
            calculator.setOperation(binaryOperation);
            canChangeOperator = false;
            if (getSecondNumber() == null) {
                setBinarySecondNumber();
            }

            calculate();

        }

        if (!isError) {
            clearHistory();
        }
        negatePressed = false;
        canBackspace = false;
        memoryPressed = false;

        scrollOutOperationMemory();
    }

    private void clearHistory () {
        calculator.clearHistory();
        outOperationMemory.setText(emptyString);
        negateHistory = emptyString;
        historyUnaryOperations = emptyString;
    }


    //endregion

    //region Calculate
    /*
     * Method calculates binary operation,
     * calls print result method,
     * catches exception and
     * prohibits to backspace text in general display
     */
    private void calculate () {
        try {
            calculator.calculate();

            if (getResult() != null) {
                printResult(FormatterNumber.numberFormatter(getResult()));
                canBackspace = false;
            }
            if (!equalWasPress && !percentPressed && unaryOperation == null) {
                calculator.setNumberSecond(null);
            }

        } catch (Exception e) {
            printError(e);
        }
    }

    //endregion

    //region Print

    /*
     * Method outputs exception's message on general display
     */
    private void printError (Exception e) {
        isError = true;
        operationsIsDisable(true);
        memoryPanel.setDisable(true);
        generalDisplay.setStyle(firstStyleLabel);

        generalDisplay.setText(e.getMessage());

        resizeOutputText();
        scrollOutOperationMemory();
    }

    /*
     * Method outputs calculation's result on general display
     */
    private void printResult (String text) {
        try {
            isOverflow(FormatterNumber.parseNumber(text));
            generalDisplay.setText(text);
            resizeOutputText();
        } catch (OverflowException | ParseException e) {
            printError(e);
        }
    }

    /*
     * Method formatters number for print to general display
     */
    private String formatterNumber (BigDecimal number) {
        return FormatterNumber.numberFormatter(number);
    }

    /*
     * Method checked number before prints to general display.
     * If number is more than max number or less min number throws OverflowException
     */
    private void isOverflow (BigDecimal result) throws OverflowException {
        boolean overflow = false;
        BigDecimal number = null;
        try {
            number = FormatterNumber.parseNumber(FormatterNumber.numberFormatter(result));
        } catch (ParseException e) {
            printError(e);
        }

        if (number.abs().compareTo(BigDecimal.ONE) > 0) {
            overflow = number.abs().compareTo(MAX_NUMBER) >= 0;
        } else if (number.abs().compareTo(BigDecimal.ONE) < 0 && number.compareTo(BigDecimal.ZERO) != 0) {
            overflow = number.abs().compareTo(MIN_NUMBER) <= 0;
        }

        if (overflow) {
            throw new OverflowException("Overflow");
        }
    }
    //endregion

    //region ChangeHistory

    /*
     * Method formatters number for print to history operation
     */
    private String formatterNumberHistory (String text) {
        BigDecimal number = null;

        if (text.contains("e")) {
            try {
                number = FormatterNumber.parseNumber(text);
            } catch (ParseException e) {
                printError(e);
            }
        } else {
            number = new BigDecimal(text.replace(",", "."));
        }

        return Text.deleteNumberSeparator(formatterNumber(number), separatorNumber);
    }


    //endregion

    //region Scroll ChangeHistory
    @FXML
    void scrollButtonLeftPressed () {
        scrollPaneOperation.setHvalue(scrollPaneOperation.getHvalue() + moveScroll);
        scrollButtonRight.setVisible(true);
        if (scrollPaneOperation.getHvalue() == scrollPaneOperation.getHmax()) {
            scrollButtonLeft.setVisible(false);
            scrollButtonRight.setVisible(true);
        }
    }

    @FXML
    void scrollButtonRightPressed () {
        scrollPaneOperation.setHvalue(scrollPaneOperation.getHvalue() - moveScroll);
        scrollButtonLeft.setVisible(true);
        if (scrollPaneOperation.getHvalue() == 0) {
            scrollButtonRight.setVisible(false);
            scrollButtonLeft.setVisible(true);
        }
    }

    /*
     * Method scrolls history, if text width more then width history area
     */
    private void scrollOutOperationMemory () {
        moveScroll = ResizeDisplay.scrollText(scrollPaneOperation, outOperationMemory.getText(), scrollButtonLeft, scrollButtonRight);
    }
    //endregion

    //region Clear
    @FXML
    void clearAllC () {
        generalDisplay.setStyle(firstStyleLabel);
        generalDisplay.setText(defaultText);

        outOperationMemory.setText("");
        binaryOperation = null;
        calculator.clearCalculator();
        calculator.setPercentOperation(null);
        historyArrayG.clear();
        historyUnaryOperations = emptyString;
        pointInText = false;
        canChangeOperator = false;
        scrollButtonRight.setVisible(false);
        scrollButtonLeft.setVisible(false);
        negateHistory = emptyString;
        clearHistory();
        historyUnaryOperations = emptyString;
        unaryOperation = null;
        equalWasPress = false;
        operationsIsDisable(false);
        memoryPanel.setDisable(false);
        isError = false;
        negatePressed = false;
        negateHistory = emptyString;
        percentPressed = false;
        charValidInText = CHAR_MAX_INPUT;
        resizeOutputText();
        canBackspace = true;
    }

    @FXML
    void clearNumberCE () {
        generalDisplay.setStyle(firstStyleLabel);
        generalDisplay.setText(defaultText);
        pointInText = false;
        charValidInText = CHAR_MAX_INPUT;
        clearError();
        resizeOutputText();
    }

    /*
     * Methods sets default value for some variable for cleans general display
     * This method calls if calculator's number button was pressed,
     * result of calculation was printed,
     */
    private String clearDisplay (String out) {
        if (!canBackspace || memoryPressed || canChangeOperator) {
            memoryPressed = false;
            canBackspace = true;
            charValidInText = CHAR_MAX_INPUT;
            out = emptyString;
            pointInText = false;
        }
        if (binaryOperation == null || equalWasPress) {
            clearHistory();
            clearBinary();

            equalWasPress = false;
            memoryPressed = false;
        }
        return out;
    }

    private void clearBinary () {
        binaryOperation = null;
        calculator.setOperation(null);
        calculator.setNumberFirst(null);
        calculator.setResult(null);
    }

    /*
     * Method imitates press C button,
     * if exception massage was printed and some button was pressed
     */
    private void clearError () {
        if (isError) {
            C.fire();
        }
    }


    //endregion

    //region Memory

    @FXML
    void memoryStore () {
        if (memory == null) {
            memory = new Memory();
        }
        memory.setNumber(setMemoryNumber());

        memoryButtonDisable(false);
        memoryPressed = true;
    }

    @FXML
    void memoryAdd () {
        if (memory == null) {
            memory = new Memory();
            memoryButtonDisable(false);
        }
        memory.memoryAdd(setMemoryNumber());

        memoryPressed = true;
    }

    @FXML
    void memoryClear () {
        memory.memoryClear();
        memory = null;
        memoryButtonDisable(true);
    }

    @FXML
    void memoryRecall () {
        if (memory != null) {
            getMemoryNumber(memory.memoryRecall());
            printResult(formatterNumber(memory.memoryRecall()));
        }
        memoryPressed = true;
    }

    @FXML
    void memorySubtract () {
        if (memory == null) {
            memory = new Memory();
            memoryButtonDisable(false);
        }
        memory.memorySubtract(setMemoryNumber());

        memoryPressed = true;
    }

    /*
     * Method makes memory clear and memory recall buttons disable or not disable
     */
    private void memoryButtonDisable (boolean disable) {
        memoryClear.setDisable(disable);
        memoryRecall.setDisable(disable);
    }

    /*
     * Method sets number in memory object
     */
    private BigDecimal setMemoryNumber () {
        BigDecimal number;

        if (getFirstNumber() != null) {
            number = getFirstNumber();
        } else {
            number = getDisplayNumber();
        }
        return number;
    }

    //endregion

    //region Window
    @FXML
    public void closeWindow () {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void maximizeWindow () {
        Stage stage = (Stage) maximizeButton.getScene().getWindow();
        ResizeWindow resizeWindow = new ResizeWindow(stage);
        resizeWindow.maximizeWindow(maximizeButton);
        resizeWindow.resizeButton(calculatorButtons);

        resizeOutputText();
        scrollOutOperationMemory();
    }

    @FXML
    public void hideWindow () {
        Stage stage = (Stage) hideButton.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    void draggedWindow (MouseEvent event) {
        Stage stage = (Stage) title.getScene().getWindow();
        stage.setX(event.getScreenX() - xOffset);
        stage.setY(event.getScreenY() - yOffset);
    }

    @FXML
    void clickWindow (MouseEvent event) {
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }


    //endregion

    //region Resize

    @FXML
    void resizeWindow () {
        Stage stage = (Stage) generalAnchorPane.getScene().getWindow();
        ResizeWindow resize = new ResizeWindow(stage);
        resize.resizeAllStage();
        scrollOutOperationMemory();
    }

    /*
     * Method resize text for general display and sets one
     */
    private void resizeOutputText () {
        generalDisplay.setFont(ResizeDisplay.fontSize(generalDisplay));
    }

    //endregion

}