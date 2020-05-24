package com.gervin.jrail;

import java.util.function.Function;

public class Executor<T,R> {

    private final T input;
    private final Function<T,R> command;

    Executor(Function<T, R> command, T input) {
        this.command = command;
        this.input = input;
    }

    public <V> Executor<T,V> thenExecute(Function<? super R, ? extends V> nextCommand) {
        return new Executor<>(command.andThen(nextCommand), input);
    }

    public R getResult() {
        return command.apply(input);
    }
}
