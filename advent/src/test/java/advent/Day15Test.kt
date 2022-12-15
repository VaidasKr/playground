package advent

import org.junit.Test

class Day15Test {
    @Test
    fun sample1() {
        Day15.noBeaconAtLine(readFile("day15sample"), 10).assert(26)
    }

    @Test
    fun sample2() {
        Day15.possibleBeaconFreq2(readFile("day15sample"), 20).assert(56000011)
    }

    @Test
    fun actual1() {
        Day15.noBeaconAtLine(readFile("day15"), 2000000).print()
    }

    @Test
    fun actual2() {
        Day15.possibleBeaconFreq2(readFile("day15"), 4_000_000).assert(11747175442119)
    }
}
