package com.example.nytimesbooksapp.presentation.navgraph


import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nytimesbooksapp.presentation.detailscreen.DetailScreen
import com.example.nytimesbooksapp.presentation.homescreen.Homescreen
import com.example.nytimesbooksapp.presentation.splashscreen.Splashscreen
import com.example.nytimesbooksapp.presentation.viewmodel.BookViewModel

@Composable
fun Navgraph(){
    val navController= rememberNavController()
    val viewModel: BookViewModel = hiltViewModel()
    NavHost(navController=navController, startDestination = NavRoot.Splash.route){
        composable(NavRoot.Splash.route){ Splashscreen(navController) }

        composable(NavRoot.Home.route){ Homescreen(navController,viewModel) }

        composable(NavRoot.Detail.route){ DetailScreen(navController,viewModel) }

    }
}