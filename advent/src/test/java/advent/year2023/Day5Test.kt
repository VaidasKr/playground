package advent.year2023

import advent.readFile
import org.junit.Assert
import org.junit.Test

class Day5Test {
    private val sampleInput = """
        seeds: 79 14 55 13

        seed-to-soil map:
        50 98 2
        52 50 48

        soil-to-fertilizer map:
        0 15 37
        37 52 2
        39 0 15

        fertilizer-to-water map:
        49 53 8
        0 11 42
        42 0 7
        57 7 4

        water-to-light map:
        88 18 7
        18 25 70

        light-to-temperature map:
        45 77 23
        81 45 19
        68 64 13

        temperature-to-humidity map:
        0 69 1
        1 0 69

        humidity-to-location map:
        60 56 37
        56 93 4
    """.trimIndent()

    val actualInput get() = readFile("2023/Day5")

    @Test
    fun sampleFindLowestLocationNumber() {
        val number = Day5.findLowestLocationNumber(sampleInput)

        Assert.assertEquals(35, number)
    }

    @Test
    fun actualFindLowestLocationNumber() {
        val number = Day5.findLowestLocationNumber(actualInput)

        Assert.assertEquals(174137457, number)
    }

    @Test
    fun sampleFindLowestLocationNumberRanges() {
        val number = Day5.findLowestLocationNumberWithRanges(sampleInput)

        Assert.assertEquals(46, number)
    }

    @Test
    fun actualFindLowestLocationNumberRanges() {
        val number = Day5.findLowestLocationNumberWithRanges(actualInput)

        Assert.assertEquals(1493866, number)
    }
}
