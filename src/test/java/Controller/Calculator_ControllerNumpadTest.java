package Controller;

class Calculator_ControllerNumpadTest extends Calculator_ControllerTest {
    @Override
    void assertNumber(String result, String buttonsPressed, String outOperationMemoryResult) {
        checkNumpadInputNumber(result, buttonsPressed, outOperationMemoryResult);

    }

}