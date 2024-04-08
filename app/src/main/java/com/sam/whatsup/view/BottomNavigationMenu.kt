package com.sam.whatsup.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sam.whatsup.R
import com.sam.whatsup.util.navigateTo

enum class BottomNavigationBar(val icon: Int, val navDestination: DestinationScreen) {

    CHATLIST(R.drawable.chatlist, DestinationScreen.ChatList),
    STATUSLIST(R.drawable.statuslist, DestinationScreen.StatusList),
    PROFILE(R.drawable.profile, DestinationScreen.Profile)

}

@Composable
fun BottomNavigationMenu(selectedItem: BottomNavigationBar, navController: NavController) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(Color.White)
    ) {

        for (item in BottomNavigationBar.entries) {

            Image(
                painter = painterResource(id = item.icon), contentDescription = null,
                modifier = Modifier
                    .size(45.dp)
                    .padding(4.dp)
                    .weight(1f)
                    .clickable {
                        navigateTo(navController, item.navDestination.route)
                    },
                colorFilter = if (item == selectedItem)
                    ColorFilter.tint(color = Color.Black)
                else
                    ColorFilter.tint(color = Color.Gray)
            )

        }

    }

}