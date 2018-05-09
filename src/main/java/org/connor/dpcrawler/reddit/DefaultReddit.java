package org.connor.dpcrawler.reddit;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import kotlin.sequences.Sequence;
import net.dean.jraw.RedditClient;
import net.dean.jraw.http.OkHttpNetworkAdapter;
import net.dean.jraw.http.UserAgent;
import net.dean.jraw.models.Listing;
import net.dean.jraw.models.PublicContribution;
import net.dean.jraw.models.Submission;
import net.dean.jraw.models.SubredditSort;
import net.dean.jraw.oauth.Credentials;
import net.dean.jraw.oauth.OAuthHelper;
import net.dean.jraw.references.SubredditReference;
import net.dean.jraw.tree.CommentNode;
import net.dean.jraw.tree.RootCommentNode;
import org.connor.dpcrawler.config.Config;
import org.connor.dpcrawler.config.PropertiesParser;
import org.connor.dpcrawler.model.Post;
import org.connor.dpcrawler.model.User;
import org.jetbrains.annotations.NotNull;

import java.io.FileNotFoundException;
import java.util.*;
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

    public Set<String> blah() {
        Set<String> authors = new HashSet<>();
        CommentNode commentNodeSequence = client.submission("589txl").comments();
        Deque<CommentNode> deque = new ArrayDeque<>();
        deque.add(commentNodeSequence);
        while (!deque.isEmpty()) {
            CommentNode node = deque.pop();
            PublicContribution subject = node.getSubject();
            String author = subject.getAuthor();
            authors.add(author);
            deque.addAll(node.getReplies());
        }
        return authors;
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
    public List<Post> getSubmissionPostList(Listing<Submission> nextPage) {
        return nextPage.getChildren()
                .stream()
                .map(this::submissionToPost)
                .collect(Collectors.toList());
    }

    public static void main(String[] args) throws FileNotFoundException {
        DefaultReddit reddit = new DefaultReddit(PropertiesParser.retrieveConfigFromProperties());
        System.out.println(reddit.blah());
    }
}
