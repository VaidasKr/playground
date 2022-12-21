package advent.year2015

object Day5 {
    private val vovels = "aeiou".toCharArray()
    private val badPairs = "abcdpqxy".toCharArray()
    private val badPairCount = 4

    fun isNice(line: String): Boolean {
        var hasPairs = false
        var previous = '!'
        var vovelCount = 0
        for (i in line.indices) {
            val char = line[i]
            if (vovels.contains(char)) vovelCount++
            hasPairs = hasPairs || previous == char
            for (j in 0 until badPairCount) {
                if (badPairs[j * 2] == previous && badPairs[j * 2 + 1] == char) return false
            }
            previous = char
        }
        return hasPairs && vovelCount > 2
    }

    fun isNice2(line: String): Boolean {
        var hasRepeatPair = false
        var hasRepeatingChar = false
        for (i in line.indices) {
            val char = line[i]
            if (i + 2 < line.length && line[i + 2] == char) hasRepeatingChar = true
            if (i + 1 < line.length - 2) {
                val next = line[i + 1]
                for (j in i + 2 until line.lastIndex) {
                    if (line[j] == char && line[j + 1] == next) hasRepeatPair = true
                }
            }
        }
        return hasRepeatPair && hasRepeatingChar
    }
}
