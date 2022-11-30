package net.gotzi.drawmachine.view.hub.sim;

import net.gotzi.drawmachine.DrawMachineSim;
import net.gotzi.drawmachine.api.sim.SimProgramInfo;
import net.gotzi.drawmachine.json.SimProgramLoader;
import net.gotzi.drawmachine.view.hub.Coordinator;
import net.gotzi.drawmachine.view.hub.FileHubView;
import net.gotzi.drawmachine.view.hub.FileView;
import net.gotzi.drawmachine.view.workspace.Workspace;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class SimProgramCoordinator implements Coordinator<SimProgramInfo, SimProgramFileView> {

    private final SimProgramLoader loader;

    private final FileHubView fileHubView;

    public SimProgramCoordinator(SimProgramLoader loader, FileHubView fileHubView) {
        this.loader = loader;
        this.fileHubView = fileHubView;
    }

    @Override
    public SimProgramFileView create(String name) throws Exception {
        SimProgramInfo simProgramInfo = loader.getDefault();

        Workspace workspace = DrawMachineSim.getInstance().getView().getWorkspace();

        File file;

        try {
            file = new File(workspace.getDirectoryPath() + "\\" + name);

            if (!file.createNewFile()) {
                throw new IOException("File/Mode already Exists");
            }

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(DrawMachineSim.getInstance().getWindow(), "Problem while creating File: " + ex.getMessage());
            return null;
        }

        name = getNonDuplicateName(file);
        if (name == null) return null;

        save(file, simProgramInfo);
        return new SimProgramFileView(simProgramInfo, name, file);
    }

    @Override
    public SimProgramFileView load(File file) throws Exception {
        Path path = Path.of(file.toURI());
        String source = Files.readString(path);

        SimProgramInfo simProgramInfo = loader.load(source);

        String name;

        name = getNonDuplicateName(file);
        if (name == null) return null;

        return new SimProgramFileView(simProgramInfo, name, file);
    }

    private String getNonDuplicateName(File file) {
        String name;
        if (fileHubView.indexOfTab(file.getName()) != -1) {
            int index = fileHubView.indexOfTab(file.getName());

            if (fileHubView.getComponentAt(index) instanceof FileView fileView) {
                if (file.getAbsolutePath().equals(fileView.getAbsolutePath()))
                    return null;
                else
                    fileHubView.setTitleAt(index, fileView.getAbsolutePath());
            }

            name = file.getAbsolutePath();
        } else
            name = file.getName();
        return name;
    }

    @Override
    public void save(File file, Object object) throws Exception {
        SimProgramInfo simProgramInfo = (SimProgramInfo) object;
        Object data = loader.unload(simProgramInfo);

        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(data.toString());
        bufferedWriter.close();
    }
}
