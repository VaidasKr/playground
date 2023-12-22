package advent.year2023

import advent.assert
import advent.readFile
import org.junit.Test

class Day22Test {
    private val sample
        get() = """
        1,0,1~1,2,1
        0,0,2~2,0,2
        0,2,3~2,2,3
        0,0,4~0,2,4
        2,0,5~2,2,5
        0,1,6~2,1,6
        1,1,8~1,1,9
    """.trimIndent()

    private val actual get() = readFile(2023, 22)

    @Test
    fun `part1 sample`() {
        Day22.safeToDisintegrateBricks(sample).assert(5)
    }

    @Test
    fun `part2 sample`() {
        Day22.sumOfDropsWhenRemoved(sample).assert(7)
    }

    @Test
    fun `part1 actual`() {
        Day22.safeToDisintegrateBricks(actual).assert(424)
    }

    @Test
    fun `part2 actual`() {
        Day22.sumOfDropsWhenRemoved(actual).assert(55483)
    }
}
