package net.gotzi.drawmachine.view.menubar.actions;

import net.gotzi.drawmachine.DrawMachineSim;
import net.gotzi.drawmachine.view.hub.FileHubView;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class NewProgramAction extends AbstractAction {

    /**
     * The function creates a new file with the name specified by the user, and then opens the file in a new tab
     *
     * @param e The ActionEvent that triggered the action.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        FileHubView fileHubView = DrawMachineSim.getInstance().getView().getFileHub();


        String str = JOptionPane.showInputDialog("Name your Mode");
        if (str == null) return;
        if (!str.contains(".dmsp")) str += ".dmsp";

        fileHubView.createFilePage(str);
    }
}
