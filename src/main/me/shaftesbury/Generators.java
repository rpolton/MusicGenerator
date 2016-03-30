package me.shaftesbury;

import java.util.List;

public class Generators
{
//    public static Note nextNoteConstantDissonance(final double dissonance, final double dissDeviation, final List<Note> previousNotes)
//    {
//        final List<Note> prev2 = Functional.noException.take(2, previousNotes);
//        if(prev2.size()==1)
//        {
//            final Note prevNote = prev2.get(0);
//            final double prevFreq = Note.asFreq(prevNote);
//        }
//        else
//        {
//            final Note prevNotem1 = prev2.get()
//        }
//
//        return new Note();
//    }
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

    public static Note nextNoteUpInKey(final Scale scale, final List<Note> previousNotes)
    {
        final Note prev = previousNotes.get(0);
        return prev;//scale.getFollowingNote(prev);
    }

    public static Note nextNoteDownInKey(final Scale scale, final List<Note> previousNotes)
    {
        final Note prev = previousNotes.get(0);
        return prev;//scale.getPrecedingNote(prev);
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
