package advent.year2022

import advent.assert
import advent.readFile
import org.junit.Test
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.atomic.AtomicInteger

class Day19Test {
    @Test
    fun sample() {
        val sampleInput =
            "Blueprint 1: Each ore robot costs 4 ore. Each clay robot costs 2 ore. Each obsidian robot costs 3 ore and 14 clay. Each geode robot costs 2 ore and 7 obsidian.\n" +
                "Blueprint 2: Each ore robot costs 2 ore. Each clay robot costs 3 ore. Each obsidian robot costs 3 ore and 8 clay. Each geode robot costs 3 ore and 12 obsidian."
        val day19 = Day19(sampleInput)
        day19.blueprints.first().searchDecisionBased(24).assert(9)
        day19.blueprints[1].searchDecisionBased(24).assert(12)
//        day19.qualityLevelSum(24).assert(33)
    }

    @Test
    fun actual() {
        val day19 = Day19(readFile("2022/day19"))
        val sum = AtomicInteger()
        val scheduler = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())
        val futures = mutableListOf<Future<*>>()
        for (i in day19.blueprints.indices) {
            futures.add(
                scheduler.submit {
                    val bluePrint = day19.blueprints[i]
                    val actual = bluePrint.searchDecisionBased(24)
                    sum.updateAndGet { it + bluePrint.id * actual }
                }
            )
        }
        for (future in futures) {
            future.get()
        }
        sum.get().assert(1346)
        sum.set(1)
        for (i in 0..2) {
            futures.add(
                scheduler.submit {
                    val bluePrint = day19.blueprints[i]
                    val actual = bluePrint.searchDecisionBased(32)
                    sum.updateAndGet { it * actual }
                }
            )
        }
        for (future in futures) {
            future.get()
        }
        sum.get().assert(7644)
    }
}
