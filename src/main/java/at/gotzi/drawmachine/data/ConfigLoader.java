package at.gotzi.drawmachine.data;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ConfigLoader extends Loader<Map<String, String>> {

    private final InputStream inputStream;

    private Map<String, String> map;

    public ConfigLoader(InputStream inputStream) {
        this.inputStream = inputStream;
    }

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