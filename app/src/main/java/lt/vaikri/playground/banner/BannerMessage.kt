package lt.vaikri.playground.banner

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import lt.vaikri.playground.R

object BannerMessage {
    @Composable
    fun MessageOnly(state: StatusBannerState) {
        val messages = state.messages
        AnimatedVisibility(
            modifier = Modifier.fillMaxWidth(),
            visible = messages.isNotEmpty(),
            enter = fadeIn(animationSpec = keyframes { durationMillis = 1500 }) +
                slideInVertically(animationSpec = keyframes { durationMillis = 1500 }),
            exit = fadeOut(animationSpec = keyframes { durationMillis = 1500 }) +
                slideOutVertically(animationSpec = keyframes { durationMillis = 1500 })
        ) {
            MessageContent(state.renderMessage())
        }
    }

    @Composable
    private fun MessageContent(message: String) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp),
            text = message,
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            color = Color.White
        )
    }

    @Composable
    fun MessageWithPadding(state: StatusBannerState) {
        val newMessage = state.renderMessage()
        var message by remember {
            mutableStateOf(newMessage)
        }
        LaunchedEffect(key1 = newMessage) {
            if (newMessage.isNotBlank()) {
                message = newMessage
            }
        }
        Box(
            Modifier
                .fillMaxWidth()
                .background(Color.Red)
        ) {
            val paddingValues = WindowInsets.statusBars.asPaddingValues()
            StatusBarSize(paddingValues = paddingValues)
            AnimatedVisibility(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingValues = paddingValues),
                visible = state.hasMessages,
                enter = fadeIn(animationSpec = tween(1500, easing = LinearEasing)) +
                    expandVertically(animationSpec = tween(1500, easing = LinearEasing), clip = false),
                exit = fadeOut(animationSpec = tween(1500, easing = LinearEasing)) +
                    shrinkVertically(animationSpec = tween(1500, easing = LinearEasing), clip = false)
            ) {
                Box(modifier = Modifier.align(Alignment.BottomCenter)) {
                    MessageContent(message)
                }
            }
        }
    }

    @Composable
    private fun StatusBarSize(paddingValues: PaddingValues) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(id = R.color.purple_700))
                .padding(paddingValues)
        )
    }
}
