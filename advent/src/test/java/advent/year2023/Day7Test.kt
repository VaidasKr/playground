package advent.year2023

import advent.readFile
import org.junit.Assert
import org.junit.Test

class Day7Test {
    private val sampleInput = """
        32T3K 765
        T55J5 684
        KK677 28
        KTJJT 220
        QQQJA 483
    """.trimIndent()

    private val actualInput get() = readFile("2023/Day7")

    @Test
    fun samplePart1() {
        val result = Day7.calculateWinningBidSum(sampleInput)

        Assert.assertEquals(6440, result)
    }

    @Test
    fun actualPart1() {
        val result = Day7.calculateWinningBidSum(actualInput)

        Assert.assertEquals(248179786, result)
    }

    @Test
    fun samplePart2() {
        val result = Day7.calculateWinningBidSumWithJokerRule(sampleInput)

        Assert.assertEquals(5905, result)
    }

    @Test
    fun actualPart2() {
        val result = Day7.calculateWinningBidSumWithJokerRule(actualInput)

        Assert.assertEquals(247885995, result)
    }
}
