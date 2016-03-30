package me.shaftesbury.music.test;

import com.google.common.collect.Lists;
import me.shaftesbury.Note;
import me.shaftesbury.utils.functional.Func;
import me.shaftesbury.utils.functional.Functional;
import org.javatuples.Pair;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class NoteTest
{
    @Test
    public void verifyEquals()
    {
        final Note c = new Note("C", 4);
        final Note c1 = new Note("C", 4);
        Assert.assertEquals(c,c1);
    }

    @Test
    public void verifyEqualsNegativeTest()
    {
        final Note c = new Note("C", 4);
        final Note c1 = new Note("C", 5);
        Assert.assertNotEquals(c,c1);
    }

    @Test
    public void verifyPitchClassEqualsIsTheSameAsSame()
    {
        final Note p = new Note("A", 4);

        final boolean cmp = p.equals(p);
        Assert.assertTrue(cmp);
        Assert.assertEquals(p,p);
        Assert.assertSame(p,p);
    }

    @Test
    public void verifyPitchClassReferenceEquals()
    {
        final Note p1 = new Note("A", 4);
        final Note p2 = new Note("A", 4);

        Assert.assertEquals(p1,p2);
        Assert.assertNotSame(p1,p2);
    }

    @Test
    public void verifyDissimilarPitchClassesAreNotEqual()
    {
        final Note p1 = new Note("A", 4);
        final Note p2 = new Note("A#", 4);

        Assert.assertNotEquals(p1,p2);
    }

    @Test
    public void verifyEnharmonicPitchClassesAreEqual()
    {
        final Note p1 = new Note("A#", 4);
        final Note p2 = new Note("Bb", 4);

        Assert.assertEquals(p1,p2);
    }


    @Test
    public void verifyIncrementNote()
    {
        final List<Note> input = Lists.newArrayList(new Note("A",3),new Note("B",3),new Note("C",4),new Note("G#",4));
        final List<Note> expected = Lists.newArrayList(new Note("A#",3),new Note("C",4),new Note("C#",4),new Note("A",4));

        final List<Note> output = Functional.toList(Functional.map(new Func<Note,Note>(){
            @Override
            public Note apply(final Note note) {
                return Note.incrementByHalfStep(note);
            }
        }, input));

//        AssertIterable.assertArrayEquals(expected,output);
        for(final Pair<Note,Note> pair:Functional.zip(expected, output))
            Assert.assertEquals(pair.getValue0(),pair.getValue1());
    }

    @Test
    public void verifyFreqOfReferenceNote()
    {
        final Note A440 = new Note("A",4);

        final double freq = Note.toFreq(A440);

        Assert.assertEquals(440.0, freq, 0.001);
    }

    @Test
    public void verifyFreqOfMiddleC()
    {
        final Note middleC = new Note("C",4);

        final double freq = Note.toFreq(middleC);

        Assert.assertEquals(261.626, freq, 0.001);
    }
}
