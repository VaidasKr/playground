package advent.year2015

object Day11 {
    fun isValid(pass: String): Boolean {
        var hasStraight = false
        val pairs = hashSetOf<Char>()
        for (i in pass.indices) {
            val char = pass[i]
            if (char == 'i' || char == 'o' || char == 'l') return false
            if (!hasStraight) {
                if (i + 2 < pass.length) {
                    if (pass[i + 1] - char == 1 && pass[i + 2] - pass[i + 1] == 1) hasStraight = true
                } else {
                    return false
                }
            }
            if (i + 1 < pass.length && pass[i + 1] == char) {
                pairs.add(char)
            }
        }
        return hasStraight && pairs.size > 1
    }

    fun next(current: String): String {
        var next = current.nextWithoutCheck()
        while (!isValid(next)) {
            next = next.nextWithoutCheck()
        }
        return next
    }

    private fun String.nextWithoutCheck(): String {
        val chars = toCharArray()
        var shouldIncrement = chars.lastIndex
        while (shouldIncrement > -1) {
            val updated = chars[shouldIncrement] + 1
            if (updated > 'z') {
                chars[shouldIncrement] = 'a'
            } else {
                chars[shouldIncrement] = updated
                break
            }
            shouldIncrement--
        }
        return String(chars)
    }
}
