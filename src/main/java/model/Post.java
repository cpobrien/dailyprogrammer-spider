package model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "model.Post")
public class Post {
    private final String id;
    private final String title;
    private final String url;

    public Post(String id, String title, String url) {
        this.id = id;
        this.title = title;
        this.url = url;
    }

    @DynamoDBHashKey(attributeName = "Id")
    public String getId() {
        return id;
    }

    @DynamoDBAttribute(attributeName = "Title")
    public String getTitle() {
        return title;
    }

    @DynamoDBAttribute(attributeName = "Url")
    public String getUrl() {
        return url;
    }
}
