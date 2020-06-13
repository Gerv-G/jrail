package com.gervin.jrail;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class FailedValidation<T> implements Validator<T> {

    private T invalidData;

    FailedValidation(T invalidData) {
        this.invalidData = invalidData;
    }

    @Override
    public Validator<T> thenValidateWith(Predicate<T> rule) {
        return new FailedValidation<>(invalidData);
    }

    @Override
    public <R> Executor<T, R> thenExecute(Function<T, R> command) {
        return new SkippedExecutor<>();
    }

    @Override
    public T getData() {
        return invalidData;
    }

    @Override
    public Validator<T> orInFailureUse(Supplier<T> supplier) {
        return new SuccessfulValidation<>(supplier.get());
    }
}
