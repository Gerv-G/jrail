package com.gervin.jrail;

import java.util.function.Function;

public class SkippedExecutor<T,R> implements Executor<T,R> {

    @Override
    public <V> Executor<T, V> thenExecute(Function<? super R, ? extends V> nextCommand) {
        return new SkippedExecutor<>();
    }

    @Override
    public R getResult() {
        return null;
    }
}
