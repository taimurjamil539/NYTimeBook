package com.example.nytimesbooksapp.presentation.splashscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.NYtimeprojectBooks.R
import kotlinx.coroutines.delay


@Composable
fun Splashscreen(navController: NavController){
    LaunchedEffect(keys = arrayOf(true)) {
        delay(2000)
        navController.navigate("home"){
            popUpTo("splash"){inclusive=true}
        }

    }


    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Image(painter = painterResource(id = R.drawable.logo), contentDescription = "logo", modifier = Modifier.width(100.dp).height(100.dp))
    }
}