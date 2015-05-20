package me.shaftesbury;

import me.shaftesbury.utils.functional.Func;
import me.shaftesbury.utils.functional.Functional;
import me.shaftesbury.utils.functional.IterableHelper;

import java.util.Comparator;
import java.util.List;

public class Chord
{
    static Comparator<Double> sorter = new Comparator<Double>()
    {
        @Override public int compare(final Double i, final Double j) { return Functional.Sorter(i, j); }
    };

    private final List<Double> pitches;
    private final String name;
    private final double root;
    public Chord(final String name, final List<Double> pitches)
    {
        this.pitches = Functional.sortWith(sorter, pitches);
        this.name = name;
        this.root = pitches.get(0); // assume it's the first one unless otherwise told
    }

    public Chord(final String name, final double root, final List<Double> pitches)
    {
        this.pitches = Functional.sortWith(sorter, pitches);
        this.name = name;
        this.root = root;
    }

    public List<Double> pitches()
    {
        return pitches;
    }

    private List<Double> calculateInversion(final int which)
    {
        return IterableHelper.create(pitches).skip(which).concat(IterableHelper.create(pitches).take(which).map(new Func<Double, Double>() {
            @Override
            public Double apply(final Double pitch) {
                return pitch*2.0;
            }
        })).toList();
    }

    public Chord firstInversion()
    {
        // has the second note, the 3rd, in the bass
        return new Chord(name+"1st", root, calculateInversion(1));
    }

    public Chord secondInversion()
    {
        // fifth is in the bass
        return new Chord(name+"2nd", root, calculateInversion(2));
    }

    public Chord thirdInversion()
    {
        // seventh is in the bass
        return new Chord(name+"3rd", root, calculateInversion(3));
    }

    public String toString()
    {
        return name+"("+root+")";
    }

    public boolean equals(Object o)
    {
        if(!(o instanceof Chord)) return false;
        final Chord other = (Chord)o;
        return pitches.containsAll(other.pitches) && other.pitches.containsAll(pitches);
    }

    public int hashCode()
    {
        return pitches.hashCode();
    }
}
