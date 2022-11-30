package net.gotzi.drawmachine.view.menubar.actions;

import net.gotzi.drawmachine.DrawMachineSim;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class SaveProgramAction extends AbstractAction {

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            DrawMachineSim.getInstance().getView().getFileHub().save();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
