package advent.year2015

import advent.assert
import advent.readFile
import org.junit.Test

class Day19Test {
    @Test
    fun `sample 1`() {
        val day19 = Day19()
        day19.add("H", "HO")
        day19.add("H", "OH")
        day19.add("O", "HH")
        val mutationPossibilities = day19.mutationPossibilities("HOH")
        mutationPossibilities.size.assert(4)

        day19.add("e", "H")
        day19.add("e", "O")
        day19.mutationCount("e", "HOH").assert(3)
        day19.reversed().mutationCount("HOH", "e").assert(3)
    }

    @Test
    fun `sample 2`() {
        val day19 = Day19()
        day19.add("H", "HO")
        day19.add("H", "OH")
        day19.add("O", "HH")
        val mutationPossibilities = day19.mutationPossibilities("HOHOHO")
        mutationPossibilities.size.assert(7)

        day19.add("e", "H")
        day19.add("e", "O")
        day19.mutationCount("e", "HOHOHO").assert(6)
        day19.reversed().mutationCount("HOHOHO", "e").assert(6)
    }

    @Test
    fun actual() {
        Day19.parse(readFile("2015/Day19")) { day19, source ->
            day19.mutationPossibilities(source).size.assert(509)
            day19.reversedMutations("e", source).assert(195)
        }
    }
}
