package me.shaftesbury;

import me.shaftesbury.utils.functional.Func2;
import me.shaftesbury.utils.functional.Functional;
import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.util.*;

public final class SMPCFunctions
{
    private SMPCFunctions() {}

    public static double intervalBetween(final double freq1, final double freq2)
    {
        final double fmax = Math.max(freq1, freq2);
        final double fmin = Math.min(freq1, freq2);
        return 39.8631 * Math.log10(fmax/fmin); // this is the log2 to log10 conversion factor
    }

    /**
     *
     * @param f1 is a pair containing the fundamental frequency and an int which indicates which overtone we are using from the fundamental
     * @param f2 is a pair containing the fundamental frequency and an int which indicates which overtone we are using from the fundamental
     * @return
     */
    static double dissonance(final Pair<Double,Integer> f1, final Pair<Double,Integer> f2)
    {
        final double freq1 = f1.getValue0();
        final double freq2 = f2.getValue0();
        final int i = f1.getValue1();
        final int j = f2.getValue1();
        final double b1=0.8;
        final double b2=1.6;
        final double b3=4;
        final double gamma = 1.25;
        final double x = intervalBetween(freq1, freq2);
        final double x2g = Math.pow(x, gamma);
        return Math.pow(0.88, i+j)*b3*(Math.exp(-b1*x2g)-Math.exp(-b2*x2g));
    }

    public static double dissonance(final double calculateDissonanceFrom, final double calculateDissonanceTo)
    {
        return dissonance(calculateDissonanceFrom,calculateDissonanceTo,7);
    }

    /**
     * Calculate the total dissonance, D, for a pair of tones with fundamental frequencies f1 and f2
     * taking into account N upper partials
     * @param calculateDissonanceFrom
     * @param calculateDissonanceTo
     * @param howManyPartials
     * @return
     */
    public static double dissonance(final double calculateDissonanceFrom, final double calculateDissonanceTo, final int howManyPartials)
    {
        final List<Double> dissonances = new ArrayList<Double>();
        for (int i = 0; i <= howManyPartials; ++i) {
            for (int j = 0; j <= howManyPartials; ++j) {
                final double d1 = dissonance(Pair.with(calculateDissonanceFrom * Math.pow(2, i), i), Pair.with(calculateDissonanceFrom * Math.pow(2, j), j));
                final double d2 = dissonance(Pair.with(calculateDissonanceTo * Math.pow(2, i), i), Pair.with(calculateDissonanceTo * Math.pow(2, j), j));
                final double d3 = dissonance(Pair.with(calculateDissonanceFrom * Math.pow(2, i), i), Pair.with(calculateDissonanceTo * Math.pow(2, j), j));
                dissonances.add(0.5 * (d1 + d2) + d3);
            }
        }
        final double dissonanceFromRoot = Functional.fold(new Func2<Double, Double, Double>() {
            @Override
            public Double apply(final Double state, final Double next) {
                return state + next;
            }
        }, 0.0, dissonances);
        return dissonanceFromRoot;
    }

    /**
     * Calculate the total dissonance, Dc, for a triad chord
     * @param chord
     * @param howManyPartials
     * @return
     */
    public static double dissonance(final Chord chord, final int howManyPartials)
    {
        double total = 0;
        final List<Double> pitches = chord.pitches();
        final int numPitches = pitches.size();
        for(int i=0;i<numPitches-1;++i)
            for(int j=i+1;j<numPitches;++j)
                total+=dissonance(pitches.get(i),pitches.get(j),howManyPartials);
        return total;
//        return dissonance(chord.pitches().get(0),chord.pitches().get(1),howManyPartials)+
//            dissonance(chord.pitches().get(0),chord.pitches().get(2),howManyPartials)+
//            dissonance(chord.pitches().get(1),chord.pitches().get(2),howManyPartials);
    }

    /** for a two-chord progression
     *
     * @param chord1
     * @param chord2
     * @param howManyPartials
     * @return
     */
    static double dissonance(final Chord chord1, final Chord chord2, final int howManyPartials)
    {
        double total = 0;
        for(int i=0;i<chord1.pitches().size();++i)
            for(int j=0;j<chord2.pitches().size();++j)
                total+=dissonance(chord1.pitches().get(i),chord2.pitches().get(j),howManyPartials);
        return total / (chord1.pitches().size() * chord2.pitches().size());
//        return (dissonance(chord1.pitches().get(0),chord2.pitches().get(0),howManyPartials)+
//                dissonance(chord1.pitches().get(0),chord2.pitches().get(1),howManyPartials)+
//                dissonance(chord1.pitches().get(0),chord2.pitches().get(2),howManyPartials)+
//                dissonance(chord1.pitches().get(1),chord2.pitches().get(0),howManyPartials)+
//                dissonance(chord1.pitches().get(1),chord2.pitches().get(1),howManyPartials)+
//                dissonance(chord1.pitches().get(1),chord2.pitches().get(2),howManyPartials)+
//                dissonance(chord1.pitches().get(2),chord2.pitches().get(0),howManyPartials)+
//                dissonance(chord1.pitches().get(2),chord2.pitches().get(1),howManyPartials)+
//                dissonance(chord1.pitches().get(2),chord2.pitches().get(2),howManyPartials)) / 9.0;

    }

