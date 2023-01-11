package advent.year2015

import advent.assert
import advent.print
import advent.readFile
import org.junit.Test

class Day16Test {
    @Test
    fun actual() {
        val day16 = Day16(readFile("2015/Day16"))
        day16.findMatchingSueIndex(
            children = 3,
            cats = 7,
            samoyeds = 2,
            pomeranians = 3,
            akitas = 0,
            vizslas = 0,
            goldfish = 5,
            trees = 3,
            cars = 2,
            perfumes = 1
        ).assert(212)
        day16.findMatchingSueIndexAdjusted(
            children = 3,
            cats = 7,
            samoyeds = 2,
            pomeranians = 3,
            akitas = 0,
            vizslas = 0,
            goldfish = 5,
            trees = 3,
            cars = 2,
            perfumes = 1
        ).assert(322)
    }
}
