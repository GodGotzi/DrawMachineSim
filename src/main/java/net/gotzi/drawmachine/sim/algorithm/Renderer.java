package net.gotzi.drawmachine.sim.algorithm;

import net.gotzi.drawmachine.sim.SimInfo;

public interface Renderer {

    void render(SimInfo simInfo);

    void stop();

}
