package advent

import org.junit.Assert

fun Int.assert(expected: Int) {
    Assert.assertEquals(expected, this)
}

fun Int.print() {
    println(this)
}

fun <T : Any> T.fileStream(name: String) = javaClass.classLoader.getResourceAsStream(name)!!.bufferedReader()

fun <T : Any> T.readFile(name: String): String = fileStream(name).readText().trimEnd()

fun <T : Any> T.readFileLines(name: String): List<String> = fileStream(name).readLines()

fun <T : Any> T.fileLines(name: String, onLine: (String) -> Unit): Sequence<String> {
    return fileStream(name).lineSequence()
}
