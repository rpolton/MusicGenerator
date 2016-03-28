package me.shaftesbury.music.test;

import me.shaftesbury.PitchClass;
import me.shaftesbury.PitchClassInternal;
import me.shaftesbury.Step;
import org.junit.Assert;
import org.junit.Test;

public class PitchClassTest
{
    @Test
    public void verifyAddingAStepProducesANewNoteOneHalfStepAbove()
    {
        final Step step = Step.Half;
        final PitchClass pitchClass = new PitchClass(PitchClassInternal.A);

        final PitchClass result = PitchClass.addStep(step, pitchClass);

        Assert.assertEquals(new PitchClass(PitchClassInternal.ASHARP), result);
    }
}
