package advent.year2015

object Day17 {
    fun combinationCount(amountToFill: Int, containers: List<Int>): Int =
        countCombinations(amountToFill, containers) { true }

    private inline fun countCombinations(
        amountToFill: Int,
        containers: List<Int>,
        finishedPredicate: (Set<Set<Int>>) -> Boolean
    ): Int {
        val finished = hashSetOf<Set<Int>>()
        val containerIndexes = containers.indices
        var ongoing = buildSet {
            containerIndexes.mapTo(this) { setOf(it) }
        }
        while (ongoing.isNotEmpty() && finishedPredicate(finished)) {
            val newOngoing = hashSetOf<Set<Int>>()
            ongoing.forEach { ongoingSet ->
                val ongoingSum = setSum(ongoingSet, containers)
                for (i in containerIndexes) {
                    val newContainer = containers[i]
                    if (!ongoingSet.contains(i)) {
                        if (ongoingSum + newContainer == amountToFill) {
                            finished.add(ongoingSet + i)
                        } else if (ongoingSum + newContainer < amountToFill) {
                            newOngoing.add(ongoingSet + i)
                        }
                    }
                }
            }
            ongoing = newOngoing
        }
        return finished.size
    }

    private fun setSum(ongoing: Set<Int>, containers: List<Int>): Int {
        var sum = 0
        ongoing.forEach { index ->
            sum += containers[index]
        }
        return sum
    }

    fun minCombinationCount(amountToFill: Int, containers: List<Int>): Int =
        countCombinations(amountToFill, containers) { it.isEmpty() }
}
