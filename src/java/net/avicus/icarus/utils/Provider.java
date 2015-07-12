package net.avicus.icarus.utils;

public interface Provider<R,I> {

    public R get(I input);

    public boolean isStatic();

}
