package org.connor.dpcrawler.reddit;

import org.connor.dpcrawler.config.PropertiesParser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Properties;

public class TestPropertiesParser {
    public static final String VALUE = "bar";
    public static final String KEY = "foo";
    private PropertiesParser propertiesParser;

    @Before
    public void setup() {
        Properties properties = new Properties();
        properties.put(KEY, VALUE);
        propertiesParser = new PropertiesParser(properties);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetNotExistingProperty() {
        propertiesParser.getProperty("baz");
    }

    @Test
    public void testGetProperty() {
        var result = propertiesParser.getProperty(KEY);
        Assert.assertEquals(VALUE, result);
    }
}
