/*
	Author: Elias (Gotzi) Gottsbacher
	Copyright (c) 2023 Elias Gottsbacher
*/

package net.gotzi.drawmachine.control;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class DimensionConstants {

    private static final Map<String, Dimension> dimensionMap = new HashMap<>();

    public static void load() throws IOException {
        String[] split;
        int width;
        int height;

        InputStream inputStream = DimensionConstants.class.getClassLoader().getResourceAsStream("dimension.properties");
        Properties properties = new Properties();
        properties.load(inputStream);

        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            if (entry.getValue() instanceof String value && entry.getKey() instanceof String key) {
                split = value.split(";");
                width = Integer.parseInt(split[0]);
                height = Integer.parseInt(split[1]);
                dimensionMap.put(key, new Dimension(width, height));
            }
        }
    }

    public static Dimension getConstantDimension(String key) {
        return dimensionMap.get(key);
    }
}
