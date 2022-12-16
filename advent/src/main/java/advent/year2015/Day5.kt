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
        for (i in line.indices) {
            val char = line[i]
        }
        return false
    }
}
