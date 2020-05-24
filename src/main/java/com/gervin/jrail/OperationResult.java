package com.gervin.jrail;

import java.util.function.Function;
import java.util.function.Predicate;

public interface OperationResult<T> {
    OperationResult<T> thenValidateWith(Predicate<T> rule);

    <R> Executor<T,R> thenExecute(Function<T,R> command);
}
