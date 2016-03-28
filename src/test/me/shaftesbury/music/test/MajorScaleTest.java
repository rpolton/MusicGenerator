package me.shaftesbury.music.test;

import com.google.common.collect.Lists;
import me.shaftesbury.MajorScale;
import me.shaftesbury.Note;
import me.shaftesbury.PitchClass;
import me.shaftesbury.PitchClassInternal;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class MajorScaleTest
{
    @Test
    public void createMajorScaleFromRootNote()
    {
        final Note rootNote = new Note(new PitchClass(PitchClassInternal.C),4);
        final MajorScale CMajorScale = new MajorScale(rootNote);

        final List<Note> notes = Lists.newArrayList();
        for(final Note note : CMajorScale)
        {
            notes.add(note);
        }

        Assert.assertArrayEquals(
                new Note[]{rootNote,new Note("D",4),new Note("E",4),new Note("F",4),new Note("G",4),new Note("A",4),new Note("B",4)},
                notes.toArray(new Note[0]));
    }
}
