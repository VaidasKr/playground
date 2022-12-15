package advent

import org.junit.Test

class Day15Test {
    @Test
    fun sample() {
        Day15(readFile("day15sample")).apply {
            noPossibleBeaconCountOn(10).assert(26)
            possibleBeaconFrequency(20).assert(56000011)
        }
    }

    @Test
    fun actual() {
        Day15(readFile("day15")).apply {
            noPossibleBeaconCountOn(2000000).assert(6275922)
            possibleBeaconFrequency(4_000_000).assert(11747175442119)
        }
    }
}
