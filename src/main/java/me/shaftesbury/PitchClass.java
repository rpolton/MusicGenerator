package me.shaftesbury;

import me.shaftesbury.utils.functional.Func;
import me.shaftesbury.utils.functional.Functional;

import java.util.List;

public class PitchClass
{
    private final boolean isCanonical;
    private final PitchClassInternal p;
    public PitchClass(final String name)
    {
        p = PitchClassInternal.fromString(name);
        isCanonical=canonicalPitches.contains(p);
    }
    public PitchClass(final PitchClassInternal p)
    {
        this.p=p;
        isCanonical=canonicalPitches.contains(p);
    }

    public boolean equals(final Object other)
    {
        if(other==null) return false;
        if(other.getClass()!=PitchClass.class) return false;

        final PitchClass o = (PitchClass)other;
        if(p.equals(o.p)) return true;
        //enharmonic notes are equal
        if(p.isEnharmonicWith(o.p)) return true;
        return false;
    }

    private static final List<PitchClassInternal> canonicalPitches = CircularArrayList.asCircularArrayList(
            PitchClassInternal.A,PitchClassInternal.ASHARP,PitchClassInternal.B,PitchClassInternal.C,PitchClassInternal.CSHARP,
            PitchClassInternal.D,PitchClassInternal.DSHARP,PitchClassInternal.E,PitchClassInternal.F,PitchClassInternal.FSHARP,
            PitchClassInternal.G,PitchClassInternal.GSHARP);

    private static PitchClass makeCanonical(final PitchClass p)
    {
        if(p.isCanonical) return p;
        for(final PitchClassInternal pi:canonicalPitches)
            if(!(pi==p.p) && p.p.isEnharmonicWith(pi)) return new PitchClass(pi);

        throw new IllegalStateException();
    }

    public String toString(){return p.toString();}

    public static PitchClass addStep(final Interval interval, final PitchClass note)
    {
        if(interval == Interval.MinorSecond) return addSteps(1, note);
        if(interval == Interval.Second) return addSteps(2, note);
        if(interval == Interval.MinorThird) return addSteps(3, note);

        throw new IllegalStateException("Unexpected step size "+ interval);
    }

    public static PitchClass subtractStep(final Interval interval, final PitchClass note)
    {
        if(interval == Interval.MinorSecond) return subtractSteps(1, note);
        if(interval == Interval.Second) return subtractSteps(2, note);
        if(interval == Interval.MinorThird) return subtractSteps(3, note);

        throw new IllegalStateException("Unexpected step size "+ interval);
    }

    private static PitchClass addSteps(final int howManySteps, final PitchClass note)
    {
        final PitchClass pitch = makeCanonical(note);
        final int index = Functional.findIndex(new Func<PitchClassInternal, Boolean>() {
            @Override
            public Boolean apply(final PitchClassInternal name) {
                return name.equals(pitch.p);
            }
        }, canonicalPitches);

        return new PitchClass(canonicalPitches.get(index+howManySteps));
    }

    private static PitchClass subtractSteps(final int howManySteps, final PitchClass note)
    {
        final PitchClass pitch = makeCanonical(note);
        final int index = Functional.findIndex(new Func<PitchClassInternal, Boolean>() {
            @Override
            public Boolean apply(final PitchClassInternal name) {
                return name.equals(pitch.p);
            }
        }, canonicalPitches);

        return new PitchClass(canonicalPitches.get(index-howManySteps));
    }
}
