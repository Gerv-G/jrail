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
    }

    def "A satisfied validation rule should NOT return a failed operation"() {
        given: "A test input"
            def testInput = 5

        and: "A validation rule"
            def rule = { x -> x == 5 }

        when: "the validator is invoke"
            def result = Railway
                    .forInput(testInput)
                    .thenValidateWith(rule)

        then:
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
    }

    def "An unsatisfied validation rule should NOT return a successful operation"() {
        given: "A test input"
            def testInput = 4

        and: "A validation rule"
            def rule = { x -> x == 5 }

        when: "the validator is invoke"
            def result = Railway
                    .forInput(testInput)
                    .thenValidateWith(rule)

        then:
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
            def rule1 = { x -> x == 5 }
            def rule2 = { x -> x > 0 }
            def rule3 = { x -> x < 10 }

        when: "The validator is invoked"
            def result = Railway
                    .forInput(testInput)
                    .thenValidateWith(rule1)
                    .thenValidateWith(rule2)
                    .thenValidateWith(rule3)

        then:
            result instanceof FailedOperation
    }
}
