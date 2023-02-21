package net.gotzi.drawmachine.sim.editor;

import net.gotzi.drawmachine.DrawMachineSim;
import net.gotzi.drawmachine.api.sim.SimProgramInfo;
import net.gotzi.drawmachine.api.sim.SimPoint;
import net.gotzi.drawmachine.api.sim.SimEditorValues;
import net.gotzi.drawmachine.control.UnderLayPanel;
import net.gotzi.drawmachine.utils.NumberUtils;

import javax.swing.*;

public class SimEditorView implements SimEditor, SimInfoParameters {

    private final int baseSteps;

    private UnderLayPanel view;
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
    private JTextArea simInfoArea;

    public SimEditorView(SimProgramInfo simProgramInfo) {
        this.baseSteps = Integer.parseInt(DrawMachineSim.getInstance().getConfig().get("base_steps"));

        middlePointX.setModel(new SpinnerNumberModel(0.0, -10000.0,10000.0,0.1));
        m1PointX.setModel(new SpinnerNumberModel(0.0, -10000.0,10000.0,0.1));
        m2PointX.setModel(new SpinnerNumberModel(0.0, -10000.0,10000.0,0.1));
        middlePointY.setModel(new SpinnerNumberModel(0.0, -10000.0,10000.0,0.1));
        m1PointY.setModel(new SpinnerNumberModel(0.0, -10000.0,10000.0,0.1));
        m2PointY.setModel(new SpinnerNumberModel(0.0, -10000.0,10000.0,0.1));
        m1HornLength.setModel(new SpinnerNumberModel(0.0, -10000.0,10000.0,0.1));
        m2HornLength.setModel(new SpinnerNumberModel(0.0, -10000.0,10000.0,0.1));
        mainPoleLength.setModel(new SpinnerNumberModel(0.0, -10000.0,10000.0,0.1));
        supportPoleLength.setModel(new SpinnerNumberModel(0.0, -10000.0,10000.0,0.1));
        intersectionLength.setModel(new SpinnerNumberModel(0.0, -10000.0,10000.0,0.1));

        setup(simProgramInfo);
    }

    private void setup(SimProgramInfo simProgramInfo) {
        this.view = new UnderLayPanel(panel);
        this.view.setSouthBorderThickness(5);
        this.view.setNorthBorderThickness(5);
        this.view.setWestBorderThickness(15);
        this.view.setEastBorderThickness(15);

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

        //TODO init values with simModeInfo
    }

    @Override
    public SimEditorValues getSimEditorValues() {
        return new SimEditorValues(new SimPoint(getValue(middlePointX), getValue(middlePointY)),
                        new SimPoint(getValue(m1PointX), getValue(m1PointY)),
                        new SimPoint(getValue(m2PointX), getValue(m2PointY)),
                        getValue(m1HornLength),
                        getValue(m2HornLength),
                        getValue(mainPoleLength),
                        getValue(supportPoleLength),
                        getValue(intersectionLength)
        );
    }

    private double getValue(JSpinner spinner) {
        if (NumberUtils.isDouble(spinner.getValue()))
            return Double.parseDouble(spinner.getValue().toString());

        return 0;
    }

    public JPanel getView() {
        return view;
    }

    public int getBaseSteps() {
        return baseSteps;
    }

    @Override
    public void clear() {
        simInfoArea.setText("");
    }

    @Override
    public void print(String str) {
        simInfoArea.append(str);
    }

    @Override
    public void println(String str) {
        simInfoArea.append(str + "\n");
    }
}