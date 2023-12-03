package advent.year2023

import advent.readFile
import org.junit.Assert
import org.junit.Test

class Day2Test {
    private val input1 = """
        Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
        Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
        Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
        Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
        Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
    """.trimIndent()

    @Test
    fun possibleGameSumSample() {
        val result = Day2.possibleGameIdSum(input1, 12, 13, 14)
        Assert.assertEquals(8, result)
    }

    @Test
    fun possibleGameIdSumActual() {
        val input = readFile("2023/Day2")
        val result = Day2.possibleGameIdSum(input, 12, 13, 14)
        Assert.assertEquals(2377, result)
    }

    @Test
    fun powerSumOfMinimalSetsSample() {
        val sum = Day2.powerSumOfMinimalSets(input1)
        Assert.assertEquals(2286, sum)
    }

    @Test
    fun powerSumOfMinimalSetsActual() {
        val sum = Day2.powerSumOfMinimalSets(readFile("2023/Day2"))
        Assert.assertEquals(71220, sum)
    }
}
