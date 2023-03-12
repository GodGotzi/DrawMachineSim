/*
	Author: Elias (Gotzi) Gottsbacher
	Copyright (c) 2023 Elias Gottsbacher
*/

package net.gotzi.drawmachine.view.workspace;

import net.gotzi.drawmachine.api.FileUpdateScheduler;
import net.gotzi.drawmachine.api.ThreadScheduler;
import net.gotzi.drawmachine.control.DimensionConstants;
import net.gotzi.drawmachine.control.layout.HorizontalSplitLayout;
import net.gotzi.drawmachine.handler.design.DesignColor;
import net.gotzi.drawmachine.handler.design.DesignHandler;
import net.gotzi.drawmachine.view.hub.FileHubView;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.*;
import java.util.List;

public class WorkspaceView extends JPanel implements Workspace {

    private final FileHubView fileHubView;
    private final WorkspaceTree workspaceTree;
    private final JLabel title;
    private final DesignHandler designHandler;
    private final JScrollPane scrollPane;
    private String directory;
    private ThreadScheduler updateThread = null;

    public WorkspaceView(DesignHandler designHandler, FileHubView fileHubView) {
        this.title = new JLabel();
        this.designHandler = designHandler;
        this.workspaceTree = new WorkspaceTree(new DefaultMutableTreeNode("..."));
        this.workspaceTree.getRoot().setAllowsChildren(true);
        this.fileHubView = fileHubView;
        this.scrollPane = new JScrollPane(this.workspaceTree,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        this.workspaceTree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                doMouseClicked(e);
            }
        });

        buildLayout();
    }

    /**
     * This function sets the minimum size of the panel, sets the title, sets the background color, sets the background
     * color of the tree, adds the title and scroll pane to the panel, and sets the layout of the panel.
     */
    private void buildLayout() {
        this.setMinimumSize(DimensionConstants.getConstantDimension("workspace.view.min"));

        this.title.setText("Workspace");
        this.title.setFont(this.title.getFont().deriveFont(Font.BOLD));
        this.title.setForeground(Color.WHITE);
        this.title.setHorizontalAlignment(JLabel.CENTER);

        this.designHandler.getDesignColorChanges(DesignColor.SECONDARY)
                .registerPossibleChange(this::setBackground);

        add(this.title);
        add(this.scrollPane);

        HorizontalSplitLayout horizontalSplitLayout = new HorizontalSplitLayout(this.title, scrollPane);
        horizontalSplitLayout.setComponent1Size(25);

        this.setLayout(horizontalSplitLayout);
    }

    /**
     * It loads the workspace and starts a thread that checks for changes in the workspace
     *
     * @param file The file to load the workspace from
     */
    @Override
    public synchronized void loadWorkspace(File file) {
        this.directory = file.getAbsolutePath();

        this.workspaceTree.reset();
        this.workspaceTree.getRoot().setUserObject(file.getName());

        if (file.listFiles() == null) {
            this.workspaceTree.expandPath(new TreePath(this.workspaceTree.getRoot().getPath()));
            this.workspaceTree.reload();

            return;
        }

        this.loopFiles(Objects.requireNonNull(file.listFiles()), this.workspaceTree.getRoot());
        this.workspaceTree.expandPath(new TreePath(this.workspaceTree.getRoot().getPath()));

        if (updateThread != null) updateThread.stop();
        this.updateThread = new FileUpdateScheduler(file.listFiles(), file.getAbsolutePath()) {
            @Override
            public void run() {
                File newDir = new File(getPath());
                List<File> fileCompare = new LinkedList<>();

                if (file.exists()) {
                    if (newDir.listFiles() != null && Objects.requireNonNull(newDir.listFiles()).length != 0) {
                        this.listAllFiles(Objects.requireNonNull(newDir.listFiles()), fileCompare);
                    }
                }

                if (changed(fileCompare))
                    loadWorkspace(file);

                this.sleep(100);
            }
        };

        this.updateThread.start();
    }

    /**
     * For each file in the array of files, if the file is a directory, load the directory, otherwise load the file.
     *
     * @param files The files to loop through
     * @param treeNode The node to add the files to.
     */
    private void loopFiles(File[] files, DefaultMutableTreeNode treeNode) {
        for (File f : files) {
            if (f.isDirectory()) loadDirectory(f, treeNode);
            else loadFile(f, treeNode);
        }
    }

    /**
     * It takes a file and a mutable tree node, creates a new mutable tree node with the file's name, adds it to the
     * mutable tree node, sets it to allow children, and if the file has children, loops through them and adds them to the
     * tree node
     *
     * @param file The file to be loaded
     * @param mutableTreeNode The node that will be added to the tree.
     */
    private void loadDirectory(File file, DefaultMutableTreeNode mutableTreeNode) {
        WorkspaceDir workspaceDir = new WorkspaceDir(false, file.getName());

        //DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode(file.getName());
        mutableTreeNode.add(workspaceDir);
        workspaceDir.setAllowsChildren(true);
        if (file.listFiles() == null) return;

        loopFiles(Objects.requireNonNull(file.listFiles()), workspaceDir);
    }

    /**
     * If the file is a directory, then add a new node to the tree, and recursively call this function on all the files in
     * the directory.
     *
     * @param file The file to be added to the tree.
     * @param mutableTreeNode The parent node to which the new node will be added.
     */
    private void loadFile(File file, DefaultMutableTreeNode mutableTreeNode) {
        WorkspaceFile workspaceFile = new WorkspaceFile(file, file.getName());
        //DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode(file.getName());

        mutableTreeNode.add(workspaceFile);
        workspaceFile.setAllowsChildren(false);
    }

    void doMouseClicked(MouseEvent me) {
        if (me.getClickCount() == 2) {
            TreePath tp = this.workspaceTree.getPathForLocation(me.getX(), me.getY());
            if (tp != null) {
                TreeNode treeNode = ((TreeNode)tp.getLastPathComponent());

                if (treeNode instanceof WorkspaceElement workspaceElement) {
                    if (workspaceElement.isFile() && workspaceElement instanceof WorkspaceFile workspaceFile) {
                        this.fileHubView.openFilePage(workspaceFile.getFile());
                    }
                }
            }
        }
    }

    @Override
    public String getDirectoryPath() {
        return this.directory;
    }
}