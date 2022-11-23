package net.gotzi.drawmachine.builder;

import net.gotzi.drawmachine.api.sim.SimModeInfo;

public class ModeStringBuilder extends Builder<String, ModeStringBuilder> {
    private final SimModeInfo modeInfo;

    private StringBuilder stringBuilder;

    public ModeStringBuilder(SimModeInfo modeInfo) {
        this.modeInfo = modeInfo;
    }

    @Override
    public ModeStringBuilder build() {
        this.stringBuilder = new StringBuilder();
        //TODO SimModeInfo convert to String bzw json format

        return this;
    }

    @Override
    public String getResult() {
        return stringBuilder.toString();
    }
}
