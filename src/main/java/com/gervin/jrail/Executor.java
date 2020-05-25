package com.gervin.jrail;

import java.util.function.Function;

public interface Executor<T,R> {

    <V> Executor<T,V> thenExecute(Function<? super R, ? extends V> nextCommand);

    R getResult();
}
