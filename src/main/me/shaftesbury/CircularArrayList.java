package me.shaftesbury;

import me.shaftesbury.utils.functional.Functional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class CircularArrayList<E> extends ArrayList<E>
{
    private CircularArrayList(final List<E> list) { super(list); }

    public static <T>List<T> asCircularArrayList(final T... args)
    {
        return new CircularArrayList<T>(Collections.unmodifiableList(Arrays.asList(args)));
    }

    public static <T>List<T> asCircularArrayList(final List<T> args)
    {
        return new CircularArrayList<T>(Collections.unmodifiableList(args));
    }

    public static <T>List<T> seqAsCircularArrayList(final Iterable<T> arg)
    {
        return new CircularArrayList<T>(Collections.unmodifiableList(Functional.toList(arg)));
    }

    @Override
    public E get(final int index)
    {
        final int modIndex = index % size();
        if(modIndex<0)
            return super.get(size() - modIndex);
        else
            return super.get(modIndex);
    }
}
