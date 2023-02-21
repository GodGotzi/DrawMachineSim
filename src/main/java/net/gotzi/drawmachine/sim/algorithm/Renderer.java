package net.gotzi.drawmachine.sim.algorithm;

import net.gotzi.drawmachine.sim.SimInfo;
import net.gotzi.drawmachine.sim.editor.SimInfoParameters;

public interface Renderer {

    void render(SimInfo simInfo, SimInfoParameters simInfoParameters);

    void stop();

}
