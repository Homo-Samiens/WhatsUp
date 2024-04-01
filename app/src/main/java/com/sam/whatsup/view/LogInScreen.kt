package com.sam.whatsup.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sam.whatsup.R
import com.sam.whatsup.WUViewModel
import com.sam.whatsup.util.ProgressBar
import com.sam.whatsup.util.UserSignedIn

@Composable
fun LogInScreen(navController: NavController, vm: WUViewModel) {

    UserSignedIn(vm = vm, navController = navController)

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        var emailState by remember {
            mutableStateOf(TextFieldValue())
        }

        var passwordState by remember {
            mutableStateOf(TextFieldValue())
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Image(
                painter = painterResource(id = R.drawable.chat),
                contentDescription = "Logo",
                modifier = Modifier
                    .width(105.dp)
                    .height(105.dp)
                    .padding(8.dp)
            )

            Text(
                text = "LogIn to WhatsUp",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(8.dp)
            )

            OutlinedTextField(
                value = emailState,
                onValueChange = { emailState = it },
                label = { Text(text = "E-Mail") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.padding(8.dp)
            )

            OutlinedTextField(
                value = passwordState,
                onValueChange = { passwordState = it },
                label = { Text(text = "Password") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.padding(8.dp)
            )

            Button(
                onClick = {
                          vm.LogIn(emailState.text, passwordState.text)
                },
                modifier = Modifier.padding(10.dp)
            ) {
                Text(text = "Log In")
            }

            Text(
                text = "New User?, Click here to Sign-Up",
                modifier = Modifier
                    .clickable { navController.navigate(DestinationScreen.SignUp.route) }
                    .padding(8.dp)
            )

        }

        if (vm.inProgress.value) {
            ProgressBar()
        }

    }

}