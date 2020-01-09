module Calculator {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.base;
    requires java.logging;

    opens CalculatorApplication.Controller;
    opens CalculatorApplication.View;
}