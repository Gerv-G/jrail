package com.gervin.jrail;

import java.util.function.Function;
import java.util.function.Predicate;

public class SuccessfulOperation<T> extends Operation<T> {

    SuccessfulOperation(T data) {
        super(data);
    }

    @Override
    public Operation<T> thenValidateWith(Predicate<T> rule) {
        return rule.test(data)
            ? new SuccessfulOperation<>(data)
            : new FailedOperation<>(data);
    }

    public <R> Executor<T,R> thenExecute(Function<T,R> command) {
        return new Executor<>(command, data);
    }
}
