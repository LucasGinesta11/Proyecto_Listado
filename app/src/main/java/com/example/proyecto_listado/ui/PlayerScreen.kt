package com.example.proyecto_listado.ui

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.proyecto_listado.model.MediaData
import kotlinx.coroutines.delay

@OptIn(ExperimentalAnimationApi::class)
@SuppressLint("DiscouragedApi")
@Composable
fun PlayerScreen() {
    val context = LocalContext.current
    val mediaItems = MediaData.items

    var currentIndex by remember { mutableIntStateOf(0) }

    // Cambia al siguiente elemento después de la duración especificada
    LaunchedEffect(currentIndex) {
        delay(mediaItems[currentIndex].duration.toLong())
        currentIndex = (currentIndex + 1) % mediaItems.size
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AnimatedContent(
            targetState = currentIndex,
            transitionSpec = {
                fadeIn(animationSpec = tween(durationMillis = 2000)).togetherWith(
                    fadeOut(animationSpec = tween(durationMillis = 2000))
                )
            }
        ) { targetIndex ->
            mediaItems[targetIndex].Render(context)
        }

        FloatingActionButton(
            onClick = { (context as Activity).finish() },
            contentColor = Color.White,
            containerColor = Color.Blue,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp)
        ) {
            Icon(Icons.Filled.Close, "Cerrar aplicación")
        }
    }
}
