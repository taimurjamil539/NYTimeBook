package com.example.nytimesbooksapp.presentation.homescreen

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.nytimesbooksapp.presentation.state.Bookstate
import com.example.nytimesbooksapp.presentation.viewmodel.Bookviewmodel
import com.example.nytimesbooksapp.ui.theme.MyAppTheme


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun Homescreen(
    navController: NavController,
    viewmodel: Bookviewmodel
) {
    val statevalue=viewmodel.state.value
    val isRefreshing = viewmodel.isrefreshing.value
    val isDark = viewmodel.isDarkMood.collectAsState(initial = false).value
    val lastSync = viewmodel.lastSyncDate.collectAsState(initial = "Never").value
    var searchQuery by remember { mutableStateOf("") }
    val landscape=IsLandscpe()
    val grideState = rememberLazyGridState()
    var showMenu by remember { mutableStateOf(false) }






    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = { viewmodel.refreshscreen() }
    )

    MyAppTheme(darkTheme = isDark) {


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
                        Box{
                        IconButton(onClick = {showMenu=!showMenu}) {
                            Icon(imageVector = Icons.Default.MoreVert, contentDescription = "more option")
                        }
                        DropdownMenu(expanded = showMenu, onDismissRequest = {showMenu=false}, modifier = Modifier.background(color  = MaterialTheme.colorScheme.surface)) {
                           DropdownMenuItem(onClick = {
                               showMenu=false
                               viewmodel.darktoggle(!isDark)
                           }, text = {
                               Row(
                                       verticalAlignment = Alignment.CenterVertically,
                               horizontalArrangement = Arrangement.SpaceBetween,
                               modifier = Modifier.fillMaxWidth()
                               ){

                                   Text(if (isDark) "Light Mode" else "Dark Mode",color = MaterialTheme.colorScheme.onSurface)
                                   Spacer(Modifier.width(12.dp))
                                   Icon(
                                       imageVector = if (isDark)
                                           Icons.Default.LightMode
                                       else
                                           Icons.Default.DarkMode,
                                       contentDescription = "Toggle Theme"
                                   )
                               }
                           })
                            DropdownMenuItem(onClick = {showMenu=false
                            viewmodel.refreshscreen()}, text = {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth()
                                ){
                                    Text("Refresh:",color = MaterialTheme.colorScheme.onSurface)
                                    Spacer(Modifier.width(12.dp))
                                    Icon(imageVector = Icons.Default.Refresh, contentDescription = "refresh") }
                            })
                            DropdownMenuItem(onClick = {},
                                text = {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier.fillMaxWidth()
                                    ){ Text("Last Sync: $lastSync",color = MaterialTheme.colorScheme.onSurface) }
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
                        .background(color = MaterialTheme.colorScheme.background)
                        .pullRefresh(pullRefreshState)
                        .padding(paddingValues)
                        .padding(horizontal = 12.dp)
                )  {





                    when (statevalue) {
                        is Bookstate.Loading -> {
                            Box(modifier = Modifier.fillMaxSize().background(color = MaterialTheme.colorScheme.surface), contentAlignment = Alignment.Center) {
                                LoadingAnimation()
                            }
                        }
                        is Bookstate.Error -> {
                                ErrorAnimation()
                        }

                        is Bookstate.Empty -> {
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                Text("Books not found")
                            }
                        }
                        is Bookstate.Success -> {
                            val books = statevalue.books
                            LazyVerticalGrid(
                                columns = if(landscape)GridCells.Fixed(4) else GridCells.Fixed(2),
                                state = grideState,
                                verticalArrangement = Arrangement.spacedBy(6.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.fillMaxSize()
                            ) {
                                item(span = { GridItemSpan(maxLineSpan) }){
                                    TextField(
                                        value = searchQuery,
                                        onValueChange = { query ->
                                            searchQuery = query
                                            viewmodel.searchbooks(query)
                                        },
                                        leadingIcon = {
                                            Icon(Icons.Default.Search, contentDescription = "Search")
                                        },
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
                                item(span = {GridItemSpan(maxLineSpan)}){
                                    DateFilterSection(viewModel = viewmodel)
                                }
                                items(books) { book ->
                                    BookCard(
                                        book = book,
                                        navController = navController,
                                        viewmodel = viewmodel)
                                }
                            }
                        }
                    }

                    PullRefreshIndicator(
                        refreshing = isRefreshing,
                        state = pullRefreshState,
                        modifier = Modifier.align(Alignment.TopCenter)
                    )
                    ScrollToTopBottom(grideState = grideState)


            }
        }
    }
}



