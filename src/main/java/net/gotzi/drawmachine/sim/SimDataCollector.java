package net.gotzi.drawmachine.sim;

import net.gotzi.drawmachine.api.sim.SimEditorValues;
import net.gotzi.drawmachine.api.sim.SimProgramInfo;
import net.gotzi.drawmachine.api.sim.SimValues;
import net.gotzi.drawmachine.sim.gcode.GCode;
import net.gotzi.drawmachine.view.hub.sim.SimGCodeTab;
import net.gotzi.drawmachine.view.hub.sim.SimSimulationTab;

import java.util.Arrays;

public class SimDataCollector {

    private SimSimulationTab simulationTab;
    private SimGCodeTab simGCodeTab;

    public SimProgramInfo collectProgram() {
        GCode gCode = simGCodeTab.loadGCode(false);
        SimEditorValues editorValues = simulationTab.getSimEditorView().getSimEditorValues();
        return new SimProgramInfo(editorValues, gCode);
    }

    public SimValues collectValues() {
        GCode gCode = simGCodeTab.loadGCode(true);

        SimEditorValues editorValues = simulationTab.getSimEditorView().getSimEditorValues();

        return new SimValues(
                editorValues.middlePoint(),
                editorValues.m1Point(),
                editorValues.m2Point(),
                editorValues.m1Horn(),
                editorValues.m2Horn(),
                editorValues.mainPole(),
                editorValues.supportPole(),
                editorValues.intersection(),
                gCode
        );
    }

    public void setSimGCodeTab(SimGCodeTab simGCodeTab) {
        this.simGCodeTab = simGCodeTab;
    }

    public void setSimulationTab(SimSimulationTab simulationTab) {
        this.simulationTab = simulationTab;
    }
}
