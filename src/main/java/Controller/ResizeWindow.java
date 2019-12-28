package Controller;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * This class resizes application`s window
 */

class ResizeWindow {
    /** Text for button which was pressed for maximized stage */
    private static final String BUTTON_TEXT_STAGE_MAXIMIZED = "";

    /** Text for button which was pressed for return default size stage */
    private static final String BUTTON_TEXT_STAGE_NOT_MAXIMIZED = "";

    /** Max width of device where application is used */
    private static final double MAX_WIDTH_WINDOWS = 1600;

    /** Font is always used for buttons */
    private static final String DEFAULT_FONT_BUTTON = "Calculator MDL2 Assets";
    private Stage stage;
    private Scene scene;
    private double minWidth;
    private double minHeight;
    private Cursor cursorEvent = Cursor.DEFAULT;

    /**
     * This constructor gets stage, sets min width of stage, sets min height of stage, sets scene of stage.
     *
     * @param stage Stage of application
     */
    ResizeWindow (Stage stage) {
        this.stage = stage;
        this.minWidth = stage.getMinWidth();
        this.minHeight = stage.getMinHeight();
        this.scene = stage.getScene();
    }


    /**
     * This method resizes application's stage
     * and  adds handler mouse event to all stage's children
     */
    void resizeAllStage () {
        EventHandler<MouseEvent> event = resizeWindow();
        setEventHandler(event);
    }

    private void setEventHandler (EventHandler<MouseEvent> event) {
        stage.getScene().addEventHandler(MouseEvent.MOUSE_MOVED, event);
        stage.getScene().addEventHandler(MouseEvent.MOUSE_DRAGGED, event);

        ObservableList<Node> children = stage.getScene().getRoot().getChildrenUnmodifiable();
        for (Node child : children) {
            addListenerDeeply(child, event);
        }
    }

    private void addListenerDeeply (Node node, EventHandler<MouseEvent> listener) {
        node.addEventHandler(MouseEvent.MOUSE_MOVED, listener);
        node.addEventHandler(MouseEvent.MOUSE_DRAGGED, listener);
        if (node instanceof Parent) {
            Parent parent = (Parent) node;
            ObservableList<Node> children = parent.getChildrenUnmodifiable();
            for (Node child : children) {
                addListenerDeeply(child, listener);
            }
        }
    }

    /**
     * This method changes image cursor when mouse was moved on border area.
     * Also this method changes width and height window when mouse was dragged
     * and moved in border area
     */
    private EventHandler<MouseEvent> resizeWindow () {
        EventHandler<MouseEvent> event = mouseEvent -> {
            EventType<? extends MouseEvent> mouseEventType = mouseEvent.getEventType();

            double mouseWinX = mouseEvent.getSceneX();
            double mouseWinY = mouseEvent.getSceneY();
            double sceneWidth = scene.getWidth();
            double sceneHeight = scene.getHeight();
            Screen window = Screen.getPrimary();

            if (MouseEvent.MOUSE_MOVED.equals(mouseEventType)) {
                setCursorEvent(mouseWinX, mouseWinY, sceneWidth, sceneHeight);
                scene.setCursor(cursorEvent);
            } else if (MouseEvent.MOUSE_DRAGGED.equals(mouseEventType)) {
                if (cursorEvent.equals(Cursor.N_RESIZE) || cursorEvent.equals(Cursor.NE_RESIZE) || cursorEvent.equals(Cursor.NW_RESIZE)) {
                    if (mouseEvent.getScreenY() == 0) {
                        stage.setHeight(window.getBounds().getHeight());
                        stage.setY(0);
                    } else {
                        if (mouseEvent.getScreenY() > 0 && stage.getHeight() == window.getBounds().getHeight()) {
                            stage.setHeight(stage.getHeight() - mouseEvent.getScreenY());
                            stage.setY(mouseEvent.getScreenY());
                        }
                        changeYPosition(mouseEvent);
                        if (cursorEvent.equals(Cursor.NE_RESIZE)) {
                            if (mouseWinX > minWidth) {
                                stage.setWidth(mouseWinX);
                            }
                        } else if (cursorEvent.equals(Cursor.NW_RESIZE)) {
                            changeXPosition(mouseEvent);
                        }
                    }
                } else if (cursorEvent.equals(Cursor.W_RESIZE) || cursorEvent.equals(Cursor.SW_RESIZE)) {
                    changeXPosition(mouseEvent);
                    if (cursorEvent.equals(Cursor.SW_RESIZE)) {
                        if (mouseWinY > minHeight) {
                            stage.setHeight(mouseWinY);
                        }
                    }
                } else if (cursorEvent.equals(Cursor.S_RESIZE)) {
                    if (mouseEvent.getScreenY() == window.getBounds().getHeight() - 1) {
                        stage.setY(0);
                        stage.setHeight(window.getBounds().getHeight());
                    } else if (mouseWinY > minHeight) {
                        stage.setHeight(mouseWinY);
                    }
                } else if (cursorEvent.equals(Cursor.E_RESIZE) || cursorEvent.equals(Cursor.SE_RESIZE)) {
                    if (mouseWinX > minWidth) {
                        stage.setWidth(mouseWinX);
                        if (cursorEvent.equals(Cursor.SE_RESIZE)) {
                            if (mouseWinY > minHeight) {
                                stage.setHeight(mouseWinY);
                            }
                        }
                    }
                }
            }
        };

        return event;
    }

    private void setCursorEvent (double mouseWinX, double mouseWinY, double sceneWidth, double sceneHeight) {
        int minBorder = 4;
        if (mouseWinX < minBorder && mouseWinY < minBorder) {
            cursorEvent = Cursor.NW_RESIZE;
        } else if (mouseWinX < minBorder && mouseWinY > sceneHeight - minBorder) {
            cursorEvent = Cursor.SW_RESIZE;
        } else if (mouseWinX > sceneWidth - minBorder && mouseWinY < minBorder) {
            cursorEvent = Cursor.NE_RESIZE;
        } else if (mouseWinX > sceneWidth - minBorder && mouseWinY > sceneHeight - minBorder) {
            cursorEvent = Cursor.SE_RESIZE;
        } else if (mouseWinX < minBorder) {
            cursorEvent = Cursor.W_RESIZE;
        } else if (mouseWinX > sceneWidth - minBorder) {
            cursorEvent = Cursor.E_RESIZE;
        } else if (mouseWinY < minBorder) {
            cursorEvent = Cursor.N_RESIZE;
        } else if (mouseWinY > sceneHeight - minBorder) {
            cursorEvent = Cursor.S_RESIZE;
        } else {
            cursorEvent = Cursor.DEFAULT;
        }
    }

    private void changeXPosition (MouseEvent mouseEvent) {
        double newWidth = stage.getX() - mouseEvent.getScreenX() + stage.getWidth();
        if (newWidth > stage.getMinWidth()) {
            stage.setWidth(newWidth);
            stage.setX(mouseEvent.getScreenX());
        }
    }

    private void changeYPosition (MouseEvent mouseEvent) {
        double newHeight = stage.getY() - mouseEvent.getScreenY() + stage.getHeight();
        if (newHeight > minHeight) {
            stage.setHeight(newHeight);
            stage.setY(mouseEvent.getScreenY());
        }
    }

    // Method resizes button's font size if width stage was changed
    void resizeButton (Node node) {
        ObservableList<Node> buttons = ((Parent) node).getChildrenUnmodifiable();
        for (Node child : buttons) {
            Button button = (Button) child;
            button.setFont(new Font(DEFAULT_FONT_BUTTON, newSize(button.getFont())));
        }
    }

    private double newSize (Font font) {
        double oldSize = font.getSize();
        double numberForChange = MAX_WIDTH_WINDOWS / stage.getMinWidth();
        double newSize;
        if (stage.isMaximized()) {
            newSize = oldSize + numberForChange;
        } else {
            newSize = oldSize - numberForChange;
        }
        return newSize;
    }

    /*
     * This methods makes stage size on all device window,
     * and change button text
     */
    void maximizeWindow (Button button) {
        String buttonText;
        if (!stage.isMaximized()) {
            stage.setMaximized(true);
            buttonText = BUTTON_TEXT_STAGE_MAXIMIZED;
        } else {
            stage.setMaximized(false);
            buttonText = BUTTON_TEXT_STAGE_NOT_MAXIMIZED;
        }
        button.setText(buttonText);
    }
}
