package advent.year2015

object Day3 {
    fun calculateVisitedHouses(input: String): Int {
        var santaX = 0
        var santaY = 0
        val visited = hashSetOf(santaY to santaY)
        input.forEach { char ->
            when (char) {
                '>' -> santaX++
                '<' -> santaX--
                '^' -> santaY--
                'v' -> santaY++
            }
            visited.add(santaX to santaY)
        }
        return visited.size
    }

    fun calculateVisitedHousesWithRobo(input: String): Int {
        val santa = intArrayOf(0, 0)
        val robo = intArrayOf(0, 0)
        val visited = hashSetOf(santa.toPair())
        input.forEachIndexed { index, char ->
            val giver = if (index % 2 == 0) santa else robo
            when (char) {
                '>' -> giver[0] = giver[0] + 1
                '<' -> giver[0] = giver[0] - 1
                '^' -> giver[1] = giver[1] - 1
                'v' -> giver[1] = giver[1] + 1
            }
            visited.add(giver.toPair())
        }
        return visited.size
    }

    private fun IntArray.toPair(): Pair<Int, Int> = get(0) to get(1)
}
