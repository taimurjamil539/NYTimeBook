package com.example.nytimesbooksapp.presentation.navgraph


sealed class Navroot(val route:String) {
    object Splash: Navroot("splash")
    object Home: Navroot("home")
    object Detail: Navroot("detail")

}