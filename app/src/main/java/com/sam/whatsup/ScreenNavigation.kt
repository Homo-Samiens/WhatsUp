package com.sam.whatsup

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sam.whatsup.view.DestinationScreen
import com.sam.whatsup.view.LogInScreen
import com.sam.whatsup.view.SignUpScreen

@Composable
fun ScreenNavigation() {

    val navController = rememberNavController()
    var vm = hiltViewModel<WUViewModel>()
    NavHost(navController = navController, startDestination = DestinationScreen.SignUp.route){

        composable(DestinationScreen.SignUp.route){
            SignUpScreen(navController, vm)
        }

        composable(DestinationScreen.LogIn.route){
            LogInScreen()
        }

    }
}