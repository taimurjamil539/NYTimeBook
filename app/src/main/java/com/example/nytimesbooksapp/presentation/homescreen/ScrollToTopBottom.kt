package com.example.nytimesbooksapp.presentation.homescreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.LazyGridState

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun ScrollToTopBottom(grideState: LazyGridState){
    val coroutineScope = rememberCoroutineScope()

    val showbutton by remember { derivedStateOf{grideState.firstVisibleItemIndex>0} }
    Box (modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd){
        AnimatedVisibility(
            visible = showbutton,
            enter = fadeIn(), exit =
                fadeOut(), modifier = Modifier.padding(16.dp)) {
            FloatingActionButton(onClick = { coroutineScope.launch{grideState.animateScrollToItem(0)} }) {
                Icon(imageVector = Icons.Default.ArrowUpward, contentDescription = "Scroll Arrow")
            }
        }
    }


}