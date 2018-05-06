package org.connor.dpcrawler.dynamo;

import org.connor.dpcrawler.model.Post;

import java.util.List;

public class BlindExistenceValidator implements ExistenceValidator {
    @Override
    public boolean seenPosts(List<Post> posts) {
        return false;
    }
}
