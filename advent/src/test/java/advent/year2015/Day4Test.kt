package advent.year2015

import advent.assert
import org.junit.Assert
import org.junit.Test

class Day4Test {
    @Test
    fun actual() {
        val result1 = Day4.findZeroLeadHash("ckczppom")
        Assert.assertEquals(117946, result1)
        val result2 = Day4.findZeroLeadHash2("ckczppom")
        Assert.assertEquals(3938038, result2)
    }

    @Test
    fun sample1() {
        Day4.findZeroLeadHash("abcdef").assert(609043)
    }

    @Test
    fun sample2() {
        Day4.findZeroLeadHash("pqrstuv").assert(1048970)
    }
}
