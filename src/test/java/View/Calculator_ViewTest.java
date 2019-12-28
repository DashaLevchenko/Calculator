package View;

import CalculatorApp.CalculatorMain;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.util.concurrent.TimeoutException;

import static javafx.scene.Cursor.*;
import static org.junit.jupiter.api.Assertions.*;

public class Calculator_ViewTest extends ApplicationTest {
    private Parent root;
    private Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        root = FXMLLoader.load(CalculatorMain.class.getResource("/View/calculator_view.fxml"));
        stage.setScene(new Scene(root));
        this.stage = stage;
        stage.setHeight(500);
        stage.setWidth(320);
        stage.setX(640);
        stage.setY(120);
        stage.show();
        stage.toFront();
        stage.setIconified(false);
    }

    @AfterEach
    void close() throws TimeoutException {
//        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
    }

    @BeforeAll
    static void config() throws Exception {
        System.setProperty("testfx.robot", "awt");
    }


    @Test
    void checkNWResize(){
        assertResize(640, 120, 10, 10, 950, 610, NW_RESIZE);
        assertResize(640, 120, 100, 20, 860, 600, NW_RESIZE);
        assertResize(640, 120, 500, 60, 460, 560, NW_RESIZE);
        assertResize(640, 120, 600, 20, 360, 600, NW_RESIZE);
    }

    @Test
    void checkNEResize(){
        assertResize(959, 121, 1000, 100, 360, 520, NE_RESIZE);
        assertResize(959, 121, 1100, 70, 460, 550, NE_RESIZE);
        assertResize(959, 121, 1200, 60, 560, 560, NE_RESIZE);
        assertResize(959, 121, 1300, 30, 660, 590, NE_RESIZE);

    }

    @Test
    void checkSWResize(){
        assertResize(641, 619, 600, 800, 360, 680, SW_RESIZE);
        assertResize(640, 620, 500, 890, 460, 770, SW_RESIZE);
        assertResize(641, 619, 400, 900, 560, 779, SW_RESIZE);
        assertResize(641, 619, 300, 1000, 660, 779, SW_RESIZE);

    }
    @Test
    void checkSEResize(){
        assertResize(958, 618, 1000, 800, 360, 680, SE_RESIZE);
        assertResize(958, 618, 1200, 890, 560, 770, SE_RESIZE);
        assertResize(958, 618, 1300, 900, 660, 779, SE_RESIZE);
        assertResize(958, 618, 1500, 1000, 860, 779, SE_RESIZE);

    }

    @Test
    void checkNResize(){
        assertResize(700, 120, 700, 100, 320, 520, N_RESIZE);
        assertResize(700, 119, 700, 50, 320, 570, N_RESIZE);
        assertResize(700, 119, 700, 30, 320, 590, N_RESIZE);
        assertResize(700, 119, 700, 0, 320, 900, N_RESIZE);

    }
    @Test
    void checkSResize(){
        assertResize(701, 619, 700, 700, 320, 580, S_RESIZE);
        assertResize(701, 619, 700, 750, 320, 630, S_RESIZE);
        assertResize(701, 619, 700, 890, 320, 770, S_RESIZE);
        assertResize(701, 619, 700, 950, 320, 900, S_RESIZE);
    }

    @Test
    void checkWResize(){
        assertResize(640, 180, 600, 180, 360, 500, W_RESIZE);
        assertResize(640, 180, 500, 180, 460, 500, W_RESIZE);
        assertResize(640, 180, 400, 180, 560, 500, W_RESIZE);
        assertResize(640, 180, 300, 180, 660, 500, W_RESIZE);

    }
    @Test
    void checkEResize(){
        assertResize(959, 180, 1000, 180, 360, 500, E_RESIZE);
        assertResize(959, 180, 1100, 180, 460, 500, E_RESIZE);
        assertResize(959, 180, 1200, 180, 560, 500, E_RESIZE);
        assertResize(959, 180, 1300, 180, 660, 500, E_RESIZE);

    }
    @Test
    void checkShowingButtonMenu() {
        //Show menu
        clickOn(root.lookup("#buttonMenu"));
        sleep(200);
        double xLeftMenu = root.lookup("#leftMenu").getTranslateX() + root.lookup("#leftMenu").getLayoutX();
        double yLeftMenu = root.lookup("#leftMenu").getTranslateY() + root.lookup("#leftMenu").getLayoutY();

        assertFalse(root.lookup("#textStandard").isVisible());
        assertEquals(0, xLeftMenu);
        assertEquals(79, yLeftMenu);

        //Hide menu
        clickOn(root.lookup("#buttonMenu"));
        sleep(200);
        xLeftMenu = root.lookup("#leftMenu").getTranslateX() + root.lookup("#leftMenu").getLayoutX();
        yLeftMenu = root.lookup("#leftMenu").getTranslateY() + root.lookup("#leftMenu").getLayoutY();

        assertTrue(root.lookup("#textStandard").isVisible());
        assertEquals(-264, xLeftMenu);
        assertEquals(79, yLeftMenu);
    }

    @Test
    void checkMaximize() {
        clickOn(root.lookup("#maximizeButton"));
        assertTrue(stage.isMaximized());

        clickOn(root.lookup("#maximizeButton"));
        assertFalse(stage.isMaximized());
    }

    @Test
    void checkClose() {
        clickOn(root.lookup("#cancel"));
        assertFalse(stage.isShowing());
    }

    @Test
    void checkDragWindow() {
        drag(stage.getX(), stage.getY());
        moveTo(120, 0);

        assertEquals(120, stage.getX());
        assertEquals(0, stage.getY());

        moveTo(789, 60);

        assertEquals(789, stage.getX());
        assertEquals(60, stage.getY());

        moveTo(5, 180);

        assertEquals(5, stage.getX());
        assertEquals(180, stage.getY());

        dropTo(640, 120);
    }

    @Test
    void checkHide() {
        clickOn(root.lookup("#hideButton"));
        assertTrue(stage.isIconified());
    }


    void assertCursor(double x, double y, Cursor cursorExpected) {

        if (cursorExpected.equals(NE_RESIZE) || cursorExpected.equals(SW_RESIZE)
                || cursorExpected.equals(E_RESIZE) || cursorExpected.equals(W_RESIZE)) {
            moveTo(x, y).moveTo(x - 2, y + 2).moveTo(x + 1, y - 1).moveTo(x - 1, y + 1);
        } else {
            moveTo(x, y).moveTo(x + 2, y + 2).moveTo(x - 1, y - 1).moveTo(x + 1, y + 1);
        }
        assertEquals(cursorExpected, stage.getScene().getCursor());
    }

    private void assertResize(double xStart, double yStart, double xMove, double yMove, double newWidth, double newHeight, Cursor cursorExpected) {
        assertCursor(xStart, yStart, cursorExpected);
        drag(xStart, yStart);
        moveTo(xMove, yMove);

        assertEquals(newWidth, stage.getWidth());
        assertEquals(newHeight, stage.getHeight());
    }

}
