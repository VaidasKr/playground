package advent.year2023

import advent.readFile
import org.junit.Assert
import org.junit.Test

class Day8Test {
    val sampleInput = """
        RL

        AAA = (BBB, CCC)
        BBB = (DDD, EEE)
        CCC = (ZZZ, GGG)
        DDD = (DDD, DDD)
        EEE = (EEE, EEE)
        GGG = (GGG, GGG)
        ZZZ = (ZZZ, ZZZ)
    """.trimIndent()

    val sampleInput2 = """
        LLR

        AAA = (BBB, BBB)
        BBB = (AAA, ZZZ)
        ZZZ = (ZZZ, ZZZ)
    """.trimIndent()

    val sample3 = """
        LR

        11A = (11B, XXX)
        11B = (XXX, 11Z)
        11Z = (11B, XXX)
        22A = (22B, XXX)
        22B = (22C, 22C)
        22C = (22Z, 22Z)
        22Z = (22B, 22B)
        XXX = (XXX, XXX)
    """.trimIndent()

    val actualInput get() = readFile("2023/Day8")

    @Test
    fun part1Samples() {
        val steps = Day8.stepsFrom(sampleInput, "AAA", "ZZZ")

        Assert.assertEquals(2, steps)

        val steps2 = Day8.stepsFrom(sampleInput2, "AAA", "ZZZ")

        Assert.assertEquals(6, steps2)
    }

    @Test
    fun part1actual() {
        val steps = Day8.stepsFrom(actualInput, "AAA", "ZZZ")

        Assert.assertEquals(18023, steps)
    }

    @Test
    fun part2sample() {
        val steps = Day8.simultaneousSteps(sample3)

        Assert.assertEquals(6, steps)
    }

    @Test
    fun part2actual() {
        val steps = Day8.simultaneousSteps(actualInput)

        Assert.assertEquals(14449445933179L, steps)
    }
}
