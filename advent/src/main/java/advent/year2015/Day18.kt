package advent.year2015

data class Day18(val lights: List<Boolean>, val height: Int, val width: Int) {
    val turnedOnLights: Int get() = lights.count { it }

    fun next(): Day18 {
        val nextState = List(lights.size) { index ->
            val enabledNeighbours = countEnabledNeighbours(index)
            enabledNeighbours == 3 || ((lights[index]) && enabledNeighbours == 2)
        }

        return Day18(nextState, height, width)
    }

    fun enableCorners(): Day18 {
        val newState = lights.mapIndexed { index, on ->
            on || isCorner(index)
        }
        return Day18(newState, height, width)
    }

    private fun isCorner(index: Int): Boolean {
        val size = lights.size
        return index == 0 || index == width - 1 || index == size - 1 || index == width * (height - 1)
    }

    fun nextWithCorners(): Day18 {
        val nextState = List(lights.size) { index ->
            if (isCorner(index)) {
                true
            } else {
                val enabledNeighbours = countEnabledNeighbours(index)
                enabledNeighbours == 3 || ((lights[index]) && enabledNeighbours == 2)
            }
        }

        return Day18(nextState, height, width)
    }

    private fun countEnabledNeighbours(index: Int): Int {
        val x = index % width
        val y = index / width

        var count = 0
        if (x > 0 && lights[index - 1]) count++
        if (x < width - 1 && lights[index + 1]) count++
        if (y > 0 && lights[index - width]) count++
        if (y < height - 1 && lights[index + width]) count++
        if (x > 0 && y > 0 && lights[index - 1 - width]) count++
        if (x > 0 && y < height - 1 && lights[index - 1 + width]) count++
        if (x < width - 1 && y > 0 && lights[index + 1 - width]) count++
        if (x < width - 1 && y < height - 1 && lights[index + 1 + width]) count++
        return count
    }

    companion object {
        fun fromString(input: String): Day18 {
            var height = 1
            var width = 0
            val lights = buildList {
                for (i in input.indices) {
                    when (input[i]) {
                        '.' -> {
                            width++
                            add(false)
                        }
                        '#' -> {
                            width++
                            add(true)
                        }
                        '\n' -> {
                            width = 0
                            height++
                        }
                    }
                }
            }
            return Day18(lights, height, width)
        }
    }
}
