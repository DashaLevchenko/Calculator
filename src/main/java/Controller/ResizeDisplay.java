package Controller;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Class resizes text
 */
public class ResizeDisplay {

    /**
     * Text size when window was maximized
     */
    private static final double MAX_FONT_SIZE_MAX_WINDOW = 71;

    /**
     * Text size when window wasn't maximized and text doesn't exceed border of label
     */
    private static final double MAX_FONT_SIZE_MIN_WINDOW = 46;

    /**
     * Default font is used for print
     */
    private static final String DEFAULT_FONT_DISPLAY = "Segoe UI Semibold";

    private static Stage stage;

    /**
     * Resizes size text if it exceeds border of label
     * @param label Label keeps text for resize
     * @return Font with size was resized
     */
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
        return new Font(DEFAULT_FONT_DISPLAY, newSize);
    }

    private static void setStage (Node node) {
        if (stage == null) {
            stage = (Stage) node.getScene().getWindow();
        }
    }

    /**
     * Method calculates horizontal scroll position of the ScrollPane
     * @param scrollPane Keeps text need to scroll
     * @param text Text in Scroll pane
     * @param scrollButtonLeft Button for scroll text left
     * @param scrollButtonRight Button for scroll text right
     * @return Horizontal scroll position of the ScrollPane
     */
    public static double scrollText(ScrollPane scrollPane, String text, Button scrollButtonLeft, Button scrollButtonRight){
        setStage(scrollPane);
        Text history = new Text(text);

        double moveScroll = 0;
        double maxWidthForLabelOperation = scrollPane.getWidth() - scrollPane.getPadding().getLeft() - scrollPane.getPadding().getRight();
        if (history.getBoundsInLocal().getWidth() > maxWidthForLabelOperation) {
            scrollButtonLeft.setVisible(true);
            double fullWidth = history.getBoundsInLocal().getWidth() / maxWidthForLabelOperation;
            int temp = (int) fullWidth;
            moveScroll = scrollPane.getHmax() / temp;
        } else {
            scrollButtonLeft.setVisible(false);
            scrollButtonRight.setVisible(false);
        }
        return moveScroll;
    }
}
