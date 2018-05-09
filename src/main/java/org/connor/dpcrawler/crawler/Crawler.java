package org.connor.dpcrawler.crawler;

import net.dean.jraw.models.Listing;
import net.dean.jraw.models.Submission;
import org.connor.dpcrawler.config.Config;
import org.connor.dpcrawler.dynamo.DefaultExistenceValidator;
import org.connor.dpcrawler.dynamo.ExistenceValidator;
import org.connor.dpcrawler.dynamo.Serializer;
import org.connor.dpcrawler.model.Post;
import org.connor.dpcrawler.dynamo.DefaultSerializer;
import org.connor.dpcrawler.reddit.Reddit;
import org.connor.dpcrawler.reddit.DefaultReddit;

import java.util.Iterator;
import java.util.List;

public class Crawler {
    private final Reddit reddit;
    private final Serializer serializer;
    private final ExistenceValidator existenceValidator;

    public Crawler(Config config) {
        this(new DefaultReddit(config),
                new DefaultSerializer(config),
                new DefaultExistenceValidator(config));
    }

    public Crawler(Reddit reddit, Serializer serializer, ExistenceValidator existenceValidator) {
        this.reddit = reddit;
        this.serializer = serializer;
        this.existenceValidator = existenceValidator;
    }

    public void crawlPosts() {
        for (Iterator<Listing<Submission>> it = reddit.getSubmissions(); it.hasNext();) {
            Listing<Submission> nextPage = it.next();
            List<Post> posts = reddit.getSubmissionPostList(nextPage);
            boolean readAnything = existenceValidator.seenPosts(posts);
            serializer.serialize(posts);
            if (readAnything) {
                break;
            }
        }
    }
}
