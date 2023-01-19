package advent.year2015

class Day18(private val lights: BooleanArray, private val height: Int, private val width: Int) {
    val turnedOnLights: Int get() = lights.count { it }

    fun next(): Day18 = Day18(BooleanArray(lights.size) { index -> isOn(index) }, height, width)

    fun enableCorners(): Day18 = Day18(BooleanArray(lights.size) { lights[it] || isCorner(it) }, height, width)

    private fun isCorner(index: Int): Boolean =
        index == 0 || index == width - 1 || index == lights.lastIndex || index == width * (height - 1)

    fun nextWithCorners(): Day18 =
        Day18(BooleanArray(lights.size) { index -> isOn(index) || isCorner(index) }, height, width)

    private fun isOn(index: Int): Boolean {
        val enabledNeighbours = countEnabledNeighbours(index)
        return enabledNeighbours == 3 || ((lights[index]) && enabledNeighbours == 2)
    }

    private fun countEnabledNeighbours(index: Int): Int {
        val x = index % width
        val y = index / width

        var count = 0
        if (x > 0) {
            if (lights[index - 1]) count++
            if (y > 0 && lights[index - 1 - width]) count++
            if (y < height - 1 && lights[index - 1 + width]) count++
        }
        if (x < width - 1) {
            if (lights[index + 1]) count++
            if (y > 0 && lights[index + 1 - width]) count++
            if (y < height - 1 && lights[index + 1 + width]) count++
        }
        if (y > 0 && lights[index - width]) count++
        if (y < height - 1 && lights[index + width]) count++
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
            return Day18(lights.toBooleanArray(), height, width)
        }
    }
}
