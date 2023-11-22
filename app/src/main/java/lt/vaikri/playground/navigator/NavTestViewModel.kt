package lt.vaikri.playground.navigator

import android.util.Log
import androidx.collection.SparseArrayCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NavTestViewModel : ViewModel() {
    private val _state = MutableStateFlow(NavTestState(sparseArrayOf(NavTestItem(0, false)), null, null))
    private var currentIndex = 0

    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.collectLatest { state ->
                Log.d("xzxz", "state -> $state")
            }
        }
    }

    private fun sparseArrayOf(item: NavTestItem): SparseArrayCompat<NavTestItem> {
        return SparseArrayCompat<NavTestItem>(1).apply {
            append(item.id, item)
        }
    }

    fun onNext(item: NavTestItem, footerForNext: Boolean) {
        Log.d("xzxz", "onNext with $item add footer: $footerForNext")
        _state.update { state ->
            if (item.id == FINISH) {
                state.copy(event = Close)
            } else {
                val next = NavTestItem(item.next, footerForNext)
                val index = state.items.indexOfKey(item.id)
                val items = SparseArrayCompat<NavTestItem>()
                var hasUp = false
                for (i in 0 until index) {
                    val element = state.items.valueAt(i)
                    if (element.hasFooter) {
                        items.append(element.id, element)
                        hasUp = true
                    }
                }
                items.append(item.id, item)
                if (item.hasFooter) {
                    hasUp = true
                }
                items.append(next.id, next)
                val footer = if (next.hasFooter) Footer(hasUp, false) else null
                currentIndex = items.size() - 1
                state.copy(items = items, footer = footer, event = Transition(next.id, true))
            }
        }
    }

    fun onFooterUp() {
        Log.d("xzxz", "footer up from $currentIndex")
        currentIndex--
        _state.update { state ->
            var canUp = false
            for (i in 0 until currentIndex) {
                if (state.items.valueAt(i).hasFooter) {
                    canUp = true
                }
            }
            val footer = state.footer!!.copy(canDown = true, canUp = canUp)
            state.copy(footer = footer, event = Transition(state.items.keyAt(currentIndex), false))
        }
    }

    fun onFooterDown() {
        Log.d("xzxz", "footer down from $currentIndex")
        currentIndex++
        _state.update { state ->
            val footer = state.footer!!.copy(canDown = currentIndex < state.items.size() - 1, canUp = true)
            state.copy(footer = footer, event = Transition(state.items.keyAt(currentIndex), true))
        }
    }

    fun onEventConsumed(event: Event) {
        _state.update {
            if (it.event == event) it.copy(event = null) else it
        }
    }

    companion object {
        private const val FINISH = 12
    }
}
