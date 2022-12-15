package advent

import org.junit.Test
import kotlin.system.measureNanoTime

class Day15Test {
    @Test
    fun sample() {
        Day15(readFile("day15sample")).apply {
            noPossibleBeaconCountOn(10).assert(26)
            val first = measureNanoTime { possibleBeaconFrequency(20).assert(56000011) }
            val second = measureNanoTime { possibleBeaconFrequency2(20).assert(56000011) }
            println("#1 $first")
            println("#2 $second")
        }
    }

    @Test
    fun actual() {
        Day15(readFile("day15")).apply {
            noPossibleBeaconCountOn(2000000).assert(6275922)
            val first = measureNanoTime {  possibleBeaconFrequency(4_000_000).assert(11747175442119) }
            val second = measureNanoTime {  possibleBeaconFrequency2(4_000_000).assert(11747175442119) }
            println("#1 $first")
            println("#2 $second")
        }
    }
}
