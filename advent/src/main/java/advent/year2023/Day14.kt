package advent.year2023

object Day14 {
    fun loadAfterUpTilt(input: String): Long {
        val lines = input.trim().split("\n")
        val width = lines.first().length
        val height = lines.size
        val state = lines.joinToString("")
        val buffer = state.toCharArray()
        tiltUp(buffer, width, height)
        return calculate(buffer, width, height)
    }

    fun loadAfterCycles(input: String, cycles: Int): Long {
        val lines = input.trim().split("\n")
        val width = lines.first().length
        val height = lines.size
        val state = lines.joinToString("")
        val buffer = state.toCharArray()
        val lastIndexMap = hashMapOf(buffer.contentHashCode() to 0)
        var cycle = 0
        while (cycle++ < cycles) {
            spin(buffer, width, height)
            val lastIndex = lastIndexMap.getOrPut(buffer.contentHashCode()) { cycle }
            if (lastIndex != cycle) {
                val cyclesLeft = (cycles - cycle) % (cycle - lastIndex)
                repeat(cyclesLeft) { spin(buffer, width, height) }
                break
            }
        }
        return calculate(buffer, width, height)
    }

    private fun calculate(state: CharArray, width: Int, height: Int): Long {
        var sum = 0L
        for (y in 0 until height) {
            for (x in 0 until width) {
                if (state[x + y * width] == 'O') {
                    sum += height - y
                }
            }
        }
        return sum
    }

    private fun spin(buffer: CharArray, width: Int, height: Int) {
        tiltUp(buffer, width, height)
        tiltLeft(buffer, width, height)
        tiltDown(buffer, width, height)
        tiltRight(buffer, width, height)
    }

    private fun tiltUp(buffer: CharArray, width: Int, height: Int) {
        for (y in 0 until height) {
            for (x in 0 until width) {
                val index = x + y * width
                if (buffer[index] == 'O') {
                    var rolled = index
                    for (i in index - width downTo 0 step width) {
                        if (buffer[i] == '.') {
                            rolled = i
                        } else {
                            break
                        }
                    }
                    buffer[index] = '.'
                    buffer[rolled] = 'O'
                }
            }
        }
    }

    private fun tiltLeft(buffer: CharArray, width: Int, height: Int) {
        for (y in 0 until height) {
            for (x in 0 until width) {
                val index = x + y * width
                if (buffer[index] == 'O') {
                    var rolled = index
                    for (i in index - 1 downTo index - x) {
                        if (buffer[i] == '.') {
                            rolled = i
                        } else {
                            break
                        }
                    }
                    buffer[index] = '.'
                    buffer[rolled] = 'O'
                }
            }
        }
    }

    private fun tiltDown(buffer: CharArray, width: Int, height: Int) {
        for (y in height - 1 downTo 0) {
            for (x in 0 until width) {
                val index = x + y * width
                if (buffer[index] == 'O') {
                    var rolled = index
                    for (i in index + width until buffer.size step width) {
                        if (buffer[i] == '.') {
                            rolled = i
                        } else {
                            break
                        }
                    }
                    buffer[index] = '.'
                    buffer[rolled] = 'O'
                }
            }
        }
    }

    private fun tiltRight(buffer: CharArray, width: Int, height: Int) {
        for (y in 0 until height) {
            for (x in width - 1 downTo 0) {
                val index = x + y * width
                if (buffer[index] == 'O') {
                    var rolled = index
                    for (i in index + 1 until (width * (y + 1))) {
                        if (buffer[i] == '.') {
                            rolled = i
                        } else {
                            break
                        }
                    }
                    buffer[index] = '.'
                    buffer[rolled] = 'O'
                }
            }
        }
    }
}
