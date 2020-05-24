package com.gervin.jrail

import spock.lang.Specification

class ExecutorTest extends Specification {

    def "Executor should be able to apply the command to the input"() {
        given: "A test input"
            def testInput = 5

        and: "A command"
            def command = { x -> x + 34 }

        when: "The executor is invoked"
            def result = Railway
                    .forInput(testInput)
                    .thenExecute(command)
                    .getResult()

        then:
            result == 39
    }

    def "Executors should allow chaining of commands"() {
        given: "A test input"
            def testInput = 5

        and: "A command"
            def command1 = { x -> x + 34 }
            def command2 = { x -> x - 25 }
            def command3 = { x -> x * 4}

        when: "The executor is invoked"
            def result = Railway
                    .forInput(testInput)
                    .thenExecute(command1)
                    .thenExecute(command2)
                    .thenExecute(command3)
                    .getResult()

        then:
            result == 56
    }

    def "Executors should be dependent on the order of execution"() {
        given: "A test input"
            def testInput = 5

        and: "A command"
            def command1 = { x -> x + 34 }
            def command2 = { x -> x - 19 }
            def command3 = { x -> x * 4}

        when: "The executor is invoked"
            def result1 = Railway
                    .forInput(testInput)
                    .thenExecute(command1)
                    .thenExecute(command2)
                    .thenExecute(command3)
                    .getResult()

            def result2 = Railway
                    .forInput(testInput)
                    .thenExecute(command1)
                    .thenExecute(command3)
                    .thenExecute(command2)
                    .getResult()

            def result3 = Railway
                    .forInput(testInput)
                    .thenExecute(command2)
                    .thenExecute(command1)
                    .thenExecute(command3)
                    .getResult()

        then:
            result1 == ((5 + 34) - 19) * 4
            result2 == ((5 + 34) * 4) - 19
            result3 == ((5 - 19) + 34) * 4
    }
}