package com.example.worldclockapp.presentation.navgraph

sealed class NavRoot(val route:String) {
    object Splash: NavRoot("splash")
    object Home: NavRoot("home")

}