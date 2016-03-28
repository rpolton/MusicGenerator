package me.shaftesbury;

import java.util.Arrays;

public class HungarianMinorScale extends Scale
{
    public static Scale create(final Note rootNote) { return new HungarianMinorScale(rootNote); }

    public HungarianMinorScale(final Note rootNote)
    {
        super(rootNote,Arrays.asList(Step.Whole,Step.Half,Step.AugSecond,Step.Half,Step.Half,Step.AugSecond,Step.Half));
    }

//    public static Note getNote(final MajorScale scale, final int index)
//    {
//
//
//    }
}
