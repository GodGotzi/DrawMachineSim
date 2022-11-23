package net.gotzi.drawmachine.builder;

import net.gotzi.drawmachine.DrawMachineSim;
import net.gotzi.drawmachine.api.KeyAction;
import net.gotzi.drawmachine.data.ConfigLoader;
import net.gotzi.drawmachine.handler.hotkey.HotKey;
import net.gotzi.drawmachine.handler.hotkey.HotKeyHandler;
import net.gotzi.drawmachine.view.file.ModeFileView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.InputStream;

public class HotKeyBuilder extends Builder<HotKeyHandler> {

    private final HotKeyHandler hotKeyHandler;

    public HotKeyBuilder(DrawMachineSim drawMachineSim) {
        this.hotKeyHandler = new HotKeyHandler(drawMachineSim);
    }

    @Override
    public void build() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("hotkeys.properties");
        ConfigLoader configLoader = new ConfigLoader(inputStream);
        configLoader.load();

        for (HotKey hotKey : HotKey.values()) {
            String reset_sim_view_hotkey = configLoader.getResult().get("hotkey." + hotKey.getKey());
            KeyStroke keyStroke = KeyStroke.getKeyStroke(reset_sim_view_hotkey);

            this.hotKeyHandler.addHotKey(hotKey.getKey(), keyStroke, hotKey.getDrawMachineSimAction());
        }

        setSuccessful(true);
    }

    @Override
    public HotKeyHandler getResult() {
        return this.hotKeyHandler;
    }
}
