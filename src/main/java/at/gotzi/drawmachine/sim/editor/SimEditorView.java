package at.gotzi.drawmachine.sim.editor;

import at.gotzi.drawmachine.DrawMachineSim;
import at.gotzi.drawmachine.api.sim.SimPoint;
import at.gotzi.drawmachine.api.sim.SimValues;

import javax.swing.*;

public class SimEditorView implements SimEditor {

    private final int baseSteps;

    private JPanel panel;

    public SimEditorView() {
        this.baseSteps = Integer.parseInt(DrawMachineSim.getInstance().getConfig().get("base_steps"));
    }

    public JPanel getPanel() {
        return panel;
    }

    @Override
    public SimValues getTestSimValues() {
        return new SimValues(
                new SimPoint(1050, 1050),
                new SimPoint(100, 1050-1580),
                new SimPoint(2000, 1050-1580),
                350,
                100,
                1600,
                1500,
                200,
                (double) 360/ (double) baseSteps,
                10,
                0,
                baseSteps);
    }
    @Override
    public SimValues getSimValues() {
        return null;
    }

    public int getBaseSteps() {
        return baseSteps;
    }
}