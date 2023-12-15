package advent.year2023

import advent.readFile
import org.junit.Assert
import org.junit.Test

class Day15Test {
    private val actual get() = readFile("2023/Day15")
    private val sample = "rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7"

    @Test
    fun sample1() {
        Assert.assertEquals(1320, Day15.sumOfAsciiHash(sample))
    }

    @Test
    fun actual1() {
        Assert.assertEquals(498538, Day15.sumOfAsciiHash(actual))
    }

    @Test
    fun sample2() {
        Assert.assertEquals(145, Day15.sumOfFocusPower(sample))
    }

    @Test
    fun actual2() {
        Assert.assertEquals(286278, Day15.sumOfFocusPower(actual))
    }
}
