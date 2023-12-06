package advent.year2023

import advent.readFile
import org.junit.Assert
import org.junit.Test

class Day6Test {
    val sampleInput = """
        Time:      7  15   30
        Distance:  9  40  200
    """.trimIndent()

    val actualInput get() = readFile("2023/Day6")

    @Test
    fun powerOfWinningOptionsInEachGameSample() {
        val power = Day6.powerOfWinningOptionsInEachGame(sampleInput)

        Assert.assertEquals(288, power)
    }

    @Test
    fun name() {
        val power = Day6.powerOfWinningOptionsInEachGame(actualInput)

        Assert.assertEquals(4403592, power)
    }

    @Test
    fun sample2() {
        val power = Day6.powerOfWinningOptionsSingleGame(sampleInput)

        Assert.assertEquals(71503, power)
    }

    @Test
    fun actual2() {
        val power = Day6.powerOfWinningOptionsSingleGame(actualInput)

        Assert.assertEquals(38017587, power)
    }
}
