package net.gotzi.drawmachine.view.workspace;

import java.io.File;

public class WorkspaceFile extends WorkspaceElement {

    private final File file;

    public WorkspaceFile(File file, String s) {
        super(true, s);

        this.file = file;
    }

    @Override
    public void delete() {}

    @Override
    public void rename(String s) {}

    public File getFile() {
        return file;
    }

    @Override
    public boolean isFile() {
        return true;
    }
}