    static double tension(final Pair<Double,Integer> f1, final Pair<Double,Integer> f2, final Pair<Double,Integer> f3)
    {
        final List<Pair<Double, Integer>> sortedPairs = Functional.sortWith(new Comparator<Pair<Double, Integer>>() {
            @Override
            public int compare(final Pair<Double, Integer> o1, final Pair<Double, Integer> o2) {
                return o1.getValue0().compareTo(o2.getValue0());
            }
        }, Arrays.asList(f1, f2, f3));

        final double x = intervalBetween(sortedPairs.get(0).getValue0(),sortedPairs.get(1).getValue0());
        final double y = intervalBetween(sortedPairs.get(1).getValue0(),sortedPairs.get(2).getValue0());
        final int i = sortedPairs.get(0).getValue1();
        final int j = sortedPairs.get(1).getValue1();
        final int k = sortedPairs.get(2).getValue1();
        final double alpha = 0.6;
        return Math.pow(0.88,i+j+k)*Math.exp(-Math.pow((y-x)/alpha,2));
    }

    public static double tension(final double freq1, final double freq2, final double freq3, final int howManyPartials)
    {
        final List<Double> tensions = new ArrayList<Double>();
        for (int i = 0; i <= howManyPartials; ++i)
            for (int j = 0; j < howManyPartials; ++j)
                for (int k = 0; k <= howManyPartials; ++k)
                    tensions.add(tension(Pair.with(freq1*Math.pow(2,i), i), Pair.with(freq2*Math.pow(2,j), j), Pair.with(freq3*Math.pow(2,k), k)));

        final double tension = Functional.fold(new Func2<Double, Double, Double>() {
            @Override
            public Double apply(final Double state, final Double next) {
                return state + next;
            }
        }, 0.0, tensions);
        return tension;
    }

    public static double tension(final Chord chord, final int howManyPartials)
    {
        return tension(chord.pitches().get(0), chord.pitches().get(1), chord.pitches().get(2), howManyPartials);
    }

    static double tension(final Triplet<Double,Double,Double> chord, final int howManyPartials)
    {
        return tension(chord.getValue0(),chord.getValue1(),chord.getValue2(),howManyPartials);
    }

    static double tension(final Chord chord1, final Chord chord2, final int howManyPartials)
    {
        double tension = 0.0;
        for(int i=0;i<3;++i)
        {
            for(int j=0;j<2;++j)
            {
                for(int k=j+1;k<3;++k)
                {
                    final double ffi = i==0?chord1.pitches().get(0):(i==1?chord1.pitches().get(1):chord1.pitches().get(2));
                    final double fsj = j==0?chord2.pitches().get(0):(j==1?chord2.pitches().get(1):chord2.pitches().get(2));
                    final double fsk = k==0?chord2.pitches().get(0):(k==1?chord2.pitches().get(1):chord2.pitches().get(2));
                    tension += tension(Triplet.with(ffi,fsj,fsk),howManyPartials);
                    final double fsi = i==0?chord2.pitches().get(0):(i==1?chord2.pitches().get(1):chord2.pitches().get(2));
                    final double ffj = j==0?chord1.pitches().get(0):(j==1?chord1.pitches().get(1):chord1.pitches().get(2));
                    final double ffk = k==0?chord1.pitches().get(0):(k==1?chord1.pitches().get(1):chord1.pitches().get(2));
                    tension += tension(Triplet.with(fsi,ffj,ffk),howManyPartials);
                }
            }
        }
        return tension;
    }

    public static double instability(final Chord from, final Chord to, final int howManyPartials)
    {
        return (dissonance(from,to,howManyPartials) / 9.0) + (tension(from,to,howManyPartials) * 0.2 / 18.0);
    }

    public static double instability(final Chord chord, final int howManyPartials)
    {
        return dissonance(chord, howManyPartials) +
                0.2 * tension(chord,howManyPartials);
    }

    public static double sonority(final Chord chord, final int howManyPartials)
    {
        final double instability = instability(chord, howManyPartials);
        return instability==0.0 ? Double.MAX_VALUE : 1.0 / instability;
    }
}
