package com.example.proyecto_listado.model

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
    fun Render(context: Context) {
        var currentIndex by remember { mutableIntStateOf(0) }
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
                var isVideoReady by remember { mutableStateOf(false) }

                if (isVideoReady) {
                    VideoPlayer(context, file)
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black)
                    ) {
                        LaunchedEffect(currentIndex) {
                            delay(1000)
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
        Item("paisaje", type = MediaType.VIDEO, 7000),
        Item("gato", type = MediaType.IMAGE, 4000),
        Item("mar", type = MediaType.VIDEO, 7000),
        Item("https://github.com/", type = MediaType.URL, 10000),
        Item("fuego", type = MediaType.VIDEO, 7000),

        Item("lago", type = MediaType.IMAGE, 4000),

        Item("https://orbys.eu/", type = MediaType.URL, 10000),
        Item("arana", type = MediaType.IMAGE, 4000),
        Item("https://www.android.com/intl/es_es/", type = MediaType.URL, 10000)


    )
}
