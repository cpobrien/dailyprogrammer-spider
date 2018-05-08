package org.connor.dpcrawler.reddit;

import com.google.common.collect.ImmutableList;
import net.dean.jraw.models.Listing;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MockListing<T> extends Listing {
    @Nullable
    @Override
    public String getNextName() {
        return "Foo";
    }

    @Override
    public List getChildren() {
        return ImmutableList.of();
    }
}
