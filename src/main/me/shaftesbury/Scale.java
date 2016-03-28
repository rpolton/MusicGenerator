package me.shaftesbury;

import me.shaftesbury.utils.functional.Func2;
import me.shaftesbury.utils.functional.Functional;
import me.shaftesbury.utils.functional.IterableHelper;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public abstract class Scale implements Iterable<Note>
{
    private final Note rootNote;
    private final List<Step> steps;
    private final List<PitchClass> noteNames;
    private final List<Note> notes;

    public Iterator<Note> iterator()
    {
        return notes.iterator();
    }

    protected Scale(final Note rootNote, final List<Step> steps)
    {
        this.rootNote=rootNote;
        this.steps=CircularArrayList.asCircularArrayList(steps);
        this.noteNames = CircularArrayList.seqAsCircularArrayList(
                        IterableHelper.create(steps).
                                take(steps.size()-1).
                                fold(new Func2<List<PitchClass>, Step, List<PitchClass>>() {
                                    @Override
                                    public List<PitchClass> apply(final List<PitchClass> notes, final Step step) {
                                        final PitchClass last = Functional.last(notes);
                                        return Functional.concat(notes, Arrays.asList(PitchClass.addStep(step, last)));
                                    }
                                }, Arrays.asList(Note.pitchClass(rootNote))));
        this.notes = Arrays.asList(rootNote);

    }

    public Note getNote(int index)
    {
        return notes.get(index);
    }

//    private int findIndex(final Note note)
//    {
//        return Functional.findIndex(new Func<Note, Boolean>() {
//            @Override
//            public Boolean apply(final Note n) {
//                return Note.asName(note).getValue0().equals(Note.asName(n).getValue0());
//            }
//        }, notes);
//    }

//    public Note getPrecedingNote(final Note note)
//    {
//        final int index = findIndex(note);
//        return Note.subtractStep(steps.get(index), note);
//    }

//    public Note getFollowingNote(final Note note)
//    {
//        final int index = findIndex(note);
//        return Note.addStep(steps.get(index), note);
//    }

    public Note getRootNote()
    {
        return rootNote;
    }
}
