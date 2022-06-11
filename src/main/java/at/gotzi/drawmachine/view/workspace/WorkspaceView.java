package at.gotzi.drawmachine.view.workspace;

import at.gotzi.drawmachine.api.Action;
import at.gotzi.drawmachine.api.FileUpdateScheduler;
import at.gotzi.drawmachine.api.ThreadScheduler;
import at.gotzi.drawmachine.control.layout.HorizontalSplitLayout;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class WorkspaceView extends JPanel implements Workspace {
    private final WorkspaceTree workspaceTree;
    private final JLabel title;

    private String directory;

    private Action<File> openFile;

    private Action<File> closeFile;

    public WorkspaceView() {
        this.title = new JLabel();
        title.setText("Workspace");
        this.workspaceTree = new WorkspaceTree(new DefaultMutableTreeNode("..."));
        this.workspaceTree.getRoot().setAllowsChildren(true);

        buildLayout();
    }

    private void buildLayout() {
        this.setMinimumSize(new Dimension(200, 0));
        HorizontalSplitLayout horizontalSplitLayout = new HorizontalSplitLayout(this.title, this.workspaceTree);
        horizontalSplitLayout.setComponent1Size(25);
        setLayout(horizontalSplitLayout);
        add(this.title);
        add(this.workspaceTree);
    }

    private ThreadScheduler updateThread = null;

    @Override
    public synchronized void loadWorkspace(File file) {
        this.directory = file.getAbsolutePath();

        this.workspaceTree.reset();
        this.workspaceTree.getRoot().setUserObject(file.getName());
        if (file.listFiles() == null) return;

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

    @Override
    public String getDirectoryPath() {
        return this.directory;
    }

    private void loopFiles(File[] files, DefaultMutableTreeNode treeNode) {
        for (File f : files) {
            if (f.isDirectory()) loadDirectory(f, treeNode);
            else loadFile(f, treeNode);
        }
    }

    private void loadDirectory(File file, DefaultMutableTreeNode mutableTreeNode) {
        DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode(file.getName());
        mutableTreeNode.add(treeNode);
        treeNode.setAllowsChildren(true);
        if (file.listFiles() == null) return;

        loopFiles(Objects.requireNonNull(file.listFiles()), treeNode);
    }

    private void loadFile(File file, DefaultMutableTreeNode mutableTreeNode) {
        DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode(file.getName());
        mutableTreeNode.add(treeNode);
        treeNode.setAllowsChildren(false);
    }


    public void setCloseFile(Action<File> closeFile) {
        this.closeFile = closeFile;
    }

    public void setOpenFile(Action<File> openFile) {
        this.openFile = openFile;
    }
}