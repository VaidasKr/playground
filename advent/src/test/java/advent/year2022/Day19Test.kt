package advent.year2022

import advent.assert
import advent.print
import advent.readFile
import org.junit.Assert
import org.junit.Test

class Day19Test {
    @Test
    fun sample() {
        val sampleInput =
            "Blueprint 1: Each ore robot costs 4 ore. Each clay robot costs 2 ore. Each obsidian robot costs 3 ore and 14 clay. Each geode robot costs 2 ore and 7 obsidian.\n" +
                "Blueprint 2: Each ore robot costs 2 ore. Each clay robot costs 3 ore. Each obsidian robot costs 3 ore and 8 clay. Each geode robot costs 3 ore and 12 obsidian."
        val day19 = Day19(sampleInput)
        day19.qualitySums(24).toList().apply {
            Assert.assertEquals(listOf(9, 12), this)
        }
        day19.qualityLevelSum(24).assert(33)
    }

    @Test
    fun actual() {
        val day19 = Day19(readFile("2022/day19"))
        day19.qualityLevelSum(24).print()
        day19.qualitySums(32).take(3).fold(1) { acc, sum -> acc * sum }.print()
    }
}
