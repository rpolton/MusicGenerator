package me.shaftesbury.music.test;

import com.google.common.collect.Lists;
import me.shaftesbury.Generators;
import me.shaftesbury.MajorScale;
import me.shaftesbury.Note;
import me.shaftesbury.EqualTemperedScale;
import org.junit.Assert;
import org.junit.Test;

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

//    @Test
//    public void findNextScaleNoteDown()
//    {
//        final Note middleC = new Note("C",4);
//        final EqualTemperedScale CM = new MajorScale(middleC);
//
//        final Note output = Generators.nextNoteDownInKey(CM, Lists.newArrayList(middleC));
//
//        final Note expected = new Note("B",4);
//
//        Assert.assertEquals(expected,output);
//    }
}
