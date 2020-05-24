package com.gervin.jrail;

import java.util.function.Function;
import java.util.function.Predicate;

public class FailedOperation<T> implements OperationResult<T> {

    @Override
    public OperationResult<T> thenValidateWith(Predicate<T> rule) {
        return new FailedOperation<>();
    }

    @Override
    public <R> Executor<T, R> thenExecute(Function<T, R> command) {
        return null;
    }
}
