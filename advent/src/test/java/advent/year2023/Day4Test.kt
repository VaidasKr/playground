package advent.year2023

import advent.readFile
import org.junit.Assert
import org.junit.Test

class Day4Test {
    private val sampleInput = """
    Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
    Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19
    Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1
    Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83
    Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36
    Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11
""".trimIndent()

    @Test
    fun sumOfCardScoresSample() {
        val result = Day4.calculateCardScoreSum(sampleInput)
        Assert.assertEquals(13L, result)
    }

    @Test
    fun sumOfCardScoreActual() {
        val result = Day4.calculateCardScoreSum(readFile("2023/Day4"))
        Assert.assertEquals(20407, result)
    }

    @Test
    fun sumOfScratchcards() {
        val result = Day4.calculateScratchCardSum(sampleInput)
        Assert.assertEquals(30L, result)
    }

    @Test
    fun sumOfScratchcardsActual() {
        val result = Day4.calculateScratchCardSum(readFile("2023/Day4"))
        Assert.assertEquals(23806951, result)
    }
}
