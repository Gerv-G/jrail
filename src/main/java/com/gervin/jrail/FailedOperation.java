package com.gervin.jrail;

import java.util.function.Function;
import java.util.function.Predicate;

public class FailedOperation<T> extends Operation<T> {

    FailedOperation(T data) {
        super(data);
    }

    @Override
    public Operation<T> thenValidateWith(Predicate<T> rule) {
        return new FailedOperation<>(data);
    }

    @Override
    public <R> Executor<T, R> thenExecute(Function<T, R> command) {
        return null;
    }
}
