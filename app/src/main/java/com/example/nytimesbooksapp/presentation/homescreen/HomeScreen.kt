package com.example.nytimesbooksapp.presentation.homescreen

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.nytimesbooksapp.presentation.intent.BookIntent
import com.example.nytimesbooksapp.presentation.viewmodel.BookViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun Homescreen(
    navController: NavController,
    viewmodel: BookViewModel
) {
    val state = viewmodel.state.collectAsState().value
    val gridState = rememberLazyGridState()
    val landscape = IsLandscpe()
    val context = LocalContext.current
    var showMenu by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }

    LaunchedEffect(viewmodel) {
        viewmodel.intentChannel.send(BookIntent.LoadBooks)
    }
    LaunchedEffect(searchQuery) {
        if (searchQuery.isNotBlank()) {
            delay(700)
            viewmodel.intentChannel.send(BookIntent.SearchBooks(searchQuery))
        }
    }
    val pullRefreshState = rememberPullRefreshState(
        refreshing = state.isRefreshing,
        onRefresh = { viewmodel.intentChannel.trySend(BookIntent.Refresh) }
    )
    Scaffold(
        topBar = {
            Surface(
                modifier = Modifier.statusBarsPadding(),
                tonalElevation = 4.dp,
                shadowElevation = 4.dp,
                color = MaterialTheme.colorScheme.primary
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "NY Time Books",
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                    Box {
                        IconButton(onClick = { showMenu = !showMenu }) {
                            Icon(Icons.Default.MoreVert, contentDescription = "More options")
                        }
                        DropdownMenu(
                            expanded = showMenu,
                            onDismissRequest = { showMenu = false }
                        ) {
                            DropdownMenuItem(
                                onClick = {
                                    showMenu = false
                                    viewmodel.intentChannel.trySend(
                                        BookIntent.ToggleTheme(!state.isDarkMode)
                                    )
                                },
                                text = {
                                    Row(
                                        Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(if (state.isDarkMode) "Light Mode" else "Dark Mode")
                                        Icon(
                                            if (state.isDarkMode)
                                                Icons.Default.LightMode
                                            else Icons.Default.DarkMode,
                                            contentDescription = null
                                        )
                                    }
                                }
                            )
                            DropdownMenuItem(
                                onClick = {
                                    showMenu = false
                                    viewmodel.intentChannel.trySend(BookIntent.Refresh)
                                },
                                text = {
                                    Row(
                                        Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text("Refresh")
                                        Icon(Icons.Default.Refresh, contentDescription = null)
                                    }
                                }
                            )
                            DropdownMenuItem(
                                onClick = {},
                                text = {
                                    Text("Last Sync: ${state.lastSync.ifBlank { "Never" }}")
                                }
                            )
                            DropdownMenuItem(
                                onClick = {
                                    showMenu = false
                                    val intent = Intent(
                                        context,
                                        com.example.worldclockapp.MainActivity::class.java
                                    )
                                    context.startActivity(intent)
                                },
                                text = {
                                    Row(
                                        Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text("World Clock App")
                                        Icon(Icons.Default.MoveUp, contentDescription = null)
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .pullRefresh(pullRefreshState)
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
                .padding(horizontal = 12.dp)
        ) {
            LazyVerticalGrid(
                columns = if (landscape) GridCells.Fixed(4) else GridCells.Fixed(2),
                state = gridState,
                verticalArrangement = Arrangement.spacedBy(6.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    TextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                        placeholder = { Text("Search your book") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 4.dp, vertical = 4.dp)
                            .clip(RoundedCornerShape(16.dp)),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                            focusedBorderColor = MaterialTheme.colorScheme.primary
                        )
                    )
                }
                item(span = { GridItemSpan(maxLineSpan) }) {
                    DateFilterSection(viewmodel)
                }
                when {
                    state.isLoading -> {
                        item(span = { GridItemSpan(maxLineSpan) }) {
                            Box(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(40.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                LoadingAnimation()
                            }
                        }
                    }
                    state.error != null -> {
                        item(span = { GridItemSpan(maxLineSpan) }) {
                            Box(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(40.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                ErrorAnimation()
                            }
                        }
                    }
                    state.isRooted -> {
                        item(span = { GridItemSpan(maxLineSpan) }) {
                            Box(
                                Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("Rooted Device Detected! App cannot run.")
                            }
                        }
                    }
                    state.isEmulator -> {
                        item(span = { GridItemSpan(maxLineSpan) }) {
                            Box(
                                Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("Emulator Detected! App allowed only on real device.")
                            }
                        }
                    }
                    state.books.isEmpty() -> {
                        item(span = { GridItemSpan(maxLineSpan) }) {
                            Box(
                                Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("No books found")
                            }
                        }
                    }
                    else -> {
                        items(state.books) { book ->
                            BookCard(book, navController, viewmodel)
                        }
                    }
                }
            }
            PullRefreshIndicator(
                refreshing = state.isRefreshing,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )
            ScrollToTopBottom(gridState)
        }
    }
}
