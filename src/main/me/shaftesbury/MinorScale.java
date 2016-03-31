package me.shaftesbury;

import java.util.Arrays;

public class MinorScale extends EqualTemperedScale
{
    public static EqualTemperedScale create(final Note rootNote) { return new MinorScale(rootNote); }

    public MinorScale(final Note rootNote)
    {
        super(rootNote,Arrays.asList(Step.Whole,Step.Half,Step.Whole,Step.Whole,Step.Half,Step.Whole,Step.Whole));
    }

//    public static Note getNote(final MinorScale scale, final int index)
//    {
//
//
//    }
}
