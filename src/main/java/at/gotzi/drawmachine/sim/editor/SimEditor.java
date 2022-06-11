package at.gotzi.drawmachine.sim.editor;

import at.gotzi.drawmachine.api.sim.SimValues;

public interface SimEditor {

    SimValues getTestSimValues();

    SimValues getSimValues();

    int getBaseSteps();


}
