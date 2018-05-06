package org.connor.dpcrawler.dynamo;

import org.connor.dpcrawler.model.Post;

import java.util.List;

public interface ExistenceValidator {
    boolean seenPosts(List<Post> posts);
}
