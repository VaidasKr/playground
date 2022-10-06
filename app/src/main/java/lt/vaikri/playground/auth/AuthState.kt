package lt.vaikri.playground.auth

sealed interface AuthState {
    object Loading : AuthState
    object LoggedIn : AuthState
    data class Login(val intentOwner: AuthIntentOwner) : AuthState
}
