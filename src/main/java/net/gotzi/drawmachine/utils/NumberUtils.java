package net.gotzi.drawmachine.utils;

public class NumberUtils {


    public static boolean isDouble(Object obj) {
        if (obj instanceof String str) {
            try {
                Double.parseDouble(str);
            } catch (Exception e) {
                return false;
            }
        }

        return true;
    }

}
