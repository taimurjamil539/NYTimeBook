package com.example.worldclockapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.worldclockapp.presentation.WorldClockViewModel
import com.example.worldclockapp.presentation.navgraph.NavGraph
import com.example.worldclockapp.ui.theme.MyAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val vm: WorldClockViewModel = hiltViewModel()
            val state by vm.state.collectAsState()
            val isDarkMode = state.isDarkMode
            MyAppTheme(darkTheme = isDarkMode) {
                NavGraph()
            }
        }
    }
}
