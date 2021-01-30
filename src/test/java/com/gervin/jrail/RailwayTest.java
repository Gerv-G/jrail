package com.gervin.jrail;

import com.gervin.jrail.exception.FailedValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

class RailwayTest {

    @Test
    void testChainedValidatorsAndExecutors() {
        int input = 3;
        Predicate<Integer> rule1 = x -> x > 0;
        Predicate<Integer> rule2 = x -> x < 10;
        Function<Integer, Integer> command1 = x -> x * x;
        Function<Integer, Integer> command2 = x -> x / 4;

        int result = Railway.forInput(input)
                         .thenValidateWith(rule1)
                         .thenValidateWith(rule2)
                         .thenExecute(command1)
                         .thenExecute(command2)
                         .getResult();

        assertEquals(9 / 4, result);
    }

    @Test
    void testSkippedExecutorOnFailedValidationWithNoAlternateInputAndNoDefaultReturn() {
        int input = 4;
        Predicate<Integer> rule = x -> x >= 5;
        Function<Integer, Integer> command = x -> x - 5;

        Executable railwayWithFailedValidation =
            () -> Railway.forInput(input)
                      .thenValidateWith(rule)
                      .thenExecute(command)
                      .getResult();

        assertThrows(FailedValidationException.class, railwayWithFailedValidation);
    }

    @Test
    void testAlternativeInputOnFailedValidation() {
        int input = 4;
        Predicate<Integer> rule = x -> x >= 5;
        Function<Integer, Integer> command = x -> x - 5;
        Supplier<Integer> alternativeInput = () -> 0;

        int result = Railway.forInput(input)
                         .thenValidateWith(rule)
                         .orInFailureUse(alternativeInput)
                         .thenExecute(command)
                         .getResult();

        assertEquals(-5, result);
    }

    @Test
    void testActualInputOnSuccessfulValidation() {
        int input = 20;
        Predicate<Integer> rule = x -> x >= 5;
        Function<Integer, Integer> command = x -> x - 5;
        Supplier<Integer> alternativeInput = () -> 0;

        int result = Railway.forInput(input)
                         .thenValidateWith(rule)
                         .orInFailureUse(alternativeInput)
                         .thenExecute(command)
                         .getResult();

        assertEquals(15, result);
    }

    @Test
    void testDefaultReturnValueOnFailedExecution() {
        int input = 4;
        Predicate<Integer> rule = x -> x >= 5;
        Function<Integer, Integer> command = x -> x - 5;
        Supplier<Integer> defaultReturnValue = () -> 0;

        int result = Railway.forInput(input)
                         .thenValidateWith(rule)
                         .thenExecute(command)
                         .getResultOrDefault(defaultReturnValue);

        assertEquals(defaultReturnValue.get(), result);
    }

    @Test
    void testSuccessfulValidationAndSuccessfulExecution() {
        int input = 10;
        Supplier<Integer> alternativeInput = () -> 6;
        Predicate<Integer> rule = x -> x >= 5;
        Function<Integer, Integer> command = x -> x - 5;
        Supplier<Integer> defaultReturnValue = () -> 0;

        int result = Railway.forInput(input)
            .thenValidateWith(rule)
            .orInFailureUse(alternativeInput)
            .thenExecute(command)
            .getResultOrDefault(defaultReturnValue);

        assertEquals(5, result);
    }

    @Test
    void testSuccessfulValidationAndFailedExecution() {
        int input = 10;
        Supplier<Integer> alternativeInput = () -> 6;
        Predicate<Integer> rule = x -> x >= 5;
        Function<Integer, Integer> command = x -> { throw new RuntimeException(); };
        Supplier<Integer> defaultReturnValue = () -> 0;

        int result = Railway.forInput(input)
                         .thenValidateWith(rule)
                         .orInFailureUse(alternativeInput)
                         .thenExecute(command)
                         .getResultOrDefault(defaultReturnValue);

        assertEquals(defaultReturnValue.get(), result);
    }

    @Test
    void testFailedValidationAndSuccessfulExecution() {
        int input = 4;
        Supplier<Integer> alternativeInput = () -> 6;
        Predicate<Integer> rule = x -> x >= 5;
        Function<Integer, Integer> command = x -> x - 5;
        Supplier<Integer> defaultReturnValue = () -> 0;

        int result = Railway.forInput(input)
                         .thenValidateWith(rule)
                         .orInFailureUse(alternativeInput)
                         .thenExecute(command)
                         .getResultOrDefault(defaultReturnValue);

        assertEquals(1, result);
    }

    @Test
    void testFailedValidationAndFailedExecution() {
        int input = 4;
        Supplier<Integer> alternativeInput = () -> 6;
        Predicate<Integer> rule = x -> x >= 5;
        Function<Integer, Integer> command = x -> { throw new RuntimeException(); };
        Supplier<Integer> defaultReturnValue = () -> 0;

        int result = Railway.forInput(input)
                         .thenValidateWith(rule)
                         .orInFailureUse(alternativeInput)
                         .thenExecute(command)
                         .getResultOrDefault(defaultReturnValue);

        assertEquals(defaultReturnValue.get(), result);
    }
}
