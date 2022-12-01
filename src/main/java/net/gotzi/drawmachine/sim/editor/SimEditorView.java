package net.gotzi.drawmachine.sim.editor;

import net.gotzi.drawmachine.DrawMachineSim;
import net.gotzi.drawmachine.api.sim.SimProgramInfo;
import net.gotzi.drawmachine.api.sim.SimPoint;
import net.gotzi.drawmachine.api.sim.SimRawValues;
import net.gotzi.drawmachine.api.sim.SimValues;
import net.gotzi.drawmachine.control.UnderLayPanel;
import net.gotzi.drawmachine.handler.design.DesignColor;
import net.gotzi.drawmachine.handler.design.DesignHandler;
import net.gotzi.drawmachine.utils.NumberUtils;

import javax.swing.*;
import java.awt.*;

public class SimEditorView implements SimEditor {

    private final int baseSteps;

    private JTextPane simCompletedInfo;
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
    private JPanel simCompletedInfoArea;

    public SimEditorView(SimProgramInfo simProgramInfo) {
        this.baseSteps = Integer.parseInt(DrawMachineSim.getInstance().getConfig().get("base_steps"));

        load(simProgramInfo);
    }

    private void load(SimProgramInfo simProgramInfo) {
        this.simCompletedInfo = new JTextPane();
        this.simCompletedInfo.setEditable(false);

        UnderLayPanel underLayPanel = new UnderLayPanel(simCompletedInfo);
        underLayPanel.setEastBorderThickness(5);
        underLayPanel.setWestBorderThickness(5);

        this.simCompletedInfoArea.add(underLayPanel, BorderLayout.CENTER);

        this.middlePointX.setValue(simProgramInfo.saved().middlePoint().x());
        this.middlePointY.setValue(simProgramInfo.saved().middlePoint().y());

        this.m1PointX.setValue(simProgramInfo.saved().m1Point().x());
        this.m1PointY.setValue(simProgramInfo.saved().m1Point().y());

        this.m2PointX.setValue(simProgramInfo.saved().m2Point().x());
        this.m2PointY.setValue(simProgramInfo.saved().m2Point().y());

        this.m1HornLength.setValue(simProgramInfo.saved().m1Horn());
        this.m2HornLength.setValue(simProgramInfo.saved().m2Horn());

        this.mainPoleLength.setValue(simProgramInfo.saved().mainPole());

        this.supportPoleLength.setValue(simProgramInfo.saved().supportPole());

        this.intersectionLength.setValue(simProgramInfo.saved().intersection());

        this.middleSpeed.setValue(simProgramInfo.saved().speedMiddle());

        this.m1Speed.setValue(simProgramInfo.saved().speedM1());
        this.m2Speed.setValue(simProgramInfo.saved().speedM2());

        //TODO init values with simModeInfo
    }

    public JPanel getView() {
        return panel;
    }

    @Override
    public SimValues getSimValues() {
        return new SimValues(
                new SimPoint(getValue(middlePointX), getValue(middlePointY)),
                new SimPoint(getValue(m1PointX), getValue(m1PointY)),
                new SimPoint(getValue(m2PointX), getValue(m2PointY)),
                getValue(m1HornLength),
                getValue(m2HornLength),
                getValue(mainPoleLength),
                getValue(supportPoleLength),
                getValue(intersectionLength),
                getValue(middleSpeed) / (double) baseSteps,
                getValue(m1Speed),
                getValue(m2Speed),
                baseSteps);
    }

    public SimProgramInfo getNewSimProgramInfo() {
        return new SimProgramInfo(
                new SimRawValues(
                        new SimPoint(getValue(middlePointX), getValue(middlePointY)),
                        new SimPoint(getValue(m1PointX), getValue(m1PointY)),
                        new SimPoint(getValue(m2PointX), getValue(m2PointY)),
                        getValue(m1HornLength),
                        getValue(m2HornLength),
                        getValue(mainPoleLength),
                        getValue(supportPoleLength),
                        getValue(intersectionLength),
                        getValue(middleSpeed),
                        getValue(m1Speed),
                        getValue(m2Speed)
                )
        );
    }

    private double getValue(JSpinner spinner) {
        if (NumberUtils.isDouble(spinner.getValue()))
            return Double.parseDouble(spinner.getValue().toString());

        return 0;
    }

    public int getBaseSteps() {
        return baseSteps;
    }
}