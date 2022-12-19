package advent.year2022

import advent.assert
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
        day19.qualitySums2(24).toList().apply {
            Assert.assertEquals(listOf(9, 12), this)
        }
        day19.qualityLevelSum(24).assert(33)
    }

    @Test
    fun actual() {
        val day19 = Day19(readFile("2022/day19"))
        var sum = 0
        day19.qualitySums(24).forEachIndexed { index, i ->
            println("$index $i")
        }
        day19.qualitySums2(24).forEachIndexed { index, i ->
            println("$index $i")
            sum += (index + 1) * i
        }
        sum.assert(1346)
        day19.qualitySums2(32).take(3).fold(1) { acc, multi -> acc * multi }.assert(7644)
    }
}
