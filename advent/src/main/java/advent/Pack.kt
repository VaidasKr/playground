package advent

inline fun packInts(val1: Int, val2: Int): Long {
    return val1.toLong().shl(32) or (val2.toLong() and 0xFFFFFFFF)
}

inline fun unpackInt1(value: Long): Int {
    return value.shr(32).toInt()
}

inline fun unpackInt2(value: Long): Int {
    return value.and(0xFFFFFFFF).toInt()
}
