package net.gotzi.drawmachine.data;

import net.gotzi.drawmachine.api.sim.SimModeInfo;

public class ModeLoader extends Loader<SimModeInfo> {

    private final String modeStr;

    private SimModeInfo simModeInfo;

    public ModeLoader(String modeStr) {
        this.modeStr = modeStr;
    }

    @Override
    public void load() {
        this.simModeInfo = new SimModeInfo();

        //TODO load json String to Object
    }

    @Override
    public SimModeInfo getResult() {
        return this.simModeInfo;
    }
}
