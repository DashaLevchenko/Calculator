module Calculator {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.base;
    requires java.logging;

    opens Controller;
    opens View;
}