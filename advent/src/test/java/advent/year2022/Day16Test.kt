package advent.year2022

import advent.assert
import advent.readFile
import org.junit.Assert
import org.junit.Test

class Day16Test {
    @Test
    fun sample() {
        val day16 = Day16(readFile("2022/day16sample"))
        day16.mostPressureScore(30).assert(1651)
        day16.mostPressureScore2Runners(26).assert(1707)
    }

    @Test
    fun actual() {
        val day16 = Day16(readFile("2022/day16"))
        day16.mostPressureScore(30).assert(1940)
        val result = day16.mostPressureScore2Runners(26)
        Assert.assertEquals(2469, result)
    }
}
