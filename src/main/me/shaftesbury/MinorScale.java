package me.shaftesbury;

import java.util.Arrays;

public class MinorScale extends EqualTemperedScale
{
    public static EqualTemperedScale create(final Note rootNote) { return new MinorScale(rootNote); }

    public MinorScale(final Note rootNote)
    {
        super(rootNote,Arrays.asList(Interval.Second, Interval.MinorSecond, Interval.Second, Interval.Second, Interval.MinorSecond, Interval.Second, Interval.Second));
    }

//    public static Note getNote(final MinorScale scale, final int index)
//    {
//
//
//    }
}
