package com.example.proyecto_listado.model

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.proyecto_listado.exoPlayer.VideoPlayer
import com.example.proyecto_listado.webView.WebViewComponent

data class Item(val file: String, val type: MediaType, val duration: Int) {
    @SuppressLint("DiscouragedApi")
    @Composable
    fun Render(context: Context, transitionType: TransitionType) {
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
                // Manejo de transiciones para video
                if (transitionType == TransitionType.EXPAND_AND_SHRINK || transitionType == TransitionType.SCALE ||
                    transitionType == TransitionType.EXPAND_AND_SHRINK_VERTICALLY ||
                    transitionType == TransitionType.EXPAND_AND_SHRINK_HORIZONTALLY ||
                    transitionType == TransitionType.SLIDE_VERTICALLY ||
                    transitionType == TransitionType.SLIDE_HORIZONTALLY
                ) {
                    VideoPlayer(context, file)
                } else {
                    // Para fade y slides usar esta lógica para evitar error de ExoPlayer
                    var isVideoReady by remember { mutableStateOf(false) }


                    Box(modifier = Modifier.fillMaxSize()) {
                        // Pantalla negra inicial
                        AnimatedVisibility(
                            visible = isVideoReady,
                            exit = fadeOut(animationSpec = tween(durationMillis = 2000))
                        ) {

                        }

                        // Lógica para preparar el video
                        LaunchedEffect(Unit) {
                            // Pantalla negra antes de mostrar el video

                            // Desvanecer la pantalla negra y mostrar el video
                            isVideoReady = true
                        }

                        // Video con animación
                        AnimatedVisibility(
                            visible = isVideoReady,
                            enter = when (transitionType) {
                                TransitionType.FADE -> fadeIn(animationSpec = tween(durationMillis = 2000))

                                else -> fadeIn(animationSpec = tween(durationMillis = 2000))
                            },
                            exit = fadeOut(animationSpec = tween(durationMillis = 2000))
                        ) {
                            VideoPlayer(context, file)
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

enum class TransitionType {
    FADE,
    EXPAND_AND_SHRINK,
    EXPAND_AND_SHRINK_VERTICALLY,
    EXPAND_AND_SHRINK_HORIZONTALLY,
    SCALE,
    SLIDE_VERTICALLY,
    SLIDE_HORIZONTALLY

}

object MediaData {
    val items = listOf(
        Item("gato", type = MediaType.IMAGE, 4000),
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
