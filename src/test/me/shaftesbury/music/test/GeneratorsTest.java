package me.shaftesbury.music.test;

import com.google.common.collect.Lists;
import me.shaftesbury.Generators;
import me.shaftesbury.Note;
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
}
