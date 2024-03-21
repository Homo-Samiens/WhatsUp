package com.sam.whatsup.view

sealed class DestinationScreen(var route: String) {
    object SignUp: DestinationScreen("signUp")
    object LogIn: DestinationScreen("logIn")
    object Profile: DestinationScreen("profile")

    object ChatList: DestinationScreen("chatList")
    object SingleChat: DestinationScreen("singleChat/{chatId}"){
        fun createRoute(chatId: String) = "singleChat/$chatId" //Fun to get Real-Chat-Id & use in the template.
    }

    object StatusList: DestinationScreen("statusList")
    object SingleStatus: DestinationScreen("singleStatus/{statusId}"){
        fun createRoute(statusId: String) = "singleStatus/$statusId"
    }
}