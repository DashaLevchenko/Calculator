package Controller;


class Calculator_ControllerKeyboardTest extends Calculator_ControllerTest {
    @Override
    void assertNumber(String result, String buttonsPressed, String outOperationMemoryResult) {
        checkKeyInputNumber(result, buttonsPressed, outOperationMemoryResult);
    }

}