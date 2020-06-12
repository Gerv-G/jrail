package com.gervin.jrail;

import java.util.function.Function;
import java.util.function.Predicate;

public class FailedValidation<T> extends Validator<T> {

    FailedValidation(T data) {
        super(data);
    }

    @Override
    public Validator<T> thenValidateWith(Predicate<T> rule) {
        return new FailedValidation<>(data);
    }

    @Override
    public <R> Executor<T, R> thenExecute(Function<T, R> command) {
        return new SkippedExecutor<>();
    }
}
