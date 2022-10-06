package lt.vaikri.playground.auth.compose

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import lt.vaikri.playground.auth.AuthAction
import lt.vaikri.playground.auth.AuthIntentOwner
import lt.vaikri.playground.auth.AuthState
import lt.vaikri.playground.auth.AuthViewModel
import lt.vaikri.playground.ui.theme.PlaygroundTheme
import timber.log.Timber


@Composable
fun AuthScreen(viewModel: AuthViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    AuthContent(state) {
        viewModel.onAction(AuthAction.Login)
    }
}

@Composable
private fun AuthContent(state: AuthState, onLogin: () -> Unit) {
    when (state) {
        AuthState.Loading -> Loading()
        AuthState.LoggedIn -> LoggedIn()
        is AuthState.Login -> Login(state.intentOwner, onLogin)
    }
}

@Composable
fun Loading() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun LoggedIn() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            modifier = Modifier.padding(32.dp),
            text = "You are logged in",
            style = MaterialTheme.typography.h4
        )
    }
}

@Composable
fun Login(intentOwner: AuthIntentOwner, onLogin: () -> Unit) {
    val startForResult =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                if (intent != null) {
                    val task: Task<GoogleSignInAccount> =
                        GoogleSignIn.getSignedInAccountFromIntent(intent)
                    handleSignInResult(task, onLogin)
                }
            }
        }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "You need to log in", style = MaterialTheme.typography.h4)
        Button(
            modifier = Modifier.padding(top = 24.dp),
            onClick = { startForResult.launch(intentOwner.authIntent) }
        ) {
            Text(text = "Login")
        }
    }
}

fun handleSignInResult(task: Task<GoogleSignInAccount>, onLogin: () -> Unit) {
    runCatching {
        val account: GoogleSignInAccount = task.getResult(ApiException::class.java)
        val idToken = account.idToken // send to be
    }.onFailure { cause -> Timber.e(cause) }
        .onSuccess { onLogin() }
}

@Preview
@Composable
private fun PreviewAuthContent(@PreviewParameter(AuthPreviewParameterProvider::class) state: AuthState) {
    PlaygroundTheme {
        AuthContent(state) {}
    }
}
