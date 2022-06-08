package at.gotzi.drawmachine.view;

import at.gotzi.drawmachine.view.file.FileHub;
import at.gotzi.drawmachine.view.workspace.WorkspaceView;

import javax.swing.*;

public class View extends JSplitPane {

    private final FileHub fileHub;

    private final WorkspaceView workspaceView;

    public View() {
        this.fileHub = new FileHub();
        this.workspaceView = new WorkspaceView();


        setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        setRightComponent(fileHub);
        setLeftComponent(workspaceView.getPanel());
        setResizeWeight(0.3);
    }

    public FileHub getFileHub() {
        return fileHub;
    }

    public WorkspaceView getWorkspaceView() {
        return workspaceView;
    }
}
