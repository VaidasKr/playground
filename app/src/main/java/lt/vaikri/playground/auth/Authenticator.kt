package lt.vaikri.playground.auth

import android.app.Activity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kotlinx.coroutines.channels.onFailure
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class Authenticator {
    fun signIn(activity: Activity): Flow<AuthResult> {
        return callbackFlow {
            val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("379356221365-f72l5nmsjkhkub78pefak62fev8belph.apps.googleusercontent.com")
                .requestEmail()
                .build()
            val client = GoogleSignIn.getClient(activity, options)

            val task = client.silentSignIn()
                .addOnCompleteListener(activity) { accountTask ->
                    if (accountTask.isSuccessful) {
                        val account = accountTask.result
                        trySend(AuthResult())
                            .onFailure {
                                it?.printStackTrace()
                            }
                    } else {
                        trySend(AuthResult()) // failed show sign in
                            .onFailure {
                                it?.printStackTrace()
                            }
                    }
                }
        }
    }
}
