package com.gervin.jrail

import spock.lang.Specification

class RailwayTest extends Specification {

    def "Validators can be chained with Executors"() {
        given:
            int input = 3
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
}
