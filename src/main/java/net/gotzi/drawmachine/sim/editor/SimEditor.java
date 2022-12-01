package net.gotzi.drawmachine.sim.editor;

import net.gotzi.drawmachine.api.sim.SimProgramInfo;
import net.gotzi.drawmachine.api.sim.SimValues;

public interface SimEditor {

    SimValues getSimValues();

    SimProgramInfo getNewSimProgramInfo();

    int getBaseSteps();


}
