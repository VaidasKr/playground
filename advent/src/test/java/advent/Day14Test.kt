package advent

import org.junit.Test

class Day14Test {
    @Test
    fun samplePart1() {
        val sampleInput = """498,4 -> 498,6 -> 496,6
503,4 -> 502,4 -> 502,9 -> 494,9"""
        val day14 = Day14()
        day14.addLines(sampleInput.split("\n"))
        day14.draw()
        println()
        var bags = 0
        while (day14.dropNoFloor()) {
            bags++
        }
        bags.print()
        bags.assert(24)
        println()
        day14.draw()
    }

    @Test
    fun samplePart2() {
        val sampleInput = """498,4 -> 498,6 -> 496,6
503,4 -> 502,4 -> 502,9 -> 494,9"""
        val day14 = Day14()
        day14.addLines(sampleInput.split("\n"))
        day14.draw()
        println()
        var bags = 0
        while (day14.dropWithFloor()) {
            bags++
        }
        bags.print()
        bags.assert(93)
        println()
        day14.draw(true)
    }

    @Test
    fun actual() {
        val day14 = Day14()
        day14.addLines(readFileLines("day14"))
        day14.draw()
        println()
        var bags = 0
        while (day14.dropNoFloor()) {
            bags++
        }
        println()
        day14.draw(false)
        bags.print()
        bags.assert(825)
    }

    @Test
    fun actual2() {
        val day14 = Day14()
        day14.addLines(readFileLines("day14"))
        day14.draw()
        println()
        var bags = 0
        while (day14.dropWithFloor()) {
            bags++
        }
        day14.draw(true)
        println()
        bags.print()
        bags.assert(26729)
    }
}
