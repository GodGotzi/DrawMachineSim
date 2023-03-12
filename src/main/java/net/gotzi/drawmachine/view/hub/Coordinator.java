/*
	Author: Elias (Gotzi) Gottsbacher
	Copyright (c) 2023 Elias Gottsbacher
*/

package net.gotzi.drawmachine.view.hub;

import java.io.File;

public interface Coordinator<T, V extends FileView<T>> {

    V create(String name) throws Exception;

    V load(File file) throws Exception;

    void save(File file, Object t) throws Exception;
}
