package com.sam.whatsup

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sam.whatsup.view.ChatListScreen
import com.sam.whatsup.view.DestinationScreen
import com.sam.whatsup.view.LogInScreen
import com.sam.whatsup.view.ProfileScreen
import com.sam.whatsup.view.SignUpScreen
import com.sam.whatsup.view.SingleChatScreen
import com.sam.whatsup.view.StatusListScreen

@Composable
fun ScreenNavigation() {

    val navController = rememberNavController()
    val vm = hiltViewModel<WUViewModel>()

    NavHost(navController = navController, startDestination = DestinationScreen.SignUp.route) {

        composable(DestinationScreen.SignUp.route) {
            SignUpScreen(navController, vm)
        }

        composable(DestinationScreen.LogIn.route) {
            LogInScreen(navController, vm)
        }

        composable(DestinationScreen.ChatList.route) {
            ChatListScreen(navController, vm)
        }

        composable(DestinationScreen.SingleChat.route) {
            val chatId = it.arguments?.getString("chatId")
            chatId?.let {
                SingleChatScreen(navController, vm, chatId)
            }
        }

        composable(DestinationScreen.StatusList.route) {
            StatusListScreen(navController, vm)
        }

        composable(DestinationScreen.Profile.route) {
            ProfileScreen(navController, vm)
        }

    }

}