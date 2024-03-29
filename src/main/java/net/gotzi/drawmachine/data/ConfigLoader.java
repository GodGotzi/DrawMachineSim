/*
	Author: Elias (Gotzi) Gottsbacher
	Copyright (c) 2023 Elias Gottsbacher
*/

package net.gotzi.drawmachine.data;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ConfigLoader extends Loader<Map<String, String>> {

    private final InputStream inputStream;

    private Map<String, String> map;

    public ConfigLoader(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    /**
     * It reads a file line by line and puts the key value pairs into a map.
     */
    @Override
    public void load() {
        this.map = new HashMap<>();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(inputStreamReader);
        reader.lines().forEach(line -> map.put(line.split("=")[0], line.split("=")[1]));
    }

    @Override
    public Map<String, String> getResult() {
        return map;
    }
}