package me.shaftesbury;

import java.util.Arrays;

public class MajorScale extends EqualTemperedScale
{
    public static EqualTemperedScale create(final Note rootNote) { return new MajorScale(rootNote); }

    public MajorScale(final Note rootNote)
    {
        super(rootNote,Arrays.asList(Step.Whole,Step.Whole,Step.Half,Step.Whole,Step.Whole,Step.Whole,Step.Half));
    }

//    public static Note getNote(final MajorScale scale, final int index)
//    {
//
//
//    }
}
