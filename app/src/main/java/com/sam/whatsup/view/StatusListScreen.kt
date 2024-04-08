package com.sam.whatsup.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.sam.whatsup.WUViewModel

@Composable
fun StatusListScreen(navController: NavController, vm: WUViewModel) {

    Box(modifier = Modifier.fillMaxSize()) {

        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {

            Text(text = "StatusList Screen!")

        }


        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom, horizontalAlignment = Alignment.End) {


            BottomNavigationMenu( selectedItem = BottomNavigationBar.CHATLIST, navController = navController)

        }

    }

}