package me.shaftesbury.music.test;

import me.shaftesbury.Note;
import me.shaftesbury.SMPCFunctions;
import org.junit.Assert;
import org.junit.Test;

public class SMPCFunctionsTest
{
    @Test
    public void VerifyIntervalBetween()
    {
        final double A440=440.0;
        final double middleC = Note.toFreq(new Note("C",4));

        final double v = SMPCFunctions.intervalBetween(middleC, A440);
        Assert.assertEquals(9.0,v,0.001);
    }

    @Test
    public void verifyDissonance()
    {
        final double A440=440.0;
        final double middleC = Note.toFreq(new Note("C",4));

        final double dissonance = SMPCFunctions.dissonance(middleC,A440);
        Assert.assertEquals(0.5288,dissonance,0.001);
    }
}
