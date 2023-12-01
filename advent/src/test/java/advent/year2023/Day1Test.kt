package advent.year2023

import advent.readFile
import org.junit.Assert
import org.junit.Test

class Day1Test {
    @Test
    fun sample1() {
        val input = """
            1abc2
            pqr3stu8vwx
            a1b2c3d4e5f
            treb7uchet
        """.trimIndent()
        val result = Day1.part1(input)
        Assert.assertEquals(142, result)
    }

    @Test
    fun part1full() {
        val input = readFile("2023/Day1")
        val result = Day1.part1(input)
        Assert.assertEquals(
            54968,
            result
        )
    }

    @Test
    fun part2full() {
        val input = readFile("2023/Day1")
        val result = Day1.part2(input)
        Assert.assertEquals(
            54094,
            result
        )
    }

    @Test
    fun sample2() {
        val input = """
    two1nine
    eightwothree
    abcone2threexyz
    xtwone3four
    4nineeightseven2
    zoneight234
    7pqrstsixteen
""".trimIndent()
        val result = Day1.part2(input)
        Assert.assertEquals(281, result)
    }
}
