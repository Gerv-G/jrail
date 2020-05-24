package com.gervin.jrail;

import java.util.function.Predicate;

public class Railway {

    private Object input;

    private Railway(Object input) {
        this.input = input;
    }

    public static Railway forInput(Object input) {
        return new Railway(input);
    }

    public OperationResult thenValidateWith(Predicate<Object> rule) {
        //TODO: turn this into a lambda
        return rule.test(this.input)
            ? new SuccessfulOperation(this.input)
            : new FailedOperation();
    }
}
