package me.shaftesbury;

import me.shaftesbury.utils.functional.Func;
import me.shaftesbury.utils.functional.Functional;
import me.shaftesbury.utils.functional.Option;
import org.javatuples.Pair;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Note {
// These three fields should be a union of one and two fields
    private final Option<Double> freq;
    private final Option<Pair<String,Integer>> noteAndOctave;
    public Note(final double freq)
    {
        this.freq = Option.toOption(freq);
        noteAndOctave=Option.None();
    }
    public Note(final String note, final int octave)
    {
        noteAndOctave=Option.toOption(Pair.with(note,octave));
        freq=Option.None();
    }

    public String toString(){return "Note "+noteAndOctave;}
    public boolean equals(final Note other) {return false;}
    public int hashCode() { return freq.hashCode(); }

    public static double asFreq(final Note note) { return note.freq.Some(); }
    public static Pair<String,Integer> asName(final Note note) {return Pair.with(note.noteAndOctave.Some().getValue0(), note.noteAndOctave.Some().getValue1()); }

    public static Note increment(final Note note)
    {
        final int index = Functional.findIndex(new Func<String, Boolean>() {
            @Override
            public Boolean apply(final String name) {
                return name.equals(note.noteAndOctave.Some().getValue0());
            }
        }, noteNames);
        if(index==noteNames.size()-1)
            return new Note(noteNames.get(0),note.noteAndOctave.Some().getValue1()+1);
        else
            return new Note(noteNames.get(index+1),note.noteAndOctave.Some().getValue1());
    }

    public static int getIndex(final String noteName)
    {
        return Functional.findIndex(new Func<String, Boolean>() {
            @Override
            public Boolean apply(final String name) {
                return name.equals(noteName);
            }
        }, noteNames);
    }

    public static Note decrement(final Note note)
    {
        final int index = getIndex(note.noteAndOctave.Some().getValue0());
        if(index==indexOfC)
            return new Note(noteNames.get(noteNames.size()-1),note.noteAndOctave.Some().getValue1()-1);
        else
            return new Note(noteNames.get(index-1),note.noteAndOctave.Some().getValue1());
    }

    private final static List<String> noteNames = CircularArrayList.asCircularArrayList("C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B");
    private final static int indexOfA = getIndex("A");
    private final static int indexOfB = getIndex("B");
    private final static int indexOfC = getIndex("C");

    private final static Map<Pair<String,Integer>,Double> notesToFrequencies = new HashMap<Pair<String,Integer>, Double>();
    private final static Map<Double,Note> frequenciesToNotes = new HashMap<Double, Note>();

    static
    {
        {
            int counter = 0;
            for (final int octave : Arrays.asList(4, 3, 2, 1, 0)) {
                for (int i = 0; i < 12; ++i) {
                    final String noteName = noteNames.get((octave==4?indexOfA:indexOfB) - i);
                    final Note theNote = new Note(noteName, octave);
                    save(theNote, counter--);
                    if (octave == 4 && indexOfA - i == 0) break;
                }
            }
        }
        {
            int counter = 1;
            for (final int octave : Arrays.asList(4, 5, 6, 7, 8, 9)) {
                for (int i = 0; i < 12; ++i) {
                    if (octave == 4 && i == 0) continue;
                    if (octave == 4 && (indexOfA + i)%noteNames.size() == indexOfC) break;
                    final String noteName = noteNames.get((octave==4?indexOfA:indexOfC) + i);
                    final Note theNote = new Note(noteName, octave);
                    save(theNote, counter++);
                }
            }
        }
    }

    private static void save(final Note theNote,final int counter)
    {
        final double theFreq = 440.0*Math.pow(2.0,((double)counter)/12.0);
        notesToFrequencies.put(Pair.with(theNote.noteAndOctave.Some().getValue0(),theNote.noteAndOctave.Some().getValue1()),theFreq);
        frequenciesToNotes.put(theFreq,theNote);
    }

    public static Pair<String,Integer> calculateNoteFromFreq(final double freq)
    {
        final Note note = frequenciesToNotes.get(freq);
        return Pair.with(note.noteAndOctave.Some().getValue0(),note.noteAndOctave.Some().getValue1());
    }

    public static double calculateFreqFromNote(final String note, final int octave)
    {
        return notesToFrequencies.get(Pair.with(note, octave));
    }

    public static Note addStep(final Step step, final Note note)
    {
        final Note halfstep = increment(note);
        return step==Step.Whole ? increment(halfstep) : halfstep;
    }

    public static Note subtractStep(final Step step, final Note note)
    {
        final Note halfstep = decrement(note);
        return step==Step.Whole ? decrement(halfstep) : halfstep;
    }
}
