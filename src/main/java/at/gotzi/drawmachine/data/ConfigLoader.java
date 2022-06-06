package at.gotzi.drawmachine.data;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ConfigLoader extends ILoader<Map<String, String>> {

    private final InputStream inputStream;

    public ConfigLoader(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public Map<String, String> getResult() {
        final Map<String, String> map = new HashMap<>();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(inputStreamReader);
        reader.lines().forEach(line -> map.put(line.split("=")[0], line.split("=")[1]));
        return map;
    }
}