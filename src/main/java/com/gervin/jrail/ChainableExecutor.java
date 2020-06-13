package com.gervin.jrail;

import java.util.function.Function;
import java.util.function.Supplier;

public class ChainableExecutor<T,R> implements Executor<T,R> {

    private final T input;
    private final Function<T,R> command;

    ChainableExecutor(Function<T, R> command, T input) {
        this.command = command;
        this.input = input;
    }

    @Override
    public <V> Executor<T,V> thenExecute(Function<? super R, ? extends V> nextCommand) {
        return new ChainableExecutor<>(command.andThen(nextCommand), input);
    }

    @Override
    public R getResultOrDefault(Supplier<R> defaultReturnValue) {
        try {
            return command.apply(input);
        } catch (Exception e) {
            return defaultReturnValue.get();
        }
    }

    @Override
    public R getResult() {
        return command.apply(input);
    }
}
