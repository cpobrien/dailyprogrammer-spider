package org.connor.dpcrawler.reddit;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import net.dean.jraw.models.Listing;
import net.dean.jraw.models.Submission;
import org.connor.dpcrawler.model.Post;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class MockReddit implements Reddit {
    private int getListingsAccessCount = 0;
    private int submissionToPostAccessCount = 0;
    private int getCollectAccessCount = 0;

    @NotNull
    @Override
    public Iterator<Listing<Submission>> getSubmissions() {
        getListingsAccessCount++;
        return Lists.newArrayList(new MockListing<Submission>()).iterator();
    }

    @Override
    public Post submissionToPost(Submission submission) {
        submissionToPostAccessCount++;
        return new Post("foo", "bar", "baz", Date.from(Instant.EPOCH));
    }

    @Override
    public List<Post> getCollect(Listing<Submission> nextPage) {
        getCollectAccessCount++;
        return ImmutableList.of();
    }
}
