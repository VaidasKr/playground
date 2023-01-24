package advent.year2015

import advent.assert
import advent.fileLines
import org.junit.Test

class Day24Test {
    @Test
    fun sample() {
        val day24 = Day24()
        for (i in 1..5) day24.addPackage(i)
        for (i in 7..11) day24.addPackage(i)
        day24.bestBucketCombination(3).assert(99)
        day24.bestBucketCombination(4).assert(44)
    }

    @Test
    fun actual() {
        val day24 = Day24()
        fileLines("2015/Day24").filter { it.isNotBlank() }.forEach { weight -> day24.addPackage(weight.toInt()) }
        day24.bestBucketCombination(3).assert(10439961859)
        day24.bestBucketCombination(4).assert(72050269)
    }
}
