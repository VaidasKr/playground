package advent.year2023

object Day6 {
    fun powerOfWinningOptionsInEachGame(input: String): Long {
        val lines = input.split('\n')
        var power = 0L
        val times = lines[0].substring(lines[0].indexOf(':') + 1).trim().split(' ').filter { it.isNotBlank() }
        val distances = lines[1].substring(lines[1].indexOf(':') + 1).trim().split(' ').filter { it.isNotBlank() }
        for (i in times.indices) {
            val options = findWinningOptions(times[i].toLong(), distances[i].toLong())
            if (power == 0L) {
                power = options
            } else {
                power *= options
            }
        }
        return power
    }

    private fun findWinningOptions(time: Long, distance: Long): Long {
        var winningOptions = 0L
        for (i in 1 until time - 1) {
            val distanceWhenHoldingForITime = i * (time - i)
            if (distanceWhenHoldingForITime > distance) {
                winningOptions++
            }
        }
        return winningOptions
    }

    fun powerOfWinningOptionsSingleGame(input: String): Long {
        val lines = input.split('\n')
        val times = lines[0].substring(lines[0].indexOf(':') + 1).replace(" ", "").toLong()
        val distances = lines[1].substring(lines[1].indexOf(':') + 1).replace(" ", "").toLong()
        return findWinningOptions(times, distances)
    }
}
