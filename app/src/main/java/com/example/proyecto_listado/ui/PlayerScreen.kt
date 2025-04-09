package com.example.proyecto_listado.ui

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandIn
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.proyecto_listado.model.MediaData
import com.example.proyecto_listado.model.TransitionType
import kotlinx.coroutines.delay

@OptIn(ExperimentalAnimationApi::class)
@SuppressLint("DiscouragedApi")
@Composable
fun PlayerScreen(option: TransitionType, navController: NavController) {
    val context = LocalContext.current
    val mediaItems = MediaData.items

    var currentIndex by remember { mutableIntStateOf(0) }

    // Cambia al siguiente elemento después de la duración especificada
    LaunchedEffect(currentIndex) {
        delay(mediaItems[currentIndex].duration.toLong())
        currentIndex = (currentIndex + 1) % mediaItems.size
    }

    Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        AnimatedContent(
            targetState = currentIndex,
            transitionSpec = {
                when (option) {
                    TransitionType.FADE -> {
                        fadeIn(animationSpec = tween(durationMillis = 5000)).togetherWith(
                            fadeOut(animationSpec = tween(durationMillis = 5000))
                        )
                    }

                    TransitionType.EXPAND_AND_SHRINK -> {
                        expandIn(animationSpec = tween(durationMillis = 5000)).togetherWith(
                            shrinkOut(animationSpec = tween(durationMillis = 5000))
                        )
                    }

                    TransitionType.SLIDE_HORIZONTALLY -> {
                        slideInHorizontally(
                            initialOffsetX = { -4000 },
                            animationSpec = tween(durationMillis = 5000)
                        ).togetherWith(
                            slideOutHorizontally(
                                targetOffsetX = { 4000 },
                                animationSpec = tween(durationMillis = 5000)
                            )
                        )
                    }

                    TransitionType.SLIDE_VERTICALLY -> {
                        slideInVertically(
                            initialOffsetY = { 4000 },
                            animationSpec = tween(durationMillis = 5000)
                        ).togetherWith(
                            slideOutVertically(
                                targetOffsetY = { -4000 },
                                animationSpec = tween(durationMillis = 5000)
                            )
                        )
                    }

                    TransitionType.SCALE -> {
                        scaleIn(animationSpec = tween(durationMillis = 5000)).togetherWith(
                            scaleOut(animationSpec = tween(durationMillis = 5000))
                        )
                    }

                    TransitionType.EXPAND_AND_SHRINK_VERTICALLY -> {
                        expandVertically(animationSpec = tween(durationMillis = 5000)).togetherWith(
                            shrinkVertically(animationSpec = tween(durationMillis = 5000))
                        )
                    }

                    TransitionType.EXPAND_AND_SHRINK_HORIZONTALLY -> {
                        expandHorizontally(animationSpec = tween(durationMillis = 5000)).togetherWith(
                            shrinkHorizontally(animationSpec = tween(durationMillis = 5000))
                        )
                    }
                }
            }
        ) { targetIndex ->
            mediaItems[targetIndex].Render(context, option)
        }

        FloatingActionButton(
            onClick = { navController.navigate("HomeScreen") },
            contentColor = Color.White,
            containerColor = Color.Blue,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp)
        ) {
            Icon(Icons.Filled.Home, contentDescription = "Volver a Home")
        }
    }
}
