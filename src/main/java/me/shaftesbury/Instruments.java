package me.shaftesbury;

import com.google.common.collect.Lists;
import org.javatuples.Pair;

public final class Instruments
{
    public static Instrument stratOpenString()
    {
        return new Instrument(Lists.newArrayList(Pair.with(1.0,100), Pair.with(2.0,60), Pair.with(3.0,40), Pair.with(4.0,25),
                Pair.with(5.0,30), Pair.with(6.0,15), Pair.with(7.0, 10)));
    }

    public static Instrument stratWithTrebleCut()
    {
        return new Instrument(Lists.newArrayList(Pair.with(1.0,98), Pair.with(2.0,56), Pair.with(3.0,32), Pair.with(4.0,18),
                Pair.with(5.0,20), Pair.with(6.0,5), Pair.with(7.0, 2)));
    }

    public static Instrument stratOpenStringNeckPickup()
    {
        return new Instrument(Lists.newArrayList(Pair.with(1.0,100), Pair.with(2.0,60), Pair.with(3.0,40), // no 4th harmonic because it is 0 over the neck pickup
                Pair.with(5.0,30), Pair.with(6.0,15), Pair.with(7.0, 10)));
    }
}
