package lt.vaikri.playground.navigator

import androidx.collection.SparseArrayCompat

data class NavTestState(
    val items: SparseArrayCompat<NavTestItem>,
    val footer: Footer?,
    val event: Event?
)

data class Footer(val canUp: Boolean, val canDown: Boolean)

data class NavTestItem(val id: Int, val hasFooter: Boolean) {
    val text: String get() = "Id: $id\nfooter: $hasFooter"
    val next get() = id + 1
}

sealed interface Event

object Close : Event

data class Transition(val id: Int, val isDown: Boolean) : Event
