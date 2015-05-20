package me.shaftesbury;

import me.shaftesbury.utils.functional.Func;
import me.shaftesbury.utils.functional.Functional;
import org.javatuples.Pair;

import java.util.*;

public class MusicGenerator {
    public static Func<Deque<Note>, Pair<Note, Deque<Note>>> generator(final Scale scale, final int howManyPrevNotesToConsider) {
        return new Func<Deque<Note>, Pair<Note, Deque<Note>>>() {
            @Override
            public Pair<Note, Deque<Note>> apply(final Deque<Note> previousNotes) {
                final List<Note> prevNotes = Functional.noException.take(howManyPrevNotesToConsider, previousNotes);
                final Note thisNote = prevNotes.get(0);
                final Note newNote = Generators.nextNote(scale, prevNotes);
                previousNotes.addFirst(newNote);        // !!!!!!!
                return Pair.with(thisNote,previousNotes);
            }
        };
    }

    public static void main(final String... args)
    {
        final Note startingNote = new Note("C",4);
        final Scale startingScale = new MajorScale(startingNote);
        final Deque<Note> seed = new ArrayDeque<Note>();
        seed.add(startingNote);
        final List<Note> notes = Functional.unfold(generator(startingScale, 5), new Func<Deque<Note>, Boolean>() {
            @Override
            public Boolean apply(final Deque<Note> previousNotes) {
                return previousNotes.size()==25;
            }
        }, seed);

        for(final Note note:notes)
            System.out.println(note);
    }
}
