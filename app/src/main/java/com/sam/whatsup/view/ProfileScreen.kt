package com.sam.whatsup.view

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
<<<<<<< HEAD
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
=======
import androidx.compose.foundation.shape.CircleShape
>>>>>>> eb7365fa177cdbf41fdb606ad830e87d23d670ba
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sam.whatsup.WUViewModel
import com.sam.whatsup.util.CommonDivider
import com.sam.whatsup.util.CommonImage
import com.sam.whatsup.util.CommonProgressBar
<<<<<<< HEAD
import com.sam.whatsup.util.navigateTo

@Composable
fun ProfileScreen(navController: NavController, vm: WUViewModel) {

    val inProgress = vm.inProgress.value
    if (inProgress) {
        CommonProgressBar()
    } else {

        val userData = vm.userData.value
        var name by rememberSaveable {
            mutableStateOf(userData?.name ?: "")
        }
        var number by rememberSaveable {
            mutableStateOf(userData?.number ?: "")
        }

        Column(verticalArrangement = Arrangement.SpaceBetween) {

            ProfileContent(modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(8.dp),
                vm = vm,
                name = name,
                number = number,
                onNameChange = { name = it },
                onNumberChange = { number = it },
                onBack = {
                    navigateTo(
                        navController = navController, route = DestinationScreen.ChatList.route
                    )
                },
                onSave = {
                    vm.createOrUpdateProfile(
                        name = name, number = number
                    )
                },
                onLogOut = {
                    vm.logout()
                    navigateTo(
                        navController = navController, route = DestinationScreen.LogIn.route
                    )
                })

            BottomNavigationMenu(
                selectedItem = BottomNavigationBar.PROFILE, navController = navController
            )

        }


    }

}

@Composable
fun ProfileContent(
    modifier: Modifier,
    vm: WUViewModel,
    name: String,
    number: String,
    onNameChange: (String) -> Unit,
    onNumberChange: (String) -> Unit,
    onBack: () -> Unit,
    onSave: () -> Unit,
    onLogOut: () -> Unit
) {

    val imageUrl = vm.userData.value?.imageUrl


    Column(modifier = modifier.fillMaxSize()) {
=======

@Composable
fun ProfileScreen(navController: NavController, vm: WUViewModel) {

    val inProgress = vm.inProgress.value
    if (inProgress) {
        CommonProgressBar()
    } else {
        Column {
//            ProfileContent()
            BottomNavigationMenu(
                selectedItem = BottomNavigationBar.PROFILE,
                navController = navController
            )
        }
    }

}

@Composable
fun ProfileContent(vm: WUViewModel, onBack: () -> Unit, onSave: () -> Unit) {

    val imageUrl = vm.userData.value?.imageUrl

    Column {
>>>>>>> eb7365fa177cdbf41fdb606ad830e87d23d670ba

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

<<<<<<< HEAD
            Text(text = "Back", Modifier.clickable { onBack.invoke() })

            Text(text = "Save", Modifier.clickable { onSave.invoke() })
=======
            Text(text = "Back", Modifier.clickable {
                onBack.invoke()
            })

            Text(text = "Save", Modifier.clickable {
                onSave.invoke()
            })

            CommonDivider()
            DP(imageUrl = imageUrl, vm = vm)
>>>>>>> eb7365fa177cdbf41fdb606ad830e87d23d670ba

            CommonDivider()

<<<<<<< HEAD
        CommonDivider()

        DP(imageUrl = imageUrl, vm = vm)

        CommonDivider()

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(text = "Name", modifier = Modifier.width(100.dp))

            TextField(
                value = name, onValueChange = onNameChange, colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent
                )
            )
=======
            Row {

            }
>>>>>>> eb7365fa177cdbf41fdb606ad830e87d23d670ba

        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(text = "Number", modifier = Modifier.width(100.dp))

            TextField(
                value = number, onValueChange = onNumberChange, colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent
                )
            )

        }

        CommonDivider()

        Row(
            Modifier
                .fillMaxWidth()
                .padding(16.dp), horizontalArrangement = Arrangement.Center
        ) {

            Text(text = "LogOut", modifier = Modifier.clickable { onLogOut.invoke() })

        }

    }
}

@Composable
private fun DP(imageUrl: String?, vm: WUViewModel) {

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
<<<<<<< HEAD
    ) { uri ->
=======
    ) {

            uri ->
>>>>>>> eb7365fa177cdbf41fdb606ad830e87d23d670ba
        uri?.let {
            vm.uploadDP(uri)
        }

    }

    Box(modifier = Modifier.height(intrinsicSize = IntrinsicSize.Min)) {

        Column(
            modifier = Modifier
<<<<<<< HEAD
                .padding(10.dp)
=======
                .padding(8.dp)
>>>>>>> eb7365fa177cdbf41fdb606ad830e87d23d670ba
                .fillMaxWidth()
                .clickable {
                    launcher.launch("image/*")
                }, horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Card(
                shape = CircleShape, modifier = Modifier
                    .padding(8.dp)
                    .size(100.dp)
            ) {

                CommonImage(data = imageUrl)

            }

<<<<<<< HEAD
            Text(text = "DP")
=======
            Text(text = "Change DP")
>>>>>>> eb7365fa177cdbf41fdb606ad830e87d23d670ba

        }

        if (vm.inProgress.value) {
            CommonProgressBar()
        }

    }

}