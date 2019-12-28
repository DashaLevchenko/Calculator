module calculator {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.base;
    requires java.logging;
    requires org.junit.jupiter.api;

    opens Controller;
    opens View;
    opens Model;
    opens CalculatorApp to javafx.fxml;
    exports CalculatorApp;
}