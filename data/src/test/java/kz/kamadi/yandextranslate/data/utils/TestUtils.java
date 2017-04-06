package kz.kamadi.yandextranslate.data.utils;

import java.io.File;
import java.net.URL;

public class TestUtils {
    public static File getFileFromPath(Object obj, String fileName) {
        ClassLoader classLoader = obj.getClass().getClassLoader();
        URL resource = classLoader.getResource(fileName);
        return new File(resource.getPath());
    }
}
