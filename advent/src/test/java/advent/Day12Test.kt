package advent

import org.junit.Test

class Day12Test {
    @Test
    fun name() {
        val sampleInput = readFile("day12Sample")
        Day12.shortestDistanceFromStoE(sampleInput).assert(31)
        Day12.shortestDistanceFromAnyAtoE(sampleInput).assert(29)
        val actualInput = readFile("day12Input")
        Day12.shortestDistanceFromStoE(actualInput).assert(380)
        Day12.shortestDistanceFromAnyAtoE(actualInput).assert(375)
    }
}
