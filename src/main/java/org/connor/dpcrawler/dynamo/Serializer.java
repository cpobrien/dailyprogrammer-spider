package org.connor.dpcrawler.dynamo;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.BatchGetItemOutcome;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.TableKeysAndAttributes;
import org.connor.dpcrawler.model.Post;
import org.connor.dpcrawler.config.Config;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Serializer {
    private static final String TABLE_NAME = "Post";
    private final DynamoDBMapper mapper;
    private final DynamoDB dynamoDB;

    private Serializer(DynamoDBMapper mapper, DynamoDB dynamoDB) {
        this.mapper = mapper;
        this.dynamoDB = dynamoDB;
    }

    public void serialize(List<Post> posts) {
        mapper.batchSave(posts);
    }

    public boolean seenAny(List<Post> posts) {
        TableKeysAndAttributes request = buildBatchPostRequest(posts);
        List<Item> postResults = queryBatchItemResults(request);
        return postResults.size() > 0;
    }

    private List<Item> queryBatchItemResults(TableKeysAndAttributes forumTableKeysAndAttributes) {
        BatchGetItemOutcome outcome = dynamoDB.batchGetItem(forumTableKeysAndAttributes);
        return outcome.getTableItems().get(TABLE_NAME);
    }

    @NotNull
    private TableKeysAndAttributes buildBatchPostRequest(List<Post> posts) {
        TableKeysAndAttributes batchPostRequest = new TableKeysAndAttributes(TABLE_NAME);
        batchPostRequest.addHashOnlyPrimaryKeys("Id", posts.stream().map(Post::getId).toArray());
        return batchPostRequest;
    }

    public static Serializer makeSerializer(Config config) {
        BasicAWSCredentials credentials = new BasicAWSCredentials(config.awsAccessKey, config.awsSecretKey);
        AWSStaticCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(credentials);
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .withRegion(Regions.US_EAST_1)
                .withCredentials(credentialsProvider)
                .build();
        return new Serializer(new DynamoDBMapper(client), new DynamoDB(client));
    }
}
