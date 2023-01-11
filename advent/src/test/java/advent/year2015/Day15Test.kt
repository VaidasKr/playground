package advent.year2015

import advent.assert
import org.junit.Test

class Day15Test {
    @Test
    fun sample() {
        val input = "Butterscotch: capacity -1, durability -2, flavor 6, texture 3, calories 8\n" +
            "Cinnamon: capacity 2, durability 3, flavor -2, texture -1, calories 3"
        val day15 = Day15(input)
        day15.bestScore(100).assert(62842880)
        day15.bestScoreFor500Cals(100).assert(57600000)
    }

    @Test
    fun actual() {
        val input = """
            Frosting: capacity 4, durability -2, flavor 0, texture 0, calories 5
            Candy: capacity 0, durability 5, flavor -1, texture 0, calories 8
            Butterscotch: capacity -1, durability 0, flavor 5, texture 0, calories 6
            Sugar: capacity 0, durability 0, flavor -2, texture 2, calories 1
        """.trimIndent()
        val day15 = Day15(input)
        day15.bestScore(100).assert(18965440)
        day15.bestScoreFor500Cals(100).assert(15862900)
    }
}
