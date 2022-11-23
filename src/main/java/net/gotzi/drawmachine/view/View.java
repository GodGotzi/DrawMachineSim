package net.gotzi.drawmachine.view;

import net.gotzi.drawmachine.control.UnderLayPanel;
import net.gotzi.drawmachine.handler.design.DesignColor;
import net.gotzi.drawmachine.handler.design.DesignHandler;
import net.gotzi.drawmachine.view.file.FileHubUnderLayPanel;
import net.gotzi.drawmachine.view.file.FileHubView;
import net.gotzi.drawmachine.view.workspace.Workspace;
import net.gotzi.drawmachine.view.workspace.WorkspaceView;

import javax.swing.*;
import java.awt.*;

public class View extends JSplitPane {

    private final DesignHandler designHandler;


    private FileHubView fileHubView;
    private WorkspaceView workspaceView;

    public View(DesignHandler designHandler) {
        this.designHandler = designHandler;
        //UIManager.put("TabbedPane.contentBorderInsets", new Insets(0, 0, 0, 0));
        //UIManager.put("TabbedPane.contentBorderColor", new Color(126, 60, 183));

        this.init();
    }

    private void init() {
        this.fileHubView = new FileHubView(this.designHandler);
        this.workspaceView = new WorkspaceView(this.designHandler);

        setOrientation(JSplitPane.HORIZONTAL_SPLIT);


        FileHubUnderLayPanel underLayPanel = new FileHubUnderLayPanel(this.fileHubView);
        this.designHandler.getDesignColorChanges(DesignColor.SECONDARY)
                .registerPossibleChange(color -> {
                    underLayPanel.setBorderColor(color);
                    underLayPanel.repaint();
                });

        setRightComponent(underLayPanel);
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