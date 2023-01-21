package advent.year2015

import java.lang.Integer.max
import java.util.concurrent.atomic.AtomicInteger

object Day22 {
    fun cheapestManaWin(bossState: BossState): Int {
        val start = State(
            hp = 50,
            mp = 500,
            shieldEffect = 0,
            poisonEffect = 0,
            rechargeEffect = 0,
            manaSpent = 0,
            bossState = bossState
        )
        val holder = AtomicInteger(Int.MAX_VALUE)
        afterFirstEffect(start, holder)
        return holder.get()
    }

    private fun afterFirstEffect(state: State, minManaPlaceholder: AtomicInteger) {
        if (state.hasWon) {
            state.updateMin(minManaPlaceholder)
            return
        } else if (!state.canCast) {
            return
        }
        if (state.canMm) afterSpell(state.castMm(), minManaPlaceholder)
        if (state.canDrain) afterSpell(state.castDrain(), minManaPlaceholder)
        if (state.canShield) afterSpell(state.castShield(), minManaPlaceholder)
        if (state.canPoison) afterSpell(state.castPoison(), minManaPlaceholder)
        if (state.canRecharge) afterSpell(state.castRecharge(), minManaPlaceholder)
    }

    private fun afterSpell(state: State, minManaPlaceholder: AtomicInteger) {
        if (state.manaSpent >= minManaPlaceholder.get()) return
        if (state.hasWon) {
            state.updateMin(minManaPlaceholder)
        } else {
            afterSecondEffects(state.runEffects(), minManaPlaceholder)
        }
    }

    private fun afterSecondEffects(state: State, minManaPlaceholder: AtomicInteger) {
        if (state.hasWon) {
            state.updateMin(minManaPlaceholder)
        } else {
            afterBoss(state.bossAttack(), minManaPlaceholder)
        }
    }

    private fun afterBoss(state: State, minManaPlaceholder: AtomicInteger) {
        if (!state.isAlive) return
        afterFirstEffect(state.runEffects(), minManaPlaceholder)
    }

    fun cheapestManaWinHard(bossState: BossState): Int {
        val start = State(
            hp = 50,
            mp = 500,
            shieldEffect = 0,
            poisonEffect = 0,
            rechargeEffect = 0,
            manaSpent = 0,
            bossState = bossState
        )
        val holder = AtomicInteger(Int.MAX_VALUE)
        afterFirstEffectHard(start, holder)
        return holder.get()
    }

    private fun afterFirstEffectHard(state: State, minManaPlaceholder: AtomicInteger) {
        if (state.hasWon) {
            state.updateMin(minManaPlaceholder)
            return
        } else if (!state.canCast) {
            return
        }
        playerTurn(state.reduceHp(), minManaPlaceholder)
    }

    private fun playerTurn(state: State, minManaPlaceholder: AtomicInteger) {
        if (state.isAlive) {
            castSpell(state, minManaPlaceholder)
        }
    }

    private fun castSpell(state: State, minManaPlaceholder: AtomicInteger) {
        if (state.canMm) afterSpellHard(state.castMm(), minManaPlaceholder)
        if (state.canDrain) afterSpellHard(state.castDrain(), minManaPlaceholder)
        if (state.canShield) afterSpellHard(state.castShield(), minManaPlaceholder)
        if (state.canPoison) afterSpellHard(state.castPoison(), minManaPlaceholder)
        if (state.canRecharge) afterSpellHard(state.castRecharge(), minManaPlaceholder)
    }

    private fun afterSpellHard(state: State, minManaPlaceholder: AtomicInteger) {
        if (state.manaSpent >= minManaPlaceholder.get()) return
        if (state.hasWon) {
            state.updateMin(minManaPlaceholder)
        } else {
            afterSecondEffectsHard(state.runEffects(), minManaPlaceholder)
        }
    }

    private fun afterSecondEffectsHard(state: State, minManaPlaceholder: AtomicInteger) {
        if (state.hasWon) {
            state.updateMin(minManaPlaceholder)
        } else {
            afterBossHard(state.bossAttack(), minManaPlaceholder)
        }
    }

    private fun afterBossHard(state: State, minManaPlaceholder: AtomicInteger) {
        if (!state.isAlive) return
        afterFirstEffectHard(state.runEffects(), minManaPlaceholder)
    }
}

data class State(
    val hp: Int,
    val mp: Int,
    val shieldEffect: Int,
    val poisonEffect: Int,
    val rechargeEffect: Int,
    val manaSpent: Int,
    val bossState: BossState
) {
/*
    Magic Missile costs 53 mana. It instantly does 4 damage.
    Drain costs 73 mana. It instantly does 2 damage and heals you for 2 hit points.
    Shield costs 113 mana. It starts an effect that lasts for 6 turns. While it is active, your armor is increased by 7.
    Poison costs 173 mana. It starts an effect that lasts for 6 turns. At the start of each turn while it is active, it deals the boss 3 damage.
    Recharge costs 229 mana. It starts an effect that lasts for 5 turns. At the start of each turn while it is active, it gives you 101 new mana.
*/

    val canCast get() = canMm && hp > 0
    val isAlive get() = hp > 0
    val hasWon get() = bossState.hp <= 0
    val canMm get() = mp >= 53
    val canDrain get() = mp >= 73
    val canShield get() = shieldEffect == 0 && mp >= 113
    val canPoison get() = poisonEffect == 0 && mp >= 173
    val canRecharge get() = rechargeEffect == 0 && mp >= 229

    fun castMm(): State {
        return copy(
            mp = mp - 53,
            bossState = bossState.reduceHp(4),
            manaSpent = manaSpent + 53
        )
    }

    fun updateMin(holder: AtomicInteger) {
        if (manaSpent < holder.get()) holder.set(manaSpent)
    }

    fun castDrain(): State {
        return copy(
            hp = hp + 2,
            mp = mp - 73,
            bossState = bossState.reduceHp(2),
            manaSpent = manaSpent + 73
        )
    }

    fun castShield(): State {
        return copy(shieldEffect = 6, mp = mp - 133, manaSpent = manaSpent + 113)
    }

    fun castPoison(): State {
        return copy(poisonEffect = 6, mp = mp - 173, manaSpent = manaSpent + 173)
    }

    fun castRecharge(): State {
        return copy(rechargeEffect = 5, mp = mp - 229, manaSpent = manaSpent + 229)
    }

    fun runEffects(): State {
        var bossState = bossState
        var shieldEffect = shieldEffect
        var poisonEffect = poisonEffect
        var rechargeEffect = rechargeEffect
        var mp = mp
        if (shieldEffect > 0) shieldEffect--
        if (poisonEffect > 0) {
            poisonEffect--
            bossState = bossState.reduceHp(3)
        }
        if (rechargeEffect > 0) {
            mp += 101
            rechargeEffect--
        }
        return copy(
            mp = mp,
            shieldEffect = shieldEffect,
            poisonEffect = poisonEffect,
            rechargeEffect = rechargeEffect,
            bossState = bossState
        )
    }

    fun bossAttack(): State {
        val damage = if (shieldEffect > 0) max(bossState.att - 7, 1) else bossState.att
        return copy(hp = hp - damage)
    }

    fun reduceHp(): State {
        return copy(hp = hp - 1)
    }
}

data class BossState(val hp: Int, val att: Int) {
    fun reduceHp(dmg: Int): BossState = copy(hp = hp - dmg)
}
