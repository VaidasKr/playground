package advent.year2023

object Day16 {
    fun calculateEnergizedTiles(input: String): Int {
        val trimmed = input.trim()
        val map = trimmed.split('\n')
        return calculateEnergizedTiles(map, Beam(0, 0, 1, 0))
    }

    private fun calculateEnergizedTiles(map: List<String>, start: Beam): Int {
        val visitedTiles = hashSetOf<Int>()
        val width = map.first().length
        println("$width - ${map.size}")
        var beams = setOf(start)
        val visitedBeams = beams.toHashSet()
        while (beams.isNotEmpty()) {
            val nextBeams = hashSetOf<Beam>()
            beams.forEach { beam ->
                visitedTiles.add(beam.x + beam.y * width)
                when (map[beam.y][beam.x]) {
                    '.' -> listOf(beam.copy(x = beam.x + beam.dirX, y = beam.y + beam.dirY))
                    '\\' -> listOf(Beam(beam.x + beam.dirY, beam.y + beam.dirX, beam.dirY, beam.dirX))
                    '/' -> listOf(Beam(beam.x - beam.dirY, beam.y - beam.dirX, -beam.dirY, -beam.dirX))
                    '-' -> {
                        if (beam.dirX != 0) {
                            listOf(beam.copy(x = beam.x + beam.dirX))
                        } else {
                            listOf(
                                beam.copy(x = beam.x + beam.dirY, dirX = beam.dirY, dirY = 0),
                                beam.copy(x = beam.x - beam.dirY, dirX = -beam.dirY, dirY = 0)
                            )
                        }
                    }

                    '|' -> {
                        if (beam.dirY != 0) {
                            listOf(beam.copy(y = beam.y + beam.dirY))
                        } else {
                            listOf(
                                beam.copy(y = beam.y + beam.dirX, dirY = beam.dirX, dirX = 0),
                                beam.copy(y = beam.y - beam.dirX, dirY = -beam.dirX, dirX = 0)
                            )
                        }
                    }

                    else -> throw RuntimeException("not supported char")
                }.forEach { movedBeam ->
                    if (movedBeam.x in 0 until width && movedBeam.y in map.indices) {
                        if (visitedBeams.add(movedBeam)) nextBeams.add(movedBeam)
                    }
                }
            }
            beams = nextBeams
        }
        return visitedTiles.size
    }

    fun bestConfigurationOfEnergizedTiles(input: String): Int {
        val trimmed = input.trim()
        val map = trimmed.split('\n')
        val width = map.first().length
        val height = map.size
        var max = 0
        for (x in 0 until width) {
            max = max.coerceAtLeast(calculateEnergizedTiles(map, Beam(x, 0, 0, 1)))
            max = max.coerceAtLeast(calculateEnergizedTiles(map, Beam(x, height - 1, 0, -1)))
        }
        for (y in 0 until height) {
            max = max.coerceAtLeast(calculateEnergizedTiles(map, Beam(0, y, 1, 0)))
            max = max.coerceAtLeast(calculateEnergizedTiles(map, Beam(width - 1, y, -1, 0)))
        }
        return max
    }

    private data class Beam(val x: Int, val y: Int, val dirX: Int, val dirY: Int)
}
