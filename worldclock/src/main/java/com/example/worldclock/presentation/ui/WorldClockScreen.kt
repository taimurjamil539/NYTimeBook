package com.example.worldclockapp.presentation.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.dp
import com.example.worldclockapp.presentation.mvi.WorldClockIntent
import com.example.worldclockapp.presentation.WorldClockViewModel
import androidx.compose.runtime.snapshotFlow
import kotlinx.coroutines.flow.distinctUntilChanged


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorldClockScreen(vm: WorldClockViewModel) {
    val state by vm.state.collectAsState()
    val instant by vm.utcNow.collectAsState()
    val colorScheme = MaterialTheme.colorScheme
    val isDark = state.isDarkMode
    val listState = rememberLazyListState()
    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex > 0 || listState.firstVisibleItemScrollOffset > 50 }
            .distinctUntilChanged()
            .collect { isScrolled ->
            }
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        topBar = {
            FancyTopBar(
                isDark = state.isDarkMode,
                onToggle = { vm.handleIntent(WorldClockIntent.ToggleTheme(!state.isDarkMode)) },
            )
        },
        containerColor = Color.Transparent
    ) { padding ->

        DesignBackground(state.isDarkMode)

        Box(Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 16.dp)
            ) {
                SearchBar(vm, state, colorScheme, isDark)

                AnimatedVisibility(visible = state.isLoading) {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = colorScheme.primary)
                    }
                }

                AnimatedVisibility(visible = state.error != null) {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            "Error: ${state.error}",
                            color = colorScheme.error,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }

                if (!state.isLoading && state.error == null) {
                    val shimmerPhase by rememberInfiniteTransition(label = "shimmer").animateFloat(
                        initialValue = 0f,
                        targetValue = 1f,
                        animationSpec = infiniteRepeatable(
                            animation = tween(1800, easing = LinearEasing)
                        ),
                        label = "phase"
                    )

                    LazyColumn(
                        state = listState,
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(vertical = 8.dp)
                    ) {
                        items(state.filteredZones, key = { it.id }) { zone ->
                            ModernClockCard(
                                vm = vm,
                                name = zone.displayName,
                                id = zone.id,
                                instant = instant,
                                use24h = state.use24h,
                                isDarkMode = state.isDarkMode,
                                shimmerPhase = shimmerPhase
                            )
                        }
                    }
                }
            }
            ScrollToTopButton(listState = listState)
        }
    }
}



