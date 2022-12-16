package advent.year2015

import advent.assertFalse
import advent.assertTrue
import advent.fileLines
import advent.print
import org.junit.Test

class Day5Test {
    @Test
    fun samples() {
        Day5.isNice("ugknbfddgicrmopn").assertTrue()
        Day5.isNice("aaa").assertTrue()
        Day5.isNice("jchzalrnumimnmhp").assertFalse()
        Day5.isNice("haegwjzuvuyypxyu").assertFalse()
        Day5.isNice("dvszwmarrgswjxmb").assertFalse()
    }

    @Test
    fun actual() {
        fileLines("2015/Day5")
            .filter { line -> line.isNotBlank() }
            .count { line -> Day5.isNice(line) }
            .print()
    }
}
