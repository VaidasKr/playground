package advent.year2015

import advent.assert
import advent.readFile
import org.junit.Test

class Day13Test {
    @Test
    fun sample() {
        val sampleInput = """
            Alice would gain 54 happiness units by sitting next to Bob.
            Alice would lose 79 happiness units by sitting next to Carol.
            Alice would lose 2 happiness units by sitting next to David.
            Bob would gain 83 happiness units by sitting next to Alice.
            Bob would lose 7 happiness units by sitting next to Carol.
            Bob would lose 63 happiness units by sitting next to David.
            Carol would lose 62 happiness units by sitting next to Alice.
            Carol would gain 60 happiness units by sitting next to Bob.
            Carol would gain 55 happiness units by sitting next to David.
            David would gain 46 happiness units by sitting next to Alice.
            David would lose 7 happiness units by sitting next to Bob.
            David would gain 41 happiness units by sitting next to Carol.
        """.trimIndent()
        Day13(sampleInput).bestHappinessScore().assert(330)
    }

    @Test
    fun actual() {
        val day13 = Day13(readFile("2015/Day13"))

        day13.bestHappinessScore().assert(709)
        day13.plusEmpty().bestHappinessScore().assert(668)
    }
}
