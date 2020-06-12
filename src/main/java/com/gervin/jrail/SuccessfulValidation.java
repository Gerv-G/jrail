package com.gervin.jrail;

import java.util.function.Function;
import java.util.function.Predicate;

public class SuccessfulValidation<T> extends Validator<T> {

    SuccessfulValidation(T data) {
        super(data);
    }

    @Override
    public Validator<T> thenValidateWith(Predicate<T> rule) {
        return rule.test(data)
            ? new SuccessfulValidation<>(data)
            : new FailedValidation<>(data);
    }

    @Override
    public <R> ChainableExecutor<T,R> thenExecute(Function<T,R> command) {
        return new ChainableExecutor<>(command, data);
    }
}
