package View;

import Controller.Calculator_Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.*;

public class Calculator_ViewTest extends ApplicationTest {
    private Parent root;
    private Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        root = FXMLLoader.load(Calculator_Main.class.getResource("/View/calculator_view.fxml"));
        stage.setScene(new Scene(root));
        this.stage = stage;
        stage.show();
        stage.toFront();
    }

    @BeforeAll
    static void config() throws Exception {
        System.setProperty("testfx.robot", "awt");
//
//
//        System.setProperty("testfx.robot", "glass");
//        System.setProperty("testfx.headless", "true");
//        System.setProperty("prism.order", "d3d");
//        System.setProperty("prism.text", "t2k");
//        System.setProperty("java.awt.headless", "true");
    }

    @Test
    void checkMaximize() {
        clickOn(root.lookup("#maximizeButton"));
        assertTrue(stage.isMaximized());

        clickOn(root.lookup("#maximizeButton"));
        assertFalse(stage.isMaximized());
    }

    @Test
    void checkHide() {
        clickOn(root.lookup("#hideButton"));
        assertTrue(stage.isIconified());
    }

    @Test
    void checkClose() {
        clickOn(root.lookup("#cancel"));
        assertFalse(stage.isShowing());
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

    }


    @Test
    void checkResize() {
        assertResize(640, 120, 500, 60, 460, 560, Cursor.NW_RESIZE);
        assertResize(959, 121, 1300, 60, 660, 560, Cursor.NE_RESIZE);
        assertResize(641, 619, 500, 800, 459, 679, Cursor.SW_RESIZE);
        assertResize(958, 618, 1300, 800, 659, 679, Cursor.SE_RESIZE);



        assertResize(700, 119, 700, 60, 320, 560, Cursor.N_RESIZE);
        assertResize(701, 619, 700, 800, 320, 680, Cursor.S_RESIZE);
        assertResize(640, 180, 500, 180, 460, 499, Cursor.W_RESIZE);
        assertResize(959, 180, 1300, 180, 660, 499, Cursor.E_RESIZE);
    }

    @Test
    void checkButtonColor(){

    }
    void assertCursor(double x, double y, Cursor cursorExpected) {

        if (cursorExpected.equals(Cursor.NE_RESIZE) || cursorExpected.equals(Cursor.SW_RESIZE)
                || cursorExpected.equals(Cursor.E_RESIZE) || cursorExpected.equals(Cursor.W_RESIZE)) {
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

        moveTo(xStart, yStart);
        drop();
    }

}
