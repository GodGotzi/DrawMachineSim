/*
	Author: Elias (Gotzi) Gottsbacher
	Copyright (c) 2023 Elias Gottsbacher
*/

package net.gotzi.drawmachine.handler.hotkey;

import net.gotzi.drawmachine.DrawMachineSim;
import net.gotzi.drawmachine.api.Action;
import net.gotzi.drawmachine.view.hub.sim.SimProgramFileView;

public enum HotKey {
    RESET_SIM_VIEW("reset_sim_view", drawMachineSim -> {
        SimProgramFileView simProgramFileView = (SimProgramFileView) drawMachineSim
                .getView().getFileHub().getSelectedComponent();
        if (simProgramFileView != null) simProgramFileView.getSimulationTab().getSimView().resetView();
    }),

    SAVE_SIM_PROGRAM("save_sim_program", drawMachineSim -> {
        try {
            drawMachineSim.getView().getFileHub().saveAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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

    public Action<DrawMachineSim> getAction() {
        return drawMachineSimAction;
    }
}
