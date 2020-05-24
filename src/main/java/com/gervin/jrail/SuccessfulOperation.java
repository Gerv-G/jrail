package com.gervin.jrail;

import java.util.function.Predicate;

public class SuccessfulOperation implements OperationResult {

    private Object data;

    public SuccessfulOperation(Object validData) {
        this.data = validData;
    }

    @Override
    public OperationResult thenValidateWith(Predicate<Object> rule) {
        return rule.test(data)
            ? new SuccessfulOperation(data)
            : new FailedOperation();
    }
}
