package advent.year2015

object Day8 {

    //    Santa's list is a file that contains many double-quoted string literals, one on each line.
//    The only escape sequences used are \\ (which represents a single backslash),
//    \" (which represents a lone double-quote character),
//    and \x plus two hexadecimal characters (which represents a single character with that ASCII code).
    fun memorySpaceOf(line: String): Int {
        var chars = 0
        var i = 1
        while (i < line.lastIndex) {
            if (line[i] == '\\') {
                val next = line[i + 1]
                if (next == '"' || next == '\\') {
                    i += 2
                    chars++
                } else if (next == 'x') {
                    i += 4
                    chars++
                }
            } else {
                i++
                chars++
            }
        }
        return chars
    }

    fun encodeSize(line: String): Int {
        var size = 2
        var i = 0
        while (i < line.length) {
            val char = line[i]
            if (char == '"' || char == '\\') {
                size += 2
            } else{
                size++
            }
            i++
        }
        return size
    }
}
