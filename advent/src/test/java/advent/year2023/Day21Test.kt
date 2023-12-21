package advent.year2023

import advent.assert
import advent.readFile
import org.junit.Test

class Day21Test {
    private val sample = """
        ...........
        .....###.#.
        .###.##..#.
        ..#.#...#..
        ....#.#....
        .##..S####.
        .##..#...#.
        .......##..
        .##.#.####.
        .##..##.##.
        ...........
    """.trimIndent()

    private val actual get() = readFile(2023, 21)

    @Test
    fun part1_sample() {
        Day21.possiblePositionsAfterSteps(sample, 6).assert(16)
    }

    @Test
    fun part2_sample() {
        Day21.possiblePositionsAfterStepsInfinite(sample, 5000).assert(16733044)
    }

    @Test
    fun part1_actual() {
        Day21.possiblePositionsAfterSteps(actual, 64).assert(3773)
    }

    @Test
    fun part2_actual() {
        Day21.possiblePositionsAfterStepsInfinite(actual, 26501365).assert(3773)
    }

    @Test
    fun part2_cheat() {
        var sum = 310018L
        val difDif = 122296
        var index = 589
        var lastDif = 214220

        while (index < 26501365) {
            index += 262
            lastDif += difDif
            sum += lastDif
//            println("$index $sum")
        }

        println(sum)
    }

    // 26501365, 101150
}
