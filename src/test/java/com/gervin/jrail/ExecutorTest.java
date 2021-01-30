package com.gervin.jrail;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ExecutorTest {

    @Test
    void testCommandExecution() {
        int testInput = 5;
        Function<Integer, Integer> command = x -> x + 34;
        int result = Railway.forInput(testInput)
                         .thenExecute(command)
                         .getResult();

        assertEquals(39, result);
    }

    @Test
    void testCommandChaining() {
        int testInput = 5;
        Function<Integer, Integer> command1 = x -> x + 34;
        Function<Integer, Integer> command2 = x -> x - 25;
        Function<Integer, Integer> command3 = x -> x * 4;

        int result = Railway.forInput(testInput)
                         .thenExecute(command1)
                         .thenExecute(command2)
                         .thenExecute(command3)
                         .getResult();

        assertEquals(56, result);
    }

    @Test
    void testCommandOrderOfExecution() {
        int testInput = 5;

        Function<Integer, Integer> command1 = x -> x + 34;
        Function<Integer, Integer> command2 = x -> x - 19;
        Function<Integer, Integer> command3 = x -> x * 4;

        int expectedResult1 = ((5 + 34) - 19) * 4;
        int actualResult1 = Railway.forInput(testInput)
                                .thenExecute(command1)
                                .thenExecute(command2)
                                .thenExecute(command3)
                                .getResult();

        int expectedResult2 = ((5 + 34) * 4) - 19;
        int actualResult2 = Railway.forInput(testInput)
                                .thenExecute(command1)
                                .thenExecute(command3)
                                .thenExecute(command2)
                                .getResult();

        int expectedResult3 = ((5 - 19) + 34) * 4;
        int actualResult3 = Railway.forInput(testInput)
                                .thenExecute(command2)
                                .thenExecute(command1)
                                .thenExecute(command3)
                                .getResult();

        assertEquals(expectedResult1, actualResult1);
        assertEquals(expectedResult2, actualResult2);
        assertEquals(expectedResult3, actualResult3);
    }

    @Test
    void testDefaultValueOnExceptions() {
        String input = "foo";
        String defaultValue = "bar";
        Function<String, String> functionWithException = x -> {
            throw new RuntimeException();
        };

        String result = Railway.forInput(input)
                            .thenExecute(functionWithException)
                            .getResultOrDefault(() -> defaultValue);

        assertEquals("bar", result);
    }

    @Test
    void testExceptionHandling() {
        String input = "foo";
        Function<String, String> functionWithException = x -> {
            throw new RuntimeException();
        };
        Function<String, String> exceptionHandling = x -> x + "bar";

        String result = Railway.forInput(input)
                            .thenExecute(functionWithException)
                            .orInFailureDo(exceptionHandling);

        assertEquals("foobar", result);
    }

    @Test
    void testExceptionRethrow() {
        String input = "foo";
        Function<String, String> functionWithException = x -> {
            throw new RuntimeException();
        };

        Executable railwayWithException =
            () -> Railway.forInput(input)
                      .thenExecute(functionWithException)
                      .orInFailureThrow(CustomTestException::new);

        assertThrows(CustomTestException.class, railwayWithException);
    }
}
