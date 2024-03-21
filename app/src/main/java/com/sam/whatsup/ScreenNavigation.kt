package com.sam.whatsup

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sam.whatsup.view.DestinationScreen
import com.sam.whatsup.view.LogInScreen
import com.sam.whatsup.view.SignUpScreen

@Composable
fun ScreenNavigation() {

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = DestinationScreen.SignUp.route){

        composable(DestinationScreen.SignUp.route){
            SignUpScreen(navController)
        }

        composable(DestinationScreen.LogIn.route){
            LogInScreen()
        }

    }
}