package com.gervin.jrail

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
            def result = Railway.forInput(input)
                .thenValidateWith(rule)
                .thenExecute(command)
                .getResult()

        then:
            result == null
    }
}