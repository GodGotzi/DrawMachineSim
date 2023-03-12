/*
	Author: Elias (Gotzi) Gottsbacher
	Copyright (c) 2023 Elias Gottsbacher
*/

package net.gotzi.drawmachine.builder;

import net.gotzi.drawmachine.DrawMachineSim;
import net.gotzi.drawmachine.data.ConfigLoader;
import net.gotzi.drawmachine.handler.hotkey.HotKey;
import net.gotzi.drawmachine.handler.hotkey.HotKeyHandler;

import javax.swing.*;
import java.io.InputStream;

public class HotKeyBuilder extends Builder<HotKeyHandler, HotKeyBuilder> {

    private final HotKeyHandler hotKeyHandler;

    public HotKeyBuilder(DrawMachineSim drawMachineSim) {
        this.hotKeyHandler = new HotKeyHandler(drawMachineSim);
    }

    @Override
    public HotKeyBuilder build() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("hotkeys.properties");
        ConfigLoader configLoader = new ConfigLoader(inputStream);
        configLoader.load();

        for (HotKey hotKey : HotKey.values()) {
            String hotkey = configLoader.getResult().get("hotkey." + hotKey.getKey());
            KeyStroke keyStroke = KeyStroke.getKeyStroke(hotkey);

            this.hotKeyHandler.addHotKey(hotKey.getKey(), keyStroke, hotKey.getAction());
        }

        setSuccessful(true);

        return this;
    }

    @Override
    public HotKeyHandler getResult() {
        return this.hotKeyHandler;
    }
}
