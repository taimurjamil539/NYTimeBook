package com.example.worldclockapp.presentation.navgraph
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.worldclockapp.presentation.WorldClockViewModel
import com.example.worldclockapp.presentation.ui.SplashScreen
import com.example.worldclockapp.presentation.ui.WorldClockScreen
@Composable
fun NavGraph() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = NavRoot.Splash.route
    ) {
        composable(NavRoot.Splash.route) {
            SplashScreen(
                onFinish = {
                    navController.navigate(NavRoot.Home.route) {
                        popUpTo(NavRoot.Splash.route) { inclusive = true }
                    }
                },
                isDark = false,
                navController = navController
            )
        }
        composable(NavRoot.Home.route) {
            val vm: WorldClockViewModel = hiltViewModel()
            WorldClockScreen(vm = vm)
        }
    }
}
