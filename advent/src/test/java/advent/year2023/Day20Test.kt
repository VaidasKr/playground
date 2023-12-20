package advent.year2023

import advent.assert
import advent.readFile
import org.junit.Test

class Day20Test {
    private val sample1 = """
        broadcaster -> a, b, c
        %a -> b
        %b -> c
        %c -> inv
        &inv -> a
    """.trimIndent()

    private val sample2 = """
        broadcaster -> a
        %a -> inv, con
        &inv -> b
        %b -> con
        &con -> output
    """.trimIndent()

    private val actual get() = readFile(2023, 20)

    @Test
    fun part1_sample_1() {
        Day20.pulseMultiplicationAfter(sample1, 1000).assert(32000000)
    }

    @Test
    fun part1_sample_2() {
        Day20.pulseMultiplicationAfter(sample2, 1000).assert(11687500)
    }

    @Test
    fun part1_actual() {
        Day20.pulseMultiplicationAfter(actual, 1000).assert(670984704)
    }

    @Test
    fun part2_actual() {
        Day20.findPressedToRx(actual).assert(262775362119547)
    }
}
