package me.shaftesbury.music.test;

import com.google.common.collect.Lists;
import me.shaftesbury.EqualTemperedScale;
import me.shaftesbury.Generators;
import me.shaftesbury.MajorScale;
import me.shaftesbury.Note;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Random;

public class GeneratorsTest
{
    @Test
    public void findNextChromaticNoteUp()
    {
        final Note A440 = new Note("A",4);
        final Note output = Generators.nextNoteChromaticUp(Lists.newArrayList(A440));

        final Note expected = new Note("A#",4);

        Assert.assertEquals(expected,output);
    }

    @Test
    public void findNextChromaticNoteDown()
    {
        final Note A440 = new Note("A",4);
        final Note output = Generators.nextNoteChromaticDown(Lists.newArrayList(A440));

        final Note expected = new Note("G#",4);

        Assert.assertEquals(expected,output);
    }

    @Test
    public void findNextScaleNoteUp()
    {
        final Note middleC = new Note("C",4);
        final EqualTemperedScale CM = new MajorScale(middleC);

        final Note output = Generators.nextNoteUpInKey(CM, Lists.newArrayList(middleC));

        final Note expected = new Note("D",4);

        Assert.assertEquals(expected,output);
    }

    @Test
    public void findNextScaleNoteDown()
    {
        final Note middleC = new Note("C",4);
        final EqualTemperedScale CM = new MajorScale(middleC);

        final Note output = Generators.nextNoteDownInKey(CM, Lists.newArrayList(middleC));

        final Note expected = new Note("B",3);

        Assert.assertEquals(expected,output);
    }

    @Test
    public void nextNoteConstantDissonanceTest()
    {
        final List<Note> notes = Lists.newArrayList();
        for(int i=0;i<10;++i) {
            final Note generatedNote = Generators.nextNoteConstantDissonance(new Random(), 5, 2.5, 0.5, Lists.newArrayList(new Note("A", 4)));
            notes.add(generatedNote);
            System.out.println(generatedNote);
        }

        for(final Note note : notes)
            System.out.print(note+" ");
    }
}
