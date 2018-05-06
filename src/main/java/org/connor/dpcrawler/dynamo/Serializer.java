package org.connor.dpcrawler.dynamo;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.connor.dpcrawler.config.Config;

import java.util.List;

public class Serializer {
    private final DynamoDBMapper mapper;

    public Serializer(Config config) {
        this(new DynamoDBMapper(DynamoDBClientCreator.create(config)));
    }

    public Serializer(DynamoDBMapper mapper) {
        this.mapper = mapper;
    }

    public void serialize(List<? extends Object> objects) {
        mapper.batchSave(objects);
    }
}
