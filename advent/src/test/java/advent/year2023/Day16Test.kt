package advent.year2023

import advent.readFile
import org.junit.Assert
import org.junit.Test

class Day16Test {
    private val sample = """
        .|...\....
        |.-.\.....
        .....|-...
        ........|.
        ..........
        .........\
        ..../.\\..
        .-.-/..|..
        .|....-|.\
        ..//.|....
    """.trimIndent()

    private val actual = readFile("2023/Day16")

    @Test
    fun sample1() {
        Assert.assertEquals(
            46,
            Day16.calculateEnergizedTiles(sample)
        )
    }

    @Test
    fun actual1() {
        Assert.assertEquals(
            6994,
            Day16.calculateEnergizedTiles(actual)
        )
    }

    @Test
    fun sample2() {
        Assert.assertEquals(
            51,
            Day16.bestConfigurationOfEnergizedTiles(sample)
        )
    }

    @Test
    fun actual2() {
        Assert.assertEquals(
            7488,
            Day16.bestConfigurationOfEnergizedTiles(actual)
        )
    }
}
