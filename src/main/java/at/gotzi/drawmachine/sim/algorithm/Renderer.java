package at.gotzi.drawmachine.sim.algorithm;

import at.gotzi.drawmachine.sim.SimInfo;

public interface Renderer {

    void render(SimInfo simInfo);

    void stop();

    int getCurrentSteps();

}
