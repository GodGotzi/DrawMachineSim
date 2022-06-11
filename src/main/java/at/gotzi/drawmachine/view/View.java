package at.gotzi.drawmachine.view;

import at.gotzi.drawmachine.view.file.FileHubView;
import at.gotzi.drawmachine.view.workspace.Workspace;
import at.gotzi.drawmachine.view.workspace.WorkspaceView;

import javax.swing.*;

public class View extends JSplitPane {

    private FileHubView fileHubView;

    private WorkspaceView workspaceView;

    public View() {
        this.init();
    }

    private void init() {
        this.fileHubView = new FileHubView();
        this.workspaceView = new WorkspaceView();

        setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        setRightComponent(fileHubView);
        setLeftComponent(workspaceView);
        setResizeWeight(0.01);
    }

    public FileHubView getFileHub() {
        return fileHubView;
    }

    public Workspace getWorkspace() {
        return workspaceView;
    }
}
