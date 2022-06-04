package at.gotzi.drawmachine.utils;

import javax.swing.*;

public class Helper {

    public static void showErrorMessage(Object msg) {
        JOptionPane.showConfirmDialog(null, msg);
    }

    public static String swingFormat(int space, double f, int decimalPoints) {
        String str = String.format("%" + space + "." + decimalPoints + "f", f);
        return ">" + str.replaceAll(" ", "_");
    }

    public static String swingFormat(int space, int i) {
        String str = String.format("%" + space+ "d", i);
        return ">" + str.replaceAll(" ", "_");
    }

    private static int getDigits(int num) {
        return String.valueOf(num).toCharArray().length;
    }
}
