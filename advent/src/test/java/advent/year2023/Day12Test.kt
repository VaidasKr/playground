package advent.year2023

import advent.readFile
import org.junit.Assert
import org.junit.Test

class Day12Test {
    private val sample = """
    ???.### 1,1,3
    .??..??...?##. 1,1,3
    ?#?#?#?#?#?#?#? 1,3,1,6
    ????.#...#... 4,1,1
    ????.######..#####. 1,6,5
    ?###???????? 3,2,1
""".trimIndent()

    private val actual get() = readFile("2023/Day12")

    @Test
    fun sumOfPossibleArrangementsSample() {
        val possibleArrangements = Day12.sumOfPossibleArrangements(sample)

        Assert.assertEquals(21, possibleArrangements)
    }

    @Test
    fun sumOfPossibleArrangementsUnfoldedSample() {
        val possibleArrangements = Day12.sumOfPossibleArrangementsUnfolded(sample)

        Assert.assertEquals(525152, possibleArrangements)
    }

    @Test
    fun sumOfPossibleArrangementsActual() {
        val possibleArrangements = Day12.sumOfPossibleArrangements(actual)

        Assert.assertEquals(7286, possibleArrangements)
    }

    @Test
    fun sumOfPossibleArrangementsUnfolded() {
        val possibleArrangements = Day12.sumOfPossibleArrangementsUnfolded(actual)

        Assert.assertEquals(25470469710341, possibleArrangements)
    }
}
