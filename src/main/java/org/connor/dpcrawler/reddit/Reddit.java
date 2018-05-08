package org.connor.dpcrawler.reddit;

import net.dean.jraw.models.Listing;
import net.dean.jraw.models.Submission;
import org.connor.dpcrawler.model.Post;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.List;

public interface Reddit {
    @NotNull
    Iterator getSubmissions();

    Post submissionToPost(Submission submission);

    List<Post> getCollect(Listing<Submission> nextPage);
}
