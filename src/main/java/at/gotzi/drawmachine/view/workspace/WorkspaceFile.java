package at.gotzi.drawmachine.view.workspace;

public class WorkspaceFile extends WorkspaceElement {

    public WorkspaceFile(boolean file, String s) {
        super(file, s);
    }

    @Override
    public void delete() {

    }

    @Override
    public void rename(String s) {

    }

    @Override
    public boolean isFile() {
        return true;
    }
}
