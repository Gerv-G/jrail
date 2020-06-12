package com.gervin.jrail

import spock.lang.Specification

class ValidatorTest extends Specification {

    def "A satisfied validation rule should return a successful operation"() {
        given: "A test input"
            def testInput = 5

        and: "A validation rule"
            def rule = { x -> x == 5 }

        when: "the validator is invoke"
            def result = Railway
                    .forInput(testInput)
                    .thenValidateWith(rule)

        then:
            result instanceof SuccessfulOperation
            !(result instanceof FailedOperation)
    }

    def "An unsatisfied validation rule should return a failed operation"() {
        given: "A test input"
            def testInput = 4

        and: "A validation rule"
            def rule = { x -> x == 5 }

        when: "the validator is invoke"
            def result = Railway
                    .forInput(testInput)
                    .thenValidateWith(rule)

        then:
            result instanceof FailedOperation
            !(result instanceof SuccessfulOperation)
    }

    def "Result should show a successful operation if all validation rules are satisfied"() {
        given: "A test input"
            def testInput = 5

        and: "A validation rule"
            def rule1 = { x -> x == 5 }
            def rule2 = { x -> x < 10 }

        when: "The validator is invoked"
            def result = Railway
                    .forInput(testInput)
                    .thenValidateWith(rule1)
                    .thenValidateWith(rule2)

        then:
            result instanceof SuccessfulOperation
    }

    def "Result should show a failed operation if at least one rule is not satisfied"() {
        given: "A test input"
            def testInput = 3

        and: "A validation rule"
            def rule1 = { x -> x == 5 }
            def rule2 = { x -> x < 10 }
            def rule3 = { x -> x > 0 }

        when: "The validator is invoked"
            def result = Railway
                    .forInput(testInput)
                    .thenValidateWith(rule1)
                    .thenValidateWith(rule2)
                    .thenValidateWith(rule3)

        then:
            result instanceof FailedOperation
    }

    def "Order of rules should not matter"() {
        given: "A test input"
            def testInput = 3

        and: "A validation rule"
            def satisfiedRule1 = { x -> x == 5 }
            def unsatisfiedRule = { x -> x < 10 }
            def satisfiedRule2 = { x -> x > 0 }

        when: "The unsatisfied rule is last"
            def result1 = Railway.forInput(testInput)
                    .thenValidateWith(satisfiedRule1)
                    .thenValidateWith(unsatisfiedRule)
                    .thenValidateWith(satisfiedRule2)

        and: "The unsatisfied rule is in the middle"
            def result2 = Railway.forInput(testInput)
                    .thenValidateWith(satisfiedRule1)
                    .thenValidateWith(satisfiedRule2)
                    .thenValidateWith(unsatisfiedRule)

        and: "The unsatisfied rule is first"
            def result3 = Railway.forInput(testInput)
                    .thenValidateWith(unsatisfiedRule)
                    .thenValidateWith(satisfiedRule1)
                    .thenValidateWith(satisfiedRule2)

        then:
            result1 instanceof FailedOperation
            result2 instanceof FailedOperation
            result3 instanceof FailedOperation
    }

    def "Validator should be type safe"() {
        given: "A test input"
            def testInput = "Hello"

        and: "An inappropriate rule"
            def rule = { x -> x == 7 }

        when: "the validator is invoked"
            def result = Railway
                    .forInput(testInput)
                    .thenValidateWith(rule)

        then:
            //This should actually never happen
            //Compile time checking should have never allowed the usage of the rule above
            //See TypeSafetyTest.java
            result instanceof FailedOperation
    }
}
