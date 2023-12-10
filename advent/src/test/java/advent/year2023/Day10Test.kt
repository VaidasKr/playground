package advent.year2023

import advent.readFile
import org.junit.Assert
import org.junit.Test

class Day10Test {
    val actual get() = readFile("2023/Day10")

    @Test
    fun `part1 sample2`() {
        val steps = Day10.findFurthestPoint(
            """
        .....
        .S-7.
        .|.|.
        .L-J.
        .....
    """.trimIndent()
        )

        Assert.assertEquals(4, steps)
    }

    @Test
    fun `part1 sample1`() {
        val steps = Day10.findFurthestPoint(
            """
    ..F7.
    .FJ|.
    SJ.L7
    |F--J
    LJ...
    """.trimIndent()
        )

        Assert.assertEquals(8, steps)
    }

    @Test
    fun actual1() {
        val steps = Day10.findFurthestPoint(actual)

        Assert.assertEquals(6886, steps)
    }

    @Test
    fun `part2 sample`() {
        val steps = Day10.countInsidePoints(
            """..........
.S------7.
.|F----7|.
.||....||.
.||....||.
.|L-7F-J|.
.|..||..|.
.L--JL--J.
.........."""
        )

        Assert.assertEquals(4, steps)
    }

    @Test
    fun `part2 sample2`() {
        val steps = Day10.countInsidePoints(
            """..........
.F----7F7F7F7F-7....
.|F--7||||||||FJ....
.||.FJ||||||||L7....
FJL7L7LJLJ||LJ.L-7..
L--J.L7...LJS7F-7L7.
....F-J..F7FJ|L7L7L7
....L7.F7||L7|.L7L7|
.....|FJLJ|FJ|F7|.LJ
....FJL-7.||.||||...
....L---J.LJ.LJLJ..."""
        )

        Assert.assertEquals(8, steps)
    }

    @Test
    fun `part2 sample3`() {
        val input = """
            FF7FSF7F7F7F7F7F---7
            L|LJ||||||||||||F--J
            FL-7LJLJ||||||LJL-77
            F--JF--7||LJLJ7F7FJ-
            L---JF-JLJ.||-FJLJJ7
            |F|F-JF---7F7-L7L|7|
            |FFJF7L7F-JF7|JL---7
            7-L-JL7||F7|L7F-7F7|
            L.L7LFJ|||||FJL7||LJ
            L7JLJL-JLJLJL--JLJ.L
        """.trimIndent()
        val points = Day10.countInsidePoints(input)

        Assert.assertEquals(10, points)
    }

    @Test
    fun `part 2 cheat`() {
        val points = Day10.countInsidePoints(actual)

        Assert.assertEquals(371, points)
    }
}
