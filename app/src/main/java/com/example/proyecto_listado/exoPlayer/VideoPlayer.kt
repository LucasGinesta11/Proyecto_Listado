package com.example.proyecto_listado.exoPlayer

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

@Composable
fun VideoPlayer(context: Context, video: String) {
    val exoplayer = remember {
        ExoPlayer.Builder(context).build().apply {
            val uri = Uri.parse("android.resource://${context.packageName}/raw/$video")
            val mediaItem = MediaItem.fromUri(uri)
            setMediaItem(mediaItem)
            prepare()
            playWhenReady = true
        }
    }

    // Liberar recursos
    DisposableEffect(Unit) {
        onDispose {
            exoplayer.release()
        }
    }

    AndroidView(factory = {
        PlayerView(context).apply {
            player = exoplayer
            useController = false
        }
    }, modifier = Modifier.fillMaxSize())
}