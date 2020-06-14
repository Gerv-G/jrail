package com.gervin.jrail

import com.gervin.jrail.exception.FailedValidationException
import spock.lang.Specification

class RailwayTest extends Specification {

    def "Validators can be chained with Executors"() {
        given:
            def input = 3
            def rule1 = { x -> x > 0 }
            def rule2 = { x -> x < 10 }
            def command1 = { x -> x * x }
            def command2 = { x -> x / 4 }

        when:
            def result = Railway.forInput(input)
                    .thenValidateWith(rule1)
                    .thenValidateWith(rule2)
                    .thenExecute(command1)
                    .thenExecute(command2)
                    .getResult()

        then:
            result == 9/4
    }

    def "Executors should not be invoked if validation failed"() {
        given:
            def input = 4
            def rule = { x -> x >= 5 }
            def command = { x -> x - 5 }

        when:
            Railway.forInput(input)
                .thenValidateWith(rule)
                .thenExecute(command)
                .getResult()

        then:
            thrown FailedValidationException
    }

    def "Executors should use the given default value if validation failed"() {
        given:
            def input = 4
            def rule = { x -> x >= 5 }
            def command = { x -> x - 5 }
            def defaultValue = { -> 0 }

        when:
            def result = Railway.forInput(input)
                    .thenValidateWith(rule)
                    .orInFailureUse(defaultValue)
                    .thenExecute(command)
                    .getResult()

        then:
            result == -5
    }

    def "Executors should NOT use the given default value if validation succeeded"() {
        given:
            def input = 20
            def rule = { x -> x >= 5 }
            def command = { x -> x - 5 }
            def defaultValue = { -> 0 }

        when:
            def result = Railway.forInput(input)
                    .thenValidateWith(rule)
                    .orInFailureUse(defaultValue)
                    .thenExecute(command)
                    .getResult()

        then:
            result == 15
    }

    def "Default return value should be returned if execution is skipped due to failed validation"() {
        given:
            def input = 4
            def rule = { x -> x >= 5 }
            def command = { x -> x - 5 }
            def defaultReturnValue = { -> 0 }

        when:
            def result = Railway.forInput(input)
                    .thenValidateWith(rule)
                    .thenExecute(command)
                    .getResultOrDefault(defaultReturnValue)

        then:
            result == 0
    }
}