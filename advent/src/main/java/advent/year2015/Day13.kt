package advent.year2015

class Day13(private val relations: Map<String, Map<String, Int>>) {
    constructor(input: String) : this(buildMap<String, MutableMap<String, Int>> {
        input.lineSequence().map { it.trim() }.forEach { line ->
            val spaceIndex = line.indexOf(' ')
            val name = line.substring(0, spaceIndex)
            val gain = line.substring(spaceIndex + 7, spaceIndex + 11) == "gain"
            val amount = line.substring(spaceIndex + 12, line.indexOf(' ', spaceIndex + 12))
            val lastSpace = line.lastIndexOf(' ')
            val nameTo = line.substring(lastSpace + 1, line.length - 1)
            val amountInt = if (gain) amount.toInt() else -1 * amount.toInt()
            getOrPut(name) { HashMap() }[nameTo] = amountInt
        }
    })

    fun bestHappinessScore(): Int {
        var max = 0
        val namesToShuffle = relations.keys.toMutableList()
        val seating = namesToShuffle.toMutableList()

        seating.switchAtIndex(1, namesToShuffle) {
            val score = it.score()
            if (score > max) {
                max = score
            }
        }

        return max
    }

    private fun List<String>.score(): Int {
        var score = 0
        for (i in 0 until size) {
            val relations = relations[get(i)]!!
            val previous = if (i == 0) last() else get(i - 1)
            val next = if (i == lastIndex) first() else get(i + 1)
            score += relations[previous]!!
            score += relations[next]!!
        }
        return score
    }

    private fun MutableList<String>.switchAtIndex(
        index: Int,
        options: List<String>,
        onFinished: (List<String>) -> Unit
    ) {
        if (index == size) {
            onFinished(this)
        } else {
            options.forEach { option ->
                if (!containsUntil(option, index)) {
                    set(index, option)
                    switchAtIndex(index + 1, options, onFinished)
                }
            }
        }
    }

    private fun MutableList<String>.containsUntil(element: String, until: Int): Boolean {
        var contain = false
        for (i in 0 until until) {
            if (get(i) == element) return true
        }
        return false
    }

    fun plusEmpty(): Day13 {
        val name = System.currentTimeMillis().toString()
        val values = buildMap {
            relations.keys.forEach { name ->
                put(name, 0)
            }
        }
        val relationPair = name to 0
        val updatedMap = relations.mapValues { (name, relationMap) ->
            relationMap + relationPair
        }.plus(name to values)
        return Day13(updatedMap)
    }
}
