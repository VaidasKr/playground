package advent.year2015

class Day6 {
    private val enabledMap = Array(1000) { BooleanArray(1000) }
    private val lightMap = Array(1000) { IntArray(1000) }

    val enabledCount: Int get() = enabledMap.sumOf { line -> line.count { on -> on } }
    val lightIntensity: Int get() = lightMap.sumOf { line -> line.sum() }

    fun runCommand(command: String) {
        val (opBool, opInt) = if (command.startsWith("turn on")) {
            ::turnOn to ::turnOnInt
        } else if (command.startsWith("turn off")) {
            ::turnOff to ::turnOffInt
        } else if (command.startsWith("toggle")) {
            ::toggle to ::toggleInt
        } else {
            throw RuntimeException("unknown command")
        }
        val digits = collectDigits(command)
        enabledMap.runCommand(opBool, digits[0], digits[1], digits[2], digits[3])
        lightMap.runCommand(opInt, digits[0], digits[1], digits[2], digits[3])
    }

    private fun collectDigits(command: String): List<Int> = buildList {
        var number = -1
        command.forEach { char ->
            if (char.isDigit()) {
                if (number == -1) number = 0
                number = number * 10 + char.digitToInt()
            } else if (number != -1) {
                add(number)
                number = -1
            }
        }
        if (number != -1) {
            add(number)
        }
    }

    private fun toggle(state: Boolean): Boolean = !state

    private fun turnOff(state: Boolean): Boolean = false

    private fun turnOn(state: Boolean): Boolean = true

    private fun toggleInt(state: Int): Int = state + 2

    private fun turnOffInt(state: Int): Int = (state - 1).coerceAtLeast(0)

    private fun turnOnInt(state: Int): Int = state + 1

    private fun Array<BooleanArray>.runCommand(op: (Boolean) -> Boolean, fromX: Int, fromY: Int, toX: Int, toY: Int) {
        for (x in fromX..toX) {
            for (y in fromY..toY) {
                this[x][y] = op(this[x][y])
            }
        }
    }

    private fun Array<IntArray>.runCommand(op: (Int) -> Int, fromX: Int, fromY: Int, toX: Int, toY: Int) {
        for (x in fromX..toX) {
            for (y in fromY..toY) {
                this[x][y] = op(this[x][y])
            }
        }
    }
}
