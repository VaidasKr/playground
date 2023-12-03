package advent.year2023

import advent.readFile
import org.junit.Assert
import org.junit.Test

class Day3Test {
    private val sampleInput = """
        467..114..
        ...*......
        ..35..633.
        ......#...
        617*......
        .....+.58.
        ..592.....
        ......755.
        ...${'$'}.*....
        .664.598..
    """.trimIndent()

    @Test
    fun sampleInputPart1() {
        val result = Day3.findEnginePartNumberSum(sampleInput)

        Assert.assertEquals(4361, result)
    }

    @Test
    fun sampleSingleLineOnlyLeft() {
        val result = Day3.findEnginePartNumberSum("123*")

        Assert.assertEquals(123, result)
    }

    @Test
    fun sampleSingleLineOnlyRight() {
        val result = Day3.findEnginePartNumberSum("*325")

        Assert.assertEquals(325, result)
    }

    @Test
    fun sampleLineAboveSingleNumber() {
        val result = Day3.findEnginePartNumberSum("1234567\n...*...")

        Assert.assertEquals(1234567, result)
    }

    @Test
    fun sampleLineAboveSingleNumberLeft() {
        val result = Day3.findEnginePartNumberSum("123....\n...*...")

        Assert.assertEquals(123, result)
    }

    @Test
    fun sampleLineAboveSingleNumberLeft2() {
        val result = Day3.findEnginePartNumberSum("1234...\n...*...")

        Assert.assertEquals(1234, result)
    }

    @Test
    fun sampleLineAboveSingleNumberRight() {
        val result = Day3.findEnginePartNumberSum("....567\n...*...")

        Assert.assertEquals(567, result)
    }

    @Test
    fun sampleLineAboveSingleNumberRight2() {
        val result = Day3.findEnginePartNumberSum("...4567\n...*...")

        Assert.assertEquals(4567, result)
    }

    @Test
    fun actualPart1() {
        val result = Day3.findEnginePartNumberSum(readFile("2023/Day3"))

        Assert.assertEquals(533775, result)
    }

    @Test
    fun samplePart2() {
        val result = Day3.findGearRatioSum(sampleInput)

        Assert.assertEquals(467835, result)
    }

    @Test
    fun actualPart2() {
        val result = Day3.findGearRatioSum(readFile("2023/Day3"))

        Assert.assertEquals(78236071, result)
    }
}
