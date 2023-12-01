package advent

import org.junit.Assert

fun Int.assert(expected: Int) {
    Assert.assertEquals(expected, this)
}

fun Boolean.assert(expected: Boolean) {
    if (expected) assertTrue() else assertFalse()
}

fun Boolean.assertTrue() {
    Assert.assertTrue(this)
}

fun Boolean.assertFalse() {
    Assert.assertFalse(this)
}

fun String.assert(expected: String) {
    Assert.assertEquals(expected, this)
}

fun Long.assert(expected: Long) {
    Assert.assertEquals(expected, this)
}

fun <T : Any> T.fileStream(name: String) = javaClass.classLoader.getResourceAsStream(name)!!.bufferedReader()

fun <T : Any> T.readFile(name: String): String = fileStream(name).readText().trimEnd()

fun <T : Any> T.readFileLines(name: String): List<String> = fileStream(name).readLines()

fun <T : Any> T.fileLines(name: String): Sequence<String> {
    return fileStream(name).lineSequence()
}
