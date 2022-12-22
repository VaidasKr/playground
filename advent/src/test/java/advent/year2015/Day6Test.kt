package advent.year2015

import advent.assert
import advent.fileLines
import advent.print
import org.junit.Test

class Day6Test {
    @Test
    fun sample() {
        val day6 = Day6()
        fileLines("2015/Day6").filter { it.isNotBlank() }.forEach { command ->
            day6.runCommand(command)
        }
        day6.enabledCount.print()
        day6.lightIntensity.print()
    }

    @Test
    fun tests() {
        val day6 = Day6()
        day6.enabledCount.assert(0)
        day6.lightIntensity.assert(0)
        day6.runCommand("turn on 0,0 through 0,0")
        day6.enabledCount.assert(1)
        day6.lightIntensity.assert(1)
        day6.runCommand("toggle 0,0 through 0,0")
        day6.enabledCount.assert(0)
        day6.lightIntensity.assert(3)
        day6.runCommand("turn off 0,0 through 0,0")
        day6.runCommand("turn off 0,0 through 0,0")
        day6.runCommand("turn off 0,0 through 0,0")
        day6.runCommand("turn off 0,0 through 0,0")
        day6.enabledCount.assert(0)
        day6.lightIntensity.assert(0)
        day6.runCommand("toggle 0,0 through 999,999")
        day6.enabledCount.assert(1_000_000)
        day6.lightIntensity.assert(2_000_000)
        day6.runCommand("toggle 0,0 through 999,999")
        day6.enabledCount.assert(0)
        day6.lightIntensity.assert(4_000_000)
    }
}
