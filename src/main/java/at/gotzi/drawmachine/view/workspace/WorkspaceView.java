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
        DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode(file.getName());
        mutableTreeNode.add(treeNode);
        treeNode.setAllowsChildren(true);
        if (file.listFiles() == null) return;

        loopFiles(Objects.requireNonNull(file.listFiles()), treeNode);
    }

    /**
     * If the file is a directory, then add a new node to the tree, and recursively call this function on all the files in
     * the directory.
     *
     * @param file The file to be added to the tree.
     * @param mutableTreeNode The parent node to which the new node will be added.
     */
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