package lt.vaikri.playground.navigator

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun NavTestScreen(viewModel: NavTestViewModel, finish: () -> Unit) {
    val state by viewModel.state.collectAsState()
    NavTestContent(
        state,
        finish = finish,
        viewModel::onNext,
        viewModel::onFooterUp,
        viewModel::onFooterDown,
        viewModel::onEventConsumed
    )
}

@Composable
fun NavTestContent(
    state: NavTestState,
    finish: () -> Unit,
    onNext: (NavTestItem, Boolean) -> Unit,
    onFooterUp: () -> Unit,
    onFooterDown: () -> Unit,
    onEventConsumed: (Event) -> Unit
) {
    val innerNavController = rememberNavController()
    innerNavController.enableOnBackPressed(false)
    var transitionDown by remember { mutableStateOf(true) }
    val event = state.event
    LaunchedEffect(key1 = event) {
        if (event != null) {
            when (event) {
                Close -> finish()
                is Transition -> {
                    transitionDown = event.isDown
                    val route = "page?id=${event.id}"
                    innerNavController.navigate(route) {
                        popUpTo(innerNavController.graph.id) {
                            inclusive = true
                        }
                    }
                }
            }
            onEventConsumed(event)
        }
    }

    Column {
        NavHost(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            navController = innerNavController,
            startDestination = "page"
        ) {
            composable(
                route = "page?id={id}",
                arguments = listOf(navArgument("id") { defaultValue = 0 }),
                enterTransition = {
                    if (transitionDown) {
                        slideIntoContainer(
                            towards = AnimatedContentTransitionScope.SlideDirection.Up,
                        )
                    } else {
                        slideIntoContainer(
                            towards = AnimatedContentTransitionScope.SlideDirection.Down,
                        )
                    }
                },
                exitTransition = {
                    if (transitionDown) {
                        slideOutOfContainer(
                            towards = AnimatedContentTransitionScope.SlideDirection.Up,
                        )
                    } else {
                        slideOutOfContainer(
                            towards = AnimatedContentTransitionScope.SlideDirection.Down,
                        )
                    }
                }
            ) { entry ->
                val id = remember { entry.arguments?.getInt("id") ?: 0 }
                val item = state.items.get(id) ?: state.items.valueAt(0)
                ItemPage(item) { addFooter -> onNext(item, addFooter) }
            }
        }
        if (state.footer != null) {
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                val modifier = Modifier.weight(1f)
                Button(
                    modifier = modifier,
                    enabled = state.footer.canUp,
                    onClick = { onFooterUp() }) {
                    Text(text = "Up")
                }
                Spacer(modifier = Modifier.width(16.dp))
                Button(
                    modifier = modifier,
                    enabled = state.footer.canDown,
                    onClick = { onFooterDown() }) {
                    Text(text = "Down")
                }
            }
        }
    }
}

@Composable
fun ItemPage(item: NavTestItem, onNext: (Boolean) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = item.text, style = MaterialTheme.typography.h5)
        Button(onClick = { onNext(false) }) {
            Text(text = "next")
        }
        Button(onClick = { onNext(true) }) {
            Text(text = "next with footer")
        }
    }
}
