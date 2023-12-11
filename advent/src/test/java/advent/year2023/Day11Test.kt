package advent.year2023

import advent.readFile
import org.junit.Assert
import org.junit.Test

class Day11Test {
    private val actual get() = readFile("2023/Day11")
    private val sample = """
        ...#......
        .......#..
        #.........
        ..........
        ......#...
        .#........
        .........#
        ..........
        .......#..
        #...#.....
    """.trimIndent()

    @Test
    fun findShortestPathSumAfterExpanding() {
        Assert.assertEquals(374, Day11.shortestPathSumAfterExpanding(sample, 2))
        Assert.assertEquals(1030, Day11.shortestPathSumAfterExpanding(sample, 10))
        Assert.assertEquals(8410, Day11.shortestPathSumAfterExpanding(sample, 100))
    }

    @Test
    fun findShortedPathSumAfterExpandingTwice() {
        val sum = Day11.shortestPathSumAfterExpanding(actual, 2)

        Assert.assertEquals(9177603, sum)
    }

    @Test
    fun findShortedPathSumAfterExpandingSpaces1000000() {
        val sum = Day11.shortestPathSumAfterExpanding(actual, 1000000)

        Assert.assertEquals(632003913611, sum)
    }
}
