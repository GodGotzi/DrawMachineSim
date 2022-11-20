package net.gotzi.drawmachine.view;

import net.gotzi.drawmachine.view.file.FileHubView;
import net.gotzi.drawmachine.view.workspace.Workspace;
import net.gotzi.drawmachine.view.workspace.WorkspaceView;

import javax.swing.*;
import java.awt.*;

public class View extends JSplitPane {

    private FileHubView fileHubView;

    private WorkspaceView workspaceView;

    public View() {
        UIManager.put("TabbedPane.contentAreaColor ", new Color(126, 60, 183));
        UIManager.put("TabbedPane.selected", new Color(126, 60, 183));
        UIManager.put("TabbedPane.background", new Color(126, 60, 183));
        UIManager.put("TabbedPane.shadow", new Color(126, 60, 183));
        UIManager.put("TabbedPane.borderColor", new Color(126, 60, 183));
        UIManager.put("TabbedPane.darkShadow", new Color(126, 60, 183));
        UIManager.put("TabbedPane.light", new Color(126, 60, 183));
        UIManager.put("TabbedPane.highlight", new Color(126, 60, 183));
        UIManager.put("TabbedPane.focus", new Color(126, 60, 183));
        UIManager.put("TabbedPane.unselectedBackground", new Color(126, 60, 183));
        UIManager.put("TabbedPane.selectHighlight", new Color(126, 60, 183));
        UIManager.put("TabbedPane.tabAreaBackground", new Color(126, 60, 183));
        UIManager.put("TabbedPane.borderHightlightColor", new Color(126, 60, 183));
        //UIManager.put("TabbedPane.contentBorderInsets", new Insets(0, 0, 0, 0));
        //UIManager.put("TabbedPane.contentBorderColor", new Color(126, 60, 183));

        setBackground(Color.DARK_GRAY);

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
