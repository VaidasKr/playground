package advent.year2015

import advent.readFile
import org.junit.Assert
import org.junit.Test

class Day3Test {
    @Test
    fun name() {
        val input = readFile("2015/Day3")
        val result1 = Day3.calculateVisitedHouses(input)
        Assert.assertEquals(2081,result1)
        val result2 = Day3.calculateVisitedHousesWithRobo(input)
        Assert.assertEquals(2341,result2)
    }
}
