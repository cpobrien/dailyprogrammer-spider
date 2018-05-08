package org.connor.dpcrawler.serializer;

import org.connor.dpcrawler.dynamo.Serializer;

import java.util.List;

public class StubSerializer implements Serializer {
    @Override
    public <T> void serialize(List<T> objects) {

    }

    @Override
    public <T> void serialize(T object) {

    }
}
