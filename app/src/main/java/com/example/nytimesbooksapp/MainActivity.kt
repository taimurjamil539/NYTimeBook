package com.example.nytimesbooksapp

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import com.example.nytimesbooksapp.presentation.navgraph.Navgraph
import dagger.hilt.android.AndroidEntryPoint
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.nytimesbooksapp.presentation.viewmodel.BookViewModel
import com.example.nytimesbooksapp.ui.theme.MyAppTheme



@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 1)
            }
        }


        setContent {
            val viewModel: BookViewModel= hiltViewModel()
            val uiState = viewModel.state.collectAsState()
            val isDarkMode = uiState.value.isDarkMode

            MyAppTheme(darkTheme = isDarkMode) {


                Navgraph()
            }
        }
    }
}

