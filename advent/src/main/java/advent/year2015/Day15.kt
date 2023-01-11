package advent.year2015

class Day15(input: String) {
    private val properties: List<Int>

    init {
        properties = buildList {
            input.lineSequence().forEach { line ->
                var number: Int? = null
                var negative = false

                line.forEach { char ->
                    if (char.isDigit()) {
                        if (number == null) {
                            number = char.digitToInt()
                            if (negative) {
                                number = -number!!
                                negative = false
                            }
                        } else {
                            number = number!! * 10 + char.digitToInt()
                        }
                    } else if (char == '-') {
                        negative = true
                    } else if (number != null) {
                        negative = false
                        add(number!!)
                        number = null
                    }
                }
                if (number != null) {
                    add(number!!)
                }
            }
        }
    }

    fun bestScore(spoonCount: Int): Long = bestScoreWithPredicate(spoonCount) { true }

    fun bestScoreFor500Cals(spoonCount: Int): Long = bestScoreWithPredicate(spoonCount) { spoons ->
        var calories = 0
        for (i in spoons.indices) {
            calories += spoons[i] * properties[i * 5 + 4]
        }
        calories == 500
    }

    private fun bestScoreWithPredicate(spoonCount: Int, predicate: (IntArray) -> Boolean): Long {
        var max = 0L
        runLoop(IntArray(properties.size / 5), spoonCount, 0) { spoons ->
            if (predicate(spoons)) {
                val score = calcScore(spoons)
                if (score > max) max = score
            }
        }
        return max
    }

    private fun runLoop(spoons: IntArray, spoonsLeft: Int, index: Int, onResult: (IntArray) -> Unit) {
        if (index == spoons.size - 1) {
            spoons[index] = spoonsLeft
            onResult(spoons)
        } else {
            for (i in 0..spoonsLeft) {
                spoons[index] = i
                runLoop(spoons, spoonsLeft - i, index + 1, onResult)
            }
        }
    }

    private fun calcScore(spoons: IntArray): Long {
        val propertySums = IntArray(4)
        for (i in spoons.indices) {
            val spoonsForIndex = spoons[i]
            propertySums[0] = propertySums[0] + spoonsForIndex * properties[i * 5]
            propertySums[1] = propertySums[1] + spoonsForIndex * properties[i * 5 + 1]
            propertySums[2] = propertySums[2] + spoonsForIndex * properties[i * 5 + 2]
            propertySums[3] = propertySums[3] + spoonsForIndex * properties[i * 5 + 3]
        }
        var multiplier = 1L
        propertySums.forEach { sum ->
            if (sum < 0) {
                return 0
            } else {
                multiplier *= sum
            }
        }
        return multiplier
    }
}
