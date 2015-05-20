package me.shaftesbury;

import me.shaftesbury.utils.functional.Func;
import me.shaftesbury.utils.functional.Func2;
import me.shaftesbury.utils.functional.Functional;
import me.shaftesbury.utils.functional.IterableHelper;

import java.util.Arrays;
import java.util.List;

public abstract class Scale
{
    private final Note rootNote;
    private final List<Step> steps;
    private final List<Note> notes;

    protected Scale(final Note rootNote, final List<Step> steps)
    {
        this.rootNote=rootNote;
        this.steps=steps;
        this.notes = CircularArrayList.seqAsCircularArrayList(
                        IterableHelper.create(steps).
                                take(steps.size()-1).
                                fold(new Func2<List<Note>, Step, List<Note>>() {
                                    @Override
                                    public List<Note> apply(final List<Note> notes, final Step step) {
                                        final Note last = Functional.last(notes);
                                        return Functional.concat(notes, Arrays.asList(Note.addStep(step, last)));
                                    }
                                }, Arrays.asList(rootNote)));

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
                return Note.asName(note).getValue0().equals(Note.asName(n).getValue0());
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
