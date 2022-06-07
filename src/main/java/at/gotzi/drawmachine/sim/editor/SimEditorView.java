package at.gotzi.drawmachine.sim.editor;

import javax.swing.*;

public class SimEditorView implements SimEditor {
    private JPanel panel;







    public JPanel getPanel() {
        return panel;
    }

    @Override
    public double getSpeedMiddle() {
        return 0.0001;
    }

    @Override
    public double getSpeedM1() {
        return 0.1;
    }

    @Override
    public double getSpeedM2() {
        return 0.1;
    }
}
