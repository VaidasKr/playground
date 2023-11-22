package lt.vaikri.playground.edit

import android.text.Editable
import android.text.InputFilter
import android.text.InputType
import android.text.TextWatcher
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalTextInputService
import androidx.compose.ui.text.input.EditCommand
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.ImeOptions
import androidx.compose.ui.text.input.PlatformTextInputService
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.TextInputService
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

@Composable
fun EditScreen(state: EditState, onSwitch: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val filters = remember(state.type) {
            buildList {
                add(InputFilter.LengthFilter(state.limit))
                if (state.type == EditState.NumberType) add(InputFilter { source, start, end, dest, dStart, dEnd ->
                    for (i in start until end) {
                        if (!source[i].isDigit()) {
                            return@InputFilter ""
                        }
                    }
                    null
                })
            }
        }
        val inputType = remember(state.type) {
            when (state.type) {
                EditState.NumberType -> InputType.TYPE_CLASS_NUMBER
                is EditState.TextType -> {
                    if (state.type.regex != null) {
                        InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                    } else {
                        InputType.TYPE_CLASS_TEXT
                    }
                }
            }
        }
        val resetTo = remember { mutableStateOf<String?>(null) }
        InputText(
            modifier = Modifier.fillMaxWidth(),
            text = state.initial,
            filters = filters,
            inputType = inputType,
            resetTo = resetTo,
            onTextChange = { value ->

            }
        )
        Button(onClick = { resetTo.value = state.reset }) {
            Text(text = "reset")
        }
        Button(onClick = onSwitch) {
            Text(text = "switch")
        }
    }
}

@Composable
private fun InputText(
    modifier: Modifier,
    text: String,
    filters: List<InputFilter>,
    resetTo: MutableState<String?>,
    inputType: Int,
    onTextChange: (String) -> Unit
) {
    val textState = remember(text) { mutableStateOf(text) }
    LaunchedEffect(key1 = resetTo.value) {
        resetTo.value?.apply {
            textState.value = this
            resetTo.value = null
        }
    }
    val watcher = remember {
        object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                onTextChange(p0?.toString().orEmpty())
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        }
    }
    val inputService: TextInputService = remember {
        TextInputService(
            object : PlatformTextInputService {
                override fun hideSoftwareKeyboard() {
                    TODO("Not yet implemented")
                }

                override fun showSoftwareKeyboard() {
                    TODO("Not yet implemented")
                }

                override fun startInput(
                    value: TextFieldValue,
                    imeOptions: ImeOptions,
                    onEditCommand: (List<EditCommand>) -> Unit,
                    onImeActionPerformed: (ImeAction) -> Unit
                ) {
                    TODO("Not yet implemented")
                }

                override fun stopInput() {
                    TODO("Not yet implemented")
                }

                override fun updateState(oldValue: TextFieldValue?, newValue: TextFieldValue) {
                    TODO("Not yet implemented")
                }
            }
        )
    }
    CompositionLocalProvider(
        LocalTextInputService provides inputService
    ) {

    }
    AndroidView(
        modifier = modifier,
        factory = { context ->
            TextInputLayout(context).apply {
                addView(TextInputEditText(context).apply {
                    setFilters(filters.toTypedArray())
                    maxLines = 6
                    this.inputType = inputType
                    addTextChangedListener(watcher)
                })
            }
        },
        update = { layout ->
            layout.editText?.setText(text)
        },
        onRelease = { layout ->
            layout.editText?.removeTextChangedListener(watcher)
        }
    )
}
