package at.gotzi.drawmachine.builder;

import at.gotzi.drawmachine.api.sim.SimModeInfo;

public class ModeStringBuilder extends Builder<String> {
    private final SimModeInfo modeInfo;

    private StringBuilder stringBuilder;

    public ModeStringBuilder(SimModeInfo modeInfo) {
        this.modeInfo = modeInfo;
    }

    @Override
    public void build() {
        this.stringBuilder = new StringBuilder();
        //TODO SimModeInfo convert to String bzw json format
    }

    @Override
    public String getResult() {
        return stringBuilder.toString();
    }
}
