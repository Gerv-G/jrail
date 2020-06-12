package com.gervin.jrail;

import org.junit.Ignore;
import org.junit.Test;

public class TypeSafetyTest {

    @Test
    public void validRule() {
        int input = 5;

        Validator<Integer> result
            = Railway.forInput(input)
                  .thenValidateWith(x -> x < 6);

        assert result instanceof SuccessfulValidation;
    }

    @Ignore
    //added this rule as a documentation and not as an actual test
    public void invalidRule() {
        String input = "hello";

//        Operation<Integer> result
//            = Railway.forInput(input)
//                  .thenValidateWith(x -> x < 6);    //lambda expression here is invalid

        assert false;
    }
}
