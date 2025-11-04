package com.example.nytimesbooksapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.runtime.collectAsState
import com.example.nytimesbooksapp.presentation.navgraph.Navgraph
import com.example.nytimesbooksapp.ui.theme.NYTimesBooksAppTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.nytimesbooksapp.presentation.viewmodel.Bookviewmodel
import com.example.nytimesbooksapp.ui.theme.MyAppTheme


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(
                lightScrim = Color.Transparent.toArgb(),
                darkScrim = Color.Transparent.toArgb()
            )
        )

        setContent {
            val viewModel: Bookviewmodel= hiltViewModel()
            val isDark = viewModel.isDarkMood.collectAsState(initial = false).value

            MyAppTheme(darkTheme = isDark) {


                Navgraph()




            }
        }
    }
}

