package net.avicus.icarus.utils;

import lombok.ToString;

@ToString
public class StaticProvider<R,I> implements Provider<R,I> {

    private final R response;

    public StaticProvider(R response) {
        this.response = response;
    }

    @Override
    public R get(I input) {
        return response;
    }

    @Override
    public boolean isStatic() {
        return true;
    }
}
