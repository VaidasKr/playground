package lt.vaikri.playground.auth

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.onFailure
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import timber.log.Timber
import javax.inject.Inject

class Authenticator @Inject constructor(@ApplicationContext private val context: Context) {
    val client by lazy {
        val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("379356221365-f72l5nmsjkhkub78pefak62fev8belph.apps.googleusercontent.com")
            .requestEmail()
            .build()
        GoogleSignIn.getClient(context, options)
    }

    fun silentSignIn(): Flow<AuthResult> {
        return callbackFlow {
            val listener = ClearableListener { accountTask ->
                if (accountTask.isSuccessful) {
                    val account = accountTask.result
                    trySendBlocking(AuthResult(account?.idToken))
                        .onFailure {
                            it?.apply { Timber.e(this) }
                        }
                } else {
                    trySendBlocking(AuthResult(null)) // failed show sign in
                        .onFailure {
                            it?.apply { Timber.e(this) }
                        }
                }
            }
            client.silentSignIn()
                .addOnCompleteListener(listener)
            awaitClose { listener.clear() }
        }
    }

    private class ClearableListener(
        listener: OnCompleteListener<GoogleSignInAccount?>
    ) : OnCompleteListener<GoogleSignInAccount?> {
        private var listener: OnCompleteListener<GoogleSignInAccount?>? = listener

        override fun onComplete(result: Task<GoogleSignInAccount?>) {
            listener?.onComplete(result)
        }

        fun clear() {
            listener = null
        }
    }
}
