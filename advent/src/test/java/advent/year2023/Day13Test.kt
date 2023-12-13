package advent.year2023

import advent.readFile
import org.junit.Assert.assertEquals
import org.junit.Test

class Day13Test {
    private val actual get() = readFile("2023/Day13")

    @Test
    fun sample1() {
        val input = """
            #.##..##.
            ..#.##.#.
            ##......#
            ##......#
            ..#.##.#.
            ..##..##.
            #.#.##.#.

            #...##..#
            #....#..#
            ..##..###
            #####.##.
            #####.##.
            ..##..###
            #....#..#
        """.trimIndent()

        assertEquals(405, Day13.findSummarizedMirrorSum(input, 0))
    }

    @Test
    fun actual1() {
        assertEquals(35360, Day13.findSummarizedMirrorSum(actual, 0))
    }

    @Test
    fun actual2() {
        assertEquals(36755, Day13.findSummarizedMirrorSum(actual, 1))
    }

    @Test
    fun sample2() {
        val input = """
            #.##..##.
            ..#.##.#.
            ##......#
            ##......#
            ..#.##.#.
            ..##..##.
            #.#.##.#.

            #...##..#
            #....#..#
            ..##..###
            #####.##.
            #####.##.
            ..##..###
            #....#..#
        """.trimIndent()

        assertEquals(400, Day13.findSummarizedMirrorSum(input, 1))
    }
}
