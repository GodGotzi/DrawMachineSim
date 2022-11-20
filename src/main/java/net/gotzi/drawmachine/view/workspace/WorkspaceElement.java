package net.gotzi.drawmachine.view.workspace;

import javax.swing.tree.DefaultMutableTreeNode;

public abstract class WorkspaceElement extends DefaultMutableTreeNode {

    private final boolean file;

    public WorkspaceElement(boolean file, String s) {
        super(s);
        this.file = file;
    }

    public abstract void delete();

    public abstract void rename(String s);

    public boolean isFile() {
        return this.file;
    }
}
