package ru.job4j.dream;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public final class SourcePath {
    private static StringImages stringImages = new StringImages();

    public static class StringImages {
        private final String pathToImages;

        public StringImages() {
            File file = new File("Resource.properties");
            Properties properties = new Properties();
            try {
                properties.load(new FileReader(file));
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.pathToImages = properties.getProperty("pathToDir");
        }

        public static String give() {
            return stringImages.pathToImages;
        }
    }
}
