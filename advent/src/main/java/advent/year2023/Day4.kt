package advent.year2023

object Day4 {
    fun calculateCardScoreSum(sampleInput: String): Long {
        var sum = 0L
        sampleInput.split('\n').forEach { line ->
            val numbers = line.substring(line.indexOf(':') + 2).split(" | ")
            val winning = numbers[0].split(' ').filter { it.isNotBlank() }.map { it.toInt() }.toSet()
            var cardScore = 0L
            numbers[1].split(' ').filter { it.isNotBlank() }.forEach {
                val number = it.toInt()
                if (winning.contains(number)) {
                    if (cardScore == 0L) {
                        cardScore = 1
                    } else {
                        cardScore *= 2
                    }
                }
            }
            sum += cardScore
        }
        return sum
    }

    fun calculateScratchCardSum(sampleInput: String): Long {
        var cardSum = 0L

        val lines = sampleInput.trim().split('\n')
        val map = mutableMapOf<Int, Int>()
        for (i in lines.indices) {
            val line = lines[i]
            val numbers = line.substring(line.indexOf(':') + 2).split(" | ")
            val winning = numbers[0].split(' ').filter { it.isNotBlank() }.map { it.toInt() }.toSet()
            var cardMatches = 0
            numbers[1].split(' ').filter { it.isNotBlank() }.forEach {
                val number = it.toInt()
                if (winning.contains(number)) {
                    cardMatches++
                }
            }
            val cardInstances = map.getOrElse(i) { 0 } + 1
            cardSum += cardInstances
            for (j in 0 until cardMatches) {
                val cardToUpdateIndex = j + i + 1
                map[cardToUpdateIndex] = map.getOrElse(cardToUpdateIndex) { 0 } + cardInstances
            }
        }
        return cardSum
    }
}
