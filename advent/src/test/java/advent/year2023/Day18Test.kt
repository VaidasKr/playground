package advent.year2023

import advent.readFile
import org.junit.Assert
import org.junit.Test

class Day18Test {
    private val sample = """
        R 6 (#70c710)
        D 5 (#0dc571)
        L 2 (#5713f0)
        D 2 (#d2c081)
        R 2 (#59c680)
        D 2 (#411b91)
        L 5 (#8ceee2)
        U 2 (#caa173)
        L 1 (#1b58a2)
        U 2 (#caa171)
        R 2 (#7807d2)
        U 3 (#a77fa3)
        L 2 (#015232)
        U 2 (#7a21e3)
    """.trimIndent()

    private val actual get() = readFile("2023/Day18")

    @Test
    fun sample1() {
        Assert.assertEquals(62, Day18.calcDigArea(sample))
    }

    @Test
    fun actual1() {
        Assert.assertEquals(38188, Day18.calcDigArea(actual))
    }

    @Test
    fun sample2() {
        Assert.assertEquals(952408144115, Day18.calcDigAreaHex(sample))
    }

    @Test
    fun actual2() {
        Assert.assertEquals(93325849869340, Day18.calcDigAreaHex(actual))
    }
}
