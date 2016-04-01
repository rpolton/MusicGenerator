package me.shaftesbury;

import me.shaftesbury.utils.functional.Functional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Generators
{
    public static Note nextNoteConstantDissonance(final Random rnd, final int maxAttempts, final double dissonance, final double dissDeviation, final List<Note> previousNotes)
    {
        final List<Note> prev2 = Functional.noException.take(2, previousNotes);
        if(prev2.size()>=1)
        {
            final Note prevNote = prev2.get(0);
            final double prevFreq = Note.toFreq(prevNote);
            double dissonance1;
            int attempts=0;
            final Map<Note,Double> dissonances = new HashMap<Note, Double>();
            Note n=Note.addStep(Interval.MinorSecond,prevNote);
            do {
                //test all notes
                final double to = Note.toFreq(n);
                dissonance1 = SMPCFunctions.dissonance(prevFreq, to, 7);
                dissonances.put(n,dissonance1);
                System.out.println(String.format("Testing with %s having dissonance %f", n, dissonance1));
                n = Note.addStep(Interval.MinorSecond,n);
            }while(! Note.pitchClass(n).equals(Note.pitchClass(prevNote)));

            n=Note.subtractStep(Interval.MinorSecond,prevNote);
            do {
                //test all notes
                final double to = Note.toFreq(n);
                dissonance1 = SMPCFunctions.dissonance(prevFreq, to, 7);
                dissonances.put(n,dissonance1);
                System.out.println(String.format("Testing with %s having dissonance %f", n, dissonance1));
                n = Note.subtractStep(Interval.MinorSecond,n);
            }while(! Note.pitchClass(n).equals(Note.pitchClass(prevNote)));

            double nearest=dissonance;
            Note nearestNote=prevNote;
            for(final Map.Entry<Note,Double> entry : dissonances.entrySet())
            {
                // find the note with dissonance nearest the required amount.
                // Choose randomly from them if there are more than one.
                if(Math.abs(entry.getValue()-dissonance)<nearest)
                {
                    nearest=entry.getValue();
                    nearestNote=entry.getKey();
                }
            }

            System.out.println(String.format("Choosing %s with dissonance %f",nearestNote, nearest));
            return nearestNote;
        }

        throw new IllegalArgumentException("No notes passed into generator.");
    }
//
//    public static Note nextNoteUpOneOctave(final List<Note> previousNotes)
//    {
//        final Note prev = previousNotes.get(0);
//        final Pair<String, Integer> parts = Note.asName(prev);
//        return new Note(parts.getValue0(),parts.getValue1()+1);
//    }
//
//    public static Note nextNoteUpAlternatingThirdsInKey(final Scale scale, final List<Note> previousNotes)
//    {
//        final List<Note> prev2 = Functional.noException.take(2, previousNotes);
//        return prev2.size()==1
//                ? nextNoteUpAThirdInKey(scale,previousNotes)
//                : scale.getFollowingNote(prev2.get(1));
//    }
//
//    public static Note nextNoteUpAThirdInKey(final Scale scale, final List<Note> previousNotes)
//    {
//        final Note prev = previousNotes.get(0);
//        final Note second = scale.getFollowingNote(prev);
//        final Note third = scale.getFollowingNote(second);
//        return third;
//    }

    public static Note nextNoteUpInKey(final EqualTemperedScale scale, final List<Note> previousNotes)
    {
        final Note prev = previousNotes.get(0);
        return scale.getFollowingNote(prev);
    }

    public static Note nextNoteDownInKey(final EqualTemperedScale scale, final List<Note> previousNotes)
    {
        final Note prev = previousNotes.get(0);
        return scale.getPrecedingNote(prev);
    }

    public static Note nextNoteChromaticUp(final List<Note> previousNotes)
    {
        final Note prev = previousNotes.get(0);
        return Note.incrementByHalfStep(prev);
    }

    public static Note nextNoteChromaticDown(final List<Note> previousNotes)
    {
        final Note prev = previousNotes.get(0);
        return Note.decrement(prev);
    }

//    public static Note nextNote(final Scale scale, final List<Note> previousNotes) { return nextNoteUpAlternatingThirdsInKey(scale, previousNotes); }
}
