package com.example.proyecto_listado.ui

import android.R.attr.text
import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proyecto_listado.model.MediaType
import com.example.proyecto_listado.viewModel.ViewModel
import com.example.proyecto_listado.webView.WebViewComponent
import kotlinx.coroutines.delay

@SuppressLint("DiscouragedApi")
@Composable
fun PlayerScreen(viewModel: ViewModel = viewModel()) {
    val mediaItems = viewModel.mediaItems
    val context = LocalContext.current

    var currentIndex by remember { mutableIntStateOf(0) }
    val currentItem = mediaItems[currentIndex]

    var visible = remember { mutableStateOf(false) }

    AnimatedVisibility(
        visible = true,
        enter = fadeIn(animationSpec = tween(durationMillis = 5000)),
        exit = fadeOut(animationSpec = tween(durationMillis = 5000))
    ) {


        Box(modifier = Modifier.fillMaxSize()) {
            FloatingActionButton(
                onClick = { (context as Activity).finish() },
                contentColor = Color.White,
                containerColor = Color.Blue,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(24.dp)
            ) {
                Icon(
                    Icons.Filled.Close,
                    "Cerrar aplicacion",
                )
            }

            when (currentItem.type) {
                MediaType.IMAGE -> {
                    Image(
                        painter = painterResource(
                            id = context.resources.getIdentifier(
                                currentItem.file,
                                "raw",
                                context.packageName
                            )
                        ),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )

                }

                MediaType.VIDEO -> {
                    VideoPlayer(context, "video")
                }

                MediaType.URL -> {
                    WebViewComponent("https://orbys.eu/")
                }
            }
        }
    }

    // Recompone y lleva la duracion y conteo
    LaunchedEffect(currentItem) {
        delay(currentItem.duration.toLong())
        currentIndex = (currentIndex + 1) % mediaItems.size
    }
}