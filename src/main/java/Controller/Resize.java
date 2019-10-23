package Controller;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Resize {
    private Stage stage;
    private Scene scene;
    private double minWidth;
    private double minHeight;
    private Cursor cursorEvent = Cursor.DEFAULT;


    Resize(Stage stage) {
        this.stage = stage;
        this.minWidth = stage.getMinWidth();
        this.minHeight = stage.getMinHeight();
        this.scene = stage.getScene();
    }

    public void resizeAllStage() {
        EventHandler<MouseEvent> event = resizeWindow();

        stage.getScene().addEventHandler(MouseEvent.MOUSE_MOVED, event);
        stage.getScene().addEventHandler(MouseEvent.MOUSE_PRESSED, event);
        stage.getScene().addEventHandler(MouseEvent.MOUSE_DRAGGED, event);
        stage.getScene().addEventHandler(MouseEvent.MOUSE_EXITED, event);
        stage.getScene().addEventHandler(MouseEvent.MOUSE_EXITED_TARGET, event);

        ObservableList<Node> children = stage.getScene().getRoot().getChildrenUnmodifiable();
        for (Node child : children) {
            addListenerDeeply(child, event);
        }

    }

    private void addListenerDeeply(Node node, EventHandler<MouseEvent> listener) {
        node.addEventHandler(MouseEvent.MOUSE_MOVED, listener);
        node.addEventHandler(MouseEvent.MOUSE_PRESSED, listener);
        node.addEventHandler(MouseEvent.MOUSE_DRAGGED, listener);
        node.addEventHandler(MouseEvent.MOUSE_EXITED, listener);
        node.addEventHandler(MouseEvent.MOUSE_EXITED_TARGET, listener);
        if (node instanceof Parent) {
            Parent parent = (Parent) node;
            ObservableList<Node> children = parent.getChildrenUnmodifiable();
            for (Node child : children) {
                addListenerDeeply(child, listener);
            }
        }
    }

    private EventHandler<MouseEvent> resizeWindow() {
        EventHandler<MouseEvent> event = mouseEvent -> {
            EventType<? extends MouseEvent> mouseEventType = mouseEvent.getEventType();

            int border = 4;
            double mouseWinX = mouseEvent.getSceneX();
            double mouseWinY = mouseEvent.getSceneY();
            double sceneWidth = scene.getWidth();
            double sceneHeight = scene.getHeight();
            Screen window = Screen.getPrimary();

            if (MouseEvent.MOUSE_MOVED.equals(mouseEventType)) {
                if (mouseWinX < border && mouseWinY < border) {
                    cursorEvent = Cursor.NW_RESIZE;
                } else if (mouseWinX < border && mouseWinY > sceneHeight - border) {
                    cursorEvent = Cursor.SW_RESIZE;
                } else if (mouseWinX > sceneWidth - border && mouseWinY < border) {
                    cursorEvent = Cursor.NE_RESIZE;
                } else if (mouseWinX > sceneWidth - border && mouseWinY > sceneHeight - border) {
                    cursorEvent = Cursor.SE_RESIZE;
                } else if (mouseWinX < border) {
                    cursorEvent = Cursor.W_RESIZE;
                } else if (mouseWinX > sceneWidth - border) {
                    cursorEvent = Cursor.E_RESIZE;
                } else if (mouseWinY < border) {
                    cursorEvent = Cursor.N_RESIZE;
                } else if (mouseWinY > sceneHeight - border) {
                    cursorEvent = Cursor.S_RESIZE;
                } else {
                    cursorEvent = Cursor.DEFAULT;
                }
                scene.setCursor(cursorEvent);
            } else if (MouseEvent.MOUSE_DRAGGED.equals(mouseEventType)) {
                if (cursorEvent.equals(Cursor.N_RESIZE) || cursorEvent.equals(Cursor.NE_RESIZE) || cursorEvent.equals(Cursor.NW_RESIZE)) {
                    if (mouseEvent.getScreenY() == 0) {
                        stage.setHeight(window.getBounds().getHeight());
                        stage.setY(0);
                    } else {
                        if (mouseEvent.getScreenY() > 0 && stage.getHeight() == window.getBounds().getHeight()) {
                            stage.setHeight(stage.getHeight()-mouseEvent.getScreenY());
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

    private void changeXPosition(MouseEvent mouseEvent) {
        double newWidth = stage.getX() - mouseEvent.getScreenX() + stage.getWidth();
        if (newWidth > stage.getMinWidth()) {
            stage.setWidth(newWidth);
            stage.setX(mouseEvent.getScreenX());
        }
    }

    private void changeYPosition(MouseEvent mouseEvent) {
        double newHeight = stage.getY() - mouseEvent.getScreenY() + stage.getHeight();
        if (newHeight > minHeight) {
            stage.setHeight(newHeight);
            stage.setY(mouseEvent.getScreenY());
        }
    }


}
