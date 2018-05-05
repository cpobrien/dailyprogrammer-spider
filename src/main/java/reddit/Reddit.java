package reddit;

import config.Config;
import net.dean.jraw.RedditClient;
import net.dean.jraw.http.NetworkAdapter;
import net.dean.jraw.http.OkHttpNetworkAdapter;
import net.dean.jraw.http.UserAgent;
import net.dean.jraw.oauth.Credentials;
import net.dean.jraw.oauth.OAuthHelper;

public class Reddit {
    public static RedditClient buildRedditClient(Config config) {
        UserAgent userAgent = new UserAgent("bot",
                "org.connor.dailyprog",
                "v0.1",
                config.redditUsername);
        NetworkAdapter adapter = new OkHttpNetworkAdapter(userAgent);
        Credentials credentials = Credentials.script(
                config.redditUsername,
                config.redditPassword,
                config.redditClientId,
                config.redditClientSecret
        );
        return OAuthHelper.automatic(adapter, credentials);
    }
}
