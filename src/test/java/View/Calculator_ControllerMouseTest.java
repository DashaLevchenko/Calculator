package View;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static javafx.scene.input.KeyCode.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


class Calculator_ControllerMouseTest extends Calculator_ControllerTest {
    @Override
    void assertNumber(String result, String buttonsPressed, String clearButtonPressed) {
        checkMouseInputNumber(result, buttonsPressed, clearButtonPressed);
    }

}