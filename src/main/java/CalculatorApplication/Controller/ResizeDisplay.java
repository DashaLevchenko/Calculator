package CalculatorApplication.Controller;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Class resizes text
 */
class ResizeDisplay {

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


    /**
     * Resizes size text if it exceeds border of label
     *
     * @param label Label keeps text for resize
     * @return Font with size was resized
     */
    Font fontSizeChangedWidth (Label label) {
         Stage stage = (Stage) label.getScene().getWindow();

        String labelText = label.getText();
        Text textNew = new Text(labelText);

        Font labelFont = label.getFont();
        textNew.setFont(labelFont);

        double textWidthNew = textNew.getBoundsInLocal().getWidth();

        Insets padding = label.getPadding();
        double widthMaxTextOutput = label.getWidth() - padding.getRight() - padding.getLeft();

        double presentSize = labelFont.getSize();
        double newSize = presentSize * widthMaxTextOutput / textWidthNew;

        if (newSize > MAX_FONT_SIZE_MIN_WINDOW && !stage.isMaximized()) {
            newSize = MAX_FONT_SIZE_MIN_WINDOW;
        }
        if (stage.isMaximized()) {
            newSize = MAX_FONT_SIZE_MAX_WINDOW;
        }
        return new Font(DEFAULT_FONT_DISPLAY, newSize);
    }


    /**
     * Method calculates horizontal scroll position of the ScrollPane
     *
     * @param scrollPane        Keeps text need to scroll
     * @param text              Text in Scroll pane
     * @param scrollButtonLeft  Button for scroll text left
     * @param scrollButtonRight Button for scroll text right
     * @return Horizontal scroll position of the ScrollPane
     */
    double scrollText (ScrollPane scrollPane, String text, Button scrollButtonLeft, Button scrollButtonRight) {
        Insets padding = scrollPane.getPadding();

        double maxWidthLabelOperation = scrollPane.getWidth() - padding.getLeft() - padding.getRight();
        Text history = new Text(text);
        double historyWidth = history.getBoundsInLocal().getWidth();

        double moveScroll;
        boolean scrollButtonLeftVisible;

        if (historyWidth > maxWidthLabelOperation) {
            scrollButtonLeftVisible = true;
            int fullWidth = (int) (historyWidth / maxWidthLabelOperation);
            double maxH = scrollPane.getHmax();
            moveScroll = maxH / fullWidth;
        } else {
            scrollButtonLeftVisible = false;
            scrollButtonRight.setVisible(false);
            moveScroll = 0;
        }
        scrollButtonLeft.setVisible(scrollButtonLeftVisible);
        return moveScroll;
    }
}
