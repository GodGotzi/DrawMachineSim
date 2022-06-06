package at.gotzi.drawmachine.sim.alogrithm;

import at.gotzi.drawmachine.sim.editor.SimEditor;
import at.gotzi.drawmachine.sim.view.SimMonitor;

public interface Renderer {

    void render(SimMonitor simMonitor, SimEditor simEditor);

    void stop();

    int getCurrentSteps();

}
