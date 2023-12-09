package advent.year2023

import kotlin.math.min

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
        val copies = IntArray(lines.size)
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
            val cardInstances = copies[i] + 1
            cardSum += cardInstances
            for (j in (i + 1) until min(copies.size, i + 1 + cardMatches)) {
                copies[j] = copies[j] + cardInstances
            }
        }
        return cardSum
    }
}
