package me.shaftesbury.music.test;

import me.shaftesbury.MajorScale;
import me.shaftesbury.Note;
import me.shaftesbury.PitchClass;
import me.shaftesbury.PitchClassInternal;
import org.junit.Test;

public class MajorScaleTest
{
    @Test
    public void createMajorScaleFromRootNote()
    {
        final Note rootNote = new Note(new PitchClass(PitchClassInternal.C),4);
        final MajorScale CMajorScale = new MajorScale(rootNote);

        for(final Note note : CMajorScale)
        {
            System.out.println(note);
        }
    }
}
