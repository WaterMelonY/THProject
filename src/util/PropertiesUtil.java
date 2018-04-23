package util;

import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {

    private static  InputStream input=null;
    private static  Properties properties=null;

    static{
        try {
        	input = PropertiesUtil.class.getClassLoader().getResourceAsStream("data.properties");
            properties = new Properties();
            properties.load(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getValue(String key){

        return properties.getProperty(key);
    }
}
