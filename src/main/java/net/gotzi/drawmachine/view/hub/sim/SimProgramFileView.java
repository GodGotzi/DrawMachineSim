/*
	Author: Elias (Gotzi) Gottsbacher
	Copyright (c) 2023 Elias Gottsbacher
*/

package net.gotzi.drawmachine.view.hub.sim;

import net.gotzi.drawmachine.api.sim.SimProgramInfo;
import net.gotzi.drawmachine.sim.SimDataCollector;
import net.gotzi.drawmachine.view.hub.FileView;
import net.gotzi.drawmachine.api.components.VerticalTabbedPane;

import java.awt.*;
import java.io.File;

public class SimProgramFileView extends FileView<SimProgramInfo> {

    private final SimSimulationTab simulationTab;
    private final SimGCodeTab simGCodeTab;
    private final SimDataCollector simDataCollector;
    private final VerticalTabbedPane tabbedPane;

    public SimProgramFileView(SimProgramInfo simProgramInfo, String name, File file) {
        super(name, "dmsp", file);

        this.simDataCollector = new SimDataCollector();
        this.simGCodeTab = new SimGCodeTab(simProgramInfo);
        this.simulationTab = new SimSimulationTab(simProgramInfo, simDataCollector);

        this.simDataCollector.setSimGCodeTab(this.simGCodeTab);
        this.simDataCollector.setSimulationTab(this.simulationTab);

        this.tabbedPane = new VerticalTabbedPane();

        this.tabbedPane.addTab("Simulation", this.simulationTab);
        this.tabbedPane.addTab("GCode", this.simGCodeTab);

        this.setLayout(new BorderLayout());

        add(this.tabbedPane, BorderLayout.CENTER);
    }

    public VerticalTabbedPane getTabbedPane() {
        return tabbedPane;
    }

    public SimGCodeTab getSimGCodeTab() {
        return simGCodeTab;
    }

    public SimSimulationTab getSimulationTab() {
        return simulationTab;
    }

    @Override
    public SimProgramInfo getObjectToSave() {
        return this.simDataCollector.collectProgram();
    }
}