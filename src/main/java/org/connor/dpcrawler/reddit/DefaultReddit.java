package org.connor.dpcrawler.reddit;

import net.dean.jraw.RedditClient;
import net.dean.jraw.http.OkHttpNetworkAdapter;
import net.dean.jraw.http.UserAgent;
import net.dean.jraw.models.Listing;
import net.dean.jraw.models.Submission;
import net.dean.jraw.models.SubredditSort;
import net.dean.jraw.oauth.Credentials;
import net.dean.jraw.oauth.OAuthHelper;
import net.dean.jraw.references.SubredditReference;
import org.connor.dpcrawler.config.Config;
import org.connor.dpcrawler.model.Post;
import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultReddit implements Reddit {
    private final RedditClient client;

    public DefaultReddit(RedditClient redditClient) {
        this.client = redditClient;
    }

    public DefaultReddit(Config config) {
        this(OAuthHelper.automatic(
                new OkHttpNetworkAdapter(
                        new UserAgent("bot",
                                "org.connor.dailyprog",
                                "v0.1",
                                config.redditUsername)),
                Credentials.script(
                        config.redditUsername,
                        config.redditPassword,
                        config.redditClientId,
                        config.redditClientSecret
                )));
    }

    @Override
    @NotNull
    public Iterator<Listing<Submission>> getSubmissions() {
        SubredditReference dailyProgrammer = client.subreddit("dailyprogrammer");
        return dailyProgrammer
                .posts()
                .sorting(SubredditSort.NEW)
                .build()
                .iterator();
    }

    @Override
    public Post submissionToPost(Submission submission) {
        String id = submission.getId();
        String title = submission.getTitle();
        String url = submission.getUrl();
        Date created = submission.getCreated();
        return new Post(id, title, url, created);
    }

    @Override
    public List<Post> getCollect(Listing<Submission> nextPage) {
        return nextPage.getChildren()
                .stream()
                .map(this::submissionToPost)
                .collect(Collectors.toList());
    }
}
