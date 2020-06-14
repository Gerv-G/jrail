package com.gervin.jrail.validator;

import com.gervin.jrail.Validator;
import com.gervin.jrail.executor.ChainableExecutor;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class SuccessfulValidation<T> implements Validator<T> {

    private T data;

    public SuccessfulValidation(T data) {
        this.data = data;
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

    @Override
    public T getData() {
        return data;
    }

    @Override
    public Validator<T> orInFailureUse(Supplier<T> supplier) {
        return new SuccessfulValidation<>(data);
    }
}
