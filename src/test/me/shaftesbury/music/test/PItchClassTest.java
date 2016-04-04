package me.shaftesbury.music.test;

import me.shaftesbury.PitchClass;
import me.shaftesbury.PitchClassInternal;
import me.shaftesbury.Interval;
import org.junit.Assert;
import org.junit.Test;

public class PitchClassTest
{
    @Test
    public void verifyPitchClassEqualsIsTheSameAsSame()
    {
        final PitchClass p = new PitchClass("A");

        final boolean cmp = p.equals(p);
        Assert.assertTrue(cmp);
        Assert.assertEquals(p,p);
        Assert.assertSame(p,p);
    }

    @Test
    public void verifyPitchClassReferenceEquals()
    {
        final PitchClass p1 = new PitchClass("A");
        final PitchClass p2 = new PitchClass("A");

        Assert.assertEquals(p1,p2);
        Assert.assertNotSame(p1,p2);
    }

    @Test
    public void verifyDissimilarPitchClassesAreNotEqual()
    {
        final PitchClass p1 = new PitchClass("A");
        final PitchClass p2 = new PitchClass("A#");

        Assert.assertNotEquals(p1,p2);
    }

    @Test
    public void verifyEnharmonicPitchClassesAreEqual()
    {
        final PitchClass p1 = new PitchClass("A#");
        final PitchClass p2 = new PitchClass("Bb");

        Assert.assertEquals(p1,p2);
    }

    @Test
    public void verifyAddingAStepProducesANewNoteOneHalfStepAbove()
    {
        final Interval interval = Interval.MinorSecond;
        final PitchClass pitchClass = new PitchClass(PitchClassInternal.A);

        final PitchClass result = PitchClass.addStep(interval, pitchClass);

        Assert.assertEquals(new PitchClass(PitchClassInternal.ASHARP), result);
    }
}
