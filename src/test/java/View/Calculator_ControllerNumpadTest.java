package View;

class Calculator_ControllerNumpadTest extends Calculator_ControllerTest {
    @Override
    void assertNumber(String result, String buttonsPressed, String clearButtonPressed) {
        checkNumpadInputNumber(result, buttonsPressed, clearButtonPressed);

    }

}