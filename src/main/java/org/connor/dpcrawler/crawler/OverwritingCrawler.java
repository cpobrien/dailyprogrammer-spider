package org.connor.dpcrawler.crawler;

import net.dean.jraw.models.Listing;
import net.dean.jraw.models.Submission;
import org.connor.dpcrawler.dynamo.Serializer;
import org.connor.dpcrawler.model.Post;
import org.connor.dpcrawler.reddit.Reddit;

import java.util.List;
import java.util.stream.Collectors;

public class OverwritingCrawler implements Crawler {
    private final Reddit client;
    private final Serializer serializer;

    public OverwritingCrawler(Reddit client, Serializer serializer) {
        this.client = client;
        this.serializer = serializer;
    }

    @Override
    public void crawlPosts() {
        for (Listing<Submission> nextPage : client.getListings()) {
            List<Post> posts = nextPage.getChildren()
                    .stream()
                    .map(client::submissionToPost)
                    .collect(Collectors.toList());
            serializer.serialize(posts);
        }
    }
}
