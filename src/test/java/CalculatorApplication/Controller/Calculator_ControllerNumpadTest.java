package CalculatorApplication.Controller;

class Calculator_ControllerNumpadTest extends CalculatorControllerTest {
    @Override
    void assertNumber(String result, String buttonsPressed, String outOperationMemoryResult) {
        checkNumpadInputNumber(result, buttonsPressed, outOperationMemoryResult);
    }

}