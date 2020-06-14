package com.gervin.jrail;

import java.util.function.Function;
import java.util.function.Supplier;

public interface Executor<T,R> {

    <V> Executor<T,V> thenExecute(Function<? super R, ? extends V> nextCommand);

    R orInFailureDo(Function<T, R> failureHandler);

    R getResultOrDefault(Supplier<R> defaultReturnValue);

    R getResult();
}
