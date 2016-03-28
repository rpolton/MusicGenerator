package me.shaftesbury.music.test;

import com.google.common.collect.Lists;
import me.shaftesbury.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class MinorScaleTest
{
    @Test
    public void createMinorScaleFromRootNote()
    {
        final Note rootNote = new Note(new PitchClass(PitchClassInternal.A),3);
        final MinorScale AMinorScale = new MinorScale(rootNote);

        final List<Note> notes = Lists.newArrayList();
        for(final Note note : AMinorScale)
        {
            notes.add(note);
        }

        Assert.assertArrayEquals(
                new Note[]{rootNote,new Note("B",3),new Note("C",4),new Note("D",4),new Note("E",4),new Note("F",4),new Note("G",4)},
                notes.toArray(new Note[0]));
    }
}
