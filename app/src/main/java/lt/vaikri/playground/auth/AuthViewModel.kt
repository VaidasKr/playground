package lt.vaikri.playground.auth

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor() : ViewModel() {
    init {
        // silent login
        // show progress
        // show login or logged in state
    }
}
