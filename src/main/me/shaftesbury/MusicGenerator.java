package me.shaftesbury;

import me.shaftesbury.utils.functional.Func;
import me.shaftesbury.utils.functional.Functional;
import org.javatuples.Pair;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Random;

public class MusicGenerator {
    public static Func<Deque<Note>, Pair<Note, Deque<Note>>> generator(final int howManyAttempts, final EqualTemperedScale scale, final int howManyPrevNotesToConsider, final double averageDissonance)
    {
        final Random rnd = new Random();
        return new Func<Deque<Note>, Pair<Note, Deque<Note>>>() {
            @Override
            public Pair<Note, Deque<Note>> apply(final Deque<Note> previousNotes) {
                final List<Note> prevNotes = Functional.noException.take(howManyPrevNotesToConsider, previousNotes);
                final Note thisNote = prevNotes.get(0);
                int counter=0;
                Note newNote;
                do {
                    newNote = Generators.nextNoteConstantDissonance(rnd,howManyAttempts,averageDissonance,0.2,prevNotes);
                }while(SMPCFunctions.dissonance(Note.toFreq(thisNote),Note.toFreq(newNote))>averageDissonance && ++counter<howManyAttempts);
                previousNotes.addFirst(newNote);        // !!!!!!!
                return Pair.with(thisNote,previousNotes);
            }
        };
    }

    public static void main(final String... args)
    {
        final Note startingNote = new Note("C",4);
        final EqualTemperedScale startingScale = new MajorScale(startingNote);
        final Deque<Note> seed = new ArrayDeque<Note>();
        seed.add(startingNote);
        final int howManyAttempts = 5;
        final int howManyPrevNotesToConsider=5;
        final double averageDissonance = 0.5;
        final int howManyNotesToGenerate = 25;
        final List<Note> notes = Functional.unfold(generator(howManyAttempts, startingScale, howManyPrevNotesToConsider, averageDissonance), new Func<Deque<Note>, Boolean>() {
            @Override
            public Boolean apply(final Deque<Note> previousNotes) {
                return previousNotes.size()==howManyNotesToGenerate;
            }
        }, seed);

        for(final Note note:notes)
            System.out.println(note);
    }
}
