package com.gervin.jrail;

import java.util.function.Predicate;

public class SuccessfulOperation<T> implements OperationResult<T> {

    private T data;

    public SuccessfulOperation(T validData) {
        this.data = validData;
    }

    @Override
    public OperationResult<T> thenValidateWith(Predicate<T> rule) {
        return rule.test(data)
            ? new SuccessfulOperation<>(data)
            : new FailedOperation<>();
    }
}
