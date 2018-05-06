package org.connor.dpcrawler.dynamo;

import com.amazonaws.services.dynamodbv2.document.BatchGetItemOutcome;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.TableKeysAndAttributes;
import org.connor.dpcrawler.config.Config;
import org.connor.dpcrawler.model.Post;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DefaultExistenceValidator implements ExistenceValidator {
    private static final String TABLE_NAME = "Post";
    private final DynamoDB dynamoDB;

    public DefaultExistenceValidator(Config config) {
        this(new DynamoDB(DynamoDBClientCreator.create(config)));
    }

    public DefaultExistenceValidator(DynamoDB dynamoDB) {
        this.dynamoDB = dynamoDB;
    }

    @Override
    public boolean seenPosts(List<Post> posts) {
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
}