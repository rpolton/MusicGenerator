package me.shaftesbury.music.test;

import com.google.common.collect.Lists;
import me.shaftesbury.*;
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

    @Test
    public void verifyNextNoteUp()
    {
        final List<Note> input = Lists.newArrayList(new Note("A",3),new Note("B",3),new Note("C",4),new Note("D",4),new Note("E",4),new Note("F",4),new Note("G",4));
        final List<Note> expected = Lists.newArrayList(new Note("B",3),new Note("C",4),new Note("D",4),new Note("E",4),new Note("F",4),new Note("G",4),new Note("A",4));

        final Note middleC = new Note("C",4);
        final EqualTemperedScale CM = new MajorScale(middleC);

        final List<Note> output = Lists.newArrayList();
        for(final Note note : input)
        {
            final Note followingNote = CM.getFollowingNote(note);
            output.add(followingNote);
        }

        Assert.assertArrayEquals(expected.toArray(new Note[0]),output.toArray(new Note[0]));
    }

    @Test
    public void verifyNextNoteDown()
    {
        final List<Note> input = Lists.newArrayList(new Note("A",3),new Note("B",3),new Note("C",4),new Note("D",4),new Note("E",4),new Note("F",4),new Note("G",4));
        final List<Note> expected = Lists.newArrayList(new Note("G",3),new Note("A",3),new Note("B",3),new Note("C",4),new Note("D",4),new Note("E",4),new Note("F",4));

        final Note middleC = new Note("C",4);
        final EqualTemperedScale CM = new MajorScale(middleC);

        final List<Note> output = Lists.newArrayList();
        for(final Note note : input)
        {
            final Note followingNote = CM.getPrecedingNote(note);
            output.add(followingNote);
        }

        Assert.assertArrayEquals(expected.toArray(new Note[0]),output.toArray(new Note[0]));
    }
}
