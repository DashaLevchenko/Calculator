package Controller;


class Calculator_ControllerMouseTest extends Calculator_ControllerTest {
    @Override
    void assertNumber(String result, String buttonsPressed, String outOperationMemoryResult) {
        checkMouseInputNumber(result, buttonsPressed, outOperationMemoryResult);
    }

}