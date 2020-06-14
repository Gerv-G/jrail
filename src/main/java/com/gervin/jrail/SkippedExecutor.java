package com.gervin.jrail;

import java.util.function.Function;
import java.util.function.Supplier;

public class SkippedExecutor<T,R> implements Executor<T,R> {

    @Override
    public <V> Executor<T, V> thenExecute(Function<? super R, ? extends V> nextCommand) {
        return new SkippedExecutor<>();
    }

    @Override
    public R orInFailureDo(Function<T, R> failureHandler) {
        throw new FailedValidationException();
    }

    @Override
    public R getResultOrDefault(Supplier<R> defaultReturnValue) {
        return defaultReturnValue.get();
    }

    @Override
    public R getResult() {
        throw new FailedValidationException();
    }
}
