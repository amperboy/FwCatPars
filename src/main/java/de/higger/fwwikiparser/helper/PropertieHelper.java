package de.higger.fwwikiparser.helper;

import java.io.InputStream;
import java.util.Properties;

public class PropertieHelper {

	private static final String CONFIG_FILENAME = "config.properties";

	private static PropertieHelper instance = new PropertieHelper();

	private Properties properties = new Properties();

	private PropertieHelper() {
		InputStream is = getClass().getClassLoader().getResourceAsStream(CONFIG_FILENAME);
		try {
			properties.load(is);
		} catch (Exception e) {
			throw new RuntimeException(e.getCause());
		}
	}

	public static PropertieHelper getPropertieHelperInstance() {
		return instance;
	}

	public String getProperty(String key) {
		return getProperty(key, null);
	}

	public String getProperty(String key, String defaultValue) {
		
		String value = properties.getProperty(key, defaultValue);
		
		return value;
	}
	
	public String getBaseURL() {
		return properties.getProperty("wiki.url.base");
	}

}
