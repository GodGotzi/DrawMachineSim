package net.gotzi.drawmachine.handler.hotkey;

import net.gotzi.drawmachine.DrawMachineSim;
import net.gotzi.drawmachine.api.Action;
import net.gotzi.drawmachine.view.hub.sim.SimProgramFileView;

public enum HotKey {
    RESET_SIM_VIEW("reset_sim_view", drawMachineSim -> {
        SimProgramFileView simProgramFileView = (SimProgramFileView) drawMachineSim
                .getView().getFileHub().getSelectedComponent();
        if (simProgramFileView != null) simProgramFileView.getSimView().resetView();
    });

    private final String key;
    private final Action<DrawMachineSim> drawMachineSimAction;

    HotKey(String key, Action<DrawMachineSim> drawMachineSimAction) {
        this.key = key;
        this.drawMachineSimAction = drawMachineSimAction;
    }

    public String getKey() {
        return key;
    }

    public Action<DrawMachineSim> getDrawMachineSimAction() {
        return drawMachineSimAction;
    }
}
