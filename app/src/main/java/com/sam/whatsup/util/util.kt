package com.sam.whatsup.util

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.sam.whatsup.WUViewModel
import com.sam.whatsup.view.DestinationScreen

fun navigateTo(navController: NavController, route: String) {
    navController.navigate(route) {
        popUpTo(route)
        launchSingleTop = true
    }
}

@Composable
fun ProgressBar() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .alpha(0.5f)
            .background(Color.LightGray),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        CircularProgressIndicator()

    }

}

@Composable
fun UserSignedIn(vm: WUViewModel, navController: NavController) {

    val alreadySignIn = remember { mutableStateOf(false) }

    val signIn = vm.signIn.value

    if(signIn && !alreadySignIn.value){
        navController.navigate(DestinationScreen.ChatList.route){
            popUpTo(0)
        }
    }

}