package advent

import org.junit.Test

class Day12Test {
    @Test
    fun name() {
        Day12.shortestDistanceFromStoE(sampleInput).assert(31)
        Day12.shortestDistanceFromStoE(actualInput).assert(380)
        Day12.shortestDistanceFromAnyAtoE(sampleInput).assert(29)
        Day12.shortestDistanceFromAnyAtoE(actualInput).assert(375)
    }

    companion object {
        val sampleInput = """
            Sabqponm
            abcryxxl
            accszExk
            acctuvwj
            abdefghi
        """.trimIndent()

        val actualInput = """
            abcccccccaaaaaaaaccccccccccaaaaaaccccccaccaaaaaaaccccccaacccccccccaaaaaaaaaaccccccccccccccccccccccccccccccccaaaaa
            abcccccccaaaaaaaaacccccccccaaaaaacccccaaacaaaaaaaaaaaccaacccccccccccaaaaaaccccccccccccccccccccccccccccccccccaaaaa
            abcccccccaaaaaaaaaaccccccccaaaaaacaaacaaaaaaaaaaaaaaaaaaccccccccccccaaaaaaccccccccccccccaaacccccccccccccccccaaaaa
            abaaacccccccaaaaaaacccccccccaaacccaaaaaaaaaaaaaaaaaaaaaaaaacccccccccaaaaaaccccccccccccccaaacccccccccccccccccaaaaa
            abaaaaccccccaaaccccccccccccccccccccaaaaaaaaacaaaacacaaaaaacccccccccaaaaaaaacccccccccccccaaaaccaaacccccccccccaccaa
            abaaaaccccccaaccccaaccccccccccccccccaaaaaaacaaaaccccaaaaaccccccccccccccccacccccccccccccccaaaaaaaaacccccccccccccca
            abaaaaccccccccccccaaaacccccccccaacaaaaaaaacccaaacccaaacaacccccccccccccccccccccccccccciiiiaaaaaaaacccccccccccccccc
            abaaacccccccccccaaaaaacccccccccaaaaaaaaaaacccaaacccccccaacccccccccccaacccccccccccccciiiiiiijaaaaccccccccaaccccccc
            abaaaccccccccccccaaaacccccccccaaaaaaaacaaacccaaaccccccccccccccccccccaaacaaacccccccciiiiiiiijjjacccccccccaaacccccc
            abcccccaacaacccccaaaaaccccccccaaaaaacccccacaacccccccccccccccccccccccaaaaaaaccccccciiiinnnoijjjjjjjjkkkaaaaaaacccc
            abcccccaaaaacccccaacaaccccccccccaaaacccaaaaaaccccccccccccccccccccccccaaaaaaccccccciiinnnnooojjjjjjjkkkkaaaaaacccc
            abccccaaaaacccccccccccccccccccccaccccccaaaaaaaccccccccccccccccccccaaaaaaaaccccccchhinnnnnoooojjooopkkkkkaaaaccccc
            abccccaaaaaaccccccccccccccccccccccccccccaaaaaaacccccccccccccccccccaaaaaaaaacccccchhhnnntttuooooooopppkkkaaaaccccc
            abccccccaaaaccccccccccacccccccccccccccccaaaaaaacccaaccccccccccccccaaaaaaaaaaccccchhhnnttttuuoooooppppkkkaaaaccccc
            abccccccaccccccccccccaaaacaaaccccccccccaaaaaacaaccaacccaaccccccccccccaaacaaacccchhhnnnttttuuuuuuuuupppkkccaaccccc
            abccccccccccccccaaccccaaaaaaaccccccccccaaaaaacaaaaaacccaaaaaaccccccccaaacccccccchhhnnntttxxxuuuuuuupppkkccccccccc
            abcccccccccccccaaaacccaaaaaaacccaccccccccccaaccaaaaaaacaaaaaaccccccccaacccaaccchhhhnnnttxxxxuuyyyuupppkkccccccccc
            abcccccccccccccaaaaccaaaaaaaaacaaacccccccccccccaaaaaaaaaaaaaccccccccccccccaaachhhhmnnnttxxxxxxyyyuvppkkkccccccccc
            abcccccccccccccaaaacaaaaaaaaaaaaaaccccccccccccaaaaaacaaaaaaaccccccccccccccaaaghhhmmmttttxxxxxyyyyvvpplllccccccccc
            abccacccccccccccccccaaaaaaaaaaaaaaccccccccccccaaaaaacccaaaaaacccaacaacccaaaaagggmmmttttxxxxxyyyyvvppplllccccccccc
            SbaaaccccccccccccccccccaaacaaaaaaaacccccccccccccccaacccaaccaacccaaaaacccaaaagggmmmsttxxxEzzzzyyvvvppplllccccccccc
            abaaaccccccccccccccccccaaaaaaaaaaaaacaaccccccccccccccccaaccccccccaaaaaccccaagggmmmsssxxxxxyyyyyyvvvqqqlllcccccccc
            abaaacccccccccccccccccccaaaaaaaaaaaaaaaaacccccccccccccccccccccccaaaaaaccccaagggmmmsssxxxwywyyyyyyvvvqqlllcccccccc
            abaaaaacccccccccccccccccccaacaaaccaaaaaaacccccccccccccccccccccccaaaaccccccaagggmmmssswwwwwyyyyyyyvvvqqqllcccccccc
            abaaaaaccccccccccccccccccccccaaaccccaaaacccccccccccccccccaaccaacccaaccccccccgggmmmmssssswwyywwvvvvvvqqqlllccccccc
            abaaaaacccccccccccccaccacccccaaaccccaaaacccccccccccccccccaaaaaacccccccccccaaggggmllllsssswwywwwvvvvqqqqlllccccccc
            abaaccccccccccccccccaaaaccccccccccccaccaccccccccccccccccccaaaaacccccccccccaaagggglllllssswwwwwrrqqqqqqmmllccccccc
            abaaccccccccccccccccaaaaaccccccaaccaaccccccccccccccccccccaaaaaaccaacccccccaaaaggfffllllsswwwwrrrrqqqqqmmmcccccccc
            abacaaaccccccccccccaaaaaaccccccaaaaaaccccccaacccccccccccaaaaaaaacaaacaaccccaaaaffffflllsrrwwwrrrmmmmmmmmmcccccccc
            abaaaaaccccccccccccaaaaaaccccccaaaaaccccccaaaaccccccccccaaaaaaaacaaaaaaccccaaaaccfffflllrrrrrrkkmmmmmmmccccaccccc
            abaaaacccccccccccccccaaccccccccaaaaaacccccaaaacccccccccccccaaccaaaaaaaccccccccccccffflllrrrrrkkkmmmmmccccccaccccc
            abaaacccccccccccccccccccccccccaaaaaaaaccccaaaacccccccccccccaaccaaaaaaacccccccccccccfffllkrrrkkkkmddddcccccaaacccc
            abaaacccccccccccccccccccccccccaaaaaaaacccccccccccccccccccccccccccaaaaaaccccccccccccfffllkkkkkkkdddddddcaaaaaacccc
            abaaaacccccccccccccccccccccccccccaaccccccccccccccccccccccccccccccaacaaacccccccccccccfeekkkkkkkddddddcccaaaccccccc
            abcaaacccccccccccaaaccccccccaacccaaccccaaaaaccccaaaccccccccccccccaaccccccccccccccccceeeeekkkkdddddccccccaaccccccc
            abccccccccccccccaaaaaaccccccaaacaaccacaaaaaaaccaaaaccccccccccaccaaccccccccccccccccccceeeeeeeedddacccccccccccccccc
            abccccccccccccccaaaaaacccccccaaaaacaaaaaccaaaaaaaacccccccccccaaaaacccccccccccccccccccceeeeeeedaaacccccccccccccaaa
            abccccccaaacccccaaaaacccccccaaaaaacaaaaaaaaaaaaaaaccccccccccccaaaaaccccccccccccccccccccceeeeecaaacccccccccccccaaa
            abccccccaaaccccccaaaaacccccaaaaaaaaccaaaaacaaaaaaccccccccccccaaaaaacccccccccccccccccccccaaaccccaccccccccccccccaaa
            abccccaacaaaaacccaaaaacccccaaaaaaaacaaaaaaaaaaaaaaaccccaaaaccaaaacccccccccccccccccccccccaccccccccccccccccccaaaaaa
            abccccaaaaaaaaccccccccccccccccaaccccaacaaaaaaaaaaaaaaccaaaaccccaaacccccccccccccccccccccccccccccccccccccccccaaaaaa
        """.trimIndent()
    }
}
