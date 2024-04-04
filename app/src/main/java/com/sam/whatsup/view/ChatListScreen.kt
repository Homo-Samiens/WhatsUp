package com.sam.whatsup.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.sam.whatsup.WUViewModel

@Composable
fun ChatListScreen(navController: NavController, vm: WUViewModel) {

    Box(modifier = Modifier.fillMaxSize()) {

        Column(verticalArrangement = Arrangement.Bottom, horizontalAlignment = Alignment.End) {

            BottomNavigationMenu( selectedItem = BottomNavigationBar.CHATLIST, navController = navController)

        }

    }

}