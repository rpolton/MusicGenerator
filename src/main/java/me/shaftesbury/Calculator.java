package me.shaftesbury;

import me.shaftesbury.utils.functional.Func2;
import me.shaftesbury.utils.functional.Functional;
import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class Calculator
{
    public static void main(final String[] args)
    {
        final List<String> names = Arrays.asList("A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#");
        final List<Double> pitches = Arrays.asList(
                220.0,
                233.0818808,
                246.9416506,
                261.6255653,
                277.182631 ,
                293.6647679,
                311.1269837,
                329.6275569,
                349.2282314,
                369.9944227,
                391.995436 ,
                415.3046976,
                440.0, // root
                466.16, // b2
                493.8833013  // 2
                , 523.2511306    // m3
                , 554.365262     // 3
                , 587.3295358    // 4
                , 622.2539674    // #4 / b5
                , 659.2551138    // 5
                , 698.4564629    // m6
                , 739.9888454    // 6
                , 783.990872     // m7 (dom7)
                , 830.6093952    // 7
                , 880.0,         // root
                932.327523,
                987.7666025,
                1046.502261
        );
        final int howManyPartials = 7;

        for(final double fromFundamentalFreq1 : pitches) {
            final List<Triplet<Double,Double,Double>> dissonancesFromFreq1ToFreq2 = new ArrayList<Triplet<Double,Double,Double>>();
            for (final double toFundamentalFreq2 : pitches) {
                final double calculateDissonanceFrom = fromFundamentalFreq1;
                final double dissonanceFromRoot = SMPCFunctions.dissonance(calculateDissonanceFrom, toFundamentalFreq2, howManyPartials);
                dissonancesFromFreq1ToFreq2.add(Triplet.with(fromFundamentalFreq1,toFundamentalFreq2,dissonanceFromRoot));
            }
            final double max = Functional.fold(new Func2<Double, Triplet<Double, Double, Double>, Double>() {
                @Override
                public Double apply(final Double state, final Triplet<Double, Double, Double> element) {
                    return Math.max(state, element.getValue2());
                }
            }, 0.0, dissonancesFromFreq1ToFreq2);
            for(final Triplet<Double,Double,Double> element : dissonancesFromFreq1ToFreq2)
                System.out.println("Dissonance "+(element.getValue2()/max)+" from "+element.getValue0()+" to "+element.getValue1());
        }
//        System.out.println("Calculated dissonanceBetweenComponents between (440,0) and ("+note.getValue0()+","+note.getValue1()+") is "+dissonanceBetweenComponents);

        System.out.println("============================");

        final Chord AMajorChord = Chords.createMajorTriad(pitches.get(0), "A");
        final Chord AMinorChord = Chords.createMinorTriad(pitches.get(0), "A");
        final Chord AAugmentedChord = Chords.createAugmentedTriad(pitches.get(0), "A");
        final Chord ADiminishedChord = Chords.createDiminishedTriad(pitches.get(0), "A");
        final Chord ASuspendedSecondChord = Chords.createSus2(pitches.get(0), "A");
        final Chord ASuspendedFourthChord = Chords.createSus4(pitches.get(0), "A");

        System.out.println("dissonanceBetweenComponents in chord " + AMajorChord + " " + SMPCFunctions.dissonanceWithinChord(AMajorChord, howManyPartials));
        System.out.println("dissonanceBetweenComponents in chord " + AMinorChord + " " + SMPCFunctions.dissonanceWithinChord(AMinorChord, howManyPartials));
        System.out.println("dissonanceBetweenComponents in chord " + AAugmentedChord + " " + SMPCFunctions.dissonanceWithinChord(AAugmentedChord, howManyPartials));
        System.out.println("dissonanceBetweenComponents in chord " + ADiminishedChord + " " + SMPCFunctions.dissonanceWithinChord(ADiminishedChord, howManyPartials));
        System.out.println("dissonanceBetweenComponents in chord " + ASuspendedSecondChord + " " + SMPCFunctions.dissonanceWithinChord(ASuspendedSecondChord, howManyPartials));
        System.out.println("dissonanceBetweenComponents in chord " + ASuspendedFourthChord + " " + SMPCFunctions.dissonanceWithinChord(ASuspendedFourthChord, howManyPartials));

        System.out.println("tension in chord " + AMajorChord + " " + SMPCFunctions.tension(AMajorChord, howManyPartials));
        System.out.println("tension in chord " + AMinorChord + " " + SMPCFunctions.tension(AMinorChord, howManyPartials));
        System.out.println("tension in chord " + AAugmentedChord + " " + SMPCFunctions.tension(AAugmentedChord, howManyPartials));
        System.out.println("tension in chord " + ADiminishedChord + " " + SMPCFunctions.tension(ADiminishedChord, howManyPartials));
        System.out.println("tension in chord " + ASuspendedSecondChord + " " + SMPCFunctions.tension(ASuspendedSecondChord, howManyPartials));
        System.out.println("tension in chord " + ASuspendedFourthChord + " " + SMPCFunctions.tension(ASuspendedFourthChord, howManyPartials));

        System.out.println("tension in chord progression from " + AMajorChord + " to "+ AMinorChord + " " + SMPCFunctions.tension(AMajorChord, AMinorChord, howManyPartials));

        System.out.println("============================");

        final Triplet<Double,Double,Double> CMajorChord = Triplet.with(pitches.get(12+3+0),pitches.get(12+3+4),pitches.get(12+3+7));
        final Chord fromCMajor = Chords.createMajorTriad(CMajorChord.getValue0(), "C");

        final List<Double> tones = CircularArrayList.seqAsCircularArrayList(pitches);
        final List<String> noteNames = CircularArrayList.seqAsCircularArrayList(names);

        for(int basePitch = 0; basePitch<pitches.size();++basePitch)
        {
            final Chord major = Chords.createMajorTriad(tones.get(basePitch + 0), noteNames.get(basePitch));
            System.out.println("instability in chord progression from " + fromCMajor + " to "+ major + " " + SMPCFunctions.instability(fromCMajor, major, howManyPartials));
        }

        System.out.println("============================");

        for(int basePitch = 0; basePitch<pitches.size();++basePitch)
        {
            final Chord minor = Chords.createMinorTriad(tones.get(basePitch + 0), noteNames.get(basePitch));
            System.out.println("instability in chord progression from " + fromCMajor + " to "+ minor + " " + SMPCFunctions.instability(fromCMajor, minor, howManyPartials));
        }

        System.out.println("============================");

        for(int basePitch = 0; basePitch<pitches.size();++basePitch)
        {
            final Chord diminished = Chords.createDiminishedTriad(tones.get(basePitch + 0), noteNames.get(basePitch));
            System.out.println("instability in chord progression from " + fromCMajor + " to "+ diminished + " " + SMPCFunctions.instability(fromCMajor, diminished, howManyPartials));
        }

        System.out.println("============================");

        for(int basePitch = 0; basePitch<pitches.size();++basePitch)
        {
            final Chord augmented = Chords.createAugmentedTriad(tones.get(basePitch + 0), noteNames.get(basePitch));
            System.out.println("instability in chord progression from " + fromCMajor + " to "+ augmented + " " + SMPCFunctions.instability(fromCMajor, augmented, howManyPartials));
        }

        System.out.println("============================");

        for(int basePitch = 0; basePitch<pitches.size();++basePitch)
        {
            final Chord major = Chords.createMajorTriad(tones.get(basePitch + 0), noteNames.get(basePitch));
            final Chord minor = Chords.createMinorTriad(tones.get(basePitch + 0), noteNames.get(basePitch));
            final Chord augmented = Chords.createAugmentedTriad(tones.get(basePitch + 0), noteNames.get(basePitch));
            final Chord diminished = Chords.createDiminishedTriad(tones.get(basePitch + 0), noteNames.get(basePitch));
            final Chord suspendedSecond = Chords.createSus2(tones.get(basePitch + 0), noteNames.get(basePitch));
            final Chord suspendedFourth = Chords.createSus4(tones.get(basePitch + 0), noteNames.get(basePitch));

            System.out.println("instability in chord progression from " + fromCMajor + " to "+ major + " " + SMPCFunctions.instability(fromCMajor, major, howManyPartials));
            System.out.println("instability in chord progression from " + fromCMajor + " to "+ minor + " " + SMPCFunctions.instability(fromCMajor, minor, howManyPartials));
            System.out.println("instability in chord progression from " + fromCMajor + " to "+ augmented + " " + SMPCFunctions.instability(fromCMajor, augmented, howManyPartials));
            System.out.println("instability in chord progression from " + fromCMajor + " to "+ diminished + " " + SMPCFunctions.instability(fromCMajor, diminished, howManyPartials));
            System.out.println("instability in chord progression from " + fromCMajor + " to "+ suspendedSecond + " " + SMPCFunctions.instability(fromCMajor, suspendedSecond, howManyPartials));
            System.out.println("instability in chord progression from " + fromCMajor + " to "+ suspendedFourth + " " + SMPCFunctions.instability(fromCMajor, suspendedFourth, howManyPartials));
        }

        System.out.println("============================");
        {
            final List<Pair<Double, Double>> instabilities = new ArrayList<Pair<Double, Double>>();
            for (int basePitch = 0; basePitch < pitches.size(); basePitch += 12) {
                for (int i = 0; i < 24; ++i) {
                    final double tonic = tones.get(basePitch) * Math.pow(2, i / 24.0);
                    final Chord major = Chords.createMajorTriad(tonic);
                    final double instability = SMPCFunctions.instability(fromCMajor, major, howManyPartials);
                    System.out.println("instability in chord progression from " + fromCMajor + " to " + major + " " + instability);
                    instabilities.add(Pair.with(tonic, instability));
                }
            }
            for (final Pair<Double, Double> entry : instabilities)
                System.out.println(entry.getValue0() + "," + entry.getValue1());
        }
        System.out.println("============================");

        {
            final List<Pair<Double, Double>> instabilities = new ArrayList<Pair<Double, Double>>();
            for (int basePitch = 0; basePitch < pitches.size(); basePitch += 12) {
                for (int i = 0; i < 24; ++i) {
                    final double tonic = tones.get(basePitch) * Math.pow(2, i / 24.0);
                    final Chord minor = Chords.createMinorTriad(tonic);
                    final double instability = SMPCFunctions.instability(fromCMajor, minor, howManyPartials);
                    System.out.println("instability in chord progression from " + fromCMajor + " to " + minor + " " + instability);
                    instabilities.add(Pair.with(tonic, instability));
                }
            }
            for (final Pair<Double, Double> entry : instabilities)
                System.out.println(entry.getValue0() + "," + entry.getValue1());
        }
        System.out.println("============================");

        {
            final List<Pair<Double, Double>> instabilities = new ArrayList<Pair<Double, Double>>();
            for (int basePitch = 0; basePitch < pitches.size(); basePitch += 12) {
                for (int i = 0; i < 24; ++i) {
                    final double tonic = tones.get(basePitch) * Math.pow(2, i / 24.0);
                    final Chord aug = Chords.createAugmentedTriad(tonic);
                    final double instability = SMPCFunctions.instability(fromCMajor, aug, howManyPartials);
                    System.out.println("instability in chord progression from " + fromCMajor + " to " + aug + " " + instability);
                    instabilities.add(Pair.with(tonic, instability));
                }
            }
            for (final Pair<Double, Double> entry : instabilities)
                System.out.println(entry.getValue0() + "," + entry.getValue1());
        }
        System.out.println("============================");

        {
            final List<Pair<Double, Double>> instabilities = new ArrayList<Pair<Double, Double>>();
            for (int basePitch = 0; basePitch < pitches.size(); basePitch += 12) {
                for (int i = 0; i < 24; ++i) {
                    final double tonic = tones.get(basePitch) * Math.pow(2, i / 24.0);
                    final Chord dim = Chords.createDiminishedTriad(tonic);
                    final double instability = SMPCFunctions.instability(fromCMajor, dim, howManyPartials);
                    System.out.println("instability in chord progression from " + fromCMajor + " to " + dim + " " + instability);
                    instabilities.add(Pair.with(tonic, instability));
                }
            }
            for (final Pair<Double, Double> entry : instabilities)
                System.out.println(entry.getValue0() + "," + entry.getValue1());
        }
        System.out.println("============================");

        {
            final List<Pair<Double, Double>> instabilities = new ArrayList<Pair<Double, Double>>();
            for (int basePitch = 0; basePitch < pitches.size(); basePitch += 12) {
                for (int i = 0; i < 24; ++i) {
                    final double tonic = tones.get(basePitch) * Math.pow(2, i / 24.0);
                    final Chord sus2 = Chords.createSus2(tonic);
                    final double instability = SMPCFunctions.instability(fromCMajor, sus2, howManyPartials);
                    System.out.println("instability in chord progression from " + fromCMajor + " to " + sus2 + " " + instability);
                    instabilities.add(Pair.with(tonic, instability));
                }
            }
            for (final Pair<Double, Double> entry : instabilities)
                System.out.println(entry.getValue0() + "," + entry.getValue1());
        }
        System.out.println("============================");

        {
            final List<Pair<Double, Double>> instabilities = new ArrayList<Pair<Double, Double>>();
            for (int basePitch = 0; basePitch < pitches.size(); basePitch += 12) {
                for (int i = 0; i < 24; ++i) {
                    final double tonic = tones.get(basePitch) * Math.pow(2, i / 24.0);
                    final Chord sus4 = Chords.createSus4(tonic);
                    final double instability = SMPCFunctions.instability(fromCMajor, sus4, howManyPartials);
                    System.out.println("instability in chord progression from " + fromCMajor + " to " + sus4 + " " + instability);
                    instabilities.add(Pair.with(tonic, instability));
                }
            }
            for (final Pair<Double, Double> entry : instabilities)
                System.out.println(entry.getValue0() + "," + entry.getValue1());
        }
        System.out.println("============================");

        final Chord CMajInv1 = fromCMajor.firstInversion();
        final Chord CMajInv2 = fromCMajor.secondInversion();
        System.out.println("dissonanceBetweenComponents in chord " + fromCMajor + " " + SMPCFunctions.dissonanceWithinChord(fromCMajor, howManyPartials));
        System.out.println("dissonanceBetweenComponents in chord " + CMajInv1 + " " + SMPCFunctions.dissonanceWithinChord(CMajInv1, howManyPartials));
        System.out.println("dissonanceBetweenComponents in chord " + CMajInv2 + " " + SMPCFunctions.dissonanceWithinChord(CMajInv2, howManyPartials));

        {
            // The dissonanceBetweenComponents for the Major7 chord seems high so what are the constituent dissonances?
            final double A13 = SMPCFunctions.dissonance(pitches.get(12), pitches.get(12 + 4), howManyPartials);
            final double A15 = SMPCFunctions.dissonance(pitches.get(12), pitches.get(12 + 7), howManyPartials);
            final double A17 = SMPCFunctions.dissonance(pitches.get(12), pitches.get(12 + 11), howManyPartials);
            final double A35 = SMPCFunctions.dissonance(pitches.get(12 + 4), pitches.get(12 + 7), howManyPartials);
            final double A37 = SMPCFunctions.dissonance(pitches.get(12 + 4), pitches.get(12 + 11), howManyPartials);
            final double A57 = SMPCFunctions.dissonance(pitches.get(12 + 7), pitches.get(12 + 11), howManyPartials);
            System.out.println(String.format("Constituent dissonances %f %f %f %f %f %f", A13,A15,A17,A35,A37,A57));
            System.out.println("Sum of dissonances " + (A13+A15+A17+A35+A37+A57));
        }
        final Chord AMajor7 = Chords.createMaj7(pitches.get(12), "A");
        final Chord ADominant7 = Chords.createDom7(pitches.get(12), "A");
        final Chord AMinor7 = Chords.createMin7(pitches.get(12), "A");
        final Chord ADiminished7 = Chords.createHalfDim7(pitches.get(12), "A");
        System.out.println("dissonanceBetweenComponents in chord " + AMajor7 + " " + SMPCFunctions.dissonanceWithinChord(AMajor7, howManyPartials));
        System.out.println("dissonanceBetweenComponents in chord " + ADominant7 + " " + SMPCFunctions.dissonanceWithinChord(ADominant7, howManyPartials));
        System.out.println("dissonanceBetweenComponents in chord " + AMinor7 + " " + SMPCFunctions.dissonanceWithinChord(AMinor7, howManyPartials));
        System.out.println("dissonanceBetweenComponents in chord " + ADiminished7 + " " + SMPCFunctions.dissonanceWithinChord(ADiminished7, howManyPartials));
        System.out.println("============================");
    }
}
