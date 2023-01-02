package advent.year2015

import advent.assert
import advent.fileLines
import org.junit.Test

class Day8Test {
    @Test
    fun `test cases`() {
        var part1 = 0
        var part2 = 0
        """
        ""
        "abc"
        "aaa\"aaa"
        "\x27"    
        """.trimIndent().split("\n").forEach {
            part1 += it.length - Day8.memorySpaceOf(it)
            part2 += Day8.encodeSize(it) - it.length
        }
        part1.assert(12)
        part2.assert(19)
    }

    @Test
    fun actual() {
        var part1 = 0
        var part2 = 0
        fileLines("2015/Day8").filter { it.isNotBlank() }.forEach {
            part1 += it.length - Day8.memorySpaceOf(it)
            part2 += Day8.encodeSize(it) - it.length
        }
        part1.assert(1371)
        part2.assert(2117)
    }
}
