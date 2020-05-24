package com.gervin.jrail;

import java.util.function.Predicate;

public interface OperationResult<T> {
    OperationResult<T> thenValidateWith(Predicate<T> rule);
}
