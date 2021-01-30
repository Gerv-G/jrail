package com.gervin.jrail;

import com.gervin.jrail.validator.FailedValidation;
import com.gervin.jrail.validator.SuccessfulValidation;
import org.junit.jupiter.api.Test;

import java.util.function.Predicate;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorTest {

    @Test
    void testSuccessfulValidation() {
        int testInput = 5;
        Predicate<Integer> rule = x -> x == 5;

        Validator<Integer> result = Railway.forInput(testInput)
                                        .thenValidateWith(rule);

        assertFalse(result instanceof FailedValidation);
        assertTrue(result instanceof SuccessfulValidation);
    }

    @Test
    void testFailedValidation() {
        int testInput = 4;
        Predicate<Integer> rule = x -> x == 5;

        Validator<Integer> result = Railway.forInput(testInput)
                                        .thenValidateWith(rule);

        assertFalse(result instanceof SuccessfulValidation);
        assertTrue(result instanceof FailedValidation);
    }

    @Test
    void testSuccessfulValidationForMultipleRules() {
        int testInput = 5;
        Predicate<Integer> rule1 = x -> x == 5;
        Predicate<Integer> rule2 = x -> x < 10;

        Validator<Integer> result = Railway.forInput(testInput)
                                        .thenValidateWith(rule1)
                                        .thenValidateWith(rule2);

        assertTrue(result instanceof SuccessfulValidation);
    }

    @Test
    void testFailedValidationForMultipleRules() {
        int testInput = 3;
        Predicate<Integer> rule1 = x -> x == 5;
        Predicate<Integer> rule2 = x -> x < 10;
        Predicate<Integer> rule3 = x -> x > 0;

        Validator<Integer> result = Railway.forInput(testInput)
                                        .thenValidateWith(rule1)
                                        .thenValidateWith(rule2)
                                        .thenValidateWith(rule3);

        assertTrue(result instanceof FailedValidation);
    }

    @Test
    void testOrderAgnosticValidation() {
        int testInput = 3;
        Predicate<Integer> satisfiedRule1 = x -> x == 5;
        Predicate<Integer> unsatisfiedRule = x -> x < 10;
        Predicate<Integer> satisfiedRule2 = x -> x > 0;

        Validator<Integer> result1 = Railway.forInput(testInput)
                                         .thenValidateWith(satisfiedRule1)
                                         .thenValidateWith(unsatisfiedRule)
                                         .thenValidateWith(satisfiedRule2);

        Validator<Integer> result2 = Railway.forInput(testInput)
                                         .thenValidateWith(satisfiedRule1)
                                         .thenValidateWith(satisfiedRule2)
                                         .thenValidateWith(unsatisfiedRule);

        Validator<Integer> result3 = Railway.forInput(testInput)
                                         .thenValidateWith(unsatisfiedRule)
                                         .thenValidateWith(satisfiedRule1)
                                         .thenValidateWith(satisfiedRule2);

        assertTrue(result1 instanceof FailedValidation);
        assertTrue(result2 instanceof FailedValidation);
        assertTrue(result3 instanceof FailedValidation);
    }

    @Test
    void testValidationInputImmutability() {
        int testInput = 5;
        Predicate<Integer> rule = x -> {
            x = x + 5;
            return true;
        };

        int result = Railway.forInput(testInput)
                         .thenValidateWith(rule)
                         .getData();

        assertEquals(testInput, result);
    }

    @Test
    void testDefaultReturnValueOnFailedValidation() {
        int testInput = 4;
        Predicate<Integer> rule = x -> x == 5;
        Supplier<Integer> defaultReturnValue = () -> 0;

        int result = Railway.forInput(testInput)
                         .thenValidateWith(rule)
                         .orInFailureUse(defaultReturnValue)
                         .getData();

        assertEquals(0, result);
    }

    @Test
    void testReturnValueOnSuccessfulValidation() {
        int testInput = 5;
        Predicate<Integer> rule = x -> x == 5;
        Supplier<Integer> defaultReturnValue = () -> 0;

        int result = Railway.forInput(testInput)
                         .thenValidateWith(rule)
                         .orInFailureUse(defaultReturnValue)
                         .getData();

        assertEquals(5, result);
    }
}
