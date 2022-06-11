package at.gotzi.drawmachine.api;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public abstract class FileUpdateScheduler extends ThreadScheduler {

    private final List<File> allFiles;

    private final String path;

    public FileUpdateScheduler(File[] files, String path) {
        this.allFiles = new LinkedList<>();
        this.path = path;

        this.listAllFiles(files, this.allFiles);
    }

    public void listAllFiles(File[] files, List<File> list) {
        for (File f : files) {
            list.add(f);

            if (f.isDirectory() && f.listFiles() != null && Objects.requireNonNull(f.listFiles()).length != 0) {
                listAllFiles(Objects.requireNonNull(f.listFiles()), list);
            }
        }
    }

    public String getPath() {
        return path;
    }

    public synchronized boolean changed(List<File> list) {
        return !this.allFiles.equals(list);
    }
}
