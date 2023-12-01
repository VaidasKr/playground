package advent.year2015

import advent.readFile
import org.junit.Assert
import org.junit.Test

class Day1Test {
    @Test
    fun name() {
        val floor = Day1.getFloor(readFile("2015/Day1"))
        Assert.assertEquals(1795, floor)
    }
}
