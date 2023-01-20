package advent.year2015

import advent.assert
import org.junit.Test

class Day20Test {
    @Test
    fun sample() {
        Day20.fastPart1(60).assert(4)
    }

    @Test
    fun actual() {
        Day20.fastPart1(34_000_000).assert(786_240)
    }

    @Test
    fun `actual part 2`() {
        Day20.fastPart2(34_000_000).assert(831_600)
    }
}
