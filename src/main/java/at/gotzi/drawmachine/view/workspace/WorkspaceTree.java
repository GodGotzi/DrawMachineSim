package at.gotzi.drawmachine.view.workspace;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class WorkspaceTree extends JTree {

    private final DefaultMutableTreeNode root;

    public WorkspaceTree(DefaultMutableTreeNode root) {
        super(root);
        this.root = root;

        reset();
    }

    public void reset() {

        this.root.removeAllChildren();
        this.root.setUserObject("...");

        DefaultTreeModel model = (DefaultTreeModel) this.getModel();
        model.reload();
    }

    public DefaultMutableTreeNode getRoot() {
        return root;
    }
}
