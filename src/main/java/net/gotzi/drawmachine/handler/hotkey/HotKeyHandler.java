package net.gotzi.drawmachine.handler.hotkey;

import net.gotzi.drawmachine.DrawMachineSim;
import net.gotzi.drawmachine.api.Action;
import net.gotzi.drawmachine.api.KeyAction;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class HotKeyHandler implements IHotKeyHandler {

    private final JRootPane rootPane;

    private final DrawMachineSim drawMachineSim;

    public HotKeyHandler( DrawMachineSim drawMachineSim) {
        this.rootPane = drawMachineSim.getWindow().getRootPane();
        this.drawMachineSim = drawMachineSim;
    }

    @Override
    public void addHotKey(String key, KeyStroke keyStroke, Action<DrawMachineSim> action) {
        this.rootPane.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(keyStroke, key);
        this.rootPane.getActionMap().put(key, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                action.run(HotKeyHandler.this.drawMachineSim);
            }
        });
    }
}
