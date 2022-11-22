package net.gotzi.drawmachine.builder;

import net.gotzi.drawmachine.DrawMachineSim;
import net.gotzi.drawmachine.handler.HotKeyHandler;
import net.gotzi.drawmachine.view.file.ModeFileView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class HotKeyBuilder extends Builder<HotKeyHandler> {

    private HotKeyHandler hotKeyHandler;

    private final DrawMachineSim drawMachineSim;
    public HotKeyBuilder(DrawMachineSim drawMachineSim) {
        this.drawMachineSim = drawMachineSim;
    }

    @Override
    public void build() {
        this.hotKeyHandler = new HotKeyHandler();

        String reset_view_hotkey = drawMachineSim.getConfig().get("reset_view_hotkey");
        KeyStroke keyStroke = KeyStroke.getKeyStroke(reset_view_hotkey);

        drawMachineSim.getWindow().getRootPane().getInputMap().put(keyStroke, "reset_sim_view");
        drawMachineSim.getWindow().getRootPane().getActionMap().put("reset_sim_view", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("control R");
                ModeFileView modeFileView = (ModeFileView) drawMachineSim.getFileHub().getSelectedComponent();
                if (modeFileView != null) modeFileView.getSimView().resetView();
            }
        });

        setSuccessful(true);
    }

    @Override
    public HotKeyHandler getResult() {
        return this.hotKeyHandler;
    }
}
