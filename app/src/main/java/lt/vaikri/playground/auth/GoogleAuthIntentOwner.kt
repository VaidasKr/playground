package lt.vaikri.playground.auth

import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignInClient

class GoogleAuthIntentOwner(private val client: GoogleSignInClient) : AuthIntentOwner {
    override val authIntent: Intent
        get() = client.signInIntent
}
