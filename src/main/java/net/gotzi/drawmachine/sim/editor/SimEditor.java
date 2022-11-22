package net.gotzi.drawmachine.sim.editor;

import net.gotzi.drawmachine.api.sim.SimValues;

public interface SimEditor {

    SimValues getTestSimValues();

    SimValues getSimValues();

    int getBaseSteps();


}