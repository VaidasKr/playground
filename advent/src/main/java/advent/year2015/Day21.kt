package advent.year2015

import advent.packInts

object Day21 {
    private val weapons = listOf(
        intArrayOf(8, 4, 0),
        intArrayOf(10, 5, 0),
        intArrayOf(25, 6, 0),
        intArrayOf(40, 7, 0),
        intArrayOf(74, 8, 0)
    )
    private val armors = listOf(
        intArrayOf(0, 0, 0),
        intArrayOf(13, 0, 1),
        intArrayOf(31, 0, 2),
        intArrayOf(53, 0, 3),
        intArrayOf(75, 0, 4),
        intArrayOf(102, 0, 5)
    )
    private val rings = listOf(
        intArrayOf(0, 0, 0),
        intArrayOf(0, 0, 0),
        intArrayOf(25, 1, 0),
        intArrayOf(50, 2, 0),
        intArrayOf(100, 3, 0),
        intArrayOf(20, 0, 1),
        intArrayOf(40, 0, 2),
        intArrayOf(80, 0, 3)
    )

    fun findCheapestWinAndMostExpensiveLoss(input: String): Long {
        val inputs = input.split('\n')
            .map { line ->
                val lastSpace = line.indexOfLast { it == ' ' }
                line.substring(lastSpace + 1).toInt()
            }
        return findCheapestWinAndMostExpensiveLoss(inputs.toIntArray())
    }

    private fun findCheapestWinAndMostExpensiveLoss(bossProperties: IntArray): Long {
        val stats = IntArray(3) { 100 }
        var cheapWin = Int.MAX_VALUE
        var expensiveLoss = 0
        for (w in weapons.indices) {
            val weapon = weapons[w]
            for (a in armors.indices) {
                val armor = armors[a]
                for (r1 in 0 until rings.lastIndex) {
                    val ring1 = rings[r1]
                    for (r2 in r1 + 1 until rings.size) {
                        val ring2 = rings[r2]
                        val price = weapon[0] + armor[0] + ring1[0] + ring2[0]
                        stats[1] = weapon[1] + armor[1] + ring1[1] + ring2[1]
                        stats[2] = weapon[2] + armor[2] + ring1[2] + ring2[2]
                        if (canWin(stats, bossProperties)) {
                            if (price < cheapWin) cheapWin = price
                        } else {
                            if (price > expensiveLoss) {
                                expensiveLoss = price
                            }
                        }
                    }
                }
            }
        }
        return packInts(cheapWin, expensiveLoss)
    }

    fun canWin(user: IntArray, boss: IntArray): Boolean =
        boss[0] / (user[1] - boss[2]).coerceAtLeast(1) <= user[0] / (boss[1] - user[2]).coerceAtLeast(1)
}
