package net.gotzi.drawmachine.view.workspace;

import net.gotzi.drawmachine.api.FileUpdateScheduler;
import net.gotzi.drawmachine.api.ThreadScheduler;
import net.gotzi.drawmachine.control.layout.HorizontalSplitLayout;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.io.File;
import java.util.*;
import java.util.List;

public class WorkspaceView extends JPanel implements Workspace {

    private final WorkspaceTree workspaceTree;
    private final JLabel title;
    private final Map<File, WorkspaceElement> fileMap;

    private String directory;

    public WorkspaceView() {
        this.title = new JLabel();
        title.setText("Workspace");
        this.workspaceTree = new WorkspaceTree(new DefaultMutableTreeNode("..."));
        this.workspaceTree.getRoot().setAllowsChildren(true);
        this.fileMap = new HashMap<>();

        buildLayout();
    }

    private void buildLayout() {
        this.setMinimumSize(new Dimension(200, 0));

        ScrollPane scrollPane = new ScrollPane();
        HorizontalSplitLayout horizontalSplitLayout = new HorizontalSplitLayout(this.title, scrollPane);
        horizontalSplitLayout.setComponent1Size(25);
        setLayout(horizontalSplitLayout);

        //scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBackground(Color.GRAY);
        scrollPane.setForeground(Color.GRAY);

        scrollPane.add(this.workspaceTree);
        colorRise();

        add(this.title);
        add(scrollPane);
    }

    private void colorRise() {
        setBackground(new Color(126, 60, 183));

        this.title.setForeground(Color.WHITE);
        this.workspaceTree.setBackground(Color.GRAY);

        DefaultTreeCellRenderer renderer =
                (DefaultTreeCellRenderer) this.workspaceTree.getCellRenderer();
        renderer.setBackgroundNonSelectionColor(Color.GRAY);
        renderer.setTextNonSelectionColor(Color.WHITE);
        renderer.setTextSelectionColor(Color.WHITE);
        renderer.setBackgroundSelectionColor(Color.LIGHT_GRAY);
        renderer.setBorderSelectionColor(Color.WHITE);
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
        WorkspaceFile workspaceFile = new WorkspaceFile(true, file.getName());
        //DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode(file.getName());

        mutableTreeNode.add(workspaceFile);
        workspaceFile.setAllowsChildren(false);
    }
}