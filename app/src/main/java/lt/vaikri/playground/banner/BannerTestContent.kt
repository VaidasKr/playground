package lt.vaikri.playground.banner

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BannerTestContent() {
    Column(Modifier.fillMaxSize()) {
        val service = remember {
            StatusBannerService()
        }
        val state by service.stateFlow.collectAsState()
        BannerMessage.MessageWithPadding(state)
        val text = if (state.messages.isEmpty()) {
            "Empty"
        } else {
            state.messages.joinToString(separator = "\n") { it.name }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                fontSize = 20.sp
            )
        }
        Row {
            StatusMessage.values().forEach {
                Button(
                    modifier = Modifier
                        .weight(1f)
                        .padding(4.dp),
                    onClick = { service.addMessage(it) }) {
                    Text(text = "Add ${it.name}")
                }
            }
        }
        Row {
            StatusMessage.values().forEach {
                Button(modifier = Modifier
                    .weight(1f)
                    .padding(4.dp), onClick = { service.removeMessage(it) }) {
                    Text(text = "Rem ${it.name}")
                }
            }
        }
    }
}
