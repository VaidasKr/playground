package advent.year2023

object Day7 {
    private const val priority1 = "AKQJT98765432"
    private const val priority2 = "AKQT98765432J"

    fun calculateWinningBidSum(input: String): Long =
        input.trim().split('\n')
            .map { it.split(' ') }
            .sortedWith { pair1, pair2 -> compare(pair1[0], pair2[0]) }
            .bidRankSum()

    private fun compare(first: String, second: String): Int {
        val dif = handType(second) - handType(first)
        return if (dif == 0) compareByBestFirstCard(first, second, priority1) else dif
    }

    private fun handType(cards: String): Int {
        val buildMap = charCountMap(cards)
        return when (buildMap.size) {
            1 -> 0
            2 -> if (buildMap.any { it.value == 4 }) 1 else 2
            3 -> if (buildMap.any { it.value == 3 }) 3 else 4
            4 -> 5
            else -> 6
        }
    }

    private fun charCountMap(cards: String): Map<Char, Int> = buildMap {
        for (char in cards) {
            val existingCount = getOrElse(char) { 0 }
            put(char, existingCount + 1)
        }
    }

    private fun compareByBestFirstCard(first: String, second: String, priority: String): Int {
        for (i in first.indices) {
            val dif = priority.indexOf(second[i]) - priority.indexOf(first[i])
            if (dif != 0) return dif
        }
        return 0
    }

    private fun List<List<String>>.bidRankSum(): Long {
        var sum = 0L
        for (i in indices) {
            sum += this[i][1].toInt() * (i + 1)
        }
        return sum
    }

    fun calculateWinningBidSumWithJokerRule(input: String): Long =
        input.trim().split('\n').map {
            val parts = it.split(' ')
            listOf(parts[0], parts[1], convertJokers(parts[0]))
        }.sortedWith { pair1, pair2 -> compareJokers(pair1, pair2) }
            .bidRankSum()

    private fun compareJokers(first: List<String>, second: List<String>): Int {
        val dif = handType(second[2]) - handType(first[2])
        return if (dif == 0) compareByBestFirstCard(first[0], second[0], priority2) else dif
    }

    private fun convertJokers(cards: String): String =
        if (cards.contains('J')) bestJokerReplacement(cards) else cards

    private fun bestJokerReplacement(cards: String): String {
        val buildMap = charCountMap(cards).filter { it.key != 'J' }
        return if (buildMap.isEmpty()) "AAAAA" else cards.replace('J', buildMap.maxBy { it.value }.key)
    }
}
