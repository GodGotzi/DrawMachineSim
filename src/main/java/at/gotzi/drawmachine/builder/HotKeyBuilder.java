package at.gotzi.drawmachine.builder;

import at.gotzi.drawmachine.DrawMachineSim;
import at.gotzi.drawmachine.api.IBuilder;
import at.gotzi.drawmachine.handler.HotKeyHandler;
import at.gotzi.drawmachine.view.file.FileView;

import javax.swing.*;

public class HotKeyBuilder extends IBuilder<HotKeyHandler> {

    private HotKeyHandler hotKeyHandler;

    private final DrawMachineSim drawMachineSim;
    public HotKeyBuilder(DrawMachineSim drawMachineSim) {
        this.drawMachineSim = drawMachineSim;
    }

    @Override
    public void build() {
        this.hotKeyHandler = new HotKeyHandler();

        //reset View
        String keyStrokeStr = drawMachineSim.getConfig().get("reset_view_hotkey");
        KeyStroke keyStroke = KeyStroke.getKeyStroke(keyStrokeStr);
        hotKeyHandler.addHotKey(keyStroke, keyEvent -> {
            FileView fileView = (FileView) drawMachineSim.getFileHub().getSelectedComponent();
            fileView.getSimView().resetView();
        });

        setSuccessful(true);
    }

    @Override
    public HotKeyHandler getResult() {
        return this.hotKeyHandler;
    }
}
