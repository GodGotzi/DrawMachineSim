package at.gotzi.drawmachine.sim.editor;

import at.gotzi.drawmachine.DrawMachineSim;
import at.gotzi.drawmachine.api.sim.SimModeInfo;
import at.gotzi.drawmachine.api.sim.SimPoint;
import at.gotzi.drawmachine.api.sim.SimValues;

import javax.swing.*;

public class SimEditorView implements SimEditor {

    private final int baseSteps;

    private JPanel panel;

    public SimEditorView(SimModeInfo simModeInfo) {
        this.baseSteps = Integer.parseInt(DrawMachineSim.getInstance().getConfig().get("base_steps"));

        //TODO init values with simModeInfo
    }

    public JPanel getPanel() {
        return panel;
    }

    @Override
    public SimValues getTestSimValues() {
        return new SimValues(
                new SimPoint(1050, 1050),
                new SimPoint(1050-700, 1050-2400),
                new SimPoint(1050+700, 1050-2400),
                280,
                280,
                2000,
                1200,
                1150,
                (double) 360/ (double) baseSteps,
                1,
                30,
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