package advent.year2015

import advent.assert
import advent.print
import org.junit.Test

class Day4Test {
    @Test
    fun actual() {
        Day4.findZeroLeadHash("ckczppom").print()
        Day4.findZeroLeadHash2("ckczppom").print()
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
