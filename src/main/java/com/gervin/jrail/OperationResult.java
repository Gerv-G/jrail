package com.gervin.jrail;

import java.util.function.Predicate;

public interface OperationResult {
    OperationResult thenValidateWith(Predicate<Object> rule);
}
