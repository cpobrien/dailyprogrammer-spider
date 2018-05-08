package org.connor.dpcrawler.dynamo;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import org.connor.dpcrawler.config.Config;

import java.util.List;

public class DefaultSerializer implements Serializer {
    private final DynamoDBMapper mapper;

    public DefaultSerializer(Config config) {
        this(new DynamoDBMapper(DynamoDBClientCreator.create(config)));
    }

    public DefaultSerializer(DynamoDBMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public <T> void serialize(List<T> objects) {
        mapper.batchSave(objects);
    }

    @Override
    public <T> void serialize(T object) {
        mapper.save(object);
    }
}
