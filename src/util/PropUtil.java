package util;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by root on 15-10-17.
 */
public class PropUtil {

    private  static final String CONFIG_FILE = "scgl.properties";
    
    private Properties prop;
    
	public static PropUtil instance = new PropUtil();

    public PropUtil() {
        prop = new Properties();
        try {
            prop.load(PropUtil.class.getClassLoader().getResourceAsStream(CONFIG_FILE));
        } catch (IOException e) {
            //log.error(e);
            System.out.println(e.toString());
        }
    }

    public String getProp(final String key) {
        return prop.getProperty(key);
    }

    public String getProp(final String key, final String defaultValue) {
        return prop.getProperty(key, defaultValue);
    }
 
}
