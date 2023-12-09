package advent.year2023

import advent.readFile
import org.junit.Assert
import org.junit.Test

class Day9Test {
    private val samples = listOf(
        "0 3 6 9 12 15",
        "1 3 6 10 15 21",
        "10 13 16 21 30 45"
    )
    private val sampleAnswers = listOf(18L, 28L, 68L)
    private val sampleAnswers2 = listOf(-3L, 0, 5)

    private val actual = readFile("2023/Day9")

    @Test
    fun getNextNumber() {
        for (i in 0 until 2) {
            val next = Day9.predictNextNumber(samples[i])

            Assert.assertEquals(sampleAnswers[i], next)
        }
    }

    @Test
    fun sumOfNextNumbers() {
        val sum = Day9.sumOfNextNumbers(samples.joinToString("\n"))

        Assert.assertEquals(sampleAnswers.sum(), sum)
    }

    @Test
    fun actual1() {
        val sum = Day9.sumOfNextNumbers(actual)

        Assert.assertEquals(1877825184, sum)
    }

    @Test
    fun getPreviousNumber() {
        for (i in 0 until 2) {
            val prev = Day9.predictPrevNumber(samples[i])

            Assert.assertEquals("for line ${samples[i]}", sampleAnswers2[i], prev)
        }
    }

    @Test
    fun sumOfPrevNumbers() {
        val sum = Day9.sumOfPrevNumbers(samples.joinToString("\n"))

        Assert.assertEquals(sampleAnswers2.sum(), sum)
    }

    @Test
    fun actual2() {
        val sum = Day9.sumOfPrevNumbers(actual)

        Assert.assertEquals(1108, sum)
    }
}
