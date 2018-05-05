import config.Config;
import config.PropertiesParser;
import dynamo.Serializer;
import net.dean.jraw.RedditClient;
import reddit.Reddit;

import java.io.FileNotFoundException;

public class Application {
    public void run() {
        var config = getConfig();
        var redditClient = Reddit.buildRedditClient(config);
        var dynamoDBSerializer = Serializer.makeSerializer(config);
        new Crawler(redditClient, dynamoDBSerializer).crawlPosts();
    }

    private Config getConfig() {
        Config config = null;
        try {
            config = PropertiesParser.retrieveConfigFromProperties();
        } catch (FileNotFoundException e) {
            System.err.println("You're missing a config.properties file at the root of /r/dailyprogrammer crawler");
            System.exit(1);
        }
        return config;
    }

    public static void main(String[] args) {
        new Application().run();
    }

}
