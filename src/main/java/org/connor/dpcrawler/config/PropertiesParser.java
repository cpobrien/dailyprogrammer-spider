package org.connor.dpcrawler.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertiesParser {
    private static final String PROPERTIES_PATH = "config.properties";
    private final Properties properties;

    public PropertiesParser(Properties properties) {
        this.properties = properties;
    }

    public static Config retrieveConfigFromProperties() throws FileNotFoundException, IllegalArgumentException {
        var properties = new Properties();
        try {
            properties.load(new FileInputStream(PROPERTIES_PATH));
        } catch (IOException e) {
            throw new FileNotFoundException("Cannot find properties file!");
        }
        return new PropertiesParser(properties).propertyToConfig();
    }

    private Config propertyToConfig() throws IllegalArgumentException {
        var awsPrivateKey = getProperty("aws.accessKey");
        var awsSecretKey = getProperty("aws.secretKey");
        var redditUsername = getProperty("reddit.username");
        var redditPassword = getProperty("reddit.password");
        var redditClientId = getProperty("reddit.clientId");
        var redditClientSecret= getProperty("reddit.clientSecret");
        return new Config(awsPrivateKey,
                awsSecretKey,
                redditUsername,
                redditPassword,
                redditClientId,
                redditClientSecret);
    }

    public String getProperty(String key) throws IllegalArgumentException {
        var value = properties.getProperty(key);
        if (value == null) {
            throw new IllegalArgumentException(String.format("Missing key %s!", key));
        }
        return value;
    }
}
