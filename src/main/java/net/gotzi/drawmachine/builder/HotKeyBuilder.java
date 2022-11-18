package net.gotzi.drawmachine.builder;

import net.gotzi.drawmachine.DrawMachineSim;
import net.gotzi.drawmachine.handler.HotKeyHandler;
import net.gotzi.drawmachine.view.file.ModeFileView;

import javax.swing.*;

public class HotKeyBuilder extends Builder<HotKeyHandler> {

    private HotKeyHandler hotKeyHandler;

    private final DrawMachineSim drawMachineSim;
    public HotKeyBuilder(DrawMachineSim drawMachineSim) {
        this.drawMachineSim = drawMachineSim;
    }

    @Override
    public void build() {
        this.hotKeyHandler = new HotKeyHandler();

        String keyStrokeStr = drawMachineSim.getConfig().get("reset_view_hotkey");
        KeyStroke keyStroke = KeyStroke.getKeyStroke(keyStrokeStr);
        hotKeyHandler.addHotKey(keyStroke, keyEvent -> {
            ModeFileView modeFileView = (ModeFileView) drawMachineSim.getFileHub().getSelectedComponent();
            modeFileView.getSimView().resetView();
            System.out.println(":D");
        });

        setSuccessful(true);
    }

    @Override
    public HotKeyHandler getResult() {
        return this.hotKeyHandler;
    }
}
