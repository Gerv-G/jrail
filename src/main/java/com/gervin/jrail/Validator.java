package com.gervin.jrail;

import java.util.function.Function;
import java.util.function.Predicate;

public abstract class Validator<T> {

    protected T data;

    protected Validator(T data) {
        this.data = data;
    }

    abstract Validator<T> thenValidateWith(Predicate<T> rule);

    abstract <R> Executor<T,R> thenExecute(Function<T,R> command);
}
