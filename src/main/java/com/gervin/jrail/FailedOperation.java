package com.gervin.jrail;

import java.util.function.Function;
import java.util.function.Predicate;

public class FailedOperation implements OperationResult {

    @Override
    public OperationResult thenValidateWith(Predicate<Object> rule) {
        return new FailedOperation();
    }
}
