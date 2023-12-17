package advent.year2023

import advent.readFile
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class Day17Test {
    private val sample = """
        2413432311323
        3215453535623
        3255245654254
        3446585845452
        4546657867536
        1438598798454
        4457876987766
        3637877979653
        4654967986887
        4564679986453
        1224686865563
        2546548887735
        4322674655533
    """.trimIndent()

    private val actual get() = readFile("2023/Day17")

    @Test
    fun sample1() {
        assertEquals(102, Day17.findMinimalHeatLoss(sample))
    }

    @Test
    fun actual1() {
        assertEquals(907, Day17.findMinimalHeatLoss(actual))
    }

    @Test
    fun sample2() {
        assertEquals(94, Day17.findMinimalHeatLossUltra(sample))
    }

    @Test
    fun actual2() {
        assertEquals(1057, Day17.findMinimalHeatLossUltra(actual))
    }
}
