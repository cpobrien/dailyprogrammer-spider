package org.connor.dpcrawler.reddit;

import net.dean.jraw.RedditClient;
import net.dean.jraw.http.NetworkAdapter;
import net.dean.jraw.http.OkHttpNetworkAdapter;
import net.dean.jraw.http.UserAgent;
import net.dean.jraw.models.Listing;
import net.dean.jraw.models.Submission;
import net.dean.jraw.models.SubredditSort;
import net.dean.jraw.oauth.Credentials;
import net.dean.jraw.oauth.OAuthHelper;
import net.dean.jraw.pagination.DefaultPaginator;
import net.dean.jraw.references.SubredditReference;
import org.connor.dpcrawler.config.Config;
import org.connor.dpcrawler.model.Post;
import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Reddit {
    private final RedditClient client;

    public Reddit(RedditClient redditClient) {
        this.client = redditClient;
    }

    public Reddit(Config config) {
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

    @NotNull
    public DefaultPaginator<Submission> getListings() {
        SubredditReference dailyProgrammer = client.subreddit("dailyprogrammer");
        return dailyProgrammer
                .posts()
                .sorting(SubredditSort.NEW)
                .build();
    }

    public Post submissionToPost(Submission submission) {
        String id = submission.getId();
        String title = submission.getTitle();
        String url = submission.getUrl();
        Date created = submission.getCreated();
        return new Post(id, title, url, created);
    }

    public List<Post> getCollect(Listing<Submission> nextPage) {
        return nextPage.getChildren()
                .stream()
                .map(this::submissionToPost)
                .collect(Collectors.toList());
    }
}
