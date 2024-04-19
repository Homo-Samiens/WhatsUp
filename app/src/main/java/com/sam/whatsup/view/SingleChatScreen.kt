package com.sam.whatsup.view

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.sam.whatsup.WUViewModel

@Composable
fun SingleChatScreen(navController: NavController, vm: WUViewModel, chatId: String) {

    Text(text = chatId)

}