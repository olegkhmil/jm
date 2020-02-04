package util;

import java.io.IOException;
import java.util.Properties;

public class PropertyReader {

    public static Properties getProperties(String fileName){
        Properties properties = new Properties();
        try {
             properties.load(PropertyReader.class.getClassLoader().getResourceAsStream(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}
