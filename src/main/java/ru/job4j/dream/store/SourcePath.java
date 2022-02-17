package ru.job4j.dream.store;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SourcePath {
   private static String pathToImages;

    private SourcePath() {
        Properties properties = new Properties();
        InputStream in = SourcePath.class.getClassLoader()
                .getResourceAsStream("Resource.properties");
        try {
            properties.load((in));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.pathToImages = properties.getProperty("pathToDir");
    }

    public static String give() {
        new SourcePath();
        return pathToImages;
    }
}
