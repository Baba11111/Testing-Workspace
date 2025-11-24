package config;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private Properties properties;

    // Constructor to load properties file
    public ConfigReader() {
        try {
            FileInputStream fileInputStream = new FileInputStream("G:\\myprojects\\ACUtilities\\config.properties");
            properties = new Properties();
            properties.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not load config.properties file.");
        }
    }

    // Method to get property by key
    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    // Method to get integer property
    public int getIntProperty(String key) {
        return Integer.parseInt(properties.getProperty(key));
    }
    
    public String getUsername() {
        return properties.getProperty("username");
    }

    // Method to get the password from the config file
    public String getPassword() {
        return properties.getProperty("password");
    }

}