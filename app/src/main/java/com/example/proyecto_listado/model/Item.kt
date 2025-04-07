package com.example.proyecto_listado.model

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.proyecto_listado.exoPlayer.VideoPlayer
import com.example.proyecto_listado.webView.WebViewComponent
import kotlinx.coroutines.delay

data class Item(val file: String, val type: MediaType, val duration: Int) {
    @SuppressLint("DiscouragedApi")
    @Composable
    fun Render(context: Context, transitionType: String) {
        when (type) {
            MediaType.IMAGE -> {
                val resourceId = context.resources.getIdentifier(file, "raw", context.packageName)
                Image(
                    painter = painterResource(id = resourceId),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
            }

            MediaType.VIDEO -> {
                // No aplico cambios en estas transiciones porque ya funcionan
                if (transitionType == "Expand and Shrink" || transitionType == "Scale") {
                    VideoPlayer(context, file)
                } else {
                    // Para fade y slides, usar la lógica de la pantalla negra
                    var isVideoReady by remember { mutableStateOf(false) }
                    var showBlackScreen by remember { mutableStateOf(true) }


                    Box(modifier = Modifier.fillMaxSize()) {
                        // Pantalla negra inicial
                        AnimatedVisibility(
                            visible = showBlackScreen,
                            exit = fadeOut(animationSpec = tween(durationMillis = 3000))
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.Black)
                            )
                        }

                        // Video con animación
                        AnimatedVisibility(
                            visible = isVideoReady,
                            enter = when (transitionType) {
                                "Fade" -> fadeIn(animationSpec = tween(durationMillis = 3000))
                                "Slide (Horizontally)" -> slideInHorizontally(initialOffsetX = { -1000 }, animationSpec = tween(durationMillis = 3000))
                                "Slide (Vertically)" -> slideInVertically(initialOffsetY = { -1000 }, animationSpec = tween(durationMillis = 3000))
                                else -> fadeIn(animationSpec = tween(durationMillis = 3000))
                            },
                            exit = fadeOut(animationSpec = tween(durationMillis = 3000))
                        ) {
                            VideoPlayer(context, file)
                        }

                        // Lógica para preparar el video
                        LaunchedEffect(Unit) {
                            delay(1000)
                            showBlackScreen = false
                            isVideoReady = true
                        }
                    }

                }
            }

            MediaType.URL -> {
                WebViewComponent(file)
            }
        }
    }
}

enum class MediaType {
    IMAGE,
    VIDEO,
    URL
}

object MediaData {
    val items = listOf(
        Item("gato", type = MediaType.IMAGE, 4000),
        Item("arana", type = MediaType.IMAGE, 4000),
        Item("mar", type = MediaType.VIDEO, 7000),
        Item("https://github.com/", type = MediaType.URL, 10000),
        Item("lago", type = MediaType.IMAGE, 4000),
        Item("paisaje", type = MediaType.VIDEO, 7000),
        Item("https://orbys.eu/", type = MediaType.URL, 10000),
        Item("arana", type = MediaType.IMAGE, 4000),
        Item("fuego", type = MediaType.VIDEO, 7000),
        Item("https://www.android.com/intl/es_es/", type = MediaType.URL, 10000)
    )
}
