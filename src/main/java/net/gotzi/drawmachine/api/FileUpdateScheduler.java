/*
	Author: Elias (Gotzi) Gottsbacher
	Copyright (c) 2023 Elias Gottsbacher
*/

package net.gotzi.drawmachine.api;

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

    /**
     * It takes a list of files and a list of files, and adds all the files in the first list to the second list
     *
     * @param files The array of files to be listed.
     * @param list The list of files to be returned
     */
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
