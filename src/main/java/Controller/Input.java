package Controller;

import javafx.scene.control.Label;

public class Input {

    private static String defaultText = "0";

    public static String backspace(String out){
        if (out.replace(",", "").length() > 0) {
            if ((out.length() == 2 && out.contains("-")) ||
                    out.length() == 1) {
                out = defaultText;
            } else {
                out = new StringBuilder(out).deleteCharAt(out.length() - 1).toString();
            }
        }

        return out;
    }

    public static String getTextLabel (Label label, String replace){
        return label.getText().replace(replace, "");
    }

    public static String deleteNumberSeparator (String text, String replace){
        return text.replace(replace, "");
    }

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
