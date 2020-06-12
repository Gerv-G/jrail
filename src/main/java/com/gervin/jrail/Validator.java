package com.gervin.jrail;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public interface Validator<T> extends Operator<T> {

    Validator<T> thenValidateWith(Predicate<T> rule);

    <R> Executor<T,R> thenExecute(Function<T,R> command);

    T orInFailureGet(Supplier<T> supplier);
}
