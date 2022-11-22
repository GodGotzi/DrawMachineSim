package net.gotzi.drawmachine;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.*;
import java.io.IOException;

public class DrawMachineSimLoader {

    public static void main(String[] args) throws IOException {

        Application application = new DrawMachineSim();
        application.start();
    }

}
