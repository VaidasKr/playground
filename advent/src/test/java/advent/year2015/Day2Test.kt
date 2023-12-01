package advent.year2015

import advent.readFile
import org.junit.Assert
import org.junit.Test

class Day2Test {
    @Test
    fun name() {
        val input = readFile("2015/Day2")
        val result1 = Day2.calculateWrappingPaper(input)
        Assert.assertEquals(1598415, result1)
        val result2 = Day2.calculateRibbon(input)
        Assert.assertEquals(3812909, result2)
    }
}
