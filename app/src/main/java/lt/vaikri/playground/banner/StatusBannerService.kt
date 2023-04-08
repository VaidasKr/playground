package lt.vaikri.playground.banner

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class StatusBannerService {
    val stateFlow = _state.asStateFlow()
    val state get() = stateFlow.value

    fun addMessage(message: StatusMessage) {
        _state.update { it + message }
    }

    fun removeMessage(message: StatusMessage) {
        _state.update { it - message }
    }

    companion object {
        private val _state = MutableStateFlow(StatusBannerState(emptyList()))
    }
}
