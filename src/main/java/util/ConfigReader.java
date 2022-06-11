package util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ConfigReader {

    private static Properties properties;
    //Use the map collection to store the configuration information under the properties file
    private static Map<String, String> configMap;
    private static String propFileName;

    //The configuration file is loaded only once
    static {
        properties = new Properties();
        configMap = new HashMap<>();
        propFileName = "config/configuration.properties";
        InputStream inputStream = null;
        try{
            inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(propFileName);
            if (inputStream != null) {
                properties.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }
            Enumeration en = properties.propertyNames();
            while (en.hasMoreElements()){
                String key = (String) en.nextElement();
                String value = properties.getProperty(key);
                configMap.put(key, value);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //Get the value in the configuration file
    public static String getPropertyValue(String key){
        return configMap.get(key);
    }
}
