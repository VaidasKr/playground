package advent.year2015

import advent.assert
import org.junit.Assert
import org.junit.Test

class Day22Test {
    private class StateHolder(state: State) {
        private var inner = state

        fun update(update: State.() -> State) {
            inner = update(inner)
        }

        fun expect(expected: State) {
            Assert.assertEquals(expected, inner)
        }
    }

    private fun State.holder() = StateHolder(this)

    @Test
    fun `sample 1`() {
        val holder = State(10, 250, 0, 0, 0, 0, BossState(13, 8)).holder()
        holder.update { castPoison() }
        holder.expect(State(10, 77, 0, 6, 0, 173, BossState(13, 8)))
        holder.update { runEffects() }
        holder.expect(State(10, 77, 0, 5, 0, 173, BossState(10, 8)))
        holder.update { bossAttack() }
        holder.expect(State(2, 77, 0, 5, 0, 173, BossState(10, 8)))
        holder.update { runEffects() }
        holder.expect(State(2, 77, 0, 4, 0, 173, BossState(7, 8)))
        holder.update { castMm() }
        holder.expect(State(2, 24, 0, 4, 0, 226, BossState(3, 8)))
        holder.update { runEffects() }
        holder.expect(State(2, 24, 0, 3, 0, 226, BossState(0, 8)))
    }

    @Test
    fun `sample 2`() {
        val holder = State(10, 250, 0, 0, 0, 0, BossState(14, 8)).holder()
        holder.update { castRecharge() }
        holder.expect(State(10, 21, 0, 0, 5, 229, BossState(14, 8)))
        holder.update { runEffects() }
        holder.expect(State(10, 122, 0, 0, 4, 229, BossState(14, 8)))
        holder.update { bossAttack() }
        holder.expect(State(2, 122, 0, 0, 4, 229, BossState(14, 8)))
    }

    @Test
    fun actual() {
        Day22.cheapestManaWin(BossState(51, 9)).assert(900)
        Day22.cheapestManaWinHard(BossState(51, 9)).assert(1216)
    }
}
