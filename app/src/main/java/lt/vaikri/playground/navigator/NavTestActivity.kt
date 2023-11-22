package lt.vaikri.playground.navigator

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import lt.vaikri.playground.ui.theme.PlaygroundTheme

class NavTestActivity : ComponentActivity() {
    private val viewModel by viewModels<NavTestViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlaygroundTheme {
                NavTestScreen(viewModel = viewModel, ::finish)
            }
        }
    }

    companion object {
        fun newIntent(context: Context): Intent = Intent(context, NavTestActivity::class.java)
    }
}
