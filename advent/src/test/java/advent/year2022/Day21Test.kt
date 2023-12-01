package advent.year2022

import advent.assert
import advent.readFile
import org.junit.Test

class Day21Test {
    @Test
    fun `sample part 1`() {
        Day21.findRootMonkeyNumber(readFile("2022/day21sample")).assert(152)
    }

    @Test
    fun `actual part 1`() {
        Day21.findRootMonkeyNumber(readFile("2022/day21")).assert(104272990112064)
    }

    @Test
    fun `sample part 2`() {
        Day21.findHumanNumber(readFile("2022/day21sample")).assert(301)
    }

    @Test
    fun `actual part 2`() {
        Day21.findHumanNumber(readFile("2022/day21")).assert(3220993874133)
    }
}
