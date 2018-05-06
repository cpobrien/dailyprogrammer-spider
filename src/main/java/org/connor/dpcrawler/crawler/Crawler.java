package org.connor.dpcrawler.crawler;

import net.dean.jraw.models.Listing;
import net.dean.jraw.models.Submission;
import org.connor.dpcrawler.config.Config;
import org.connor.dpcrawler.dynamo.DefaultExistenceValidator;
import org.connor.dpcrawler.dynamo.ExistenceValidator;
import org.connor.dpcrawler.model.Post;
import org.connor.dpcrawler.dynamo.Serializer;
import org.connor.dpcrawler.reddit.Reddit;

import java.util.List;

public class Crawler {
    private final Reddit client;
    private final Serializer serializer;
    private final ExistenceValidator existenceValidator;

    public Crawler(Config config) {
        this(Reddit.buildRedditClient(config), new Serializer(config), new DefaultExistenceValidator(config));
    }

    public Crawler(Reddit client, Serializer serializer, ExistenceValidator existenceValidator) {
        this.client = client;
        this.serializer = serializer;
        this.existenceValidator = existenceValidator;
    }

    public void crawlPosts() {
        for (Listing<Submission> nextPage : client.getListings()) {
            List<Post> posts = client.getCollect(nextPage);
            boolean readAnything = existenceValidator.seenPosts(posts);
            serializer.serialize(posts);
            if (readAnything) {
                break;
            }
        }
    }
}
