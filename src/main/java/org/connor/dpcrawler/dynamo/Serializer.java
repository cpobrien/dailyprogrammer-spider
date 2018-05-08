package org.connor.dpcrawler.dynamo;

import java.util.List;

public interface Serializer {
     <T> void serialize(List<T> objects);
     <T> void serialize(T object);
}
