package com.smithsmodding.armory.api.util.common;

import javax.annotation.Nonnull;

/**
 * Created by marcf on 1/31/2017.
 */
public class Triple<O,T,H> {
    @Nonnull
    private final O first;
    @Nonnull
    private final T second;
    @Nonnull
    private final H thrid;


    public Triple(@Nonnull O first,@Nonnull T second, @Nonnull H thrid) {
        this.first = first;
        this.second = second;
        this.thrid = thrid;
    }

    @Nonnull
    public O getFirst() {
        return first;
    }

    @Nonnull
    public T getSecond() {
        return second;
    }

    @Nonnull
    public H getThrid() {
        return thrid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Triple)) return false;

        Triple<?, ?, ?> triple = (Triple<?, ?, ?>) o;

        if (!getFirst().equals(triple.getFirst())) return false;
        if (!getSecond().equals(triple.getSecond())) return false;
        return getThrid().equals(triple.getThrid());
    }

    @Override
    public int hashCode() {
        int result = getFirst().hashCode();
        result = 31 * result + getSecond().hashCode();
        result = 31 * result + getThrid().hashCode();
        return result;
    }
}
