package net.gotzi.drawmachine.view.hub.sim;

import net.gotzi.drawmachine.api.sim.SimProgramInfo;
import net.gotzi.drawmachine.control.layout.VerticalSplitLayout;
import net.gotzi.drawmachine.sim.SimDataCollector;
import net.gotzi.drawmachine.sim.SimView;
import net.gotzi.drawmachine.sim.editor.SimEditorView;

import javax.swing.*;

public class SimSimulationTab extends JPanel {

    private final SimEditorView simEditorView;
    private final SimView simView;

    public SimSimulationTab(SimProgramInfo programInfo, SimDataCollector dataCollector) {
        this.simEditorView = new SimEditorView(programInfo);
        this.simView = new SimView(simEditorView, dataCollector);

        add(simEditorView.getView());
        add(simView);

        buildLayout();
    }

    /**
     * This function creates a vertical split layout, and sets the size of the first component to 325 pixels.
     */
    private void buildLayout() {
        VerticalSplitLayout verticalSplitLayout = new VerticalSplitLayout(
                this.simEditorView.getView(),
                this.simView);
        verticalSplitLayout.setComponent1Size(325);
        setLayout(verticalSplitLayout);
    }

    public SimView getSimView() {
        return simView;
    }

    public SimEditorView getSimEditorView() {
        return simEditorView;
    }
}
