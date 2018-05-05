import dynamo.Serializer;
import model.Post;
import net.dean.jraw.RedditClient;
import net.dean.jraw.models.Listing;
import net.dean.jraw.models.Submission;
import net.dean.jraw.models.SubredditSort;
import net.dean.jraw.pagination.DefaultPaginator;
import net.dean.jraw.references.SubredditReference;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

class Crawler {
    private final RedditClient client;
    private final Serializer serializer;

    public Crawler(RedditClient client, Serializer serializer) {
        this.client = client;
        this.serializer = serializer;
    }

    public void crawlPosts() {
        for (Listing<Submission> nextPage : getListings()) {
            List<Post> posts = nextPage.getChildren()
                    .stream()
                    .map(this::submissionToPost)
                    .collect(Collectors.toList());
            boolean readAnything = serializer.seenAny(posts);
            serializer.serialize(posts);
            if (readAnything) {
                break;
            }
        }
    }

    private Post submissionToPost(Submission submission) {
        String id = submission.getId();
        String title = submission.getTitle();
        String url = submission.getUrl();
        return new Post(id, title, url);
    }

    @NotNull
    private DefaultPaginator<Submission> getListings() {
        SubredditReference dailyProgrammer = client.subreddit("dailyprogrammer");
        return dailyProgrammer
                .posts()
                .sorting(SubredditSort.NEW)
                .build();
    }

}
