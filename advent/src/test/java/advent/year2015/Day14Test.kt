package advent.year2015

import advent.assert
import advent.print
import advent.readFile
import org.junit.Test

class Day14Test {
    @Test
    fun sample() {
        val day14 = Day14(
            "Comet can fly 14 km/s for 10 seconds, but then must rest for 127 seconds.\nDancer can fly 16 km/s for 11 seconds, but then must rest for 162 seconds."
        )
        day14.maxDistanceAfter(1000).assert(1120)
        day14.maxScoreAfter(1000).assert(689)
    }

    @Test
    fun actual() {
        val day14 = Day14(readFile("2015/Day14"))
        day14.maxDistanceAfter(2503).assert(2696)
        day14.maxScoreAfter(2503).print()
    }
}
