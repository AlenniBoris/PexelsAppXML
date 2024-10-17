package com.example.pexelsproject.screens.liked.views

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import kotlinx.coroutines.launch

@Composable
fun PhotoCards(
    url: String,
    author: String?,
    onSwipeLeft: () -> Unit,
    onSwipeRight: () -> Unit
){

    val offsetX = remember { Animatable(0f) }
    val swipeThreshold = 150f  // Пороговое значение для свайпа

    val coroutineScope = rememberCoroutineScope()

    val modifier = Modifier
        .padding(8.dp)
        .clip(RoundedCornerShape(24.dp))
        .pointerInput(Unit) {
            detectHorizontalDragGestures(
                onDragEnd = {
                    coroutineScope.launch {
                        if (offsetX.value > swipeThreshold) {
                            onSwipeRight()
                        } else if (offsetX.value < -swipeThreshold) {
                            onSwipeLeft()
                        }
                        offsetX.animateTo(0f)
                    }
                }
            ) { change, dragAmount ->
                change.consume()
                coroutineScope.launch {
                    offsetX.snapTo(offsetX.value + dragAmount)
                }
            }
        }

    Box(
        modifier = modifier
            .offset { IntOffset(offsetX.value.toInt(), 0) }
            .background(Color.White)
            .padding(8.dp)
            .clip(RoundedCornerShape(24.dp)),
        contentAlignment = Alignment.BottomCenter
    ){
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = url,
            contentDescription = "image",
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp)
                .background(Color.Black.copy(0.4f)),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = author.toString(),
                color = Color.White,
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}
