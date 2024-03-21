package com.sam.whatsup.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sam.whatsup.WUViewModel

@Composable
fun SignUpScreen(navController: NavController, vm: WUViewModel) {

    Box(modifier = Modifier.fillMaxSize()){

        Column(modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            Text(text = "Welcome to WhatsUp", fontWeight = FontWeight.Bold)

        }

        Row(modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 50.dp),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Center,) {
            Text(text = "Already a User?")
            Text(text = "LogIn Here", Modifier.clickable {
                navController.navigate(DestinationScreen.LogIn.route)
            })
        }

    }

}