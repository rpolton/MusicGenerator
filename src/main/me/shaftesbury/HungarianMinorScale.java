package me.shaftesbury;

import java.util.Arrays;

public class HungarianMinorScale extends EqualTemperedScale
{
    public static EqualTemperedScale create(final Note rootNote) { return new HungarianMinorScale(rootNote); }

    public HungarianMinorScale(final Note rootNote)
    {
        super(rootNote,Arrays.asList(Interval.Second, Interval.MinorSecond, Interval.MinorThird, Interval.MinorSecond, Interval.MinorSecond, Interval.MinorThird, Interval.MinorSecond));
    }

//    public static Note getNote(final MajorScale scale, final int index)
//    {
//
//
//    }
}
