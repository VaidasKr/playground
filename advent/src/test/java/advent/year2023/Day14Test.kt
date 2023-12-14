package advent.year2023

import advent.readFile
import org.junit.Assert
import org.junit.Test

class Day14Test {
    private val sample = """
        O....#....
        O.OO#....#
        .....##...
        OO.#O....O
        .O.....O#.
        O.#..O.#.#
        ..O..#O..O
        .......O..
        #....###..
        #OO..#....
    """.trimIndent()
    private val actual get() = readFile("2023/Day14")

    @Test
    fun sample1() {
        Assert.assertEquals(136, Day14.loadAfterUpTilt(sample))
    }

    @Test
    fun sample2() {
        Assert.assertEquals(64, Day14.loadAfterCycles(sample, 1000000000))
    }

    @Test
    fun actual1() {
        Assert.assertEquals(108857, Day14.loadAfterUpTilt(actual))
    }

    @Test
    fun actual2() {
        Assert.assertEquals(95273, Day14.loadAfterCycles(actual, 1000000000))
    }
}
