/*
	Author: Elias (Gotzi) Gottsbacher
	Copyright (c) 2023 Elias Gottsbacher
*/

package net.gotzi.drawmachine.view.workspace;

import java.io.File;

public interface Workspace {
    void loadWorkspace(File file);

    String getDirectoryPath();

}
