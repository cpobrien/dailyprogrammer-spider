package org.connor.dpcrawler.crawler;

import org.connor.dpcrawler.dynamo.AffirmExistenceValidator;
import org.connor.dpcrawler.dynamo.DenyExistenceValidator;
import org.connor.dpcrawler.reddit.MockReddit;
import org.connor.dpcrawler.serializer.StubSerializer;
import org.junit.Before;

public class CrawlerTest {
    private MockReddit reddit;
    private StubSerializer serializer;

    @Before
    public void setup() {
        reddit = new MockReddit();
        serializer = new StubSerializer();
    }

    private Crawler createBlindExistenceCrawler() {
        return new Crawler(reddit, serializer, new DenyExistenceValidator());
    }

    private Crawler createAffirmExistenceCrawler() {
        return new Crawler(reddit, serializer, new AffirmExistenceValidator());
    }
}
