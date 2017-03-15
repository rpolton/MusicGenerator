package me.shaftesbury;

import java.util.Arrays;

public class MajorScale extends EqualTemperedScale
{
    public static EqualTemperedScale create(final Note rootNote) { return new MajorScale(rootNote); }

    public MajorScale(final Note rootNote)
    {
        super(rootNote,Arrays.asList(Interval.Second, Interval.Second, Interval.MinorSecond, Interval.Second, Interval.Second, Interval.Second, Interval.MinorSecond));
    }

//    public static Note getNote(final MajorScale scale, final int index)
//    {
//
//
//    }
}
