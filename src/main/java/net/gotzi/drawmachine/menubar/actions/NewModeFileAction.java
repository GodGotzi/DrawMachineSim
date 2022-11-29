package net.gotzi.drawmachine.menubar.actions;

import net.gotzi.drawmachine.DrawMachineSim;
import net.gotzi.drawmachine.api.sim.SimModeInfo;
import net.gotzi.drawmachine.api.sim.SimPoint;
import net.gotzi.drawmachine.api.sim.SimRawValues;
import net.gotzi.drawmachine.builder.ModeStringBuilder;
import net.gotzi.drawmachine.manager.ModeFileManager;
import net.gotzi.drawmachine.view.file.FileHubView;
import net.gotzi.drawmachine.view.file.FileView;
import net.gotzi.drawmachine.view.file.ModeFileView;
import net.gotzi.drawmachine.view.workspace.Workspace;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.*;

public class NewModeFileAction extends AbstractAction {

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
        if (!str.contains(".mdm")) str += ".mdm";

        SimModeInfo simModeInfo = new SimModeInfo(new SimRawValues(new SimPoint(0, 0), new SimPoint(0, 0), new SimPoint(0, 0), 0, 0, 0, 0, 0, 0, 0, 0));

        ModeFileManager.ModeFileCreator modeFileCreator = new ModeFileManager.ModeFileCreator(str, simModeInfo);
        File file = modeFileCreator.createNewModeFile();
        if (file == null) return;

        String name;

        if (fileHubView.indexOfTab(file.getName()) != -1) {
            int index = fileHubView.indexOfTab(file.getName());

            if (fileHubView.getComponentAt(index) instanceof FileView fileView) {
                if (file.getAbsolutePath().equals(fileView.getAbsolutePath()))
                    return;
                else
                    fileHubView.setTitleAt(index, fileView.getAbsolutePath());
            }

            name = file.getAbsolutePath();
        } else
            name = file.getName();


        ModeFileView modeFileView = new ModeFileView(simModeInfo, name, file);
        DrawMachineSim.getInstance().getView().getFileHub().openFilePage(modeFileView);
    }
}
