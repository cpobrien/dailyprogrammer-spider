package org.connor.dpcrawler;

import org.connor.dpcrawler.config.Config;
import org.connor.dpcrawler.config.PropertiesParser;
import org.connor.dpcrawler.crawler.OverwritingCrawler;
import org.connor.dpcrawler.dynamo.Serializer;
import org.connor.dpcrawler.crawler.DefaultCrawler;
import org.connor.dpcrawler.reddit.Reddit;

import java.io.FileNotFoundException;

public class Application {
    public void run() {
        var config = getConfig();
        var reddit = Reddit.buildRedditClient(config);
        var dynamoDBSerializer = Serializer.makeSerializer(config);
        new OverwritingCrawler(reddit, dynamoDBSerializer).crawlPosts();
    }

    private Config getConfig() {
        Config config = null;
        try {
            config = PropertiesParser.retrieveConfigFromProperties();
        } catch (FileNotFoundException e) {
            System.err.println("You're missing a org.connor.dpcrawler.config.properties file at the root of /r/dailyprogrammer crawler");
            System.exit(1);
        }
        return config;
    }

    public static void main(String[] args) {
        new Application().run();
    }

}
