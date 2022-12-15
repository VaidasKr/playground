package advent.year2015

import advent.print
import advent.readFile
import org.junit.Test

class Day1Test {
    @Test
    fun name() {
        Day1.getFloor(readFile("2015/Day1")).print()
    }
}
