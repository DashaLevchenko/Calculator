package Controller;


class Calculator_ControllerKeyboardTest extends CalculatorControllerTest {
    @Override
    void assertNumber(String result, String buttonsPressed, String outOperationMemoryResult) {
        checkKeyInputNumber(result, buttonsPressed, outOperationMemoryResult);
    }

}