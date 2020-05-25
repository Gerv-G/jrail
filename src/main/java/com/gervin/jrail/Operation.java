package com.gervin.jrail;

import java.util.function.Function;
import java.util.function.Predicate;

public abstract class Operation<T> {

    protected T data;

    protected Operation(T data) {
        this.data = data;
    }

    abstract Operation<T> thenValidateWith(Predicate<T> rule);

    abstract <R> Executor<T,R> thenExecute(Function<T,R> command);
}
