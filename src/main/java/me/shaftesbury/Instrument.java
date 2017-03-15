package me.shaftesbury;

import com.google.common.collect.Lists;
import org.javatuples.Pair;

import java.util.Collections;
import java.util.List;

public class Instrument
{
    private final List<Pair<Double,Integer>> timbre;  // frequency ratio and volume
    // the volume for each component possibly should be an envelope and not an integer
    public Instrument(final Iterable<Pair<Double,Integer>> timbre)
    {
        this.timbre = Lists.newCopyOnWriteArrayList(timbre);
    }

    public List<Pair<Double,Integer>> getTimbre()
    {
        return Collections.unmodifiableList(timbre);
    }
}
