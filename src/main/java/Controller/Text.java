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
     *
     * @param text Text which need to change
     * @return Text was changed
     */
    public static String backspace (String text) {
        String comma = ",";

        int symbolsInTextWithoutComma = text.replace(comma, "").length();
        if (symbolsInTextWithoutComma > 0) {
            String minus = "-";

            int textLength = text.length();
            int minLengthWithMinus = 2;
            int minLength = 1;

            if ((textLength == minLengthWithMinus && text.contains(minus)) ||
                    textLength == minLength) {
                text = defaultText;
            } else {
                text = new StringBuilder(text).deleteCharAt(textLength - 1).toString();
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
     *
     * @param text Text need to change
     * @return Text with "-"
     */
    public String addNegate (String text) {
        if (!text.equals(defaultText)) {
            char minus = '-';
            char firstChar = text.charAt(0);
            if (firstChar != minus) {
                text = minus + text;
            } else {
                text = new StringBuilder(text).deleteCharAt(firstChar).toString();
            }
        }
        return text;
    }




}
