package net.gotzi.drawmachine.sim.editor;

import net.gotzi.drawmachine.DrawMachineSim;
import net.gotzi.drawmachine.api.sim.SimModeInfo;
import net.gotzi.drawmachine.api.sim.SimPoint;
import net.gotzi.drawmachine.api.sim.SimValues;

import javax.swing.*;

public class SimEditorView implements SimEditor {

    private final int baseSteps;

    private JPanel panel;
    private JSpinner middlePointX;
    private JSpinner m1PointX;
    private JSpinner m2PointX;
    private JSpinner middlePointY;
    private JSpinner m1PointY;
    private JSpinner m2PointY;
    private JSpinner m1HornLength;
    private JSpinner m2HornLength;
    private JSpinner mainPoleLength;
    private JSpinner supportPoleLength;
    private JSpinner intersectionLength;
    private JSpinner m2Speed;
    private JSpinner m1Speed;
    private JSpinner middleSpeed;

    public SimEditorView(SimModeInfo simModeInfo) {
        this.baseSteps = Integer.parseInt(DrawMachineSim.getInstance().getConfig().get("base_steps"));


        load(simModeInfo);
    }

    private void load(SimModeInfo simModeInfo) {
        this.middlePointX.setValue(simModeInfo.saved().middlePoint().x());
        this.middlePointY.setValue(simModeInfo.saved().middlePoint().y());

        this.m1PointX.setValue(simModeInfo.saved().m1Point().x());
        this.m1PointY.setValue(simModeInfo.saved().m1Point().y());

        this.m2PointX.setValue(simModeInfo.saved().m2Point().x());
        this.m2PointY.setValue(simModeInfo.saved().m2Point().y());

        this.m1HornLength.setValue(simModeInfo.saved().m1Horn());
        this.m2HornLength.setValue(simModeInfo.saved().m2Horn());

        this.mainPoleLength.setValue(simModeInfo.saved().mainPole());

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
                200,
                200,
                2200,
                1400,
                1100,
                (double) 360/ (double) baseSteps,
                60,
                32,
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