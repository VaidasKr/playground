package advent.year2015

object Day20 {
    fun fastPart1(presents: Int): Int {
        val buffer = IntArray(presents / 10) { 10 }
        for (elf in 2 until buffer.size) {
            var house = elf
            while (house < buffer.size) {
                buffer[house] += elf * 10
                house += elf
            }
            if (buffer[elf] >= presents) return elf
        }
        return 0
    }

    fun fastPart2(presents: Int): Int {
        val buffer = IntArray(presents / 10)
        for (elf in 1 until buffer.size) {
            var house = elf
            var iterations = 0
            while (house < buffer.size && iterations++ < 50) {
                buffer[house] += elf * 11
                house += elf
            }
            if (buffer[elf] >= presents) return elf
        }
        return 0
    }
}
