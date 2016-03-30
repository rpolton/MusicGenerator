package me.shaftesbury;

import me.shaftesbury.utils.functional.Func;
import me.shaftesbury.utils.functional.Functional;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Note {
// These three fields should be a union of one and two fields
//    private final double freq;
    private final PitchClass pitchClass;
    private final int octave;
//    public Note(final double freq)
//    {
//        this.freq = Option.toOption(freq);
//        noteAndOctave=Option.None();
//    }
    public Note(final String note, final int octave)
    {
        pitchClass=new PitchClass(note);
        this.octave=octave;
    }
    public Note(final PitchClass note, final int octave)
    {
        this.pitchClass=note;
        this.octave=octave;
    }

    public String toString(){return pitchClass+""+octave;}
    public boolean equals(final Object other) {
        if(other==null) return false;
        if(other.getClass()!=Note.class) return false;
        final Note o = (Note)other;
        return octave==o.octave && pitchClass.equals(o.pitchClass);
    }
    public int hashCode() { return pitchClass.hashCode(); }

//    public static double asFreq(final Note note) { return note.freq.Some(); }
    public static PitchClass pitchClass(final Note note) { return note.pitchClass; }
//    public static Pair<PitchClass,Integer> asName(final Note note) {return Pair.with(note.noteAndOctave.Some().getValue0(), note.noteAndOctave.Some().getValue1()); }

    private static int getIndex(final PitchClassInternal noteName)
    {
        return Functional.findIndex(new Func<PitchClassInternal, Boolean>() {
            @Override
            public Boolean apply(final PitchClassInternal name) {
                return name.equals(noteName);
            }
        }, noteNames);
    }

    private final static List<PitchClassInternal> noteNames = CircularArrayList.asCircularArrayList(
            PitchClassInternal.C,PitchClassInternal.CSHARP,PitchClassInternal.D,PitchClassInternal.DSHARP,PitchClassInternal.E,
            PitchClassInternal.F,PitchClassInternal.FSHARP,PitchClassInternal.G,PitchClassInternal.GSHARP,PitchClassInternal.A,
            PitchClassInternal.ASHARP,PitchClassInternal.B);
    private final static int indexOfA = getIndex(PitchClassInternal.A);
    private final static int indexOfB = getIndex(PitchClassInternal.B);
    private final static int indexOfC = getIndex(PitchClassInternal.C);

    private final static Map<String,Double> notesToFrequencies = new HashMap<String, Double>();
    private final static Map<Double,String> frequenciesToNotes = new HashMap<Double, String>();

    static
    {
        {
            int counter = 0;
            for (final int octave : Arrays.asList(4, 3, 2, 1, 0)) {
                for (int i = 0; i < 12; ++i) {
                    final PitchClassInternal noteName = noteNames.get((octave==4?indexOfA:indexOfB) - i);
                    final Note theNote = new Note(new PitchClass(noteName), octave);
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
                    final PitchClassInternal noteName = noteNames.get((octave==4?indexOfA:indexOfC) + i);
                    final Note theNote = new Note(new PitchClass(noteName), octave);
                    save(theNote, counter++);
                }
            }
        }
    }

    private static void save(final Note theNote,final int counter)
    {
        final double theFreq = 440.0*Math.pow(2.0,((double)counter)/12.0);
        notesToFrequencies.put(theNote.toString(),theFreq);
        frequenciesToNotes.put(theFreq,theNote.toString());
    }

//    public static Pair<PitchClass,Integer> fromFreq(final double freq)
//    {
//        final Note note = new Note(frequenciesToNotes.get(freq));
//        return Pair.with(note.pitchClass,note.octave);
//    }

    public static double toFreq(final Note note)
    {
        if(notesToFrequencies.containsKey(note.toString()))
            return notesToFrequencies.get(note.toString());

        for(final Map.Entry<String,Double> entry : notesToFrequencies.entrySet())
            System.out.println(entry);
        throw new IllegalStateException(String.format("%s not recognised. Calculation must be performed to determine the frequency.", note));
    }

    public static Note addStep(final Step step, final Note note)
    {
        if(step.equals(Step.Half)) return incrementByHalfStep(note);
        if(step.equals(Step.Whole)) return incrementByHalfStep(incrementByHalfStep(note));
        if(step.equals(Step.AugSecond)) return incrementByHalfStep(incrementByHalfStep(incrementByHalfStep(note)));

        throw new IllegalStateException("Unrecognised step");
    }
//
//    public static Note subtractStep(final Step step, final Note note)
//    {
//        final Note halfstep = decrement(note);
//        return step==Step.Whole ? decrement(halfstep) : halfstep;
//    }

    public static Note incrementByHalfStep(final Note note)
    {
        final int newOctave = note.octave +
                ((note.pitchClass.equals(new PitchClass("B"))) ? 1 : 0 );
        return new Note(PitchClass.addStep(Step.Half,note.pitchClass),newOctave);
    }

    public static Note decrement(final Note note)
    {
        final int newOctave = note.octave -
                ((note.pitchClass.equals(new PitchClass("B"))) ? 1 : 0);
        return new Note(PitchClass.subtractStep(Step.Half,note.pitchClass),newOctave);
    }
}
