package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyReader {

    public static Properties getProperties(String propFile){
        Properties properties = new Properties();
        try {
             properties.load(new FileInputStream(propFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}
