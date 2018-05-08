package org.connor.dpcrawler.model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.google.common.collect.Lists;
import org.connor.dpcrawler.config.PropertiesParser;
import org.connor.dpcrawler.dynamo.DefaultSerializer;
import org.connor.dpcrawler.dynamo.Serializer;

import java.io.FileNotFoundException;
import java.util.List;

@DynamoDBTable(tableName = "User")
public class User {
    private final String name;
    private final Score score;
    private final List<Entry> entries;

    public User(String name, Score score, List<Entry> entries) {
        this.name = name;
        this.score = score;
        this.entries = entries;
    }

    @DynamoDBHashKey(attributeName = "Name")
    public String getName() {
        return name;
    }

    @DynamoDBAttribute(attributeName = "Score")
    public Score getScore() {
        return score;
    }

    @DynamoDBAttribute(attributeName = "Entries")
    public List<Entry> getEntries() {
        return entries;
    }

    @DynamoDBDocument
    public static class Score {
        private final int hardsSolved;
        private final int mediumsSolved;
        private final int easiesSolved;

        public Score(int hardsSolved, int mediumsSolved, int easiesSolved) {
            this.hardsSolved = hardsSolved;
            this.mediumsSolved = mediumsSolved;
            this.easiesSolved = easiesSolved;
        }

        public int getHardsSolved() {
            return hardsSolved;
        }

        public int getMediumsSolved() {
            return mediumsSolved;
        }

        public int getEasiesSolved() {
            return easiesSolved;
        }
    }

    @DynamoDBDocument
    public static class Entry {
        private final String content;

        public Entry(String content) {
            this.content = content;
        }

        public String getContent() {
            return content;
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        Serializer serializer = new DefaultSerializer(PropertiesParser.retrieveConfigFromProperties());
        Score score = new Score(0, 0, 0);
        List<Entry> entries = Lists.newArrayList(new Entry("blah"));
        User user = new User("foo", score, entries);
        serializer.serialize(user);
    }
}
