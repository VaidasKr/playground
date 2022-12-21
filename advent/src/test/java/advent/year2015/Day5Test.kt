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
        var nice1 = 0
        var nice2 = 0
        fileLines("2015/Day5")
            .filter { line -> line.isNotBlank() }
            .forEach { line ->
                if (Day5.isNice(line)) nice1++
                if (Day5.isNice2(line)) nice2++
            }
        nice1.print()
        nice2.print()
    }
}
