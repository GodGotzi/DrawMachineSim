package at.gotzi.drawmachine.sim.editor;

import at.gotzi.drawmachine.sim.api.SimValues;

public interface SimEditor {

    SimValues getTestSimValues();

    SimValues getSimValues();

    int getBaseSteps();


}
