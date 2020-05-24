package com.gervin.jrail;

import java.util.function.Predicate;

public class Railway<T> {

    private T input;

    private Railway(T input) {
        this.input = input;
    }

    public static <T> Railway<T> forInput(T input) {
        return new Railway<>(input);
    }

    public OperationResult<T> thenValidateWith(Predicate<T> rule) {
        //TODO: turn this into a lambda
        return rule.test(this.input)
            ? new SuccessfulOperation<>(this.input)
            : new FailedOperation<>();
    }
}
