package org.connor.dpcrawler.config;

public class Config {
    public final String awsAccessKey;
    public final String awsSecretKey;
    public final String redditUsername;
    public final String redditPassword;
    public final String redditClientId;
    public final String redditClientSecret;

    public Config(String awsPrivateKey,
                  String awsSecretKey,
                  String redditUsername,
                  String redditPassword,
                  String redditClientId,
                  String redditClientSecret) {
        this.awsAccessKey = awsPrivateKey;
        this.awsSecretKey = awsSecretKey;
        this.redditUsername = redditUsername;
        this.redditPassword = redditPassword;
        this.redditClientId = redditClientId;
        this.redditClientSecret = redditClientSecret;
    }
}
