package advent.year2023

object Day2 {
    private val colors = listOf("red", "green", "blue")

    fun possibleGameIdSum(input: String, red: Int, green: Int, blue: Int): Int {
        val numbers = listOf(red, green, blue)
        return input.trim().split('\n').sumOf { line ->
            val id = line.substring(line.indexOf(' ') + 1, line.indexOf(':')).toInt()
            if (line.substring(line.indexOf(':') + 1, line.length).split(';').all { set ->
                    set.split(',').all { color ->
                        val trimmed = color.trim()
                        val number = trimmed.substring(0, trimmed.indexOf(' ')).toInt()
                        val colorString = trimmed.substring(trimmed.indexOf(' ') + 1, trimmed.length).trim()
                        var possible = true
                        for (i in colors.indices) {
                            if (colorString == colors[i] && number > numbers[i]) {
                                possible = false
                                break
                            }
                        }
                        possible
                    }
                }) {
                id
            } else {
                0
            }
        }
    }

    fun powerSumOfMinimalSets(input: String): Long {
        return input.trim().split('\n').sumOf { line ->
            val id = line.substring(line.indexOf(' ') + 1, line.indexOf(':')).toInt()
            val minNumbers = intArrayOf(0, 0, 0)
            line.substring(line.indexOf(':') + 1, line.length).split(';').forEach { set ->
                set.split(',').forEach { color ->
                    val trimmed = color.trim()
                    val number = trimmed.substring(0, trimmed.indexOf(' ')).toInt()
                    val colorString = trimmed.substring(trimmed.indexOf(' ') + 1, trimmed.length).trim()
                    for (i in colors.indices) {
                        if (colorString == colors[i] && number > minNumbers[i]) {
                            minNumbers[i] = number
                        }
                    }
                }
            }
            var power = 1L
            minNumbers.forEach {
                power *= it
            }
            power
        }
    }
}
