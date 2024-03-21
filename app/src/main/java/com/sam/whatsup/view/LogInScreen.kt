package com.sam.whatsup.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun LogInScreen() {
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center){
        Column {

            Text(text = "Hii, This is LogIn Screen!")

        }
    }
}