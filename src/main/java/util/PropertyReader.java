package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyReader {

    public static Properties getProperties(){
        Properties properties = new Properties();
        try {
             properties.load(PropertyReader.class.getClassLoader().getResourceAsStream("db.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}
