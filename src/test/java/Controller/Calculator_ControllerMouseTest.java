package Controller;

import static org.junit.jupiter.api.Assertions.assertEquals;


class Calculator_ControllerMouseTest extends Calculator_ControllerTest {
    @Override
    void assertNumber(String result, String buttonsPressed, String clearButtonPressed) {
        checkMouseInputNumber(result, buttonsPressed, clearButtonPressed);
    }

}