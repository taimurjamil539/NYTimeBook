package com.example.nytimesbooksapp.presentation.navgraph

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nytimesbooksapp.presentation.detailscreen.DetailScreen
import com.example.nytimesbooksapp.presentation.homescreen.Homescreen
import com.example.nytimesbooksapp.presentation.splashscreen.Splashscreen
import com.example.nytimesbooksapp.presentation.viewmodel.Bookviewmodel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navgraph(){

    val navController= rememberNavController()
    val viewModel: Bookviewmodel = hiltViewModel()

    NavHost(navController=navController, startDestination = Navroot.Splash.route){
        composable(Navroot.Splash.route){ Splashscreen(navController) }

        composable(Navroot.Home.route){ Homescreen(navController,viewModel) }

        composable(Navroot.Detail.route){ DetailScreen(navController,viewModel) }

    }
}