package lt.vaikri.playground.auth

sealed interface AuthAction {
    object Login : AuthAction
}
