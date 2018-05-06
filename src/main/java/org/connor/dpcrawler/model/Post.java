package org.connor.dpcrawler.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.Date;

@DynamoDBTable(tableName = "Post")
public class Post {
    private final String id;
    private final String title;
    private final String url;
    private final Date created;

    public Post(String id, String title, String url, Date created) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.created = created;
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

    @DynamoDBAttribute(attributeName = "Created")
    public Date getCreated() {
        return created;
    }
}
