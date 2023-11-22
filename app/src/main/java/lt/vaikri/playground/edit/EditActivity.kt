package lt.vaikri.playground.edit

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import kotlinx.coroutines.flow.MutableStateFlow
import lt.vaikri.playground.ui.theme.PlaygroundTheme

class EditActivity : ComponentActivity() {
    private val state = MutableStateFlow(EditState(0, "123", "", "Label", 30, EditState.NumberType))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlaygroundTheme {
                val state by state.collectAsState()
                val change = remember { { updateState() } }
                EditScreen(state, change)
            }
        }
    }

    private fun updateState() {
        val type = state.value.id
        val newState = when ((type + 1) % 3) {
            0 -> {
                EditState(0, "123", "", "Label", 30, EditState.NumberType)
            }

            1 -> {
                EditState(
                    id = 1,
                    initial = "abc",
                    reset = "",
                    hint = "Label",
                    limit = 8,
                    type = EditState.TextType(true, Regex("^([A-Z]{2}\\d{2}|[A-Z]{1}\\d{1})\\s?[A-Z]{3}"), false)
                )
            }

            else -> {
                EditState(
                    id = 2,
                    initial = "abc",
                    reset = "",
                    hint = "Label",
                    limit = 30,
                    type = EditState.TextType(false, null, true)
                )
            }
        }
        state.value = newState
    }

    companion object {
        fun newIntent(context: Context): Intent = Intent(context, EditActivity::class.java)
    }
}
