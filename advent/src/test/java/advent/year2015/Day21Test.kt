package advent.year2015

import advent.assert
import advent.readFile
import advent.unpackInt1
import advent.unpackInt2
import org.junit.Test

class Day21Test {
    @Test
    fun actualPart1() {
        Day21.canWin(intArrayOf(8, 5, 5), intArrayOf(12, 7, 2)).assert(true)
        val input = readFile("2015/Day21")
        val packed = Day21.findCheapestWinAndMostExpensiveLoss(input)
        unpackInt1(packed).assert(91)
        unpackInt2(packed).assert(158)
    }
}
