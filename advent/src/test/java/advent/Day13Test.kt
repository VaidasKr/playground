package advent

import org.junit.Assert
import org.junit.Test

class Day13Test {

    @Test
    fun `single pair tests`() {
        val actual = arrayOf(
            testCompare("[1,1,3,1,1]", "[1,1,5,1,1]") to true,
            testCompare("[[1],[2,3,4]]", "[[1],4]") to true,
            testCompare("[9]", "[[8,7,6]]") to false,
            testCompare("[[4,4],4,4]", "[[4,4],4,4,4]") to true,
            testCompare("[7,7,7,7]", "[7,7,7]") to false,
            testCompare("[]", "[3]") to true,
            testCompare("[[[]]]", "[[]]") to false,
            testCompare("[1,[2,[3,[4,[5,6,7]]]],8,9]", "[1,[2,[3,[4,[5,6,0]]]],8,9]") to false
        )
        val failIndexes = mutableListOf<Int>()
        actual.forEachIndexed { index, (actual, expected) ->
            if (actual != expected) failIndexes.add(index)
        }
        if (failIndexes.isNotEmpty()) {
            Assert.fail("failed at: " + failIndexes.joinToString())
        }
    }

    @Test
    fun `test sample`() {
        val sample = Day13()
        fileLines("day13sample").forEach {
            sample.add(it)
        }
        sample.sumOfRightOrderIndexes().assert(13)
        val actual = Day13()
        fileLines("day13actual").forEach {
            actual.add(it)
        }
        actual.sumOfRightOrderIndexes().assert(5623)
    }

    @Test
    fun `test part 2`() {
        val additional = sequenceOf("[[2]]", "[[6]]")
        val day = Day13()
        val sorted = fileLines("day13sample").plus(additional).toMutableList()
            .filter { it.isNotBlank() }
            .sortedWith { first, second ->
                day.compare(first, second)
            }.onEach {
                println(it)
            }
        var score = 1
        additional.forEach {
            val indexOf = sorted.indexOf(it) +1
            println("index of $it $indexOf")
            score *= indexOf
        }
        score.print()
    }

    private fun testCompare(left: String, right: String): Boolean = Day13().compareBoolean(left, right)
}
