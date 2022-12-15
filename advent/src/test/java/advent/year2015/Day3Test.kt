package advent.year2015

import advent.print
import advent.readFile
import org.junit.Test

class Day3Test {
    @Test
    fun name() {
        val input = readFile("2015/Day3")
        Day3.calculateVisitedHouses(input).print()
        Day3.calculateVisitedHousesWithRobo(input).print()
    }
}
