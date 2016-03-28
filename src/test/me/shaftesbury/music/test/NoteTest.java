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
}
