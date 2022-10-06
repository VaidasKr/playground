package lt.vaikri.playground.auth.compose

import android.content.Intent
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import lt.vaikri.playground.auth.AuthIntentOwner
import lt.vaikri.playground.auth.AuthState

class AuthPreviewParameterProvider : PreviewParameterProvider<AuthState> {
    override val values = sequenceOf(
        AuthState.Loading,
        AuthState.LoggedIn,
        AuthState.Login(object : AuthIntentOwner {
            override val authIntent: Intent
                get() = TODO("Not yet implemented")
        })
    )
}
