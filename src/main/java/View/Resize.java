package View;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class Resize {
//    public static void addResizeListener(Stage stage, double minWidth, double minHeight, double maxWidth, double maxHeight) {
    public static void addResizeListener(Stage stage) {
       EventHandler <MouseEvent> event = new EventHandler<MouseEvent>() {
           @Override
           public void handle(MouseEvent mouseEvent) {
               EventType<? extends MouseEvent> mouseEventType = mouseEvent.getEventType();
               Scene scene = stage.getScene();
                Cursor cursorEvent = Cursor.DEFAULT;
                int border = 4;
               double mouseEventX = mouseEvent.getSceneX(),
                       mouseEventY = mouseEvent.getSceneY(),
                       sceneWidth = scene.getWidth(),
                       sceneHeight = scene.getHeight();

               if (MouseEvent.MOUSE_MOVED.equals(mouseEventType)) {
                   if (mouseEventX < border && mouseEventY < border) {
                       cursorEvent = Cursor.NW_RESIZE;
                   } else if (mouseEventX < border && mouseEventY > sceneHeight - border) {
                       cursorEvent = Cursor.SW_RESIZE;
                   } else if (mouseEventX > sceneWidth - border && mouseEventY < border) {
                       cursorEvent = Cursor.NE_RESIZE;
                   } else if (mouseEventX > sceneWidth - border && mouseEventY > sceneHeight - border) {
                       cursorEvent = Cursor.SE_RESIZE;
                   } else if (mouseEventX < border) {
                       cursorEvent = Cursor.W_RESIZE;
                   } else if (mouseEventX > sceneWidth - border) {
                       cursorEvent = Cursor.E_RESIZE;
                   } else if (mouseEventY < border) {
                       cursorEvent = Cursor.N_RESIZE;
                   } else if (mouseEventY > sceneHeight - border) {
                       cursorEvent = Cursor.S_RESIZE;
                   } else {
                       cursorEvent = Cursor.DEFAULT;
                   }
                   scene.setCursor(cursorEvent);
               }
           }
       };
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

    private static void addListenerDeeply(Node node, EventHandler<MouseEvent> listener) {
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

}
