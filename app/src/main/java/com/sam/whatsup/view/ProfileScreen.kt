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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sam.whatsup.WUViewModel
import com.sam.whatsup.util.CommonDivider
import com.sam.whatsup.util.CommonImage
import com.sam.whatsup.util.CommonProgressBar

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

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(text = "Back", Modifier.clickable {
                onBack.invoke()
            })

            Text(text = "Save", Modifier.clickable {
                onSave.invoke()
            })

            CommonDivider()
            DP(imageUrl = imageUrl, vm = vm)

            CommonDivider()

            Row {

            }

        }

    }
}

@Composable
fun DP(imageUrl: String?, vm: WUViewModel) {

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) {

            uri ->
        uri?.let {
            vm.uploadDP(uri)
        }

    }

    Box(modifier = Modifier.height(intrinsicSize = IntrinsicSize.Min)) {

        Column(
            modifier = Modifier
                .padding(8.dp)
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

            Text(text = "Change DP")

        }

        if (vm.inProgress.value) {
            CommonProgressBar()
        }

    }

}