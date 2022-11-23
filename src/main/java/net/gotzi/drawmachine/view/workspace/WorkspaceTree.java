package net.gotzi.drawmachine.view.workspace;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class WorkspaceTree extends JTree {

    private final DefaultMutableTreeNode root;

    public WorkspaceTree(DefaultMutableTreeNode root) {
        super(root);
        this.root = root;

        this.reset();
    }

    /**
     * Remove all the children of the root node, set the root node's user object to "...", and reload the tree.
     */
    public void reset() {

        this.root.removeAllChildren();
        this.root.setUserObject("...");

        this.reload();
    }

    /**
     * Reload the tree model.
     */
    public void reload() {
        DefaultTreeModel model = (DefaultTreeModel) this.getModel();
        model.reload();
    }

    public DefaultMutableTreeNode getRoot() {
        return root;
    }
}
