package com.example.pexelsproject.presentation.liked.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pexelsproject.R


@Composable
fun EmptyScreen(
    onExploreClicked: () -> Unit,
    text: String,
    btnText: String = "Explore",
    hasInternet: Boolean = true
){
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!hasInternet){
            Image(
                painter = painterResource(id =
                R.drawable.ic_no_network
                ),
                contentDescription = "Wifi"
            )
        }else{
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = text,
                textAlign = TextAlign.Center,
                fontSize = 14.sp
            )
        }

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp)
                .clickable {
                    onExploreClicked()
                },
            text = btnText,
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
            color = Color.Red
        )
    }
}