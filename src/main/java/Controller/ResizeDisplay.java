package Controller;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ResizeDisplay {
    private static final double MAX_FONT_SIZE_MAX_WINDOW = 71;
    private static final double MAX_FONT_SIZE_MIN_WINDOW = 46;
    private static final String DEFAULT_FONT_NAME = "Segoe UI Semibold";
    private static Stage stage;

    public static Font fontSize(Label label){
        setStage(label);
        Text textNew = new Text(label.getText());
        double widthMaxTextOutput = label.getWidth() - label.getPadding().getRight() - label.getPadding().getLeft();
        textNew.setFont(label.getFont());
        double textWidthNew = textNew.getBoundsInLocal().getWidth();
        double presentSize = label.getFont().getSize();
        double percentOfChange;
        double newSize;

        percentOfChange = widthMaxTextOutput / textWidthNew;
        newSize = presentSize * percentOfChange;

        if (newSize > MAX_FONT_SIZE_MIN_WINDOW && !stage.isMaximized()) {
            newSize = MAX_FONT_SIZE_MIN_WINDOW;
        }
        if (stage.isMaximized()) {
            newSize = MAX_FONT_SIZE_MAX_WINDOW;
        }
        return new Font(DEFAULT_FONT_NAME, newSize);
    }

    private static void setStage (Node node) {
        if (stage == null) {
            stage = (Stage) node.getScene().getWindow();
        }
    }

    public static double scrollText(ScrollPane scrollPane, String text, Button scrollButtonLeft, Button scrollButtonRight){
        setStage(scrollPane);
        Text history = new Text(text);

        double moveScroll = 0;
        double maxWidthForLabelOperation = scrollPane.getWidth() - scrollPane.getPadding().getLeft() - scrollPane.getPadding().getRight();
        if (history.getBoundsInLocal().getWidth() > maxWidthForLabelOperation) {
            scrollButtonLeft.setVisible(true);
            double p = history.getBoundsInLocal().getWidth() / maxWidthForLabelOperation;
            int temp = (int) p;
            moveScroll = scrollPane.getHmax() / temp;
        } else {
            scrollButtonLeft.setVisible(false);
            scrollButtonRight.setVisible(false);
        }
        return moveScroll;
    }
}
