package lt.vaikri.playground.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val authenticator: Authenticator) : ViewModel() {
    private val _state: MutableStateFlow<AuthState> = MutableStateFlow(AuthState.Loading)
    val state: StateFlow<AuthState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            authenticator.silentSignIn().map { authResult ->
                if (authResult.token == null) {
                    AuthState.Login(GoogleAuthIntentOwner(authenticator.client))
                } else {
                    AuthState.LoggedIn
                }
            }.catch {
                Timber.e(it)
                emit(AuthState.Login(GoogleAuthIntentOwner(authenticator.client)))
            }
                .collect { newState ->
                    _state.value = newState
                }
        }
    }

    fun onAction(authAction: AuthAction) {
        _state.value = AuthState.LoggedIn
    }
}
