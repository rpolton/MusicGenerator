package me.shaftesbury;

import java.util.Arrays;

public class Chords
{
    private final static int unison=0;
    private final static int minorSecond=1;
    private final static int second=2;
    private final static int minorThird=3;
    private final static int third=4;
    private final static int fourth=5;
    private final static int augmentedFourth=6;
    private final static int diminishedFifth=6;
    private final static int fifth=7;
    private final static int augmentedFifth=8;
    private final static int minorSixth=8;
    private final static int sixth=9;
    private final static int minorSeventh=10;
    private final static int seventh=11;

    private static double addInterval(final double base, final int interval)
    {
        return base*Math.pow(2,interval/12.0);
    }

    private static Chord majorTriad(final double tonic, final String name)
    {
        return new Chord(name, Arrays.asList(tonic, addInterval(tonic, third), addInterval(tonic, fifth)));
    }

    private static Chord minorTriad(final double tonic, final String name)
    {
        return new Chord(name, Arrays.asList(tonic, addInterval(tonic, minorThird), addInterval(tonic, fifth)));
    }

    private static Chord augmentedTriad(final double tonic, final String name)
    {
        return new Chord(name, Arrays.asList(tonic, addInterval(tonic, third), addInterval(tonic, augmentedFifth)));
    }

    private static Chord diminishedTriad(final double tonic, final String name)
    {
        return new Chord(name, Arrays.asList(tonic, addInterval(tonic, minorThird), addInterval(tonic, diminishedFifth)));
    }

    private static Chord sus2(final double tonic, final String name)
    {
        return new Chord(name, Arrays.asList(tonic, addInterval(tonic, second), addInterval(tonic, fifth)));
    }

    private static Chord sus4(final double tonic, final String name)
    {
        return new Chord(name, Arrays.asList(tonic, addInterval(tonic, fourth), addInterval(tonic, fifth)));
    }

    private static Chord major7(final double tonic, final String name)
    {
        return new Chord(name, Arrays.asList(tonic, addInterval(tonic, third), addInterval(tonic, fifth), addInterval(tonic, seventh)));
    }

    private static Chord dominant7(final double tonic, final String name)
    {
        return new Chord(name, Arrays.asList(tonic, addInterval(tonic, third), addInterval(tonic, fifth), addInterval(tonic, minorSeventh)));
    }

    private static Chord minor7(final double tonic, final String name)
    {
        return new Chord(name, Arrays.asList(tonic, addInterval(tonic, minorThird), addInterval(tonic, fifth), addInterval(tonic, minorSeventh)));
    }

    private static Chord halfDiminished7(final double tonic, final String name)
    {
        return new Chord(name, Arrays.asList(tonic, addInterval(tonic, minorThird), addInterval(tonic, diminishedFifth), addInterval(tonic, minorSeventh)));
    }

    public static Chord createMajorTriad(final double tonic)
    {
        return majorTriad(tonic, "Major");
    }

    public static Chord createMinorTriad(final double tonic)
    {
        return minorTriad(tonic, "Minor");
    }

    public static Chord createAugmentedTriad(final double tonic)
    {
        return augmentedTriad(tonic, "Aug");
    }

    public static Chord createDiminishedTriad(final double tonic)
    {
        return diminishedTriad(tonic, "Dim");
    }

    public static Chord createSus2(final double tonic)
    {
        return sus2(tonic, "sus2");
    }

    public static Chord createSus4(final double tonic)
    {
        return sus4(tonic, "sus4");
    }

    public static Chord createMaj7(final double tonic)
    {
        return major7(tonic, "M7");
    }

    public static Chord createDom7(final double tonic)
    {
        return dominant7(tonic, "7");
    }

    public static Chord createMin7(final double tonic)
    {
        return minor7(tonic, "m7");
    }

    public static Chord createHalfDim7(final double tonic)
    {
        return halfDiminished7(tonic, "dim7");
    }

    public static Chord createMajorTriad(final double tonic, final String name)
    {
        return majorTriad(tonic, name + "Major");
    }

    public static Chord createMinorTriad(final double tonic, final String name)
    {
        return minorTriad(tonic, name + "Minor");
    }

    public static Chord createAugmentedTriad(final double tonic, final String name)
    {
        return augmentedTriad(tonic, name + "Aug");
    }

    public static Chord createDiminishedTriad(final double tonic, final String name)
    {
        return diminishedTriad(tonic, name + "Dim");
    }

    public static Chord createSus2(final double tonic, final String name)
    {
        return sus2(tonic, name + "sus2");
    }

    public static Chord createSus4(final double tonic, final String name)
    {
        return sus4(tonic, name + "sus4");
    }

    public static Chord createMaj7(final double tonic, final String name)
    {
        return major7(tonic, name + "M7");
    }

    public static Chord createDom7(final double tonic, final String name)
    {
        return dominant7(tonic, name + "7");
    }

    public static Chord createMin7(final double tonic, final String name)
    {
        return minor7(tonic, name + "m7");
    }

    public static Chord createHalfDim7(final double tonic, final String name)
    {
        return halfDiminished7(tonic, name + "dim7");
    }
}
