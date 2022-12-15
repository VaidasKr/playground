package advent.year2015

import advent.print
import advent.readFile
import org.junit.Test

class Day2Test {
    @Test
    fun name() {
        val input = readFile("2015/Day2")
        Day2.calculateWrappingPaper(input).print()
        Day2.calculateRibbon(input).print()
    }
}
