package advent

import org.junit.Assert

fun Int.assert(expected: Int) {
    Assert.assertEquals(expected, this)
}

fun Int.print() {
    println(this)
}
