package Controller;

import static org.junit.jupiter.api.Assertions.assertEquals;


class Calculator_ControllerMouseTest extends Calculator_ControllerTest {
    @Override
    void assertNumber(String result, String buttonsPressed, String outOperationMemoryResult) {
        checkMouseInputNumber(result, buttonsPressed, outOperationMemoryResult);
    }

}