package me.shaftesbury;

import me.shaftesbury.utils.functional.Func;
import me.shaftesbury.utils.functional.Func2;
import me.shaftesbury.utils.functional.Functional;
import me.shaftesbury.utils.functional.IterableHelper;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public abstract class EqualTemperedScale implements Iterable<Note>
{
    private final Note rootNote;
    private final List<Interval> intervals;
    private final List<PitchClass> noteNames;
    private final List<Note> notes;

    public Iterator<Note> iterator()
    {
        return notes.iterator();
    }

    protected EqualTemperedScale(final Note rootNote, final List<Interval> intervals)
    {
        this.rootNote=rootNote;
        this.intervals =CircularArrayList.asCircularArrayList(intervals);
        this.noteNames = CircularArrayList.seqAsCircularArrayList(
                pitchClassGenerator(rootNote, intervals));
        this.notes = noteGenerator(rootNote, intervals);
    }

    private static List<PitchClass> pitchClassGenerator(final Note rootNote, final List<Interval> intervals) {
        return IterableHelper.create(intervals).
                take(intervals.size()-1).
                fold(new Func2<List<PitchClass>, Interval, List<PitchClass>>() {
                    @Override
                    public List<PitchClass> apply(final List<PitchClass> notes, final Interval interval) {
                        final PitchClass last = Functional.last(notes);
                        return Functional.concat(notes, Arrays.asList(PitchClass.addStep(interval, last)));
                    }
                }, Arrays.asList(Note.pitchClass(rootNote)));
    }

    private static List<Note> noteGenerator(final Note rootNote, final List<Interval> intervals) {
        return IterableHelper.create(intervals).
                take(intervals.size()-1).
                fold(new Func2<List<Note>, Interval, List<Note>>() {
                    @Override
                    public List<Note> apply(final List<Note> notes, final Interval interval) {
                        final Note last = Functional.last(notes);
                        return Functional.concat(notes, Arrays.asList(Note.addStep(interval, last)));
                    }
                }, Arrays.asList(rootNote));
    }


    public Note getNote(int index)
    {
        return notes.get(index);
    }

    private int findIndex(final Note note)
    {
        return Functional.findIndex(new Func<Note, Boolean>() {
            @Override
            public Boolean apply(final Note n) {
                return Note.pitchClass(note).equals(Note.pitchClass(n));
            }
        }, notes);
    }

    public Note getPrecedingNote(final Note note)
    {
        final int index = findIndex(note);
        return Note.subtractStep(intervals.get((index+ intervals.size()-1)% intervals.size()), note);
    }

    public Note getFollowingNote(final Note note)
    {
        final int index = findIndex(note);
        return Note.addStep(intervals.get(index), note);
    }

    public Note getRootNote()
    {
        return rootNote;
    }
}
