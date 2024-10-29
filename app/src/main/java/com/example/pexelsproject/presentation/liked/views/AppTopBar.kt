package com.example.pexelsproject.presentation.liked.views

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pexelsproject.R

@Composable
fun AppTopBar(navigationAction : () -> Unit){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 17.dp)
            .padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .size(40.dp)
                .background(MaterialTheme.colorScheme.surface)
        ) {
            IconButton(
                onClick = {
                    navigationAction()
                },
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.back_btn_description),
                    tint = if(isSystemInDarkTheme()){ Color.White } else Color.Black
                )
            }
        }


        Text(
            text = stringResource(R.string.liked_title),
            fontSize = 18.sp,
            color = if(isSystemInDarkTheme()){ Color.White } else Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()

        )

    }
}