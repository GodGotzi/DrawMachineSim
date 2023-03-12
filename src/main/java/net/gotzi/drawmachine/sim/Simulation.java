/*
	Author: Elias (Gotzi) Gottsbacher
	Copyright (c) 2023 Elias Gottsbacher
*/

package net.gotzi.drawmachine.sim;

import net.gotzi.drawmachine.api.sim.SimRenderState;

public interface Simulation {

    void run();

    void stop();

    void resetView();

    void resetCanvas();

    int getTimestamp();

    boolean isRunning();

    void updateState(SimRenderState state);

    boolean isFastMode();

}
