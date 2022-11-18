package net.gotzi.drawmachine.menubar.actions;

import net.gotzi.drawmachine.DrawMachineSim;
import net.gotzi.drawmachine.api.sim.SimModeInfo;
import net.gotzi.drawmachine.builder.ModeStringBuilder;
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
        String str = getName();
        if (str == null) return;
        if (!str.contains(".mdm")) str += ".mdm";

        SimModeInfo simModeInfo = new SimModeInfo();
        Object data = createData(simModeInfo);

        try {
            this.createFile(str, data);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(DrawMachineSim.getInstance().getWindow().getFrame(), "Problem while creating File: " + ex.getMessage());
            return;
        }

        ModeFileView modeFileView = new ModeFileView(simModeInfo, str);
        DrawMachineSim.getInstance().getFileHub().openFilePage(modeFileView);
    }

    private Object createData(SimModeInfo simModeInfo) {
        ModeStringBuilder modeStringBuilder = new ModeStringBuilder(simModeInfo);
        modeStringBuilder.build();
        return modeStringBuilder.getResult();
    }

    /**
     * It creates a new file in the workspace directory with the name of the first parameter and the contents of the second
     * parameter
     *
     * @param str The name of the file to be created.
     * @param data The data to be written to the file.
     */
    private void createFile(String str, Object data) throws IOException {
        Workspace workspace = DrawMachineSim.getInstance().getWorkspace();
        File newFile = new File(workspace.getDirectoryPath() + "\\" + str);
        boolean ret = newFile.createNewFile();

        if (!ret) {
            throw new IOException("File/Mode already Exists");
        }

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(newFile));
        bufferedWriter.write(data.toString());
        bufferedWriter.close();
    }

    private String getName() {
        return JOptionPane.showInputDialog("Name your Mode");
    }
}
