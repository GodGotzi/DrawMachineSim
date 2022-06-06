package at.gotzi.drawmachine.utils;

import at.gotzi.drawmachine.DrawMachineSim;

import javax.swing.*;
import java.awt.*;

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

    public static String getCallerMethodName() {
        return new Exception().getStackTrace()[2].getMethodName();
    }

    public static String getCallerMethodName(int index) {
        return new Exception().getStackTrace()[index].getMethodName();
    }

    public static String getCallerClassName() {
        return new Exception().getStackTrace()[2].getClassName();
    }

    public static String getCallerClassName(int index) {
        return new Exception().getStackTrace()[index].getClassName();
    }

    public static void printClassMethodName() {
        System.out.println("Class:" + getCallerClassName() + " Method: " + getCallerMethodName());
    }

    public static void printClassMethodName(String msg) {
        System.out.println("Class:" + getCallerClassName() + " Method: " + getCallerMethodName() + " Message: " + msg);
    }

    private static int getDigits(int num) {
        return String.valueOf(num).toCharArray().length;
    }

    public static int getPaperWidth() {
        return Integer.parseInt(DrawMachineSim.getInstance().getConfig().get("paper_width"));
    }

    public static int getPaperHeight() {
        return Integer.parseInt(DrawMachineSim.getInstance().getConfig().get("paper_height"));
    }

    public static Dimension getPaperDimension() {
        return new Dimension(getPaperWidth(), getPaperHeight());
    }



}