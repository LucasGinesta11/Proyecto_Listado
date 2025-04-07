package com.example.proyecto_listado.ui

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.proyecto_listado.model.MediaData
import kotlinx.coroutines.delay

@OptIn(ExperimentalAnimationApi::class)
@SuppressLint("DiscouragedApi")
@Composable
fun PlayerScreen(option: String, navController: NavController) {
    val context = LocalContext.current
    val mediaItems = MediaData.items

    var currentIndex by remember { mutableIntStateOf(0) }

    // Cambia al siguiente elemento después de la duración especificada
    LaunchedEffect(currentIndex) {
        delay(mediaItems[currentIndex].duration.toLong())
        currentIndex = (currentIndex + 1) % mediaItems.size
    }

    Box(modifier = Modifier.fillMaxSize()) {

        when (option) {
            "Fade" -> {
                AnimatedContent(
                    targetState = currentIndex,
                    transitionSpec = {
                        fadeIn(animationSpec = tween(durationMillis = 3000)).togetherWith(
                            fadeOut(animationSpec = tween(durationMillis = 3000))
                        )
                    }
                ) { targetIndex ->
                    mediaItems[targetIndex].Render(context, option)
                }
            }

            "Expand and Shrink" -> {
                AnimatedContent(
                    targetState = currentIndex,
                    transitionSpec = {
                        expandIn(animationSpec = tween(durationMillis = 3000)).togetherWith(
                            shrinkOut(animationSpec = tween(durationMillis = 3000))
                        )
                    }
                ) { targetIndex ->
                    mediaItems[targetIndex].Render(context, option)
                }
            }

            "Slide (Horizontally)" -> {
                AnimatedContent(
                    targetState = currentIndex,
                    transitionSpec = {

                        slideInHorizontally(animationSpec = tween(durationMillis = 3000)).togetherWith(
                            slideOutHorizontally(animationSpec = tween(durationMillis = 3000))
                        )
                    }
                ) { targetIndex ->
                    mediaItems[targetIndex].Render(context, option)
                }
            }

            "Slide (Vertically)" -> {
                AnimatedContent(
                    targetState = currentIndex,
                    transitionSpec = {

                        slideInVertically(animationSpec = tween(durationMillis = 3000)).togetherWith(
                            slideOutVertically(animationSpec = tween(durationMillis = 3000))
                        )
                    }
                ) { targetIndex ->
                    mediaItems[targetIndex].Render(context, option)
                }
            }

            "Scale" -> {
                AnimatedContent(
                    targetState = currentIndex,
                    transitionSpec = {

                        scaleIn(animationSpec = tween(durationMillis = 3000)).togetherWith(
                            scaleOut(animationSpec = tween(durationMillis = 3000))
                        )
                    }
                ) { targetIndex ->
                    mediaItems[targetIndex].Render(context, option)
                }
            }
        }

        FloatingActionButton(
            onClick = { navController.navigate("HomeScreen") },
            contentColor = Color.White,
            containerColor = Color.Blue,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp)
        ) {
            Icon(Icons.Filled.Home, "Volver a Home")
        }
    }
}