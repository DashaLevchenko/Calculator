package Controller;

/**
 * This class changes text
 */
public class Text {
    /**
     * Default value for general display
     */
    private static String defaultText = "0";

    /**
     * This method deletes last symbol in text
     * @param text Text which need to change
     * @return Text was changed
     */
    public static String backspace(String text){
        if (text.replace(",", "").length() > 0) {
            if ((text.length() == 2 && text.contains("-")) ||
                    text.length() == 1) {
                text = defaultText;
            } else {
                text = new StringBuilder(text).deleteCharAt(text.length() - 1).toString();
            }
        }

        return text;
    }

    /**
     * Method removes some separator from text
     * @param text Text
     * @param replace Separator need to remove
     * @return Text without separator
     */
    public static String deleteNumberSeparator (String text, String replace){
        return text.replace(replace, "");
    }

    /**
     * Method inserts "-" before text.
     * Example: before: "9", after: "-9"
     * @param text Text need to change
     * @return Text with "-"
     */
    public static String addNegate(String text){
        if (!text.equals(defaultText)) {
            if (text.charAt(0) != '-') {
                text = "-"+text;
            } else {
                text = new StringBuilder(text).deleteCharAt(0).toString();
            }
        }
        return text;
    }




}
