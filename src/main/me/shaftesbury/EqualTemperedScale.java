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
    private final List<Step> steps;
    private final List<PitchClass> noteNames;
    private final List<Note> notes;

    public Iterator<Note> iterator()
    {
        return notes.iterator();
    }

    protected EqualTemperedScale(final Note rootNote, final List<Step> steps)
    {
        this.rootNote=rootNote;
        this.steps=CircularArrayList.asCircularArrayList(steps);
        this.noteNames = CircularArrayList.seqAsCircularArrayList(
                pitchClassGenerator(rootNote, steps));
        this.notes = noteGenerator(rootNote,steps);
    }

    private static List<PitchClass> pitchClassGenerator(final Note rootNote, final List<Step> steps) {
        return IterableHelper.create(steps).
                take(steps.size()-1).
                fold(new Func2<List<PitchClass>, Step, List<PitchClass>>() {
                    @Override
                    public List<PitchClass> apply(final List<PitchClass> notes, final Step step) {
                        final PitchClass last = Functional.last(notes);
                        return Functional.concat(notes, Arrays.asList(PitchClass.addStep(step, last)));
                    }
                }, Arrays.asList(Note.pitchClass(rootNote)));
    }

    private static List<Note> noteGenerator(final Note rootNote, final List<Step> steps) {
        return IterableHelper.create(steps).
                take(steps.size()-1).
                fold(new Func2<List<Note>, Step, List<Note>>() {
                    @Override
                    public List<Note> apply(final List<Note> notes, final Step step) {
                        final Note last = Functional.last(notes);
                        return Functional.concat(notes, Arrays.asList(Note.addStep(step, last)));
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
        return Note.subtractStep(steps.get(index), note);
    }

    public Note getFollowingNote(final Note note)
    {
        final int index = findIndex(note);
        return Note.addStep(steps.get(index), note);
    }

    public Note getRootNote()
    {
        return rootNote;
    }
}
