package com.gervin.jrail;

import java.util.function.Function;
import java.util.function.Predicate;

public class Railway<T> {

    private T input;

    private Railway(T input) {
        this.input = input;
    }

    public static <T> Railway<T> forInput(T input) {
        return new Railway<>(input);
    }

    public Operation<T> thenValidateWith(Predicate<T> rule) {
        //TODO: turn this into a lambda
        return rule.test(input)
            ? new SuccessfulOperation<>(input)
            : new FailedOperation<>(input);
    }

    public <R> ChainableExecutor<T,R> thenExecute(Function<T,R> command) {
        return new ChainableExecutor<>(command, input);
    }
}
